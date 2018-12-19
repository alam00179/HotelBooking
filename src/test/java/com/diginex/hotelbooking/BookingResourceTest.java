package com.diginex.hotelbooking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.diginex.hotelbooking.config.BookingResource;


public class BookingResourceTest {

	@Test
	public void test_getTotalRooms() {
		
		BookingManager bm = new BookingManager();
		
		assertEquals(bm.findAllRooms().size(), BookingResource.getTotalRooms());
	}
}
