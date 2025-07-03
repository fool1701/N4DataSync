============================================================
PAGE 53
============================================================

# Localization
## Overview
 All aspects of the Niagara framework are designed for localization. The basic philosophy for localization is that one
 language may supported in-place or multiple languages may be supported via indirection. The foundation of localization
 is based on the Context and Lexicon APIs.
## Context
 Any framework API which is designed to return a string for human display, takes a Context parameter. Context provides
 information to an API about the context of the call including the desired locale. Many APIs implement Context directly
 including OrdTarget, ExportOp, and WebOp. For example if you are processing a web HTTP request, you can pass the
 WebOp instance as your Context and the framework will automatically localize display strings based on the user who is
 logged in for that HTTP session.
 Note that Workbench code always uses the default locale of the VM, so it is typical to just use null for Context.
 However code designed to run in a station VM should always pass through Context.
## Lexicon
 Lexicons are Java properties files which store localized key/value pairs. They are either deployed within modules or
 located in a directory called "file:!lexicon/". A module may contain multiple lexicon files, each of which is associated with
 a language. The "file:!lexicon/" directory may contain zero or more lang subdirectories, which are used to store the
 lexicon files for specific languages, where lang is the locale code. Within the directory there is a file per module named
 "moduleName.lexicon". Every module with a lexicon should also provide a fallback lexicon bundled in the root directory
 of module's jar file: "module://moduleName/moduleName.lexicon" (note in the source tree it is just "module.lexicon").
 Access to lexicons is provided via the Lexicon API.
## BFormat
 Many Niagara APIs make use of the BFormat class to store a formatted display string. BFormat provides the ability to
 insert special function calls into the display string using the percent sign. One of these calls maps a string defined in a
 lexicon via the syntax "%lexicon(module:key)%. Whenever a display string is stored as a BFormat, you may store one
 locale in-place or you may use the %lexicon()% call to indirectly reference a lexicon string.
## Slots
 One of the first steps in localization, is to provide locale specific slot names. Every slot has a programmatic name and a
 context sensitive display name. The process for deriving the display name for a slot:
1. BComplex.getDisplayName(Slot, Context): The first step is to call this API. You may override this method to
 provide your own implementation for localization.
2. NameMap: The framework looks for a slot called "displayNames" that stores a BNameMap. If a NameMap is found
 and it contains an entry for the slot, that is used for the display name. Note the NameMap value is evaluated as a
 BFormat, so it may contain a lexicon call. NameMaps are useful ways to localize specific slots, localize instances, or
 to localize dynamic slots.
3. Lexicon: Next the framework attempts to find the display name for a slot using the lexicon. The lexicon module is
 based on the slot's declaring type and the key is the slot name itself.
4. Slot Default: If we still haven't found a display name, then we use a fallback mechanism. If the slot is frozen, the
 display name is the result of TextUtil.toFriendly(name). If the slot is dynamic the display name is the
 result of SlotPath.unescape(name).
## Facets
============================================================
PAGE 54
============================================================

 Sometimes facets are used to store display string. In these cases, the string is interpreted as a BFormat so that a
 %lexicon()% call may be configured. This design pattern is used for:
Boolean trueText
Boolean falseText
## FrozenEnums
 Compile time enums subclass from BFrozenEnum. Similar to slot names and display names, enums have a programmatic
 tag and a display tag. Localization of display tags uses the following process:
1. Lexicon: The framework first attempts to map the display tag to a lexicon. The module is the declaring type of the
 FrozenEnum and the key is the programmatic tag.
2. Default: If a display tag isn't found in the lexicon, then the fallback is the result of
 TextUtil.toFriendly(tag).
## DynamicEnums
 Localization of BDynamicEnums is done via the BEnumRange API. An EnumRange may be associated with a
 DynamicEnum directly via DynamicEnum.make() or indirectly via Context facets. An EnumRange may be composed of a
 FrozenEnum's range and/or dynamic ordinal/tag pairs. Any portion of the frozen range uses the same localization
 process as FrozenEnun. The dynamic portion of the range uses the following process:
1. Lexicon: If BEnumRange.getOptions() contains a "lexicon" value, then we attempt to map the display tag to a
 lexicon where the module is the value of the "lexicon" option and the key is the programmatic tag.
2. Default: If a display tag is not found using the lexicon, and the ordinal does map to a programmatic tag, then the
 result of SlotPath.unescape(tag) is returned.
3. Ordinal: The display tag for an ordinal that isn't included in the range is the ordinal itself as a decimal integer.
## User Interface
 When building a user interface via the bajaui APIs, all display text should be localizable via lexicons. In the case of simple
 BLabels, just using the Lexicon API is the best strategy.
 The Command and ToggleCommand APIs also provide built-in support for fetching their label, icon, accelerator, and
 description from a lexicon. Take the following code example:

class DoIt extends Command
{
  DoIt(BWidget owner) { super(owner, lex, "do.it"); }
  static final Lexicon lex = Lexicon.make(MyCommand.class);
}

 In the example above DoIt would automatically have it's display configured from the declaring module's lexicon:
do.it.label=Do It
do.it.icon=module://icons/x16/build.png
do.it.accelerator=Ctrl+D
do.it.description=Do it, whatever it is.
## Locale Selection
 Every time a Niagara VM is started it attempts to select a default locale using the host operating system. The OS default
 may be overridden via the command line flag "-locale:lang", where lang is the locale code. The locale code can be any
 string that maps to a lexicon directory, but typically it is a ISO 639 locale code such as "fr". The default locale of the VM
 may be accessed via the Sys.getLanguage() API.
 When the workbench is launched as a desktop application it follows the rules above to select it's locale. Once selected the
============================================================
PAGE 55
============================================================

 entire workbench uses that locale independent of user accounts used to log into stations.
 The locale for web browser access to a station follows the rules:
1. User.language: If the language property of user is a non-empty string, then it defines the locale to use.
2. Accept Language: Next the framework tries to select a locale based on the "Accept-Language" passed in the
 browser's HTTP request. Typically this is configured in the browser's options.
3. Default: If all else fails, then the default locale of the station's VM is used
## Time Formatting
 The default time format is defined by the lexicon key baja:timeFormat. But it may be selectively overridden by users. To
 change the time format in the Workbench use General Options under Tools | Options. Use the User.facets property to
 change it for browser users.
 Niagara' time format uses a simple pattern language:
Pattern Description
YY Two digit year
YYYY Four digit year
M One digit month
MM Two digit month
MMM Abbreviated month name
D One digit day of month
DD Two digit day of month
h One digit 12 hour
hh Two digit 12 hour
H One digit 24 hour
HH Two digit 24 hour
mm Two digit minutes
ss Seconds (and milliseconds if applicable)
a AM/PM marker
z Timezone
anything else Character literal
 In addition to the time format configured by the user, developers may customize the resolution via the following facets:
BFacets.SHOW_TIME
BFacets.SHOW_DATE
BFacets.SHOW_SECONDS
BFacets.SHOW_MILLISECONDS
BFacets.SHOW_TIME_ZONE
 To programmatically format a time using this infrastructure use the BAbsTime or BTime APIs.
## Unit Conversion
============================================================
PAGE 56
============================================================

 By default the framework displays all numeric values using their configured units (via Context facets). Users may override
 this behavior to have all values converted to the US/English system or SI/Metric systems. To enable this feature in
 Workbench use General Options under Tools | Options. Use the User.facets property to enable it for browser users.
 The list of units known to the system and how to convert is configured via the file:!lib/units.xml XML file. The mapping
 of those units between English and Metric is done in the file:!lib/unitConversion.xml XML file.
 To programmatically format and auto-convert numerics use the BFloat or BDouble APIs.
 Note this unit conversion is independent of the conversion which may be performed by ProxyExts when mapping a point
 into a driver.
