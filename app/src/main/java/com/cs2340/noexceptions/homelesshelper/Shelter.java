package com.cs2340.noexceptions.homelesshelper;



public class Shelter {
    String id;
    String name;
    String capacity;
    String gender;
    String longitude;
    String latitude;
    String address;
    String telephoneNumber;
    public Shelter(String id, String name, String capacity, String gender,
                   String longitude, String latitude, String address, String telephoneNumber) {
        this.id = id;
        this.name = name;
        this.capacity = capacity.replace("\"", "").replace(",", "\n").replace("Anyone", "");
        this.gender = (gender.replace("/", "\n")).replace("w/", "\n");
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address.replace("\"", "");
        this.telephoneNumber = telephoneNumber;
    }
    @Override
    public String toString() {
        return "Shelter: " + name + "\n" + address + "\n" + telephoneNumber;
    }

}
