/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Conflict interface for Wolf Scheduler 
 * @author jason
 *
 */
public interface Conflict {
	/**
	 * Method to check activity times and dictate whether they are conflicting or not
	 * @param possibleConflictingActivity when an activity is possibly conflicting
	 * @throws ConflictException when there is conflicitng activity
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
