package tests.FloorSubsystem;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import FloorSubsystem.Floor;
import common.Direction;

/**
 * Tests the FloorInfo class.
 *
 * @author Favour
 *
 */
class FloorInfoTest {

	/**
	 * Instance of the FloorInfo class
	 */
	Floor floor;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		floor = new Floor(1);

	}

	/**
	 * Test method for Floor number
	 */
	@Test
	void testFloorNumber() {
		floor = new Floor(2);
		assertTrue(floor.getFloorNumber() == 2);
	}

	/**
	 * Test method for button press.
	 */
	@Test
	void testButtonPress() {
		floor.pressFloorButton(Direction.UP);
		assertTrue(floor.getUpLampButton().getDirection() == Direction.UP);
		assertTrue(floor.getUpLampButton().isButtonPressed());
	}
}
