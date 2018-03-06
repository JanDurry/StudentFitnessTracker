package android.mi.ur.studentfitnesstracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.mi.ur.studentfitnesstracker.Constants.Constants;
import android.mi.ur.studentfitnesstracker.Objects.SessionItem;

import java.util.ArrayList;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class SessionDatabaseAdapter {

    private static final String DATABASE_NAME = "studentfitnesstracker.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "sessionitems";

    private SessionDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public SessionDatabaseAdapter(Context context) {
        dbHelper = new SessionDBOpenHelper(context, DATABASE_NAME, null,
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

    public long insertSessionItem(SessionItem session) {
        ContentValues sessionValues = new ContentValues();
        sessionValues.put(Constants.KEY_DATE, session.getFormattedDate());
        return db.insert(DATABASE_TABLE, null, sessionValues);
    }

    public void removeSessionItem(SessionItem session) {
        String toDelete = Constants.KEY_ID + "=?";
        String[] deleteArguments = new String[]{""+session.getId()+""};
        db.delete(DATABASE_TABLE, toDelete, deleteArguments);
    }

    public ArrayList<SessionItem> getAllSessionItems() {
        ArrayList<SessionItem> sessions = new ArrayList<SessionItem>();
        return sessions;

    }


    //** private class to create database table **/

    private class SessionDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + Constants.KEY_ID
                + " integer primary key autoincrement, " + Constants.KEY_DATE
                + " text not null);";

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
