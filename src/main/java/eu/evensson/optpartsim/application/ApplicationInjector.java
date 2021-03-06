package eu.evensson.optpartsim.application;

import java.util.Optional;
import java.util.Random;

import eu.evensson.optpartsim.di.ScopeEntry;
import eu.evensson.optpartsim.simulation.Simulation;
import eu.evensson.optpartsim.simulation.SimulationInjector;
import eu.evensson.optpartsim.simulation.SimulationScope;

public class ApplicationInjector {

	private static Optional<Random> random = Optional.empty();

	public static void setRandom(final Random random) {
		ApplicationInjector.random = Optional.ofNullable(random);
	}

	public static Application injectApplication(final ApplicationScope scope) {
		return new Application(injectPrinter(), injectArgumentParser(),
				injectParticleGenerator(), injectSimulationScopeEntry());
	}

	private static Printer injectPrinter() {
		return string -> System.out.println(string);
	}

	private static ArgumentParser injectArgumentParser() {
		return new JCommanderArgumentParser(injectPrinter());
	}

	private static ParticleGenerator injectParticleGenerator() {
		return new RandomParticleGenerator(injectRandom());
	}

	private static Random injectRandom() {
		return random.orElse(new Random(injectSeed()));
	}

	private static long injectSeed() {
		return 0;
	}

	private static ScopeEntry<SimulationScope, Simulation> injectSimulationScopeEntry() {
		return scope -> SimulationInjector.injectSimulation(scope);
	}
}
