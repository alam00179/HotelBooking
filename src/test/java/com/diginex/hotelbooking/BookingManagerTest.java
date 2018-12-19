package com.diginex.hotelbooking;

import static org.junit.Assert.assertEquals;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.diginex.hotelbooking.config.BookingResource;
import com.diginex.hotelbooking.model.Booking;
import com.diginex.hotelbooking.model.Room;

public class BookingManagerTest {

	private BookingManager bm;
	private LocalDate today;

	@BeforeClass
	public static void globalSetUp() {

		givenMinimumRoomNumber(2);
	}

	@Before
	public void initialize() {

		bm = new BookingManager();
		today = LocalDate.now();
	}

	@Test
	public void test_storeBooking() {
		String name = "Khairul Alam";

		Booking booking = new Booking(1, today, 1, name);

		assert bm.storeBooking(booking);
		
		List<Booking> bookings = bm.findBookings(name);

		assert name.equals(bookings.get(0).getGuestName());
	}

	@Test
	public void test_findAvailableRooms() {

		bm.storeBooking(new Booking(1, today, 1, "Alam"));
		bm.storeBooking(new Booking(2, today, 2, "Alam"));
		bm.storeBooking(new Booking(2, today.plusDays(1), 2, "Alam"));

		List<Room> availableRooms = bm.findAvailableRooms(today);
		List<Booking> bookingsOnDate = bm.findBookings(today);

		int remainingRooms = BookingResource.getTotalRooms() - bookingsOnDate.size();

		assertEquals(remainingRooms, availableRooms.size());

		assert !availableRooms.stream().anyMatch(r -> r.getRoomNumber() == 1);
		assert !availableRooms.stream().anyMatch(r -> r.getRoomNumber() == 2);

	}

	@Test
	public void test_findBookingsByGuest() {

		bm.storeBooking(new Booking(1, today, 1, "Alam"));
		bm.storeBooking(new Booking(1, today.plusDays(1), 1, "Alam"));
		
		bm.storeBooking(new Booking(2, today, 2, "khairul"));

		assertEquals(2, bm.findBookings("alam").size());
		assertEquals(1, bm.findBookings("KHAIRUL").size());
	}

	@Test
	public void test_findBookingsByDate() {

		bm.storeBooking(new Booking(1, today, 1, "Alam"));
		bm.storeBooking(new Booking(2, today, 2, "Alam"));

		bm.storeBooking(new Booking(2, today.plusDays(1), 2, "Alam"));

		List<Booking> bookings = bm.findBookings(today);
		assertEquals(2, bookings.size());
	}

	@Test
	public void test_findAllRooms() {

		assertEquals(BookingResource.getTotalRooms(), bm.findAllRooms().size());
	}

	private static void givenMinimumRoomNumber(int totalRooms) {

		if (BookingResource.getTotalRooms() < totalRooms) {

			Properties prop = new Properties();
			OutputStream output = null;

			try {

				output = new FileOutputStream("src/main/resources/booking.properties");

				prop.setProperty(BookingResource.TOTAL_ROOMS, String.valueOf(totalRooms));
				prop.store(output, null);

			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}

}
