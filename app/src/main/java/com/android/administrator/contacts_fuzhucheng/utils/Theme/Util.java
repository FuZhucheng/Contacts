package com.android.administrator.contacts_fuzhucheng.utils.Theme;

import android.content.Context;

import com.android.administrator.contacts_fuzhucheng.R;
import com.android.administrator.contacts_fuzhucheng.utils.SharedPreferences.SharedPreferrenceHelper;

/**
 * Created by Administrator on 2016/5/15.
 */
public class Util {
    public static int getAppTheme(Context context){
        String value = SharedPreferrenceHelper.gettheme(context);
        switch (Integer.valueOf(value)){
            case 1:
                return R.style.SwitchTheme1;
            case 2:
                return R.style.SwitchTheme2;
            default:
                return R.style.SwitchTheme2;
        }
    }


    public static void switchAppTheme(Context context){
        String value = SharedPreferrenceHelper.gettheme(context);
        switch (Integer.valueOf(value)){
            case 1:
                SharedPreferrenceHelper.settheme(context,"2");
                break;
            case 2:
                SharedPreferrenceHelper.settheme(context,"1");
                break;
            default:
                SharedPreferrenceHelper.settheme(context,"1");
                break;
        }
    }
}
