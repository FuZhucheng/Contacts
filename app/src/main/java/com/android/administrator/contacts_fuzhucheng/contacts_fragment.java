package com.android.administrator.contacts_fuzhucheng;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/19.
 */
public class contacts_fragment extends Fragment {

    private static final int AddContact_ID= Menu.FIRST;
    private static final int DELEContact_ID=Menu.FIRST+2;
    private static final int EXITContact_ID=Menu.FIRST+3;

    public static final String AUTHORITY = "com.guo.provider.ContactsProvider";
    public static final String CONTACTS_TABLE = "contacts";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTACTS_TABLE);

    private List<String> data=new ArrayList<String>();  //存储联系人数据

    private ContentResolver resolver;
//    public final static String AUTHORITY ="content://com.android.administrator.contacts.fuzhucheng.ContactsProvider/mycontacts.db";


    private ListView listView;   //用来显示联系人列表（主页）

    private ContentProvider intent=new ContactsProvider();


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.contacts, null); //先解析file.xml布局，得到一个view
        Cursor cursor= intent.query(CONTENT_URI, null, null, null, null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                int index[]=new int[]{
                        cursor.getColumnIndex("name"),
                        cursor.getColumnIndex("mobileNumeber"),
                        cursor.getColumnIndex("remark")

                };
                Log.i("cursor", ""+cursor.getColumnIndex("name")+" "+cursor.getColumnIndex("mobileNumeber")+" "+ cursor.getColumnIndex("remark"));

            }
        }
//        private List<String> data=new ArrayList<String>();  //存储联系人数据



//        //查询
//        Cursor cursor = query(CONTENT_URI,null,null,null,null);
//        if(cursor != null){
//            if(cursor.moveToFirst()){
//                int index[] = new int[]{
//                        cursor.getColumnIndex("info_id"),
//                        cursor.getColumnIndex("info_name"),
//                        cursor.getColumnIndex("info_age")
//                };
//                do {
//                    System.out.println(cursor.getString(index[1]));
//                    System.out.println(cursor.getString(index[2]));
//                } while (cursor.moveToNext());
//            }
//        }
//    }

//        ListView listView = (ListView) rootView.findViewById(R.id.listView);
//        List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
//        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//                "Linux", "OS/2" };
//
//        ArrayAdapter<String> files = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1,
//                values);
//        listView.setAdapter(files);



//        SimpleAdapter simpleAdapter=new SimpleAdapter(
//                this.getActivity().getApplicationContext(),
//                listData,
//                R.layout.item,
//                new String[]{"file_list_item1"},
//                new int[]{R.id.name});
//        listView.setAdapter(simpleAdapter);
        return rootView;

//        return inflater.inflate(R.layout.contacts, null);
        //设置菜单长按
//        getListView().setOnCreateContextContextMenuListener(this);
        //查询，获得所有联系人的数据
//        Cursor cursor=managedQuery(getIn)
//        Intent intent=getIntent();
//        if (intent.getData()==null){
//            intent.setData(ContactsProvider.CONTENT_URI);
//        }

    }


    public void onPause() {

        super.onPause();
    }

    public void onResume() {

        super.onResume();
    }

    public void onDestroy() {

        super.onDestroy();
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onStop() {
        super.onStop();
    }

    public void onStart() {
        super.onStart();
    }

}
