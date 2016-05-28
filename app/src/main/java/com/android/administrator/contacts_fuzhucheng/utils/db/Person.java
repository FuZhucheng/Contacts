package com.android.administrator.contacts_fuzhucheng.utils.db;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Person {
    private Integer _id;
    private String name;
    private Integer _mobilephone;
    private String remark;


    public Integer get_id() {
        return _id;
    }
    public void set_id(Integer _id) {
        this._id = _id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getMbilephone(){
        return _mobilephone;
    }
    public void setMobilephone(Integer _mobilephone) {
        this._mobilephone = _mobilephone;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
