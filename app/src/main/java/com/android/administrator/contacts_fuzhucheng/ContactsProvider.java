package com.android.administrator.contacts_fuzhucheng;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

//import java.sql.SQLException;

/**
 * Created by Administrator on 2016/4/18.
 */
public class ContactsProvider extends ContentProvider {

    //标签
    private static final String TAG = "ContactsProvider";
    //数据库帮助类
    private DBHelper dbHelper;
    //数据库
    private SQLiteDatabase contactsDB;
    //数据库操作uri
    public static final String AUTHORITY = "com.guo.provider.ContactsProvider";
    public static final String CONTACTS_TABLE = "contacts";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTACTS_TABLE);

    //自定义类型
    public static final int CONTACTS = 1;
    public static final int CONTACTS_ID = 2;
    private static final UriMatcher uriMatcher;

    static {
        //没有匹配信息
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //全部联系人
        uriMatcher.addURI(AUTHORITY, "contacts", CONTACTS);
        //单独一个联系人信息
        uriMatcher.addURI(AUTHORITY, "contatcs/#", CONTACTS_ID);

    }

    //取数据库
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        //执行创建数据库
        contactsDB = dbHelper.getWritableDatabase();
        return (contactsDB == null) ? false : true;
    }

    //查询数据
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.e(TAG + ":query", "in Query");
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        //设置要查询的数据表
        qb.setTables(CONTACTS_TABLE);

        switch (uriMatcher.match(uri)) {
            //构建where语句，定位到指定id值的列
            case CONTACTS_ID:
                qb.appendWhere(ContactColumn._ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                break;
        }
        //查询
        Cursor c = qb.query(contactsDB, projection, selection, selectionArgs, null, null, sortOrder);
        //设置通知改变uri
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    //uri类型转换
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            //所有联系人
            case CONTACTS:
                return "vnd.android.cursor.dir/vnd.guo.android.mycontacts";
            //指定联系人
            case CONTACTS_ID:
                return "vnd.android.cursor.item/vnd.guo.android.mycontacts";
            default:
                throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
    }


    public Uri insert(Uri uri, ContentValues initialValues) {
        //判断uri地址是否合法
        if (uriMatcher.match(uri) != CONTACTS) {
            throw new IllegalArgumentException("Unknown URI" + uri);
        }
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
            Log.e(TAG + "insert", "initialValues is not null");
        } else {
            values = new ContentValues();
        }
        //如果对应的名称没有值，则设置默认值为“”
        if (values.containsKey(ContactColumn.NAME) == false) {
            values.put(ContactColumn.NAME, "");
        }
        if (values.containsKey(ContactColumn.MOBILENUM) == false) {
            values.put(ContactColumn.MOBILENUM, "");
        }
        if (values.containsKey(ContactColumn.REMARK) == false) {
            values.put(ContactColumn.REMARK, "");
        }
        Log.e(TAG + "insert", values.toString());
        //插入数据
        long rowId = contactsDB.insert(CONTACTS_TABLE, null, values);
        if (rowId > 0) {
            //将id值加入uri地址中
            Uri noteUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            //通知改变
            getContext().getContentResolver().notifyChange(noteUri, null);
            Log.e(TAG + "insert", noteUri.toString());
            return noteUri;
        }
        throw new SQLException("Failed to insert row into" + uri);

    }


    public int delete(Uri uri, String where, String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            //删除满足where条件的行
            case CONTACTS:
                count = contactsDB.delete(CONTACTS_TABLE, where, selectionArgs);
                break;
            case CONTACTS_ID:
                //取得联系人的id信息
                String contactID = uri.getPathSegments().get(1);
                //删除满足where条件，并且id为contactsID的记录
                count = contactsDB.delete(CONTACTS_TABLE, ContactColumn._ID + "="
                        + contactID
                        + (!TextUtils.isEmpty(where) ? "AND(" + where + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


    public int update(Uri uri, ContentValues values, String where, String[] selectionArgs) {
        int count;
        Log.e(TAG + "update", values.toString());
        Log.e(TAG + "update", uri.toString());
        Log.e(TAG + "update :match", "" + uriMatcher.match(uri));
        switch (uriMatcher.match(uri)) {
            //根据where条件批量进行更新
            case CONTACTS:
                Log.e(TAG + "update", CONTACTS + "");
                count = contactsDB.update(CONTACTS_TABLE, values, where, selectionArgs);
                break;
            //更新指定行
            case CONTACTS_ID:
                String contactID = uri.getPathSegments().get(1);
                Log.e(TAG + "update", contactID + "");
                count = contactsDB.update(CONTACTS_TABLE, values, ContactColumn._ID + "=" + contactID
                        + (!TextUtils.isEmpty(where) ? "AND(" + where + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        //通知更改
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}

