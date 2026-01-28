package com.wip;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainClassTest {
	
	MainClass mc;

	@BeforeClass
	public static void setUpClass() {
		System.out.println("Setting up class resources...");
	}
	
	@Before
	public void setUp() {
		mc = new MainClass();
	}

	
	@Test
    public void testAdd() {
       
        int expectedResult = 15;

        // Act
        int actualResult = mc.add(10, 5);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
	
	@After
	public void tearDown() {
		mc = null;
	}
	
	@AfterClass
	public static void tearDownClass() {
		System.out.println("Cleaning up class resources...");
	}
	

}
