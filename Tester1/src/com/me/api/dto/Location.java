package com.me.api.dto;

public class Location {
	
	public Location(String latitude, String longitute) {
		
		this.setLatitude(latitude);
		this.setLongitute(longitute);
		
	}
	
	private String latitude;
	private String longitute;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitute() {
		return longitute;
	}

	public void setLongitute(String longitute) {
		this.longitute = longitute;
	}
}
