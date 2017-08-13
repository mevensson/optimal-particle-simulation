package eu.evensson.optpartsim.simulation;

import java.util.Optional;

import eu.evensson.optpartsim.physics.Box;
import eu.evensson.optpartsim.physics.Particle;

public class EventChecker {

	private CellStructure cellStructure;

	public EventChecker(final CellStructure cellStructure) {
		this.cellStructure = cellStructure;
	}

	public Event check(final Particle particle) {
		final Optional<Double> wallBounceLeftTime = wallBounceLeftTime(particle);
		if (wallBounceLeftTime.isPresent()) {
			return new WallBounceEvent(wallBounceLeftTime.get(), particle);
		}

		final Optional<Double> wallBounceRightTime = wallBounceRightTime(particle);
		return new WallBounceEvent(wallBounceRightTime.get(), particle);
	}

	private Optional<Double> wallBounceLeftTime(final Particle particle) {
		final Box walls = cellStructure.getWalls();
		final double distance = particle.position().x() - walls.x();
		final double bounceTime = distance / -particle.velocity().x();
		if (bounceTime > 0) {
			return Optional.of(bounceTime);
		}

		return Optional.empty();
	}

	private Optional<Double> wallBounceRightTime(final Particle particle) {
		final Box walls = cellStructure.getWalls();
		final double distance =
				(walls.x() + walls.width()) - particle.position().x();
		return Optional.of(distance / particle.velocity().x());
	}
}
