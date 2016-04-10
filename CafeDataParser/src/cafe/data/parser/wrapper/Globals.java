package cafe.data.parser.wrapper;

/**
 * Object holding static constants to represent various String and integer values needed by the parser.
 * Constants include the various file names, column number matching data in the csv files, and date format matching the specific files.
 * If inputting a new csv file, be sure to validate that the columns match the values here and to edit the row or column counts.
 * @author Ryan Zembrodt
 */
public class Globals {
	// Data filenames
	public static final String GATES_FILE   = "gatesData.csv";
	public static final String POS_FILE     = "PointOfSaleSimulation.csv";
	public static final String VIEWS_FILE   = "viewsData.csv";
	public static final String WEATHER_FILE = "weatherData.csv";
	
	// Gates data
	public static final int G_COL_COUNT = 6;
	public static final int G_ROW_COUNT = 16041;
	public static final int G_LOC = 0;
	public static final int G_GATE = 1;
	public static final int G_DATE = 2;
	public static final int G_DUR = 3;
	public static final int G_IN = 4;
	public static final int G_OUT = 5;
	
	// Views data
	public static final int V_COL_COUNT = 7;
	public static final int V_ROW_COUNT = 31803;
	public static final int V_LOC = 0;
	public static final int V_GENDER = 1;
	public static final int V_AGE = 2;
	public static final int V_DATE = 3;
	public static final int V_DWELL = 4;
	public static final int V_ATTENTION = 5;
	public static final int V_WATCHER = 6;
	public static final int V_RES = 7;
	
	// Point of sale data
	public static final int P_COL_COUNT = 21;
	public static final int P_ROW_COUNT = 138652;
	public static final int P_AGE = 0;
	public static final int P_AGE_ID = 1;
	public static final int P_GENDER = 2;
	public static final int P_GENDER_ID = 3;
	public static final int P_DWELL = 4;
	public static final int P_ATTENTION = 5;
	public static final int P_TEMP = 6;
	public static final int P_HUMIDITY = 7;
	public static final int P_PERCIPITATION = 8;
	public static final int P_SALE_ITEM = 9;
	public static final int P_SALE_TYPE = 10;
	public static final int P_SALE_TEMP = 11;
	public static final int P_SALE_HEALTH = 12;
	public static final int P_TOTAL_CUST = 13;
	public static final int P_DATE = 14;
	public static final int P_DOW = 15;
	public static final int P_ITEM_ID = 16;
	public static final int P_ITEM_TYPE = 17;
	public static final int P_ITEM_TEMP = 18;
	public static final int P_ITEM_HEALTH = 19;
	public static final int P_PURCHASE_SALE = 20;
	
	// Weather data
	public static final int W_COL_COUNT = 5;
	public static final int W_ROW_COUNT = 1686;
	public static final int W_DATE = 0;
	public static final int W_TEMP = 1;
	public static final int W_HUM = 2;
	public static final int W_PERC = 3;
	public static final int W_SEV = 4;
	
	// Datetime formats
	public static final String GATES_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String VIEWS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String POS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String WEATHER_DATE_FORMAT = "MM/dd/yyyy hh:mm:ss a";
}
