/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Class for the Event
 * @author jason
 */
public class Event extends Activity {
	/**
	 * event Detail string for the event class
	 */
	public String eventDetails;

	/**
	 * Constructor for Event Class
	 * @param title for the event 
	 * @param meetingDays meetingDays for the event
	 * @param startTime of the event
	 * @param endTime of the event
	 * @param eventDetails of the event
	 */
	public Event(String title, String meetingDays, int startTime, int endTime, String eventDetails) {
		super(title, meetingDays, startTime, endTime);
		setEventDetails(eventDetails);
	}

	/**
	 * Returns the event details of the chosen event
	 * @return the eventDetails
	 */
	public String getEventDetails() {
		return eventDetails;
	}

	/**
	 * Sets the event details for the event
	 * @param eventDetails the eventDetails to set
	 */
	public void setEventDetails(String eventDetails) {
		
		if (eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details.");
		}
		
		this.eventDetails = eventDetails;
	}
	
	/**
	 * overrides default to test specific event settings
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		
		if (meetingDays == null || "".equals(meetingDays)){
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		int m = 0;
        int t = 0;
        int w = 0;
        int h = 0;
        int f = 0;
        int s = 0;
        int u = 0;
	          
        
        for (int i = 0; i < meetingDays.length(); i++) {
	          char dummyChar = meetingDays.charAt(i);
	          
	          if (dummyChar == 'A') {
	        	  throw new IllegalArgumentException("Invalid meeting days and times.");
	          }
	          
	          if (!(dummyChar == 'M' || dummyChar == 'T' || dummyChar == 'W' || dummyChar == 'H'
		      		  || dummyChar == 'F' || dummyChar == 'S' || dummyChar == 'U')){
		        	  throw new IllegalArgumentException("Invalid meeting days and times.");
	          }
	        
			  if ('M' == dummyChar) {
				  m = m + 1;
			  }
			  else if ('T' == dummyChar) {
				  t = t + 1;
			  }
			  else if ('W' == dummyChar) {
				  w = w + 1;
			  }
			  else if ('H' == dummyChar) {
				  h = h + 1;
			  }
			  else if ('F' == dummyChar) {
				  f = f + 1;
			  }
			  else if ('S' == dummyChar) {
				  s = s + 1;
			  }
			  else if ('U' == dummyChar) {
				  u = u + 1;
			  }
			  else {
			    throw new IllegalArgumentException("Invalid meeting days and times.");
			  }
        }
		  
		  if (m > 1 || t > 1 || w > 1 || h > 1 || f > 1 || s > 1 || u > 1) {
		     throw new IllegalArgumentException ("Invalid meeting days and times.");
		  }
		
        
        super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
        
	}
	

	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = new String[4];
		
		shortDisplay[0] = "";
		shortDisplay[1] = "";
		shortDisplay[2] = getTitle();
		shortDisplay[3] = getMeetingString();
		
		return shortDisplay;
	}

	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = new String[7];
		
		longDisplay[0] = "";
		longDisplay[1] = "";
		longDisplay[2] = getTitle();
		longDisplay[3] = "";
		longDisplay[4] = "";
		longDisplay[5] = getMeetingString();
		longDisplay[6] = getEventDetails();
		
		return longDisplay;
	}

	@Override
	public String toString() {
		String eventString = getTitle() + "," + getMeetingDays() + "," + getStartTime() + "," +
				getEndTime() + "," + getEventDetails();
		return eventString;
	}

	

	@Override
	public boolean isDuplicate(Activity activity) {
		return activity instanceof Event && ((Event)activity).getTitle().equals(getTitle());	
	}

}
