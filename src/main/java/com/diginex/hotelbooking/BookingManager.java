package com.diginex.hotelbooking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.diginex.hotelbooking.config.BookingResource;
import com.diginex.hotelbooking.model.Booking;
import com.diginex.hotelbooking.model.Room;

public class BookingManager {

	private List<Booking> bookings;

	private List<Room> rooms;

	public BookingManager() {
		super();
		this.bookings = new ArrayList<>();
		this.rooms = initializeRooms();
	}

	private List<Room> initializeRooms() {
		List<Room> rooms = new ArrayList<>();
		int totalRooms = BookingResource.getTotalRooms();
		while (totalRooms > 0) {
			rooms.add(new Room(totalRooms));
			totalRooms--;
		}
		return rooms;
	}

	public synchronized boolean storeBooking(Booking booking) {
		return this.bookings.add(booking);
	}

	public synchronized List<Room> findAvailableRooms(LocalDate date) {

		List<Room> availableRooms = new ArrayList<>();
		List<Booking> bookingsOnDate = findBookings(date);
		List<Integer> reservedRooms = bookingsOnDate.stream().map(Booking::getRoomNumber).collect(Collectors.toList());

		availableRooms = this.rooms.stream().filter(r -> !reservedRooms.contains(r.getRoomNumber()))
				.collect(Collectors.toList());

		return availableRooms;
	}

	public synchronized List<Booking> findBookings(LocalDate date) {
		return this.bookings.stream().filter(b -> b.getBookingDate().compareTo(date) == 0).collect(Collectors.toList());
	}

	public synchronized List<Booking> findBookings(String guestName) {

		return this.bookings.stream().filter(b -> b.getGuestName().equalsIgnoreCase(guestName))
				.collect(Collectors.toList());
	}

	public synchronized List<Room> findAllRooms() {
		return this.rooms;
	}

	public synchronized List<Booking> findAllBookings() {
		return this.bookings;
	}

}
