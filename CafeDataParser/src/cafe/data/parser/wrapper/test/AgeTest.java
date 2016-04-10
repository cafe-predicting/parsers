package cafe.data.parser.wrapper.test;

import org.junit.Test;

import cafe.data.parser.wrapper.Age;
import junit.framework.TestCase;

/**
 * JUnit4 test cases for {@link Age}
 * @author Ryan Zembrodt
 */
public class AgeTest extends TestCase {
	/**
	 * Checks the correct string is returned from toString.
	 * Also used to check if an incorrect age integer value is entered, the 'ERROR' value is returned.
	 */
	@Test
	public void testToString() {
		assertTrue(new Age(-1).toString().equals("ERROR"));
		assertTrue(new Age(Age.ADULT).toString().equals("Adult"));
		assertTrue(new Age(Age.CHILD).toString().equals("Child"));
		assertTrue(new Age(Age.SENIOR).toString().equals("Senior"));
		assertTrue(new Age(Age.YOUNG_ADULT).toString().equals("Young Adult"));
		assertTrue(new Age(Age.UNKNOWN).toString().equals("Unknown"));
		assertTrue(new Age(5).toString().equals("ERROR"));
	}
	
	/**
	 * Checks the equals(int) function returns true when the Age's value equals the passed int.
	 */
	@Test
	public void testEqualsInteger() {
		Age age1 = new Age(Age.YOUNG_ADULT);
		
		assertTrue(age1.equals(Age.YOUNG_ADULT));
		assertFalse(age1.equals(Age.ADULT));
	}
	
	/**
	 * Checks the equals(Object) function returns true when the two Age's values are equal, or
	 * returns false when the two Age's values are not equal or if the passed object is null or not an instance of Age.
	 */
	@Test
	public void testEqualsObject() {
		Age age1 = new Age(Age.YOUNG_ADULT);
		Age age2 = new Age(Age.YOUNG_ADULT);
		Age unequalAge = new Age(Age.ADULT);
		Age nullAge = null;
		
		assertTrue(age1.equals(age2));
		assertFalse(age1.equals(unequalAge));
		assertFalse(age1.equals(nullAge));
		assertFalse(age1.equals(new Object()));
	}
}
