package edu.ncsu.csc216.wolf_scheduler.io;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.course.Event;

/**
 * Tests for ActivityRecordIO.
 * @author Sarah Heckman
 */
public class ActivityRecordIOTest {

	
	/**
	 * Tests writeActivityRecords()
	 */
	@Test
	public void testWriteActivityRecords() {
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(new Course("CSC 116", "Intro to Programming - Java", "003", 3, "spbalik", "MW", 1250, 1440));
		activities.add(new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "MW", 1330, 1445));
		activities.add(new Course("CSC 216", "Software Development Fundamentals", "601", 3, "jctetter", "A"));
		
		try {
			ActivityRecordIO.writeActivityRecords("test-files/actual_course_records.txt", activities);
		} catch (IOException e) {
			fail("Cannot write to course records file");
		}
		
		checkFiles("test-files/expected_course_records.txt", "test-files/actual_course_records.txt");
	}
	
	/**
	 * Tests writeActivityRecords() with Event objects.
	 */
	@Test
	public void testWriteActivityRecordsWithEvents() {
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(new Course("CSC 116", "Intro to Programming - Java", "003", 3, "spbalik", "MW", 1250, 1440));
		activities.add(new Event("Exercise", "UMTHFS", 800, 900, "Cardio with rest day on Wednesday."));
		activities.add(new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "MW", 1330, 1445));
		activities.add(new Course("CSC 216", "Software Development Fundamentals", "601", 3, "jctetter", "A"));
		
		try {
			ActivityRecordIO.writeActivityRecords("test-files/actual_activity_records.txt", activities);
		} catch (IOException e) {
			fail("Cannot write to activity records file");
		}
		
		checkFiles("test-files/expected_activity_records.txt", "test-files/actual_activity_records.txt");
	}

	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}