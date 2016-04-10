package cafe.data.parser.wrapper;

/**
 * Helper object used to represent the integer values of age from the point of sale and views data files.
 * @author Ryan Zembrodt
 */
public class Age {
	// Constant integers representing each possible value for age.
	public static final int UNKNOWN = 0;
	public static final int CHILD = 1;
	public static final int YOUNG_ADULT = 2;
	public static final int ADULT = 3;
	public static final int SENIOR = 4;
	
	final private int value;
	
	/**
	 * Builds an Age object based on the integer value
	 * @param value
	 */
	public Age(int value) {
		this.value = value;
	}
	
	/**
	 * Returns the corresponding string value for the object's value.
	 * Returns 'Error' if the value is unsupported.
	 */
	@Override
	public String toString() {
		switch (value) {
		case UNKNOWN:
			return "Unknown";
		case CHILD:
			return "Child";
		case YOUNG_ADULT:
			return "Young Adult";
		case ADULT:
			return "Adult";
		case SENIOR:
			return "Senior";
		default:
			return "ERROR";
		}
	}
	
	/**
	 * Compares this object's value with a passed integer.
	 * @param age integer to be compared to.
	 * @return true if the integers are equal, false otherwise.
	 */
	public boolean equals(int age) {
		return this.value == age;
	}
	
	/**
	 * Override of Object's equal method to compare two Age objects' values.
	 * Does not compare the objects memory addresses.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!Age.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final Age a = (Age)obj;
		
		return this.value == a.value;
	}
}
