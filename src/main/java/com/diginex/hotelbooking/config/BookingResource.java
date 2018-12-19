package com.diginex.hotelbooking.config;

import java.util.ResourceBundle;

public class BookingResource {

	public static final String TOTAL_ROOMS = "TOTAL_ROOMS";

	public static int getTotalRooms() {
	
		ResourceBundle resource = ResourceBundle.getBundle("booking");
		
		return Integer.parseInt(resource.getString(TOTAL_ROOMS));
	}
	
	
}
