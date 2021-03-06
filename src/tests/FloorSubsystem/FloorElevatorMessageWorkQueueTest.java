package tests.FloorSubsystem;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import FloorSubsystem.FloorElevatorMessageWorkQueue;
import FloorSubsystem.FloorSchedulerMessageWorkQueue;
import FloorSubsystem.Floor;
import common.messages.Message;
import common.messages.MessageType;
import common.messages.elevator.ElevatorFloorArrivalMessage;
import common.messages.elevator.ElevatorFloorSignalRequestMessage;
import common.messages.elevator.ElevatorStatusMessage;
import common.remote_procedure.SubsystemCommunicationRPC;
import common.remote_procedure.SubsystemComponentType;

/**
 * This is a test for the floor/elevator message communication
 * @author Jacob
 */
public class FloorElevatorMessageWorkQueueTest {
	private SubsystemCommunicationRPC schedulerFloorSubsystemCommunication, elevatorFloorSubsystemCommunication;
	private SubsystemCommunicationRPC floorSchedulerSubsystemCommunication, floorElevatorSubsystemCommunication;
	private FloorElevatorMessageWorkQueue workQueue;
	
	private int ELEVATOR_ID = 1;
	private int FLOOR_ID = 1;
	private int NUMBER_OF_FLOORS = 1;
	static int ELEVATOR_FLOOR_TO_FLOOR_TIME_MILLISECONDS = 2000;
	
	private ArrayDeque<Message> receivedElevatorMessages = new ArrayDeque<>();
	private Floor[] floors = new Floor[NUMBER_OF_FLOORS];

	@BeforeEach
	void setup() {
		schedulerFloorSubsystemCommunication = new SubsystemCommunicationRPC(SubsystemComponentType.SCHEDULER,
				SubsystemComponentType.FLOOR_SUBSYSTEM);
		
		floorSchedulerSubsystemCommunication = new SubsystemCommunicationRPC(SubsystemComponentType.FLOOR_SUBSYSTEM,
	 		SubsystemComponentType.SCHEDULER);
		
		elevatorFloorSubsystemCommunication = new SubsystemCommunicationRPC(SubsystemComponentType.ELEVATOR_SUBSYSTEM,
				SubsystemComponentType.FLOOR_SUBSYSTEM);
		
		floorElevatorSubsystemCommunication = new SubsystemCommunicationRPC(SubsystemComponentType.FLOOR_SUBSYSTEM,
	 		SubsystemComponentType.ELEVATOR_SUBSYSTEM);
		
		//floors = new HashMap<Integer, Floor>();
		for (int i = 0; i < floors.length; i++) {
			floors[i] = new Floor(i, ELEVATOR_FLOOR_TO_FLOOR_TIME_MILLISECONDS);
		}
		
		workQueue = new FloorElevatorMessageWorkQueue(floorSchedulerSubsystemCommunication, floorElevatorSubsystemCommunication, floors);
		
		receivedElevatorMessages = new ArrayDeque<>();
	}
	
	@AfterEach
	void tearDown() {
		schedulerFloorSubsystemCommunication = null;
		workQueue = null;
		floors = null;
		
		receivedElevatorMessages = null;
	}
	

	@Test
	void testWorkQueueArrivalMessageHandler() {
		simulateElevatorMessageWaiting();
		//ElevatorFloorSignalRequestMessage floorSignalRequestMessage = new ElevatorFloorSignalRequestMessage(el);
		
		ElevatorFloorArrivalMessage floorMessage = new ElevatorFloorArrivalMessage(ELEVATOR_ID, FLOOR_ID);
		
		ElevatorFloorArrivalMessage arrivalMessage = null;
		try {
			workQueue.enqueueMessage(floorMessage);
		
			Thread.sleep(100);
			
			arrivalMessage = (ElevatorFloorArrivalMessage) receivedElevatorMessages.pop();
			assertTrue(arrivalMessage != null);
			assertTrue(arrivalMessage.getMessageType() == MessageType.FLOOR_ARRIVAL_MESSAGE);
			
		} catch (NoSuchElementException e) {
			System.out.println("Completed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void simulateElevatorMessageWaiting() {
		(new Thread() {
			@Override
			public void run() {
				try {
					synchronized (receivedElevatorMessages) {
						receivedElevatorMessages.add(elevatorFloorSubsystemCommunication.receiveMessage());
					}
				} catch (Exception e) {
					System.out.print("Exception occurred: " + e);
				}
			}
		}).start();
	}
}
