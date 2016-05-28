package com.android.administrator.contacts_fuzhucheng.ui.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.administrator.contacts_fuzhucheng.R;
import com.android.administrator.contacts_fuzhucheng.ui.ContactsBean;
import com.android.administrator.contacts_fuzhucheng.ui.fragment.ContactsFragment;
import com.android.administrator.contacts_fuzhucheng.utils.db.MyAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactView extends AppCompatActivity {

    private ImageView image;

    private TextView tv_name;
    private TextView tv_mobilephone;
    private TextView tv_remark;

    private TextView revise;
    private TextView share;
    private TextView delete;

    //隐藏的需要添加的信息
    private TextView tv_qq;
    private TextView tv_web;
    private TextView tv_address;
    private TextView tv_e_mail;
    private TextView tv_show_qq;
    private TextView tv_show_web;
    private TextView tv_show_address;
    private TextView tv_show_e_mail;
    //对应的分隔线
    private TextView qq_line;
    private TextView web_line;
    private TextView address_line;

    private long exitTime = 0;//监听返回两次退出


    private MyAdapter adapter;//自定义baseadapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contacts);


        initView();


        Intent intent_get = getIntent();
        String value_name = intent_get.getStringExtra("name");
        String value_mobilephone = intent_get.getStringExtra("mobilephone");
        String value_id = intent_get.getStringExtra("id");
        String value_position=intent_get.getStringExtra("position");
        String value_remark=intent_get.getStringExtra("remark");
        String value_qq=intent_get.getStringExtra("qq");
        String value_web=intent_get.getStringExtra("web");
        String value_address=intent_get.getStringExtra("address");
        String value_e_mail=intent_get.getStringExtra("e_mail");

        String name_finish_revise=intent_get.getStringExtra("revise_finish_name");
        String mobilephone_finish_revise=intent_get.getStringExtra("revise_finish_mobilephone");
        String remark_finish_revise=intent_get.getStringExtra("revise_finish_remark");
        String qq_finish_revise=intent_get.getStringExtra("revise_finish_qq");
        String web_finish_revise=intent_get.getStringExtra("revise_finish_web");
        String address_finish_revise=intent_get.getStringExtra("revise_finish_address");
        String e_mail_finish_revise=intent_get.getStringExtra("revise_finish_e_mail");
//        Log.e("qq_finish",qq_finish_revise);
//        Log.e("web_finish",web_finish_revise);
//        Log.e("address_finish",address_finish_revise);
//        Log.e("e_mail_finish",e_mail_finish_revise);

        //监听
        initListener(value_id,value_position);

        //没有编辑过的数据
        tv_name.setText(value_name);
        tv_mobilephone.setText(value_mobilephone);
        tv_remark.setText(value_remark);

        //设置qq
        if (value_qq==null){
        }
        else {
            tv_qq.setVisibility(View.VISIBLE);
            tv_show_qq.setVisibility(View.VISIBLE);
            qq_line.setVisibility(View.VISIBLE);
            tv_show_qq.setText(value_qq);
        }
        //设置web
        if (value_web==null){
        }
        else {
            tv_web.setVisibility(View.VISIBLE);
            tv_show_web.setVisibility(View.VISIBLE);
            web_line.setVisibility(View.VISIBLE);
            tv_show_web.setText(value_web);
        }
        //设置address
        if (value_address==null){
        }
        else {
            tv_address.setVisibility(View.VISIBLE);
            tv_show_address.setVisibility(View.VISIBLE);
            address_line.setVisibility(View.VISIBLE);
            tv_show_address.setText(value_address);
        }
        //设置e-mail
        if (value_e_mail==null){
        }
        else {
            tv_e_mail.setVisibility(View.VISIBLE);
            tv_show_e_mail.setVisibility(View.VISIBLE);
            tv_show_e_mail.setText(value_e_mail);
        }

        //判断是否有头像，无则使用默认头像
        if (image.getDrawable()==null){
            image.setImageResource(R.drawable.image_contacts);
        }
        else {
            image.setImageBitmap(get_people_image(ContactView.this, value_mobilephone));
        }


        //得到编辑后的信息，返回到查看详细的界面，把数据刷新
        if (name_finish_revise==null&&mobilephone_finish_revise==null&&remark_finish_revise==null) {
            Log.e("revise_none","没有编辑name&&mobilephone");
        }
        else {
            tv_name.setText(name_finish_revise);
            tv_mobilephone.setText(mobilephone_finish_revise);
            tv_remark.setText(remark_finish_revise);
        }
        if (web_finish_revise==null){
            Log.e("revise_none","没有编辑web");
        }
        else {
            tv_web.setVisibility(View.VISIBLE);
            tv_show_web.setVisibility(View.VISIBLE);
            web_line.setVisibility(View.VISIBLE);
            tv_show_web.setText(web_finish_revise);
        }
        if (qq_finish_revise==null) {
            Log.e("revise_none","没有编辑qq");
        }
        else {
            tv_qq.setVisibility(View.VISIBLE);
            tv_show_qq.setVisibility(View.VISIBLE);
            qq_line.setVisibility(View.VISIBLE);
            tv_show_qq.setText(qq_finish_revise);
        }
        if (address_finish_revise==null){
            Log.e("revise_none","没有编辑address");
        }
        else {
            tv_address.setVisibility(View.VISIBLE);
            tv_show_address.setVisibility(View.VISIBLE);
            address_line.setVisibility(View.VISIBLE);
            tv_show_address.setText(address_finish_revise);
        }
        if (e_mail_finish_revise==null){
            Log.e("revise_none","没有编辑e-mail");
        }
        else {
            tv_e_mail.setVisibility(View.VISIBLE);
            tv_show_e_mail.setVisibility(View.VISIBLE);
            tv_show_e_mail.setText(e_mail_finish_revise);
        }


    }

    private void initView() {
        tv_name = (TextView) findViewById(R.id.look_name);
        tv_mobilephone = (TextView) findViewById(R.id.look_mobilephone);
        tv_remark = (TextView) findViewById(R.id.look_remark);

        revise = (TextView) findViewById(R.id.revise);
        share = (TextView) findViewById(R.id.share);
        delete = (TextView) findViewById(R.id.delete);

        image=(ImageView)findViewById(R.id.image);

        tv_qq=(TextView)findViewById(R.id.qq);
        tv_web=(TextView)findViewById(R.id.web);
        tv_address=(TextView)findViewById(R.id.address);
        tv_e_mail=(TextView)findViewById(R.id.e_mail);
        tv_show_qq=(TextView)findViewById(R.id.show_qq);
        tv_show_web=(TextView)findViewById(R.id.show_web);
        tv_show_address=(TextView)findViewById(R.id.show_address);
        tv_show_e_mail=(TextView)findViewById(R.id.show_e_mail);

        qq_line=(TextView)findViewById(R.id.qq_line);
        web_line=(TextView)findViewById(R.id.web_line);
        address_line=(TextView)findViewById(R.id.address_line);
    }

    private void initListener(String id, final String position) {

        revise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {               //跳转到编辑联系人activity

                final String name = tv_name.getText().toString();
                final String mobilephone = tv_mobilephone.getText().toString();
                final String remark = tv_remark.getText().toString();
                final String qq= tv_show_qq.getText().toString();
                final String web=tv_show_web.getText().toString();
                final String address=tv_show_address.getText().toString();
                final String e_mail=tv_show_e_mail.getText().toString();

                Intent intent_revise = new Intent();
                intent_revise.putExtra("name_revise", name);
                intent_revise.putExtra("mobilephone_revise", mobilephone);
//                intent_revise.putExtra("id_revise", id);
                intent_revise.putExtra("remark_revise",remark);


                    intent_revise.putExtra("qq_revise", String.valueOf(qq));
                    intent_revise.putExtra("web_revise", web);
                    intent_revise.putExtra("address_revise", address);
                intent_revise.putExtra("e_mail_revise", e_mail);



                intent_revise.setClass(ContactView.this, ReviseActivity.class);
                startActivity(intent_revise);
                ContactView.this.finish();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = tv_name.getText().toString();
                final String mobilephone = tv_mobilephone.getText().toString();
//                Toast.makeText(ContactView.this, "分享联系人", Toast.LENGTH_SHORT).show();
                Intent shareIntent = new Intent();

                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, name + mobilephone);
                shareIntent.setType("text/plain");

                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享到"));

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ContactView.this).setIcon(R.drawable.image_contacts).setTitle("是否删除此联系人")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                final String name = tv_name.getText().toString();
                                //根据姓名求id
                                Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
                                ContentResolver resolver = getContentResolver();
                                Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data._ID}, "display_name=?", new String[]{name}, null);
                                if (cursor.moveToFirst()) {
                                    int id = cursor.getInt(0);
                                    //根据id删除data中的相应数据
                                    resolver.delete(uri, "display_name=?", new String[]{name});
                                    uri = Uri.parse("content://com.android.contacts/data");
                                    resolver.delete(uri, "raw_contact_id=?", new String[]{id + ""});
                                }
                                cursor.close();
                                adapter.remove(Integer.parseInt(position));
                                adapter.notifyDataSetChanged();
                                Toast.makeText(ContactView.this, "删除联系人", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).show();

            }
        });
    }



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




    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Intent intent_back_Mainactivity=new Intent();
                intent_back_Mainactivity.setClass(ContactView.this,MainActivity.class);
                startActivity(intent_back_Mainactivity);
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