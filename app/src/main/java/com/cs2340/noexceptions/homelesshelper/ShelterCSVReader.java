package com.cs2340.noexceptions.homelesshelper;


import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShelterCSVReader {
    InputStream inputStream;
    public ShelterCSVReader(InputStream is) {
        inputStream = is;
    }
    public List<String[]> readShelters() {
        List<String[]> shelters = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String shelterInfo;
            reader.readLine();
            while ((shelterInfo = reader.readLine()) != null) {
                shelterInfo = shelterInfo.replace(",,", ",N/A,");
                String[] shelter = shelterInfo.split(
                        ",(?=\\d)|(?<=\"),|,(?=-\\d)|,(?=\\\")|(?<=\\d),|,(?=\\()|, (?=F)|,(?=[FV])| ,(?=-)| ,(?=()) |,(?=\\() |,(?=N\\/A)"
                );
                if (Integer.parseInt(shelter[0]) == 9) {
                    String[] splitCapacity = shelter[2].split(",");
                    shelter = new String[] {shelter[0], shelter[1], splitCapacity[0], splitCapacity[1], shelter[3], shelter[4], shelter[5] + "," + shelter[6], shelter[7], shelter[8]};

                }
                if (Integer.parseInt(shelter[0]) == 12) {
                    shelter = new String[] {shelter[0], shelter[1], shelter[2], shelter[3], shelter[4], shelter[5], shelter[6] + "," + shelter[7], shelter[8], shelter[9]};
                }
                shelters.add(shelter);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error reading the list of shelters:\t" + ex);
        } finally {
            try {
                inputStream.close();
            } catch(IOException ex) {
                throw new RuntimeException("Error closing csv file:\t" + ex);
            }
        }
        return shelters;
    }
}
