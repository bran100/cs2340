package com.cs2340.noexceptions.homelesshelper;



public class Admin extends User {
    public Admin(String name, boolean accountState, String contact_info, String username, String password) {
        this.name = name;
        this.accountState = accountState;
        this.contact_info = contact_info;
        this.userName = username;
        this.password = password;
        accountType = "Admin";
    }
    public Admin() {

    }
}
