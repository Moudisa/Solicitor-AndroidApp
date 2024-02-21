package com.apkglobal.taekook;

public class ModelComment {

    String cId, comment, timeStamp, uid, email, dp, name;

    public ModelComment() {
    }

    public ModelComment(String cId, String comment, String timeStamp, String uid, String email, String dp, String name) {
        this.cId = cId;
        this.comment = comment;
        this.timeStamp = timeStamp;
        this.uid = uid;
        this.email = email;
        this.dp = dp;
        this.name = name;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
