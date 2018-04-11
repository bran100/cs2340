package com.cs2340.noexceptions.homelesshelper;


/**
 * Admin class representing an Admin.
 */
class Admin extends User {
    /** Admin constructor. Creates an instance of the Admin class.
     * @param name The name of the Admin
     * @param username The username
     * @param password The password
     */
    public Admin(String name,
                 String username, String password) {
        this.name = name;
        this.accountState = true;
        this.contact_info = "";
        this.userName = username;
        this.password = password;
    }

    /**
     * Default Constructor for an Admin
     */
    public Admin() {

    }
}
