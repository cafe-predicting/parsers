package cafe.data.parser.test;

import org.junit.Test;

import cafe.data.parser.Item;
import junit.framework.TestCase;

/**
 * JUnit4 test cases for {@link Item}
 * @author Ryan Zembrodt
 */
public class ItemTest extends TestCase {
	/**
	 * Checks the Item constructor that the correct boolean value is assigned for the passed String value.
	 */
	@Test
	public void testItem() {
		Item healthyItem = new Item(0, "Food", "Hot", "Healthy");
		Item unhealthyItem = new Item(1, "Food", "Cold", "Unhealthy");
		
		assertTrue(healthyItem.isHealthy());
		assertFalse(unhealthyItem.isHealthy());
	}
	
	/**
	 * Checks the custom equals function returns true when two Item objects have the same values.
	 * Asserts the function returns false when any of the 4 values differ, when the passed Object is null, or is not an Item object.
	 */
	@Test
	public void testEquals() {
		Item item1 = new Item(0, "Food", "Hot", "Healthy");
		Item item2 = new Item(0, "Food", "Hot", "Healthy");
		Item unequalItem1 = new Item(1, "Food", "Hot", "Healthy");
		Item unequalItem2 = new Item(0, "Drink", "Hot", "Healthy");
		Item unequalItem3 = new Item(0, "Food", "Cold", "Healthy");
		Item unequalItem4 = new Item(0, "Food", "Hot", "Unhealthy");
		Item nullItem = null;
		
		assertTrue(item1.equals(item2));
		assertFalse(item1.equals(unequalItem1));
		assertFalse(item1.equals(unequalItem2));
		assertFalse(item1.equals(unequalItem3));
		assertFalse(item1.equals(unequalItem4));
		assertFalse(item1.equals(nullItem));
		assertFalse(item1.equals(new Object()));
	}
}
