package cafe.data.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cafe.data.parser.wrapper.Age;
import cafe.data.parser.wrapper.Gender;
import cafe.data.parser.wrapper.Globals;
import cafe.data.parser.wrapper.Precipitation;

public class CafeDataParser {
	
	// Flag which lists you would like to build or are using.
	public static final boolean BUILD_WEATHER = true;
	public static final boolean BUILD_GATES = true;
	public static final boolean BUILD_VIEWS = true;
	public static final boolean BUILD_POS = true;
	
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
				
				while ((line = br.readLine()) != null) {
					count++;
					if (!line.isEmpty()) {
						
						String[] dataEntry = line.split(",");
						if (dataEntry.length != Globals.W_COL_COUNT) {
							System.out.println("Invalid entry on line " + count);
							continue;
						}
						
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
				LocalDateTime currentDate = null;
				
				while ((line = br.readLine()) != null) {
					count++;
					if (!line.isEmpty()) {
						
						String[] dataEntry = line.split(",");
						if (dataEntry.length != Globals.P_COL_COUNT) {
							System.out.println("Invalid entry on line " + count);
							continue;
						}
						
						int age = parseInt(dataEntry[Globals.P_AGE_ID]);
						int gender = parseInt(dataEntry[Globals.P_GENDER_ID]);
						LocalDateTime posDate = LocalDateTime.parse(dataEntry[Globals.P_DATE], dateFormatter);
						String dayOfWeek = dataEntry[Globals.P_DOW];
						int itemId = parseInt(dataEntry[Globals.P_ITEM_ID]);
						String itemType = dataEntry[Globals.P_ITEM_TYPE];
						String itemTemp = dataEntry[Globals.P_ITEM_TEMP];
						String itemHealth = dataEntry[Globals.P_ITEM_HEALTH];
						int advItemId = parseInt(dataEntry[Globals.P_SALE_ITEM]);
						String advItemType = dataEntry[Globals.P_SALE_TYPE];
						String advItemTemp = dataEntry[Globals.P_SALE_TEMP];
						String advItemHealth = dataEntry[Globals.P_SALE_HEALTH];
						boolean boughtAdvertised = parseBoolean(dataEntry[Globals.P_PURCHASE_SALE]);
						double temperature = toFahrenheit(parseDouble(dataEntry[Globals.P_TEMP]));
						String precipitation = dataEntry[Globals.P_PERCIPITATION];
						
						if (!posDate.equals(currentDate)) {
							currentDate = posDate;
							customers.add(new Customer(posDate, dayOfWeek, gender, age, boughtAdvertised, temperature, precipitation));
						}
						if (posDate.equals(currentDate)) {
							customers.get(customers.size()-1).addItem(new Item(
									itemId,	itemType, itemTemp, itemHealth));
							customers.get(customers.size()-1).addAdvertisedItem(new Item(advItemId, advItemType, advItemTemp, advItemHealth));
							if (boughtAdvertised) {
								customers.get(customers.size()-1).setBoughtAdvertised(boughtAdvertised);
							}
						}
						
						posData.add(new PointOfSaleData(
								age,
								gender,
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
								posDate,
								dayOfWeek,
								itemId,
								itemType,
								itemTemp,
								itemHealth,
								parseBoolean(dataEntry[Globals.P_PURCHASE_SALE])));
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
		
		
		buildWeatherGatesFile(weatherData, gatesData);

		buildWeatherGatesFile2(weatherData, gatesData);
		
		buildHealthyFile(customers);
		
		buildHealthyScript();
		
		buildViewsFile(viewsData);
		
		buildHealthyFile(customers);
		
		buildGatesViewsFile(gatesData, viewsData);
	}
	
	// Static helper methods
	
	public static Integer parseInt(String s) {
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
	
	public static String splitDateTime(String line) {
		StringBuilder sb = new StringBuilder();
		
		String[] lines = line.split(",");
		
		for (String s : lines) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			try {
				LocalDateTime datetime = LocalDateTime.parse(s, formatter);
				sb.append(datetime.toLocalDate().toString() + ",");
				sb.append(datetime.getHour() + ",");
			}
			catch (DateTimeParseException e) {
				sb.append(s + ",");
			}
		}
		
		return sb.toString();
	}
	
	public static double toFahrenheit(double celsius) {
		return celsius * 1.8 + 32.0;
	}
	
	/**
	 * 
	 * @param temperature in fahrenheit
	 * @param humidity in percentage
	 * @return the heat index temperature value
	 * @throws IllegalArgumentException if temperature is not 70 degrees Fahrenheit or greater.
	 */
	public static double getHeatIndex(double temperature, double humidity) {
		if (temperature < 70.0) {
			throw new IllegalArgumentException("Temperature must be 70 degrees Fahrenheit or greater to calculate the heat index.");
		}
		
		final double C1 = -42.379;
		final double C2 = 2.04901523;
		final double C3 = 10.14333127;
		final double C4 = -0.22475541;
		final double C5 = -0.00683783;
		final double C6 = -0.05481717;
		final double C7 = -0.00122874;
		final double C8 = 0.00085282;
		final double C9 = -0.00000199;
		
		return C1 + (C2 * temperature) + (C3 * humidity) + (C4 * temperature * humidity)
				+ (C5 * (temperature * temperature)) + (C6 * (humidity * humidity))
				+ (C7 * (temperature * temperature) * humidity) + (C8 * temperature * (humidity * humidity))
				+ (C9 * (temperature * temperature) * (humidity * humidity));
	}

	/**
	 * 
	 * @param temperature in fahrenheit
	 * @param humidity in percentage
	 * @return the wind chill temperature value
	 */
	public static double getWindChill(double temperature, double humidity) {
		final double WIND_SPEED = 10.0; // Lexington, KY winter average
		return 35.74 + (0.6215 * temperature) - (35.75 * Math.pow(WIND_SPEED, 0.16))
				+ (0.4275 * temperature * Math.pow(WIND_SPEED, 0.16));
	}
	
	public static boolean isMinuteEqual(LocalDateTime datetime1, LocalDateTime datetime2) {
		return datetime1.getYear() == datetime2.getYear()
				&& datetime1.getMonthValue() == datetime2.getMonthValue()
				&& datetime1.getDayOfMonth() == datetime2.getDayOfMonth()
				&& datetime1.getHour() == datetime2.getHour()
				&& datetime1.getMinute() == datetime2.getMinute();
	}

	public static LocalDateTime toQuarterHour(LocalDateTime date) {
		int minuteMod = date.getMinute() % 15;
		
		LocalDateTime retDate = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
				date.getHour(), date.getMinute());
		if (minuteMod < 8) {
			retDate = retDate.minusMinutes((long)minuteMod);
		} else {
			retDate = retDate.plusMinutes((long)(15 - minuteMod));
		}
		return retDate;
	}
	
	public static LocalDateTime toEigthHour(LocalDateTime date) {
		double minuteMod = date.getMinute() % 7.5;
		
		LocalDateTime retDate = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
				date.getHour(), date.getMinute());
		
		if (minuteMod <= 3.75) {
			retDate = retDate.minusMinutes((long)minuteMod + 1);
			double remainder = minuteMod - (int)minuteMod;
			if (remainder > 0) {
				retDate = retDate.withSecond(30);
			}
		} else {
			retDate = retDate.plusMinutes((long)(7.5 - minuteMod));
			double remainder = (7.5 - minuteMod) - (int)(7.5 - minuteMod);
			if (remainder > 0) {
				retDate = retDate.withSecond(30);
			}
		}
		return retDate;
	}
	
	public static boolean isLunchTime(LocalDateTime date) {
		return date.getHour() >= 11 && date.getHour() < 13;
	}
	
	// Builders
	
	public static void buildWeatherGatesFile(List<WeatherData> weatherData, List<GatesData> gatesData) {
		
		PrintWriter weatherGatesFile = null;
		try {
			weatherGatesFile = new PrintWriter("weatherGatesData.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		weatherGatesFile.println("Datetime,Date,DayOfMonth,DayOfWeek,Minute,Temp,Humidity,Precipitation,InCount,OutCount");
		
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
							+ weather.getPercipitation() + ",");
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
		System.out.println("weatherGatesData.csv created.");
	}
	
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
	
	public static void buildGatesViewsFile(List<GatesData> gatesData, List<ViewsData> viewsData) {
		PrintWriter gatesViewsFile = null;
		try {
			gatesViewsFile = new PrintWriter("gatesViewsData.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		gatesViewsFile.println();
		
		for (GatesData gate : gatesData) {
			boolean foundDate = false;
			
			Map<Gender, Integer> genderCount = new LinkedHashMap<Gender, Integer>();
			genderCount.put(new Gender(Gender.MALE), 0); genderCount.put(new Gender(Gender.FEMALE), 0); genderCount.put(new Gender(Gender.UNKNOWN), 0);
			
			Map<Age, Integer> ageCount = new LinkedHashMap<Age, Integer>();
			ageCount.put(new Age(Age.CHILD), 0); ageCount.put(new Age(Age.YOUNG_ADULT), 0); ageCount.put(new Age(Age.ADULT), 0); ageCount.put(new Age(Age.SENIOR), 0); ageCount.put(new Age(Age.UNKNOWN), 0);
			
			for (ViewsData view : viewsData) {
				if (!foundDate && toEigthHour(gate.getDate()).equals(toEigthHour(view.getDate()))) {
					foundDate = true;
				}
				if (foundDate && toEigthHour(gate.getDate()).equals(toEigthHour(view.getDate()))) {
					genderCount.put(view.getGender(), genderCount.get(view.getGender()) + 1);
					ageCount.put(view.getAge(), ageCount.get(view.getAge()) + 1);
					continue;
				}
				if (foundDate) {
					for (Gender gender : genderCount.keySet()) {
						gatesViewsFile.print(genderCount.get(gender) + ",");
					}
					break;
				}
			}
		}
		
		gatesViewsFile.close();
	}

	public static void buildHealthyFile(List<Customer> customers) {
		PrintWriter file = null;
		try {
			file = new PrintWriter("healthyData.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		file.println("Date,DayOfMonth,Minute,DayOfWeek,HealthyCount,UnhealthyCount,Percentage,Gender,Age,AdvHealth,BoughtAdv,AdvTemp,AdvType,Temperature,Precipitation");
		
		for (Customer customer : customers) {
			int healthyCount = 0;
			for (Item item : customer.getPurchasedItems()) {
				if (item.isHealthy()) {
					healthyCount++;
				}
			}
			
			String advHealth = null;
			String advTemp = null;
			String advType = null;
			
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
					+ customer.getDate().getDayOfMonth() + ","
					+ (customer.getDate().getHour() * 60) + customer.getDate().getMinute() + ","
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
	
	public static void buildHealthyScript() {
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
		String dependentVar = "boughtHealthy";
		
		PrintWriter file = null;
		try {
			file = new PrintWriter("healthyScript.R");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		file.println("healthyTester <- function(dataset) {");
		
		file.println("\trandData <- dataset[sample(1:nrow(dataset)),]");
		file.println("\ttrainData <- randData[1:(floor(nrow(randData)*0.8)),]");
		file.println("\ttestData <- randData[(floor(nrow(randData)*0.8)+1):nrow(randData),]");
		file.println();
		
		for (int arr = 0; arr < independentVars.length; arr++) {
			for (int i = 0; i < independentVars[arr].length; i++) {
				for (int j = i+1; j < independentVars[arr].length + 1; j++) {
					StringBuilder variables = new StringBuilder();
					variables.append(independentVars[arr][i]);//.substring(0, 2));
					file.print("\tm <- naiveBayes(" + dependentVar + " ~ " + independentVars[arr][i]);
					if (j < independentVars[arr].length) {
						variables.append(","+independentVars[arr][j]);//.substring(0, 2));
						file.print("+" + independentVars[arr][j]);
						if (j < independentVars[arr].length - 1) {
							file.print("+");
						}
					}
					for (int k = j+1; k < independentVars[arr].length; k++) {
						variables.append(","+independentVars[arr][k]);//.substring(0, 2));
						file.print(independentVars[arr][k]);
						if (k < independentVars[arr].length - 1) {
							file.print("+");
						}
					}
					file.print(", data = trainData)\n");
					file.println("\tp <- predict(m, testData)");
					file.println("\tprint(sprintf(\"(" + variables.toString() + "): %s\", prop.table(table(p == testData$" + dependentVar + "))[2]))");
					file.println();
				}
			}
		}
		
		file.println("}");
		file.close();
		System.out.println("healthyScript.R created.");
	}
	
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
					variables.append(independentVars[arr][i]);//.substring(0, 2));
					file.print("\tm <- M5P(" + dependentVar + " ~ " + independentVars[arr][i]);
					if (j < independentVars[arr].length) {
						variables.append(","+independentVars[arr][j]);//.substring(0, 2));
						file.print("+" + independentVars[arr][j]);
						if (j < independentVars[arr].length - 1) {
							file.print("+");
						}
					}
					for (int k = j+1; k < independentVars[arr].length; k++) {
						variables.append(","+independentVars[arr][k]);//.substring(0, 2));
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
