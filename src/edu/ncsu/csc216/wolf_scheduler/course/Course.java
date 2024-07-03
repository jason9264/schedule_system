package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Course Class File
 * @author 'Jason Wang
 */
public class Course extends Activity {
	
	/** constant for minumum name length */
	private static final int MIN_NAME_LENGTH = 5;
	/** constant for maximum name length */
	private static final int MAX_NAME_LENGTH = 8;
	/** constant for the minimum letter count */
	private static final  int MIN_LETTER_COUNT = 1;
	/** constant for the maximum letter count */
	private static final int MAX_LETTER_COUNT = 4;
	/** constant for the digit count */
	private static final int DIGIT_COUNT = 3;
	/** constant for the section length */
	private static final int SECTION_LENGTH = 3;
	/** constant for the maximum credits */
	private static final int MAX_CREDITS = 5;
	/** constant for the minimum credits */
	private static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/**
	 * Constructs a Course object with values for all fields.
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays meeting days for Course as series of chars
	 * @param startTime start time for Course
	 * @param endTime end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
            int startTime, int endTime) {
        super(title, meetingDays, startTime, endTime);
        setName(name);
        setSection(section);
        setCredits(credits);
        setInstructorId(instructorId);
    }

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId, and meetingDays for 
	 * courses that are arranged.
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
	    this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}
	
	/**
	 * Returns the Course's name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name.
	 * @param name the name to set
	 */
	private void setName(String name) {
		
		//Throws an exception if the name is null
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		// Throw exception if the name is an empty string
		// throw exception if the name contains less than 5 characters
		// or if it is greater than 8
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		//Check for pattern of L[LLL] NNN
		//Initialize counters and lfags
		int letterCounter = 0;
		int digitCounter = 0;
		boolean spaceFlag = false;
		char c;
		
		for (int i = 0; i < name.length(); i++) {
			c = name.charAt(i);
		
			if(!spaceFlag){
				if(Character.isLetter(c)) {
					letterCounter = letterCounter + 1;
				}
				else if (c == ' ') {
					spaceFlag = true;
				}
				else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
			
			else if (spaceFlag){
				if (Character.isDigit(c)) {
					digitCounter = digitCounter + 1;
					}
				else {
					throw new IllegalArgumentException ("Invalid course name.");
				}
			}
		}
		
		//Check that the number of letters is correct
		if (letterCounter < MIN_LETTER_COUNT || letterCounter > MAX_LETTER_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		//check that the numbef of digits is correct
		if (digitCounter != DIGIT_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
			
		this.name = name;
	}

	/**
	 * Returns the Course's section.
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section.
	 * @param section the section to set
	 */
	public void setSection(String section) {
		
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		
		char c;
		for (int i = 0; i < 3; i++) {
			c = section.charAt(i);
			if (Character.isLetter(c) || c == ' ') {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		
		this.section = section;
	}

	/**
	 * Returns the Course's credits.
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credits.
	 * @param credits the credits to set
	 */
	public void setCredits(int credits) {
		
		//throws an exceptionif the credits for the course is out of the minumum and maximum bounds
		if(credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		
		this.credits = credits;
	}
	
	/**
	 * Overrides default to inclue specific course settings
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		
		 if ("A".equals(meetingDays) && (startTime != 0 || endTime != 0)){
			 throw new IllegalArgumentException("Invalid meeting days and times.");
		 }
		 
        int m = 0;
        int t = 0;
        int w = 0;
        int h = 0;
        int f = 0;
        int a = 0;
        
        for (int i = 0; i < meetingDays.length(); i++) {
	          char dummyChar = meetingDays.charAt(i);
	          
	          if (dummyChar == 'A' && meetingDays.length() != 1) {
	        	  throw new IllegalArgumentException("Invalid meeting days and times.");
	          }
	          
	          if (!(dummyChar == 'M' || dummyChar == 'T' || dummyChar == 'W' || dummyChar == 'H'
	        		  || dummyChar == 'F' || dummyChar == 'A')){
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
			  else if ('A' == dummyChar) {
				  a = a + 1;
			  }
			  else {
			    throw new IllegalArgumentException("Invalid meeting days and times.");
			  }
        }
		  
		  if (m > 1 || t > 1 || w > 1 || h > 1 || f > 1 || a > 1) {
		     throw new IllegalArgumentException ("Invalid meeting days and times.");
		  }
		  
		  super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		  
	    }
		
	/**
	 * Returns the Course's instructorId
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's instructorId
	 * @param instructorId the instructorId to set
	 */
	public void setInstructorId(String instructorId) {
		
		//throws and exception upon either a null or empty string for the instructors id
		if (instructorId == null || "".equals(instructorId)) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
			
		this.instructorId = instructorId;
	}

	
	

	/**
	 * Returns a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
	    if ("A".equals(getMeetingDays()))
	    {
	        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays();
	    }
	    return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime(); 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = new String[4];
		
		shortDisplay[0] = getName();
		shortDisplay[1] = getSection();
		shortDisplay[2] = getTitle();
		shortDisplay[3] = getMeetingString();
		
		return shortDisplay;
	}

	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = new String[7];
		
		longDisplay[0] = getName();
		longDisplay[1] = getSection();
		longDisplay[2] = getTitle();
		longDisplay[3] = Integer.toString(getCredits());
		longDisplay[4] = getInstructorId();
		longDisplay[5] = getMeetingString();
		longDisplay[6] = "";
		
		return longDisplay;
	}

	@Override
	public boolean isDuplicate(Activity activity) {
		return activity instanceof Course && ((Course)activity).getName().equals(this.getName());
	}

	
	
	
	

}
