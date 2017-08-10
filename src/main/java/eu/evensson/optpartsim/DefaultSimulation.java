package eu.evensson.optpartsim;

import java.util.List;

public class DefaultSimulation implements Simulation {

	private final CellStructure cellStructure;
	private final EventQueue eventQueue;

	public DefaultSimulation(final CellStructure cellStructure,
			final EventQueue eventQueue) {
		this.cellStructure = cellStructure;
		this.eventQueue = eventQueue;
	}

	@Override
	public double simulate(final List<Particle> particles) {
		for (final Particle particle : particles) {
			final Box box = cellStructure.insert(particle);
			eventQueue.add(new TransferEvent(particle.intersects(box), particle));
		}

		return 0.0;
	}

}
