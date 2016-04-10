package cafe.data.parser.wrapper;

public class Age {
	public static final int UNKNOWN = 0;
	public static final int CHILD = 1;
	public static final int YOUNG_ADULT = 2;
	public static final int ADULT = 3;
	public static final int SENIOR = 4;
	
	final private int value;
	
	public Age(int value) {
		this.value = value;
	}
	
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
	
	public boolean equals(int age) {
		return this.value == age;
	}
	
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
