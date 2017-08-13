package eu.evensson.optpartsim.simulation;

import eu.evensson.optpartsim.physics.Box;
import eu.evensson.optpartsim.physics.Particle;

public class EventChecker {

	private CellStructure cellStructure;

	public EventChecker(final CellStructure cellStructure) {
		this.cellStructure = cellStructure;
	}

	public Event check(final Particle particle) {
		final Box walls = cellStructure.getWalls();
		final double distance = particle.position().x() - walls.x();
		return new WallBounceEvent(
				distance / -particle.velocity().x(), particle);
	}

}
