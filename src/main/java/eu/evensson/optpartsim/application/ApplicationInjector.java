package eu.evensson.optpartsim.application;

import java.util.Random;

import eu.evensson.optpartsim.physics.Box;
import eu.evensson.optpartsim.simulation.CellStructure;
import eu.evensson.optpartsim.simulation.DefaultSimulation;
import eu.evensson.optpartsim.simulation.EventChecker;
import eu.evensson.optpartsim.simulation.EventQueue;
import eu.evensson.optpartsim.simulation.Simulation;

public class ApplicationInjector {

	private static Random random = null;

	public static void setRandom(final Random random) {
		ApplicationInjector.random = random;
	}

	public static Application injectApplication(final ApplicationScope scope) {
		return new Application(injectPrinter(), injectArgumentParser(),
				injectParticleGenerator(), injectSimulation());
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
		if (random != null) {
			return random;
		}

		return new Random(injectSeed());
	}

	private static long injectSeed() {
		return 0;
	}

	private static Simulation injectSimulation() {
		return new DefaultSimulation(injectCellStructure(), injectEventQueue(),
				injectEventChecker());
	}

	private static CellStructure injectCellStructure() {
		return new CellStructure(injectWalls(), 1, 1);
	}

	private static Box injectWalls() {
		return new Box(0.0, 0.0, 10.0, 10.0);
	}

	private static EventQueue injectEventQueue() {
		return new EventQueue();
	}


	private static EventChecker injectEventChecker() {
		return new EventChecker(injectCellStructure());
	}
}
