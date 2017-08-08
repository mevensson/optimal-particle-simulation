package eu.evensson.optpartsim;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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

	private static final String[] ARGS = new String[0];

	private static final String SIMULATION_RESULT_TEXT =
			"Total momentum is %g.";

	private static final Double SIMULATION_RESULT = 12.34;

	Printer printer = mock(Printer.class);
	ArgumentParser argumentParser = mock(ArgumentParser.class);
	Simulation simulation = mock(Simulation.class);

	Application application;

	@BeforeEach
	void createApplication() {
		application = new Application(printer, argumentParser, simulation);
	}

	@DisplayName("parses arguments")
	@Test
	void parsesArguments() {
		when(argumentParser.parse(ARGS)).thenReturn(new Arguments());

		application.run(ARGS);

		verify(argumentParser).parse(same(ARGS));
	}

	@DisplayName("skips simulation if argument parsing fails")
	@Test
	void skipsSimulationIfArgumentParsingFails() {
		when(argumentParser.parse(ARGS)).thenReturn(null);

		application.run(ARGS);

		verify(simulation, never()).simulate();
	}

	@DisplayName("prints simulation result")
	@Test
	void printsSimulationResult() {
		when(argumentParser.parse(ARGS)).thenReturn(new Arguments());
		when(simulation.simulate()).thenReturn(SIMULATION_RESULT);

		application.run(ARGS);

		verify(printer).print(
				String.format(SIMULATION_RESULT_TEXT, SIMULATION_RESULT));
	}
}
