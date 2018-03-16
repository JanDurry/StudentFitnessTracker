package android.mi.ur.studentfitnesstracker.Constants;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class Constants {

    //** Permission Check **/

    public static final int PERMISSION_REQUEST_LOCATION = 99;
    //** -------SessionFragment------***//

    public static final boolean BICYCLE_SELECT = true;
    public static final boolean RUNNING_SELECT = false;

    //** -------SessionService------- **/

    public static final int LOCATION_UPDATE_TIME = 5000;
    public static final float LOCATION_UPDATE_DISTANCE = 10;


    //** -------Calculator------- **/

    public static final int CALCULATOR_FIVE_SECONDS_FACTOR = 720;
    public static final int CALCULATOR_TEN_SECONDS_FACTOR = 360;
    public static final int CALCULATOR_ONE_SECOND_FACTOR = 3600;

    //** -------Database------- **//

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

    public static final int COLUMN_WEIGHT_INDEX = 1;

    public static final int DEFAULT_WEIGHT = 75;


    public static final int COLUMN_ID_INDEX = 0;
    public static final int COLUMN_TYPE_INDEX = 1;
    public static final int COLUMN_DISTANCE_INDEX = 2;
    public static final int COLUMN_DATE_INDEX = 3;
    public static final int COLUMN_PACE_INDEX = 4;
    public static final int COLUMN_KCAL_INDEX = 5;
    public static final int COLUMN_TIME_INDEX = 6;


}
