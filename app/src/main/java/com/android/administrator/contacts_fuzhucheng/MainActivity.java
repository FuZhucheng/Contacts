package com.android.administrator.contacts_fuzhucheng;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        android.view.View.OnClickListener {

    private ViewPager mViewPager;// 用来放置界面切换
    private PagerAdapter mPagerAdapter;// 初始化View适配器

    List<Fragment> fragmentList;//页面集合
    //四个fragment页面
    contacts_fragment contacts_fragment;
    location_sharefragment location_sharefragment;
    association_activity_fragment association_activity_fragment;
    association_show_fragment association_show_fragment;

    //定义下面导航栏的textview
    private TextView one;
    private TextView two;
    private TextView three;
    private TextView four;
    //自定义菜单
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
        initViewPage();
        initEvent();
        //初始化两个菜单布局
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(mNavigationView);


    }

    private void initEvent() {
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

        fragmentList=new ArrayList<Fragment>();
        contacts_fragment=new contacts_fragment();
        location_sharefragment=new location_sharefragment();
        association_activity_fragment=new association_activity_fragment();
        association_show_fragment=new association_show_fragment();

        fragmentList.add(contacts_fragment);
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

    }

//    //添加fragment到activity函数中
//    private void addFragment(Fragment fragment, String tag) {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(R.id.main_content, fragment, tag);
//        transaction.commit();
//    }

    //初始化左边的菜单
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {


                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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

        one.setBackgroundColor(getResources().getColor(R.color.white));
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
    class FragAdapter extends FragmentStatePagerAdapter
    {

        public FragAdapter(FragmentManager fm)
        {
            super(fm);
        }

        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        public int getCount() {
            return fragmentList.size();
        }


    }

}
