package eu.evensson.optpartsim;

import eu.evensson.optpartsim.application.Application;
import eu.evensson.optpartsim.application.ApplicationInjector;
import eu.evensson.optpartsim.application.ApplicationScope;

public class Main {

	public static void main(final String[] args) {
		final ApplicationScope scope = new ApplicationScope();
		final Application application = ApplicationInjector.injectApplication(scope);
		application.run(args);
	}

}
