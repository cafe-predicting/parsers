package cafe.data.parser;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import cafe.data.parser.wrapper.Age;

public class Item {
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
