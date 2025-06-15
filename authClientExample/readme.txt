AuthClientExample

This example provide a simple java based ScramSha256 HTTP client for authenticating with a Niagara 4.0 station. HTTP
digest requires that the server store passwords in a reversible form. This goes against security best practices for
storing credentials and is not supported by the standard Niagara station digest authentication scheme.

The ScramSha256Client provided is a condensed form and is missing a few optimization that the may prove to be
beneficial.

- Since java works on a garbage collection mechanism, immutable objects, like strings, can linger in memory for a
  while. A preferable approach would be to use byte or char arrays for storing sensitive info and zeroing them out
  when done, even before garbage collection has occurred. While this is still not perfect (memory reallocation by the
  vm since arrays may not be pinned), this is a much better approach.

- The standard array and string comparison functions in java are designed as a fail fast comparison. This means that
  as soon as the comparison discovers that the objects are of different length or when the come across the first
  mismatched character, they will fail. For string and array comparisons, use a constant time comparison algorithm
  instead of the standard ones in java.

- On slow platforms, calculating some of the values (like the salted password) can take quite a bit of time. Creating
  a cache for the salted password so that repeated recalculations aren't necessary can be beneficial. This needs to be
  balanced with storing sensitive data in memory.

In the AuthClientExample class is a reference to an inner class called the TrustModifier class. This class is for
demonstration purposes only and SHOULD NOT be used in production under any circumstance. It may be tempting to say
"It's not that big of a deal!" That would be completely incorrect and use of the class completely invalidates the use
of TLS since identity can no longer be trusted (non-repudiation).

This example makes use of a java precompiler called java-comment-preprocessor and can be found at
https://github.com/raydac/java-comment-preprocessor. To enable/disable the bulk of the output, comment out the
line in preprocess.gradle that refers to DEBUG.

To compile:

run "gradlew build" from the project root directory.

run "java -jar .\build\libs\AuthClientExample-1.5-SNAPSHOT.jar http[s]://<username>:<password>@<host>[:<port>] [client_type]" from
the project root directory.

client_type can be "ax", "n4", or "n4header". Default is "n4header". n4header refers to the header authentication mechanism added in
Niagara 4.4. This mechanism is not supported in N4 stations prior to 4.4. "n4" client_type should be used for pre-4.4 stations.