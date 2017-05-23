package eu.evensson.optpartsim;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("An Application")
@RunWith(JUnitPlatform.class)
public class ApplicationTest {

	private static final String USAGE_TEXT =
			"Usage: optpartsim <particles> <time> <max velocity>";
	private static final String SIMULATION_RESULT_TEXT =
			"Total momentum is %g.";

	private static final long PARTICLES = 12;
	private static final double TIME = 34.56;
	private static final long MAX_VELOCITY = 78;
	private static final long SIZE = 90;
	private static final Double SIMULATION_RESULT = 12.34;

	Printer printer = mock(Printer.class);
	Simulation simulation = mock(Simulation.class);

	Application application;

	@BeforeEach
	void createApplication() {
		application = new Application(printer, simulation);
	}

	@DisplayName("prints usage if less than 4 arguments")
	@Test
	void printsUsageIfLessThan3Arguments() {
		final String[] args = new String[3];
		application.run(args);

		verify(printer).print(USAGE_TEXT);
	}

	@DisplayName("prints usage if more than 4 arguments")
	@Test
	void printsUsageIfMoreThan4Arguments() {
		final String[] args = new String[5];
		application.run(args);

		verify(printer).print(USAGE_TEXT);
	}

	@DisplayName("calls simulate with the 4 arguments")
	@Test
	void callsSimulateWithThe3Arguments() {
		final String[] args = new String[] {
				Long.toString(PARTICLES),
				Double.toString(TIME),
				Long.toString(MAX_VELOCITY),
				Long.toString(SIZE)
		};

		application.run(args);

		verify(simulation).simulate(PARTICLES, TIME, MAX_VELOCITY, SIZE);
	}

	@DisplayName("prints simulation result")
	@Test
	void printsSimulationResult() {
		final String[] args = new String[] {
				Long.toString(PARTICLES),
				Double.toString(TIME),
				Long.toString(MAX_VELOCITY),
				Long.toString(SIZE)
		};
		when(simulation.simulate(anyLong(), anyDouble(), anyLong(), anyLong()))
				.thenReturn(SIMULATION_RESULT);

		application.run(args);

		verify(printer).print(
				String.format(SIMULATION_RESULT_TEXT, SIMULATION_RESULT));
	}
}
