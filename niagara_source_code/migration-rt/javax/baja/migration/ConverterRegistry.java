/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.migration;

import javax.baja.registry.TypeInfo;
import javax.baja.sys.BInterface;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.ModuleException;
import javax.baja.sys.ModuleNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.sys.TypeException;
import javax.baja.util.BTypeSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ConverterRegistry
 *
 * This class manages a registry of {@link javax.baja.migration.BIBogElementConverter}s, which are registered
 * on Niagara AX types to convert them to Niagara 4.
 *
 * @author     <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *
 */
public class ConverterRegistry
{
  private static boolean initialized = false;

  /**
   * Initialize our registry of bogConverters.
   */
  public static void initialize()
  {
    TypeInfo[] types = Sys.getRegistry().getConcreteTypes(BIBogElementConverter.TYPE.getTypeInfo());
    setupConvertersMap(types, true);
    TypeInfo[] pxTypes = Sys.getRegistry().getConcreteTypes(BIPxElementConverter.TYPE.getTypeInfo());
    setupConvertersMap(pxTypes, false);
    initialized = true;
  }

  private static void setupConvertersMap(TypeInfo[] types, boolean bogConverter)
  {
    for (TypeInfo typeInfo : types)
    {
      BInterface converter;
      List<String> convertTypes;
      if(bogConverter)
      {
        converter = (BIBogElementConverter) typeInfo.getInstance();
        convertTypes = ((BIBogElementConverter)converter).getConvertTypes();
      }
      else
      {
        converter = (BIPxElementConverter) typeInfo.getInstance();
        convertTypes = ((BIPxElementConverter)converter).getConvertTypeSpecs();
      }

      Type newType = converter.getType();
      for (String convertType : convertTypes)
      {
        BInterface alreadyThere = bogConverter ? bogConverters.get(convertType) : pxConverters.get(convertType);
        if ((alreadyThere != null) && !alreadyThere.getType().equals(newType))
        {
          throw new DuplicateConverterException(convertType, newType, alreadyThere.getType(), bogConverter);
        }

        // register any package conversions
        List<String> packageConversions;
        if(bogConverter)
        {
          bogConverters.put(convertType,(BIBogElementConverter) converter);
          packageConversions = ((BIBogElementConverter) converter).getPackageConversions();
        }
        else
        {
          if (convertType.contains(":"))
          {
            // the pxConverter needs to keep track of modules and types since modules and types are kept separate in PX files
            String[] splitConvertType = convertType.split(":");
            String moduleType = splitConvertType[0];
            pxConverters.put(moduleType, (BIPxElementConverter) converter);
            String typeName = splitConvertType[1];
            // no need to worry about duplicate types. they are handled by the DuplicateConverterException above
            pxConverters.put(typeName, (BIPxElementConverter) converter);
          }

          // the px properties section of the px file may contain a full typeSpec
          pxConverters.put(convertType, (BIPxElementConverter) converter);

          packageConversions = ((BIPxElementConverter) converter).getPackageConversions();
        }

        if(packageConversions != null)
        {
          for (String pkg : packageConversions)
          {
            String[] a = pkg.split(",");
            if (a.length != 2)
            {
              throw new BajaRuntimeException("Invalid format in " +
                  converter.getClass().getName() + ".getPackageConversions()");
            }
            ConverterRegistry.packageConversions.put(a[0], a[1]);
          }
        }
      }
    }
  }

  /**
   * Check for a registered package conversion for the specified package.
   * @param packageName specify a java package name
   * @return a modified package name or null if no conversion needed
   */
  public static String lookUpPackageConversion(String packageName)
  {
    if (!initialized) initialize();
    String newPkg = packageConversions.get(packageName);
    return (newPkg != null) ? newPkg : packageName;
  }

  /**
   * Lookup the {@link javax.baja.migration.BIPxElementConverter}s that can handle a given typespec or module by name
   * from a Px file.
   * <p>
   * This returns the list of pxConverters it finds that can convert all of the various classes in the
   * hierarchy of a particular Niagara {@link javax.baja.sys.Type}. The list is ordered from superclass to subclass,
   * and <b>MUST</b> be applied in that order.
   * @param moduleOrTypeName a typespec name
   * @return the ordered list of bogConverters to apply for that type.
   */
  @SuppressWarnings("unchecked")
  public static List<BIPxElementConverter> lookupPxConverters(String moduleOrTypeName)
  {
    return (List<BIPxElementConverter>) getConverters(moduleOrTypeName, false);
  }

  /**
   * Lookup the {@link javax.baja.migration.BIBogElementConverter}s that can handle a given typespec or module by
   * name from a bog file.
   * <p>
   * This returns the list of bogConverters it finds that can convert all of the various classes in the
   * hierarchy of a particular Niagara {@link javax.baja.sys.Type}.  The list is ordered from superclass
   * to subclass, and <b>MUST</b> be applied in that order.
   * @param moduleOrTypeName a typespec or module name
   * @return the ordered list of bogConverters to apply for that type.
   */
  @SuppressWarnings("unchecked")
  public static List<BIBogElementConverter> lookupConverters(String moduleOrTypeName)
  {
    return (List<BIBogElementConverter>) getConverters(moduleOrTypeName, true);
  }

  private static List<? extends BInterface> getConverters(String moduleOrTypeName, boolean bogConverter)
  {
    if (!initialized) initialize();
    List<BInterface> list = new ArrayList<>();

    if (moduleOrTypeName != null)
    {
      BInterface converter = bogConverter ? bogConverters.get(moduleOrTypeName) : pxConverters.get(moduleOrTypeName);
      if (converter == null)
      {
        // No converter found for the supplied name.  Check to see if we
        // know about this as a module or type in N4.  If the module or type name
        // cannot be found in the registry, then provide a removal converter to remove
        // any instances of types from that module.  If no exception occurs, the
        // module or type still exists and does not need conversion, so return null.
        int colon = moduleOrTypeName.indexOf(":");
        try
        {
          if (colon < 0)
            Sys.getRegistry().getModules(moduleOrTypeName);
          else
            Sys.getRegistry().getModules(moduleOrTypeName.substring(0,colon));
        }
        catch (ModuleNotFoundException e1)
        {
          // Build a list with the removal converter.  We don't want to walk the hierarchy
          // because we're going to remove the bog element.  So just return the list now.
          BInterface remover = bogConverter ?
              new BModuleRemovalConverter(moduleOrTypeName) : new BPxRemovalConverter(moduleOrTypeName);
          list.add(remover);

          if (log.isLoggable(Level.FINEST)) log.finest("Returning converter list for "+moduleOrTypeName+": "+list);
          return list;
        }
      }
      else
      {
        list.add(0, converter);
      }

      if (moduleOrTypeName.indexOf(":") > 0)
      {
        BTypeSpec ts = BTypeSpec.make(moduleOrTypeName);
        try
        {
          // We've already added the starting type
          Type t = ts.getResolvedType().getSuperType();
          while (t != null)  // get type from the converter - the new type it creates.
          {
            BInterface converter1 = bogConverter ? bogConverters.get(t.getTypeSpec().toString())
                : pxConverters.get(t.getTypeSpec().toString());

            if (converter1 != null)
            {
              // Add this to the beginning of the list so the list will already be in
              // the correct order.
              list.add(0, converter1);
            }
            t = t.getSuperType();
          }
        }
        catch (ModuleException | TypeException e)
        {
          if (log.isLoggable(Level.FINE))
            log.fine("No registered type for " + moduleOrTypeName + "; returning only the registered converter " + converter);
        }
      }
    }

    if (log.isLoggable(Level.FINEST)) log.finest("Returning converter list for "+moduleOrTypeName+": "+list);
    return list;
  }

  /**
   * Command line check for the list of available bogConverters, and to identify the converter
   * for a particular type.
   * If invoked with no arguments, this will list all the bogConverters registered along with the
   * types they claim to convert.
   * If invoked with one or more arguments, each argument is interpreted as a module or type name
   * to be used to lookup a converter, and the converter for that name is displayed.
   * @param args list of module/type names.
   */
  public static void main(String... args)
  {
    initialize();

    if (args.length == 0)
    {
      printConverters(true);
      printConverters(false);
    }
    else
    {
      for (String arg : args)
      {
        try
        {
          BTypeSpec typeSpec = BTypeSpec.make(arg);
          Type type = typeSpec.getResolvedType();
          if (type.is(BIBogElementConverter.TYPE))
          {
            printConvertTypes(typeSpec.getInstance().as(BIBogElementConverter.class));
          }
          else if(type.is(BIPxElementConverter.TYPE))
          {
            printConvertTypes(typeSpec.getInstance().as(BIPxElementConverter.class));
          }
          else
          {
            System.out.println("moduleOrTypeName: " + arg + " -> Converters: " + lookupConverters(arg));
          }
        }
        catch (Exception e)
        {
          System.out.println("Could not list convertTypes for "+arg+":"+e);
        }
      }
    }
  }

  private static void printConverters(boolean bogConverter)
  {
    List<BInterface> printed = new ArrayList<>();
    Map<String, ? extends BInterface> converters;
    BInterface converter;
    if(bogConverter)
    {
      converters = bogConverters;
    }
    else
    {
      converters = pxConverters;
    }

    for (String key : converters.keySet())
    {
      converter = converters.get(key);
      if (printed.contains(converter)) continue;
      printed.add(converter);

      if(bogConverter)
      {
        printConvertTypes((BIBogElementConverter) converter);
      }
      else
      {
        printConvertTypes((BIPxElementConverter) converter);
      }
    }
  }

  private static void printConvertTypes(BIBogElementConverter converter)
  {
    System.out.println("Converter: "+converter.getType());
    for (String convertType : converter.getConvertTypes())
    {
      System.out.println("\t"+convertType);
    }
  }

  private static void printConvertTypes(BIPxElementConverter converter)
  {
    System.out.println("Converter: "+converter.getType());
    for (String convertType : converter.getConvertTypeSpecs())
    {
      System.out.println("\t"+convertType);
    }
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final Logger log = Logger.getLogger("migration.registry");


////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private static Map<String,BIBogElementConverter> bogConverters = new HashMap<>();
  private static Map<String,BIPxElementConverter> pxConverters = new HashMap<>();
  private static Map<String,String> packageConversions = new HashMap<>();

}
