package FloorSubsystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import common.SimulationFloorInputData;
import common.SystemValidationUtil;
import common.exceptions.InvalidSystemConfigurationInputException;
import common.messages.FloorElevatorTargetedMessage;
import common.messages.Message;
import common.messages.MessageChannel;
import common.messages.elevator.ElevatorFloorSignalRequestMessage;
import common.messages.floor.ElevatorFloorRequest;
import common.messages.scheduler.SchedulerFloorCommand;

/**
 * This class simulates the FloorSubsystem thread
 *
 * @author Favour, Delight, paulokenne
 */
public class FloorSubsystem implements Runnable {

	/**
	 * The number of floors
	 */
	public static final int NUMBER_OF_FLOORS = 3;

	/**
	 * The floor to floor distance in meters
	 */
	public static final double FLOOR_TO_FLOOR_DISTANCE = 4.5;

	/**
	 * The floors.
	 */
	private Floor[] floors = new Floor[NUMBER_OF_FLOORS];

	/**
	 * The name of the input text file
	 */
	private String inputFileName;

	/**
	 * Collection of the simulation input objects
	 */
	private ArrayList<SimulationFloorInputData> floorDataCollection = new ArrayList<>();

	/**
	 * The floor subsystem transmission message channel.
	 */
	private MessageChannel floorSubsystemTransmissonChannel;

	/**
	 * The floor subsystem transmission message channel.
	 */
	private MessageChannel floorSubsystemReceiverChannel;

	/**
	 * The elevator subsystem transmission message channel.
	 */
	private MessageChannel elevatorSubsystemReceiverChannel;

	/**
	 * This is the default constructor of the class
	 *
	 * @param inputFileName       - The input text file
	 * @param floorMessageChannel - The message channel for communicating with the
	 *                            scheduler
	 */
	public FloorSubsystem(String inputFileName, MessageChannel floorSubsystemTransmissonChannel,
			MessageChannel floorSubsystemReceiverChannel, MessageChannel elevatorSubsystemReceiverChannel) {
		// Validate that the floor subsystem values are valid
		try {
			SystemValidationUtil.validateFloorToFloorDistance(FLOOR_TO_FLOOR_DISTANCE);
		} catch (InvalidSystemConfigurationInputException e) {
			System.out.println("InvalidSystemConfigurationInputException: " + e);
			// Terminate if the elevation configuration are invalid.
			System.exit(1);
		}

		this.inputFileName = inputFileName;
		this.floorSubsystemTransmissonChannel = floorSubsystemTransmissonChannel;
		this.floorSubsystemReceiverChannel = floorSubsystemReceiverChannel;
		this.elevatorSubsystemReceiverChannel = elevatorSubsystemReceiverChannel;

		// Add floors to the floor subsystem
		for (int i = 0; i < floors.length; i++) {
			floors[i] = new Floor(i);
		}
	}

	/**
	 * This method reads in the input text file and converts it to
	 * SimulationFloorInputData objects as needed
	 *
	 */
	private void readInputFile() {
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new FileReader(inputFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		String input = "";

		try {
			while ((input = bufferedReader.readLine()) != null) {
				floorDataCollection.add(new SimulationFloorInputData(input));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is an override of the runnable run method
	 */
	@Override
	public void run() {
		// Only attempt to read file when a file name as been passed
		if (!inputFileName.equals(""))
			readInputFile();

		while (true) {

			/**
			 * Place input data in the transmission channel if we have data to send and the
			 * transmission channel is free.
			 */
			if (floorSubsystemTransmissonChannel.isEmpty() && !floorDataCollection.isEmpty()) {

				SimulationFloorInputData floorInputData = floorDataCollection.get(0);
				floorDataCollection.remove(0);

				ElevatorFloorRequest elevatorFloorRequest = new ElevatorFloorRequest(floorInputData.getCurrentFloor(),
						floorInputData.getFloorDirectionButton());

				// Updating the floor properties(User interacting with the floor button)
				int floorId = floorInputData.getCurrentFloor();
				floors[floorId].pressFloorButton(floorInputData.getFloorDirectionButton());
				floors[floorId].printFloorStatus();

				// sending the job to the scheduler
				floorSubsystemTransmissonChannel.appendMessage(elevatorFloorRequest);
			}

			// Checking if we have a request message
			if (!floorSubsystemReceiverChannel.isEmpty()) {
				handleRequest(floorSubsystemReceiverChannel.popMessage());
			}

		}

	}

	/**
	 * Get the floors
	 */
	public Floor[] getFloors() {
		return floors;
	}

	/**
	 * Handle message accordingly
	 *
	 * @param message the message
	 */
	public void handleRequest(Message message) {

		switch (message.getMessageType()) {

		case ELEVATOR_FLOOR_MESSAGE:
			handleElevatorRequest((FloorElevatorTargetedMessage) message);
			break;

		case SCHEDULER_FLOOR_COMMAND:
			handleSchedulerFloorCommand((SchedulerFloorCommand) message);
			break;

		default:
			break;
		}
	}

	/**
	 * Handle the elevator request appropriately
	 *
	 * @param request the request
	 */
	private void handleElevatorRequest(FloorElevatorTargetedMessage request) {

		int floorId = request.getFloorId();
		int elevatorId = request.getElevatorId();

		// Validate the floor id
		if (!SystemValidationUtil.isFloorNumberInRange(floorId)) {
			return;
		}

		switch (request.getRequestType()) {

		case ELEVATOR_FLOOR_SIGNAL_REQUEST:
			ElevatorFloorSignalRequestMessage floorSignalRequestMessage = (ElevatorFloorSignalRequestMessage) request;

			floors[floorId].notifyElevatorAtFloorArrival(floorId, elevatorId,
					floorSignalRequestMessage.getElevatorMotor(), elevatorSubsystemReceiverChannel,
					floorSignalRequestMessage.isFloorFinalDestination());
			break;

		case ELEVATOR_LEAVING_FLOOR_MESSAGE:
			floors[floorId].elevatorLeavingFloor(elevatorId);
			break;

		default:
			break;

		}
	}

	/**
	 * Handle scheduler floor command
	 *
	 * @param command the command
	 */
	private void handleSchedulerFloorCommand(SchedulerFloorCommand command) {

		// Validate the floor id
		if (!SystemValidationUtil.isFloorNumberInRange(command.getFloorId())) {
			return;
		}

		switch (command.getCommand()) {

		case TURN_OFF_FLOOR_LAMP:
			floors[command.getFloorId()].turnOffLampButton(command.getLampButtonDirection());
			break;

		default:
			break;
		}

	}
}
