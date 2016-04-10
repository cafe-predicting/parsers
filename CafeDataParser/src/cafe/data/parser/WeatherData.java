package cafe.data.parser;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class WeatherData {
	private LocalDateTime datetime;
	private double temperature; // fahrenheit
	private int humidity; // percentage
	private String percipitation;
	private boolean isSevereWeather;
	
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
