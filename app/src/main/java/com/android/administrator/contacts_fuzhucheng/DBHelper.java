package com.android.administrator.contacts_fuzhucheng;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/18.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mycontacts.db";
    public static final int DATABASE_VERSION = 2;
    public static final String CONTACTS_TABLE = "contacts";     //表名

    public static final String DATABASE_CREATE = "CREATE TABLE" + CONTACTS_TABLE + "("
            + ContactColumn._ID + "integer primary key autoincrement,"
            + ContactColumn.NAME + " text,"
            + ContactColumn.MOBILENUM + " text,"
            + ContactColumn.REMARK + " text)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE);
        onCreate(db);
    }
}
