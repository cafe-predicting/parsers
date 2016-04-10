package cafe.data.parser;

import java.time.LocalDateTime;

import cafe.data.parser.wrapper.Age;
import cafe.data.parser.wrapper.Gender;

/**
 * Object representing entires in the views data file.
 * @author Ryan Zembrodt
 */
public class ViewsData {
	private int locationId;
	private Gender gender;
	private Age age;
	private LocalDateTime datetime;
	private int dwellTime;
	private int attentionTime;
	private int watcherCount;

	/**
	 * Builds a ViewsData object from the raw views data values.
	 * @param locationId
	 * @param gender integer that is then represented by a {@link Gender} object
	 * @param age integer that is then represented by a {@link Age} object
	 * @param datetime
	 * @param dwellTime
	 * @param attentionTime
	 * @param watcherCount
	 */
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
