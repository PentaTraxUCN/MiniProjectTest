package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.*;

import controllayer.*;
import modellayer.Currency;

/**
 * Inspired by the book: Flexible, Reliable Software Henrik Bærbak Christensen:
 * Flexible, Reliable Software. Taylor and Francis Group, LLC 2010
 */

public class TestCalculationCurrencyDkk {

	static ControlPayStation ps;

	/** Fixture for pay station testing. */
	@BeforeAll
	public static void setUp() {
		ps = new ControlPayStation();
	}

	/**
	 * Entering 50 øre should make the display report 3 minutes parking time.
	 */
	@Test
	public void shouldDisplay3MinFor50Ore() throws IllegalCoinException {
		
		// Arrange
		int expectedParkingTime = 3;	// In minutes
		int coinValue = 50;
		Currency.ValidCurrency coinCurrency = Currency.ValidCurrency.DKK;
		Currency.ValidCoinType coinType = Currency.ValidCoinType.FRACTION;
		
		// Act
		ps.addPayment(coinValue, coinCurrency, coinType);
			
		// Assert
		assertEquals(expectedParkingTime, ps.readDisplay(), "Should display 3 min for 50 øre");
	}



	/** Fixture for pay station testing. */
	@AfterAll
	public static void cleanUp() {
		ps.setReady();
	}	
	
}
