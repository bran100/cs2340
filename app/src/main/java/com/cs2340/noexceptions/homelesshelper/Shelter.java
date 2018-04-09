package com.cs2340.noexceptions.homelesshelper;



import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Shelter class holding information for the shelter
 */
public class Shelter {
    private String id;
    private String name;
    private String capacity;
    private String gender;
    private String age;
    private String longitude;
    private String latitude;
    private String address;
    private String telephoneNumber;
    private String restrictions;

    /**
     * The shelter constructor
     * @param id The shelter's id
     * @param name The shelter's name
     * @param capacity The shelter's capacity or a default of 50
     * @param restrictions Special restrictions on age or gender
     * @param longitude Longitude of the shelter
     * @param latitude Latitude of the shelter
     * @param address The shelter's address
     * @param telephoneNumber The shelter's telephone number
     */
    public Shelter(String id, String name, String capacity, String restrictions,
                   String longitude, String latitude, String address, String telephoneNumber) {
        this.id = id;
        this.name = name;
        String temp = capacity.replace("\"", "").replace(",", "\n").replace("Anyone", "");
        changeCapacity(temp);
        //this.gender = (gender.replace("/", "\n")).replace("w/", "\n");
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address.replace("\"", "");
        this.telephoneNumber = telephoneNumber;
        this.restrictions = restrictions;
        findGender(restrictions);
        findAge(restrictions);
    }

    /**
     * Default constructor for the shelter
     */
    public Shelter() {
    }
    @Override
    public String toString() {
        return name;
    }

    private void findGender(String restrictions) {
        if (restrictions.toLowerCase().contains("ANYONE".toLowerCase())) {
            gender = "Male/Female";
        }
        else if (restrictions.toLowerCase().contains("WOMEN".toLowerCase())) {
            gender = "Female";
        }
        else if (restrictions.toLowerCase().contains("MEN".toLowerCase())) {
            gender = "Male";
        }
        else {
            gender = "Male/Female/Unknown";
        }
    }

    private void findAge(String restrictions) {
        if (restrictions.toLowerCase().contains("NEWBORNS".toLowerCase())) {
            age = "Families with newborns";
        }
        else if (restrictions.toLowerCase().contains("CHILDREN".toLowerCase())) {
            if (restrictions.toLowerCase().contains("YOUNG".toLowerCase())) {
                age = "Children / Young Adults";
            } else {
                age = "Children";
            }
        }
        else if (restrictions.toLowerCase().contains("YOUNG".toLowerCase())) {
            age = "Young Adults";
        }
        else {
            age = "Anyone";
        }
    }

    private void changeCapacity(String capacity) {
        int total = 0;
        if (capacity.contains("N/A")) {
            total = 50;
        } else {
            if (capacity.contains(" ")) {
                String[] capacityNum = capacity.split(" ");
                for (String cn : capacityNum) {
                    try {
                        int num = Integer.parseInt(cn);
                        total += num;
                    } catch (NumberFormatException e) {
                        Log.d("Capacity", "changeCapacity: Not Valid Number");
                    }
                }
            } else {
                try {
                    total = Integer.parseInt(capacity);
                } catch (NumberFormatException nfe) {
                    Log.d("Capacity", "changeCapacity: Not Valid Number");
                }
            }
        }
        this.capacity = Integer.toString(total);
    }
    boolean updateCapacity(int numPeople) {
        if (((Integer.parseInt(capacity)) - numPeople) < (0)) {
            return false;
        } else {
            int cap = Integer.parseInt(capacity) - numPeople;
            capacity = Integer.toString(cap);
            DatabaseReference database = FirebaseDatabase.getInstance()
                    .getReference().child("shelters").child(id);
            Map<String, Object> taskMap = new HashMap<>();
            taskMap.put("capacity", capacity);
            database.updateChildren(taskMap);
            return true;
        }
    }

    /**
     * Takes the given restrictions input and parses it for the gender and age information
     */
    public void updateAllNeeded() {
        findGender(restrictions);
        findAge(restrictions);
        address = address.replace("\"", "");
        capacity = capacity.replace("\"", "").replace(",",
                "\n").replace("Anyone", "");
        changeCapacity(capacity);
    }


    String getId() {
        return id;
    }
    String getName() {
        return name;
    }
    CharSequence getCapacity() {
    return capacity;
    }
    String getGender() {
        return gender;
    }
    String getAge() {
        return age;
    }
    String getLongitude() {
        return longitude;
    }
    String  getLatitude() {
        return latitude;
    }
    CharSequence getAddress() {
        return address;
    }
    String getTelephoneNumber() {
        return telephoneNumber;
    }
    String getRestrictions() {
        return restrictions;
    }
}
