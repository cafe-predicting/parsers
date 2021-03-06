package cafe.data.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cafe.data.parser.wrapper.Globals;
import cafe.data.parser.wrapper.Precipitation;

/**
 * The main parser object. This is built in several parts: the main method, helper method, and builder methods.
 * 
 * @author Ryan Zembrodt
 */
public class CafeDataParser {
	
	// Flag which lists you would like to build or are using.
	public static final boolean BUILD_WEATHER = true;
	public static final boolean BUILD_GATES = true;
	public static final boolean BUILD_VIEWS = true;
	public static final boolean BUILD_POS = true;
	
	/**
	 * Main method. This method's job is to populate each of the data lists (specified by the build flags) from the four data files.
	 * @param args
	 */
	public static void main(String[] args) {
	
		List<WeatherData> weatherData = null;
		List<GatesData> gatesData = null;
		List<ViewsData> viewsData = null;
		List<PointOfSaleData> posData = null;
		
		List<Customer> customers = null;
		
		DateTimeFormatter dateFormatter = null;
		
		if (BUILD_WEATHER) {
			// Parse weather data csv file
			weatherData = new ArrayList<WeatherData>(Globals.W_ROW_COUNT);
			dateFormatter = DateTimeFormatter.ofPattern(Globals.WEATHER_DATE_FORMAT);
			try (BufferedReader br = new BufferedReader(new FileReader(Globals.WEATHER_FILE))) {
				
				int count = 0;
				String line = null;
				
				// Loop through each entry on the data file.
				while ((line = br.readLine()) != null) {
					count++;
					// Check that this row entry contains data.
					if (!line.isEmpty()) {
						// Split an entry based on commas.
						String[] dataEntry = line.split(",");
						// Check the amount of data in this row entry based on how many there should be
						if (dataEntry.length != Globals.W_COL_COUNT) {
							// Log the error to notify user, but do not throw an exception
							System.out.println("Invalid entry on line " + count);
							continue;
						}
						
						// Build a WeatherData object from this raw data using helper methods.
						weatherData.add(new WeatherData(
								LocalDateTime.parse(dataEntry[Globals.W_DATE], dateFormatter),
								toFahrenheit(parseDouble(dataEntry[Globals.W_TEMP])),
								parseInt(dataEntry[Globals.W_HUM]),
								dataEntry[Globals.W_PERC],
								parseBoolean(dataEntry[Globals.W_SEV])));
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("File " + Globals.WEATHER_FILE + " does not exist.");
				weatherData = null;
			} catch (IOException e) {
				System.out.println("IO exception");
				weatherData = null;
			}
			if (weatherData != null) {
				System.out.println("Loaded weather data.");
			}
		}
		
		if (BUILD_GATES) {
			// Parse Gates data csv file
			gatesData = new ArrayList<GatesData>(Globals.G_ROW_COUNT);
			dateFormatter = DateTimeFormatter.ofPattern(Globals.GATES_DATE_FORMAT);
			try (BufferedReader br = new BufferedReader(new FileReader(Globals.GATES_FILE))) {
				
				int count = 0;
				String line = null;
				
				while ((line = br.readLine()) != null) {
					count++;
					if (!line.isEmpty()) {
						
						String[] dataEntry = line.split(",");
						if (dataEntry.length != Globals.G_COL_COUNT) {
							System.out.println("Invalid entry on line " + count);
							continue;
						}
						
						gatesData.add(new GatesData(
								parseInt(dataEntry[Globals.G_LOC]),
								parseInt(dataEntry[Globals.G_GATE]),
								LocalDateTime.parse(dataEntry[Globals.G_DATE], dateFormatter),
								parseInt(dataEntry[Globals.G_DUR]),
								parseInt(dataEntry[Globals.G_IN]),
								parseInt(dataEntry[Globals.G_OUT])));
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("File " + Globals.GATES_FILE + " does not exist.");
				gatesData = null;
			} catch (IOException e) {
				System.out.println("IO exception");
				gatesData = null;
			}
			if (gatesData != null) {
				System.out.println("Loaded gates data.");
			}
		}
		
		if (BUILD_VIEWS) {
			// Parse Views data csv file
			viewsData = new ArrayList<ViewsData>(Globals.V_ROW_COUNT);
			dateFormatter = DateTimeFormatter.ofPattern(Globals.VIEWS_DATE_FORMAT);
			try (BufferedReader br = new BufferedReader(new FileReader(Globals.VIEWS_FILE))) {
				
				int count = 0;
				String line = null;
				
				while ((line = br.readLine()) != null) {
					count++;
					if (!line.isEmpty()) {
						
						String[] dataEntry = line.split(",");
						if (dataEntry.length != Globals.V_COL_COUNT) {
							System.out.println("Invalid entry on line " + count);
							continue;
						}
						
						viewsData.add(new ViewsData(
								parseInt(dataEntry[Globals.V_LOC]),
								parseInt(dataEntry[Globals.V_GENDER]),
								parseInt(dataEntry[Globals.V_AGE]),
								LocalDateTime.parse(dataEntry[Globals.V_DATE], dateFormatter),
								parseInt(dataEntry[Globals.V_DWELL]),
								parseInt(dataEntry[Globals.V_ATTENTION]),
								parseInt(dataEntry[Globals.V_WATCHER])));
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("File " + Globals.VIEWS_FILE + " does not exist.");
				viewsData = null;
			} catch (IOException e) {
				System.out.println("IO exception");
				viewsData = null;
			}
			if (viewsData != null) {
				System.out.println("Loaded views data.");
			}
		}
		
		if (BUILD_POS) {
			// Parse Point of sale data csv file
			posData = new ArrayList<PointOfSaleData>(Globals.P_ROW_COUNT);
			customers = new ArrayList<Customer>();
			dateFormatter = DateTimeFormatter.ofPattern(Globals.POS_DATE_FORMAT);
			try (BufferedReader br = new BufferedReader(new FileReader(Globals.POS_FILE))) {
				
				int count = 0;
				String line = null;
				
				// Loop through each entry in the point of sale data file.
				while ((line = br.readLine()) != null) {
					count++;
					if (!line.isEmpty()) {
						// Split the row by commas and check the correct amount of values are in it.
						String[] dataEntry = line.split(",");
						if (dataEntry.length != Globals.P_COL_COUNT) {
							// If the correct amount of values is not found alert user and continue to the next entry
							System.out.println("Invalid entry on line " + count);
							continue;
						}
						
						// Builds a PointOfSaleData object based on the raw values defined above.
						posData.add(new PointOfSaleData(
								parseInt(dataEntry[Globals.P_AGE_ID]),
								parseInt(dataEntry[Globals.P_GENDER_ID]),
								parseDouble(dataEntry[Globals.P_DWELL]),
								parseDouble(dataEntry[Globals.P_ATTENTION]),
								toFahrenheit(parseDouble(dataEntry[Globals.P_TEMP])),
								parseDouble(dataEntry[Globals.P_HUMIDITY]),
								dataEntry[Globals.P_PERCIPITATION],
								parseInt(dataEntry[Globals.P_SALE_ITEM]),
								dataEntry[Globals.P_SALE_TYPE],
								dataEntry[Globals.P_SALE_TEMP],
								dataEntry[Globals.P_SALE_HEALTH],
								parseInt(dataEntry[Globals.P_TOTAL_CUST]),
								LocalDateTime.parse(dataEntry[Globals.P_DATE], dateFormatter),
								dataEntry[Globals.P_DOW],
								parseInt(dataEntry[Globals.P_ITEM_ID]),
								dataEntry[Globals.P_ITEM_TYPE],
								dataEntry[Globals.P_ITEM_TEMP],
								dataEntry[Globals.P_ITEM_HEALTH],
								parseBoolean(dataEntry[Globals.P_PURCHASE_SALE])));
					}
				}
				
				// Sort the POS Data (by date)
				Collections.sort(posData, new Comparator<PointOfSaleData>() {
					public int compare(PointOfSaleData pos1, PointOfSaleData pos2) {
						return pos1.getDate().compareTo(pos2.getDate());
					}
				});
				
				// Build the list of customers from the POS data.
				customers = new ArrayList<Customer>();
				Customer prevCustomer = null;
				int startIndex = 0;
				
				for (int i = 0; i <= posData.size(); i++) {
					// This date is different to the previously used one, therefore we have a new customer.
					if (prevCustomer == null || i == posData.size() || !prevCustomer.getDate().equals(posData.get(i).getDate())) {
						if (i > startIndex) {
							int endIndex = i-1;
							
							// Used to build a list of all unique customers of the current datetime.
							List<Customer> currentCustomers = new ArrayList<Customer>();
							
							// Loops through the section of POS data with the current datetime to find unique customers
							for (int j = startIndex; j <= endIndex; j++) {
								// Create a customer out of the current POS data row
								Customer c = new Customer(posData.get(j).getDate(), posData.get(j).getDayOfWeek(), posData.get(j).getGender(), posData.get(j).getAge(), posData.get(j).getBoughtAdvertised(), posData.get(j).getTemperature(), posData.get(j).getPrecipitation());
								// Checks if an identical customer exists already exists in the list (equal date, gender, and age).
								if (!currentCustomers.contains(c)) {
									// Adds the current customer if it does not exist.
									currentCustomers.add(c);
								}
							}
							
							// Loops through the previously created unique customers to add their items
							for (Customer currCustomer : currentCustomers) {
								// Loop through the POS data for each unique customer in order to get the item information
								for (int j = startIndex; j <= endIndex; j++) {
									Customer c = new Customer(posData.get(j).getDate(), posData.get(j).getDayOfWeek(), posData.get(j).getGender(), posData.get(j).getAge(), posData.get(j).getBoughtAdvertised(), posData.get(j).getTemperature(), posData.get(j).getPrecipitation());
									// Checks that the current POS data customer information is equal to the current unique customer (equal date, gender, and age)
									if (currCustomer.equals(c)) {
										// Adds the purchased item and advertised item from this POS data row to the customer.
										currCustomer.addItem(new Item(posData.get(j).getItemId(), posData.get(j).getItemType(), posData.get(j).getItemTemp(), posData.get(j).getItemHealth()));
										currCustomer.addAdvertisedItem(new Item(posData.get(j).getAdvertisedItemId(), posData.get(j).getAdvertisedItemType(), posData.get(j).getAdvertisedItemTemp(), posData.get(j).getAdvertisedItemHealth()));
										// Used to set that the customer bought the advertised item. Only sets if true (as this is defaulted to false)
										if (posData.get(j).getBoughtAdvertised()) {
											currCustomer.setBoughtAdvertised(posData.get(j).getBoughtAdvertised());
										}
									}
								}
								// Add the current unique customer to the overall customers list after its items have been added
								customers.add(currCustomer);
							}
						}
						
						// Only set the start index and previous customer when i is a valid index within posData (as the for loop loops from 0 to posData.size)
						// This is needed to use the above if statement to catch the last datetime in the POS data list.
						if (i < posData.size()) {
							startIndex = i;						
							prevCustomer = new Customer(posData.get(i).getDate(), posData.get(i).getDayOfWeek(), posData.get(i).getGender(), posData.get(i).getAge(), posData.get(i).getBoughtAdvertised(), posData.get(i).getTemperature(), posData.get(i).getPrecipitation());
						}
					}
				}
				
			} catch (FileNotFoundException e) {
				System.out.println("File " + Globals.POS_FILE + " does not exist.");
				posData = null;
			} catch (IOException e) {
				System.out.println("IO exception");
				posData = null;
			}
			if (posData != null) {
				System.out.println("Loaded point of sale data.");
			}
		}
		
		System.out.println("Data load complete.");
		
		// Various static build methods defined below used to create different files (both csv and R scripts) for input into R.
		buildWeatherGatesFile(weatherData, gatesData);

		buildWeatherGatesFile2(weatherData, gatesData);
		
		buildHealthyFile(customers);
		
		buildViewsFile(viewsData);
		
		buildHealthyFile(customers);
		
		buildHealthyScript();
		
		buildWeatherScript();
	}
	
	// Static helper methods
	
	/**
	 * Wrapper for {@link Integer#parseInt} to default the return null instead of throwing an exception or return 0 if it is an empty string.
	 * @param s the string to be parsed
	 * @return the integer value of the string, null if string cannot be parsed, or 0 if the string is empty.
	 */
	public static Integer parseInt(String s) {
		// Checks that the passed string is not null and is not empty after all whitespace is trimmed.
		if (s != null && !s.trim().isEmpty()) {
			try{
				return Integer.parseInt(s);
			}
			catch (Exception e) {
				System.out.println("Unexpected exception caught trying to parse '" + s + "' to an Integer.");
				return null;
			}
		} else {
			return 0;
		}
	}
	
	/**
	 * Wrapper for {@link Double#parseDouble} to default the return null instead of throwing an exception or return 0 if it is an empty string.
	 * @param s the string to be parsed
	 * @return the double value of the string, null if string cannot be parsed, or 0 if the string is empty.
	 */
	public static Double parseDouble(String s) {
		if (s != null && !s.trim().isEmpty()) {
			try{
				return Double.parseDouble(s);
			}
			catch (Exception e) {
				System.out.println("Unexpected exception caught trying to parse '" + s + "' to a Double.");
				return null;
			}
		} else {
			return 0.0;
		}
	}
	
	/**
	 * Wrapper for {@link Boolean#parseBoolean} to default the return null instead of throwing an exception or return false if it is an empty string.
	 * Will return false for all string values not equal (ignoring case) to 'true'.
	 * @param s the string to be parsed
	 * @return the boolean value of the string, null if string cannot be parsed, or false if the string is empty.
	 */
	public static Boolean parseBoolean(String s) {
		if (s != null && !s.trim().isEmpty()) {
			try{
				return Boolean.parseBoolean(s);
			}
			catch (Exception e) {
				System.out.println("Unexpected exception caught trying to parse '" + s + "' to a Boolean.");
				return null;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Converts a temperature value in Celsius to Fahrenheit.
	 * @param celsius the value in Celsius
	 * @return the value in Fahrenheit
	 */
	public static double toFahrenheit(double celsius) {
		return celsius * 1.8 + 32.0;
	}
	
	/**
	 * Checks if two LocalDateTime objects are equal down to the minute.
	 * This does not just check the minute value but also year, month, day, and hour values.
	 * @param datetime1
	 * @param datetime2
	 * @return true of the LocalDateTime objects are equal to the minute, false otherwise.
	 */
	public static boolean isMinuteEqual(LocalDateTime datetime1, LocalDateTime datetime2) {
		return datetime1.getYear() == datetime2.getYear()
				&& datetime1.getMonthValue() == datetime2.getMonthValue()
				&& datetime1.getDayOfMonth() == datetime2.getDayOfMonth()
				&& datetime1.getHour() == datetime2.getHour()
				&& datetime1.getMinute() == datetime2.getMinute();
	}

	/**
	 * Converts a LocalDateTime to a LocalDateTime with the time converted to the nearest quarter hour (:00, :15, :30 :45, :60)
	 * @param date the LocalDateTime to convert
	 * @return a LocalDateTime with its LocalTime value converted to the nearest quarter hour.
	 */
	public static LocalDateTime toQuarterHour(LocalDateTime date) {
		// Normalize seconds to 0-1 range: (seconds - min) / (max - min) where min = 0 and max = 60
		double secondsNormalized = (double)date.getSecond() / 60.0;
		// Append the seconds to minutes value as a decimal
		double minuteMod = (double)date.getMinute() + secondsNormalized;
		// The remainder of the current minute (0-60) divided by 15.
		minuteMod %= 15.0;
		
		// Create a copy of the passed LocalDateTime object.
		LocalDateTime retDate = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
				date.getHour(), date.getMinute(), 0);
		
		// The minute value is closer to the lower bound so should be rounded down.
		if (minuteMod < 7.5) {
			// Subtract the remainder from the current minutes (LocalDateTime.minusMinutes will change the hour/day/month/year value if enough is subtracted)
			retDate = retDate.minusMinutes((long)minuteMod);
		// The minute value is closer to the upper bound so should be rounded up.
		} else {
			retDate = retDate.plusMinutes(15L - (long)minuteMod);
		}
		return retDate;
	}
	
	/**
	 * Converts a LocalDateTime to a LocalDateTime with the time converted to the nearest eighth hour (:00, :07:30, :15, :22:30, :30, :37:30, :45, :52:30, :60)
	 * @param date the LocalDateTime to convert
	 * @return a LocalDateTime with its LocalTime value converted to the nearest eighth hour.
	 */
	public static LocalDateTime toEighthHour(LocalDateTime date) {
		// Normalize seconds to 0-1 range: (seconds - min) / (max - min) where min = 0 and max = 60
		double secondsNormalized = (double)date.getSecond() / 60.0;
		// Append the seconds to minutes value as a decimal
		double minuteMod = (double)date.getMinute() + secondsNormalized;
		// The remainder of the current minute (0-60) divided by 7.5
		minuteMod %= 7.5;
		
		// Create a copy of the passed LocalDateTime object.
		LocalDateTime retDate = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
				date.getHour(), date.getMinute());
		
		// The minute value is closer to the lower bound so should be rounded down.
		if (minuteMod < 3.75) {
			retDate = retDate.minusMinutes((long)minuteMod);
			// If the time was rounded to a boundary on :07:30, :22:30, :37:30, or :52:30 subtract 1 more minute.
			if (retDate.getMinute() % 15 != 0) {
				retDate = retDate.minusMinutes(1L);
			}
		// The minute value is closer to the upper bound so should be rounded up.
		} else {
			retDate = retDate.plusMinutes((long)(7.5 - (long)minuteMod));
		}
		
		// If the new rounded minute is not on a quarter hour (:00, :15, :30, or :45) add 30 seconds.
		// This is for the boundaries :07:30, :22:30, :37:30, and :52:30.
		if (retDate.getMinute() % 15 != 0) {
			retDate = retDate.withSecond(30);
		} else {
			retDate = retDate.withSecond(0);
		}
		
		return retDate;
	}
	
	/**
	 * Checks if the passed LocalDateTime's time is within lunch break (11:00am to 12:59pm)
	 * @param date
	 * @return if the LocalDateTime is during lunch hour.
	 */
	public static boolean isLunchTime(LocalDateTime date) {
		return date.getHour() >= 11 && date.getHour() < 13;
	}
	
	// Builders
	
	/**
	 * Builds a csv file with the sum of all customers that entered the cafeteria from the gates data file for each entry
	 * in the weather data file.
	 * Creates a file with entries for Datetime, Date, DayOfMonth, DayOfWeek, Minute, Temperature, Humidity, Precipitation, InCount, and OutCount.
	 * @param weatherData
	 * @param gatesData
	 */
	public static void buildWeatherGatesFile(List<WeatherData> weatherData, List<GatesData> gatesData) {
		
		PrintWriter weatherGatesFile = null;
		try {
			weatherGatesFile = new PrintWriter("weatherGatesData.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Header for the csv file
		weatherGatesFile.println("Datetime,Date,DayOfMonth,DayOfWeek,Minute,Temp,Humidity,Precipitation,InCount,OutCount");
		
		for (WeatherData weather : weatherData) {
			boolean foundDate = false;
			
			int inCount = 0;
			int outCount = 0;
			for (GatesData gate : gatesData) {
				// If a matching quarter hour in weather data and gates data files has not been found
				if (!foundDate && isMinuteEqual(toQuarterHour(weather.getDate()), toQuarterHour(gate.getDate()))) {
					foundDate = true; // we've found a matching date
					
					// Convert the current weather entry to its closest quarter hour
					LocalDateTime quarterHour = toQuarterHour(weather.getDate());
					
					// Output the weather for this quarter hour to the file we are created.
					weatherGatesFile.print(quarterHour + ","
							+ LocalDate.of(quarterHour.getYear(), quarterHour.getMonthValue(), quarterHour.getDayOfMonth()) + ","
							+ quarterHour.getDayOfMonth() + ","
							+ quarterHour.getDayOfWeek().toString() + ","
							+ (quarterHour.getHour() * 60 + quarterHour.getMinute()) + ","
							+ weather.getTemperature() + ","
							+ weather.getHumidity() + ","
							+ weather.getPercipitation() + ",");
				}
				// If a matching quarter hour in weather data and gates data files has been found
				// and the current gates data entry is still equal
				if (foundDate && isMinuteEqual(toQuarterHour(weather.getDate()), toQuarterHour(gate.getDate()))) {
					// Increment the in and out counts based on the current gates data entry's values
					inCount += gate.getInCount();
					outCount += gate.getOutCount();
					continue;
				}
				// If a matching quarter hour in weather data and gates data files has been found
				// but the current gates data entry is not equal (we have passed that quarter hour in the gates data file)
				if (foundDate) {
					// Add the in and out count sums from the gates file to the file to be outputted.
					weatherGatesFile.println(inCount + "," + outCount);
					break; // Stop looping through the gates data file
				}
			}
		}
		
		weatherGatesFile.close();
		System.out.println("weatherGatesData.csv created.");
	}
	
	/**
	 * Builds a csv file with the sum of all customers that entered the cafeteria from the gates data file for each entry
	 * in the weather data file.
	 * Same as {@link CafeDataParser#buildWeatherGatesFile(List, List)} but with the 'Is severe weather' value outputted.
	 * @param weatherData
	 * @param gatesData
	 */
	public static void buildWeatherGatesFile2(List<WeatherData> weatherData, List<GatesData> gatesData) {
		
		PrintWriter weatherGatesFile = null;
		try {
			weatherGatesFile = new PrintWriter("weatherGatesPrecData.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		weatherGatesFile.println("Datetime,Date,Day of month,Day of week,Minute of day,Temperature (F),Humidity (%),Percipitation,Is severe weather,In count,Out count");
		
		for (WeatherData weather : weatherData) {
			boolean foundDate = false;
			
			int inCount = 0;
			int outCount = 0;
			for (GatesData gate : gatesData) {
				if (!foundDate && isMinuteEqual(toQuarterHour(weather.getDate()), toQuarterHour(gate.getDate()))) {
					foundDate = true;
					
					LocalDateTime quarterHour = toQuarterHour(weather.getDate());
					weatherGatesFile.print(quarterHour + ","
							+ LocalDate.of(quarterHour.getYear(), quarterHour.getMonthValue(), quarterHour.getDayOfMonth()) + ","
							+ quarterHour.getDayOfMonth() + ","
							+ quarterHour.getDayOfWeek().toString() + ","
							+ (quarterHour.getHour() * 60 + quarterHour.getMinute()) + ","
							+ weather.getTemperature() + ","
							+ weather.getHumidity() + ","
							+ (new Precipitation(weather.getPercipitation())).getValue() + ","
							+ weather.isSevereWeather() + ",");
				}
				if (foundDate && isMinuteEqual(toQuarterHour(weather.getDate()), toQuarterHour(gate.getDate()))) {
					inCount += gate.getInCount();
					outCount += gate.getOutCount();
					continue;
				}
				if (foundDate) {
					weatherGatesFile.println(inCount + "," + outCount);
					break;
				}
			}
		}
		
		weatherGatesFile.close();
		System.out.println("weatherGatesPrecData.csv created.");
	}

	/**
	 * Builds a csv file with data from the views data file while converting the datetime value to both minute and day of week
	 * and the age and gender integer values to strings.
	 * Creates a file with entries for Datetime, Minute, DayOfWeek, Age, Gender, AttentionTime, and DwellTime.
	 * @param viewsData
	 */
	public static void buildViewsFile(List<ViewsData> viewsData) {
		PrintWriter viewsFile = null;
		try {
			viewsFile = new PrintWriter("viewsFile.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		viewsFile.println("Datetime,Minute,DayOfWeek,Age,Gender,AttentionTime,DwellTime");
		
		for (ViewsData view : viewsData) {
			viewsFile.println(view.getDate() + ","
					+ (view.getDate().getHour() * 60 + view.getDate().getMinute()) + ","
					+ view.getDate().getDayOfWeek() + ","
					+ view.getAge() + ","
					+ view.getGender() + ","
					+ view.getAttentionTime() + ","
					+ view.getDwellTime());
		}
		
		viewsFile.close();
		
		System.out.println("ViewsFile built.");
	}

	/**
	 * Builds a csv file from the customer list using values for how much healthy and unhealthy items they purchased and the percentage of their meal that is healthy.
	 * Creates entries for Date, DayOfMonth, Minute, DayOfWeek, HealthyCount, UnhealhtyCount, Percentage (of healthy food), Gender, Age, Healthiness of Advertised item,
	 * 	Is Advertised item purchased, Advertised item's temperature, Advertised item's type, Weather Temperature, and Weather Precipitaiton.
	 * @param customers
	 */
	public static void buildHealthyFile(List<Customer> customers) {
		PrintWriter file = null;
		try {
			file = new PrintWriter("healthyData.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		file.println("Datetime,Date,DayOfMonth,Hour,Minute,DayOfWeek,HealthyCount,UnhealthyCount,Percentage,Gender,Age,AdvHealth,BoughtAdv,AdvTemp,AdvType,Temperature,Precipitation");
		
		for (Customer customer : customers) {
			int healthyCount = 0;
			
			// Count the amount of healthy items from the customer's purchased item list.
			for (Item item : customer.getPurchasedItems()) {
				if (item.isHealthy()) {
					healthyCount++;
				}
			}
			
			String advHealth = null;
			String advTemp = null;
			String advType = null;
			
			// Loops through all advertised items the customer had and sets value to 'Both' if any of the values were both
			// healthy and unhealthy, hot and cold, or food and drink.
			for (Item i : customer.getAdvertisedItems()) {
				String itemHealth = i.isHealthy() ? "Healthy" : "Unhealthy";
				
				if (advHealth == null) {
					advHealth = itemHealth;
				}
				else if (!advHealth.equals(itemHealth)) {
					advHealth = "Both";
				}
					
				if (advTemp == null) {
					advTemp = i.getTemp();
				}
				else if (!advTemp.equals(i.getTemp())) {
					advTemp = "Both";
				}
				
				if (advType == null) {
					advType = i.getType();
				}
				else if (!advType.equals(i.getType())) {
					advType = "Both";
				}
			}
			
			file.println(customer.getDate() + ","
					+ LocalDate.of(customer.getDate().getYear(), customer.getDate().getMonthValue(), customer.getDate().getDayOfMonth()) + ","
					+ customer.getDate().getDayOfMonth() + ","
					+ customer.getDate().getHour() + ","
					+ (customer.getDate().getHour() * 60 + customer.getDate().getMinute()) + ","
					+ customer.getDayOfWeek() + ","
					+ healthyCount + ","
					+ (customer.getPurchasedItems().size() - healthyCount) + ","
					+ ((double)healthyCount / (double)customer.getPurchasedItems().size() * 100.0) + ","
					+ customer.getGender() + ","
					+ customer.getAge() + ","
					+ advHealth + ","
					+ customer.getBoughtAdvertised() + ","
					+ advTemp + ","
					+ advType + ","
					+ customer.getTemperature() + ","
					+ customer.getPrecipitation()
			);
		}
		
		file.close();
		System.out.println("healthyData.csv created.");
	}
	
	/**
	 * Static method used to build an R script whose purpose is for testing data outputted from {@link CafeDataParser#buildHealthyFile(List)} in R.
	 */
	public static void buildHealthyScript() {
		// Different combinations of all independent variables from the healthy data file.
		String[][] independentVars = { 
				{ "DayOfMonth", "DayOfWeek", "Minute", "Gender", "Age", "AdvHealth", "AdvTemp", "Temperature", "Precipitation" },
				{ "DayOfWeek", "Minute", "Gender", "Age", "AdvHealth", "AdvTemp", "Temperature", "Precipitation", "DayOfMonth" },
				{ "Minute", "Gender", "Age", "AdvHealth", "AdvTemp", "Temperature", "Precipitation", "DayOfMonth", "DayOfWeek" },
				{ "Gender", "Age", "AdvHealth", "AdvTemp", "Temperature", "Precipitation", "DayOfMonth", "DayOfWeek", "Minute" },
				{ "Age", "AdvHealth", "AdvTemp", "Temperature", "Precipitation", "DayOfMonth", "DayOfWeek", "Minute", "Gender" },
				{ "AdvHealth", "AdvTemp", "Temperature", "Precipitation", "DayOfMonth", "DayOfWeek", "Minute", "Gender", "Age" },
				{ "AdvTemp", "Temperature", "Precipitation", "DayOfMonth", "DayOfWeek", "Minute", "Gender", "Age", "AdvHealth" },
				{ "Temperature", "Precipitation", "DayOfMonth", "DayOfWeek", "Minute", "Gender", "Age", "AdvHealth", "AdvTemp" },
				{ "Precipitation", "DayOfMonth", "DayOfWeek", "Minute", "Gender", "Age", "AdvHealth", "AdvTemp", "Temperature" }
		};
		// The dependent variable from the healthy data file.
		String dependentVar = "boughtHealthy";
		
		PrintWriter file = null;
		try {
			file = new PrintWriter("healthyScript.R");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Function name
		file.println("healthyTester <- function(dataset) {");
		
		// '\t' is used to tab the code so the final script is readable
		// Used to randomize the inputted dataset and create train and test data from this.
		file.println("\trandData <- dataset[sample(1:nrow(dataset)),]");
		file.println("\ttrainData <- randData[1:(floor(nrow(randData)*0.8)),]");
		file.println("\ttestData <- randData[(floor(nrow(randData)*0.8)+1):nrow(randData),]");
		file.println();
		
		// Loop through each combination of independent variables
		for (int arr = 0; arr < independentVars.length; arr++) {
			// Loop through the current combination of independent variables
			for (int i = 0; i < independentVars[arr].length; i++) {
				// Loop through the current combination of independent variables that decreases in size.
				for (int j = i+1; j < independentVars[arr].length + 1; j++) {
					// Create the list of independent variables used
					StringBuilder variables = new StringBuilder();
					variables.append(independentVars[arr][i]);
					
					// Outputs the line to create the model using naiveBayes and the combination of independent variables.
					file.print("\tm <- naiveBayes(" + dependentVar + " ~ " + independentVars[arr][i]);
					
					// Only output the '+' following the first independent variable and the next variable and '+' if they
					// exist in the independentVars list being looped through.
					if (j < independentVars[arr].length) {
						variables.append(","+independentVars[arr][j]);
						file.print("+" + independentVars[arr][j]);
						if (j < independentVars[arr].length - 1) {
							file.print("+");
						}
					}
					
					// Loop through the rest of the independent variables
					for (int k = j+1; k < independentVars[arr].length; k++) {
						variables.append(","+independentVars[arr][k]);
						file.print(independentVars[arr][k]);
						// Output a '+' if not at the end of the week.
						if (k < independentVars[arr].length - 1) {
							file.print("+");
						}
					}
					// Finish the naiveBayes R statement.
					file.print(", data = trainData)\n");
					// Output the predictor value using the predict method
					file.println("\tp <- predict(m, testData)");
					// Output the variables used to create the above predictor and its accuracy.
					file.println("\tprint(sprintf(\"(" + variables.toString() + "): %s\", prop.table(table(p == testData$" + dependentVar + "))[2]))");
					file.println();
				}
			}
		}
		
		// Close the function
		file.println("}");
		file.close();
		System.out.println("healthyScript.R created.");
	}
	
	/**
	 * Static method used to build an R script whose purpose is for testing data outputted from {@link CafeDataParser#buildWeatherGatesFile(List, List)} in R.
	 * Method is identical to {@link CafeDataParser#buildHealthyScript()} but converted for the weather and gates file.
	 */
	public static void buildWeatherScript() {
		String[][] independentVars = { 
				{ "DayOfMonth", "DayOfWeek", "Minute", "Temperature", "Precipitation", "Humidity" },
				{ "DayOfWeek", "Minute", "Temperature", "Precipitation", "Humidity", "DayOfMonth" },
				{ "Minute", "Temperature", "Precipitation", "Humidity", "DayOfMonth", "DayOfWeek" },
				{ "Temperature", "Precipitation", "Humidity", "DayOfMonth", "DayOfWeek", "Minute" },
				{ "Precipitation", "Humidity", "DayOfMonth", "DayOfWeek", "Minute", "Temperature" },
				{ "Humidity", "DayOfMonth", "DayOfWeek", "Minute", "Temperature", "Precipitation" }
		};
		String dependentVar = "InCount";
		
		PrintWriter file = null;
		try {
			file = new PrintWriter("weatherScript.R");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		file.println("weatherTester <- function(dataset) {");
		
		file.println("\trandData <- dataset[sample(1:nrow(dataset)),]");
		file.println("\ttrainData <- randData[1:(floor(nrow(randData)*0.8)),]");
		file.println("\ttestData <- randData[(floor(nrow(randData)*0.8)+1):nrow(randData),]");
		file.println();
		
		for (int arr = 0; arr < independentVars.length; arr++) {
			for (int i = 0; i < independentVars[arr].length; i++) {
				for (int j = i+1; j < independentVars[arr].length + 1; j++) {
					StringBuilder variables = new StringBuilder();
					variables.append(independentVars[arr][i]);
					file.print("\tm <- M5P(" + dependentVar + " ~ " + independentVars[arr][i]);
					if (j < independentVars[arr].length) {
						variables.append(","+independentVars[arr][j]);
						file.print("+" + independentVars[arr][j]);
						if (j < independentVars[arr].length - 1) {
							file.print("+");
						}
					}
					for (int k = j+1; k < independentVars[arr].length; k++) {
						variables.append(","+independentVars[arr][k]);
						file.print(independentVars[arr][k]);
						if (k < independentVars[arr].length - 1) {
							file.print("+");
						}
					}
					file.print(", data = trainData)\n");
					file.println("\tp <- predict(m, testData)");
					file.println("\tprint(sprintf(\"(" + variables.toString()
							+ "): %f\", rSquared(actualValues = testData$" + dependentVar
							+ ", predictedValues = p)))");
					file.println();
				}
			}
		}
		
		file.println("}");
		file.close();
		System.out.println("weatherScript.R created.");
	}
}
