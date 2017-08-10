package eu.evensson.optpartsim;

import java.util.List;

public class Application {

	private static final String SIMULATION_RESULT =
			"Total momentum is %g.";

	private final Printer printer;
	private final ArgumentParser argumentParser;
	private final ParticleGenerator particleGenerator;
	private final Simulation simulation;

	public Application(final Printer printer,
			final ArgumentParser argumentParser,
			final ParticleGenerator particleGenerator,
			final Simulation simulation) {
		this.printer = printer;
		this.argumentParser = argumentParser;
		this.particleGenerator = particleGenerator;
		this.simulation = simulation;
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

		final double result = simulation.simulate(particleList);

		printer.print(String.format(SIMULATION_RESULT, result));
	}

}
