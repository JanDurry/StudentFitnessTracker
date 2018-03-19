package android.mi.ur.studentfitnesstracker.Constants;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class Constants {

    /** MAP Options **/

    public static final int POLYLINE_WIDTH = 5;
    public static final int CAMERA_DEFAULT_ZOOM = 16;
    public static final int CAMERA_ON_SESSION_FINISHED_ZOOM = 14;
    public static final int CAMERA_BEARING = 0;
    public static final int CAMERA_TILT = 45;
    public static final String CAMERA_DEFAULT_TITLE = "Regensburg";
    public static final double CAMERA_DEFAULT_LATITUDE = 49.013432;
    public static final double CAMERA_DEFAULT_LONGITUDE = 12.101624;




    /** -----TIME CONVERTER NUMBERS AND CALCULATOR----- **/

    public static final int MS_TO_SECONDS = 1000;
    public static final int METERS_TO_KILOMETERS = 1000;

    public static final double CYCLE_KCAL_FACTOR = 2.6;




    //** -------------Utility----------- **/

    public static final int APPROXIMATION_SMOOTHER = 2;

    //*------------Sessiontypes------------ **/

    public static final String SESSION_TYPE_CYCLE = "Radfahren";
    public static final String SESSION_TYPE_RUN = "Laufen";

    //* ----------DATE---------------- *//

    public static final String SIMPLE_DATE_FORMAT = "dd.MM.yy HH:mm:ss";



    //** ------Permission Check------- **/

    public static final int PERMISSION_REQUEST_LOCATION = 99;




    //** -------SessionFragment------***//

    public static final boolean BICYCLE_SELECT = true;
    public static final boolean RUNNING_SELECT = false;



    //** -------SessionService------- **/

    public static final int LOCATION_UPDATE_TIME = 5000;
    public static final float LOCATION_UPDATE_DISTANCE = 10;
    public static final int GPS_PROVIDER_LISTENER_INDEX = 0;
    public static final int NETWORK_PROVIDER_LISTENER_INDEX = 1;

    /** ------ Periodic Statistics ------ **/

    public static final int TOTALS_RUN_INDEX = 0;
    public static final int TOTALS_CYCLE_INDEX = 1;




    //** -------Calculator------- **/

    public static final int CALCULATOR_FIVE_SECONDS_FACTOR = 720;
    public static final int CALCULATOR_TEN_SECONDS_FACTOR = 360;
    public static final int CALCULATOR_ONE_SECOND_FACTOR = 3600;





    //** ---------------------DATABASE---START-------------- **//

    public static final String DATABASE_NAME = "studentfitnesstracker.db";
    public static final int DATABASE_VERSION = 2;

    public static final String DATABASE_TABLE = "sessionitems";
    public static final String DATABASE_TABLE_USER = "sessionUserData";

    public static final String KEY_ID = "_id";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DISTANCE = "distance";
    public static final String KEY_DATE = "date";
    public static final String KEY_PACE = "pace";
    public static final String KEY_KCAL = "kCal";
    public static final String KEY_TIME = "time";

    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_SESSION_GOAL = "session_goal";

    public static final int COLUMN_WEIGHT_INDEX = 1;
    public static final int COLUMN_SESSION_GOAL_INDEX = 2;
    public static final int COLUMN_GOAL_DATE_INDEX = 3;

    public static final int DEFAULT_WEIGHT = 75;
    public static final int DEFAULT_GOAL_KCAL = 10000; // 15 kg Körperfett in Kalorien
    public static final String DEFAULT_GOAL_DATE = "31.12.2020";

    public static final int COLUMN_ID_INDEX = 0;
    public static final int COLUMN_TYPE_INDEX = 1;
    public static final int COLUMN_DISTANCE_INDEX = 2;
    public static final int COLUMN_DATE_INDEX = 3;
    public static final int COLUMN_PACE_INDEX = 4;
    public static final int COLUMN_KCAL_INDEX = 5;
    public static final int COLUMN_TIME_INDEX = 6;

    //** ---------------------DATABASE---ENDE-------------- **//



    /** ------------LOG TEXT TAG------------- **/

    public static final String EXCEPTION_TAG = "EXEPTION";
    public static final String METHOD_TAG = "METHODE";
    public static final String LISTENER_TAG = "LISTENER";
    public static final String ERROR_TAG = "ERROR";
    public static final String DB_TAG = "DATABASE";


    /** --------------NOTIFICATION-------------- **/

    public static final String CHANNEL_ID = "123";
    public static final int UNIQUE_ID = 7654;


    /** -------------BOTTOM NAVIGATION VIEW ID´S----------------- **/

    public static final int BOTTOM_NAVIGATION_VIEW_PERIODIC_STATISTICS_ID = 0;
    public static final int BOTTOM_NAVIGATION_VIEW_NEW_SESSION_ID = 1;
    public static final int BOTTOM_NAVIGATION_VIEW_SESSION_OVERVIEW_ID = 2;


}
