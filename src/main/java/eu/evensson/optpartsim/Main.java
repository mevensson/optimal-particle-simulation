package eu.evensson.optpartsim;

public class Main {

	public static void main(final String[] args) {
		final Application application = ApplicationInjector.injectApplication();
		application.run();
	}

}
