package eu.evensson.optpartsim.simulation;

import eu.evensson.optpartsim.physics.Particle;
import eu.evensson.optpartsim.physics.Particle.Direction;

public class EventHandler {

	private final CellStructure cellStructure;
	private final EventChecker eventChecker;
	private final EventQueue eventQueue;

	public EventHandler(final CellStructure cellStructure, final EventChecker eventChecker,
			final EventQueue eventQueue) {
		this.cellStructure = cellStructure;
		this.eventChecker = eventChecker;
		this.eventQueue = eventQueue;
	}

	public double handle(final Event event) {
		double momentum = 0;
		if (event instanceof CollisionEvent) {
			handleCollisionEvent((CollisionEvent) event);
		} else if (event instanceof WallBounceEvent) {
			momentum = handleWallBounceEvent((WallBounceEvent) event);
		}
		return momentum;
	}

	private void handleCollisionEvent(final CollisionEvent collisionEvent) {
		final Particle particle = collisionEvent.particle();
		final Particle otherParticle = collisionEvent.otherParticle();
		handleCollision(particle, otherParticle);
		handleCollision(otherParticle, particle);
	}

	private void handleCollision(final Particle particle, final Particle otherParticle) {
		cellStructure.remove(particle);

		final Particle newParticle1 = particle.collide(otherParticle);
		cellStructure.insert(newParticle1);
		eventQueue.add(eventChecker.check(newParticle1));
	}

	private double handleWallBounceEvent(final WallBounceEvent wallBounceEvent) {
		final Particle particle = wallBounceEvent.particle();
		cellStructure.remove(particle);

		final double time = wallBounceEvent.time();
		final Direction direction = wallBounceEvent.direction();
		final Particle newParticle = particle.move(time).bounce(direction);
		cellStructure.insert(newParticle);

		eventQueue.add(eventChecker.check(newParticle));

		return particle.momentum(direction);
	}

}
