/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupproject;

import groupproject.classes.Room;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author qimingchen
 */
public class RoomTest {
    
    public RoomTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setRoomType method, of class Room.
     */
    @Test
    public void testSetRoomType() {
        System.out.println("setRoomType");
        String roomType = "Single";
        Room instance = new Room();
        instance.setRoomType(roomType);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setRoomPrice method, of class Room.
     */
    @Test
    public void testSetRoomPrice() {
        System.out.println("setRoomPrice");
        int roomPrice = 123;
        Room instance = new Room();
        instance.setRoomPrice(roomPrice);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setRoomNumber method, of class Room.
     */
    @Test
    public void testSetRoomNumber() {
        System.out.println("setRoomNumber");
        String roomNumber = "123";
        Room instance = new Room();
        instance.setRoomNumber(roomNumber);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of setRoomAvailable method, of class Room.
     */
    @Test
    public void testSetRoomAvailable() {
        System.out.println("setRoomAvailable");
        boolean roomAvailable = false;
        Room instance = new Room();
        instance.setRoomAvailable(roomAvailable);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getRoomType method, of class Room.
     */
    @Test
    public void testGetRoomType() {
        System.out.println("getRoomType");
        Room instance = new Room();
        String expResult = "Single";
        String result = instance.getRoomType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getRoomPrice method, of class Room.
     */
    @Test
    public void testGetRoomPrice() {
        System.out.println("getRoomPrice");
        Room instance = new Room();
        int expResult = 100;
        int result = instance.getRoomPrice();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getRoomNumber method, of class Room.
     */
    @Test
    public void testGetRoomNumber() {
        System.out.println("getRoomNumber");
        Room instance = new Room();
        String expResult = "123";
        String result = instance.getRoomNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getRoomAvailble method, of class Room.
     */
    @Test
    public void testGetRoomAvailble() {
        System.out.println("getRoomAvailble");
        Room instance = new Room();
        boolean expResult = false;
        boolean result = instance.getRoomAvailable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
    
}
