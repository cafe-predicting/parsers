package cafe.data.parser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cafe.data.parser.wrapper.Age;
import cafe.data.parser.wrapper.Gender;

/**
 * Object used to represent customers built from the POS Data.
 * @author Ryan Zembrodt
 */
public class Customer {
	private LocalDateTime datetime;
	private String dayOfWeek;
	private Gender gender;
	private Age age;
	private List<Item> purchasedItems;
	private List<Item> advertisedItems;
	private boolean boughtAdvertised;
	private double temperature;
	private String precipitation;
	
	public Customer(LocalDateTime datetime, String dayOfWeek, int gender, int age, boolean boughtAdvertised, double temperature, String precipitation) {
		this.datetime = datetime;
		this.dayOfWeek = dayOfWeek;
		this.gender = new Gender(gender);
		this.age = new Age(age);
		this.purchasedItems = new ArrayList<Item>();
		this.advertisedItems = new ArrayList<Item>();
		this.boughtAdvertised = boughtAdvertised;
		this.temperature = temperature;
		this.precipitation = precipitation;
	}
	
	/**
	 * Adds an item to the list of items the customer purchased.
	 * @param item
	 */
	public void addItem(Item item) {
		purchasedItems.add(item);
	}
	
	/**
	 * Adds an item to the list of items advertised while the customer made their purchase.
	 * Takes into account multiple items could've have been advertised so only adds in unique Items.
	 * @param item
	 */
	public void addAdvertisedItem(Item item) {
		boolean containsItem = false;
		for (Item i : advertisedItems) {
			if (i.equals(item)) {
				containsItem = true;
			}
		}
		if (!containsItem) {
			advertisedItems.add(item);
		}
	}
	
	public LocalDateTime getDate() {
		return datetime;
	}
	
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public Age getAge() {
		return age;
	}
	
	public List<Item> getPurchasedItems() {
//		List<Item> purchasedItemsCopy = new ArrayList<Item>(purchasedItems.size());
//		Collections.copy(purchasedItemsCopy, purchasedItems);
//		return purchasedItemsCopy;
		return purchasedItems;
	}
	
	public List<Item> getAdvertisedItems() {
		return advertisedItems;
	}
	
	public boolean getBoughtAdvertised() {
		return boughtAdvertised;
	}
	
	public void setBoughtAdvertised(boolean boughtAdvertised) {
		this.boughtAdvertised = boughtAdvertised;
	}
	
	public double getTemperature() {
		return temperature;
	}
	
	public String getPrecipitation() {
		return precipitation;
	}
}
