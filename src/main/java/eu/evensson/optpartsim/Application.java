package eu.evensson.optpartsim;

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

		particleGenerator.generate(
				arguments.particles(), arguments.maxInitialVelocity());

		final double result = simulation.simulate();

		printer.print(String.format(SIMULATION_RESULT, result));
	}

}
