package ElevatorSubsystem;

import java.util.Map;

import common.messages.Message;
import common.messages.elevator.ElevatorFloorArrivalMessage;
import common.messages.elevator.ElevatorStatusMessage;
import common.messages.elevator.ElevatorTransportRequest;
import common.remote_procedure.SubsystemCommunicationRPC;
import common.work_management.MessageWorkQueue;

/**
 * This class serves the elevator related requests received from the floor subsystem.
 * 
 * @author Ryan Fife
 */
public class ElevatorFloorMessageWorkQueue extends MessageWorkQueue {
	private SubsystemCommunicationRPC schedulerSubsystemCommunication;

	private Map<Integer, ElevatorCar> elevators;

	public ElevatorFloorMessageWorkQueue(SubsystemCommunicationRPC schedulerSubsystemCommunication, Map<Integer, ElevatorCar> elevators) {
		this.schedulerSubsystemCommunication = schedulerSubsystemCommunication;
		this.elevators = elevators;
	}
	
	/**
	 * This method handles messages received from the floor subsystem
	 */
	@Override
	protected void handleMessage(Message message) {
		try {
			switch(message.getMessageType()) {
				
				case FLOOR_ARRIVAL_MESSAGE:
					ElevatorFloorArrivalMessage arrivalMessage = ((ElevatorFloorArrivalMessage) message);
					int carId = arrivalMessage.getElevatorId();
					int floorNumber = arrivalMessage.getFloorId();
					ElevatorCar car = elevators.get(carId); 
					car.setFloorNumber(floorNumber);
					
					System.out.println("(ELEVATOR) Elevator " + carId + " has reached floor: " + floorNumber);
					ElevatorStatusMessage arrivalStatus = car.createStatusMessage();
				
					schedulerSubsystemCommunication.sendMessage(arrivalStatus);
					break;
					
				case ELEVATOR_DROP_PASSENGER_REQUEST:
					schedulerSubsystemCommunication.sendMessage(message);
					break;
					
				default:
					System.out.println("(ELEVATOR) received improper message from FLOOR of type: " + message.getMessageType());
					break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
