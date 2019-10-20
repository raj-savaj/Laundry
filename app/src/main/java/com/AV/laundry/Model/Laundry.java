package com.AV.laundry.Model;

/**
 * Created by Param on 1/30/2019.
 */

public class Laundry{

    int id;
    String Name,email,mno,pwd;

    public int getId()
    {
        return id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = Name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public String getMobileNo() {
        return mno;
    }

    public void setMobileNo(String mno) {
        this.mno = mno;
    }

    public String getPassword() {
        return pwd;
    }

    public void setPassword(String pwd) {
        this.pwd = pwd;
    }

}
