package android.mi.ur.studentfitnesstracker.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.mi.ur.studentfitnesstracker.Constants.Constants;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class SessionDatabase {

    private static final String DATABASE_NAME = "fitnesstracker.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "sessionitems";

    private FitnessTrackerDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public SessionDatabase(Context context) {
        dbHelper = new FitnessTrackerDBOpenHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }


    private class FitnessTrackerDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + Constants.KEY_ID
                + " integer primary key autoincrement, " + Constants.KEY_DISTANCE
                + " integer not null);";

        public FitnessTrackerDBOpenHelper(Context c, String dbname,
                                          SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
