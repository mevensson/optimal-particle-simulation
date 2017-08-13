package eu.evensson.optpartsim.simulation;

import java.util.List;

import eu.evensson.optpartsim.physics.Particle;
import eu.evensson.optpartsim.simulation.EventQueue.EventQueueEmptyException;

public class DefaultSimulation implements Simulation {

	private static final double MASS = 1.0;

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
	public double simulate(final List<Particle> particles, final double duration) {
		for (final Particle particle : particles) {
			cellStructure.insert(particle);
			eventQueue.add(eventChecker.check(particle));
		}

		double totalMomentum = 0.0;
		try {
			while (eventQueue.peek().time() <= duration) {
				final Event event = eventQueue.removeFirst();
				if (event instanceof WallBounceEvent) {
					final WallBounceEvent wallBounceEvent = (WallBounceEvent) event;
					final double speed = -wallBounceEvent.particle().velocity()
							.x();
					totalMomentum += speed * MASS;
				}
			}
		} catch (final EventQueueEmptyException e) {
		}

		return totalMomentum;
	}

}
