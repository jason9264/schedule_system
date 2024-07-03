/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * A Custom exception
 * This is a checked exception to require the developer handle the exceptions.
 * @author jason
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * a parameterized constructor with a String specifying a message 
	 * for the Exception object
	 * @param errorMsg to be displayed when thrown
	 */
	public ConflictException(String errorMsg) {
		super(errorMsg);
	}
	/**
	 * parameterless constructor that calls the parameterized constructor
	 * with an specified default message
	 */
	public ConflictException() {
		super("Schedule conflict.");
		
	}

		
	
}
