package prototype.prototype_jeff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Jeff on 12/13/2017.
 * SQLite database class to create, add and delete from a db
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Denote columns index 1-7
    public static final String DATABASE_NAME = "notificationsS.db";
    public static final String TABLE_NAME = "notifications2_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "type";
    public static final String COL_3 = "threshhold";
    public static final String COL_4 = "date";
    public static final String COL_5 = "email";
    public static final String COL_6 = "phone";
    public static final String COL_7 = "numMinutes";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT, threshhold DOUBLE, date TEXT, email TEXT, phone TEXT, numMinutes TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newT) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Takes input and adds it to the database in the cooresponding columns
    public boolean insertData(String type, Double threshhold, long date, String email, String phone, Long numMinutes) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, type);
        contentValues.put(COL_3, threshhold);
        contentValues.put(COL_4, Long.toString(date));
        contentValues.put(COL_5, email);
        contentValues.put(COL_6, phone);
        contentValues.put(COL_7, numMinutes);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            Log.d("DATABASE TEST", "REFUND INSERTED TO DATABASE FAILED ");

            return false;
        } else {
            if (type.equals("REFUND")) {
                Log.d("DATABASE TEST", "REFUND INSERTED TO DATABASE");
            }
            if(type.equals("PERIODIC")){
                Log.d("DATABASE TEST", "PERIODIC INSERTED TO DATABASE");
            }
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);
        return result;
    }

    // Deletes all data in case the db should be cleared for any reason
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }
}
