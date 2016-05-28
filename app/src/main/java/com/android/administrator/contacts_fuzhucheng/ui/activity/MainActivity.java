package com.android.administrator.contacts_fuzhucheng.ui.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.administrator.contacts_fuzhucheng.R;
import com.android.administrator.contacts_fuzhucheng.ui.ContactsBean;
import com.android.administrator.contacts_fuzhucheng.ui.fragment.ContactsFragment;
import com.android.administrator.contacts_fuzhucheng.ui.fragment.Association_activityFragment;
import com.android.administrator.contacts_fuzhucheng.ui.fragment.Association_showFragment;
import com.android.administrator.contacts_fuzhucheng.ui.fragment.Location_shareFragment;
import com.android.administrator.contacts_fuzhucheng.utils.Theme.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        android.view.View.OnClickListener {

    private long exitTime = 0;//监听返回两次退出

//    private int mThemeId = -1; // 皮肤主题ID，默认-1 不处理

    private int theme = 0;
    private Util Utils;

    private ViewPager mViewPager;// 用来放置界面切换
    private PagerAdapter mPagerAdapter;// 初始化View适配器
    private Toolbar toolbar;

    List<Fragment> fragmentList;//页面集合
    //四个fragment页面
    private ContactsFragment contactsFragment;
    private Location_shareFragment location_sharefragment;
    private Association_activityFragment association_activity_fragment;
    private Association_showFragment association_show_fragment;

    //定义下面导航栏的textview
    private TextView one;
    private TextView two;
    private TextView three;
    private TextView four;
    //自定义菜单     左边的菜单！！要这两结合起来用
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //        if(savedInstanceState==null){
//            theme=Utils.getAppTheme(this);
//        }else{
//            theme=savedInstanceState.getInt("theme");
//        }
//        setTheme(theme);


        initview();
        initViewPage();
        initListener();

        //初始化两个菜单布局
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);

        //初始化ToolBar
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(mNavigationView);


    }


    private void initListener() {
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //ViewPage左右滑动时

            public void onPageSelected(int arg0) {
                int currentItem = mViewPager.getCurrentItem();
                switch (currentItem) {
                    case 0:
                        resetImg(1);
//                        button_Contacts.setImageResource(R.drawable.image_backgroud_button);
                        break;
                    case 1:
                        resetImg(2);
//                        button_location_share.setImageResource(R.drawable.image_background);
                        break;
                    case 2:
                        resetImg(3);
//                        button_association_activity.setImageResource(R.drawable.image_background);
                        break;
                    case 3:
                        resetImg(4);
//                        button_association_show.setImageResource(R.drawable.image_background);
                        break;
                    default:
                        break;
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void initViewPage() {
        mViewPager = (ViewPager) findViewById(R.id.viewpage);

        fragmentList = new ArrayList<>();
        contactsFragment = new ContactsFragment();
        location_sharefragment = new Location_shareFragment();
        association_activity_fragment = new Association_activityFragment();
        association_show_fragment = new Association_showFragment();

        fragmentList.add(contactsFragment);
        fragmentList.add(location_sharefragment);
        fragmentList.add(association_activity_fragment);
        fragmentList.add(association_show_fragment);

        mViewPager.setAdapter(new FragAdapter(getSupportFragmentManager()));
//        mViewPager.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));

    }


    private void initview() {
        mViewPager = (ViewPager) findViewById(R.id.viewpage);
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        four = (TextView) findViewById(R.id.four);
        Utils = new Util();

    }

    //    //添加fragment到activity函数中
//    private void addFragment(Fragment fragment, String tag) {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(R.id.main_content, fragment, tag);
//        transaction.commit();
//    }
//使用dialog弹出来
    private void dialog2() {
        final String items[] = {"蓝色主题", "灰色主题", "紫色主题", "小麦色主题"};
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        //builder.setMessage("是否确认退出?"); //设置内容
        builder.setIcon(R.drawable.image_contacts);//设置图标，图片id即可
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (items[which]) {

                    case "蓝色主题":
//                    onTheme(R.style.SwitchTheme1);
                        contactsFragment.setColor_blue();
                        toolbar.setBackgroundColor(Color.parseColor("#102b6a"));
//                        one.setBackgroundColor(Color.parseColor("#102b6a"));
//                        two.setBackgroundColor(Color.parseColor("#102b6a"));
//                        three.setBackgroundColor(Color.parseColor("#102b6a"));
//                        four.setBackgroundColor(Color.parseColor("#102b6a"));

                        break;
                    case "灰色主题":
//                    onTheme(android.R.style.Theme_Black);
                        contactsFragment.setColor_gray();
                        toolbar.setBackgroundColor(Color.parseColor("#77787b"));
                        break;
                    case "紫色主题":
                        toolbar.setBackgroundColor(Color.parseColor("#3F51B5"));
                        break;
                    case "小麦色主题":
                        toolbar.setBackgroundColor(Color.parseColor("#c37e00"));
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
                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }


    //初始化左边的菜单
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {


                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);

                        switch (menuItem.getItemId()) {//得到被点击的item的itemId
                            case R.id.import_contacts://对应的ID就是在add方法中所设定的Id
                                Toast.makeText(MainActivity.this, "导入联系人", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.nav_gallery:
                                Toast.makeText(MainActivity.this, "关于", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.theme:
//                                Toast.makeText(MainActivity.this, "意见", Toast.LENGTH_LONG).show();
                                dialog2();
                                break;
                            case R.id.setting:
                                Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    //初始化右边的菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
//            int id = item.getItemId();
//
//            //noinspection SimplifiableIfStatement
//            if (id == R.id.action_settings) {
//                Utils.switchAppTheme(this);
//                return true;
//            }
//            if(id==R.id.contacts_person){
//                Utils.switchAppTheme(this);
//                reload();
//                return true;
//            }
//            return super.onOptionsItemSelected(item);
//
//
//        }
//        return super.onOptionsItemSelected(item);
//
        }
        return true;
    }


    //点击切换和效果
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.one:
                mViewPager.setCurrentItem(0);
                resetImg(1);
//                button_Contacts.setImageResource(R.drawable.image_backgroud_button);
                break;
            case R.id.two:
                mViewPager.setCurrentItem(1);
                resetImg(2);
//                button_location_share.setImageResource(R.drawable.image_backgroud_button);
                break;
            case R.id.three:
                mViewPager.setCurrentItem(2);
                resetImg(3);
//                button_association_activity.setImageResource(R.drawable.image_backgroud_button);
                break;
            case R.id.four:
                mViewPager.setCurrentItem(3);
                resetImg(4);
//                button_association_show.setImageResource(R.drawable.image_backgroud_button);
                break;
            default:
                break;
        }
    }

    //把图片变暗，以到达滑动效果
    private void resetImg(int num) {
//        button_Contacts.setImageResource(R.drawable.image_backgroud_button);
//        button_location_share.setImageResource(R.drawable.image_background);
//        button_association_activity.setImageResource(R.drawable.image_background);
//        button_association_show.setImageResource(R.drawable.image_background);

        one.setBackgroundColor(getResources().getColor(R.color.white));     //切换变回白色
        one.setTextColor(getResources().getColor(R.color.gray));
        two.setBackgroundColor(getResources().getColor(R.color.white));
        two.setTextColor(getResources().getColor(R.color.gray));
        three.setBackgroundColor(getResources().getColor(R.color.white));
        three.setTextColor(getResources().getColor(R.color.gray));
        four.setBackgroundColor(getResources().getColor(R.color.white));
        four.setTextColor(getResources().getColor(R.color.gray));

        System.out.println(num);
        switch (num) {
            case 1:
                one.setBackgroundColor(getResources().getColor(R.color.gray));
                one.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                two.setBackgroundColor(getResources().getColor(R.color.gray));
                two.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 3:
                three.setBackgroundColor(getResources().getColor(R.color.gray));
                three.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 4:
                four.setBackgroundColor(getResources().getColor(R.color.gray));
                four.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
        }
    }

    //使用内部类设置fragment适配器
    class FragAdapter extends FragmentStatePagerAdapter {

        public FragAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        public int getCount() {
            return fragmentList.size();
        }


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


//    @Override
//    protected void onResume() {
////        Log.d(TAG, "onResume");
//        super.onResume();
//        if(theme==Utils.getAppTheme(this)){
//
//        }else{
//            reload();
//        }
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("theme", theme);
    }

//    public void reload() {
//        Intent intent = getIntent();
//        overridePendingTransition(0, 0);//不设置进入退出动画
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        finish();
//
//        overridePendingTransition(0, 0);
//        startActivity(intent);
//    }


}

