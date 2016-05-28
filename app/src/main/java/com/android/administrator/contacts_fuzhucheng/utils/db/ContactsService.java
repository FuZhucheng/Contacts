package com.android.administrator.contacts_fuzhucheng.utils.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/23.
 */
public class ContactsService {
    private DBOpenHelper dbOpenHelper;

    public ContactsService(Context context) {
        // TODO Auto-generated constructor stub
        dbOpenHelper=new DBOpenHelper(context);
    }
    public void save(Person person){
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        db.execSQL("insert into person(name,age) values(?,?)",new Object[]{person.getName(),person.getMbilephone(),person.getRemark()});
    }
    public void delete(Integer _id){
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        db.execSQL("delete from person where _id=?",new Object[]{_id});
    }
    public Person find(Integer _id){
        SQLiteDatabase db=dbOpenHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from person where _id=?", new String[]{_id.toString()});
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int mobilephone = cursor.getInt(cursor.getColumnIndex("mobilephone"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));

            Person person = new Person();
            person.set_id(id);
            person.setName(name);
            person.setMobilephone(mobilephone);
            person.setRemark(remark);
            return person;
        }
        return null;
    }
    public List<Person> findAll(){
        SQLiteDatabase db=dbOpenHelper.getReadableDatabase();
        List<Person> persons = new ArrayList<Person>();
        Cursor cursor=db.rawQuery("select * from person", null);
        while(cursor.moveToNext()){
            Person person=new Person();
            int id=cursor.getInt(cursor.getColumnIndex("_id"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            int mobilephone = cursor.getInt(cursor.getColumnIndex("mobilephone"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            person.set_id(id);
            person.setName(name);
            person.setMobilephone(mobilephone);
            person.setRemark(remark);
            persons.add(person);
        }
        return persons;
    }
}
