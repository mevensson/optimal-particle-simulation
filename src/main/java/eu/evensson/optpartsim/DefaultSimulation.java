package eu.evensson.optpartsim;

import java.util.List;

public class DefaultSimulation implements Simulation {

	private final CellStructure cellStructure;
	private final EventQueue eventQueue;
	private EventChecker eventChecker;

	public DefaultSimulation(final CellStructure cellStructure,
			final EventQueue eventQueue, final EventChecker eventChecker) {
		this.cellStructure = cellStructure;
		this.eventQueue = eventQueue;
		this.eventChecker = eventChecker;
	}

	@Override
	public double simulate(final List<Particle> particles) {
		for (final Particle particle : particles) {
			cellStructure.insert(particle);
			eventQueue.add(eventChecker.check(particle));
		}

		return 0.0;
	}

}
