package common.messages.elevator;

import java.util.Date;

import common.DateFormat;
import common.Direction;
import common.messages.Message;
import common.messages.MessageType;

/**
 * A DS representing an elevator status response
 *
 * @author Ryan Fife
 *
 */

public class ElevatorStatusMessage extends Message {

	/**
	 * The elevator id
	 */
	private int elevatorId;

	/**
	 * The flood number
	 */
	private int floorNumber;

	/**
	 * The direction
	 */
	private Direction direction;

	/**
	 * A time stamp
	 */
	private String timestamp;

	/**
	 * The exception
	 */
	private Exception errorState;

	private boolean isDoorOpen;

	/**
	 * A flag indicating that the elevator is resolving an error
	 */
	private boolean isResolvingError;

	/**
	 * A flag that indicates that the scheduler should issue the next command
	 */
	private boolean issueNextCommand = true;

	/**
	 * A flag that indicates that the message is solely for the GUI
	 */
	private boolean isGUIOnlyStatusMessage = false;

	/**
	 * A ElevatorStatusMessage constructor
	 *
	 * @param elevatorId       the elevator id
	 * @param direction        the direction
	 * @param floorNumber      the floor number
	 * @param errorState       the error state
	 * @param isResolvingError a flag indicating that the elevator is resolving an
	 *                         error
	 */
	public ElevatorStatusMessage(int elevatorId, Direction direction, int floorNumber, Exception errorState,
			boolean isResolvingError, boolean doorIsOpen) {
		super(MessageType.ELEVATOR_STATUS_MESSAGE);
		this.timestamp = DateFormat.DATE_FORMAT.format(new Date());

		this.elevatorId = elevatorId;
		this.direction = direction;
		this.floorNumber = floorNumber;
		this.errorState = errorState;
		this.isResolvingError = isResolvingError;
		this.isDoorOpen = doorIsOpen;
	}

	/**
	 * A ElevatorStatusMessage constructor
	 *
	 * @param elevatorId       the elevator id
	 * @param direction        the direction
	 * @param floorNumber      the floor number
	 * @param errorState       the error state
	 * @param isResolvingError a flag indicating that the elevator is resolving an
	 *                         error
	 */
	public ElevatorStatusMessage(int elevatorId, Direction direction, int floorNumber, Exception errorState,
			boolean isResolvingError, boolean issueNextCommand, boolean doorIsOpen) {
		super(MessageType.ELEVATOR_STATUS_MESSAGE);
		this.timestamp = DateFormat.DATE_FORMAT.format(new Date());

		this.elevatorId = elevatorId;
		this.direction = direction;
		this.floorNumber = floorNumber;
		this.errorState = errorState;
		this.isDoorOpen = doorIsOpen;
		this.isResolvingError = isResolvingError;
		this.issueNextCommand = issueNextCommand;
	}

	/**
	 * Return a status message that is only for the GUI
	 *
	 * @return
	 */
	public ElevatorStatusMessage forGuiOnly() {
		this.isGUIOnlyStatusMessage = true;
		return this;
	}

	/**
	 * Return a flag that indicates if the message is for the GUI only
	 *
	 * @return true if for the GUI only and false otherwise
	 */
	public boolean isGUIOnly() {
		return isGUIOnlyStatusMessage;
	}

	/**
	 * @return the elevatorId
	 */
	public int getElevatorId() {
		return elevatorId;
	}

	/**
	 * @return the floorNumber
	 */
	public int getFloorNumber() {
		return floorNumber;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the errorState
	 */
	public Exception getErrorState() {
		return errorState;
	}

	/**
	 * @return the whether or not the door is open
	 */
	public boolean isDoorOpen() {
		return isDoorOpen;
	}

	/**
	 * @return the isResolvingError
	 */
	public boolean isResolvingError() {
		return isResolvingError;
	}

	/**
	 * Return a flag indicating whether the scheduler should issue the next command
	 *
	 * @return the issueNextCommand
	 */
	public boolean shouldIssueNextCommand() {
		return issueNextCommand;
	}

}
