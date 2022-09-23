package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import controllayer.*;
import modellayer.*;

/**
 * Inspired by the book: Flexible, Reliable Software Henrik Bærbak Christensen:
 * Flexible, Reliable Software. Taylor and Francis Group, LLC 2010
 */

public class TestCalculationCurrencyMixed {

	static ControlPayStation ps;

	/** Fixture for pay station testing. */
	@BeforeAll
	public static void setUp() {
		ps = new ControlPayStation();
	}

	/**
	 * Entering 1 cent and 50 øre should make the display report 4 minutes parking time.
	 */
	@Test
	public void shouldDisplay4MinFor1CentAnd1Ore() throws IllegalCoinException {
		// Arrange
		
		// Act

		// Assert
		assertEquals(0, 1, "Dummy");		
	}

	
	/** Fixture for pay station testing. */
	@AfterAll
	public static void cleanUp() {
		ps.setReady();
	}
	
}
