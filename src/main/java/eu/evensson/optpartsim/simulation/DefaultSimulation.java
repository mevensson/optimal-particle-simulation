package eu.evensson.optpartsim.simulation;

import java.util.List;

import eu.evensson.optpartsim.physics.Particle;
import eu.evensson.optpartsim.simulation.EventQueue.EventQueueEmptyException;

public class DefaultSimulation implements Simulation {

	private final CellStructure cellStructure;
	private final EventQueue eventQueue;
	private final EventChecker eventChecker;
	private final EventHandler eventHandler;

	public DefaultSimulation(final CellStructure cellStructure, final EventQueue eventQueue,
			final EventChecker eventChecker, final EventHandler eventHandler) {
		this.cellStructure = cellStructure;
		this.eventQueue = eventQueue;
		this.eventChecker = eventChecker;
		this.eventHandler = eventHandler;
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
				totalMomentum += eventHandler.handle(event);
			}
		} catch (final EventQueueEmptyException e) {
		}

		return totalMomentum;
	}

}
