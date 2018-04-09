package com.cs2340.noexceptions.homelesshelper;


/**
 * HomelessPerson class representing an homeless person.
 */
class HomelessPerson extends User {
    int numPeople;
    private String reservedShelter;
    private boolean reserved;

    /**
     * A constructor for creating an instance of a homeless person
     * @param name The name of the homeless person
     * @param accountState True or False whether or not the user is banned
     * @param contact_info The phone number of the user
     * @param username The username
     * @param password The password
     * @param numPeople The number of people they are reserving vacancies
     * @param reservedShelter The shelter they have reserved
     * @param reserved True or False of whether or not they have a shelter reserved
     */
    public HomelessPerson(String name, boolean accountState, String contact_info, String username,
                          String password, int numPeople,
                          String reservedShelter, boolean reserved) {
        this.name = name;
        this.accountState = accountState;
        this.contact_info = contact_info;
        this.userName = username;
        this.password = password;
        accountType = "Homeless Person";
        this.numPeople = numPeople;
        this.reservedShelter = reservedShelter;
        this.reserved = reserved;
    }

    /**
     * Default constructor for HomelessPerson
     */
    public HomelessPerson() {

    }

    /**
     * This method will reserve a vacancy for a user in the given shelter
     * @param s A shelter
     * @return True or False of whether or not the shelter was reserved
     */
    boolean reserveVacancy(Shelter s) {
        boolean check;
        if (!reserved) {
            check = s.updateCapacity(numPeople);
            if (check) {
                reservedShelter = s.getName();
                reserved = true;
            }
            return check;
        }
        return false;
    }

    /**
     * A method that will return a homeless person's reserved shelter's name
     * @return A string representing the reserved shelter
     */
    CharSequence getReservedShelter() {
        return reservedShelter;
    }
    void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }
    boolean getReserved() {
        return reserved;
    }
    void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
    void setReservedShelter(String reservedShelter) {
        this.reservedShelter = reservedShelter;
    }
    int getNumPeople() {
        return numPeople;
    }

    public String toString() {
        return name + ":\n Reserved:" + reserved + "\nReserved Shelter: " + reservedShelter;
    }
}
