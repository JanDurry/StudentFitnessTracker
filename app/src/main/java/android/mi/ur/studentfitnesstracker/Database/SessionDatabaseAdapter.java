package android.mi.ur.studentfitnesstracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.mi.ur.studentfitnesstracker.Constants.Constants;
import android.mi.ur.studentfitnesstracker.Objects.SessionItem;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class SessionDatabaseAdapter {

    private SessionDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public SessionDatabaseAdapter(Context context) {
        dbHelper = new SessionDBOpenHelper(context, Constants.DATABASE_NAME, null,
                Constants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
            Log.e("DB", "DATABASE OPEN");
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
            Log.e("DB", "DATABASE CONNECTION FAILED");
        }
    }

    public void close() {
        db.close();
    }

    public long insertSessionItem(SessionItem session) {
        ContentValues sessionValues = new ContentValues();
        sessionValues.put(Constants.KEY_TYPE, session.getSessionType());
        sessionValues.put(Constants.KEY_DISTANCE, session.getDistance());
        sessionValues.put(Constants.KEY_DATE, session.getDate().toString());
        sessionValues.put(Constants.KEY_PACE, session.getPace());
        sessionValues.put(Constants.KEY_KCAL, session.getkCal());
        sessionValues.put(Constants.KEY_TIME, session.getTime());
        return db.insert(Constants.DATABASE_TABLE, null, sessionValues);
    }

    public void removeSessionItem(SessionItem session) {
        String toDelete = Constants.KEY_DATE + "=?";
        String[] deleteArguments = new String[]{session.getDate().toString()};
        db.delete(Constants.DATABASE_TABLE, toDelete, deleteArguments);
    }

    public ArrayList<SessionItem> getAllSessionItems() {
        ArrayList<SessionItem> sessions = new ArrayList<SessionItem>();
        Cursor cursor = db.query(Constants.DATABASE_TABLE, new String[] { Constants.KEY_ID,
                Constants.KEY_TYPE, Constants.KEY_DISTANCE, Constants.KEY_DATE, Constants.KEY_PACE,
                Constants.KEY_KCAL, Constants.KEY_TIME }, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String type = cursor.getString(Constants.COLUMN_TYPE_INDEX);
                int distance = cursor.getInt(Constants.COLUMN_DISTANCE_INDEX);
                String date = cursor.getString(Constants.COLUMN_DATE_INDEX);
                String pace = cursor.getString(Constants.COLUMN_PACE_INDEX);
                double kcal = cursor.getDouble(Constants.COLUMN_KCAL_INDEX);
                int time = cursor.getInt(Constants.COLUMN_TIME_INDEX);

                sessions.add(new SessionItem(type, distance, time, kcal, pace, date));

            } while (cursor.moveToNext());
        }
        return sessions;
    }


    //** private class to create database table **/

    private class SessionDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + Constants.DATABASE_TABLE + " (" + Constants.KEY_ID
                + " integer primary key autoincrement, " + Constants.KEY_TYPE
                + " text not null, " + Constants.KEY_DISTANCE + " integer not null, "
                + Constants.KEY_DATE + " text, " + Constants.KEY_PACE + " text not null, "
                + Constants.KEY_KCAL + " double not null, " + Constants.KEY_TIME + " integer not null);";

        public SessionDBOpenHelper(Context c, String dbname,
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
