package com.apkglobal.taekook;

public class Articles {

    public String name, pCategory, pDescription, uid, dp, pTime, pTitle, pId;

    public Articles(){

    }

    public Articles(String name, String pCategory, String pDescription, String uid, String dp, String pTime, String pTitle, String pId) {
        this.name = name;
        this.pCategory = pCategory;
        this.pDescription = pDescription;
        this.uid = uid;
        this.dp = dp;
        this.pTime = pTime;
        this.pTitle = pTitle;
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
