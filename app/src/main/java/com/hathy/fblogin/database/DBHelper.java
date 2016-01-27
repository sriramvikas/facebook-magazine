package com.hathy.fblogin.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase writeDb = null;
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "fbdatabase";


    String ACCESS_TOKEN = "token";

    String CREATE_ACCESS_TOKEN = "create table " + ACCESS_TOKEN + "(token TEXT)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        writeDb = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_ACCESS_TOKEN);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub


        db.execSQL("DROP TABLE IF EXISTS " + CREATE_ACCESS_TOKEN);
        // Log.e("console","db upgrading");
        // create new tables
        onCreate(db);
    }


    public void addToken(String token) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (this.writeDb == null)
            writeDb = this.getWritableDatabase();
        try {
            Cursor c = db.rawQuery("select token from " + ACCESS_TOKEN + " where token='" + token + "'", null);
            if (c.getCount() > 0) {
                Log.e("update func token", "ping");
                ContentValues values = new ContentValues();
                values.put("token", token);
                this.writeDb.update(ACCESS_TOKEN, values, "token=" + token, null);
            } else {
                ContentValues values = new ContentValues();
                values.put("token", token);
                writeDb.insert(ACCESS_TOKEN, null, values);
            }
        } catch (Exception e) {
            Log.e("exp add token", "" + e.getMessage());
        }
    }


    public JSONArray getToken() throws JSONException {

        SQLiteDatabase db = this.getReadableDatabase();
        //JSONObject data = new JSONObject();
        JSONArray events = new JSONArray();
        try {

            Cursor c = db.rawQuery("select *  from " + ACCESS_TOKEN, null);
            // Cursor c = db.rawQuery("select *  from " + EVENTLIST_ENTRY_TABLE + " where eventid='" + eventID + "' AND attendeeid='"+ attendeeId +"' AND startdate='"+startDate+"';", null);
            if (c.getCount() == 0) {
                return events;
            } else if (c.moveToFirst()) {
                JSONObject data = null;
                do {
                    data = new JSONObject();
                    data.put("token", c.getString((c.getColumnIndex("token"))));
                    events.put(data);
                } while (c.moveToNext());

            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("exp getting entry", "" + e.getMessage());
        }
        return events;
    }


    public int getTokenCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        try {
            Cursor c = db.rawQuery("select *  from " + ACCESS_TOKEN, null);
            if (c.getCount() == 0) {
                return count;
            } else {
                c.moveToFirst();
                count = c.getCount();
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("exp count entry", "" + e.getMessage());
        }
        return count;
    }
    public void dropTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + ACCESS_TOKEN);
    }
}
