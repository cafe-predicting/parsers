package cafe.data.parser.test;

import java.time.LocalDateTime;

import org.junit.Test;

import cafe.data.parser.CafeDataParser;
import junit.framework.TestCase;

/**
 * JUnit4 test cases for {@link CafeDataParser}
 * @author Ryan Zembrodt
 */
public class CafeDataParserTest extends TestCase {
	/**
	 * Checks the correct values are returned from {@link CafeDataParser#parseInt(String)}
	 */
	@Test
	public void testParseInt() {
		String i = "9";
		String empty = "  ";
		String error = "not_a_num";
		
		assertEquals(Integer.valueOf(9), CafeDataParser.parseInt(i));
		assertEquals(Integer.valueOf(0), CafeDataParser.parseInt(empty));
		assertEquals(null, CafeDataParser.parseInt(error));
	}
	
	/**
	 * Checks the correct values are returned from {@link CafeDataParser#parseDouble(String)}
	 */
	@Test
	public void testParseDouble() {
		String d = "9.9";
		String empty = "  ";
		String error = "not_a_num";
		
		assertEquals(Double.valueOf(9.9), CafeDataParser.parseDouble(d));
		assertEquals(Double.valueOf(0.0), CafeDataParser.parseDouble(empty));
		assertEquals(null, CafeDataParser.parseDouble(error));
	}
	
	/**
	 * Checks the correct values are returned from {@link CafeDataParser#parseBoolean(String)}
	 */
	@Test
	public void testParseBoolean() {
		String b = "TRUE";
		String empty = "  ";
		String error = "not_a_num";
		
		assertEquals(Boolean.valueOf(true), CafeDataParser.parseBoolean(b));
		assertEquals(Boolean.valueOf(false), CafeDataParser.parseBoolean(empty));
		assertEquals(Boolean.valueOf(false), CafeDataParser.parseBoolean(error));
	}
	
	/**
	 * Checks values are correctly converted from Celsius to Fahrenheit
	 */
	@Test
	public void testToFahrenheit() {
		double celsius = 20;
		double fahrenheit = 68.0; // = 20 * 1.8 + 32
		
		assertEquals(fahrenheit, CafeDataParser.toFahrenheit(celsius));
	}
	
	/**
	 * Checks that the correct boolean value is returned for when two LocalDateTime objects are passed with exact values 
	 * but differing 'second', 'minute', and 'day' values, respectively.
	 */
	@Test
	public void testIsMinuteEqual() {
		// Exact date with differing 'second' values
		LocalDateTime datetime1 = LocalDateTime.of(2016, 8, 28, 9, 15, 21);
		LocalDateTime datetime2 = LocalDateTime.of(2016, 8, 28, 9, 15, 41);
		
		// Exact date with differing 'minute' values
		LocalDateTime datetime3 = LocalDateTime.of(2016, 8, 28, 9, 15, 21);
		LocalDateTime datetime4 = LocalDateTime.of(2016, 8, 28, 9, 35, 21);
		
		// Exact date with differing 'day' values
		LocalDateTime datetime5 = LocalDateTime.of(2016, 8, 28, 9, 15, 21);
		LocalDateTime datetime6 = LocalDateTime.of(2016, 8, 22, 9, 15, 21);
		
		assertTrue(CafeDataParser.isMinuteEqual(datetime1, datetime2));
		assertFalse(CafeDataParser.isMinuteEqual(datetime3, datetime4));
		assertFalse(CafeDataParser.isMinuteEqual(datetime5, datetime6));
	}
	
	/**
	 * Checks that a LocalDateTime object rounded to the correct value is returned.
	 * Checks boundaries on :00, :15, :30, and :45
	 */
	@Test
	public void testToQuarterHour() {
		LocalDateTime zeroBound1 = LocalDateTime.of(2016, 8, 28, 9, 7, 29);
		LocalDateTime zeroBound2 = LocalDateTime.of(2016, 8, 28, 8, 52, 30);
		LocalDateTime zero = LocalDateTime.of(2016, 8, 28, 9, 0, 0);
		
		LocalDateTime fifteenBound1 = LocalDateTime.of(2016, 8, 28, 9, 7, 30);
		LocalDateTime fifteenBound2 = LocalDateTime.of(2016, 8, 28, 9, 22, 29);
		LocalDateTime fifteen = LocalDateTime.of(2016, 8, 28, 9, 15, 0);
		
		LocalDateTime thirtyBound1 = LocalDateTime.of(2016, 8, 28, 9, 22, 30);
		LocalDateTime thirtyBound2 = LocalDateTime.of(2016, 8, 28, 9, 37, 29);
		LocalDateTime thirty = LocalDateTime.of(2016, 8, 28, 9, 30, 0);
		
		LocalDateTime fortyFiveBound1 = LocalDateTime.of(2016, 8, 28, 9, 37, 30);
		LocalDateTime fortyFiveBound2 = LocalDateTime.of(2016, 8, 28, 9, 52, 29);
		LocalDateTime fortyFive = LocalDateTime.of(2016, 8, 28, 9, 45, 0);
		
		assertEquals(zero, CafeDataParser.toQuarterHour(zeroBound1));
		assertEquals(zero, CafeDataParser.toQuarterHour(zeroBound2));
		assertEquals(fifteen, CafeDataParser.toQuarterHour(fifteenBound1));
		assertEquals(fifteen, CafeDataParser.toQuarterHour(fifteenBound2));
		assertEquals(thirty, CafeDataParser.toQuarterHour(thirtyBound1));
		assertEquals(thirty, CafeDataParser.toQuarterHour(thirtyBound2));
		assertEquals(fortyFive, CafeDataParser.toQuarterHour(fortyFiveBound1));
		assertEquals(fortyFive, CafeDataParser.toQuarterHour(fortyFiveBound2));
	}
	
	/**
	 * Checks that a LocalDateTime object rounded to the correct value is returned.
	 * Checks boundaries on :00, :07:30, :15, :22:30, :30, :37:30, :45, and :52:30.
	 */
	@Test
	public void testToEighthHour() {
		LocalDateTime zeroBound1 = LocalDateTime.of(2016, 8, 28, 9, 3, 44);
		LocalDateTime zeroBound2 = LocalDateTime.of(2016, 8, 28, 8, 56, 15);
		LocalDateTime zero = LocalDateTime.of(2016, 8, 28, 9, 0, 0);
		
		LocalDateTime sevenFiveBound1 = LocalDateTime.of(2016, 8, 28, 9, 11, 14);
		LocalDateTime sevenFiveBound2 = LocalDateTime.of(2016, 8, 28, 9, 3, 45);
		LocalDateTime sevenFive = LocalDateTime.of(2016, 8, 28, 9, 7, 30);
		
		LocalDateTime fifteenBound1 = LocalDateTime.of(2016, 8, 28, 9, 18, 44);
		LocalDateTime fifteenBound2 = LocalDateTime.of(2016, 8, 28, 9, 11, 15);
		LocalDateTime fifteen = LocalDateTime.of(2016, 8, 28, 9, 15, 0);
		
		LocalDateTime twentyTwoFiveBound1 = LocalDateTime.of(2016, 8, 28, 9, 26, 14);
		LocalDateTime twentyTwoFiveBound2 = LocalDateTime.of(2016, 8, 28, 9, 18, 45);
		LocalDateTime twentyTwoFive = LocalDateTime.of(2016, 8, 28, 9, 22, 30);
		
		LocalDateTime thirtyBound1 = LocalDateTime.of(2016, 8, 28, 9, 33, 44);
		LocalDateTime thirtyBound2 = LocalDateTime.of(2016, 8, 28, 9, 26, 15);
		LocalDateTime thirty = LocalDateTime.of(2016, 8, 28, 9, 30, 0);
		
		LocalDateTime thirtySevenFiveBound1 = LocalDateTime.of(2016, 8, 28, 9, 41, 14);
		LocalDateTime thirtySevenFiveBound2 = LocalDateTime.of(2016, 8, 28, 9, 33, 45);
		LocalDateTime thirtySevenFive = LocalDateTime.of(2016, 8, 28, 9, 37, 30);
		
		LocalDateTime fortyFiveBound1 = LocalDateTime.of(2016, 8, 28, 9, 48, 44);
		LocalDateTime fortyFiveBound2 = LocalDateTime.of(2016, 8, 28, 9, 41, 15);
		LocalDateTime fortyFive = LocalDateTime.of(2016, 8, 28, 9, 45, 0);
		
		LocalDateTime fiftyTwoFiveBound1 = LocalDateTime.of(2016, 8, 28, 9, 56, 14);
		LocalDateTime fiftyTwoFiveBound2 = LocalDateTime.of(2016, 8, 28, 9, 48, 45);
		LocalDateTime fiftyTwoFive = LocalDateTime.of(2016, 8, 28, 9, 52, 30);
		
		assertEquals(zero, CafeDataParser.toEighthHour(zeroBound1));
		assertEquals(zero, CafeDataParser.toEighthHour(zeroBound2));
		assertEquals(sevenFive, CafeDataParser.toEighthHour(sevenFiveBound1));
		assertEquals(sevenFive, CafeDataParser.toEighthHour(sevenFiveBound2));
		assertEquals(fifteen, CafeDataParser.toEighthHour(fifteenBound1));
		assertEquals(fifteen, CafeDataParser.toEighthHour(fifteenBound2));
		assertEquals(twentyTwoFive, CafeDataParser.toEighthHour(twentyTwoFiveBound1));
		assertEquals(twentyTwoFive, CafeDataParser.toEighthHour(twentyTwoFiveBound2));
		assertEquals(thirty, CafeDataParser.toEighthHour(thirtyBound1));
		assertEquals(thirty, CafeDataParser.toEighthHour(thirtyBound2));
		assertEquals(thirtySevenFive, CafeDataParser.toEighthHour(thirtySevenFiveBound1));
		assertEquals(thirtySevenFive, CafeDataParser.toEighthHour(thirtySevenFiveBound2));
		assertEquals(fortyFive, CafeDataParser.toEighthHour(fortyFiveBound1));
		assertEquals(fortyFive, CafeDataParser.toEighthHour(fortyFiveBound2));
		assertEquals(fiftyTwoFive, CafeDataParser.toEighthHour(fiftyTwoFiveBound1));
		assertEquals(fiftyTwoFive, CafeDataParser.toEighthHour(fiftyTwoFiveBound2));
	}
	
	/**
	 * Checks that a the correct boolean value is returned for LocalDateTime objects whose values are on either side
	 * of the boundaries for what is defined as lunch time in {@link CafeDataParser#isLunchTime(LocalDateTime)}
	 */
	@Test
	public void testIsLunchTime() {
		LocalDateTime lunchTime1 = LocalDateTime.of(2016, 8, 28, 11, 0, 0);
		LocalDateTime lunchTime2 = LocalDateTime.of(2016, 8, 28, 12, 59, 59);
		LocalDateTime beforeLunchTime = LocalDateTime.of(2016, 8, 28, 10, 59, 59);
		LocalDateTime afterLunchTime = LocalDateTime.of(2016, 8, 28, 1, 0, 0);
		
		assertTrue(CafeDataParser.isLunchTime(lunchTime1));
		assertTrue(CafeDataParser.isLunchTime(lunchTime2));
		assertFalse(CafeDataParser.isLunchTime(beforeLunchTime));
		assertFalse(CafeDataParser.isLunchTime(afterLunchTime));
	}
}
