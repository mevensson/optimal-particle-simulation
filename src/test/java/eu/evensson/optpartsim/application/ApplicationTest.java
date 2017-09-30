package eu.evensson.optpartsim.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import eu.evensson.optpartsim.di.ScopeEntry;
import eu.evensson.optpartsim.physics.Box;
import eu.evensson.optpartsim.physics.Particle;
import eu.evensson.optpartsim.simulation.Simulation;
import eu.evensson.optpartsim.simulation.SimulationScope;

@DisplayName("An Application")
public class ApplicationTest {

	private static final String[] ARGS = new String[0];

	private static final String SIMULATION_RESULT_TEXT =
			"Total momentum is %g.";

	private static final Double SIMULATION_RESULT = 12.34;

	Printer printer = mock(Printer.class);
	ArgumentParser argumentParser = mock(ArgumentParser.class);
	ParticleGenerator particleGenerator = mock(ParticleGenerator.class);
	Simulation simulation = mock(Simulation.class);
	Arguments arguments = mock(Arguments.class);
	@SuppressWarnings("unchecked")
	private ScopeEntry<SimulationScope, Simulation> simulationScopeEntry =
			mock(ScopeEntry.class);

	Application application;


	@BeforeEach
	void createApplication() {
		application = new Application(printer, argumentParser,
				particleGenerator, simulationScopeEntry);
	}

	@BeforeEach
	void mockArguments() {
		when(argumentParser.parse(ARGS)).thenReturn(arguments);
	}

	@BeforeEach
	void mockSimulationScopeEntry() {
		when(simulationScopeEntry.enter(any())).thenReturn(simulation);
	}

	@DisplayName("parses arguments")
	@Test
	void parsesArguments() {
		application.run(ARGS);

		verify(argumentParser).parse(same(ARGS));
	}

	@DisplayName("skips simulation if argument parsing fails")
	@Test
	void skipsSimulationIfArgumentParsingFails() {
		when(argumentParser.parse(ARGS)).thenReturn(null);

		application.run(ARGS);

		verify(simulation, never()).simulate(any(), anyDouble());
	}

	@DisplayName("generates particles")
	@ParameterizedTest
	@CsvSource({ "1234, 56.78, 90.12, 34.56" })
	void generatesParticles(final long numParticles,
			final double boxHeight, final double boxWidth, final double maxInitialVelocity) {
		final String[] args = new String[] {
				"-p", Long.toString(numParticles),
				"-v", Double.toString(maxInitialVelocity),
				"-h", Double.toString(boxHeight),
				"-w", Double.toString(boxWidth)
		};
		final JCommanderArgumentParser realArgumentParser = new JCommanderArgumentParser(printer);
		final Arguments realArguments = realArgumentParser.parse(args);

		when(argumentParser.parse(args)).thenReturn(realArguments);

		application.run(args);

		final Box expectedBox = new Box(
				Particle.RADIUS,
				Particle.RADIUS,
				boxWidth - 2.0 * Particle.RADIUS,
				boxHeight - 2.0 * Particle.RADIUS);
		verify(particleGenerator).generate(
				numParticles, expectedBox, maxInitialVelocity);
	}

	@DisplayName("enters simulation scope")
	@ParameterizedTest
	@CsvSource({ "12.34, 56.78" })
	void entersSimulationScope(final double boxHeight, final double boxWidth) {
		final String[] args = new String[] {
				"-h", Double.toString(boxHeight),
				"-w", Double.toString(boxWidth)
		};
		final JCommanderArgumentParser realArgumentParser = new JCommanderArgumentParser(printer);
		final Arguments realArguments = realArgumentParser.parse(args);
		when(argumentParser.parse(args)).thenReturn(realArguments);

		application.run(args);

		final SimulationScope expectedSimulationScope =
				new SimulationScope(boxWidth, boxHeight);
		verify(simulationScopeEntry).enter(expectedSimulationScope);
	}

	@DisplayName("simulates with the particle list and simulation duration")
	@Test
	void simulatesWithParticleListAndDuration() {
		final List<Particle> particleList = new ArrayList<>();
		when(particleGenerator.generate(anyLong(), any(), anyDouble()))
				.thenReturn(particleList);

		final double duration = 12.34;
		when(arguments.simulationDuration()).thenReturn(duration);

		application.run(ARGS);

		verify(simulation).simulate(same(particleList), eq(duration));
	}

	@DisplayName("prints simulation result")
	@Test
	void printsSimulationResult() {
		when(simulation.simulate(any(), anyDouble())).thenReturn(SIMULATION_RESULT);

		application.run(ARGS);

		verify(printer).print(
				String.format(SIMULATION_RESULT_TEXT, SIMULATION_RESULT));
	}
}
