package edu.ncsu.csc216.wolf_scheduler.course;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import edu.ncsu.csc216.wolf_scheduler.scheduler.WolfScheduler;

/**
 * Tests the Event class.
 * @author Sarah Heckman
 */
public class EventTest {
	
	/** Event title */
	private static final String EVENT_TITLE = "Exercise";
	/** Event meeting days */
	private static final String EVENT_MEETING_DAYS = "MTWHF";
	/** Event start time */
	private static final int EVENT_START_TIME = 800;
	/** Event end time */
	private static final int EVENT_END_TIME = 900;
	/** Event details */
	private static final String EVENT_DETAILS = "Cardio Time!";
	
	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";

	/**Test Event.Event().*/
	@Test
	public void testEvent() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Event(null, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS));
		assertEquals("Invalid title.", e1.getMessage(), "Incorrect exception thrown with invalid event name - " + null);
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Event("", EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS));
		assertEquals("Invalid title.", e2.getMessage(), "Incorrect exception thrown with invalid event name - " + null);
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Event(EVENT_TITLE, null, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS));
		assertEquals("Invalid meeting days and times.", e3.getMessage(), "Incorrect exception thrown with invalid event name - " + null);

		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> new Event(EVENT_TITLE, "", EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS));
		assertEquals("Invalid meeting days and times.", e4.getMessage(), "Incorrect exception thrown with invalid event name - " + null);

		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> new Event(EVENT_TITLE, EVENT_MEETING_DAYS, 1897, EVENT_END_TIME, EVENT_DETAILS));
		assertEquals("Invalid meeting days and times.", e5.getMessage(), "Incorrect exception thrown with invalid event name - " + null);

		Exception e6 = assertThrows(IllegalArgumentException.class,
				() -> new Event(EVENT_TITLE, EVENT_MEETING_DAYS, -1, EVENT_END_TIME, EVENT_DETAILS));
		assertEquals("Invalid meeting days and times.", e6.getMessage(), "Incorrect exception thrown with invalid event name - " + null);

		Exception e7 = assertThrows(IllegalArgumentException.class,
				() -> new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, 997, EVENT_DETAILS));
		assertEquals("Invalid meeting days and times.", e7.getMessage(), "Incorrect exception thrown with invalid event name - " + null);

		Exception e8 = assertThrows(IllegalArgumentException.class,
				() -> new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, 90, EVENT_DETAILS));
		assertEquals("Invalid meeting days and times.", e8.getMessage(), "Incorrect exception thrown with invalid event name - " + null);

		Exception e9 = assertThrows(IllegalArgumentException.class,
				() -> new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, null));
		assertEquals("Invalid event details.", e9.getMessage(), "Incorrect exception thrown with invalid event name - " + null);
		
		//Test a valid event
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		assertAll("Event",
			() -> assertEquals(EVENT_TITLE, event.getTitle(), "incorrect title"),
			() -> assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays(), "incorrect meeting days"),
			() -> assertEquals(EVENT_START_TIME, event.getStartTime(), "incorrect start time"),
			() -> assertEquals(EVENT_END_TIME, event.getEndTime(), "incorrect end time"),
			() -> assertEquals(EVENT_DETAILS, event.getEventDetails(), "incorrect end time"));	
	}
	
	/**
	 * Test Event.Event().
	 */
	@Test
	public void testEventTimes() {
		// Test case 3: Test overlapping event time
	    WolfScheduler schedule = new WolfScheduler(validTestFile);

	    assertThrows(IllegalArgumentException.class, () -> {
	        schedule.addEventToSchedule("Event 4", "MWF", 9, 10, "Event details");
	        schedule.addEventToSchedule("Event 5", "MWF", 8, 9, "Event details");
	    });
	    
	    // Test case 4: Test event time starts at the same time
	    assertThrows(IllegalArgumentException.class, () -> {
	        schedule.addEventToSchedule("Event 6", "MWF", 9, 10, "Event details");
	        schedule.addEventToSchedule("Event 7", "MWF", 9, 11, "Event details");
	    });
	    
	    // Test case 5: Test event time ends at the same time
	    assertThrows(IllegalArgumentException.class, () -> {
	        schedule.addEventToSchedule("Event 8", "MWF", 9, 10, "Event details");
	        schedule.addEventToSchedule("Event 9", "MWF", 8, 10, "Event details");
	    });
	    
	    // Test case 6: Test event time starts at the end of another event time
	    assertThrows(IllegalArgumentException.class, () -> {
	        schedule.addEventToSchedule("Event 10", "MWF", 9, 10, "Event details");
	        schedule.addEventToSchedule("Event 11", "MWF", 10, 11, "Event details");
	    });
	    
	    // Test case 7: Test event time ends at the start of another event time
	    assertThrows(IllegalArgumentException.class, () -> {
	        schedule.addEventToSchedule("Event 12", "MWF", 9, 10, "Event details");
	        schedule.addEventToSchedule("Event 13", "MWF", 8, 9, "Event details");
	    });
	    
	    // Test case 8: Test event time starts inside another event time
	    assertThrows(IllegalArgumentException.class, () -> {
	        schedule.addEventToSchedule("Event 14", "MWF", 9, 10, "Event details");
	        schedule.addEventToSchedule("Event 15", "MWF", 9, 9, "Event details");
	    });
	}

	/**
	 * Tests setMeetingDaysAndTime().
	 * @param meetingString valid meeting string
	 * @param startTime valid start time
	 * @param endTime valid end time
	 * @param expectedStartTime expected start time from the first three arguments
	 * @param expectedEndTime expected end time from the first three arguments
	 */
	@ParameterizedTest(name = "{index} => meetingString={0}, startTime={1}, endTime={2}, expectedStartTime={3}, expectedEndTime={4}")
	@CsvSource({
		"UMTWF,1000,1100,1000,1100",
		"MWF,1350,1445,1350,1445"
	})
	public void testSetMeetingDaysAndTimesValid(String meetingString, int startTime, int endTime, int expectedStartTime, int expectedEndTime) {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		
		event.setMeetingDaysAndTime(meetingString, startTime, endTime);
		
		assertEquals(meetingString, event.getMeetingDays(), "incorrect meeting days");
		assertEquals(expectedStartTime, event.getStartTime(), "incorrect start time");
		assertEquals(expectedEndTime, event.getEndTime(), "incorrect end time");
	}
	
	/**
	 * Tests invalid meeting days and times
	 * @param meetingString valid meeting string
	 * @param startTime valid start time
	 * @param endTime valid end time
	 */
	@ParameterizedTest(name = "{index} => meetingString={0}, startTime={1}, endTime={2}")
	@CsvSource({
		"AM,1330,1445",
		"MTZTH,1330,1445",
		"MTTHS,1330,1445",
		"MWF,2400,1445",
		"MWF,1360,1445",
		"MWF,-1,1445",
		"MWF,1330,2400",
		"MWF,1330,1360",
		"MWF,1330,-1",
		"MWF,1445,1330",
		"A,0,0"
	})
	public void testSetMeetingDaysAndTimesInvalid(String meetingString, int startTime, int endTime) {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> event.setMeetingDaysAndTime(meetingString, startTime, endTime));
		assertEquals("Invalid meeting days and times.", exception.getMessage(), "Incorrect exception thrown with invalid input.");
		assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays(), "incorrect meeting days");
		assertEquals(EVENT_START_TIME, event.getStartTime(), "incorrect start time");
		assertEquals(EVENT_END_TIME, event.getEndTime(), "incorrect end time");
		
	}

	/**
	 * Test Event.getMeetingString().
	 */
	@Test
	public void testGetMeetingString() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		assertEquals("MTWHF 8:00AM-9:00AM", event.getMeetingString());
		
		event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, 1200, 1300, EVENT_DETAILS);
		assertEquals("MTWHF 12:00PM-1:00PM", event.getMeetingString());
		
		event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, 1135, 1235, EVENT_DETAILS);
		assertEquals("MTWHF 11:35AM-12:35PM", event.getMeetingString());
	}

	/**
	 * Test Event.getShortDisplayArray().
	 */
	@Test
	public void testGetShortDisplayArray() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		
		String [] actualShortDisplay = event.getShortDisplayArray();
		assertEquals("", actualShortDisplay[0]);
		assertEquals("", actualShortDisplay[1]);
		assertEquals(EVENT_TITLE, actualShortDisplay[2]);
		assertEquals("MTWHF 8:00AM-9:00AM", actualShortDisplay[3]);
	}

	/**
	 * Test Event.getLongDisplayArray().
	 */
	@Test
	public void testGetLongDisplayArray() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		
		String [] actualLongDisplay = event.getLongDisplayArray();
		assertEquals("", actualLongDisplay[0]);
		assertEquals("", actualLongDisplay[1]);
		assertEquals(EVENT_TITLE, actualLongDisplay[2]);
		assertEquals("", actualLongDisplay[3]);
		assertEquals("", actualLongDisplay[4]);
		assertEquals("MTWHF 8:00AM-9:00AM", actualLongDisplay[5]);
		assertEquals(EVENT_DETAILS, actualLongDisplay[6]);
	}

	/**
	 * Test.Event.setEventDetails().
	 */
	@Test
	public void testSetEventDetailsValid() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		
		event.setEventDetails("Other details");
		assertAll("Event",
				() -> assertEquals(EVENT_TITLE, event.getTitle(), "incorrect title"),
				() -> assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(EVENT_START_TIME, event.getStartTime(), "incorrect start time"),
				() -> assertEquals(EVENT_END_TIME, event.getEndTime(), "incorrect end time"),
				() -> assertEquals("Other details", event.getEventDetails(), "incorrect end time"));
	}
	
	/** 
	 * Tests setEventDetails with invalid input.
	 * @param invalid invalid input for the test
	 */
	@ParameterizedTest
	@NullSource
	public void testSetEventDetailsInvalid(String invalid) {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, invalid));
		assertEquals("Invalid event details.", exception.getMessage(), "Incorrect exception thrown with invalid input - " + invalid);
	}
	
	/**
	 * Test Event.setTitle().
	 */
	@Test
	public void testSetTitleValid() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		
		event.setTitle("A new title");
		assertAll("Event",
				() -> assertEquals("A new title", event.getTitle(), "incorrect title"),
				() -> assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(EVENT_START_TIME, event.getStartTime(), "incorrect start time"),
				() -> assertEquals(EVENT_END_TIME, event.getEndTime(), "incorrect end time"),
				() -> assertEquals(EVENT_DETAILS, event.getEventDetails(), "incorrect end time"));
	}
	
	/** 
	 * Tests setTitle with invalid input.
	 * @param invalid invalid input for the test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testSetTitleInvalid(String invalid) {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> new Event(invalid, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS));
		assertEquals("Invalid title.", exception.getMessage(), "Incorrect exception thrown with invalid input - " + invalid);
	}


	/**
	 * Test Event.toString().
	 */
	@Test
	public void testToString() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		assertEquals(EVENT_TITLE + "," + EVENT_MEETING_DAYS + "," + EVENT_START_TIME + "," + EVENT_END_TIME + "," + EVENT_DETAILS, event.toString());
	}

	/**
	 * Test Event.equals().
	 */
	@Test
	public void testEqualsObject() {
		Activity e1 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		Activity e2 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		Activity e3 = new Event("Title", EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		Activity e4 = new Event(EVENT_TITLE, "MWF", EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		Activity e5 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, 830, EVENT_END_TIME, EVENT_DETAILS);
		Activity e6 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, 930, EVENT_DETAILS);
		Activity e7 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		Activity e8 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, "Details");
		
		//Test for equality in both directions
		assertTrue(e1.equals(e2));
		assertTrue(e2.equals(e1));
		
		//Test for each of the fields
		assertFalse(e1.equals(e3));
		assertFalse(e1.equals(e4));
		assertFalse(e1.equals(e5));
		assertFalse(e1.equals(e6));
		assertTrue(e1.equals(e7)); //field not considered in equals
		assertTrue(e1.equals(e8)); //field not considered in equals
	}

	/**
	 * Test Event.hashCode().
	 */
	@Test
	public void testHashCode() {
		Activity e1 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		Activity e2 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		Activity e3 = new Event("Title", EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		Activity e4 = new Event(EVENT_TITLE, "MWF", EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		Activity e5 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, 830, EVENT_END_TIME, EVENT_DETAILS);
		Activity e6 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, 930, EVENT_DETAILS);
		Activity e7 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_DETAILS);
		Activity e8 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, "Details");
		
		//Test for the same hash code for the same values
		assertEquals(e1.hashCode(), e2.hashCode());
		
		//Test for each of the fields
		assertNotEquals(e1.hashCode(), e3.hashCode());
		assertNotEquals(e1.hashCode(), e4.hashCode());
		assertNotEquals(e1.hashCode(), e5.hashCode());
		assertNotEquals(e1.hashCode(), e6.hashCode());
		assertEquals(e1.hashCode(), e7.hashCode()); //field not considered in hashCode
		assertEquals(e1.hashCode(), e8.hashCode()); //field not considered in hashCode
		
	}

}