package com.cs2340.noexceptions.homelesshelper;


/**
 * The user superclass for a general user
 */
public class User {
    String userName;
    String password;
    String name;
    boolean accountState;
    String contact_info;
    String accountType;

    String getUserName() {
        return userName;
    }
    String getPassword() {
        return password;
    }
    String getName() {
        return name;
    }
    boolean getAccountState() {
        return accountState;
    }
    String getContact_info() {
        return contact_info;
    }
    String getAccountType() {
        return accountType;
    }
}
