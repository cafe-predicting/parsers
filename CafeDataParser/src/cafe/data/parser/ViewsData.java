package cafe.data.parser;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import cafe.data.parser.wrapper.Age;
import cafe.data.parser.wrapper.Gender;

public class ViewsData {
	private int locationId;
	private Gender gender;
	private Age age;
	private LocalDateTime datetime;
	private int dwellTime;
	private int attentionTime;
	private int watcherCount;
	//private int res_1;
	
	public ViewsData(int locationId, int gender, int age, LocalDateTime datetime, int dwellTime, int attentionTime, int watcherCount) {
		this.locationId = locationId;
		this.gender = new Gender(gender);
		this.age = new Age(age);
		this.datetime = datetime;
		this.dwellTime = dwellTime;
		this.attentionTime = attentionTime;
		this.watcherCount = watcherCount;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public Age getAge() {
		return age;
	}
	
	public LocalDateTime getDate() {
		return datetime;
	}
	
	public int getDwellTime() {
		return dwellTime;
	}
	
	public int getAttentionTime() {
		return attentionTime;
	}
}
