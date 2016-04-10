package cafe.data.parser;

/**
 * Object used to represent all the data about an item a customer purchased or the advertised item from the POS data.
 * @author Ryan Zembrodt
 */
public class Item {
	// Constants for the different strings possible for the item's values.
	private static final String HEALTHY = "Healthy";
	private static final String UNHEALTHY = "Unhealthy";
	private static final String HOT = "Hot";
	private static final String COLD = "Cold";
	private static final String DRINK = "Drink";
	private static final String FOOD = "Food";
	
	private final int id;
	private final String type;
	private final String temp;
	private final boolean healthy;
	
	/**
	 * Constructs an Item from the raw data found in the POS data file.
	 * @param id
	 * @param type
	 * @param temp
	 * @param healthy string representing healthy or unhealthy (see {@link Item#HEALTHY} and {@link Item#UNHEALTHY})
	 */
	public Item(int id, String type, String temp, String healthy) {
		this.id = id;
		this.type = type;
		this.temp = temp;
		if (healthy.equals(HEALTHY)) {
			this.healthy = true;
		} else {
			this.healthy = false;
		}
	}
	
	public String getType() {
		return type;
	}
	
	public String getTemp() {
		return temp;
	}
	
	public boolean isHealthy() {
		return healthy;
	}
	
	@Override
	public String toString() {
		return "Item(" + id + "," + type + "," + temp + "," + (healthy ? "Healthy" : "Unhealthy") + ")";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (healthy ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((temp == null) ? 0 : temp.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * Checks if Items are equal based on their attribute values and not their memory address.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!Item.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final Item i = (Item)obj;
		
		return this.id == i.id && this.type.equals(i.type) && this.temp.equals(i.temp) && this.healthy == i.healthy;
	}
}
