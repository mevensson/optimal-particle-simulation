package eu.evensson.optpartsim;

public class Application {

	private static final String SIMULATION_RESULT =
			"Total momentum is %g.";

	private final Printer printer;
	private final Simulation simulation;

	public Application(final Printer printer, final Simulation simulation) {
		this.printer = printer;
		this.simulation = simulation;
	}

	public void run() {
		final double result = simulation.simulate();

		printer.print(String.format(SIMULATION_RESULT, result));
	}

}
