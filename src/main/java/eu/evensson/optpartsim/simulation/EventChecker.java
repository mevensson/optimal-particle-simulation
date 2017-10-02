package eu.evensson.optpartsim.simulation;

import java.util.Collection;
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
				wallBounceHorizontalEvent(particle),
				wallBounceVerticalEvent(particle),
				collisionEvent(particle))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.min(Comparator.comparingDouble(event -> event.time()))
			.get();
	}

	private Optional<Event> wallBounceHorizontalEvent(final Particle particle) {
		final Box walls = cellStructure.getWalls();
		final double bounceTime = particle.collisionTime(walls, Direction.HORIZONTAL);
		return Optional.of(
				new WallBounceEvent(bounceTime, particle, Direction.HORIZONTAL));
	}

	private Optional<Event> wallBounceVerticalEvent(final Particle particle) {
		final Box walls = cellStructure.getWalls();
		final double bounceTime = particle.collisionTime(walls, Direction.VERTICAL);
		return Optional.of(
				new WallBounceEvent(bounceTime, particle, Direction.VERTICAL));
	}

	private Optional<Event> collisionEvent(final Particle particle) {
		final Collection<Particle> neighbourParticles =
				cellStructure.neighbourParticles(particle);
		Optional<Double> earliestCollisionTime = Optional.empty();
		Optional<Particle> collisionParticle = Optional.empty();
		for (final Particle neighbourParticle : neighbourParticles) {
			final Optional<Double> collisionTime =
					particle.collisionTime(neighbourParticle);
			if (collisionTime.isPresent()
					&& (!earliestCollisionTime.isPresent()
						|| collisionTime.get() < earliestCollisionTime.get())) {
				earliestCollisionTime = collisionTime;
				collisionParticle = Optional.of(neighbourParticle);
			}
		}
		if (collisionParticle.isPresent()) {
			return Optional.of(new CollisionEvent(earliestCollisionTime.get(),
					particle, collisionParticle.get()));
		}
		return Optional.empty();
	}
}
