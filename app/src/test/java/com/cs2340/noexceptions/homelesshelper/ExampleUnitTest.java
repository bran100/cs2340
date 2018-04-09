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

    //Nenad

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
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

    //Daisha

    @Test
    public void testFindAgeNewborn() {
        Shelter s = new Shelter("", "", "10", "NEWBORNS",
                "", "", "", "");
        assertEquals("Families with newborns", s.getAge());
    }

    @Test
    public void testFindAgeCY() {
        Shelter s = new Shelter("", "", "10", "YOUNG or CHILDREN",
                "", "", "", "");
        assertEquals("Children / Young Adults", s.getAge());
    }

    @Test
    public void testFindAgeChildren() {
        Shelter s = new Shelter("", "", "10", "CHILDREN",
                "", "", "", "");
        assertEquals("Children", s.getAge());
    }

    @Test
    public void testAgeYoungAdults() {
        Shelter s = new Shelter("", "", "10", "YOUNG",
                "", "", "", "");
        assertEquals("Young Adults", s.getAge());
    }

    @Test
    public void testAgeAnyone() {
        Shelter s = new Shelter("", "", "10", "VETERANS",
                "", "", "", "");
        assertEquals("Anyone", s.getAge());
    }

    //Armand

    @Test
    public void testFindGenderAnyone() {
        Shelter s = new Shelter("", "", "10", "ANYONE",
                "", "", "", "");
        assertEquals("Male/Female", s.getGender());
    }

    @Test
    public void testFindWomen() {
        Shelter s = new Shelter("", "", "10", "WOMEN",
                "", "", "", "");
        assertEquals("Female", s.getGender());
    }

    @Test
    public void testFindGenderMen() {
        Shelter s = new Shelter("", "", "10", "MEN",
                "", "", "", "");
        assertEquals("Male", s.getGender());
    }

    @Test
    public void testFindGenderNone() {
        Shelter s = new Shelter("", "", "10", "",
                "", "", "", "");
        assertEquals("Male/Female/Unknown", s.getGender());
    }

}