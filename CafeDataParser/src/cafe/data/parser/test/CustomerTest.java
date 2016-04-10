package cafe.data.parser.test;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cafe.data.parser.Customer;
import cafe.data.parser.Item;
import cafe.data.parser.wrapper.Age;
import cafe.data.parser.wrapper.Gender;

/**
 * JUnit4 test cases for {@link Customer}
 * @author Ryan Zembrodt
 */
public class CustomerTest {
	private Customer customer;
	
	/**
	 * Initialize the customer object.
	 */
	@Before
	public void initialize() {
		customer = new Customer(
				LocalDateTime.of(2016, 8, 28, 9, 30, 21),
				"MONDAY",
				Gender.MALE,
				Age.YOUNG_ADULT,
				true,
				50.0,
				"Cloudy");
	}
	
	/**
	 * Checks that the correct list of purchased items are returned after they are added.
	 */
	@Test
	public void testAddItem() {
		Item item1 = new Item(0, "Food", "Hot", "Unhealthy");
		Item item2 = new Item(1, "Drink", "Cold", "Healthy");
		
		customer.addItem(item1);
		customer.addItem(item2);
		
		Assert.assertEquals(item1, customer.getPurchasedItems().get(0));
		Assert.assertEquals(item2, customer.getPurchasedItems().get(1));
	}
	
	/**
	 * Checks that the correct list of advertised items are returned after they are added.
	 * Also checks that duplicate items (of the same values) are not added.
	 */
	@Test
	public void testAddAdvertisedItem() {
		Item item1 = new Item(0, "Food", "Hot", "Unhealthy");
		Item item2 = new Item(0, "Food", "Hot", "Unhealthy"); // Identical to item1
		Item item3 = new Item(1, "Drink", "Cold", "Healthy");
		
		customer.addAdvertisedItem(item1);
		customer.addAdvertisedItem(item2); // Should be ignored
		customer.addAdvertisedItem(item3);
		
		Assert.assertEquals(item1, customer.getAdvertisedItems().get(0));
		Assert.assertFalse(item2.equals(customer.getAdvertisedItems().get(1)));
		Assert.assertEquals(item3, customer.getAdvertisedItems().get(1));
	}
}
