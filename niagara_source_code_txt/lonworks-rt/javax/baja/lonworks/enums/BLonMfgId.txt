/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BLonMfgId class provides enumeration for the manufacturer 
 * field in LonMark program ids. Current to SPID Master List or 2012-4-2.
 *
 * @author    Robert Adams
 * @creation  14 Jan 01
 * @version   $Revision: 1$ $Date: 12/11/00 8:13:10 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "unknown", ordinal = 0),
    @Range(value = "echelon", ordinal = 1),
    @Range(value = "motorola", ordinal = 2),
    @Range(value = "ibm", ordinal = 3),
    @Range(value = "sild", ordinal = 4),
    @Range(value = "helvar", ordinal = 5),
    @Range(value = "ahlstrom", ordinal = 6),
    @Range(value = "tmi", ordinal = 7),
    @Range(value = "danfoss", ordinal = 8),
    @Range(value = "iec", ordinal = 9),
    @Range(value = "kaba", ordinal = 10),
    @Range(value = "ish", ordinal = 11),
    @Range(value = "honeywell", ordinal = 12),
    @Range(value = "leviton", ordinal = 13),
    @Range(value = "grayhill", ordinal = 14),
    @Range(value = "smartControls", ordinal = 15),
    @Range(value = "andover", ordinal = 16),
    @Range(value = "johnsonControls", ordinal = 17),
    @Range(value = "heatTimer", ordinal = 18),
    @Range(value = "taControl", ordinal = 19),
    @Range(value = "groupSchneider", ordinal = 20),
    @Range(value = "weidmuller", ordinal = 21),
    @Range(value = "siebe", ordinal = 22),
    @Range(value = "jGordonDesign", ordinal = 23),
    @Range(value = "circon", ordinal = 24),
    @Range(value = "staefa", ordinal = 25),
    @Range(value = "homeAutomation", ordinal = 26),
    @Range(value = "comelta", ordinal = 27),
    @Range(value = "hycal", ordinal = 28),
    @Range(value = "caradonTrend", ordinal = 29),
    @Range(value = "powerMeasurement", ordinal = 30),
    @Range(value = "csi", ordinal = 31),
    @Range(value = "abb", ordinal = 32),
    @Range(value = "electronicSystems", ordinal = 33),
    @Range(value = "continentalControl", ordinal = 34),
    @Range(value = "msrTechnolgien", ordinal = 35),
    @Range(value = "hubbell", ordinal = 36),
    @Range(value = "mcquay", ordinal = 37),
    @Range(value = "vaisala", ordinal = 38),
    @Range(value = "svm", ordinal = 39),
    @Range(value = "bircherGebaudeAg", ordinal = 40),
    @Range(value = "hachCompany", ordinal = 41),
    @Range(value = "theTraneCompany", ordinal = 42),
    @Range(value = "lintonSystems", ordinal = 43),
    @Range(value = "osmonics", ordinal = 44),
    @Range(value = "delmatic", ordinal = 45),
    @Range(value = "elmLtd", ordinal = 46),
    @Range(value = "philipsLighting", ordinal = 47),
    @Range(value = "safeguard", ordinal = 48),
    @Range(value = "seaboard", ordinal = 49),
    @Range(value = "lighthouse", ordinal = 50),
    @Range(value = "auslon", ordinal = 51),
    @Range(value = "kabaBenzing", ordinal = 52),
    @Range(value = "rpRichards", ordinal = 53),
    @Range(value = "camilleBauer", ordinal = 54),
    @Range(value = "honeywell37", ordinal = 55),
    @Range(value = "programmedWater", ordinal = 56),
    @Range(value = "magnetek", ordinal = 57),
    @Range(value = "mentzelUndKrutmann", ordinal = 58),
    @Range(value = "zellwegerAnalytics", ordinal = 59),
    @Range(value = "tlon", ordinal = 60),
    @Range(value = "enermet", ordinal = 61),
    @Range(value = "orasGroup", ordinal = 62),
    @Range(value = "mstAnalytics", ordinal = 63),
    @Range(value = "dhElektronikAnlagenbau", ordinal = 64),
    @Range(value = "alyaInternational", ordinal = 65),
    @Range(value = "crystalControls", ordinal = 66),
    @Range(value = "yokogawa", ordinal = 67),
    @Range(value = "douglasPowerEquip", ordinal = 68),
    @Range(value = "develcoElectronik", ordinal = 69),
    @Range(value = "gebruderTroxGmb", ordinal = 70),
    @Range(value = "tsiInc", ordinal = 71),
    @Range(value = "rikenKeikiCo", ordinal = 72),
    @Range(value = "gesytecGmbh", ordinal = 73),
    @Range(value = "cumminsEngineCo", ordinal = 74),
    @Range(value = "landertMotorenAg", ordinal = 75),
    @Range(value = "toshibaCorp", ordinal = 76),
    @Range(value = "satronInstrumentsInc", ordinal = 77),
    @Range(value = "toshibaInfoSystems", ordinal = 78),
    @Range(value = "fujiElectricCo", ordinal = 80),
    @Range(value = "computerProcessControls", ordinal = 81),
    @Range(value = "somfy", ordinal = 82),
    @Range(value = "alcoControls", ordinal = 83),
    @Range(value = "keleAndAssociates", ordinal = 84),
    @Range(value = "grundfosElectronics", ordinal = 85),
    @Range(value = "zoneControlsKb", ordinal = 86),
    @Range(value = "reko", ordinal = 87),
    @Range(value = "coactiveNetworksInc", ordinal = 89),
    @Range(value = "nodusGmbh", ordinal = 90),
    @Range(value = "acutherm", ordinal = 91),
    @Range(value = "sontayOpenSystems", ordinal = 92),
    @Range(value = "cAndKSystems", ordinal = 93),
    @Range(value = "sysmikGmbh", ordinal = 94),
    @Range(value = "yamatakeCorp", ordinal = 95),
    @Range(value = "ctiProducts", ordinal = 96),
    @Range(value = "belimoAutomation", ordinal = 97),
    @Range(value = "neurologicResearch", ordinal = 98),
    @Range(value = "cnaEngineers", ordinal = 99),
    @Range(value = "energyControlsInternational", ordinal = 100),
    @Range(value = "frSauterAg", ordinal = 101),
    @Range(value = "teldaElectronics", ordinal = 102),
    @Range(value = "comtecTechnologie", ordinal = 103),
    @Range(value = "abbGebaudetechnikAg", ordinal = 104),
    @Range(value = "siemensStaefaControlsUsa", ordinal = 105),
    @Range(value = "luxmateControlsGmbh", ordinal = 106),
    @Range(value = "matrixControls", ordinal = 107),
    @Range(value = "huppeFormSonnenschutzsysteme", ordinal = 108),
    @Range(value = "samsungHeavyIndustries", ordinal = 110),
    @Range(value = "kitzCorp", ordinal = 111),
    @Range(value = "wago", ordinal = 112),
    @Range(value = "matsushitaElectricWorks", ordinal = 113),
    @Range(value = "siemensLandisStaefaKorea", ordinal = 114),
    @Range(value = "samsonAg", ordinal = 115),
    @Range(value = "enelIt", ordinal = 116),
    @Range(value = "vapacHumidityControls", ordinal = 117),
    @Range(value = "dciCo", ordinal = 118),
    @Range(value = "yorkInternationalCorp", ordinal = 119),
    @Range(value = "legrand", ordinal = 120),
    @Range(value = "wabtecCorp", ordinal = 121),
    @Range(value = "reginAb", ordinal = 122),
    @Range(value = "watanabeElectricIndustryCo", ordinal = 123),
    @Range(value = "firecom", ordinal = 124),
    @Range(value = "australonEnterprises", ordinal = 125),
    @Range(value = "meikosha", ordinal = 126),
    @Range(value = "knorrBrakeCorp", ordinal = 127),
    @Range(value = "viessmannWerke", ordinal = 128),
    @Range(value = "siemensLandisUsa", ordinal = 129),
    @Range(value = "kongsbergAnalogic", ordinal = 130),
    @Range(value = "distechControls", ordinal = 131),
    @Range(value = "idecIzumiCorp", ordinal = 132),
    @Range(value = "toshibaLighting", ordinal = 133),
    @Range(value = "reserved", ordinal = 134),
    @Range(value = "daikinIndustries", ordinal = 135),
    @Range(value = "rockwellAutomation", ordinal = 136),
    @Range(value = "alstonTransport", ordinal = 137),
    @Range(value = "luminator", ordinal = 138),
    @Range(value = "hyundaiAutonetCo", ordinal = 139),
    @Range(value = "pdlIndustries", ordinal = 140),
    @Range(value = "plexusTechnology", ordinal = 141),
    @Range(value = "tridium", ordinal = 142),
    @Range(value = "ercoLeuchten", ordinal = 143),
    @Range(value = "cetelab", ordinal = 144),
    @Range(value = "ciac", ordinal = 145),
    @Range(value = "networkControls", ordinal = 146),
    @Range(value = "valvconCorp", ordinal = 147),
    @Range(value = "carel", ordinal = 148),
    @Range(value = "fieldServerTechnologies", ordinal = 149),
    @Range(value = "halenSmartCompany", ordinal = 150),
    @Range(value = "faiveley", ordinal = 151),
    @Range(value = "lonMarkTechnicalStaff", ordinal = 159),
    @Range(value = "axsysAutomation", ordinal = 160),
    @Range(value = "adicCo", ordinal = 161),
    @Range(value = "mitsubishiElectricCorp", ordinal = 162),
    @Range(value = "hermos", ordinal = 163),
    @Range(value = "kiebackandPeter", ordinal = 164),
    @Range(value = "terasakiElectricCo", ordinal = 165),
    @Range(value = "microlabSistemiSrl", ordinal = 166),
    @Range(value = "wattStopper", ordinal = 167),
    @Range(value = "aquametro", ordinal = 168),
    @Range(value = "infranetPartners", ordinal = 169),
    @Range(value = "stifabFarex", ordinal = 170),
    @Range(value = "agtatec", ordinal = 171),
    @Range(value = "surfNetworks", ordinal = 172),
    @Range(value = "kamstrup", ordinal = 173),
    @Range(value = "gentec", ordinal = 174),
    @Range(value = "cypressSemiconductor", ordinal = 175),
    @Range(value = "intellicomInnovation", ordinal = 176),
    @Range(value = "shikokuInstrumentation", ordinal = 177),
    @Range(value = "carrierCorporation", ordinal = 178),
    @Range(value = "shanghaiChangXiangComputer", ordinal = 179),
    @Range(value = "raypak", ordinal = 180),
    @Range(value = "nicoTechnology", ordinal = 181),
    @Range(value = "lochinvarCorporation", ordinal = 182),
    @Range(value = "programmedWaterTech", ordinal = 183),
    @Range(value = "kaifaTechnology", ordinal = 184),
    @Range(value = "capelon", ordinal = 185),
    @Range(value = "oas", ordinal = 186),
    @Range(value = "microTask", ordinal = 187),
    @Range(value = "pureChoice", ordinal = 188),
    @Range(value = "vaconPlc", ordinal = 189),
    @Range(value = "orionCI", ordinal = 190),
    @Range(value = "samsungElectronics", ordinal = 191),
    @Range(value = "drucegrove", ordinal = 192),
    @Range(value = "janitzaElectronic", ordinal = 193),
    @Range(value = "oilesCorporation", ordinal = 194),
    @Range(value = "osakiElectric", ordinal = 196),
    @Range(value = "viconicsElectronics", ordinal = 197),
    @Range(value = "fujiElectricSystems", ordinal = 198),
    @Range(value = "hubbellBuildingAutomation", ordinal = 199),
    @Range(value = "zanderFacilityEngineering", ordinal = 200),
    @Range(value = "solidyneCorp", ordinal = 201),
    @Range(value = "badgerMeter", ordinal = 202),
    @Range(value = "draegerSafety", ordinal = 203),
    @Range(value = "lgElectronics", ordinal = 204),
    @Range(value = "hitachi", ordinal = 205),
    @Range(value = "gorenje", ordinal = 206),
    @Range(value = "functionalDevices", ordinal = 207),
    @Range(value = "onicon", ordinal = 208),
    @Range(value = "electronicTheatreControls", ordinal = 209),
    @Range(value = "gulfSecurity", ordinal = 210),
    @Range(value = "controlTechniques", ordinal = 211),
    @Range(value = "phoenixControls", ordinal = 212),
    @Range(value = "vaComTechnologies", ordinal = 213),
    @Range(value = "buildingAutomation", ordinal = 214),
    @Range(value = "loytec", ordinal = 215),
    @Range(value = "spiSystems", ordinal = 216),
    @Range(value = "quantumAutomation", ordinal = 217),
    @Range(value = "lsIndustrialSystems", ordinal = 218),
    @Range(value = "nanjingLianhongAutomation", ordinal = 219),
    @Range(value = "sitecoControl", ordinal = 220),
    @Range(value = "voyantSolutions", ordinal = 221),
    @Range(value = "elkaElektronik", ordinal = 222),
    @Range(value = "mSystem", ordinal = 223),
    @Range(value = "schneiderElectric", ordinal = 224),
    @Range(value = "isde", ordinal = 225),
    @Range(value = "paragonControls", ordinal = 226),
    @Range(value = "schneiderElectricMerten", ordinal = 227),
    @Range(value = "picElectronics", ordinal = 228),
    @Range(value = "airTestTechnologies", ordinal = 229),
    @Range(value = "spega", ordinal = 230),
    @Range(value = "hunterDouglas", ordinal = 231),
    @Range(value = "lennoxIndustries", ordinal = 232),
    @Range(value = "citylone", ordinal = 233),
    @Range(value = "samsungSds", ordinal = 234),
    @Range(value = "gdMideaHeatingAndVentEquip", ordinal = 235),
    @Range(value = "vosslohSchwabeDeutschland", ordinal = 236),
    @Range(value = "verisIndustries", ordinal = 237),
    @Range(value = "blueEarthInc", ordinal = 238),
    @Range(value = "benHtsAg", ordinal = 239),
    @Range(value = "hoshizakiAmerica", ordinal = 240),
    @Range(value = "honeywellEmon", ordinal = 241),
    @Range(value = "simon", ordinal = 242),
    @Range(value = "sloanValve", ordinal = 243),
    @Range(value = "trustbridge", ordinal = 244),
    @Range(value = "mangelberger", ordinal = 245),
    @Range(value = "secyourit", ordinal = 246),
    @Range(value = "guangdongRongwen", ordinal = 247),
    @Range(value = "ecosian", ordinal = 248),
    @Range(value = "apanet", ordinal = 249),
    @Range(value = "lonMarkAfs1", ordinal = 10479),
    @Range(value = "honeywellFieldProgrammed", ordinal = 13108),
    @Range(value = "celsiusBeneluxBV", ordinal = 1048132)
  }
)
public final class BLonMfgId
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonMfgId(3957611241)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for unknown. */
  public static final int UNKNOWN = 0;
  /** Ordinal value for echelon. */
  public static final int ECHELON = 1;
  /** Ordinal value for motorola. */
  public static final int MOTOROLA = 2;
  /** Ordinal value for ibm. */
  public static final int IBM = 3;
  /** Ordinal value for sild. */
  public static final int SILD = 4;
  /** Ordinal value for helvar. */
  public static final int HELVAR = 5;
  /** Ordinal value for ahlstrom. */
  public static final int AHLSTROM = 6;
  /** Ordinal value for tmi. */
  public static final int TMI = 7;
  /** Ordinal value for danfoss. */
  public static final int DANFOSS = 8;
  /** Ordinal value for iec. */
  public static final int IEC = 9;
  /** Ordinal value for kaba. */
  public static final int KABA = 10;
  /** Ordinal value for ish. */
  public static final int ISH = 11;
  /** Ordinal value for honeywell. */
  public static final int HONEYWELL = 12;
  /** Ordinal value for leviton. */
  public static final int LEVITON = 13;
  /** Ordinal value for grayhill. */
  public static final int GRAYHILL = 14;
  /** Ordinal value for smartControls. */
  public static final int SMART_CONTROLS = 15;
  /** Ordinal value for andover. */
  public static final int ANDOVER = 16;
  /** Ordinal value for johnsonControls. */
  public static final int JOHNSON_CONTROLS = 17;
  /** Ordinal value for heatTimer. */
  public static final int HEAT_TIMER = 18;
  /** Ordinal value for taControl. */
  public static final int TA_CONTROL = 19;
  /** Ordinal value for groupSchneider. */
  public static final int GROUP_SCHNEIDER = 20;
  /** Ordinal value for weidmuller. */
  public static final int WEIDMULLER = 21;
  /** Ordinal value for siebe. */
  public static final int SIEBE = 22;
  /** Ordinal value for jGordonDesign. */
  public static final int J_GORDON_DESIGN = 23;
  /** Ordinal value for circon. */
  public static final int CIRCON = 24;
  /** Ordinal value for staefa. */
  public static final int STAEFA = 25;
  /** Ordinal value for homeAutomation. */
  public static final int HOME_AUTOMATION = 26;
  /** Ordinal value for comelta. */
  public static final int COMELTA = 27;
  /** Ordinal value for hycal. */
  public static final int HYCAL = 28;
  /** Ordinal value for caradonTrend. */
  public static final int CARADON_TREND = 29;
  /** Ordinal value for powerMeasurement. */
  public static final int POWER_MEASUREMENT = 30;
  /** Ordinal value for csi. */
  public static final int CSI = 31;
  /** Ordinal value for abb. */
  public static final int ABB = 32;
  /** Ordinal value for electronicSystems. */
  public static final int ELECTRONIC_SYSTEMS = 33;
  /** Ordinal value for continentalControl. */
  public static final int CONTINENTAL_CONTROL = 34;
  /** Ordinal value for msrTechnolgien. */
  public static final int MSR_TECHNOLGIEN = 35;
  /** Ordinal value for hubbell. */
  public static final int HUBBELL = 36;
  /** Ordinal value for mcquay. */
  public static final int MCQUAY = 37;
  /** Ordinal value for vaisala. */
  public static final int VAISALA = 38;
  /** Ordinal value for svm. */
  public static final int SVM = 39;
  /** Ordinal value for bircherGebaudeAg. */
  public static final int BIRCHER_GEBAUDE_AG = 40;
  /** Ordinal value for hachCompany. */
  public static final int HACH_COMPANY = 41;
  /** Ordinal value for theTraneCompany. */
  public static final int THE_TRANE_COMPANY = 42;
  /** Ordinal value for lintonSystems. */
  public static final int LINTON_SYSTEMS = 43;
  /** Ordinal value for osmonics. */
  public static final int OSMONICS = 44;
  /** Ordinal value for delmatic. */
  public static final int DELMATIC = 45;
  /** Ordinal value for elmLtd. */
  public static final int ELM_LTD = 46;
  /** Ordinal value for philipsLighting. */
  public static final int PHILIPS_LIGHTING = 47;
  /** Ordinal value for safeguard. */
  public static final int SAFEGUARD = 48;
  /** Ordinal value for seaboard. */
  public static final int SEABOARD = 49;
  /** Ordinal value for lighthouse. */
  public static final int LIGHTHOUSE = 50;
  /** Ordinal value for auslon. */
  public static final int AUSLON = 51;
  /** Ordinal value for kabaBenzing. */
  public static final int KABA_BENZING = 52;
  /** Ordinal value for rpRichards. */
  public static final int RP_RICHARDS = 53;
  /** Ordinal value for camilleBauer. */
  public static final int CAMILLE_BAUER = 54;
  /** Ordinal value for honeywell37. */
  public static final int HONEYWELL_37 = 55;
  /** Ordinal value for programmedWater. */
  public static final int PROGRAMMED_WATER = 56;
  /** Ordinal value for magnetek. */
  public static final int MAGNETEK = 57;
  /** Ordinal value for mentzelUndKrutmann. */
  public static final int MENTZEL_UND_KRUTMANN = 58;
  /** Ordinal value for zellwegerAnalytics. */
  public static final int ZELLWEGER_ANALYTICS = 59;
  /** Ordinal value for tlon. */
  public static final int TLON = 60;
  /** Ordinal value for enermet. */
  public static final int ENERMET = 61;
  /** Ordinal value for orasGroup. */
  public static final int ORAS_GROUP = 62;
  /** Ordinal value for mstAnalytics. */
  public static final int MST_ANALYTICS = 63;
  /** Ordinal value for dhElektronikAnlagenbau. */
  public static final int DH_ELEKTRONIK_ANLAGENBAU = 64;
  /** Ordinal value for alyaInternational. */
  public static final int ALYA_INTERNATIONAL = 65;
  /** Ordinal value for crystalControls. */
  public static final int CRYSTAL_CONTROLS = 66;
  /** Ordinal value for yokogawa. */
  public static final int YOKOGAWA = 67;
  /** Ordinal value for douglasPowerEquip. */
  public static final int DOUGLAS_POWER_EQUIP = 68;
  /** Ordinal value for develcoElectronik. */
  public static final int DEVELCO_ELECTRONIK = 69;
  /** Ordinal value for gebruderTroxGmb. */
  public static final int GEBRUDER_TROX_GMB = 70;
  /** Ordinal value for tsiInc. */
  public static final int TSI_INC = 71;
  /** Ordinal value for rikenKeikiCo. */
  public static final int RIKEN_KEIKI_CO = 72;
  /** Ordinal value for gesytecGmbh. */
  public static final int GESYTEC_GMBH = 73;
  /** Ordinal value for cumminsEngineCo. */
  public static final int CUMMINS_ENGINE_CO = 74;
  /** Ordinal value for landertMotorenAg. */
  public static final int LANDERT_MOTOREN_AG = 75;
  /** Ordinal value for toshibaCorp. */
  public static final int TOSHIBA_CORP = 76;
  /** Ordinal value for satronInstrumentsInc. */
  public static final int SATRON_INSTRUMENTS_INC = 77;
  /** Ordinal value for toshibaInfoSystems. */
  public static final int TOSHIBA_INFO_SYSTEMS = 78;
  /** Ordinal value for fujiElectricCo. */
  public static final int FUJI_ELECTRIC_CO = 80;
  /** Ordinal value for computerProcessControls. */
  public static final int COMPUTER_PROCESS_CONTROLS = 81;
  /** Ordinal value for somfy. */
  public static final int SOMFY = 82;
  /** Ordinal value for alcoControls. */
  public static final int ALCO_CONTROLS = 83;
  /** Ordinal value for keleAndAssociates. */
  public static final int KELE_AND_ASSOCIATES = 84;
  /** Ordinal value for grundfosElectronics. */
  public static final int GRUNDFOS_ELECTRONICS = 85;
  /** Ordinal value for zoneControlsKb. */
  public static final int ZONE_CONTROLS_KB = 86;
  /** Ordinal value for reko. */
  public static final int REKO = 87;
  /** Ordinal value for coactiveNetworksInc. */
  public static final int COACTIVE_NETWORKS_INC = 89;
  /** Ordinal value for nodusGmbh. */
  public static final int NODUS_GMBH = 90;
  /** Ordinal value for acutherm. */
  public static final int ACUTHERM = 91;
  /** Ordinal value for sontayOpenSystems. */
  public static final int SONTAY_OPEN_SYSTEMS = 92;
  /** Ordinal value for cAndKSystems. */
  public static final int C_AND_KSYSTEMS = 93;
  /** Ordinal value for sysmikGmbh. */
  public static final int SYSMIK_GMBH = 94;
  /** Ordinal value for yamatakeCorp. */
  public static final int YAMATAKE_CORP = 95;
  /** Ordinal value for ctiProducts. */
  public static final int CTI_PRODUCTS = 96;
  /** Ordinal value for belimoAutomation. */
  public static final int BELIMO_AUTOMATION = 97;
  /** Ordinal value for neurologicResearch. */
  public static final int NEUROLOGIC_RESEARCH = 98;
  /** Ordinal value for cnaEngineers. */
  public static final int CNA_ENGINEERS = 99;
  /** Ordinal value for energyControlsInternational. */
  public static final int ENERGY_CONTROLS_INTERNATIONAL = 100;
  /** Ordinal value for frSauterAg. */
  public static final int FR_SAUTER_AG = 101;
  /** Ordinal value for teldaElectronics. */
  public static final int TELDA_ELECTRONICS = 102;
  /** Ordinal value for comtecTechnologie. */
  public static final int COMTEC_TECHNOLOGIE = 103;
  /** Ordinal value for abbGebaudetechnikAg. */
  public static final int ABB_GEBAUDETECHNIK_AG = 104;
  /** Ordinal value for siemensStaefaControlsUsa. */
  public static final int SIEMENS_STAEFA_CONTROLS_USA = 105;
  /** Ordinal value for luxmateControlsGmbh. */
  public static final int LUXMATE_CONTROLS_GMBH = 106;
  /** Ordinal value for matrixControls. */
  public static final int MATRIX_CONTROLS = 107;
  /** Ordinal value for huppeFormSonnenschutzsysteme. */
  public static final int HUPPE_FORM_SONNENSCHUTZSYSTEME = 108;
  /** Ordinal value for samsungHeavyIndustries. */
  public static final int SAMSUNG_HEAVY_INDUSTRIES = 110;
  /** Ordinal value for kitzCorp. */
  public static final int KITZ_CORP = 111;
  /** Ordinal value for wago. */
  public static final int WAGO = 112;
  /** Ordinal value for matsushitaElectricWorks. */
  public static final int MATSUSHITA_ELECTRIC_WORKS = 113;
  /** Ordinal value for siemensLandisStaefaKorea. */
  public static final int SIEMENS_LANDIS_STAEFA_KOREA = 114;
  /** Ordinal value for samsonAg. */
  public static final int SAMSON_AG = 115;
  /** Ordinal value for enelIt. */
  public static final int ENEL_IT = 116;
  /** Ordinal value for vapacHumidityControls. */
  public static final int VAPAC_HUMIDITY_CONTROLS = 117;
  /** Ordinal value for dciCo. */
  public static final int DCI_CO = 118;
  /** Ordinal value for yorkInternationalCorp. */
  public static final int YORK_INTERNATIONAL_CORP = 119;
  /** Ordinal value for legrand. */
  public static final int LEGRAND = 120;
  /** Ordinal value for wabtecCorp. */
  public static final int WABTEC_CORP = 121;
  /** Ordinal value for reginAb. */
  public static final int REGIN_AB = 122;
  /** Ordinal value for watanabeElectricIndustryCo. */
  public static final int WATANABE_ELECTRIC_INDUSTRY_CO = 123;
  /** Ordinal value for firecom. */
  public static final int FIRECOM = 124;
  /** Ordinal value for australonEnterprises. */
  public static final int AUSTRALON_ENTERPRISES = 125;
  /** Ordinal value for meikosha. */
  public static final int MEIKOSHA = 126;
  /** Ordinal value for knorrBrakeCorp. */
  public static final int KNORR_BRAKE_CORP = 127;
  /** Ordinal value for viessmannWerke. */
  public static final int VIESSMANN_WERKE = 128;
  /** Ordinal value for siemensLandisUsa. */
  public static final int SIEMENS_LANDIS_USA = 129;
  /** Ordinal value for kongsbergAnalogic. */
  public static final int KONGSBERG_ANALOGIC = 130;
  /** Ordinal value for distechControls. */
  public static final int DISTECH_CONTROLS = 131;
  /** Ordinal value for idecIzumiCorp. */
  public static final int IDEC_IZUMI_CORP = 132;
  /** Ordinal value for toshibaLighting. */
  public static final int TOSHIBA_LIGHTING = 133;
  /** Ordinal value for reserved. */
  public static final int RESERVED = 134;
  /** Ordinal value for daikinIndustries. */
  public static final int DAIKIN_INDUSTRIES = 135;
  /** Ordinal value for rockwellAutomation. */
  public static final int ROCKWELL_AUTOMATION = 136;
  /** Ordinal value for alstonTransport. */
  public static final int ALSTON_TRANSPORT = 137;
  /** Ordinal value for luminator. */
  public static final int LUMINATOR = 138;
  /** Ordinal value for hyundaiAutonetCo. */
  public static final int HYUNDAI_AUTONET_CO = 139;
  /** Ordinal value for pdlIndustries. */
  public static final int PDL_INDUSTRIES = 140;
  /** Ordinal value for plexusTechnology. */
  public static final int PLEXUS_TECHNOLOGY = 141;
  /** Ordinal value for tridium. */
  public static final int TRIDIUM = 142;
  /** Ordinal value for ercoLeuchten. */
  public static final int ERCO_LEUCHTEN = 143;
  /** Ordinal value for cetelab. */
  public static final int CETELAB = 144;
  /** Ordinal value for ciac. */
  public static final int CIAC = 145;
  /** Ordinal value for networkControls. */
  public static final int NETWORK_CONTROLS = 146;
  /** Ordinal value for valvconCorp. */
  public static final int VALVCON_CORP = 147;
  /** Ordinal value for carel. */
  public static final int CAREL = 148;
  /** Ordinal value for fieldServerTechnologies. */
  public static final int FIELD_SERVER_TECHNOLOGIES = 149;
  /** Ordinal value for halenSmartCompany. */
  public static final int HALEN_SMART_COMPANY = 150;
  /** Ordinal value for faiveley. */
  public static final int FAIVELEY = 151;
  /** Ordinal value for lonMarkTechnicalStaff. */
  public static final int LON_MARK_TECHNICAL_STAFF = 159;
  /** Ordinal value for axsysAutomation. */
  public static final int AXSYS_AUTOMATION = 160;
  /** Ordinal value for adicCo. */
  public static final int ADIC_CO = 161;
  /** Ordinal value for mitsubishiElectricCorp. */
  public static final int MITSUBISHI_ELECTRIC_CORP = 162;
  /** Ordinal value for hermos. */
  public static final int HERMOS = 163;
  /** Ordinal value for kiebackandPeter. */
  public static final int KIEBACKAND_PETER = 164;
  /** Ordinal value for terasakiElectricCo. */
  public static final int TERASAKI_ELECTRIC_CO = 165;
  /** Ordinal value for microlabSistemiSrl. */
  public static final int MICROLAB_SISTEMI_SRL = 166;
  /** Ordinal value for wattStopper. */
  public static final int WATT_STOPPER = 167;
  /** Ordinal value for aquametro. */
  public static final int AQUAMETRO = 168;
  /** Ordinal value for infranetPartners. */
  public static final int INFRANET_PARTNERS = 169;
  /** Ordinal value for stifabFarex. */
  public static final int STIFAB_FAREX = 170;
  /** Ordinal value for agtatec. */
  public static final int AGTATEC = 171;
  /** Ordinal value for surfNetworks. */
  public static final int SURF_NETWORKS = 172;
  /** Ordinal value for kamstrup. */
  public static final int KAMSTRUP = 173;
  /** Ordinal value for gentec. */
  public static final int GENTEC = 174;
  /** Ordinal value for cypressSemiconductor. */
  public static final int CYPRESS_SEMICONDUCTOR = 175;
  /** Ordinal value for intellicomInnovation. */
  public static final int INTELLICOM_INNOVATION = 176;
  /** Ordinal value for shikokuInstrumentation. */
  public static final int SHIKOKU_INSTRUMENTATION = 177;
  /** Ordinal value for carrierCorporation. */
  public static final int CARRIER_CORPORATION = 178;
  /** Ordinal value for shanghaiChangXiangComputer. */
  public static final int SHANGHAI_CHANG_XIANG_COMPUTER = 179;
  /** Ordinal value for raypak. */
  public static final int RAYPAK = 180;
  /** Ordinal value for nicoTechnology. */
  public static final int NICO_TECHNOLOGY = 181;
  /** Ordinal value for lochinvarCorporation. */
  public static final int LOCHINVAR_CORPORATION = 182;
  /** Ordinal value for programmedWaterTech. */
  public static final int PROGRAMMED_WATER_TECH = 183;
  /** Ordinal value for kaifaTechnology. */
  public static final int KAIFA_TECHNOLOGY = 184;
  /** Ordinal value for capelon. */
  public static final int CAPELON = 185;
  /** Ordinal value for oas. */
  public static final int OAS = 186;
  /** Ordinal value for microTask. */
  public static final int MICRO_TASK = 187;
  /** Ordinal value for pureChoice. */
  public static final int PURE_CHOICE = 188;
  /** Ordinal value for vaconPlc. */
  public static final int VACON_PLC = 189;
  /** Ordinal value for orionCI. */
  public static final int ORION_CI = 190;
  /** Ordinal value for samsungElectronics. */
  public static final int SAMSUNG_ELECTRONICS = 191;
  /** Ordinal value for drucegrove. */
  public static final int DRUCEGROVE = 192;
  /** Ordinal value for janitzaElectronic. */
  public static final int JANITZA_ELECTRONIC = 193;
  /** Ordinal value for oilesCorporation. */
  public static final int OILES_CORPORATION = 194;
  /** Ordinal value for osakiElectric. */
  public static final int OSAKI_ELECTRIC = 196;
  /** Ordinal value for viconicsElectronics. */
  public static final int VICONICS_ELECTRONICS = 197;
  /** Ordinal value for fujiElectricSystems. */
  public static final int FUJI_ELECTRIC_SYSTEMS = 198;
  /** Ordinal value for hubbellBuildingAutomation. */
  public static final int HUBBELL_BUILDING_AUTOMATION = 199;
  /** Ordinal value for zanderFacilityEngineering. */
  public static final int ZANDER_FACILITY_ENGINEERING = 200;
  /** Ordinal value for solidyneCorp. */
  public static final int SOLIDYNE_CORP = 201;
  /** Ordinal value for badgerMeter. */
  public static final int BADGER_METER = 202;
  /** Ordinal value for draegerSafety. */
  public static final int DRAEGER_SAFETY = 203;
  /** Ordinal value for lgElectronics. */
  public static final int LG_ELECTRONICS = 204;
  /** Ordinal value for hitachi. */
  public static final int HITACHI = 205;
  /** Ordinal value for gorenje. */
  public static final int GORENJE = 206;
  /** Ordinal value for functionalDevices. */
  public static final int FUNCTIONAL_DEVICES = 207;
  /** Ordinal value for onicon. */
  public static final int ONICON = 208;
  /** Ordinal value for electronicTheatreControls. */
  public static final int ELECTRONIC_THEATRE_CONTROLS = 209;
  /** Ordinal value for gulfSecurity. */
  public static final int GULF_SECURITY = 210;
  /** Ordinal value for controlTechniques. */
  public static final int CONTROL_TECHNIQUES = 211;
  /** Ordinal value for phoenixControls. */
  public static final int PHOENIX_CONTROLS = 212;
  /** Ordinal value for vaComTechnologies. */
  public static final int VA_COM_TECHNOLOGIES = 213;
  /** Ordinal value for buildingAutomation. */
  public static final int BUILDING_AUTOMATION = 214;
  /** Ordinal value for loytec. */
  public static final int LOYTEC = 215;
  /** Ordinal value for spiSystems. */
  public static final int SPI_SYSTEMS = 216;
  /** Ordinal value for quantumAutomation. */
  public static final int QUANTUM_AUTOMATION = 217;
  /** Ordinal value for lsIndustrialSystems. */
  public static final int LS_INDUSTRIAL_SYSTEMS = 218;
  /** Ordinal value for nanjingLianhongAutomation. */
  public static final int NANJING_LIANHONG_AUTOMATION = 219;
  /** Ordinal value for sitecoControl. */
  public static final int SITECO_CONTROL = 220;
  /** Ordinal value for voyantSolutions. */
  public static final int VOYANT_SOLUTIONS = 221;
  /** Ordinal value for elkaElektronik. */
  public static final int ELKA_ELEKTRONIK = 222;
  /** Ordinal value for mSystem. */
  public static final int M_SYSTEM = 223;
  /** Ordinal value for schneiderElectric. */
  public static final int SCHNEIDER_ELECTRIC = 224;
  /** Ordinal value for isde. */
  public static final int ISDE = 225;
  /** Ordinal value for paragonControls. */
  public static final int PARAGON_CONTROLS = 226;
  /** Ordinal value for schneiderElectricMerten. */
  public static final int SCHNEIDER_ELECTRIC_MERTEN = 227;
  /** Ordinal value for picElectronics. */
  public static final int PIC_ELECTRONICS = 228;
  /** Ordinal value for airTestTechnologies. */
  public static final int AIR_TEST_TECHNOLOGIES = 229;
  /** Ordinal value for spega. */
  public static final int SPEGA = 230;
  /** Ordinal value for hunterDouglas. */
  public static final int HUNTER_DOUGLAS = 231;
  /** Ordinal value for lennoxIndustries. */
  public static final int LENNOX_INDUSTRIES = 232;
  /** Ordinal value for citylone. */
  public static final int CITYLONE = 233;
  /** Ordinal value for samsungSds. */
  public static final int SAMSUNG_SDS = 234;
  /** Ordinal value for gdMideaHeatingAndVentEquip. */
  public static final int GD_MIDEA_HEATING_AND_VENT_EQUIP = 235;
  /** Ordinal value for vosslohSchwabeDeutschland. */
  public static final int VOSSLOH_SCHWABE_DEUTSCHLAND = 236;
  /** Ordinal value for verisIndustries. */
  public static final int VERIS_INDUSTRIES = 237;
  /** Ordinal value for blueEarthInc. */
  public static final int BLUE_EARTH_INC = 238;
  /** Ordinal value for benHtsAg. */
  public static final int BEN_HTS_AG = 239;
  /** Ordinal value for hoshizakiAmerica. */
  public static final int HOSHIZAKI_AMERICA = 240;
  /** Ordinal value for honeywellEmon. */
  public static final int HONEYWELL_EMON = 241;
  /** Ordinal value for simon. */
  public static final int SIMON = 242;
  /** Ordinal value for sloanValve. */
  public static final int SLOAN_VALVE = 243;
  /** Ordinal value for trustbridge. */
  public static final int TRUSTBRIDGE = 244;
  /** Ordinal value for mangelberger. */
  public static final int MANGELBERGER = 245;
  /** Ordinal value for secyourit. */
  public static final int SECYOURIT = 246;
  /** Ordinal value for guangdongRongwen. */
  public static final int GUANGDONG_RONGWEN = 247;
  /** Ordinal value for ecosian. */
  public static final int ECOSIAN = 248;
  /** Ordinal value for apanet. */
  public static final int APANET = 249;
  /** Ordinal value for lonMarkAfs1. */
  public static final int LON_MARK_AFS_1 = 10479;
  /** Ordinal value for honeywellFieldProgrammed. */
  public static final int HONEYWELL_FIELD_PROGRAMMED = 13108;
  /** Ordinal value for celsiusBeneluxBV. */
  public static final int CELSIUS_BENELUX_BV = 1048132;

  /** BLonMfgId constant for unknown. */
  public static final BLonMfgId unknown = new BLonMfgId(UNKNOWN);
  /** BLonMfgId constant for echelon. */
  public static final BLonMfgId echelon = new BLonMfgId(ECHELON);
  /** BLonMfgId constant for motorola. */
  public static final BLonMfgId motorola = new BLonMfgId(MOTOROLA);
  /** BLonMfgId constant for ibm. */
  public static final BLonMfgId ibm = new BLonMfgId(IBM);
  /** BLonMfgId constant for sild. */
  public static final BLonMfgId sild = new BLonMfgId(SILD);
  /** BLonMfgId constant for helvar. */
  public static final BLonMfgId helvar = new BLonMfgId(HELVAR);
  /** BLonMfgId constant for ahlstrom. */
  public static final BLonMfgId ahlstrom = new BLonMfgId(AHLSTROM);
  /** BLonMfgId constant for tmi. */
  public static final BLonMfgId tmi = new BLonMfgId(TMI);
  /** BLonMfgId constant for danfoss. */
  public static final BLonMfgId danfoss = new BLonMfgId(DANFOSS);
  /** BLonMfgId constant for iec. */
  public static final BLonMfgId iec = new BLonMfgId(IEC);
  /** BLonMfgId constant for kaba. */
  public static final BLonMfgId kaba = new BLonMfgId(KABA);
  /** BLonMfgId constant for ish. */
  public static final BLonMfgId ish = new BLonMfgId(ISH);
  /** BLonMfgId constant for honeywell. */
  public static final BLonMfgId honeywell = new BLonMfgId(HONEYWELL);
  /** BLonMfgId constant for leviton. */
  public static final BLonMfgId leviton = new BLonMfgId(LEVITON);
  /** BLonMfgId constant for grayhill. */
  public static final BLonMfgId grayhill = new BLonMfgId(GRAYHILL);
  /** BLonMfgId constant for smartControls. */
  public static final BLonMfgId smartControls = new BLonMfgId(SMART_CONTROLS);
  /** BLonMfgId constant for andover. */
  public static final BLonMfgId andover = new BLonMfgId(ANDOVER);
  /** BLonMfgId constant for johnsonControls. */
  public static final BLonMfgId johnsonControls = new BLonMfgId(JOHNSON_CONTROLS);
  /** BLonMfgId constant for heatTimer. */
  public static final BLonMfgId heatTimer = new BLonMfgId(HEAT_TIMER);
  /** BLonMfgId constant for taControl. */
  public static final BLonMfgId taControl = new BLonMfgId(TA_CONTROL);
  /** BLonMfgId constant for groupSchneider. */
  public static final BLonMfgId groupSchneider = new BLonMfgId(GROUP_SCHNEIDER);
  /** BLonMfgId constant for weidmuller. */
  public static final BLonMfgId weidmuller = new BLonMfgId(WEIDMULLER);
  /** BLonMfgId constant for siebe. */
  public static final BLonMfgId siebe = new BLonMfgId(SIEBE);
  /** BLonMfgId constant for jGordonDesign. */
  public static final BLonMfgId jGordonDesign = new BLonMfgId(J_GORDON_DESIGN);
  /** BLonMfgId constant for circon. */
  public static final BLonMfgId circon = new BLonMfgId(CIRCON);
  /** BLonMfgId constant for staefa. */
  public static final BLonMfgId staefa = new BLonMfgId(STAEFA);
  /** BLonMfgId constant for homeAutomation. */
  public static final BLonMfgId homeAutomation = new BLonMfgId(HOME_AUTOMATION);
  /** BLonMfgId constant for comelta. */
  public static final BLonMfgId comelta = new BLonMfgId(COMELTA);
  /** BLonMfgId constant for hycal. */
  public static final BLonMfgId hycal = new BLonMfgId(HYCAL);
  /** BLonMfgId constant for caradonTrend. */
  public static final BLonMfgId caradonTrend = new BLonMfgId(CARADON_TREND);
  /** BLonMfgId constant for powerMeasurement. */
  public static final BLonMfgId powerMeasurement = new BLonMfgId(POWER_MEASUREMENT);
  /** BLonMfgId constant for csi. */
  public static final BLonMfgId csi = new BLonMfgId(CSI);
  /** BLonMfgId constant for abb. */
  public static final BLonMfgId abb = new BLonMfgId(ABB);
  /** BLonMfgId constant for electronicSystems. */
  public static final BLonMfgId electronicSystems = new BLonMfgId(ELECTRONIC_SYSTEMS);
  /** BLonMfgId constant for continentalControl. */
  public static final BLonMfgId continentalControl = new BLonMfgId(CONTINENTAL_CONTROL);
  /** BLonMfgId constant for msrTechnolgien. */
  public static final BLonMfgId msrTechnolgien = new BLonMfgId(MSR_TECHNOLGIEN);
  /** BLonMfgId constant for hubbell. */
  public static final BLonMfgId hubbell = new BLonMfgId(HUBBELL);
  /** BLonMfgId constant for mcquay. */
  public static final BLonMfgId mcquay = new BLonMfgId(MCQUAY);
  /** BLonMfgId constant for vaisala. */
  public static final BLonMfgId vaisala = new BLonMfgId(VAISALA);
  /** BLonMfgId constant for svm. */
  public static final BLonMfgId svm = new BLonMfgId(SVM);
  /** BLonMfgId constant for bircherGebaudeAg. */
  public static final BLonMfgId bircherGebaudeAg = new BLonMfgId(BIRCHER_GEBAUDE_AG);
  /** BLonMfgId constant for hachCompany. */
  public static final BLonMfgId hachCompany = new BLonMfgId(HACH_COMPANY);
  /** BLonMfgId constant for theTraneCompany. */
  public static final BLonMfgId theTraneCompany = new BLonMfgId(THE_TRANE_COMPANY);
  /** BLonMfgId constant for lintonSystems. */
  public static final BLonMfgId lintonSystems = new BLonMfgId(LINTON_SYSTEMS);
  /** BLonMfgId constant for osmonics. */
  public static final BLonMfgId osmonics = new BLonMfgId(OSMONICS);
  /** BLonMfgId constant for delmatic. */
  public static final BLonMfgId delmatic = new BLonMfgId(DELMATIC);
  /** BLonMfgId constant for elmLtd. */
  public static final BLonMfgId elmLtd = new BLonMfgId(ELM_LTD);
  /** BLonMfgId constant for philipsLighting. */
  public static final BLonMfgId philipsLighting = new BLonMfgId(PHILIPS_LIGHTING);
  /** BLonMfgId constant for safeguard. */
  public static final BLonMfgId safeguard = new BLonMfgId(SAFEGUARD);
  /** BLonMfgId constant for seaboard. */
  public static final BLonMfgId seaboard = new BLonMfgId(SEABOARD);
  /** BLonMfgId constant for lighthouse. */
  public static final BLonMfgId lighthouse = new BLonMfgId(LIGHTHOUSE);
  /** BLonMfgId constant for auslon. */
  public static final BLonMfgId auslon = new BLonMfgId(AUSLON);
  /** BLonMfgId constant for kabaBenzing. */
  public static final BLonMfgId kabaBenzing = new BLonMfgId(KABA_BENZING);
  /** BLonMfgId constant for rpRichards. */
  public static final BLonMfgId rpRichards = new BLonMfgId(RP_RICHARDS);
  /** BLonMfgId constant for camilleBauer. */
  public static final BLonMfgId camilleBauer = new BLonMfgId(CAMILLE_BAUER);
  /** BLonMfgId constant for honeywell37. */
  public static final BLonMfgId honeywell37 = new BLonMfgId(HONEYWELL_37);
  /** BLonMfgId constant for programmedWater. */
  public static final BLonMfgId programmedWater = new BLonMfgId(PROGRAMMED_WATER);
  /** BLonMfgId constant for magnetek. */
  public static final BLonMfgId magnetek = new BLonMfgId(MAGNETEK);
  /** BLonMfgId constant for mentzelUndKrutmann. */
  public static final BLonMfgId mentzelUndKrutmann = new BLonMfgId(MENTZEL_UND_KRUTMANN);
  /** BLonMfgId constant for zellwegerAnalytics. */
  public static final BLonMfgId zellwegerAnalytics = new BLonMfgId(ZELLWEGER_ANALYTICS);
  /** BLonMfgId constant for tlon. */
  public static final BLonMfgId tlon = new BLonMfgId(TLON);
  /** BLonMfgId constant for enermet. */
  public static final BLonMfgId enermet = new BLonMfgId(ENERMET);
  /** BLonMfgId constant for orasGroup. */
  public static final BLonMfgId orasGroup = new BLonMfgId(ORAS_GROUP);
  /** BLonMfgId constant for mstAnalytics. */
  public static final BLonMfgId mstAnalytics = new BLonMfgId(MST_ANALYTICS);
  /** BLonMfgId constant for dhElektronikAnlagenbau. */
  public static final BLonMfgId dhElektronikAnlagenbau = new BLonMfgId(DH_ELEKTRONIK_ANLAGENBAU);
  /** BLonMfgId constant for alyaInternational. */
  public static final BLonMfgId alyaInternational = new BLonMfgId(ALYA_INTERNATIONAL);
  /** BLonMfgId constant for crystalControls. */
  public static final BLonMfgId crystalControls = new BLonMfgId(CRYSTAL_CONTROLS);
  /** BLonMfgId constant for yokogawa. */
  public static final BLonMfgId yokogawa = new BLonMfgId(YOKOGAWA);
  /** BLonMfgId constant for douglasPowerEquip. */
  public static final BLonMfgId douglasPowerEquip = new BLonMfgId(DOUGLAS_POWER_EQUIP);
  /** BLonMfgId constant for develcoElectronik. */
  public static final BLonMfgId develcoElectronik = new BLonMfgId(DEVELCO_ELECTRONIK);
  /** BLonMfgId constant for gebruderTroxGmb. */
  public static final BLonMfgId gebruderTroxGmb = new BLonMfgId(GEBRUDER_TROX_GMB);
  /** BLonMfgId constant for tsiInc. */
  public static final BLonMfgId tsiInc = new BLonMfgId(TSI_INC);
  /** BLonMfgId constant for rikenKeikiCo. */
  public static final BLonMfgId rikenKeikiCo = new BLonMfgId(RIKEN_KEIKI_CO);
  /** BLonMfgId constant for gesytecGmbh. */
  public static final BLonMfgId gesytecGmbh = new BLonMfgId(GESYTEC_GMBH);
  /** BLonMfgId constant for cumminsEngineCo. */
  public static final BLonMfgId cumminsEngineCo = new BLonMfgId(CUMMINS_ENGINE_CO);
  /** BLonMfgId constant for landertMotorenAg. */
  public static final BLonMfgId landertMotorenAg = new BLonMfgId(LANDERT_MOTOREN_AG);
  /** BLonMfgId constant for toshibaCorp. */
  public static final BLonMfgId toshibaCorp = new BLonMfgId(TOSHIBA_CORP);
  /** BLonMfgId constant for satronInstrumentsInc. */
  public static final BLonMfgId satronInstrumentsInc = new BLonMfgId(SATRON_INSTRUMENTS_INC);
  /** BLonMfgId constant for toshibaInfoSystems. */
  public static final BLonMfgId toshibaInfoSystems = new BLonMfgId(TOSHIBA_INFO_SYSTEMS);
  /** BLonMfgId constant for fujiElectricCo. */
  public static final BLonMfgId fujiElectricCo = new BLonMfgId(FUJI_ELECTRIC_CO);
  /** BLonMfgId constant for computerProcessControls. */
  public static final BLonMfgId computerProcessControls = new BLonMfgId(COMPUTER_PROCESS_CONTROLS);
  /** BLonMfgId constant for somfy. */
  public static final BLonMfgId somfy = new BLonMfgId(SOMFY);
  /** BLonMfgId constant for alcoControls. */
  public static final BLonMfgId alcoControls = new BLonMfgId(ALCO_CONTROLS);
  /** BLonMfgId constant for keleAndAssociates. */
  public static final BLonMfgId keleAndAssociates = new BLonMfgId(KELE_AND_ASSOCIATES);
  /** BLonMfgId constant for grundfosElectronics. */
  public static final BLonMfgId grundfosElectronics = new BLonMfgId(GRUNDFOS_ELECTRONICS);
  /** BLonMfgId constant for zoneControlsKb. */
  public static final BLonMfgId zoneControlsKb = new BLonMfgId(ZONE_CONTROLS_KB);
  /** BLonMfgId constant for reko. */
  public static final BLonMfgId reko = new BLonMfgId(REKO);
  /** BLonMfgId constant for coactiveNetworksInc. */
  public static final BLonMfgId coactiveNetworksInc = new BLonMfgId(COACTIVE_NETWORKS_INC);
  /** BLonMfgId constant for nodusGmbh. */
  public static final BLonMfgId nodusGmbh = new BLonMfgId(NODUS_GMBH);
  /** BLonMfgId constant for acutherm. */
  public static final BLonMfgId acutherm = new BLonMfgId(ACUTHERM);
  /** BLonMfgId constant for sontayOpenSystems. */
  public static final BLonMfgId sontayOpenSystems = new BLonMfgId(SONTAY_OPEN_SYSTEMS);
  /** BLonMfgId constant for cAndKSystems. */
  public static final BLonMfgId cAndKSystems = new BLonMfgId(C_AND_KSYSTEMS);
  /** BLonMfgId constant for sysmikGmbh. */
  public static final BLonMfgId sysmikGmbh = new BLonMfgId(SYSMIK_GMBH);
  /** BLonMfgId constant for yamatakeCorp. */
  public static final BLonMfgId yamatakeCorp = new BLonMfgId(YAMATAKE_CORP);
  /** BLonMfgId constant for ctiProducts. */
  public static final BLonMfgId ctiProducts = new BLonMfgId(CTI_PRODUCTS);
  /** BLonMfgId constant for belimoAutomation. */
  public static final BLonMfgId belimoAutomation = new BLonMfgId(BELIMO_AUTOMATION);
  /** BLonMfgId constant for neurologicResearch. */
  public static final BLonMfgId neurologicResearch = new BLonMfgId(NEUROLOGIC_RESEARCH);
  /** BLonMfgId constant for cnaEngineers. */
  public static final BLonMfgId cnaEngineers = new BLonMfgId(CNA_ENGINEERS);
  /** BLonMfgId constant for energyControlsInternational. */
  public static final BLonMfgId energyControlsInternational = new BLonMfgId(ENERGY_CONTROLS_INTERNATIONAL);
  /** BLonMfgId constant for frSauterAg. */
  public static final BLonMfgId frSauterAg = new BLonMfgId(FR_SAUTER_AG);
  /** BLonMfgId constant for teldaElectronics. */
  public static final BLonMfgId teldaElectronics = new BLonMfgId(TELDA_ELECTRONICS);
  /** BLonMfgId constant for comtecTechnologie. */
  public static final BLonMfgId comtecTechnologie = new BLonMfgId(COMTEC_TECHNOLOGIE);
  /** BLonMfgId constant for abbGebaudetechnikAg. */
  public static final BLonMfgId abbGebaudetechnikAg = new BLonMfgId(ABB_GEBAUDETECHNIK_AG);
  /** BLonMfgId constant for siemensStaefaControlsUsa. */
  public static final BLonMfgId siemensStaefaControlsUsa = new BLonMfgId(SIEMENS_STAEFA_CONTROLS_USA);
  /** BLonMfgId constant for luxmateControlsGmbh. */
  public static final BLonMfgId luxmateControlsGmbh = new BLonMfgId(LUXMATE_CONTROLS_GMBH);
  /** BLonMfgId constant for matrixControls. */
  public static final BLonMfgId matrixControls = new BLonMfgId(MATRIX_CONTROLS);
  /** BLonMfgId constant for huppeFormSonnenschutzsysteme. */
  public static final BLonMfgId huppeFormSonnenschutzsysteme = new BLonMfgId(HUPPE_FORM_SONNENSCHUTZSYSTEME);
  /** BLonMfgId constant for samsungHeavyIndustries. */
  public static final BLonMfgId samsungHeavyIndustries = new BLonMfgId(SAMSUNG_HEAVY_INDUSTRIES);
  /** BLonMfgId constant for kitzCorp. */
  public static final BLonMfgId kitzCorp = new BLonMfgId(KITZ_CORP);
  /** BLonMfgId constant for wago. */
  public static final BLonMfgId wago = new BLonMfgId(WAGO);
  /** BLonMfgId constant for matsushitaElectricWorks. */
  public static final BLonMfgId matsushitaElectricWorks = new BLonMfgId(MATSUSHITA_ELECTRIC_WORKS);
  /** BLonMfgId constant for siemensLandisStaefaKorea. */
  public static final BLonMfgId siemensLandisStaefaKorea = new BLonMfgId(SIEMENS_LANDIS_STAEFA_KOREA);
  /** BLonMfgId constant for samsonAg. */
  public static final BLonMfgId samsonAg = new BLonMfgId(SAMSON_AG);
  /** BLonMfgId constant for enelIt. */
  public static final BLonMfgId enelIt = new BLonMfgId(ENEL_IT);
  /** BLonMfgId constant for vapacHumidityControls. */
  public static final BLonMfgId vapacHumidityControls = new BLonMfgId(VAPAC_HUMIDITY_CONTROLS);
  /** BLonMfgId constant for dciCo. */
  public static final BLonMfgId dciCo = new BLonMfgId(DCI_CO);
  /** BLonMfgId constant for yorkInternationalCorp. */
  public static final BLonMfgId yorkInternationalCorp = new BLonMfgId(YORK_INTERNATIONAL_CORP);
  /** BLonMfgId constant for legrand. */
  public static final BLonMfgId legrand = new BLonMfgId(LEGRAND);
  /** BLonMfgId constant for wabtecCorp. */
  public static final BLonMfgId wabtecCorp = new BLonMfgId(WABTEC_CORP);
  /** BLonMfgId constant for reginAb. */
  public static final BLonMfgId reginAb = new BLonMfgId(REGIN_AB);
  /** BLonMfgId constant for watanabeElectricIndustryCo. */
  public static final BLonMfgId watanabeElectricIndustryCo = new BLonMfgId(WATANABE_ELECTRIC_INDUSTRY_CO);
  /** BLonMfgId constant for firecom. */
  public static final BLonMfgId firecom = new BLonMfgId(FIRECOM);
  /** BLonMfgId constant for australonEnterprises. */
  public static final BLonMfgId australonEnterprises = new BLonMfgId(AUSTRALON_ENTERPRISES);
  /** BLonMfgId constant for meikosha. */
  public static final BLonMfgId meikosha = new BLonMfgId(MEIKOSHA);
  /** BLonMfgId constant for knorrBrakeCorp. */
  public static final BLonMfgId knorrBrakeCorp = new BLonMfgId(KNORR_BRAKE_CORP);
  /** BLonMfgId constant for viessmannWerke. */
  public static final BLonMfgId viessmannWerke = new BLonMfgId(VIESSMANN_WERKE);
  /** BLonMfgId constant for siemensLandisUsa. */
  public static final BLonMfgId siemensLandisUsa = new BLonMfgId(SIEMENS_LANDIS_USA);
  /** BLonMfgId constant for kongsbergAnalogic. */
  public static final BLonMfgId kongsbergAnalogic = new BLonMfgId(KONGSBERG_ANALOGIC);
  /** BLonMfgId constant for distechControls. */
  public static final BLonMfgId distechControls = new BLonMfgId(DISTECH_CONTROLS);
  /** BLonMfgId constant for idecIzumiCorp. */
  public static final BLonMfgId idecIzumiCorp = new BLonMfgId(IDEC_IZUMI_CORP);
  /** BLonMfgId constant for toshibaLighting. */
  public static final BLonMfgId toshibaLighting = new BLonMfgId(TOSHIBA_LIGHTING);
  /** BLonMfgId constant for reserved. */
  public static final BLonMfgId reserved = new BLonMfgId(RESERVED);
  /** BLonMfgId constant for daikinIndustries. */
  public static final BLonMfgId daikinIndustries = new BLonMfgId(DAIKIN_INDUSTRIES);
  /** BLonMfgId constant for rockwellAutomation. */
  public static final BLonMfgId rockwellAutomation = new BLonMfgId(ROCKWELL_AUTOMATION);
  /** BLonMfgId constant for alstonTransport. */
  public static final BLonMfgId alstonTransport = new BLonMfgId(ALSTON_TRANSPORT);
  /** BLonMfgId constant for luminator. */
  public static final BLonMfgId luminator = new BLonMfgId(LUMINATOR);
  /** BLonMfgId constant for hyundaiAutonetCo. */
  public static final BLonMfgId hyundaiAutonetCo = new BLonMfgId(HYUNDAI_AUTONET_CO);
  /** BLonMfgId constant for pdlIndustries. */
  public static final BLonMfgId pdlIndustries = new BLonMfgId(PDL_INDUSTRIES);
  /** BLonMfgId constant for plexusTechnology. */
  public static final BLonMfgId plexusTechnology = new BLonMfgId(PLEXUS_TECHNOLOGY);
  /** BLonMfgId constant for tridium. */
  public static final BLonMfgId tridium = new BLonMfgId(TRIDIUM);
  /** BLonMfgId constant for ercoLeuchten. */
  public static final BLonMfgId ercoLeuchten = new BLonMfgId(ERCO_LEUCHTEN);
  /** BLonMfgId constant for cetelab. */
  public static final BLonMfgId cetelab = new BLonMfgId(CETELAB);
  /** BLonMfgId constant for ciac. */
  public static final BLonMfgId ciac = new BLonMfgId(CIAC);
  /** BLonMfgId constant for networkControls. */
  public static final BLonMfgId networkControls = new BLonMfgId(NETWORK_CONTROLS);
  /** BLonMfgId constant for valvconCorp. */
  public static final BLonMfgId valvconCorp = new BLonMfgId(VALVCON_CORP);
  /** BLonMfgId constant for carel. */
  public static final BLonMfgId carel = new BLonMfgId(CAREL);
  /** BLonMfgId constant for fieldServerTechnologies. */
  public static final BLonMfgId fieldServerTechnologies = new BLonMfgId(FIELD_SERVER_TECHNOLOGIES);
  /** BLonMfgId constant for halenSmartCompany. */
  public static final BLonMfgId halenSmartCompany = new BLonMfgId(HALEN_SMART_COMPANY);
  /** BLonMfgId constant for faiveley. */
  public static final BLonMfgId faiveley = new BLonMfgId(FAIVELEY);
  /** BLonMfgId constant for lonMarkTechnicalStaff. */
  public static final BLonMfgId lonMarkTechnicalStaff = new BLonMfgId(LON_MARK_TECHNICAL_STAFF);
  /** BLonMfgId constant for axsysAutomation. */
  public static final BLonMfgId axsysAutomation = new BLonMfgId(AXSYS_AUTOMATION);
  /** BLonMfgId constant for adicCo. */
  public static final BLonMfgId adicCo = new BLonMfgId(ADIC_CO);
  /** BLonMfgId constant for mitsubishiElectricCorp. */
  public static final BLonMfgId mitsubishiElectricCorp = new BLonMfgId(MITSUBISHI_ELECTRIC_CORP);
  /** BLonMfgId constant for hermos. */
  public static final BLonMfgId hermos = new BLonMfgId(HERMOS);
  /** BLonMfgId constant for kiebackandPeter. */
  public static final BLonMfgId kiebackandPeter = new BLonMfgId(KIEBACKAND_PETER);
  /** BLonMfgId constant for terasakiElectricCo. */
  public static final BLonMfgId terasakiElectricCo = new BLonMfgId(TERASAKI_ELECTRIC_CO);
  /** BLonMfgId constant for microlabSistemiSrl. */
  public static final BLonMfgId microlabSistemiSrl = new BLonMfgId(MICROLAB_SISTEMI_SRL);
  /** BLonMfgId constant for wattStopper. */
  public static final BLonMfgId wattStopper = new BLonMfgId(WATT_STOPPER);
  /** BLonMfgId constant for aquametro. */
  public static final BLonMfgId aquametro = new BLonMfgId(AQUAMETRO);
  /** BLonMfgId constant for infranetPartners. */
  public static final BLonMfgId infranetPartners = new BLonMfgId(INFRANET_PARTNERS);
  /** BLonMfgId constant for stifabFarex. */
  public static final BLonMfgId stifabFarex = new BLonMfgId(STIFAB_FAREX);
  /** BLonMfgId constant for agtatec. */
  public static final BLonMfgId agtatec = new BLonMfgId(AGTATEC);
  /** BLonMfgId constant for surfNetworks. */
  public static final BLonMfgId surfNetworks = new BLonMfgId(SURF_NETWORKS);
  /** BLonMfgId constant for kamstrup. */
  public static final BLonMfgId kamstrup = new BLonMfgId(KAMSTRUP);
  /** BLonMfgId constant for gentec. */
  public static final BLonMfgId gentec = new BLonMfgId(GENTEC);
  /** BLonMfgId constant for cypressSemiconductor. */
  public static final BLonMfgId cypressSemiconductor = new BLonMfgId(CYPRESS_SEMICONDUCTOR);
  /** BLonMfgId constant for intellicomInnovation. */
  public static final BLonMfgId intellicomInnovation = new BLonMfgId(INTELLICOM_INNOVATION);
  /** BLonMfgId constant for shikokuInstrumentation. */
  public static final BLonMfgId shikokuInstrumentation = new BLonMfgId(SHIKOKU_INSTRUMENTATION);
  /** BLonMfgId constant for carrierCorporation. */
  public static final BLonMfgId carrierCorporation = new BLonMfgId(CARRIER_CORPORATION);
  /** BLonMfgId constant for shanghaiChangXiangComputer. */
  public static final BLonMfgId shanghaiChangXiangComputer = new BLonMfgId(SHANGHAI_CHANG_XIANG_COMPUTER);
  /** BLonMfgId constant for raypak. */
  public static final BLonMfgId raypak = new BLonMfgId(RAYPAK);
  /** BLonMfgId constant for nicoTechnology. */
  public static final BLonMfgId nicoTechnology = new BLonMfgId(NICO_TECHNOLOGY);
  /** BLonMfgId constant for lochinvarCorporation. */
  public static final BLonMfgId lochinvarCorporation = new BLonMfgId(LOCHINVAR_CORPORATION);
  /** BLonMfgId constant for programmedWaterTech. */
  public static final BLonMfgId programmedWaterTech = new BLonMfgId(PROGRAMMED_WATER_TECH);
  /** BLonMfgId constant for kaifaTechnology. */
  public static final BLonMfgId kaifaTechnology = new BLonMfgId(KAIFA_TECHNOLOGY);
  /** BLonMfgId constant for capelon. */
  public static final BLonMfgId capelon = new BLonMfgId(CAPELON);
  /** BLonMfgId constant for oas. */
  public static final BLonMfgId oas = new BLonMfgId(OAS);
  /** BLonMfgId constant for microTask. */
  public static final BLonMfgId microTask = new BLonMfgId(MICRO_TASK);
  /** BLonMfgId constant for pureChoice. */
  public static final BLonMfgId pureChoice = new BLonMfgId(PURE_CHOICE);
  /** BLonMfgId constant for vaconPlc. */
  public static final BLonMfgId vaconPlc = new BLonMfgId(VACON_PLC);
  /** BLonMfgId constant for orionCI. */
  public static final BLonMfgId orionCI = new BLonMfgId(ORION_CI);
  /** BLonMfgId constant for samsungElectronics. */
  public static final BLonMfgId samsungElectronics = new BLonMfgId(SAMSUNG_ELECTRONICS);
  /** BLonMfgId constant for drucegrove. */
  public static final BLonMfgId drucegrove = new BLonMfgId(DRUCEGROVE);
  /** BLonMfgId constant for janitzaElectronic. */
  public static final BLonMfgId janitzaElectronic = new BLonMfgId(JANITZA_ELECTRONIC);
  /** BLonMfgId constant for oilesCorporation. */
  public static final BLonMfgId oilesCorporation = new BLonMfgId(OILES_CORPORATION);
  /** BLonMfgId constant for osakiElectric. */
  public static final BLonMfgId osakiElectric = new BLonMfgId(OSAKI_ELECTRIC);
  /** BLonMfgId constant for viconicsElectronics. */
  public static final BLonMfgId viconicsElectronics = new BLonMfgId(VICONICS_ELECTRONICS);
  /** BLonMfgId constant for fujiElectricSystems. */
  public static final BLonMfgId fujiElectricSystems = new BLonMfgId(FUJI_ELECTRIC_SYSTEMS);
  /** BLonMfgId constant for hubbellBuildingAutomation. */
  public static final BLonMfgId hubbellBuildingAutomation = new BLonMfgId(HUBBELL_BUILDING_AUTOMATION);
  /** BLonMfgId constant for zanderFacilityEngineering. */
  public static final BLonMfgId zanderFacilityEngineering = new BLonMfgId(ZANDER_FACILITY_ENGINEERING);
  /** BLonMfgId constant for solidyneCorp. */
  public static final BLonMfgId solidyneCorp = new BLonMfgId(SOLIDYNE_CORP);
  /** BLonMfgId constant for badgerMeter. */
  public static final BLonMfgId badgerMeter = new BLonMfgId(BADGER_METER);
  /** BLonMfgId constant for draegerSafety. */
  public static final BLonMfgId draegerSafety = new BLonMfgId(DRAEGER_SAFETY);
  /** BLonMfgId constant for lgElectronics. */
  public static final BLonMfgId lgElectronics = new BLonMfgId(LG_ELECTRONICS);
  /** BLonMfgId constant for hitachi. */
  public static final BLonMfgId hitachi = new BLonMfgId(HITACHI);
  /** BLonMfgId constant for gorenje. */
  public static final BLonMfgId gorenje = new BLonMfgId(GORENJE);
  /** BLonMfgId constant for functionalDevices. */
  public static final BLonMfgId functionalDevices = new BLonMfgId(FUNCTIONAL_DEVICES);
  /** BLonMfgId constant for onicon. */
  public static final BLonMfgId onicon = new BLonMfgId(ONICON);
  /** BLonMfgId constant for electronicTheatreControls. */
  public static final BLonMfgId electronicTheatreControls = new BLonMfgId(ELECTRONIC_THEATRE_CONTROLS);
  /** BLonMfgId constant for gulfSecurity. */
  public static final BLonMfgId gulfSecurity = new BLonMfgId(GULF_SECURITY);
  /** BLonMfgId constant for controlTechniques. */
  public static final BLonMfgId controlTechniques = new BLonMfgId(CONTROL_TECHNIQUES);
  /** BLonMfgId constant for phoenixControls. */
  public static final BLonMfgId phoenixControls = new BLonMfgId(PHOENIX_CONTROLS);
  /** BLonMfgId constant for vaComTechnologies. */
  public static final BLonMfgId vaComTechnologies = new BLonMfgId(VA_COM_TECHNOLOGIES);
  /** BLonMfgId constant for buildingAutomation. */
  public static final BLonMfgId buildingAutomation = new BLonMfgId(BUILDING_AUTOMATION);
  /** BLonMfgId constant for loytec. */
  public static final BLonMfgId loytec = new BLonMfgId(LOYTEC);
  /** BLonMfgId constant for spiSystems. */
  public static final BLonMfgId spiSystems = new BLonMfgId(SPI_SYSTEMS);
  /** BLonMfgId constant for quantumAutomation. */
  public static final BLonMfgId quantumAutomation = new BLonMfgId(QUANTUM_AUTOMATION);
  /** BLonMfgId constant for lsIndustrialSystems. */
  public static final BLonMfgId lsIndustrialSystems = new BLonMfgId(LS_INDUSTRIAL_SYSTEMS);
  /** BLonMfgId constant for nanjingLianhongAutomation. */
  public static final BLonMfgId nanjingLianhongAutomation = new BLonMfgId(NANJING_LIANHONG_AUTOMATION);
  /** BLonMfgId constant for sitecoControl. */
  public static final BLonMfgId sitecoControl = new BLonMfgId(SITECO_CONTROL);
  /** BLonMfgId constant for voyantSolutions. */
  public static final BLonMfgId voyantSolutions = new BLonMfgId(VOYANT_SOLUTIONS);
  /** BLonMfgId constant for elkaElektronik. */
  public static final BLonMfgId elkaElektronik = new BLonMfgId(ELKA_ELEKTRONIK);
  /** BLonMfgId constant for mSystem. */
  public static final BLonMfgId mSystem = new BLonMfgId(M_SYSTEM);
  /** BLonMfgId constant for schneiderElectric. */
  public static final BLonMfgId schneiderElectric = new BLonMfgId(SCHNEIDER_ELECTRIC);
  /** BLonMfgId constant for isde. */
  public static final BLonMfgId isde = new BLonMfgId(ISDE);
  /** BLonMfgId constant for paragonControls. */
  public static final BLonMfgId paragonControls = new BLonMfgId(PARAGON_CONTROLS);
  /** BLonMfgId constant for schneiderElectricMerten. */
  public static final BLonMfgId schneiderElectricMerten = new BLonMfgId(SCHNEIDER_ELECTRIC_MERTEN);
  /** BLonMfgId constant for picElectronics. */
  public static final BLonMfgId picElectronics = new BLonMfgId(PIC_ELECTRONICS);
  /** BLonMfgId constant for airTestTechnologies. */
  public static final BLonMfgId airTestTechnologies = new BLonMfgId(AIR_TEST_TECHNOLOGIES);
  /** BLonMfgId constant for spega. */
  public static final BLonMfgId spega = new BLonMfgId(SPEGA);
  /** BLonMfgId constant for hunterDouglas. */
  public static final BLonMfgId hunterDouglas = new BLonMfgId(HUNTER_DOUGLAS);
  /** BLonMfgId constant for lennoxIndustries. */
  public static final BLonMfgId lennoxIndustries = new BLonMfgId(LENNOX_INDUSTRIES);
  /** BLonMfgId constant for citylone. */
  public static final BLonMfgId citylone = new BLonMfgId(CITYLONE);
  /** BLonMfgId constant for samsungSds. */
  public static final BLonMfgId samsungSds = new BLonMfgId(SAMSUNG_SDS);
  /** BLonMfgId constant for gdMideaHeatingAndVentEquip. */
  public static final BLonMfgId gdMideaHeatingAndVentEquip = new BLonMfgId(GD_MIDEA_HEATING_AND_VENT_EQUIP);
  /** BLonMfgId constant for vosslohSchwabeDeutschland. */
  public static final BLonMfgId vosslohSchwabeDeutschland = new BLonMfgId(VOSSLOH_SCHWABE_DEUTSCHLAND);
  /** BLonMfgId constant for verisIndustries. */
  public static final BLonMfgId verisIndustries = new BLonMfgId(VERIS_INDUSTRIES);
  /** BLonMfgId constant for blueEarthInc. */
  public static final BLonMfgId blueEarthInc = new BLonMfgId(BLUE_EARTH_INC);
  /** BLonMfgId constant for benHtsAg. */
  public static final BLonMfgId benHtsAg = new BLonMfgId(BEN_HTS_AG);
  /** BLonMfgId constant for hoshizakiAmerica. */
  public static final BLonMfgId hoshizakiAmerica = new BLonMfgId(HOSHIZAKI_AMERICA);
  /** BLonMfgId constant for honeywellEmon. */
  public static final BLonMfgId honeywellEmon = new BLonMfgId(HONEYWELL_EMON);
  /** BLonMfgId constant for simon. */
  public static final BLonMfgId simon = new BLonMfgId(SIMON);
  /** BLonMfgId constant for sloanValve. */
  public static final BLonMfgId sloanValve = new BLonMfgId(SLOAN_VALVE);
  /** BLonMfgId constant for trustbridge. */
  public static final BLonMfgId trustbridge = new BLonMfgId(TRUSTBRIDGE);
  /** BLonMfgId constant for mangelberger. */
  public static final BLonMfgId mangelberger = new BLonMfgId(MANGELBERGER);
  /** BLonMfgId constant for secyourit. */
  public static final BLonMfgId secyourit = new BLonMfgId(SECYOURIT);
  /** BLonMfgId constant for guangdongRongwen. */
  public static final BLonMfgId guangdongRongwen = new BLonMfgId(GUANGDONG_RONGWEN);
  /** BLonMfgId constant for ecosian. */
  public static final BLonMfgId ecosian = new BLonMfgId(ECOSIAN);
  /** BLonMfgId constant for apanet. */
  public static final BLonMfgId apanet = new BLonMfgId(APANET);
  /** BLonMfgId constant for lonMarkAfs1. */
  public static final BLonMfgId lonMarkAfs1 = new BLonMfgId(LON_MARK_AFS_1);
  /** BLonMfgId constant for honeywellFieldProgrammed. */
  public static final BLonMfgId honeywellFieldProgrammed = new BLonMfgId(HONEYWELL_FIELD_PROGRAMMED);
  /** BLonMfgId constant for celsiusBeneluxBV. */
  public static final BLonMfgId celsiusBeneluxBV = new BLonMfgId(CELSIUS_BENELUX_BV);

  /** Factory method with ordinal. */
  public static BLonMfgId make(int ordinal)
  {
    return (BLonMfgId)unknown.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonMfgId make(String tag)
  {
    return (BLonMfgId)unknown.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonMfgId(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonMfgId DEFAULT = unknown;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonMfgId.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /** Use this to translate original enum tags to new manufacter name
   *  per lonmark.org*/
  public String getConvertedName()
  {
    switch(getOrdinal())
    {
      case KABA                            :  return "Kaba Benzing";
      case ANDOVER                         :  return "Schneider Electric Andover";
      case TA_CONTROL                      :  return "Schneider Electric Tac Ab";
      case GROUP_SCHNEIDER                 :  return "Schneider Electric Power";
      case WEIDMULLER                      :  return "Schneider Elec Moeller Svea";
      case SIEBE                           :  return "Schneider Electric Tac Llc";
      case J_GORDON_DESIGN                 :  return "Control Solutions";
      case CIRCON                          :  return "Efficient Building Circon";
      case STAEFA                          :  return "Siemens Bt";
      case HOME_AUTOMATION                 :  return "Eaton Electrical";
      case HYCAL                           :  return "Hy Cal";
      case CARADON_TREND                   :  return "Trend Control Systems";
      case CSI                             :  return "Schneider Electric Csi";
      case ABB                             :  return "Abb ";
      case ELECTRONIC_SYSTEMS              :  return "Johnson Controls Es Usa";
      case SVM                             :  return "Abb Metering Svm";
      case BIRCHER_GEBAUDE_AG              :  return "Penta Control Bircher";
      case THE_TRANE_COMPANY               :  return "Trane";
      case OSMONICS                        :  return "GeWater Technologies";
      case KABA_BENZING                    :  return "Kaba Benzing52";
      case RP_RICHARDS                     :  return "Richards Zeta";
      case HONEYWELL_37                    :  return "Honeywell Inc55";
      case MAGNETEK                        :  return "Yaskawa Electric America";
      case TLON                            :  return "IPlon";
      case ORAS_GROUP                      :  return "Oras";
      case SATRON_INSTRUMENTS_INC          :  return "LevecInc";
      case REKO                            :  return "Warena Electronic";
      case NODUS_GMBH                      :  return "Ewe Tel Nodus";
      case COMTEC_TECHNOLOGIE              :  return "Distech Controls SAS";
      case HUPPE_FORM_SONNENSCHUTZSYSTEME  :  return "Huppelux Huppe Form";
      case MATSUSHITA_ELECTRIC_WORKS       :  return "Panasonic Electric Works";
      case SIEMENS_LANDIS_STAEFA_KOREA     :  return "Siemens BtKorea";
      case VAPAC_HUMIDITY_CONTROLS         :  return "Eaton Williams Vapac";
      case AUSTRALON_ENTERPRISES           :  return "Intermoco Australon";
      case SIEMENS_LANDIS_USA              :  return "Siemens Bt North America";
      case CETELAB                         :  return "Capelon Cetelab";
      case SCHNEIDER_ELECTRIC              :  return "Schneider Elec Lexel Merten";
      case SCHNEIDER_ELECTRIC_MERTEN       :  return "Schneider Elec Merten Svea";
      // 4-24-2014
      case ZELLWEGER_ANALYTICS        :  return "Honeywell Analytics";
      case VICONICS_ELECTRONICS       :  return "Schneider Viconics Electronis";
    }
    return getTag();
  }

}
