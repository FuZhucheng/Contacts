package com.android.administrator.contacts_fuzhucheng.utils.db;

/**
 * Created by Administrator on 2016/5/4.
 */
public class UserBean {

    private int img;
    private String title;
    private String info;
    private String item_Id;

    public void setImg(int img){
        this.img = img;
    };

    public int getImg() {
        return img;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setItem_Id(String item_Id) {
        this.item_Id = item_Id;
    }

    public String getItem_Id() {
        return item_Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
