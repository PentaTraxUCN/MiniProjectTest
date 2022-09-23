package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import org.junit.*;
import java.time.LocalDate;

//import controllayer.ControlPayStation;
//import controllayer.Currency;
//import controllayer.IPayStation;
//import controllayer.IReceipt;
//import controllayer.IllegalCoinException;

import databaselayer.*;
import modellayer.*;
import controllayer.*;

//import static org.junit.Assert.*;

/**
 * Inspired by the book: Flexible, Reliable Software Henrik B�rbak Christensen:
 * Flexible, Reliable Software. Taylor and Francis Group, LLC 2010
 */

public class TestDatabaseAccess {
	
	static DBConnection con = null;
	static PBuy tempPBuy;

	/** Fixture for pay station testing. */
	@Before
	public static void setUp() {
		con = DBConnection.getInstance();
	}
	
	
	@Test
	public void wasConnected() {
		//assertNotNull(con, "Connected - connection cannot be null");
		
		DBConnection.closeConnection();
		boolean wasNullified = DBConnection.instanceIsNull();
		assertTrue(wasNullified, "Disconnected - instance set to null");
		
		con = DBConnection.getInstance();
		boolean connectionIsOpen = DBConnection.getOpenStatus();
		assertTrue(connectionIsOpen);	
	}
	
	
	@Test
	public void wasInsertedBuy() {
		
		// Arrange
		LocalDate timeNow = java.time.LocalDate.now();
		double payedCentAmount = 100;
		
		tempPBuy = new PBuy();
		
		PPayStation pStat = new PPayStation(1, "P-423E");
		pStat.setAmount(payedCentAmount);
		tempPBuy.setAssociatedPaystation(pStat);
		tempPBuy.setBuyTime(timeNow);
		
		DatabasePBuy dbPbuy = new DatabasePBuy();
		
		// Act
		int key = 0; // Updated by debugging team
		try {
			key = dbPbuy.insertParkingBuy(tempPBuy);
		} catch (DatabaseLayerException e) {
			e.printStackTrace();
		}
		
		// Assert
		assertEquals(true, key > 0, "Able to insert buy: ");
		
	}	
	
	
	@Test
	public void wasRetrievedPriceDatabaselayer() { // Updated by debugging team
		// Arrange
		PPrice foundPrice = null;
		int pZoneId = 2;
		DatabasePPrice dbPrice = new DatabasePPrice();

		// Act
		try {
			foundPrice = dbPrice.getPriceByZoneId(pZoneId);
		} catch (DatabaseLayerException e) {
			e.printStackTrace();
		}
		
		// Assert
		assertEquals(true, foundPrice != null, "Able to get the parking price from db: ");
		
	}
	
	
	@Test
	public void wasRetrievedPriceControllayer() { // Updated by debugging team

		// Arrange
		PPrice foundPrice = null;
		ControlPrice cPrice = new ControlPrice();
		int pZoneId = 2;
		
		// Act
		try {
			foundPrice = cPrice.getPriceRemote(pZoneId);
		} catch (DatabaseLayerException e) {
			e.printStackTrace();
		}
		
		// Assert
		assertEquals(true, foundPrice != null, "Able to get parking price through controllayer: ");
		
	}	
	
	
	/** Fixture for pay station testing. */
	@AfterAll
	public static void cleanUp() {
		DBConnection.closeConnection();
	}	
	
	@AfterClass
	public static void cleanUpWhenFinish() {
		// 		
		// Arrange
		DatabasePBuy dbPbuy = new DatabasePBuy();
		int numDeleted = 0;
		
		// Act
		try {
			numDeleted = dbPbuy.deleteParkingBuy(tempPBuy);
		} catch(Exception ex) { 
			System.out.println("Error: " + ex.getMessage());
		} finally {
			DBConnection.closeConnection();
		}
	
		// Assert
		assertEquals(1, numDeleted, "One row deleted");
	}	

}
