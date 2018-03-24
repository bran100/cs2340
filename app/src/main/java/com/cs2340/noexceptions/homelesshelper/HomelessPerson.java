package com.cs2340.noexceptions.homelesshelper;



public class HomelessPerson extends User {
    public int numPeople;
    private String reservedShelter;
    public boolean reserved;
    public HomelessPerson(String name, boolean accountState, String contact_info, String username, String password, int numPeople, String reservedShelter, boolean reserved) {
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
    public HomelessPerson() {

    }
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
    public String getReservedShelter() {
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

    public void undoReservation() {
        reserved = false;
        reservedShelter = "";
    }
    public String toString() {
        return name + ":\n Reserved:" + reserved + "\nReserved Shelter: " + reservedShelter;
    }
}
