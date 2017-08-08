package eu.evensson.optpartsim;

public class Application {

	private static final String SIMULATION_RESULT =
			"Total momentum is %g.";

	private final Printer printer;
	private final ArgumentParser argumentParser;
	private final Simulation simulation;


	public Application(final Printer printer,
			final ArgumentParser argumentParser, final Simulation simulation) {
		this.printer = printer;
		this.argumentParser = argumentParser;
		this.simulation = simulation;
	}

	public void run(final String[] args) {
		if (argumentParser.parse(args) == null) {
			return;
		}

		final double result = simulation.simulate();

		printer.print(String.format(SIMULATION_RESULT, result));
	}

}
