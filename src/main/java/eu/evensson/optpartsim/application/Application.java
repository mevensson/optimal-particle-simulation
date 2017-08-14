package eu.evensson.optpartsim.application;

import java.util.List;

import eu.evensson.optpartsim.di.ScopeEntry;
import eu.evensson.optpartsim.physics.Particle;
import eu.evensson.optpartsim.simulation.Simulation;
import eu.evensson.optpartsim.simulation.SimulationScope;

public class Application {

	private static final String SIMULATION_RESULT =
			"Total momentum is %g.";

	private final Printer printer;
	private final ArgumentParser argumentParser;
	private final ParticleGenerator particleGenerator;
	private final ScopeEntry<SimulationScope, Simulation> simulationScopeEntry;

	public Application(final Printer printer,
			final ArgumentParser argumentParser,
			final ParticleGenerator particleGenerator,
			final ScopeEntry<SimulationScope, Simulation> simulationScopeEntry) {
		this.printer = printer;
		this.argumentParser = argumentParser;
		this.particleGenerator = particleGenerator;
		this.simulationScopeEntry = simulationScopeEntry;
	}

	public void run(final String[] args) {
		final Arguments arguments = argumentParser.parse(args);
		if (arguments == null) {
			return;
		}

		final List<Particle> particleList = particleGenerator.generate(
				arguments.particles(),
				arguments.boxHeight(),
				arguments.boxWidth(),
				arguments.maxInitialVelocity());

		final Simulation simulation = simulationScopeEntry.enter(
				new SimulationScope(arguments.boxWidth(), arguments.boxHeight()));

		final double result = simulation.simulate(
				particleList, arguments.simulationDuration());

		printer.print(String.format(SIMULATION_RESULT, result));
	}

}
