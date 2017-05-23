package eu.evensson.optpartsim;

public class Application {

	private static final String SIMULATION_RESULT =
			"Total momentum is %g.";
	private static final String USAGE =
			"Usage: optpartsim <particles> <time> <max velocity>";

	private final Printer printer;
	private final Simulation simulation;

	public Application(final Printer printer, final Simulation simulation) {
		this.printer = printer;
		this.simulation = simulation;
	}

	public void run(final String[] args) {
		if (args.length != 3) {
			printer.print(USAGE);
			return;
		}

		final double result = simulation.simulate(
				Long.parseLong(args[0]),
				Double.parseDouble(args[1]),
				Long.parseLong(args[2]));

		printer.print(String.format(SIMULATION_RESULT, result));
	}

}
