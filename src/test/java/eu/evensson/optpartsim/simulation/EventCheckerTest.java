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

	private CellStructure cellStructure = mock(CellStructure.class);

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
	@Test
	void returnsCollisionEvent() {
		final double speed = 1.0;
		final Vector velocity = vector(speed, 0.0);
		final Particle particle = new Particle(ID, TIME, POSITION, velocity);

		final Vector otherPosition = POSITION.add(vector(WALLS.width() / 3.0, 0.0));
		final Vector otherVelocity = vector(-speed, 0.0);
		final Particle otherParticle = new Particle(ID + 1, TIME, otherPosition,
				otherVelocity);
		when(cellStructure.neighbourParticles(particle))
				.thenReturn(asList(otherParticle));

		final Event event = anEventChecker.check(particle);

		final double distanceBetweenParticles = otherPosition.x() - POSITION.x() - 2 * Particle.RADIUS;
		final double relativeSpeed = 2.0 * speed;
		final double timeToCollision = distanceBetweenParticles / relativeSpeed;
		final double collisionTime = TIME + timeToCollision;
		assertThat(event, is(instanceOf(CollisionEvent.class)));
		final CollisionEvent collisionEvent = (CollisionEvent) event;
		assertThat(collisionEvent.time(), is(closeTo(collisionTime, ulp(collisionTime))));
		assertThat(collisionEvent.particle(), is(particle));
		assertThat(collisionEvent.otherParticle(), is(otherParticle));
	}
}
