package com.pescod.mobliesecurity.domain;

/**
 * Created by pescod on 4/11/2016.
 */
public class ContactInfo {
    private String name;
    private String phone;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
    
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
