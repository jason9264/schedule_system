package edu.ncsu.csc216.wolf_scheduler.course;
/**
 * Class for Activity (which includes Event and Course) in WolfScheduler Program
 * @author jason
 *
 */
public abstract class Activity implements Conflict {

	/** upper constant for hours */
	private static final int UPPER_HOUR = 24;
	/** minutes in an hour upper constant */
	private static final int UPPER_MINUTE = 60;
	/** Course's title. */
	private  String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;

	
	/**
	 * Default constructor for the activity class to create an activity
	 * @param title of the activity
	 * @param meetingDays the meeting Days for the activity
	 * @param startTime the starting time for the activity
	 * @param endTime the end time for the activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
        super();
        setTitle(title);
        setMeetingDaysAndTime(meetingDays, startTime, endTime);
    }

	/**
	 * Returns the Course's title as is
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Course's Title to the parameter.
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		
		//First condition checks if title is null or empty
		if (title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid title.");
		}
		
		//Second condition check if title is null or length is 0
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		
		
		this.title = title;
	}

	/**
	 * sets the Course's meeting day and time
	 * @param meetingDays the meeting day string
	 * @param startTime the starting time for course
	 * @param endTime the end time for the course
	 * @throws IllegalArgumentException meetingDays invalid
	 * @throws IllegalArgumentException meeting days consist of any characters other than ‘M’, ‘T’, ‘W’, ‘H’, ‘F’, or ‘A’
	 * @throws IllegalArgumentException meeting days have a duplicate character
	 * @throws IllegalArgumentException if ‘A’ is in the meeting days list, it must be the only character
	 * @throws IllegalArgumentException he start time is not between 0000 and 2359 an invalid military time
	 * @throws IllegalArgumentException the end time is not between 0000 and 2359 or an invalid military time
	 * @throws IllegalArgumentException the end time is less than the start time (i.e., no overnight classes)
	 * @throws IllegalArgumentException a start time and/or end time is listed when meeting days is ‘A’
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
	
		if (meetingDays == null || "".equals(meetingDays)){
	      throw new IllegalArgumentException("Invalid meeting days and times.");
		}
	
		  if (startTime < 0 || startTime > 2359) {
			  throw new IllegalArgumentException("Invalid meeting days and times.");
		  }
		  if (endTime < 0 || endTime > 2359 || endTime < startTime) {
			  throw new IllegalArgumentException("Invalid meeting days and times.");
		  }
		  
		  
		  int startHour = startTime / 100;
		  int startMin = startTime % 100;
		  int endHour =  endTime / 100;
		  int endMin = endTime % 100;
		  
		  
		  if (startHour < 0 || startHour >= UPPER_HOUR) {
		     throw new IllegalArgumentException("Invalid meeting days and times.");
		  }
		  if (startMin < 0 || startMin >= UPPER_MINUTE) {
		     throw new IllegalArgumentException("Invalid meeting days and times.");
		  }
		  if (endHour < 0 || endHour >= UPPER_HOUR) {
		     throw new IllegalArgumentException("Invalid meeting days and times.");
		  }
		
		  if (endMin < 0 || endMin >= UPPER_MINUTE) {
		     throw new IllegalArgumentException("Invalid meeting days and times.");
		  }
	
		
		  //everything is valid and works together!
		  this.meetingDays = meetingDays;
		  this.startTime = startTime;
		  this.endTime = endTime;
	}

	/**
	 * Returns the Course's meetingDays
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the Course's startTime.
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Course's endTime.
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Returns the Course's meeting as a string.
	 * @return arragned if meeting day as a string
	 */
	public String getMeetingString() {
		
		String meetingString = null;
		if("A".equals(meetingDays)){
			return "Arranged";
		}
		meetingString = meetingDays + " " + getTimeString(startTime, endTime);
		return meetingString;
		
	}

	/**
	 * method to get the time as string for another method usage.
	 * @param startTime the starting time for the course
	 * @param endTime the ending time for the course
	 * @return the time as a string
	 */
	public String getTimeString(int startTime, int endTime) {
		
	    int startHour = startTime / 100;
	    int startMin = startTime % 100;
	    int endHour =  endTime / 100;
	    int endMin = endTime % 100;
	    
	    String startHourString;
	    String startMinString;
	    String startTimeString;
	    String endHourString;
	    String endMinString;
	    String endTimeString;
	    
	    if (startHour > 12) {
	    	startHour = startHour - 12;
	    	startHourString = startHour + ":";
	    	
	    	if(startMin < 10) {
	    		startMinString = "0" + startMin + "PM";
	    	}
	    	else {
	    		startMinString = startMin + "PM";
	    	}
	    	
	    	startTimeString = startHourString + startMinString;
	    }
	    
	    else if (startHour == 12) {
	    	startHourString = "12:";
	    	
	    	if(startMin < 10) {
	    		startMinString = "0" + startMin + "PM";
	    	}
	    	else {
	    		startMinString = startMin + "PM";
	    	}
	    	
	    	startTimeString = startHourString + startMinString;
	    }
	
	    else if (startHour == 0) {
			startHourString = "12:";
	    	
	    	if(startMin < 10) {
	    		startMinString = "0" + startMin + "AM";
	    	}
	    	else {
	    		startMinString = startMin + "AM";
	    	}
	    	
	    	startTimeString = startHourString + startMinString;
	    }
	    
	    else {
	    	startHourString = startHour + ":";
	    	
	    	if(startMin < 10) {
	    		startMinString = "0" + startMin + "AM";
	    	}
	    	else {
	    		startMinString = startMin + "AM";
	    	}
	    	
	    	startTimeString = startHourString + startMinString;
	    }
	    
	    
	    if (endHour > 12) {
	    	
	    	endHour = endHour - 12;
	    	endHourString = endHour + ":";
	    	
	    	if(endMin < 10) {
	    		endMinString = "0" + endMin + "PM";
	    	}
	    	else {
	    		endMinString = endMin + "PM";
	    	}
	    	
	    	endTimeString = endHourString + endMinString;
	    }
	    
	    else if (endHour == 12) {
	    	endHourString = "12:";
	    	
	    	if(endMin < 10) {
	    		endMinString = "0" + endMin + "PM";
	    	}
	    	else {
	    		endMinString = endMin + "PM";
	    	}
	    	
	    	endTimeString = endHourString + endMinString;
	    }
	    
	    else if (endHour == 0) {
	    	endHourString = "12:";
	    	
	    	if(endMin < 10) {
	    		endMinString = "0" + endMin + "AM";
	    	}
	    	else {
	    		endMinString = endMin + "AM";
	    	}
	    	
	    	endTimeString = endHourString + endMinString;
	    }
	    
	    else {
	    	
	    	endHourString = endHour + ":";
	    	
	    	if(endMin < 10) {
	    		endMinString = "0" + endMin + "AM";
	    	}
	    	else {
	    		endMinString = endMin + "AM";
	    	}
	    	
	    	endTimeString = endHourString + endMinString;
	    }
	    
		return startTimeString + "-" + endTimeString;
		
	}
	
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		//Parameter variables
		int possibleAStartTime = possibleConflictingActivity.getStartTime();
		int possibleAEndTime = possibleConflictingActivity.getEndTime();
		String possibleAWeekdays = possibleConflictingActivity.getMeetingDays();
		
		
		//This item variables
		int thisStartTime = this.getStartTime();
		int thisEndTime = this.getEndTime();
		String thisWeekdays = this.getMeetingDays();
		
		for(int i = 0; i < possibleAWeekdays.length(); ++i) {
			for (int j = 0; j < thisWeekdays.length(); ++j) {
				
				if(!("A".equals(possibleAWeekdays) || "A".equals(thisWeekdays))
						&& possibleAWeekdays.charAt(i) == thisWeekdays.charAt(j)){
					
				// compare weekday strings for same weekday
				
					// check the start times if they have ANY of the same days
					if(possibleAStartTime == thisStartTime) {
						throw new ConflictException("Schedule conflict.");
					}
					// check the end times if they have ANY of the same days
					if(possibleAEndTime == thisEndTime) {
						throw new ConflictException("Schedule conflict.");
					}
					// check that the param start time is not inbetween this start && end
					if (possibleAStartTime > thisStartTime && possibleAStartTime < thisEndTime) {
						throw new ConflictException("Schedule conflict.");
					}
					//check that the param end time is not inbetween this start && end
					if(possibleAEndTime > thisStartTime && possibleAEndTime < thisEndTime) {
						throw new ConflictException("Schedule conflict.");
					}
					// this version
					// check the start times vs end time
					if(thisStartTime == possibleAEndTime) {
						throw new ConflictException("Schedule conflict.");
					}
					if(thisEndTime == possibleAStartTime) {
						throw new ConflictException("Schedule conflict.");
					}
					// check that the this start time is not inbetween param start && end
					if(thisStartTime > possibleAStartTime && thisEndTime < possibleAStartTime) {
						throw new ConflictException("Schedule conflict.");
					}
					//check that the this end time is not inbetween param start && end
					if(thisEndTime > possibleAStartTime && thisEndTime < possibleAEndTime) {
						throw new ConflictException("Schedule conflict.");
					}
				}
						
				}
			}
		}
		
	

	/**
	 * Display in GUI to provide a short version of the array of information
	 * @return a string array version of short display
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * Display in GUI to provide a long version of the array of information
	 * @return a string array version of long display
	 */
	public abstract String[] getLongDisplayArray();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	/**
	 * Abstract method called from the other classes
	 * @param activity of the actual item
	 * @return the true of false value
	 */
	public abstract boolean isDuplicate(Activity activity);

}