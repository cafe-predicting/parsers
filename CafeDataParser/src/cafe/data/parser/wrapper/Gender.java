package cafe.data.parser.wrapper;

public class Gender {
	public static final int UNKNOWN = 0;
	public static final int MALE = 1;
	public static final int FEMALE = 2;
	
	final private int value;
	
	public Gender(int value) {
		this.value = value;
	}
	
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
	
	public boolean equals(int gender) {
		return this.value == gender;
	}
	
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
