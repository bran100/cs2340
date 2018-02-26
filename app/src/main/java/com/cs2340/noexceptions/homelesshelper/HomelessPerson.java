package com.cs2340.noexceptions.homelesshelper;

/**
 * Created by nenadstanic on 2/26/18.
 */

public class HomelessPerson extends User {
    public HomelessPerson(String name, String account_state, String contact_info) {
        this.name = name;
        this.acount_state = account_state;
        this.contact_info = contact_info;
    }
}
