package com.android.administrator.contacts_fuzhucheng;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2016/4/18.
 */
public class ContactColumn implements BaseColumns{
    public ContactColumn(){

    }
    //列名
    public static final String NAME="name";
    public static final String MOBILENUM="mobileNumeber";
    public static final String REMARK="remark";

    //列索引值
    public static final int _ID_COLUMN=0;
    public static final int NAME_COLUMN=1;
    public static final int MOBILENUM_COLUMN=2;

    //查询结果
    public static final String[] PROJECTTION={
            _ID,
            NAME,
            MOBILENUM,
            REMARK
    };

}
