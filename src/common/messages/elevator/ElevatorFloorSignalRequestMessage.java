/**
 *
 */
package common.messages.elevator;

import ElevatorSubsystem.ElevatorMotor;
import common.messages.FloorElevatorTargetedMessage;
import common.messages.MessageType;

/**
 * This class represents a request that sent to the floor that indicates the
 * elevator is coming in its elevatorDirection
 *
 * @author paulokenne
 *
 */
public class ElevatorFloorSignalRequestMessage extends FloorElevatorTargetedMessage {

	/**
	 * A flag indicating whether the floor is the final destination
	 */
	private boolean isFloorFinalDestination;

	/**
	 * The elevator motor
	 */
	private ElevatorMotor elevatorMotor;

	/**
	 * A ElevatorFloorSignalRequestMessage constructor
	 */
	public ElevatorFloorSignalRequestMessage(int elevatorId, int floorId, ElevatorMotor elevatorMotor,
			boolean isFloorFinalDestination) {
		super(elevatorId, floorId, MessageType.ELEVATOR_FLOOR_SIGNAL_REQUEST);
		this.isFloorFinalDestination = isFloorFinalDestination;
		this.elevatorMotor = elevatorMotor;
	}

	/**
	 * @return the isFloorFinalDestination flag
	 */
	public boolean isFloorFinalDestination() {
		return isFloorFinalDestination;
	}

	/**
	 * @return the elevatorMotor
	 */
	public ElevatorMotor getElevatorMotor() {
		return elevatorMotor;
	}
}
