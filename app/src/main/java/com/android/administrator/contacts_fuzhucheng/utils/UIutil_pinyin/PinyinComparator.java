package com.android.administrator.contacts_fuzhucheng.utils.UIutil_pinyin;

import com.android.administrator.contacts_fuzhucheng.ui.ContactsBean;

import java.util.Comparator;

public class PinyinComparator implements Comparator<ContactsBean> {

    public int compare(ContactsBean o1, ContactsBean o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }

}
