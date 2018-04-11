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
     * @param capacity The shelter's capacity or a default of 50
     * @param restrictions Special restrictions on age or gender
     */
    public Shelter(String capacity, String restrictions) {
        this.id = "";
        this.name = "";
        String temp = capacity.replace("\"", "");
        String temp2 = temp.replace(",", "\n");
        String temp3 = temp2.replace("Anyone", "");
        changeCapacity(temp3);
        this.longitude = "";
        this.latitude = "";
        this.address = "".replace("\"", "");
        this.telephoneNumber = "";
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
        String restrictionsLower = restrictions.toLowerCase();
        if (restrictionsLower.contains("ANYONE".toLowerCase())) {
            gender = "Male/Female";
        }
        else if (restrictionsLower.contains("WOMEN".toLowerCase())) {
            gender = "Female";
        }
        else if (restrictionsLower.contains("MEN".toLowerCase())) {
            gender = "Male";
        }
        else {
            gender = "Male/Female/Unknown";
        }
    }

    private void findAge(String restrictions) {
        String restrictionsLower = restrictions.toLowerCase();
        if (restrictionsLower.contains("NEWBORNS".toLowerCase())) {
            age = "Families with newborns";
        }
        else if (restrictionsLower.contains("CHILDREN".toLowerCase())) {
            if (restrictionsLower.contains("YOUNG".toLowerCase())) {
                age = "Children / Young Adults";
            } else {
                age = "Children";
            }
        }
        else if (restrictionsLower.contains("YOUNG".toLowerCase())) {
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
            FirebaseDatabase fireBase = FirebaseDatabase.getInstance();
            DatabaseReference databaseMain = fireBase.getReference();
            DatabaseReference databaseShelters = databaseMain.child("shelters");
            DatabaseReference database = databaseShelters.child(id);
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
        String temp = capacity.replace("\"", "");
        String temp2 = temp.replace(",", "\n");
        capacity = temp2.replace("Anyone", "");
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
    String getRestrictions() {return restrictions;}
    String[] getEditInfo() {
        return new String[]{name,gender,capacity,longitude,latitude,address,telephoneNumber,age,id};
    }
    String[] shelterMapsInfo() {
        updateAllNeeded();
        return new String[]{latitude,longitude,name,telephoneNumber};
    }
}
