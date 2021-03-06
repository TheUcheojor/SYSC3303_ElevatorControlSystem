/**
 *
 */
package common.messages;

/**
 * The different types of request.
 *
 * @author paulokenne
 *
 */
public enum MessageType {

	/**
	 * A job request which indicates a passenger wishes to go from floor A to B.
	 */
	JOB_REQUEST,

	/**
	 * A request to ask about the elevator status.
	 */
	ELEVATOR_STATUS_REQUEST,

	/**
	 * A message providing the elevator status.
	 */
	ELEVATOR_STATUS_MESSAGE,

	/**
	 * A subset of messages, elevator commands, used for communication between the
	 * scheduler and elevator
	 */
	SCHEDULER_ELEVATOR_COMMAND,

	/**
	 * A subset of messages, floor commands, used for communication between the
	 * scheduler and floor
	 */
	SCHEDULER_FLOOR_COMMAND,

	/**
	 * A message indicates that the elevator to arrive at a destination floor
	 */
	ELEVATOR_DROP_PASSENGER_REQUEST,

	/**
	 * A message indicating that the floor passenger requested for an elevator at a
	 * floor
	 */
	ELEVATOR_PICK_UP_PASSENGER_REQUEST,
	
	/**
	 * A message indicating that the elevator has arrived to the floor
	 */
	FLOOR_ARRIVAL_MESSAGE,

	/**
	 * A message indicating that the elevator is coming in the elevatorDirection of
	 * the floor
	 */
	ELEVATOR_FLOOR_SIGNAL_REQUEST,

	/**
	 * A message indicating that the elevator is leaving the floor
	 */
	ELEVATOR_LEAVING_FLOOR_MESSAGE,

	/**
	 * A request for testing purposes.
	 */
	TEST_REQUEST,

	/**
	 * A message that indicates that the intended message did not send or could not
	 * be received.
	 */
	COMMUNICATION_FAILURE,

	/**
	 * A message that indicates that the sent message was received
	 */
	ACKNOWLEDGEMENT_RESPONSE,

	/**
	 * A message that inidicates an elevator is stuck at a floor
	 */
	STUCK_AT_FLOOR_FAULT,
	
	/**
	 * A message provides a status update for the GUI
	 */
	GUI_MESSAGE,

	/**
	 * A message that a passenger has been dropped off at a floor
	 */
	PASSENGER_DROP_OFF_COMPLETE,

}
