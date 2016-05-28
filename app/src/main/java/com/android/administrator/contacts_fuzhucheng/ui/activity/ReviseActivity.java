package com.android.administrator.contacts_fuzhucheng.ui.activity;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.administrator.contacts_fuzhucheng.R;
import com.android.administrator.contacts_fuzhucheng.ui.ContactsBean;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ReviseActivity extends AppCompatActivity {

    private static final String TAG = "ReviseActivity";

    private EditText et_name;
    private EditText et_mobilephone;
    private EditText et_remark;
    private EditText et_homephone;
    private Button sure;
    private Button cancle;
    private TextView addd_other;
    private Uri uri_image;//获取的图片资源
    private String photoid;


    private ImageView view_image;

    private ContactsBean mOldContact;
    private ContactsBean mNewContact;

    private long exitTime = 0;//监听返回两次退出

    private Bitmap bitmap;

    //隐藏的需要添加的信息
    private TextView tv_qq;
    private TextView tv_web;
    private TextView tv_address;
    private TextView tv_e_mail;
    private EditText et_show_qq;
    private EditText et_show_web;
    private EditText et_show_address;
    private EditText et_show_e_mail;
    //对应的分隔线
    private TextView qq_line;
    private TextView web_line;
    private TextView address_line;


    String value_get_qq="";
    String value_get_web="";
    String value_get_address="";
    String value_get_e_mail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise);

        initView();
        initListener();

        Intent intent_revise_get = getIntent();
        final String value_get_name = intent_revise_get.getStringExtra("name_revise");
        final String value_get_mobilephone = intent_revise_get.getStringExtra("mobilephone_revise");
//        final String value_get_id = intent_revise_get.getStringExtra("id_revise");
        final String value_get_remark = intent_revise_get.getStringExtra("remark_revise");

            value_get_qq = intent_revise_get.getStringExtra("qq_revise");

            value_get_web = intent_revise_get.getStringExtra("web_revise");

            value_get_address = intent_revise_get.getStringExtra("address_revise");

            value_get_e_mail=intent_revise_get.getStringExtra("e_mail");


        Log.e("web",value_get_web);
        et_name.setText(value_get_name);
        et_mobilephone.setText(value_get_mobilephone);
        et_remark.setText(value_get_remark);
        //设置qq
        if (value_get_qq==null||value_get_qq.equals("")){
            tv_qq.setVisibility(View.INVISIBLE);
            et_show_qq.setVisibility(View.INVISIBLE);
            qq_line.setVisibility(View.INVISIBLE);
        }
        else {
            tv_qq.setVisibility(View.VISIBLE);
            et_show_qq.setVisibility(View.VISIBLE);
            qq_line.setVisibility(View.VISIBLE);
            et_show_qq.setText(value_get_qq);
        }
        //设置web
        if (value_get_web==null||value_get_web.equals("")){
            tv_web.setVisibility(View.INVISIBLE);
            et_show_web.setVisibility(View.INVISIBLE);
            web_line.setVisibility(View.INVISIBLE);
        }
        else {
            tv_web.setVisibility(View.VISIBLE);
            et_show_web.setVisibility(View.VISIBLE);
            web_line.setVisibility(View.VISIBLE);
            et_show_web.setText(value_get_web);
        }
        //设置address
        if (value_get_address==null||value_get_address.equals("")){
            tv_address.setVisibility(View.INVISIBLE);
            et_show_address.setVisibility(View.INVISIBLE);
            address_line.setVisibility(View.INVISIBLE);
        }
        else {
            tv_address.setVisibility(View.VISIBLE);
            et_show_address.setVisibility(View.VISIBLE);
            address_line.setVisibility(View.VISIBLE);
            et_show_address.setText(value_get_address);
        }
        //设置e-mail
        if (value_get_e_mail==null||value_get_e_mail.equals("")){
            tv_e_mail.setVisibility(View.INVISIBLE);
            et_show_e_mail.setVisibility(View.INVISIBLE);

        }
        else {
            tv_e_mail.setVisibility(View.VISIBLE);
            et_show_e_mail.setVisibility(View.VISIBLE);
            et_show_e_mail.setText(value_get_e_mail);
        }



        mOldContact = new ContactsBean();
        mOldContact.setDisplayName(value_get_name);
        mOldContact.setPhoneNumber(value_get_mobilephone);
        mOldContact.setRemark(value_get_remark);
//        mOldContact.setQq(value_get_qq);
//        mOldContact.setWeb(value_get_web);
//        mOldContact.setAddress(value_get_address);
//        mOldContact.setE_mail(value_get_e_mail);


    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_mobilephone = (EditText) findViewById(R.id.et_mobilephone);
        et_remark = (EditText) findViewById(R.id.et_remark);
        et_homephone = (EditText) findViewById(R.id.et_homephone);
        cancle = (Button) findViewById(R.id.button2);
        sure = (Button) findViewById(R.id.button1);
        view_image = (ImageView) findViewById(R.id.view_image_revise);
        addd_other=(TextView)findViewById(R.id.add_other);


        tv_qq=(TextView)findViewById(R.id.qq);
        tv_web=(TextView)findViewById(R.id.web);
        tv_address=(TextView)findViewById(R.id.address);
        tv_e_mail=(TextView)findViewById(R.id.e_mail);
        et_show_qq=(EditText)findViewById(R.id.et_show_qq);
        et_show_web=(EditText)findViewById(R.id.et_show_web);
        et_show_address=(EditText)findViewById(R.id.et_show_address);
        et_show_e_mail=(EditText)findViewById(R.id.et_show_e_mail);
        qq_line=(TextView)findViewById(R.id.qq_line);
        web_line=(TextView)findViewById(R.id.web_line);
        address_line=(TextView)findViewById(R.id.address_line);
    }

    private void initListener() {
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = et_name.getText().toString();
                final String mobilephone = et_mobilephone.getText().toString();
                final String remark = et_remark.getText().toString();
                final String qq=et_show_qq.getText().toString();
                final String web=et_show_web.getText().toString();
                final String address=et_show_address.getText().toString();
                final String e_mail=et_show_e_mail.getText().toString();

//                revise_finish(id, name, mobilephone);                 //第一种方法，然而按照api写的出了问题

//                Log.e("qq_finish", String.valueOf(qq));
//                Log.e("web_finish", web);

                mNewContact = new ContactsBean();
                mNewContact.setDisplayName(name);
                mNewContact.setPhoneNumber(mobilephone);
                mNewContact.setRemark(remark);
                mNewContact.setQq(String.valueOf(qq));
                mNewContact.setWeb(web);
                mNewContact.setAddress(address);
                mNewContact.setE_mail(e_mail);
                updateContact(mOldContact, mNewContact);


//                Log.e("QQ_set",mNewContact.getQq());

//                Log.e("web_finish", web);
//                Log.e("address_finish", mNewContact.getAddress());
//                Log.e("e_mail_finish", mNewContact.getE_mail());


                Intent intent_revise_receiver = new Intent();
                intent_revise_receiver.putExtra("revise_finish_name", mNewContact.getDisplayName());
                intent_revise_receiver.putExtra("revise_finish_mobilephone", mNewContact.getPhoneNumber());
                intent_revise_receiver.putExtra("revise_finish_remark", mNewContact.getRemark());
                intent_revise_receiver.putExtra("revise_finish_qq",mNewContact.getQq());
                intent_revise_receiver.putExtra("revise_finish_web",mNewContact.getWeb());
                intent_revise_receiver.putExtra("revise_finish_address",mNewContact.getAddress());
                intent_revise_receiver.putExtra("revise_finish_e_mail",mNewContact.getE_mail());
                intent_revise_receiver.setClass(ReviseActivity.this, ContactView.class);
                startActivity(intent_revise_receiver);
                finish();

            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });

        view_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用意图进行访问手机图库
                Intent intent = new Intent();
                    /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
	                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
	                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
            }
        });
        addd_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
    }

//    private void revise_finish(String id_new_name, String name, String mobilephone) {
////        ContentValues values = new ContentValues();
////        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, mobilephone);
////        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
////        String where = ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
////                + ContactsContract.Data.MIMETYPE + "=?";
////        String[] selectionArgs = new String[] { id,
////                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE };
////        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
////                where, selectionArgs);
//
//
////        Uri uri = Uri.parse("content://com.android.contacts/data");//对data表的所有数据操作
////        ContentResolver resolver = getContentResolver();
////        ContentValues values = new ContentValues();
////        values.put("data1", mobilephone);
////        resolver.update(uri, values, where, new String[]{id,
////                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE});
//
//        Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{ContactsContract.RawContacts.Data.RAW_CONTACT_ID},
//
//                ContactsContract.Contacts.DISPLAY_NAME + "=?", new String[]{name}, null);
//        cursor.moveToFirst();
//        String id_name = cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.Data.RAW_CONTACT_ID));
//        cursor.close();
//        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//
//        //更新电话号码
//
//        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
//                .withSelection(
//
//                        ContactsContract.RawContacts.Data.RAW_CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + " = ?" +
//                                " AND " + ContactsContract.CommonDataKinds.Phone.TYPE + "=?", new String[]{id_name, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
//                                String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME)}).withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobilephone).build());
//
//        try {
//            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//        } catch (Exception e) {
//        }
//    }


    public void updateContact(ContactsBean oldContact, ContactsBean newContact) {
        Log.e(TAG, "update start");
        String id = getContactID(oldContact.getDisplayName());

        ContentResolver resolver = getContentResolver();

        Uri datauri=Uri.parse("content://com.android.contacts/data");


        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        //update name
        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
                        new String[]{id, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE})
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, newContact.getDisplayName())
                .build());
        Log.e(TAG, "update name: " + newContact.getDisplayName());

        //update number
        if (!newContact.getPhoneNumber().equals("")) {
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{id, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, newContact.getPhoneNumber())
                    .build());
            Log.e(TAG, "update number: " + newContact.getPhoneNumber());
        }
        //update  remark
        if (!newContact.getRemark().equals("")) {
            // 更新remark
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + " = ?",
                            new String[]{id, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.Note.NOTE, newContact.getRemark())
                    .build());
            Log.e(TAG, "update remark:" + newContact.getRemark());
        }


        //update email if mail
            if(!newContact.getE_mail().equals("")) {
                ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
                                new String[]{id, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE})
                        .withValue(ContactsContract.CommonDataKinds.Email.DATA, newContact.getE_mail())
                        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, 1)
                        .build());
                Log.d("e_mail", "update email: " + newContact.getE_mail());
            }
        else {
                ContentValues e_mail_values=new ContentValues();
                // 添加Email
                e_mail_values.clear();
                e_mail_values.put("raw_contact_id", id);
                e_mail_values.put("mimetype", "vnd.android.cursor.item/email_v2");
                e_mail_values.put("data2", newContact.getE_mail());
                resolver.insert(datauri, e_mail_values);

            }

//        view_image.setDrawingCacheEnabled(true);
//        UpdateContactsPhoto(ReviseActivity.this, Integer.parseInt(id), view_image.getDrawingCache(), photoid);
//        view_image.setDrawingCacheEnabled(false);


        //修改联系人的头像
//        final ByteArrayOutputStream os = new ByteArrayOutputStream();
// 将Bitmap压缩成PNG编码，质量为100%存储
//        view_image.setDrawingCacheEnabled(true);
//        view_image.getDrawingCache().compress(Bitmap.CompressFormat.PNG, 100, os);
//
//        byte[] avatar =os.toByteArray();
//        ContentValues values = new ContentValues();
//        values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, id);
//        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
//        values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, avatar);
//        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values, null, null);

        //update qq
        if(!newContact.getQq().equals("")) {
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + " = ?",
                            new String[]{id, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.Im.DATA1, newContact.getQq())
                    .withValue(
                            ContactsContract.CommonDataKinds.Im.PROTOCOL,
                            ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ)
                    .build());
//            Log.e("qq_show",et_show_qq.getText().toString());
            Log.d(TAG, "update qq: " + newContact.getQq());
        }
        else {
            ContentValues qqvalues=new ContentValues();
            qqvalues.put("mimetype","vnd.android.cursor.item/im");
            qqvalues.put("raw_contact_id", id);
            qqvalues.put("data1",newContact.getQq());
            resolver.insert(datauri, qqvalues);

            Log.d(TAG, "update qq: " + newContact.getQq());
        }
        //update web
        if(!newContact.getWeb().equals("")) {
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{id, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.Website.URL, newContact.getWeb())
                    .build());
            Log.d("web", "update web: " + newContact.getWeb());
        }
        else {
            Log.d("web", "update web: " + newContact.getWeb());

        }
        //update address
        if(!newContact.getAddress().equals("")) {
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{id, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.STREET, newContact.getAddress())
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
                            ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK)
                    .build());
            Log.d("address", "update address: " + newContact.getAddress());
        }
        else {
            Log.d("address", "update address: " + newContact.getAddress());
        }

        try {
            this.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            Log.e(TAG, "update success");
        } catch (Exception e) {
            Log.e(TAG, "update failed");
            Log.e(TAG, e.getMessage());
        }
    }


    //使用dialog弹出来
    private void dialog() {
        final String items[] = {"QQ", "web", "address", "e-mail"};

//        Log.i(LOG_TAG,id);

        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(ReviseActivity.this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        //builder.setMessage("是否确认退出?"); //设置内容
        builder.setIcon(R.drawable.image_contacts);//设置图标，图片id即可
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (items[which]) {
                    case "QQ":
                        tv_qq.setVisibility(View.VISIBLE);
                        et_show_qq.setVisibility(View.VISIBLE);
                        qq_line.setVisibility(View.VISIBLE);
                        break;
                    case "web":
                        tv_web.setVisibility(View.VISIBLE);
                        et_show_web.setVisibility(View.VISIBLE);
                        web_line.setVisibility(View.VISIBLE);
                        break;
                    case "address":
                        tv_address.setVisibility(View.VISIBLE);
                        et_show_address.setVisibility(View.VISIBLE);
                        address_line.setVisibility(View.VISIBLE);
                        break;
                    case "e-mail":
                        tv_e_mail.setVisibility(View.VISIBLE);
                        et_show_e_mail.setVisibility(View.VISIBLE);
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
                Toast.makeText(ReviseActivity.this, "确定", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }


    public static void UpdateContactsPhoto(Context context, int contacts_id,
                                           Bitmap bitmap, String photoId) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

//            photoId=photoId.substring(38);
        // 将Bitmap压缩成PNG编码，质量为100%存储
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] avatar = os.toByteArray();
        ContentValues values = new ContentValues();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, contacts_id);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
        values.put("is_super_primary", 1);
        values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, avatar);
        if (photoId !=null) {
            int row = context.getContentResolver().update(ContactsContract.Data.CONTENT_URI,
                    values, "_id=" + Integer.parseInt(photoId), null);
            Log.d("tag", "row=" + row);
        } else {
            Uri row = context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
            Log.d("tag", "uri_row=" + row);
        }
    }
    //根据名字得到id
    public String getContactID(String name) {
        String id = "0";
        Cursor cursor = this.getContentResolver().query(
                android.provider.ContactsContract.Contacts.CONTENT_URI,
                new String[]{android.provider.ContactsContract.Contacts._ID},
                android.provider.ContactsContract.Contacts.DISPLAY_NAME +
                        "='" + name + "'", null, null);
        if (cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndex(
                    android.provider.ContactsContract.Contacts._ID));
        }
        cursor.close();
        return id;
    }

    //访问手机图库，并返回图片数据
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            uri_image = data.getData();
            photoid=uri_image.toString();
            Log.e("uri", uri_image.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri_image));
                ImageView view_image = (ImageView) findViewById(R.id.view_image_revise);

	                /* 将Bitmap设定到ImageView */
                view_image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Intent no_revise = new Intent();
                no_revise.putExtra("name", mOldContact.getDisplayName());
                no_revise.putExtra("mobilephone", mOldContact.getPhoneNumber());
                no_revise.putExtra("remark",mOldContact.getRemark());
                no_revise.putExtra("qq",mOldContact.getQq());
                no_revise.putExtra("web",mOldContact.getWeb());
                no_revise.putExtra("address",mOldContact.getAddress());
                no_revise.putExtra("e_mail",mOldContact.getE_mail());
                no_revise.setClass(ReviseActivity.this, ContactView.class);
                startActivity(no_revise);
                finish();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
