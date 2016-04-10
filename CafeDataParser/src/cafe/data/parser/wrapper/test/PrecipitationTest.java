package cafe.data.parser.wrapper.test;

import org.junit.Test;

import cafe.data.parser.wrapper.Precipitation;
import junit.framework.TestCase;

/**
 * JUnit4 test cases for {@link Precipitation}
 * @author Ryan Zembrodt
 */
public class PrecipitationTest extends TestCase {
	/**
	 * Checks that the correct inner int value is stored when a Precipitation is created from a String value.
	 */
	@Test
	public void testPrecipitation() {
		assertEquals(Precipitation.CLEAR, new Precipitation(Precipitation.CLEAR_STR).getValue());
		assertEquals(Precipitation.CLOUDS, new Precipitation(Precipitation.CLOUDS_STR).getValue());
		assertEquals(Precipitation.DRIZZLE, new Precipitation(Precipitation.DRIZZLE_STR).getValue());
		assertEquals(Precipitation.FOG, new Precipitation(Precipitation.FOG_STR).getValue());
		assertEquals(Precipitation.MIST, new Precipitation(Precipitation.MIST_STR).getValue());
		assertEquals(Precipitation.RAIN, new Precipitation(Precipitation.RAIN_STR).getValue());
		assertEquals(Precipitation.SNOW, new Precipitation(Precipitation.SNOW_STR).getValue());
	}
	
	/**
	 * Checks that the correct string values are returned from the toString function, including when the Precipitation object
	 * is built with an incorrect string value.
	 */
	@Test
	public void testToString() {
		assertEquals(Precipitation.CLEAR_STR, new Precipitation(Precipitation.CLEAR_STR).toString());
		assertEquals(Precipitation.CLOUDS_STR, new Precipitation(Precipitation.CLOUDS_STR).toString());
		assertEquals(Precipitation.DRIZZLE_STR, new Precipitation(Precipitation.DRIZZLE_STR).toString());
		assertEquals(Precipitation.FOG_STR, new Precipitation(Precipitation.FOG_STR).toString());
		assertEquals(Precipitation.MIST_STR, new Precipitation(Precipitation.MIST_STR).toString());
		assertEquals(Precipitation.RAIN_STR, new Precipitation(Precipitation.RAIN_STR).toString());
		assertEquals(Precipitation.SNOW_STR, new Precipitation(Precipitation.SNOW_STR).toString());
		assertEquals("Unknown precipitation", new Precipitation("Incorrect string value").toString());
	}
	
	/**
	 * Checks the equals(int) function returns true when the Precipitation's value equals the passed int.
	 */
	@Test
	public void testEqualsInteger() {
		Precipitation precipitation = new Precipitation(Precipitation.CLEAR_STR);
		
		assertTrue(precipitation.equals(Precipitation.CLEAR));
		assertFalse(precipitation.equals(Precipitation.CLOUDS));
	}
	
	/**
	 * Checks the equals(String) function returns true when the Precipitation's value equals the passed String.
	 */
	@Test
	public void testEqualsString() {
		Precipitation precipitation = new Precipitation(Precipitation.CLEAR_STR);
		
		assertTrue(precipitation.equals(Precipitation.CLEAR_STR));
		assertFalse(precipitation.equals(Precipitation.CLOUDS_STR));
	}
	
	/**
	 * Checks the equals(Object) function returns true when the two Precipitation's values are equal, or
	 * returns false when the two Precipitation's values are not equal or if the passed object is null or not an instance of Precipitation.
	 */
	@Test
	public void testEqualsObject() {
		Precipitation precipitation1 = new Precipitation(Precipitation.CLEAR_STR);
		Precipitation precipitation2 = new Precipitation(Precipitation.CLEAR_STR);
		Precipitation unequalPrecipitation = new Precipitation(Precipitation.CLOUDS_STR);
		Precipitation nullPrecipitation = null;
		
		assertTrue(precipitation1.equals(precipitation2));
		assertFalse(precipitation1.equals(unequalPrecipitation));
		assertFalse(precipitation1.equals(nullPrecipitation));
		assertFalse(precipitation1.equals(new Object()));
	}
}
