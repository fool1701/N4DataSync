/*
 *
 *  * Copyright (c) 2015. Tridium, Inc. All Rights Reserved.
 *
 */

package com.tridium.example.auth.client;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

/**
 * ScramSha256Client is a utility class for managing the creation and processing
 * of messages used during a ScramSha256 handshake. This version of the class has
 * been simplified. Things that can be improved:
 *
 *   - Switch from using a String for password to a byte[] or char[] since they
 *     can be cleared prior to garbage collection
 *
 *   - For the string and byte[] comparisons, use a constant time comparison
 *     algorythm instead of the standard ones in java
 *
 *   - Creating a cache for the salted password so that repeated recalculations
 *     aren't necessary. This needs to be balanced with storing sensitive data
 *     in memory.
 *
 * NOTES:
 *
 *   When a problem with the data is detected (generally due to invalid credentials,
 *   etc.), a SecurityException is thrown. The reason no messages are attached
 *   to the security exception is that the client should not be told why it failed
 *   since that might provide hints to an attacker.
 *
 *   Refer to RFC5802 (https://tools.ietf.org/html/rfc5802) for details regarding
 *   the ScramSha handshake mechanism.
 */
public final class ScramSha256Client
{
  /**
   * Simple constructor that takes the username and password
   *
   * @param userName
   * @param password
   */
  public ScramSha256Client(String userName, String password)
  {
    try
    {
      this.userName = usernamePrep(userName);
      this.password = passwordPrep(password);
    }
    catch(Exception e)
    {
      clearData();
      throw new SecurityException();
    }
  }

  /**
   * Creates the client-first-message as described in RFC5802
   * @return the completed client message
   */
  public String createClientFirstMessage()
  {
    try
    {
      byte[] nonceVal = new byte[16];
      new SecureRandom().nextBytes(nonceVal);
      clientNonce = Base64.getEncoder().encodeToString(nonceVal);
      clientFirstMessageBare = _createClientFirstMessageBare(userName, clientNonce);
      StringBuilder buf = new StringBuilder("n,,").append(clientFirstMessageBare);

      //#ifdef DEBUG
      debug("clientFirstMessage = " + buf.toString());
      //#endif
      return buf.toString();
    }
    catch(Exception e)
    {
      //#ifdef DEBUG
      debug("exception in createClientFirstMessage:");
      if (debugFlag) e.printStackTrace();
      //#endif
    }

    clearData();
    throw new SecurityException();
  }

  /**
   * based on the contents of the serverFirstMessage, creates the client-final-message
   * that can be send back to the server, or throws an exception if things aren't
   * proceeding correctly
   *
   * @param serverFirstMessage first message returned by the server as described in RFC502
   * @return the completed client message
   */
  public String createClientFinalMessage(String serverFirstMessage)
  {
    try
    {
      //#ifdef DEBUG
      debug("received serverFirstMessage = " + serverFirstMessage);
      //#endif

      // grab server provided values
      Properties values = parseMessage(serverFirstMessage);
      iterationCount = Integer.parseInt(values.getProperty("i"));
      salt = Base64.getDecoder().decode(values.getProperty("s"));

      // sanity check, make sure client nonce hasn't changed
      String clientNonce = values.getProperty("r").substring(0, this.clientNonce.length());
      if (!this.clientNonce.equals(clientNonce))
        throw new SecurityException();

      serverNonce = values.getProperty("r").substring(this.clientNonce.length());

      // calculate unknown values
      saltedPassword = _createSaltedPassword(password, salt, iterationCount);
      clientFinalMessageWithoutProof = _createClientFinalMessageWithoutProof(this.clientNonce, serverNonce);
      authMessage = _createAuthMessage(clientFirstMessageBare, serverFirstMessage, clientFinalMessageWithoutProof);
      byte[] clientProof = _createClientProof(saltedPassword, authMessage);

      // now that we are done with the password, clear it
      password = null;

      String clientFinalMessage = clientFinalMessageWithoutProof + ",p=" + Base64.getEncoder().encodeToString(clientProof);
      //#ifdef DEBUG
      debug("clientFinalMessage = " + clientFinalMessage);
      //#endif

      return clientFinalMessage;
    }
    catch(Exception e)
    {
      //#ifdef DEBUG
      debug("exception in createClientFinalMessage:");
      if (debugFlag) e.printStackTrace();
      //#endif
    }

    clearData();

    throw new SecurityException();
  }

  /**
   * parses the final message from the server and performs the last validation as defined in RFC5802
   *
   * @param serverFinalMessage final message from the server
   */
  public void processServerFinalMessage(String serverFinalMessage)
  {
    try
    {
      //#ifdef DEBUG
      debug("received serverFinalMessage = " + serverFinalMessage);
      //#endif
      Properties values = parseMessage(serverFinalMessage);
      byte[] serverSignature = _createServerSignature(saltedPassword, authMessage);
      byte[] remoteServerSignature = Base64.getDecoder().decode(values.getProperty("v"));

      //#ifdef DEBUG
      debug("comparing server signature " + bytesToHex(serverSignature) + " <=> " + bytesToHex(remoteServerSignature));
      //#endif
      if (Arrays.equals(serverSignature, remoteServerSignature))
      {
        //#ifdef DEBUG
        debug("accepted remoteServerSignature");
        //#endif
        return;
      }
    }
    catch(Exception e)
    {
      //#ifdef DEBUG
      debug("exception in processServerFinalMessage:");
      if (debugFlag) e.printStackTrace();
      //#endif
    }
    finally
    {
      clearData();
    }

    //#ifdef DEBUG
    debug("server validation failed");
    //#endif

    throw new SecurityException();
  }

  /**
   * standardizes the username string for languages that have multiple
   * definitions for the same character sets
   *
   * @param userName
   * @return normalized username
   */
  private final String usernamePrep(String userName)
  {
    String value = Normalizer.normalize(userName, Normalizer.Form.NFKC);
    value = value.replaceAll("=", "=3D");
    value = value.replaceAll(",", "=2C");

    return value;
  }

  /**
   * standardizes the password string for languages that have multiple
   * definitions for the same character sets
   *
   * @param password
   * @return normalized username
   */
  private final String passwordPrep(String password)
  {
    return Normalizer.normalize(password, Normalizer.Form.NFKC);
  }

  /**
   * creates a salted pasword using PBKDF2 per RFC5802
   *
   * @param password normalized password
   * @param salt salt value returned by server
   * @param iterationCount iteration count value returned by server
   * @return generated salted password byte[]
   * @throws Exception
   */
  private final byte[] _createSaltedPassword(String password, byte[] salt, int iterationCount)
      throws Exception
  {
    byte[] dk;

    PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, 256);
    SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    dk = key.generateSecret(keySpec).getEncoded();

    return dk;
  }

  /**
   * performs a hash of the provider bytes
   *
   * @param src bytes to use to calculate hash
   * @return hash value
   * @throws NoSuchAlgorithmException
   */
  private final byte[] h(byte[] src) throws NoSuchAlgorithmException
  {
    MessageDigest sha256 = MessageDigest.getInstance("Sha-256");
    sha256.update(src);
    byte[] hash = sha256.digest();
    return hash;
  }

  /**
   * performs a basic xor of two byte arrays
   *
   * @param left bytes to use to calculate hash
   * @param right bytes to use to calculate hash
   * @return hash value
   * @throws NoSuchAlgorithmException
   */
  private final byte[] xor(byte[] left, byte[] right) throws NullPointerException, IllegalArgumentException
  {
    if (left == null || right == null) throw new NullPointerException();
    if (left.length != right.length) throw new IllegalArgumentException();

    byte[] result = new byte[left.length];
    for (int i = 0; i < left.length; i++)
    {
      result[i] = (byte) (left[i] ^ right[i]);
    }

    return result;
  }

  /**
   * calculates a basic hmac for the provided key and data byte[]
   *
   * @param keyBytes
   * @param dataBytes
   * @return
   */
  private final byte[] hmac(byte[] keyBytes, byte[] dataBytes)
  {
    Mac mac;
    try
    {
      mac = Mac.getInstance("HmacSha256");
      SecretKey macKey = null;
      if (keyBytes.length > 0)
        macKey = new SecretKeySpec(keyBytes, "HmacSHA256");
      mac.init(macKey);
      byte[] result = mac.doFinal(dataBytes);
      return result;
    }
    catch (Exception e)
    {
      throw new SecurityException("Could not get hmac: " + e);
    }
  }

  /**
   * create the client proof per Rfc5802
   *
   * @param saltedPassword
   * @param authMessage
   * @return proof byte[]
   * @throws NoSuchAlgorithmException
   */
  private final byte[] _createClientProof(byte[] saltedPassword, String authMessage)
      throws NoSuchAlgorithmException
  {
    byte[] clientKey = _createClientKey(saltedPassword);
    byte[] storedKey = h(clientKey);
    byte[] clientSignature = hmac(storedKey, authMessage.getBytes(StandardCharsets.UTF_8));
    byte[] clientProof = xor(clientKey, clientSignature);

    return clientProof;
  }

  /**
   * calculates client key value as a byte array with the salted password
   *
   * @param saltedPassword
   * @return client key
   */
  private final byte[] _createClientKey(byte[] saltedPassword)
  {
    byte[] clientKey = hmac(saltedPassword, "Client Key".getBytes(StandardCharsets.UTF_8));
    return clientKey;
  }

  /**
   * creates the basic components of the client first message
   *
   * @param userName
   * @param clientNonce
   * @return
   */
  private final String _createClientFirstMessageBare(String userName, String clientNonce)
  {
    StringBuilder buf = new StringBuilder("n=");
    buf.append(userName).append(",r=").append(clientNonce);
    return buf.toString();
  }

  /**
   * extract the various tuples from the return message and returns as a properties bundle
   *
   * @param message
   * @return
   */
  private final Properties parseMessage(String message)
  {
    Properties props = new Properties();

    String[] components = message.split(",");
    for (int i = 0; i < components.length; i++)
    {
      if (components[i].length() > 0 && components[i].indexOf("=") > 0)
      {
        int eq = components[i].indexOf("=");
        String key = components[i].substring(0, eq);
        String value = components[i].substring(eq + 1);
        props.put(key, value);
      }
    }

    return props;
  }

  /**
   * creates the client final message before the proof has been applied
   *
   * @param clientNonce
   * @param serverNonce
   * @return
   */
  private final String _createClientFinalMessageWithoutProof(String clientNonce, String serverNonce)
  {
    StringBuilder buf = new StringBuilder("c=biws,r=");
    buf.append(clientNonce).append(serverNonce);
    return buf.toString();
  }

  /**
   * create the auth message from provided components per Rfc5802
   *
   * @param clientFirstMessageBare
   * @param serverFirstMessage
   * @param clientFinalMessageWithoutProof
   * @return
   */
  private final String _createAuthMessage(String clientFirstMessageBare, String serverFirstMessage, String clientFinalMessageWithoutProof)
  {
    StringBuilder buf = new StringBuilder(clientFirstMessageBare);
    buf.append(",").append(serverFirstMessage).append(",").append(clientFinalMessageWithoutProof);
    return buf.toString();
  }

  /**
   * calculate the server signature derived from the auth message and salted password per
   * Rfc5802
   *
   * @param saltedPassword
   * @param authMessage
   * @return
   */
  private final byte[] _createServerSignature(byte[] saltedPassword, String authMessage)
  {
    byte[] serverKey = hmac(saltedPassword, "Server Key".getBytes(StandardCharsets.UTF_8));
    byte[] serverSignature = hmac(serverKey, authMessage.getBytes(StandardCharsets.UTF_8));

    return serverSignature;
  }

  /**
   * clean up any data that may remain
   */
  private final void clearData()
  {
    userName = null;
    password = null;
    salt = null;
    iterationCount = 0;
    saltedPassword = null;
    clientNonce = null;
    serverNonce = null;
    clientFirstMessageBare = null;
    clientFinalMessageWithoutProof = null;
    authMessage = null;
  }

  final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

  public static String bytesToHex(byte[] bytes)
  {
    char[] hexChars = new char[bytes.length * 2];
    for ( int j = 0; j < bytes.length; j++ ) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = hexArray[v >>> 4];
      hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }

    return new String(hexChars);
  }

  //#ifdef DEBUG
  private static void debug(String msg)
  {
    if (debugFlag)
      System.err.println("[ScramSha256Client] " + msg);
  }
  //#endif

  private String userName = null;
  private String password = null;
  private byte[] salt = null;
  private int iterationCount = 0;
  private byte[] saltedPassword = null;
  private String clientNonce = null;
  private String serverNonce = null;
  private String clientFirstMessageBare = null;
  private String clientFinalMessageWithoutProof = null;
  private String authMessage = null;
  public static boolean debugFlag = true;

}
