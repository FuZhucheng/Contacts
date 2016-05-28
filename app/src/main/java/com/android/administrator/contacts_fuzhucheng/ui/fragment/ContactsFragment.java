package com.android.administrator.contacts_fuzhucheng.ui.fragment;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.administrator.contacts_fuzhucheng.ContactsProvider;
import com.android.administrator.contacts_fuzhucheng.R;
import com.android.administrator.contacts_fuzhucheng.ui.ContactsBean;
import com.android.administrator.contacts_fuzhucheng.ui.activity.AddActivity;
import com.android.administrator.contacts_fuzhucheng.ui.activity.ContactView;
import com.android.administrator.contacts_fuzhucheng.ui.view.ClearEditText;
import com.android.administrator.contacts_fuzhucheng.ui.view.SideBar;
import com.android.administrator.contacts_fuzhucheng.utils.UIutil_pinyin.CharacterParser;
import com.android.administrator.contacts_fuzhucheng.utils.db.MyAdapter;
import com.android.administrator.contacts_fuzhucheng.utils.UIutil_pinyin.PinyinComparator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 *     存在一些问题：代码量和维护性都不好
 *     解决方法：第一：你不是能知道修改这个联系人在数据里的id嘛，你回去的时候，根据这个id再去重新查一遍
*第二：写缓存，把数据保存到内存里
 *配合对象没管理好
 *
 */
public class ContactsFragment extends Fragment {


    private SideBar sideBar;        //左侧导航栏

    public ClearEditText mClearEditText;   //上面的搜索

//    static int j=0;

    //根据拼音来排列ListView里面的数据类
    private PinyinComparator pinyinComparator;

    //汉字转换成拼音的类
    private CharacterParser characterParser;


//    private View view_pop;//坑爹的popupwindow！！！！activity和fragment中差异极大

    private List<ContactsBean> listContacts = new ArrayList<>();  //存储联系人数据

    public static  ContactsBean contactsBean;

//    private ContentProvider intent_provider = new ContactsProvider();

//    private PopupWindow mPopWindow;//点击item的菜单

    private static final String LOG_TAG = "MY_LOG_TAG";

    private ListView listView;   //用来显示联系人列表（主页）

//    private DBOpenHelper dbOpenHelper;        //不用自己创建的数据库了

    private ContentProvider intent = new ContactsProvider();



//    private SimpleCursorAdapter adapter;



    private MyAdapter adapter;//自定义baseadapter

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);
        setHasOptionsMenu(true);


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts, null); //先解析file.xml布局，得到一个view
        listView = (ListView) rootView.findViewById(R.id.listView);

        mClearEditText = (ClearEditText) rootView.findViewById(R.id.filter_edit);
        mClearEditText.setBackgroundColor(Color.parseColor("#6f599c"));
        sideBar = (SideBar) rootView.findViewById(R.id.sidrbar);



                                                            //这个是右侧的监听，里面的方法跟拼音排序联系人了起来
//        //设置右侧触摸监听
//        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
//
//            @Override
//            public void onTouchingLetterChanged(String s) {
//                //该字母首次出现的位置
//                int position = adapter.getPositionForSection(s.charAt(0));
//                if(position != -1){
//                    listView.setSelection(position);
//                }
//
//            }
//        });

//        Uri selectUri = Uri.parse("content://com.android.personProvider/person");
//        Cursor cursor = contentResolver.query(selectUri, null, null, null, null);
        //获取将要绑定的数据设置到data中
//        data = getData();

//        data = readContacts();
        adapter = new MyAdapter(getActivity());

        adapter.setData(readContacts());
        listView.setAdapter(adapter);


        //item的监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Map<String,String>map=(Map<String, String>)parent.getItemAtPosition(position);            //之前使用了架构不好的map

//                Toast.makeText(getActivity(), "联系人: "  + listContacts.get(position).getDisplayName()
//                        + "，手机号: " + listContacts.get(position).getPhoneNumber(), Toast.LENGTH_SHORT).show();
                ContactsBean listContacts = (ContactsBean) adapter.getItem(position);

                dialog(listContacts, position);
//                Log.i(LOG_TAG, String.valueOf(map));
            }
        });

        return rootView;

    }

    //删除联系人
    public void showDelete(final String name, final int position) {
        new AlertDialog.Builder(getActivity()).setIcon(R.drawable.image_contacts).setTitle("是否删除此联系人")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

//                        Uri deleteUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
//                        Log.i(LOG_TAG, String.valueOf(deleteUri));
//                        Uri lookupUri = ContactsContract.Contacts.getLookupUri(getContext().getContentResolver(), deleteUri);
//                        Log.e(LOG_TAG, String.valueOf(lookupUri));
//                        if (lookupUri != Uri.EMPTY) {
////                            Log.i("delete_url",lookupUri.toString());
//                            getActivity().getContentResolver().delete(deleteUri, null, null);
//
//                        }

                        //根据姓名求_id
                        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
                        ContentResolver resolver =getContext().getContentResolver();
                        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data._ID},"display_name=?", new String[]{name}, null);
                        if(cursor.moveToFirst()){
                            int id = cursor.getInt(0);
                            //根据id删除data中的相应数据
                            resolver.delete(uri, "display_name=?", new String[]{name});
                            uri = Uri.parse("content://com.android.contacts/data");
                            resolver.delete(uri, "raw_contact_id=?", new String[]{id+""});
                        }
                        cursor.close();


//                        Cursor contactsCur = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//                        while (contactsCur.moveToNext()) {
//                            String where = ContactsContract.Data._ID + " =?";
//                            String[] whereparams = new String[]{contactId};
//                            getActivity().getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, where, whereparams);
//                        }

                        adapter.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "该联系人已经被删除.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).show();
    }

    //使用dialog弹出来
    private void dialog(final ContactsBean contactsBean, final int position) {
        final String items[] = {"查看详细", "新增联系人", "删除联系人", "分享联系人"};

//        Log.i(LOG_TAG,id);

        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  //先得到构造器
        builder.setTitle(contactsBean.getDisplayName()); //设置标题
        //builder.setMessage("是否确认退出?"); //设置内容
        builder.setIcon(R.drawable.image_contacts);//设置图标，图片id即可
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                Uri uri = null;
//                Toast.makeText(getActivity(), items[which], Toast.LENGTH_SHORT).show();
                switch (items[which]) {
                    case "查看详细":
                        Intent intent_look = new Intent();
                        intent_look.putExtra("name",contactsBean.getDisplayName());
                        intent_look.putExtra("mobilephone",contactsBean.getPhoneNumber());
                        intent_look.putExtra("id",String.valueOf(contactsBean.getContactId()));
                        intent_look.putExtra("position",String.valueOf(position));
                        intent_look.putExtra("remark",contactsBean.getRemark());
                        intent_look.putExtra("qq",contactsBean.getQq());
                        intent_look.putExtra("web",contactsBean.getWeb());
                        intent_look.putExtra("address",contactsBean.getAddress());
                        intent_look.putExtra("e_mail",contactsBean.getE_mail());
                        Log.e("id", String.valueOf(contactsBean.getContactId()));
//                        intent_look.putExtra("contact_position",position);
                        intent_look.setClass(getActivity(), ContactView.class);
                        startActivity(intent_look);
                        getActivity().finish();

            //访问本地通讯录
//                        uri = ContactsContract.Contacts.CONTENT_URI;
//                        Uri personUri = ContentUris.withAppendedId(uri, listContacts.getContactId());
//                        Intent intent2 = new Intent();
//                        intent2.setAction(Intent.ACTION_VIEW);
//                        intent2.setData(personUri);
//                        startActivity(intent2);


                        break;
                    case "新增联系人":
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), AddActivity.class);
                        startActivity(intent);
                        break;
                    case "删除联系人":
                        showDelete(contactsBean.getDisplayName(), position);
//                        Toast.makeText(getActivity(), "删除联系人", Toast.LENGTH_SHORT).show();
                        break;
                    case "分享联系人":
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_TEXT,
                                contactsBean.getDisplayName()+contactsBean.getPhoneNumber());
                        shareIntent.setType("text/plain");

                        //设置分享列表的标题，并且每次都显示分享列表
                        startActivity(Intent.createChooser(shareIntent, "分享到"));
                        break;
                    default:
                        break;
                }

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "确定", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }


    //坑爹的popupwindow！！！！在fragment里面的事件，那个点击外部消失事件限定在activity
//    private void showPopupWindow(){
//
//        //设置contentView
//        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow, null);
////        contentView.setFocusable(true);//默认的是所有的操作都无效的，除了HOME键。而且是可以操作后面的界面的。想要锁定后面的界面
////        contentView.setFocusableInTouchMode(true);
//
//        mPopWindow = new PopupWindow(contentView,
//                ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT);
//        //设置popupwindow位置
//        mPopWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
////        mPopWindow.setTouchable(true); // 设置PopupWindow可触摸
//
//        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
//        mPopWindow.setFocusable(true);
//        mPopWindow.setOutsideTouchable(true);//设置点击屏幕其它地方弹出框消失
//        view_pop.onTouchEvent(  );
//
//        // 设置各个控件的点击响应
//        TextView tv1 = (TextView)contentView.findViewById(R.id.pop_computer);
//        TextView tv2 = (TextView)contentView.findViewById(R.id.pop_financial);
//        TextView tv3 = (TextView)contentView.findViewById(R.id.pop_manage);
//
//        tv1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri uri = null;
//                uri = ContactsContract.Contacts.CONTENT_URI;
////                Uri personUri = ContentUris.withAppendedId(uri, cb.getContactId());
//
//                Intent intent=new Intent();
//                intent.setClass(getActivity(),ContactView.class);
//                startActivity(intent);
//
//            }
//        });
//        tv2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "默认Toast样式",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//        tv3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "默认Toast样式",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //显示PopupWindow
//        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.contacts, null);
//        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
//
//
//    }

//    public boolean onTouchEvent(MotionEvent event) {
//// TODO Auto-generated method stub
//        if (mPopWindow != null && mPopWindow.isShowing()) {
//            mPopWindow.dismiss();
//            mPopWindow = null;
//        }
////        return super.onTouchEvent(event);
//        return false;
//    }



    //中间listview数据显示
    public List<ContactsBean> readContacts() {
//        Cursor cursor = null;
////        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
////        Map<String, Object> map;
//
//        int contactIdIndex = 0;
//
//
//        try {
//// 查询联系人数据
//            cursor = getContext().getContentResolver().query(
//                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                    null, null, null, null);
//
//
//
//            while (cursor.moveToNext()) {
//// 获取联系人姓名
//                String displayName = cursor.getString(cursor.getColumnIndex(
//                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//// 获取联系人手机号
//                String number = cursor.getString(cursor.getColumnIndex(
//                        ContactsContract.CommonDataKinds.Phone.NUMBER));
//                contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);        //数据库中联系人id
//                String contactId = cursor.getString(contactIdIndex);        //数据库中联系人id
//
//
//
//
//
//                //把+86删除
//                if (number.startsWith("+86"))
//                    number = number.substring(3);
//
////                map = new HashMap<String, Object>();
////                map.put("img", R.drawable.image_contacts);
////                map.put("title", displayName);
////                map.put("info", number);
////                map.put("item_Id", contactId);
////                list.add(map);
//
////                Log.i(LOG_TAG,String.valueOf(cursor.toString()));
////                Log.i(LOG_TAG,String.valueOf(map));      // 测试放进去有没有错
//                ContactsBean contactsBean = new ContactsBean();
//                contactsBean.setContactId(contactId);
//                contactsBean.setPhoto(R.drawable.image_contacts);
//                contactsBean.setDisplayName(displayName);
//                contactsBean.setPhoneNumber(number);
//                listContacts.add(contactsBean);
//
//
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//
//            }
//        }



        //uri = content://com.android.contacts/contacts
        Uri uri = Uri.parse("content://com.android.contacts/contacts"); //访问raw_contacts表
        ContentResolver resolver = this.getContext().getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.RawContacts.Data._ID}, null, null, null);  //获得_id属性

        while (cursor.moveToNext()) {
//            StringBuilder buf = new StringBuilder();
            contactsBean = new ContactsBean();
//            contactsBean.setPhoto(R.drawable.image_contacts);
            int id = cursor.getInt(0);//获得id并且在data中寻找数据
//            buf.append("id="+id);
            Log.e("contactRawid", String.valueOf(id));
            contactsBean.setContactId(String.valueOf(id));
            uri = Uri.parse("content://com.android.contacts/contacts/" + id + "/data"); //如果要获得data表中某个id对应的数据，则URI为content://com.android.contacts/contacts/#/data
            Cursor cursor2 = resolver.query(uri, new String[]{ContactsContract.Data.DATA1, ContactsContract.Data.MIMETYPE}, null, null, null);  //data1存储各个记录的总数据，mimetype存放记录的类型，如电话、email等


            while (cursor2.moveToNext()) {
                String data = cursor2.getString(cursor2.getColumnIndex("data1"));
                if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/name")) {       //如果是名字
                    contactsBean.setDisplayName(data);

//                    String pinyin = characterParser.getSelling(data);                             //bug在这里，getSelling为抽象方法，检查过值没错
//                    String sortString = pinyin.substring(0, 1).toUpperCase();
//                    // 正则表达式，判断首字母是否是英文字母
//                    if(sortString.matches("[A-Z]")){
//                        contactsBean.setSortLetters(sortString.toUpperCase());
//                    }else{
//                        contactsBean.setSortLetters("#");
//                    }

                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/phone_v2")) {  //如果是电话
//                    buf.append(",phone="+data);
                    //把+86删除
                    if (data.startsWith("+86"))
                        data = data.substring(3);
                    contactsBean.setPhoneNumber(data);
                }
                else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/note")){//备注
                    contactsBean.setRemark(data);
//                    Log.e("remark",data);
                }
//                contactsBean.setPhoto(get_people_image(getContext(),contactsBean.getPhoneNumber()));
                else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/photo")){    //头像,这个方式是错的！！因为遍历的时候只查出有头像的那个联系人！
                    contactsBean.setPhoto(get_people_image(getContext(),contactsBean.getPhoneNumber()));
//                    Log.e("number",contactsBean.getPhoneNumber());
                }

                else if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/email_v2")){  //如果是email
                    contactsBean.setE_mail(data);
//                    Log.e("e_mail",data);
                }
                else if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/postal-address_v2")){ //如果是地址
                    contactsBean.setAddress(data);
//                    Log.e("address",data);
                }
//                else if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/organization")){  //如果是组织
//                }
                else if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/website")){ //如果是网址
                    contactsBean.setWeb(data);
//                    Log.e("web",data);
                }
                else if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/im")){ //如果是qq
                    contactsBean.setQq(data);
//                    Log.e("qq",data);
                }

            }


            listContacts.add(contactsBean);
            cursor2.close();
        }
        cursor.close();



        return listContacts;
    }

    //太过复杂的数据显示方法
//    private List<Map<String, Object>> getData()
//    {
//
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//            Map<String, Object> map;

//        //没问题的数据显示方法
////        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
////        Map<String, Object> map;
////        for(int i=0;i<10;i++)
////        {
////            map = new HashMap<String, Object>();
////            map.put("img", R.drawable.image_contacts);
////            map.put("title", "靓仔");
////            map.put("info", "打波无...");
////            list.add(map);
////        }
//        return list;
//    }


    // 封装好的方法，根据号码获得联系人头像
    public Bitmap get_people_image(Context context, String x_number) {

        Bitmap bmp_head = null;
        // 获得Uri
        Uri uriNumber2Contacts = Uri.parse("content://com.android.contacts/"
                + "data/phones/filter/" + x_number);
        // 查询Uri，返回数据集
        Cursor cursorCantacts = context.getContentResolver().query(
                uriNumber2Contacts,
                null,
                null,
                null,
                null);
        // 如果该联系人存在
        if (cursorCantacts.getCount() > 0) {
            // 移动到第一条数据
            cursorCantacts.moveToFirst();
            // 获得该联系人的contact_id
            Long contactID = cursorCantacts.getLong(cursorCantacts.getColumnIndex("contact_id"));
            // 获得contact_id的Uri
            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactID);
            // 打开头像图片的InputStream
            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), uri);
            // 从InputStream获得bitmap
            bmp_head = BitmapFactory.decodeStream(input);
        }
//        Log.e("photo", String.valueOf(bmp_head));
        cursorCantacts.close();

            return bmp_head;

    }


    //设置listview背景色
    public void setColor_blue(){
        listView.setBackgroundColor(Color.parseColor("#102b6a"));
        mClearEditText.setBackgroundColor(Color.parseColor("#102b6a"));
    }
    public void setColor_gray(){
        listView.setBackgroundColor(Color.parseColor("#77787b"));
        mClearEditText.setBackgroundColor(Color.parseColor("#77787b"));
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

