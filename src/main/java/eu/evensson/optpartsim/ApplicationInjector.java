package eu.evensson.optpartsim;

public class ApplicationInjector {

	public static Application injectApplication() {
		return new Application(injectPrinter(), injectSimulation());
	}

	private static Printer injectPrinter() {
		return string -> System.out.println(string);
	}

	private static Simulation injectSimulation() {
		return null;
	}

}