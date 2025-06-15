These are some example Niagara modules demonstrating the use of the Niagara
developer environment.

# Environment setup

You must have a working Niagara installation and point the build
environment at it for these samples to work. This can be done in one of two
ways: environment variables or an `environment.gradle` script

## Environment variables

The `NIAGARA_HOME` and `NIAGARA_USER_HOME` variables must be set. If you
are running with the Niagara console, `NIAGARA_HOME` is already set; you
will just need to set `NIAGARA_USER_HOME` to the correct path on your
platform. This can vary, but usually defaults to:

```
C:\Users\USERNAME\Niagara4.x\BRAND
```

## environment.gradle

Note: If you have an environment.gradle from a working environment, you can
just drop it in.

A sample environment.gradle is provided at
`environment.gradle.SAMPLE`. Rename it to `environment.gradle` and set its
contents as appropriate. For example:

``` groovy
gradle.ext.niagara_home = C:/Niagara/Niagara/4.9.0.198
gradle.ext.niagara_user_home = C:/Users/USERNAME/Niagara4.9/tridium
```

