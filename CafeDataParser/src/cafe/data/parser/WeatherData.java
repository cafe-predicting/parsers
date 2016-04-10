package cafe.data.parser;

import java.time.LocalDateTime;

/**
 * Object representing entries in the weather data file.
 * @author Ryan Zembrodt
 */
public class WeatherData {
	private LocalDateTime datetime;
	private double temperature; // Fahrenheit
	private int humidity; // percentage
	private String percipitation;
	private boolean isSevereWeather;
	
	/**
	 * WeatherData object built from the raw data in the weather data file.
	 * @param datetime
	 * @param temperature represented in Fahrenheit.
	 * @param humidity
	 * @param percipitation
	 * @param isSevereWeather
	 */
	public WeatherData(LocalDateTime datetime, double temperature, int humidity, String percipitation, boolean isSevereWeather) {
		this.datetime = datetime;
		this.temperature = temperature;
		this.humidity = humidity;
		this.percipitation = percipitation;
		this.isSevereWeather = isSevereWeather;
	}
	
	public LocalDateTime getDate() {
		return datetime;
	}
	
	public double getTemperature() {
		return temperature;
	}
	
	public int getHumidity() {
		return humidity;
	}
	
	public String getPercipitation() {
		return percipitation;
	}
	
	public boolean isSevereWeather() {
		return isSevereWeather;
	}
}
