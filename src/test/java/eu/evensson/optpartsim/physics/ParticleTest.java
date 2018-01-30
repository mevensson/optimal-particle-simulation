package eu.evensson.optpartsim.physics;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAnd;
import static eu.evensson.optpartsim.physics.Vector.vector;
import static java.lang.Math.abs;
import static java.lang.Math.ulp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSources;
import org.junit.jupiter.params.provider.CsvSource;

import eu.evensson.optpartsim.physics.Particle.Direction;

@DisplayName("A Particle")
public class ParticleTest {

	static final long ID = 12;
	static final double TIME = 1.3;
	static final Vector POSITION = vector(10.0, 20.0);
	static final Vector VELOCITY = vector(30.0, 40.0);

	Particle aParticle;

	@BeforeEach
	void createParticle() {
		aParticle = new Particle(ID, TIME, POSITION, VELOCITY);
	}

	@DisplayName("has an Id")
	@Test
	void hasAnId() {
		assertThat(aParticle.id(), is(ID));
	}

	@DisplayName("has a Time")
	@Test
	void hasATime() {
		assertThat(aParticle.time(), is(TIME));
	}

	@DisplayName("has a Position")
	@Test
	void hasAPosition() {
		assertThat(aParticle.position(), is(POSITION));
	}

	@DisplayName("has a Velocity")
	@Test
	void hasAVelocity() {
		assertThat(aParticle.velocity(), is(VELOCITY));
	}

	@DisplayName("has a horizontal Momentum")
	@Test
	void hasAHorizontalMomentum() {
		final double momentum = abs(VELOCITY.x()) * Particle.MASS;
		assertThat(aParticle.momentum(Direction.HORIZONTAL), is(momentum));
	}

	@DisplayName("has a vertical Momentum")
	@Test
	void hasAVerticalMomentum() {
		final double momentum = abs(VELOCITY.y()) * Particle.MASS;
		assertThat(aParticle.momentum(Direction.VERTICAL), is(momentum));
	}

	@DisplayName("when moved")
	@Nested
	class WhenMoved {

		static final double NEW_TIME = 7.3;

		Particle movedParticle;

		@BeforeEach
		void moveParticle() {
			movedParticle = aParticle.move(NEW_TIME);
		}

		@DisplayName("is not modified")
		@Test
		void isNotModified() {
			assertThat(aParticle,
					is(new Particle(ID, TIME, POSITION, VELOCITY)));
		}

		@DisplayName("returns a Particle with")
		@Nested
		class ReturnsAParticleWith {

			@DisplayName("the same id")
			@Test
			void hasSameId() {
				assertThat(movedParticle.id(), is(ID));
			}

			@DisplayName("the new time")
			@Test
			void hasTheNewTime() {
				assertThat(movedParticle.time(), is(NEW_TIME));
			}

			@DisplayName("a position that is the sum old position "
					+ "and the velocity multiplied with the time difference")
			@Test
			void hasTheNewPosition() {
				assertThat(movedParticle.position(),
						is(POSITION.add(VELOCITY.multiply(NEW_TIME - TIME))));
			}

			@DisplayName("the same velocity")
			@Test
			void hasSameVelocity() {
				assertThat(movedParticle.velocity(), is(VELOCITY));
			}
		}
	}

	@DisplayName("when horizontally bounced")
	@Nested
	class WhenHorizontallyBounced {

		Particle bouncedParticle;

		@BeforeEach
		void bounceParticle() {
			bouncedParticle = aParticle.bounce(Particle.Direction.HORIZONTAL);
		}

		@DisplayName("is not modified")
		@Test
		void isNotModified() {
			assertThat(aParticle,
					is(new Particle(ID, TIME, POSITION, VELOCITY)));
		}

		@DisplayName("returns a Particle with")
		@Nested
		class ReturnsAParticleWith {

			@DisplayName("the same id")
			@Test
			void hasSameId() {
				assertThat(bouncedParticle.id(), is(ID));
			}

			@DisplayName("the same time")
			@Test
			void hasTheNewTime() {
				assertThat(bouncedParticle.time(), is(TIME));
			}

			@DisplayName("the same position")
			@Test
			void hasTheNewPosition() {
				assertThat(bouncedParticle.position(), is(POSITION));
			}

			@DisplayName("the horizontal part of the velocity is reversed")
			@Test
			void hasSameVelocity() {
				assertThat(bouncedParticle.velocity(), is(
						vector(-VELOCITY.x(), VELOCITY.y())));
			}
		}

	}

	@DisplayName("when vertically bounced")
	@Nested
	class WhenVerticallyBounced {

		Particle bouncedParticle;

		@BeforeEach
		void bounceParticle() {
			bouncedParticle = aParticle.bounce(Particle.Direction.VERTICAL);
		}

		@DisplayName("is not modified")
		@Test
		void isNotModified() {
			assertThat(aParticle,
					is(new Particle(ID, TIME, POSITION, VELOCITY)));
		}

		@DisplayName("returns a Particle with")
		@Nested
		class ReturnsAParticleWith {

			@DisplayName("the same id")
			@Test
			void hasSameId() {
				assertThat(bouncedParticle.id(), is(ID));
			}

			@DisplayName("the same time")
			@Test
			void hasTheNewTime() {
				assertThat(bouncedParticle.time(), is(TIME));
			}

			@DisplayName("the same position")
			@Test
			void hasTheNewPosition() {
				assertThat(bouncedParticle.position(), is(POSITION));
			}

			@DisplayName("the vertical part of the velocity is reversed")
			@Test
			void hasSameVelocity() {
				assertThat(bouncedParticle.velocity(), is(
						vector(VELOCITY.x(), -VELOCITY.y())));
			}
		}

	}

	@DisplayName("returns collision time when it collides with a box")
	@Nested
	class IntersectsABox {
		static final double SPEED = 2.0;
		final Box BOX = new Box(5.0, 10.0, 20.0, 30.0);

		@DisplayName("to the left")
		@Test
		void toTheLeft() {
			final Vector velocity = vector(-SPEED, SPEED / 2);
			final Particle particle = new Particle(ID, TIME, POSITION, velocity);
			final double distance = POSITION.x() - BOX.x() - Particle.RADIUS;
			final double intersectionTime = TIME + distance / SPEED;
			assertThat(particle.collisionTime(BOX, Direction.HORIZONTAL),
					is(intersectionTime));
		}

		@DisplayName("to the right")
		@Test
		void toTheRight() {
			final Vector velocity = vector(SPEED, SPEED / 2);
			final Particle particle = new Particle(ID, TIME, POSITION, velocity);
			final double distance = BOX.x() + BOX.width() - POSITION.x() - Particle.RADIUS;
			final double intersectionTime = TIME + distance / SPEED;
			assertThat(particle.collisionTime(BOX, Direction.HORIZONTAL),
					is(intersectionTime));
		}

		@DisplayName("to the top")
		@Test
		void toTheTop() {
			final Vector velocity = vector(SPEED / 2, -SPEED);
			final Particle particle = new Particle(ID, TIME, POSITION, velocity);
			final double distance = POSITION.y() - BOX.y() -Particle.RADIUS;
			final double intersectionTime = TIME + distance / SPEED;
			assertThat(particle.collisionTime(BOX, Direction.VERTICAL),
					is(intersectionTime));
		}

		@DisplayName("to the bottom")
		@Test
		void toTheBottom() {
			final Vector velocity = vector(SPEED / 2, SPEED);
			final Particle particle = new Particle(ID, TIME, POSITION, velocity);
			final double distance = BOX.y() + BOX.height() - POSITION.y() - Particle.RADIUS;
			final double intersectionTime = TIME + distance / SPEED;
			assertThat(particle.collisionTime(BOX, Direction.VERTICAL),
					is(intersectionTime));
		}
	}

	@DisplayName("collides with other particle")
	@Nested
	class CollidesWithOtherParticle {
		@DisplayName("when other particle is head on on x-axis")
		@ParameterizedTest
		@CollisionSources({
			@CollisionSource(time = 3.0, p1Time = 0.0, p1Ang = 0.0, p2Time = 0.0, p2Ang = Math.PI), // Zero time
			@CollisionSource(time = 4.3, p1Time = 1.3, p1Ang = 0.0, p2Time = 1.3, p2Ang = Math.PI), // Same time
			@CollisionSource(time = 3.0, p1Time = 0.0, p1Ang = 0.0, p2Time = 1.0, p2Ang = Math.PI), // Different times
			@CollisionSource(time = 3.0, p1Time = 1.0, p1Ang = Math.PI, p2Time = 0.0, p2Ang = 0.0), // Reversed
			@CollisionSource(time = 3.0, p1Time = 3.0, p1Ang = 0.0, p2Time = 0.0, p2Ang = Math.PI), // Particle 1 at collision time
			@CollisionSource(time = 3.0, p1Time = 0.0, p1Ang = 0.0, p2Time = 3.0, p2Ang = Math.PI), // Particle 2 at collision time
			@CollisionSource(time = 3.0, p1Time = 3.0, p1Ang = 0.0, p2Time = 3.0, p2Ang = Math.PI), // Touching
		})
		void headOnXAxis(final double expectedCollisionTime, final Particle p1, final Particle p2) {
			final Optional<Double> collisionTime = p1.collisionTime(p2);

			assertThat(collisionTime,
					isPresentAnd(closeTo(expectedCollisionTime,
							Math.ulp(expectedCollisionTime))));
		}

		@DisplayName("when other particle is head on on y-axis")
		@ParameterizedTest
		@CollisionSources({
			@CollisionSource(time = 3.0, p1Time = 0.0, p1Ang = Math.PI / 2, p2Time = 0.0, p2Ang = 3 * Math.PI / 2), // Zero time
			@CollisionSource(time = 4.3, p1Time = 1.3, p1Ang = Math.PI / 2, p2Time = 1.3, p2Ang = 3 * Math.PI / 2), // Same time
			@CollisionSource(time = 3.0, p1Time = 0.0, p1Ang = Math.PI / 2, p2Time = 1.0, p2Ang = 3 * Math.PI / 2), // Different times
			@CollisionSource(time = 3.0, p1Time = 1.0, p1Ang = 3 * Math.PI / 2, p2Time = 0.0, p2Ang = Math.PI / 2), // Reversed
		})
		void headOnYAxis(final double expectedCollisionTime, final Particle p1, final Particle p2) {
			final Optional<Double> collisionTime = p1.collisionTime(p2);

			assertThat(collisionTime,
					isPresentAnd(closeTo(expectedCollisionTime,
							Math.ulp(expectedCollisionTime))));
		}

		@DisplayName("when top and bottom is barely touching")
		@ParameterizedTest
		@CsvSource({
			"0.0, 6.0, 1.0, 0.0, 14.0, -1.0, 4.0", // Zero time
			"1.3, 6.0, 1.0, 1.3, 14.0, -1.0, 5.3", // Same time
			"0.0, 6.0, 1.0, 1.0, 13.0, -1.0, 4.0", // Different times
			"1.0, 13.0, -1.0, 0.0, 6.0, 1.0, 4.0", // Reversed
			})
		void topAndBottomTouching(
				final double t1, final double y1, final double ys1,
				final double t2, final double y2, final double ys2,
				final double expectedCollisionTime) {
			final Particle p1 = new Particle(ID, t1, vector(0.0, y1), vector(0.0, ys1));
			final double offset = 2.0 * Particle.RADIUS;
			final Particle p2 = new Particle(ID, t2, vector(offset, y2), vector(0.0, ys2));

			final Optional<Double> collisionTime = p1.collisionTime(p2);

			assertThat(collisionTime,
					isPresentAnd(closeTo(expectedCollisionTime,
							Math.ulp(expectedCollisionTime))));
		}
	}


	@DisplayName("does not collide with other particle")
	@Nested
	class DoesNotCollideWithOtherParticle {
		@DisplayName("when other particle is in opposite direction on x-axis")
		@ParameterizedTest
		@CsvSource({
				"0.0, 6.0, -1.0, 0.0, 14.0, 1.0",  // Zero time
				"9.0, 6.0, -1.0, 9.0, 14.0, 1.0",  // Same time
				"9.0, 15.0, -1.0, 0.0, 14.0, 1.0", // Particle past collision
				"0.0, 6.0, -1.0, 9.0, 5.0, 1.0",   // Other past collision
				})
		void oppositeDirectionOnXAxis(
				final double t1, final double x1, final double xs1,
				final double t2, final double x2, final double xs2) {
			final Particle p1 = new Particle(ID, t1, vector(x1, 0.0), vector(xs1, 0.0));
			final Particle p2 = new Particle(ID, t2, vector(x2, 0.0), vector(xs2, 0.0));

			final Optional<Double> collisionTime = p1.collisionTime(p2);

			assertThat(collisionTime, isEmpty());
		}

		@DisplayName("when other particle is in opposite direction on y-axis")
		@ParameterizedTest
		@CsvSource({
			"0.0, 6.0, -1.0, 0.0, 14.0, 1.0", // Zero time
			"9.0, 6.0, -1.0, 9.0, 14.0, 1.0", // Same time
			"9.0, 9.0, -1.0, 9.0, 11.0, 1.0", // Touching
		})
		void oppositeDirectionOnYAxis(
				final double t1, final double y1, final double ys1,
				final double t2, final double y2, final double ys2) {
			final Particle p1 = new Particle(ID, t1, vector(0.0, y1), vector(0.0, ys1));
			final Particle p2 = new Particle(ID, t2, vector(0.0, y2), vector(0.0, ys2));

			final Optional<Double> collisionTime = p1.collisionTime(p2);

			assertThat(collisionTime, isEmpty());
		}

		@DisplayName("when top and bottom is barely missing")
		@ParameterizedTest
		@CsvSource({
			"0.0, 6.0, 1.0, 0.0, 14.0, -1.0", // Zero time
			})
		void topAndBottomBarelyMissing(
				final double t1, final double y1, final double ys1,
				final double t2, final double y2, final double ys2) {
			final Particle p1 = new Particle(ID, t1, vector(0.0, y1), vector(0.0, ys1));
			final double hitOffset = 2.0 * Particle.RADIUS;
			final double missOffset = hitOffset + 5.0 * ulp(hitOffset);
			final Particle p2 = new Particle(ID, t2, vector(missOffset, y2), vector(0.0, ys2));

			final Optional<Double> collisionTime = p1.collisionTime(p2);

			assertThat(collisionTime, isEmpty());
		}

		@DisplayName("when parallel and touching")
		@ParameterizedTest
		@CsvSource({
			"0.0, 10.0, 1.0, 0.0, 10.0, 1.0", // Zero time
		})
		void parallelAndTouching(
				final double t1, final double y1, final double ys1,
				final double t2, final double y2, final double ys2) {
			final Particle p1 = new Particle(ID, t1, vector(0.0, y1), vector(0.0, ys1));
			final double hitOffset = 2.0 * Particle.RADIUS;
			final Particle p2 = new Particle(ID, t2, vector(hitOffset, y2), vector(0.0, ys2));

			final Optional<Double> collisionTime = p1.collisionTime(p2);

			assertThat(collisionTime, isEmpty());
		}

		@DisplayName("when other particle is itself")
		@Test
		void withItself() {
			final Optional<Double> collisionTime = aParticle.collisionTime(aParticle);

			assertThat(collisionTime, isEmpty());
		}
	}

	@DisplayName("when collided with other particle")
	@Nested
	class WhenCollidedWithOtherParticle {

		@DisplayName("is not modified")
		@ParameterizedTest
		@CollisionSources(@CollisionSource(time = TIME))
		void isNotModified(final double t, final Particle particle, final Particle otherParticle) {
			final long id = particle.id();
			final double time = particle.time();
			final double px = particle.position().x();
			final double py = particle.position().y();
			final double vx = particle.velocity().x();
			final double vy = particle.velocity().y();

			particle.collide(otherParticle);

			assertThat(particle,
					is(new Particle(id, time, vector(px, py), vector(vx, vy))));
		}

		@DisplayName("the other particle is not modified")
		@ParameterizedTest
		@CollisionSources(@CollisionSource(time = TIME))
		void otherParticleIsNotModified(final double t, final Particle particle, final Particle otherParticle) {
			final long id = otherParticle.id();
			final double time = otherParticle.time();
			final double px = otherParticle.position().x();
			final double py = otherParticle.position().y();
			final double vx = otherParticle.velocity().x();
			final double vy = otherParticle.velocity().y();

			particle.collide(otherParticle);

			assertThat(otherParticle,
					is(new Particle(id, time, vector(px, py), vector(vx, vy))));
		}

		@DisplayName("returns a Particle with")
		@Nested
		class ReturnsAParticleWith {

			@DisplayName("the same id")
			@ParameterizedTest
			@CollisionSources(@CollisionSource(time = TIME))
			void hasSameId(final double time, final Particle particle, final Particle otherParticle) {
				final Particle newParticle = particle.collide(otherParticle);

				assertThat(newParticle.id(), is(particle.id()));
			}

			@DisplayName("the time of the collision")
			@ParameterizedTest
			@CollisionSources(@CollisionSource(time = 1.0, p1Time = 0.0))
			void hasTheTimeOfTheCollision(final double time, final Particle particle, final Particle otherParticle) {
				final Particle newParticle = particle.collide(otherParticle);

				assertThat(newParticle.time(), is(time));
			}

			@DisplayName("the position of the collision")
			@ParameterizedTest
			@CollisionSources(@CollisionSource(time = 1.0, p1Time = 0.0))
			void hasThePositionOfTheCollision(final double time, final Particle particle, final Particle otherParticle) {
				final Particle newParticle = particle.collide(otherParticle);

				assertThat(newParticle.position(), is(particle.move(time).position()));
			}

			@DisplayName("a new velocity")
			@ParameterizedTest
			@CollisionSources(@CollisionSource(time = 1.0, p1Time = 0.0))
			void hasANewVelocity(final double time, final Particle particle, final Particle otherParticle) {
				final Particle newParticle = particle.collide(otherParticle);

				assertThat(newParticle.velocity(), is(not(particle.velocity())));
			}
		}

		@DisplayName("the sum of the horizontal momentum is unchanged")
		@ParameterizedTest
		@CollisionSources(@CollisionSource(time = 1.0, p1Time = 0.0))
		void theSumOfTheHorizontalMomentumIsUnchanged(final double time, final Particle particle, final Particle otherParticle) {
			final Particle newParticle = particle.collide(otherParticle);
			final Particle newOtherParticle = otherParticle.collide(particle);

			final double newMomentum = newParticle.momentum(Direction.HORIZONTAL) + newOtherParticle.momentum(Direction.HORIZONTAL);
			final double expectedMomentum = particle.momentum(Direction.HORIZONTAL) + otherParticle.momentum(Direction.HORIZONTAL);
			assertThat(newMomentum, is(closeTo(expectedMomentum, ulp(expectedMomentum))));
		}

		@DisplayName("the sum of the vertical momentum is unchanged")
		@ParameterizedTest
		@CollisionSources(@CollisionSource(time = 1.0, p1Time = 0.0))
		void theSumOfTheVerticalMomentumIsUnchanged(final double time, final Particle particle, final Particle otherParticle) {
			final Particle newParticle = particle.collide(otherParticle);
			final Particle newOtherParticle = otherParticle.collide(particle);

			final double newMomentum = newParticle.momentum(Direction.VERTICAL) + newOtherParticle.momentum(Direction.VERTICAL);
			final double expectedMomentum = particle.momentum(Direction.VERTICAL) + otherParticle.momentum(Direction.VERTICAL);
			assertThat(newMomentum, is(closeTo(expectedMomentum, ulp(expectedMomentum))));
		}

		@DisplayName("the sum of the energy is unchanged")
		@ParameterizedTest
		@CollisionSources(@CollisionSource(time = 1.0, p1Time = 0.0))
		void theSumOfTheEnergyIsUnchanged(final double time, final Particle particle, final Particle otherParticle) {
			final Particle newParticle = particle.collide(otherParticle);
			final Particle newOtherParticle = otherParticle.collide(particle);

			final double newEnergy = energy(newParticle) + energy(newOtherParticle);
			final double expectedEnergy = energy(particle) + energy(otherParticle);
			assertThat(newEnergy, is(closeTo(expectedEnergy, ulp(expectedEnergy))));
		}

		double energy(final Particle particle) {
			return Particle.MASS * particle.velocity().norm();
		}
	}
}
