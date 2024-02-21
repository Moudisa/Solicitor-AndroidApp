package com.apkglobal.taekook;

public class Users {

    public String name, email, contact, address, experience, cases, working, qualification, image, thumb_image, uid, onlineStatus, typingTo;

    public Users() {

    }

    public Users(String name, String email, String contact, String address, String experience, String cases,
                 String working, String qualification, String image, String thumb_image, String uid, String onlineStatus, String typingTo) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.experience = experience;
        this.cases = cases;
        this.working = working;
        this.qualification = qualification;
        this.image = image;
        this.thumb_image = thumb_image;
        this.uid = uid;
        this.onlineStatus = onlineStatus;
        this.typingTo = typingTo;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getWorking() {
        return working;
    }

    public void setWorking(String working) {
        this.working = working;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getTypingTo() {
        return typingTo;
    }

    public void setTypingTo(String typingTo) {
        this.typingTo = typingTo;
    }
}
