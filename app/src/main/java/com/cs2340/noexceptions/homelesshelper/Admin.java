package com.cs2340.noexceptions.homelesshelper;


/**
 * Admin class representing an Admin.
 */
class Admin extends User {
    /** Admin constructor. Creates an instance of the Admin class.
     * @param name The name of the Admin
     * @param accountState True or False if the account is banned or not
     * @param contact_info The contact information (phone number)
     * @param username The username
     * @param password The password
     */
    public Admin(String name, boolean accountState,
                 String contact_info, String username, String password) {
        this.name = name;
        this.accountState = accountState;
        this.contact_info = contact_info;
        this.userName = username;
        this.password = password;
        accountType = "Admin";
    }

    /**
     * Default Constructor for an Admin
     */
    public Admin() {

    }
}
