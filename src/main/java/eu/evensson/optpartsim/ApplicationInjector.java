package eu.evensson.optpartsim;

import java.util.List;
import java.util.Random;

public class ApplicationInjector {

	public static Application injectApplication(final ApplicationScope scope) {
		return new Application(injectPrinter(), injectArgumentParser(),
				injectParitcleGenerator(), injectSimulation());
	}

	private static Printer injectPrinter() {
		return string -> System.out.println(string);
	}

	private static ArgumentParser injectArgumentParser() {
		return new JCommanderArgumentParser(injectPrinter());
	}

	private static ParticleGenerator injectParitcleGenerator() {
		return new RandomParticleGenerator(injectRandom());
	}

	private static Simulation injectSimulation() {
		return new Simulation() {
			@Override
			public double simulate(final List<Particle> list) {
				return 0;
			}
		};
	}

	private static Random injectRandom() {
		return new Random(injectSeed());
	}

	private static long injectSeed() {
		return 0;
	}

}
