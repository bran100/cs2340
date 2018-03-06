package com.cs2340.noexceptions.homelesshelper;



public class Shelter {
    String id;
    String name;
    String capacity;
    String gender;
    String age;
    String longitude;
    String latitude;
    String address;
    String telephoneNumber;
    public Shelter(String id, String name, String capacity, String restrictions,
                   String longitude, String latitude, String address, String telephoneNumber) {
        this.id = id;
        this.name = name;
        this.capacity = capacity.replace("\"", "").replace(",", "\n").replace("Anyone", "");
        //this.gender = (gender.replace("/", "\n")).replace("w/", "\n");
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address.replace("\"", "");
        this.telephoneNumber = telephoneNumber;
        findGender(restrictions);
        findAge(restrictions);
    }
    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public void findGender(String restrictions) {
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
    public void findAge(String restrictions) {
        if (restrictions.toLowerCase().contains("NEWBORNS".toLowerCase())) {
            age = "Families with newborns";
        }
        else if (restrictions.toLowerCase().contains("CHILDREN".toLowerCase())) {
            age = "Children";
        }
        else if (restrictions.toLowerCase().contains("YOUNG".toLowerCase())) {
            age = "Young Adults";
        }
        else {
            age = "Anyone";
        }
    }

}
