package eu.evensson.optpartsim;

public class Main {

	public static void main(final String[] args) {
		final ApplicationScope scope = new ApplicationScope();
		final Application application = ApplicationInjector.injectApplication(scope);
		application.run(args);
	}

}
