package eu.evensson.optpartsim;

public class ApplicationInjector {

	public static Application injectApplication(final ApplicationScope scope) {
		return new Application(injectPrinter(), injectArgumentParser(),
				injectSimulation());
	}

	private static Printer injectPrinter() {
		return string -> System.out.println(string);
	}

	private static ArgumentParser injectArgumentParser() {
		return new JCommanderArgumentParser(injectPrinter());
	}

	private static Simulation injectSimulation() {
		return new Simulation() {
			@Override
			public double simulate() {
				return 0;
			}
		};
	}

}
