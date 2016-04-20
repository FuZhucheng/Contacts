package com.android.administrator.contacts_fuzhucheng;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

    private ContentResolver resolver;
    public final static String AUTHORITY ="content://com.android.administrator.contacts.fuzhucheng.ContactsProvider/mycontacts.db";

    private ListView listView;
    private ContentProvider intent;


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.contacts, null); //先解析file.xml布局，得到一个view

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
