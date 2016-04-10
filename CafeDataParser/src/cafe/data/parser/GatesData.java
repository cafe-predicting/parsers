package cafe.data.parser;

import java.time.LocalDateTime;

/**
 * Object used to represent entries from the gates data file.
 * @author Ryan Zembrodt
 */
public class GatesData {
	private int locationId;
	private int gateId;
	private LocalDateTime datetime;
	private int gateDuration;
	private int inCount;
	private int outCount;
	
	public GatesData(int locationId, int gateId, LocalDateTime datetime, int gateDuration, int inCount, int outCount) {
		this.locationId = locationId;
		this.gateId = gateId;
		this.datetime = datetime;
		this.gateDuration = gateDuration;
		this.inCount = inCount;
		this.outCount = outCount;
	}
	
	public LocalDateTime getDate() {
		return datetime;
	}
	
	public int getGate() {
		return gateId;
	}
	
	public int getGateDuration() {
		return gateDuration;
	}
	
	public int getInCount() {
		return inCount;
	}
	
	public int getOutCount() {
		return outCount;
	}
}
