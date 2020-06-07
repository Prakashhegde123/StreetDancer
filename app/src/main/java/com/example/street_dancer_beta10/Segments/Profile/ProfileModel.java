package com.example.street_dancer_beta10.Segments.Profile;

public class ProfileModel {
    String dob;
    String gender;
    String mail;
    String name;
    String pass;
    String phone;
    String repass;

    public ProfileModel() {
    }

    public ProfileModel(String dob, String gender, String mail, String name, String pass, String phone, String repass) {
        this.dob = dob;
        this.gender = gender;
        this.mail = mail;
        this.name = name;
        this.pass = pass;
        this.phone = phone;
        this.repass = repass;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRepass() {
        return repass;
    }

    public void setRepass(String repass) {
        this.repass = repass;
    }
}
