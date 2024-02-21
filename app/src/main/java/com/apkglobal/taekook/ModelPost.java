package com.apkglobal.taekook;

public class ModelPost {

    String uid, name, email, dp, pTime, pTitle, pDescription, pId;

    public ModelPost() {
    }

    public ModelPost(String uid, String name, String email, String dp, String pTime, String pTitle, String pDescription, String pId) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.dp = dp;
        this.pTime = pTime;
        this.pTitle = pTitle;
        this.pDescription = pDescription;
        this.pId = pId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
