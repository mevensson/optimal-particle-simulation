package eu.evensson.optpartsim.simulation;

import java.util.Comparator;
import java.util.stream.Stream;

import eu.evensson.optpartsim.physics.Box;
import eu.evensson.optpartsim.physics.Particle;
import eu.evensson.optpartsim.physics.Particle.Direction;

public class EventChecker {

	private CellStructure cellStructure;

	public EventChecker(final CellStructure cellStructure) {
		this.cellStructure = cellStructure;
	}

	public Event check(final Particle particle) {
		return Stream.of(
				wallBounceHorizontalEvent(particle),
				wallBounceVerticalEvent(particle))
			.min(Comparator.comparingDouble(event -> event.time()))
			.get();
	}

	private Event wallBounceHorizontalEvent(final Particle particle) {
		final Box walls = cellStructure.getWalls();
		final double bounceTime = particle.collisionTime(walls, Direction.HORIZONTAL);
		return new WallBounceEvent(bounceTime, particle, Direction.HORIZONTAL);
	}

	private Event wallBounceVerticalEvent(final Particle particle) {
		final Box walls = cellStructure.getWalls();
		final double bounceTime = particle.collisionTime(walls, Direction.VERTICAL);
		return new WallBounceEvent(bounceTime, particle, Direction.VERTICAL);
	}
}
