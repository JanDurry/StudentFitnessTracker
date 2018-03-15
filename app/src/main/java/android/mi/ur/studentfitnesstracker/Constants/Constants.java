package android.mi.ur.studentfitnesstracker.Constants;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class Constants {


    //** -------SessionFragment------***//

    public static final boolean BYCICLE_SELECT = true;
    public static final boolean RUNNING_SELECT = false;

    //** -------SessionService------- **/

    public static final int LOCATION_UPDATE_TIME = 1000;
    public static final float LOCATION_UPDATE_DISTANCE = 0;


    //** -------Database------- **//

    public static final String DATABASE_NAME = "studentfitnesstracker.db";
    public static final int DATABASE_VERSION = 2;

    public static final String DATABASE_TABLE = "sessionitems";

    public static final String KEY_ID = "_id";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DISTANCE = "distance";
    public static final String KEY_DATE = "date";
    public static final String KEY_PACE = "pace";
    public static final String KEY_KCAL = "kCal";
    public static final String KEY_TIME = "time";


    public static final int COLUMN_ID_INDEX = 0;
    public static final int COLUMN_TYPE_INDEX = 1;
    public static final int COLUMN_DISTANCE_INDEX = 2;
    public static final int COLUMN_DATE_INDEX = 3;
    public static final int COLUMN_PACE_INDEX = 4;
    public static final int COLUMN_KCAL_INDEX = 5;
    public static final int COLUMN_TIME_INDEX = 6;


}
