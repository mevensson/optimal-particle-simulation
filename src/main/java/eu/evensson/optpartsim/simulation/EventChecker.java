package eu.evensson.optpartsim.simulation;

import java.util.Comparator;
import java.util.Optional;
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
				wallBounceLeftTime(particle),
				wallBounceRightTime(particle),
				wallBounceTopTime(particle))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.min(Comparator.comparingDouble(event -> event.time()))
			.get();
	}

	private Optional<Event> wallBounceLeftTime(final Particle particle) {
		final Box walls = cellStructure.getWalls();
		final double distance = particle.position().x() - walls.x();
		final double timeToBounce = distance / -particle.velocity().x();
		if (timeToBounce > 0) {
			return Optional.of(new WallBounceEvent(
					timeToBounce + particle.time(), particle, Direction.HORIZONTAL));
		}

		return Optional.empty();
	}

	private Optional<Event> wallBounceRightTime(final Particle particle) {
		final Box walls = cellStructure.getWalls();
		final double distance =
				(walls.x() + walls.width()) - particle.position().x();
		final double timeToBounce = distance / particle.velocity().x();
		if (timeToBounce > 0) {
			return Optional.of(new WallBounceEvent(
					timeToBounce + particle.time(), particle, Direction.HORIZONTAL));
		}

		return Optional.empty();
	}

	private Optional<Event> wallBounceTopTime(final Particle particle) {
		final Box walls = cellStructure.getWalls();
		final double distance = particle.position().y() - walls.y();
		final double timeToBounce = distance / -particle.velocity().y();
		if (timeToBounce > 0) {
			return Optional.of(new WallBounceEvent(
					timeToBounce + particle.time(), particle, Direction.VERTICAL));
		}

		return Optional.empty();
	}
}
