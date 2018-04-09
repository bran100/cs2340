package com.cs2340.noexceptions.homelesshelper;

import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    /*
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
                    } catch (Exception e) {
                        Log.d("Capacity", "changeCapacity: Not Valid Number");
                    }
                }
            } else {
                total = Integer.parseInt(capacity);
            }
        }
        this.capacity = Integer.toString(total);
    }
     */
    @Test
    public void testCapacityNASuccess() {
        Shelter s = new Shelter("", "", "N/A", "",
                "", "", "", "");
        assertEquals(Integer.toString(50), s.getCapacity());
    }
    @Test
    public void testCapacityNAFail() {
        Shelter s = new Shelter("", "", "30", "",
                "", "", "", "");
        assertNotEquals(Integer.toString(50), s.getCapacity());
    }
    @Test
    public void testSplit() {
        PowerMockito.mockStatic(Log.class);
        Shelter s = new Shelter("", "", "10 Apartments, 20 Single Bed", "",
                "", "", "", "");
        assertEquals(Integer.toString(30), s.getCapacity());
    }
    @Test
    public void testSplitFail() {
        Shelter s = new Shelter("", "", "10", "",
                "", "", "", "");
        assertEquals(Integer.toString(10), s.getCapacity());
    }
    @Test
    public void testNonIntegerException() {
        PowerMockito.mockStatic(Log.class);
        Shelter s = new Shelter("", "", " Apartment", "",
                "", "", "", "");
        assertEquals(Integer.toString(0), s.getCapacity());
    }
    @Test
    public void testNotValidNumber() {
        PowerMockito.mockStatic(Log.class);
        Shelter s = new Shelter("", "", "Rooms", "",
                "", "", "", "");
        assertEquals(Integer.toString(0), s.getCapacity());
    }
}