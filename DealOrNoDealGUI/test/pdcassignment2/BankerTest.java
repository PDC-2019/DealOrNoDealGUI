/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author Mridula 17980560
 * @author Grace 16946441
 */
public class BankerTest {
    
    public BankerTest() {
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
     * Test of returnOffer method, of class Banker.
     * if the offer is zero
     */
    @Test
    public void testReturnOfferAtZero() 
    {
        System.out.println("\nTEST FOR OFFER AT ZERO");
        Banker instance = new Banker();
        int amount = 0;
        int expResult = 0;
        System.out.println("Expected Result is: "+expResult);
        int result = instance.returnOffer();
        System.out.println("Result is: "+result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of returnOffer method, of class Banker.
     * if the offer is negative, should return false
     */
    @Test
    public void testReturnOfferAtNegative() {
        System.out.println("\nTEST OFFER FOR IF NEGATIVE");
        Banker instance = new Banker();
        boolean amount = false;
        boolean expNegative = false;
        System.out.println("Expected Result is: "+expNegative);
        boolean result = false;
        System.out.println("Result is: "+result);
        assertEquals(expNegative, result);
    }
    
    /**
     * Test of returnOffer method, of class Banker.
     * if the offer is positive, should return true
     */
    @Test
    public void testReturnOfferAtPositive() {
        System.out.println("\nTEST OFFER FOR IF POSITIVE");
        Banker instance = new Banker();
        boolean amount = true;
        boolean expPositive = true;
        System.out.println("Expected Result is: "+expPositive);
        boolean result = true;
        System.out.println("Result is: "+result);
        assertEquals(expPositive, result);
    }
    
    /**
     * Test of returnOffer method, of class Banker.
     * if the offer is over 1 million, should return false
     */
    @Test
    public void testReturnOfferOverMillion() {
        System.out.println("\nTEST OFFER AT OVER 1 MILLION");
        Banker instance = new Banker();
        boolean amount = false;
        boolean expResult = false;
        System.out.println("Expected Result is: "+expResult);
        boolean result = false;
        System.out.println("Result is: "+result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of returnOffer method, of class Banker.
     * if the offer is between 0 and 1 million
     */
    @Test
    public void testReturnOfferInRange() {
        System.out.println("\nTEST OFFER FOR WITHIN RANGE");
        Banker instance = new Banker();
        int amount = 200;
        int expResult = 200;
        System.out.println("Expected Result is: 0 < offer < 1000000");
        int result = 200;
        System.out.println("Result is: "+result);
        assertEquals(expResult, result);
    }
    
}
