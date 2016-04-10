package cafe.data.parser.wrapper;

/**
 * Helper object used to represent the integer values of gender from the point of sale and views data files.
 * @author Ryan Zembrodt
 */
public class Gender {
	// Constant integers representing each possible value for gender.
	public static final int UNKNOWN = 0;
	public static final int MALE = 1;
	public static final int FEMALE = 2;
	
	final private int value;
	
	/**
	 * Builds a Gender object based on the integer value
	 * @param value
	 */
	public Gender(int value) {
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
		case MALE:
			return "Male";
		case FEMALE:
			return "Female";
		default:
			return "ERROR";
		}
	}
	
	/**
	 * Compares this object's value with a passed integer.
	 * @param gender integer to be compared to.
	 * @return true if the integers are equal, false otherwise.
	 */
	public boolean equals(int gender) {
		return this.value == gender;
	}
	
	/**
	 * Override of Object's equal method to compare two Gender objects' values.
	 * Does not compare the objects memory addresses.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!Gender.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final Gender g = (Gender)obj;
		
		return this.value == g.value;
	}
}
