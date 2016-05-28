package com.android.administrator.contacts_fuzhucheng.ui;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by cheng on 2016/5/5.
 */
public class ContactsBean implements Serializable {

    private String contactId;   // 联系人Id
    private String displayName; // 联系人
    private String phoneNumber;  // 手机号码
    private Bitmap photo;  //联系人头像
    private String sortLetters;  //显示数据拼音的首字母
    private String remark;//备注
    private String qq;
    private String web;
    private String address;
    private String e_mail;

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }





    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getContactId() {
        return contactId;

    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



}
