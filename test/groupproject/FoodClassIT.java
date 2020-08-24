/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupproject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import groupproject.classes.FoodCustomer;

/**
 *
 * @author jaspe
 */
public class FoodClassIT {
    
    public FoodClassIT() {
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
     * Test of getFoodType method, of class FoodClass.
     */
    @Test
    public void testGetFoodType() {
        System.out.println("getFoodType");
        FoodCustomer instance = new FoodCustomer("Food", "Rice", 3);
        String expResult = "Food";
        String result = instance.getFoodType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getQuantity method, of class FoodClass.
     */
    @Test
    public void testGetQuantity() {
        System.out.println("getQuantity");
        FoodCustomer instance = new FoodCustomer("Food", "Rice", 3, 5);
        int expResult = 3;
        int result = instance.getQuantity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFoodName method, of class FoodClass.
     */
    @Test
    public void testGetFoodName() {
        System.out.println("getFoodName");
        FoodCustomer instance = new FoodCustomer("Food", "Rice", 3, 5);
        String expResult = "Rice";
        String result = instance.getFoodName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getFoodPrices method, of class FoodClass.
     */
    @Test
    public void testGetFoodPrices() {
        System.out.println("getFoodPrices");
        FoodCustomer instance = new FoodCustomer("Food", "Rice", 3, 5);
        int expResult = 5;
        int result = instance.getFoodPrices();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTotalPrices method, of class FoodClass.
     */
    @Test
    public void testGetTotalPrices() {
        System.out.println("getTotalPrices");
        FoodCustomer instance = new FoodCustomer("Food", "Rice", 3, 5);
        int expResult = 15;
        int result = instance.getTotalPrices();
        assertEquals(expResult, result);
    }

    /**
     * Test of setQuantity method, of class FoodClass.
     */
    @Test
    public void testSetQuantity() {
        System.out.println("setQuantity");
        int num = 4;
        FoodCustomer instance = new FoodCustomer();
        instance.setQuantity(num);
        int expResult = 4;
        int result = instance.getQuantity();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of listLength method, of class FoodClass.
     */
    @Test
    public void testListLength() {
        System.out.println("listLength");
        FoodCustomer instance = new FoodCustomer();
        int expResult = 8;
        int result = instance.listLength();
        assertEquals(expResult, result);
    }

    /**
     * Test of dType method, of class FoodClass.
     */
    @Test
    public void testDType() {
        System.out.println("dType");
        int i = 0;
        FoodCustomer instance = new FoodCustomer();
        String expResult = "Food";
        String result = instance.dType(i);
        assertEquals(expResult, result);
    }

    /**
     * Test of dName method, of class FoodClass.
     */
    @Test
    public void testDName() {
        System.out.println("dName");
        int i = 0;
        FoodCustomer instance = new FoodCustomer();
        String expResult = "Chicken Wings";
        String result = instance.dName(i);
        assertEquals(expResult, result);
    }

    /**
     * Test of dPrice method, of class FoodClass.
     */
    @Test
    public void testDPrice() {
        System.out.println("dPrice");
        int i = 0;
        FoodCustomer instance = new FoodCustomer();
        int expResult = 4;
        int result = instance.dPrice(i);
        assertEquals(expResult, result);
    }

    /**
     * Test of main method, of class FoodClass.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        FoodCustomer.main(args);
    }
    
}
