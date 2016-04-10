package cafe.data.parser.wrapper.test;

import org.junit.Test;

import cafe.data.parser.wrapper.Gender;
import junit.framework.TestCase;

/**
 * JUnit4 test cases for {@link Gender}
 * @author Ryan Zembrodt
 */
public class GenderTest extends TestCase {
	/**
	 * Checks the correct string is returned from toString.
	 * Also used to check if an incorrect age integer value is entered, the 'ERROR' value is returned.
	 */
	@Test
	public void testToString() {
		assertTrue(new Gender(-1).toString().equals("ERROR"));
		assertTrue(new Gender(Gender.FEMALE).toString().equals("Female"));
		assertTrue(new Gender(Gender.MALE).toString().equals("Male"));
		assertTrue(new Gender(Gender.UNKNOWN).toString().equals("Unknown"));
		assertTrue(new Gender(3).toString().equals("ERROR"));
	}
	
	/**
	 * Checks the equals(int) function returns true when the Gender's value equals the passed int.
	 */
	@Test
	public void testEqualsInteger() {
		Gender gender = new Gender(Gender.FEMALE);
		
		assertTrue(gender.equals(Gender.FEMALE));
		assertFalse(gender.equals(Gender.MALE));
	}
	
	/**
	 * Checks the equals(Object) function returns true when the two Gender's values are equal, or
	 * returns false when the two Gender's values are not equal or if the passed object is null or not an instance of Gender.
	 */
	@Test
	public void testEqualsObject() {
		Gender gender1 = new Gender(Gender.MALE);
		Gender gender2 = new Gender(Gender.MALE);
		Gender unequalGender = new Gender(Gender.FEMALE);
		Gender nullGender = null;
		
		assertTrue(gender1.equals(gender2));
		assertFalse(gender1.equals(unequalGender));
		assertFalse(gender1.equals(nullGender));
		assertFalse(gender1.equals(new Object()));
	}
}
