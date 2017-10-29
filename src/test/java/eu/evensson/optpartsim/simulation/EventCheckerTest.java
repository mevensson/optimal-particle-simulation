package eu.evensson.optpartsim.simulation;

import static eu.evensson.optpartsim.physics.Vector.vector;
import static java.lang.Math.ulp;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import eu.evensson.optpartsim.physics.Box;
import eu.evensson.optpartsim.physics.Particle;
import eu.evensson.optpartsim.physics.Vector;

@DisplayName("An Event Checker")
public class EventCheckerTest {

	private static final long ID = 0;
	private static final Box WALLS = new Box(5.0, 10.0, 20.0, 40.0);
	private static final Vector POSITION = vector(
			WALLS.x() + WALLS.width() / 3.0,
			WALLS.y() + WALLS.height() / 3.0);

	private static final double TIME = 100.0;

	private final CellStructure cellStructure = mock(CellStructure.class);

	private EventChecker anEventChecker;

	@BeforeEach
	void createEventChecker() {
		when(cellStructure.getWalls()).thenReturn(WALLS);
		anEventChecker = new EventChecker(cellStructure);
	}

	@DisplayName("returns wall bounce left event")
	@Test
	void returnsWallBounceLeftEvent() {
		final double speed = 1.0;
		final Vector velocity = vector(-speed , 0.0);
		final Particle particle = new Particle(ID, TIME, POSITION, velocity);

		final Event event = anEventChecker.check(particle);

		final double distanceToWall = POSITION.x() - WALLS.x()
				- Particle.RADIUS;
		final double timeToWall = distanceToWall / speed;
		final WallBounceEvent expectedEvent = new WallBounceEvent(
				TIME + timeToWall, particle, Particle.Direction.HORIZONTAL);
		assertThat(event, is(expectedEvent));
	}

	@DisplayName("returns wall bounce right event")
	@Test
	void returnsWallBounceRightEvent() {
		final double speed = 1.0;
		final Vector velocity = vector(speed , 0.0);
		final Particle particle = new Particle(ID, TIME, POSITION, velocity);

		final Event event = anEventChecker.check(particle);

		final double distanceToWall = WALLS.x() + WALLS.width() - POSITION.x()
				- Particle.RADIUS;
		final double timeToWall = distanceToWall / speed;
		final WallBounceEvent expectedEvent = new WallBounceEvent(
				TIME + timeToWall, particle, Particle.Direction.HORIZONTAL);
		assertThat(event, is(expectedEvent));
	}

	@DisplayName("returns wall bounce top event")
	@Test
	void returnsWallBounceTopEvent() {
		final double speed = 1.0;
		final Vector velocity = vector(0.0, -speed);
		final Particle particle = new Particle(ID, TIME, POSITION, velocity);

		final Event event = anEventChecker.check(particle);

		final double distanceToWall = POSITION.y() - WALLS.y()
				- Particle.RADIUS;
		final double timeToWall = distanceToWall / speed;
		final WallBounceEvent expectedEvent = new WallBounceEvent(
				TIME + timeToWall, particle, Particle.Direction.VERTICAL);
		assertThat(event, is(expectedEvent));
	}

	@DisplayName("returns wall bounce bottom event")
	@Test
	void returnsWallBounceBottomEvent() {
		final double speed = 1.0;
		final Vector velocity = vector(0.0, speed);
		final Particle particle = new Particle(ID, TIME, POSITION, velocity);

		final Event event = anEventChecker.check(particle);

		final double distanceToWall = WALLS.y() + WALLS.height() - POSITION.y()
				- Particle.RADIUS;
		final double timeToWall = distanceToWall / speed;
		final WallBounceEvent expectedEvent = new WallBounceEvent(
				TIME + timeToWall, particle, Particle.Direction.VERTICAL);
		assertThat(event, is(expectedEvent));
	}

	@DisplayName("returns collision event")
	@Nested
	class ReturnsCollisionEvent {

		Particle collidingParticle(final long id, final Particle particle,
				final double timeToCollision) {
			final Vector velocity = particle.velocity();
			final Vector newVelocity = velocity.multiply(-1.0);

			final Vector position = particle.position();
			final Vector collisionPosition = position.add(velocity.multiply(timeToCollision));
			final Vector newCollisionPosition = collisionPosition.subtract(
					newVelocity.unit().multiply(Particle.RADIUS * 2));
			final Vector newPosition = newCollisionPosition.subtract(
					newVelocity.multiply(timeToCollision));

			return new Particle(id, particle.time(), newPosition, newVelocity);
		}

		@DisplayName("for the earliest collision")
		@Test
		void forEarliestCollision() {
			final double timeToCollision = 1.0;
			final Vector velocity = vector(1.0, 0.0);
			final Particle particle = new Particle(ID, TIME, POSITION, velocity);
			final Particle collidingParticle1 = collidingParticle(ID + 1, particle, timeToCollision + 1.0);
			final Particle nonCollidingParticle = new Particle(ID + 2, TIME,
					POSITION.add(vector(0.0, 2.0)), velocity);
			final Particle collidingParticle2 = collidingParticle(ID + 3, particle, timeToCollision );
			when(cellStructure.neighbourParticles(particle)).thenReturn(
					asList(collidingParticle1, nonCollidingParticle, collidingParticle2));

			final Event event = anEventChecker.check(particle);

			final double collisionTime = TIME + timeToCollision;
			assertThat(event, is(instanceOf(CollisionEvent.class)));
			final CollisionEvent collisionEvent = (CollisionEvent) event;
			assertThat(collisionEvent.time(), is(closeTo(collisionTime, ulp(collisionTime))));
			assertThat(collisionEvent.particle(), is(particle));
			assertThat(collisionEvent.otherParticle(), is(collidingParticle2));
		}
	}
}
