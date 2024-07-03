package edu.ncsu.csc216.wolf_scheduler.scheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.course.Event;
import edu.ncsu.csc216.wolf_scheduler.io.ActivityRecordIO;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;

/**
 * creates the class for WolfScheduler
 * @author jason
 * Constructior for WolfScheduler default
 */
public class WolfScheduler {
	
	/** course catalog */
    private ArrayList<Course> catalog = new ArrayList<Course>();
    
	/** array list of courses schedule */
    private ArrayList<Activity> schedule = new ArrayList<Activity>(); 

	/** title of the schedule */
    private String title = "";


    
	/**
	 * the constructor to create array
	 * @param fileName for the file
	 */
	public WolfScheduler(String fileName) {
		
		schedule = new ArrayList<Activity>();
		this.title = "My Schedule";
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Cannot find file.");
		}
	}

	/**
	 * method to get courseCatalog as a 2d array
	 * @return a string of the course Catalog as a 2d Array
	 */
	public String[][] getCourseCatalog() {
		if (0 == catalog.size()) {
			String[][] emptyLog = new String[0][0];
			return emptyLog;
		}
		else {
			String [][] courseCatalog = new String[catalog.size()][4];
			for (int i = 0; i < catalog.size(); i++) {
				Course c = catalog.get(i);
				courseCatalog[i] = c.getShortDisplayArray();
			}
			return courseCatalog;
		}
	}

	/**
	 * gets the Scheduled Courses
	 * @return the scheduleCourses as a 2d Array
	 */
	public String[][] getScheduledActivities() {
		if (0 == schedule.size()) {
			String[][] emptySchedule = new String[0][0];
			return emptySchedule;
		}		
		else {
			String[][] scheduledActivities = new String[schedule.size()][4];
			for (int i = 0; i <= schedule.size() - 1; i++) {
				Activity c = schedule.get(i);
				scheduledActivities[i] = c.getShortDisplayArray();
			}
			return scheduledActivities;
		}
	}
	
	
	/**
	 * Gets the full schedule of courses
	 * @return the fulle schedule of courses as a 2d array
	 */
	public String[][] getFullScheduledActivities() {
		if (0 == schedule.size()) {
			String[][] emptyFullScheduledActivities = new String[0][0];
			return emptyFullScheduledActivities;
		}		
		else {
			String[][] fullScheduledActivities = new String[schedule.size()][7];
			for (int i = 0; i <= schedule.size() - 1; i++) {
				Activity c = schedule.get(i);
				fullScheduledActivities[i] = c.getLongDisplayArray();
			}
			return fullScheduledActivities;
		}
		
	}
	
	/**
	 * gets the specified course from the catalog with name and section
	 * @param name of the course
	 * @param section of the course
	 * @return the course or null if the course is in catalog
	 */
	public Course getCourseFromCatalog(String name, String section) {
		
		Course course = null;
		for (int i = 0; i < catalog.size(); i++) {
			course = catalog.get(i);
			if(course.getName().equals(name) && course.getSection().equals(section)) {
				return course;
			}
		}
		
		return null;
	}

	
	/**
	 * adds the course from the catalog with the name and section
	 * @param name of the course
	 * @param section of the course
	 * @return true or false based on success
	 */
	public boolean addCourseToSchedule(String name, String section) {
		//Initialize variables to hold pulled items from the catalog
		Course course = null;
		//The specific itemf rom teh catalog
		Course courseFromCatalog = getCourseFromCatalog(name, section);
		boolean b = false;
		if (courseFromCatalog == null) {
			return b;
		}
		
		String courseFromCatalogName = courseFromCatalog.getName();
		String courseFromCatalogSection = courseFromCatalog.getSection();
		String courseName;
		String courseSection;
		boolean courseFlag = b;
		int size = catalog.size();
		
		//checks that the course exists in the catalog
		for (int i = 0; i <= catalog.size() - 1; i++) {
			course = catalog.get(i);
			courseName = course.getName();
			courseSection = course.getSection();
			if(courseName == courseFromCatalogName && courseSection == courseFromCatalogSection) {	
				courseFlag = true;
			}
			if (i == size - 1 && courseFlag == b) {
				return b;
			}
		}
		
		Activity courseHolder = null;
		//Checks for duplicate and repetition
		for (int i = 0; i <= schedule.size() - 1; i++) {
			//this holds the current item from the schedule
			courseHolder = schedule.get(i);
			//gets the name of the item from the schedule
			if (courseFromCatalog.isDuplicate(courseHolder)) {
				throw new IllegalArgumentException("You are already enrolled in " + courseFromCatalog.getName() + "");
			}
			//run only on instanceof course to compare
			//Parameter variables
			int possibleAStartTime = courseHolder.getStartTime();
			int possibleAEndTime = courseHolder.getEndTime();
			String possibleAWeekdays = courseHolder.getMeetingDays();
			
			
			//This item variables
			int thisStartTime = courseFromCatalog.getStartTime();
			int thisEndTime = courseFromCatalog.getEndTime();
			String thisWeekdays = courseFromCatalog.getMeetingDays();
			
			for(int k = 0; k < possibleAWeekdays.length(); ++k) {
				for (int j = 0; j < thisWeekdays.length(); ++j) {
					
					if(!("A".equals(possibleAWeekdays) || "A".equals(thisWeekdays)) 
							&& possibleAWeekdays.charAt(k) == thisWeekdays.charAt(j)){
					// compare weekday strings for same weekday
						// check the start times if they have ANY of the same days
						if(possibleAStartTime == thisStartTime) {
							throw new IllegalArgumentException("The course cannot be added due to a conflict.");
						}
						// check the end times if they have ANY of the same days
						if(possibleAEndTime == thisEndTime) {
							throw new IllegalArgumentException("The course cannot be added due to a conflict.");
						}
						// check that the param start time is not inbetween this start && end
						if (possibleAStartTime > thisStartTime && possibleAStartTime < thisEndTime) {
							throw new IllegalArgumentException("The course cannot be added due to a conflict.");
						}
						//check that the param end time is not inbetween this start && end
						if(possibleAEndTime > thisStartTime && possibleAEndTime < thisEndTime) {
							throw new IllegalArgumentException("The course cannot be added due to a conflict.");
						}
						// this version
						// check the start times vs end time
						if(thisStartTime == possibleAEndTime) {
							throw new IllegalArgumentException("The course cannot be added due to a conflict.");
						}
						if(thisEndTime == possibleAStartTime) {
							throw new IllegalArgumentException("The course cannot be added due to a conflict.");
						}
						// check that the this start time is not inbetween param start && end
						if(thisStartTime > possibleAStartTime && thisEndTime < possibleAStartTime) {
							throw new IllegalArgumentException("The course cannot be added due to a conflict.");
						}
						//check that the this end time is not inbetween param start && end
						if(thisEndTime > possibleAStartTime && thisEndTime < possibleAEndTime) {
							throw new IllegalArgumentException("The course cannot be added due to a conflict.");
						}
					
						
					}
						
					}
				}

		}
		
		
		for (int i = 0; i <= catalog.size() - 1; i++) {
			course = catalog.get(i);
			courseName = course.getName();
			courseSection = course.getSection();
				if(courseName == courseFromCatalogName && courseSection == courseFromCatalogSection) {
					schedule.add(courseFromCatalog);
					return true;
				}
			}
		return b;
	}
	
			

	/**
	 * removes a course from the schedule
	 * @param idx TODO
	 * @return boolean based on success
	 */
	public boolean removeActivityFromSchedule(int idx) {
		try {
			schedule.remove(idx);
			return true;
		}
		catch (IndexOutOfBoundsException e){
			return false;
		}			
	}

	/**
	 * resets the schedule
	 */
	public void resetSchedule() {
		schedule.clear();
		
	}

	/**
	 * sets the title of the schedule
	 * @param title of the schedule
	 */
	public void setScheduleTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}
	
	/**
	 * gets the schedules title
	 * @return the schedules title
	 */
	public String getScheduleTitle() {
		return this.title;
	}

	/**
	 * exports the schedule to a file
	 * @param filename of the file
	 * @throws IllegalArgumentException if this is not successful
	 */
	public void exportSchedule(String filename) throws IllegalArgumentException {
		
		try {
			ActivityRecordIO.writeActivityRecords(filename, schedule);
		}
		catch (IOException e)  {
			throw new IllegalArgumentException("The file cannot be saved");
		}
	}
	
	/**
	 * Adds in event to the schedule
	 * @param eventTitle of the event
	 * @param eventMeetingDays meeting days of the event
	 * @param eventStartTime start time of the event
	 * @param eventEndTime end time of the event
	 * @param eventDetails details of the event
	 */
	public void addEventToSchedule(String eventTitle, String eventMeetingDays, int eventStartTime, int eventEndTime, String eventDetails) {
		Activity eventHolder = null;
		Activity eventToAdd = new Event(eventTitle, eventMeetingDays, eventStartTime, eventEndTime, eventDetails);
		//Checks for duplicate and repetition
		for (int i = 0; i <= schedule.size() - 1; i++) {
			//this holds the current item from the schedule
			eventHolder = schedule.get(i);
			//gets the name of the item from the schedule
				if (eventToAdd.isDuplicate(eventHolder)) {
					throw new IllegalArgumentException("You have already created an event called " + eventTitle);
				}	
					//Parameter variables
					int possibleAStartTime = eventHolder.getStartTime();
					int possibleAEndTime = eventHolder.getEndTime();
					String possibleAWeekdays = eventHolder.getMeetingDays();
				
					//This item variables
					int thisStartTime = eventToAdd.getStartTime();
					int thisEndTime = eventToAdd.getEndTime();
					String thisWeekdays = eventToAdd.getMeetingDays();
					
					for(int k = 0; k < possibleAWeekdays.length(); ++k) {
						for (int j = 0; j < thisWeekdays.length(); ++j) {
							
							if(!("A".equals(possibleAWeekdays) || "A".equals(thisWeekdays))
									&& possibleAWeekdays.charAt(k) == thisWeekdays.charAt(j)){
								
							// compare weekday strings for same weekday
								// check the start times if they have ANY of the same days
								if(possibleAStartTime == thisStartTime) {
									throw new IllegalArgumentException("The event cannot be added due to a conflict.");
								}
								// check the end times if they have ANY of the same days
								if(possibleAEndTime == thisEndTime) {
									throw new IllegalArgumentException("The event cannot be added due to a conflict.");
								}
								// check that the param start time is not inbetween this start && end
								if (possibleAStartTime > thisStartTime && possibleAStartTime < thisEndTime) {
									throw new IllegalArgumentException("The event cannot be added due to a conflict.");
								}
								//check that the param end time is not inbetween this start && end
								if(possibleAEndTime > thisStartTime && possibleAEndTime < thisEndTime) {
									throw new IllegalArgumentException("The event cannot be added due to a conflict.");
								}
								// this version
								// check the start times vs end time
								if(thisStartTime == possibleAEndTime) {
									throw new IllegalArgumentException("The event cannot be added due to a conflict.");
								}
								if(thisEndTime == possibleAStartTime) {
									throw new IllegalArgumentException("The event cannot be added due to a conflict.");
								}
								// check that the this start time is not inbetween param start && end
								if(thisStartTime > possibleAStartTime && thisEndTime < possibleAStartTime) {
									throw new IllegalArgumentException("The event cannot be added due to a conflict.");
								}
								//check that the this end time is not inbetween param start && end
								if(thisEndTime > possibleAStartTime && thisEndTime < possibleAEndTime) {
									throw new IllegalArgumentException("The event cannot be added due to a conflict.");
								}
							}
								
							
								
							
						}
				}
			}
		
		schedule.add(eventToAdd);
		}
	}

	


