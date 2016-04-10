package cafe.data.parser.wrapper;

public class Precipitation {
	private static final int UNKNOWN = 0;
	public static final int CLEAR    = 1;
	public static final int CLOUDS   = 2;
	public static final int DRIZZLE  = 3;
	public static final int FOG      = 4;
	public static final int MIST     = 5;
	public static final int RAIN     = 6;
	public static final int SNOW     = 7;
	
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
	
	public int getValue() {
		return num;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public boolean equals(int precipitation) {
		return this.num == precipitation;
	}
	
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
