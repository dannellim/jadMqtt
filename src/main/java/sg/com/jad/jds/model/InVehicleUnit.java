package sg.com.jad.jds.model;

public class InVehicleUnit {
	
	private String vehicleID = ""; //TST0728L
    private String mobileID = ""; //6B2E
    private String sessionID = "";

    private String driverID = "";
    private String driverName = "";
    private String driverPass = "";
    private String driverLogonResult = "";

    private int meterVersion = 70; //need get version
    private String meterChecksum = "";
    private String mapVersion = "|OpenStreetMap 230420|SLA 230430|";  //7c 4d 61 70 20 76 65 72 20 72 65 74 3a 20 31 2e 30 7c 7c (pre-configured) |Map ver ret: 1.0||
    private String ivdType = "0A"; //ucast running no 0x1 00001 + model number 010 //default 11 00010 (running no in backend 0x2) + model number 001
    private double ivdVersion = 1.00;

    private String scin = "";

    private double latitude = 0;
    private double longitude = 0;
    private double bearing = 0;
    private float accuracy = 0f;
    private int speed = 0;

    private String hexGpsFixFlag = "0";
    private String hexZoneFlag = "00";
    private String hexEmergencyFlag = "00";
    private String hexExpressWayFlag = "0";

    private String meterFlag = "10"; // 10 meter error 00 meter vacant 01 meter stop 11 meter hired  ///default on error mode (will change once taximeter detected
    private String suspensionFlag = "0";  //1 suspended 0 not suspended
    private String ivdStatusFlag = "1011"; // Offline

    private transient String hexServerByte = "1";

    private double accumulatedMileage = 0; //KM
    private double hiredDistance = 0; //KM
    private double unhiredDistance = 0; //KM
    private double totalDistance = 0; //KM

    /////variables for ping response///////////

    private String refNo = "";
    private String seqNo = "";
    private String t1 = ""; //time stamp upon sending by backend to comms server (now 0000 - un used)
    private String t2 = ""; //time stamp upon sending by comms server to ivd (now 0000 - un used)
    private String t3 = ""; //time stamp upon sending by by IVD to comms server (now 0000 - un used)
    private String t4 = ""; //time stamp upon sending by comms server to backend (now 0000 - un used)

    /////variables for ping response///////////

    ///START FOR LOGIN IN --- DEFAULT AND UNUSED FOR NOW/////////////////

    private String cannedMsgVersionNumber = "01";
    private String generalParameterVersionNumber = "01";
    private String tariffInfoVersionNumber = "01";
    private String zoneDefinitionVersionNumber = "01";
    private String rankDefinitionVersionNumber = "01";
    private String expresswayDefinitionVersionNumber = "01";
    private String addressUpdateVersionNumber = "01";

}
