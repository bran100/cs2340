package com.cs2340.noexceptions.homelesshelper;


/**
 * HomelessPerson class representing an homeless person.
 */
class HomelessPerson extends User {
    private int numPeople;
    private String reservedShelter;
    private boolean reserved;

    /**
     * A constructor for creating an instance of a homeless person
     * @param name The name of the homeless person
     * @param username The username
     * @param password The password
     */
    HomelessPerson(String name, String username,
                   String password) {
        this.name = name;
        this.accountState = true;
        this.contact_info = "";
        this.userName = username;
        this.password = password;
        this.numPeople = 0;
        this.reservedShelter = "";
        this.reserved = false;
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
    public boolean reserveVacancy(Shelter s) {
        boolean check;
        if (!reserved) {
            check = s.updateCapacity(numPeople);
            if (check) {
                reservedShelter = s.getName();
                reserved = true;
            }
            return !check;
        }
        return true;
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
    private void setReserved() {
        this.reserved = false;
    }
    private void setReservedShelter() {
        this.reservedShelter = "";
    }
    int getNumPeople() {
        return numPeople;
    }

    public String toString() {
        return name + ":\n Reserved:" + reserved + "\nReserved Shelter: " + reservedShelter;
    }
    boolean[] shelterInfoBool(Shelter s) {
        boolean[] temp = new boolean[2];
        temp[0] = reserveVacancy(s);
        temp[1] = reserved;
        return temp;
    }
    void homelessInfo() {
        setReserved();
        setReservedShelter();
    }
    String[] getInfo() {
        return new String[] {reservedShelter, Integer.toString(numPeople)};
    }
}
