package com.diginex.hotelbooking.model;

import java.time.LocalDate;

public class Booking {

	private int bookingNumber;
	private String guestName;
	private LocalDate reservedDate;
	private int roomNumber;

	public Booking(int bookingNumber, LocalDate reservedDate, int roomNumber, String guestName) {

		this.bookingNumber = bookingNumber;
		this.reservedDate = reservedDate;
		this.roomNumber = roomNumber;
		this.guestName = guestName;
	}

	public int getBookingNumber() {
		return bookingNumber;
	}

	public String getGuestName() {
		return guestName;
	}

	public LocalDate getBookingDate() {
		return reservedDate;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

}
