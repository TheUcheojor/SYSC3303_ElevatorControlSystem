/**
 *
 */
package common;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class stores the properties of a given simulation floor input.
 *
 * @author paulokenne
 *
 */
public class SimulationFloorInputData {

	/**
	 * The floor data input separator
	 */
	private static final String FLOOR_DATA_INPUT_SEPARATOR = " ";

	/**
	 * The data formatter for the time field
	 */
	private final SimpleDateFormat DATA_FORMAT_FOR_ARRIVAL_TIME = new SimpleDateFormat("hh:mm:ss.mmm");
	/**
	 * The time stamp indicating when the passenger arrives.
	 */
	private String arrivalTime;

	/**
	 * The current floor.
	 */
	private Integer currentFloor;

	/**
	 * The elevator direction that the passenger wishes to go to reach his or her
	 * destination
	 */
	private Direction floorDirectionButton;

	/**
	 * The floor that the passenger wishes to go to.
	 **/
	private Integer destinationFloorCarButton;

	/**
	 * Constructor.
	 *
	 * @param arrivalTime               the arrival time
	 * @param currentFloor              the current floor
	 * @param floorDirectionButton      the floor direction
	 * @param destinationFloorCarButton the target floor
	 */
	public SimulationFloorInputData(String arrivalTime, Integer currentFloor, Direction floorDirectionButton,
			Integer destinationFloorCarButton) {
		this.arrivalTime = arrivalTime;
		this.currentFloor = currentFloor;
		this.floorDirectionButton = floorDirectionButton;
		this.destinationFloorCarButton = destinationFloorCarButton;
	}

	/**
	 * Constructor.
	 *
	 * @param dataString the data line string with formating: "Time Floor
	 *                   FloorButton CarButton"
	 */
	public SimulationFloorInputData(String dataString) throws InvalidParameterException {

		try {
			// Format of data String: Time Floor FloorButton CarButton
			String[] data = dataString.split(FLOOR_DATA_INPUT_SEPARATOR);

			System.out.println(data.length);
			Date parsedDate = DATA_FORMAT_FOR_ARRIVAL_TIME.parse(data[0]);
			this.arrivalTime = DATA_FORMAT_FOR_ARRIVAL_TIME.format(parsedDate);

			this.currentFloor = Integer.parseInt(data[1]);
			this.floorDirectionButton = Direction.valueOf(data[2]);
			this.destinationFloorCarButton = Integer.parseInt(data[3]);

		} catch (Exception e) {
			System.out.println(e);
			throw new InvalidParameterException(dataString);
		}

	}

	/**
	 * Gets the arrival time
	 *
	 * @return the arrivalTime
	 */
	public String getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Gets the current floor
	 *
	 * @return the currentFloor
	 */
	public Integer getCurrentFloor() {
		return currentFloor;
	}

	/**
	 * Gets the floor direction
	 *
	 * @return the floorDirectionButton
	 */
	public Direction getFloorDirectionButton() {
		return floorDirectionButton;
	}

	/**
	 * Gets the destination floor
	 *
	 * @return the destinationFloorCarButton
	 */
	public Integer getDestinationFloorCarButton() {
		return destinationFloorCarButton;
	}

}
