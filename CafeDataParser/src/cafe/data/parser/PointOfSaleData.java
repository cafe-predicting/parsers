package cafe.data.parser;

import java.time.LocalDateTime;

import cafe.data.parser.wrapper.Age;
import cafe.data.parser.wrapper.Gender;

/**
 * Object representing raw entries in point of sale data file.
 * @author Ryan Zembrodt
 */
public class PointOfSaleData {
	private Age age;
	private Gender gender;
	private double dwellTime;
	private double attentionTime;
	private double temperature; // Fahrenheit
	private double humidity; // percentage
	private String percipitation;
	private int saleItemId;
	private String saleItemType;
	private String saleItemTemp;
	private String saleItemHealth;
	private int totalCustomersInStore;
	private LocalDateTime datetime;
	private String dayOfWeek;
	private int itemId;
	private String itemType;
	private String itemTemp;
	private String itemHealth;
	private boolean purchasedSaleItem;
	
	public PointOfSaleData(int age, int gender, double dwellTime, double attentionTime, double temperature, double humidity, String percipitation,
			int saleItemId, String saleItemType, String saleItemTemp, String saleItemHealth, int totalCustomersInStore,
			LocalDateTime datetime, String dayOfWeek, int itemId, String itemType, String itemTemp, String itemHealth, boolean purchasedSaleItem) {
		
		this.age = new Age(age);
		this.gender = new Gender(gender);
		this.dwellTime = dwellTime;
		this.attentionTime = attentionTime;
		this.temperature = temperature;
		this.humidity = humidity;
		this.percipitation = percipitation;
		this.saleItemId = saleItemId;
		this.saleItemType = saleItemType;
		this.saleItemTemp = saleItemTemp;
		this.saleItemHealth = saleItemHealth;
		this.totalCustomersInStore = totalCustomersInStore;
		this.datetime = datetime;
		this.dayOfWeek = dayOfWeek;
		this.itemId = itemId;
		this.itemType = itemType;
		this.itemTemp = itemTemp;
		this.itemHealth = itemHealth;
		this.purchasedSaleItem = purchasedSaleItem;
	}
	
	public LocalDateTime getDate() {
		return datetime;
	}
	
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	
	public int getItemId() {
		return itemId;
	}
	
	public String getItemType() {
		return itemType;
	}
	
	public String getItemTemp() {
		return itemTemp;
	}
	
	public String getItemHealth() {
		return itemHealth;
	}
	
	public Gender getGender() {
		return this.gender;
	}
	
	public Age getAge() {
		return this.age;
	}
	
	public double getTemperature() {
		return this.temperature;
	}
	
	public double getHumidity() {
		return this.humidity;
	}
	
	public String getPrecipitation() {
		return percipitation;
	}
	
	public double getDwellTime() {
		return dwellTime;
	}
	
	public double getAttentionTime() {
		return attentionTime;
	}
	
	public boolean getBoughtAdvertised() {
		return purchasedSaleItem;
	}
	
	public int getAdvertisedItemId() {
		return saleItemId;
	}
	
	public String getAdvertisedItemType() {
		return saleItemType;
	}
	
	public String getAdvertisedItemTemp() {
		return saleItemTemp;
	}

	public String getAdvertisedItemHealth() {
		return saleItemHealth;
	}
}
