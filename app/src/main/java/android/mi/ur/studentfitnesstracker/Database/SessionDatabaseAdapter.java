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
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
            Log.e(Constants.DB_TAG, e.toString());
        }
    }

    public void close() {
        db.close();
    }

    public boolean checkIfUserDataExists() {
        String count = "SELECT count(*) FROM " + Constants.DATABASE_TABLE_USER;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0) {
            return true;
        }
        return false;
    }

    public void updateUserData(int weight, int sessionGoal) {
        String strFilter = "_id=1";
        ContentValues args = new ContentValues();
        args.put(Constants.KEY_WEIGHT, weight);
        args.put(Constants.KEY_SESSION_GOAL, sessionGoal);
        db.update(Constants.DATABASE_TABLE_USER, args, strFilter, null);
    }

    public void updateUserGoal(int sessionGoal, String date) {
        String strFilter = "_id=1";
        ContentValues args = new ContentValues();
        args.put(Constants.KEY_DATE, date);
        args.put(Constants.KEY_SESSION_GOAL, sessionGoal);
        db.update(Constants.DATABASE_TABLE_USER, args, strFilter, null);
    }

    public int getUserWeight() {
        int weight = Constants.DEFAULT_WEIGHT;
        Cursor cursor = db.query(Constants.DATABASE_TABLE_USER, new String[] { Constants.KEY_ID,
                Constants.KEY_WEIGHT, Constants.KEY_SESSION_GOAL}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            weight = cursor.getInt(Constants.COLUMN_WEIGHT_INDEX);
        }
        return weight;
    }

        public String getUserGoalDate() {
        String date = Constants.DEFAULT_GOAL_DATE;
        Cursor cursor = db.query(Constants.DATABASE_TABLE_USER, new String[] { Constants.KEY_ID,
                Constants.KEY_WEIGHT, Constants.KEY_SESSION_GOAL}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            date = cursor.getString(Constants.COLUMN_GOAL_DATE_INDEX);
        }
        return date;
    }

    public int getUserGoal() {
        int sessionGoal = Constants.DEFAULT_GOAL_KCAL;
        Cursor cursor = db.query(Constants.DATABASE_TABLE_USER, new String[] { Constants.KEY_ID,
                Constants.KEY_WEIGHT, Constants.KEY_SESSION_GOAL}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            sessionGoal = cursor.getInt(Constants.COLUMN_SESSION_GOAL_INDEX);
        }
        return sessionGoal;
    }

    public long insertUserData(int weight, int sessionGoal) {
        ContentValues userValues = new ContentValues();
        userValues.put(Constants.KEY_WEIGHT, weight);
        userValues.put(Constants.KEY_SESSION_GOAL, sessionGoal);
        return db.insert(Constants.DATABASE_TABLE_USER, null, userValues);
    }

    public long insertSessionItem(SessionItem session) {
        ContentValues sessionValues = new ContentValues();
        sessionValues.put(Constants.KEY_TYPE, session.getSessionType());
        sessionValues.put(Constants.KEY_DISTANCE, session.getDistance());
        sessionValues.put(Constants.KEY_DATE, session.getDate());
        sessionValues.put(Constants.KEY_PACE, session.getPace());
        sessionValues.put(Constants.KEY_KCAL, session.getkCal());
        sessionValues.put(Constants.KEY_TIME, session.getTime());
        return db.insert(Constants.DATABASE_TABLE, null, sessionValues);
    }

    public void removeSessionItem(SessionItem session) {
        String toDelete = Constants.KEY_DATE + "=?";
        String[] deleteArguments = new String[]{session.getDate()};
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
                String time = cursor.getString(Constants.COLUMN_TIME_INDEX);

                sessions.add(new SessionItem(type, distance, time, kcal, pace, date));

            } while (cursor.moveToNext());
        }
        return sessions;
    }


    //** private class to create database tables **/

    private class SessionDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + Constants.DATABASE_TABLE + " (" + Constants.KEY_ID
                + " integer primary key autoincrement, " + Constants.KEY_TYPE
                + " text not null, " + Constants.KEY_DISTANCE + " integer not null, "
                + Constants.KEY_DATE + " text, " + Constants.KEY_PACE + " text not null, "
                + Constants.KEY_KCAL + " double not null, " + Constants.KEY_TIME + " text not null);";

        private static final String DATABASE_CREATE_USER = "create table "
                + Constants.DATABASE_TABLE_USER + " (" + Constants.KEY_ID
                + " integer primary key autoincrement, " + Constants.KEY_WEIGHT
                + " int not null, " + Constants.KEY_SESSION_GOAL + " int not null);";

        public SessionDBOpenHelper(Context c, String dbname,
                                          SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE_USER);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
