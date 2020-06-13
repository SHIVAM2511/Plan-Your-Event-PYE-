package com.example.pye.Model;

public class UserModel {
    private String uid,name,address,phone,password,email;

    public UserModel() {
    }

    public UserModel(String uid, String name, String address, String phone, String password,String email) {
        this.uid=uid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.password=password;
        this.email=email;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
