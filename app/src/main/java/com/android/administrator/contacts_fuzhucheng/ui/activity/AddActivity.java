package com.android.administrator.contacts_fuzhucheng.ui.activity;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.administrator.contacts_fuzhucheng.ui.fragment.ContactsFragment;
import com.android.administrator.contacts_fuzhucheng.utils.db.MyAdapter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    private long exitTime = 0;//监听返回两次退出

    private Bitmap bitmap;

    private EditText et_name;
    private EditText et_mobilephone;
    private EditText et_remark;
    private EditText et_homephone;
    private TextView addd_other;

    private Button save;
    private Button cancle;

    //隐藏的需要添加的信息
    private TextView qq;
    private TextView web;
    private TextView address;
    private TextView e_mail;
    private EditText et_show_qq;
    private EditText et_show_web;
    private EditText et_show_address;
    private EditText et_show_e_mail;
    //对应的分隔线
    private TextView qq_line;
    private TextView web_line;
    private TextView address_line;
    private TextView e_mail_line;

    private ImageView view_image;
    private Uri uri_image;//获取的图片资源

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_person);

        initView();
        initListener();

    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_mobilephone = (EditText) findViewById(R.id.et_mobilephone);
        et_remark = (EditText) findViewById(R.id.et_remark);
        et_homephone = (EditText) findViewById(R.id.et_homephone);
        save = (Button) findViewById(R.id.button1);
        cancle = (Button) findViewById(R.id.button2);
        view_image = (ImageView) findViewById(R.id.view_image);
        addd_other=(TextView)findViewById(R.id.add_other);

        qq=(TextView)findViewById(R.id.qq);
        web=(TextView)findViewById(R.id.web);
        address=(TextView)findViewById(R.id.address);
        e_mail=(TextView)findViewById(R.id.e_mail);
        et_show_qq=(EditText)findViewById(R.id.et_show_qq);
        et_show_web=(EditText)findViewById(R.id.et_show_web);
        et_show_address=(EditText)findViewById(R.id.et_show_address);
        et_show_e_mail=(EditText)findViewById(R.id.et_show_e_mail);
        qq_line=(TextView)findViewById(R.id.qq_line);
        web_line=(TextView)findViewById(R.id.web_line);
        address_line=(TextView)findViewById(R.id.address_line);
        e_mail_line=(TextView)findViewById(R.id.e_mail_line);

    }
    private void initListener(){
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


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = et_name.getText().toString();
                final String mobilephone = et_mobilephone.getText().toString();
                final String remark = et_remark.getText().toString();
                final int qq= Integer.parseInt(et_show_qq.getText().toString());
                final String web=et_show_web.getText().toString();
                final String address=et_show_address.getText().toString();
                final String e_mail=et_show_e_mail.getText().toString();


//                Log.e("name", name);
//                Log.e("mobilephone",mobilephone);
//                Log.e("remark",remark);
                insert(name, mobilephone, remark,qq,web,address,e_mail);


                Toast.makeText(AddActivity.this, "添加联系人成功", Toast.LENGTH_SHORT).show();
                Intent intent_SaveBack = new Intent();
                intent_SaveBack.setClass(AddActivity.this, MainActivity.class);
                startActivity(intent_SaveBack);
                finish();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent_back = new Intent();
//                intent_back.setClass(AddActivity.this, MainActivity.class);
                finish();
            }
        });
        addd_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
    }

    private void insert(String name, String mobilephone, String remark,int qq,String web,String address,String e_mail) {
        try {


            Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
            ContentResolver resolver = getContentResolver();
            ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
            // 向raw_contact表添加一条记录
            //此处.withValue("account_name", null)一定要加，不然会抛NullPointerException
            ContentProviderOperation operation1 = ContentProviderOperation
                    .newInsert(uri).withValue("account_name", null).build();
            operations.add(operation1);
            // 向data添加数据
            uri = Uri.parse("content://com.android.contacts/data");
            //添加姓名
            ContentProviderOperation operation2 = ContentProviderOperation
                    .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                            //withValueBackReference的第二个参数表示引用operations[0]的操作的返回id作为此值
                    .withValue("mimetype", "vnd.android.cursor.item/name")
                    .withValue("data2", name).build();
            operations.add(operation2);
            //添加手机数据
            ContentProviderOperation operation3 = ContentProviderOperation
                    .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                    .withValue("data2", "2").withValue("data1", mobilephone).build();
            operations.add(operation3);
            //添加备注
            ContentProviderOperation operation4 = ContentProviderOperation
                    .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/note")
                    .withValue("data1", remark).build();
            operations.add(operation4);
//            //添加头像
//            ContentProviderOperation operation5 = ContentProviderOperation
//                    .newInsert(uri).withValueBackReference("raw_contact_id", 0)
//                    .withValue("mimetype", "vnd.android.cursor.item/photo")
//                    .withValue("data15", uri).build();
//            operations.add(operation5);

            //添加qq
            if(qq>0) {
                ContentProviderOperation operation6 = ContentProviderOperation
                        .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                        .withValue("mimetype", "vnd.android.cursor.item/im")
                        .withValue("data1", qq).build();
                operations.add(operation6);
            }
            //添加web
            ContentProviderOperation operation7 = ContentProviderOperation
                    .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/website")
                    .withValue("data1", web).build();
            operations.add(operation7);
            //添加adress
            ContentProviderOperation operation8 = ContentProviderOperation
                    .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/postal-address_v2")
                    .withValue("data1", address).build();
            operations.add(operation8);
            //添加e-mail
            ContentProviderOperation operation9 = ContentProviderOperation
                    .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/email_v2")
                    .withValue("data1", e_mail).build();
            operations.add(operation9);



            Log.e("QQ", String.valueOf(qq));

            resolver.applyBatch("com.android.contacts", operations);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static void UpdateContactsPhoto(Context context, int contacts_id,
                                           Bitmap bitmap, int photoId) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 将Bitmap压缩成PNG编码，质量为100%存储
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] avatar = os.toByteArray();
        ContentValues values = new ContentValues();
        values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, contacts_id);
        values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
        values.put("is_super_primary", 1);
        values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, avatar);
        if (photoId > 0) {
            int row = context.getContentResolver().update(ContactsContract.Data.CONTENT_URI,
                    values, "_id=" + photoId, null);
            Log.d("tag", "row=" + row);
        } else {
            Uri row = context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
            Log.d("tag", "uri_row=" + row);
        }
    }


    //使用dialog弹出来
    private void dialog() {
        final String items[] = {"QQ", "web", "address", "e-mail"};

//        Log.i(LOG_TAG,id);

        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);  //先得到构造器
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
                        qq.setVisibility(View.VISIBLE);
                        et_show_qq.setVisibility(View.VISIBLE);
                        qq_line.setVisibility(View.VISIBLE);
                        break;
                    case "web":
                        web.setVisibility(View.VISIBLE);
                        et_show_web.setVisibility(View.VISIBLE);
                        web_line.setVisibility(View.VISIBLE);
                        break;
                    case "address":
                        address.setVisibility(View.VISIBLE);
                        et_show_address.setVisibility(View.VISIBLE);
                        address_line.setVisibility(View.VISIBLE);
                        break;
                    case "e-mail":
                        e_mail.setVisibility(View.VISIBLE);
                        et_show_e_mail.setVisibility(View.VISIBLE);
                        e_mail_line.setVisibility(View.VISIBLE);
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
                Toast.makeText(AddActivity.this, "确定", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }


    //监听返回键，避免按手机返回键导致的不断activity
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getRepeatCount() == 0) {
//            //do something...
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    //访问手机图库，并返回图片数据
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            uri_image = data.getData();
            Log.e("uri", uri_image.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri_image));
                ImageView view_image = (ImageView) findViewById(R.id.view_image);

	                /* 将Bitmap设定到ImageView */
                view_image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //监听返回键,避免按手机返回键导致的不断activity
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Intent intent_back = new Intent();
                intent_back.setClass(AddActivity.this, MainActivity.class);
                startActivity(intent_back);
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
