package cafe.data.parser.wrapper;

/**
 * Helper object used to represent the string values of precipitation from the point of sale and weather data files.
 * @author Ryan Zembrodt
 */
public class Precipitation {
	// Integer constants used to represent the precipitation types.
	private static final int UNKNOWN = 0;
	public static final int CLEAR    = 1;
	public static final int CLOUDS   = 2;
	public static final int DRIZZLE  = 3;
	public static final int FOG      = 4;
	public static final int MIST     = 5;
	public static final int RAIN     = 6;
	public static final int SNOW     = 7;
	
	// String constants used to represent the precipitation types.
	private static final String UNKNOWN_STR = "Unknown precipitation";
	public static final String CLEAR_STR    = "Clear";
	public static final String CLOUDS_STR   = "Clouds";
	public static final String DRIZZLE_STR  = "Drizzle";
	public static final String FOG_STR      = "Fog";
	public static final String MIST_STR     = "Mist";
	public static final String RAIN_STR     = "Rain";
	public static final String SNOW_STR     = "Snow";
	
	final private String value;
	final private int num;
	
	/**
	 * Builds a Precipitation object based on the string value
	 * @param value
	 */
	public Precipitation(String value) {
		this.value = value;
		
		if (value.equals(CLEAR_STR)) {
			num = CLEAR;
		}
		else if (value.equals(CLOUDS_STR)) {
			num = CLOUDS;
		}
		else if (value.equals(DRIZZLE_STR)) {
			num = DRIZZLE;
		}
		else if (value.equals(FOG_STR)) {
			num = FOG;
		}
		else if (value.equals(MIST_STR)) {
			num = MIST;
		}
		else if (value.equals(RAIN_STR)) {
			num = RAIN;
		}
		else if (value.equals(SNOW_STR)) {
			num = SNOW;
		} else {
			num = UNKNOWN;
			value = UNKNOWN_STR;
		}
	}
	
	/**
	 * @return the integer value of this Precipitation
	 */
	public int getValue() {
		return num;
	}
	
	/**
	 * Returns the string value of this Precipitation.
	 * If the value used to create this object is not supported, returns 'Unknown precipitation'
	 */
	@Override
	public String toString() {
		return value;
	}
	
	/**
	 * Compares this object's value with a passed integer.
	 * @param precipitation integer to be compared to.
	 * @return true if the integers are equal, false otherwise.
	 */
	public boolean equals(int precipitation) {
		return this.num == precipitation;
	}
	
	/**
	 * Compares this object's value with a passed string.
	 * @param precipitation string to be compared to.
	 * @return true if the strings are equal (using {@link java.lang.String#equals}), false otherwise.
	 */
	public boolean equals(String precipitation) {
		return this.value.equals(precipitation);
	}
	
	/**
	 * Override of Object's equal method to compare two Precipitation objects' values.
	 * Does not compare the objects memory addresses.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!Precipitation.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final Precipitation p = (Precipitation)obj;
		
		return this.value.equals(p.value);
	}
}
