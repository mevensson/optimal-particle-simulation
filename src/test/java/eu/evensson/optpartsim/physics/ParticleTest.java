package eu.evensson.optpartsim.physics;

import static eu.evensson.optpartsim.physics.Vector.vector;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("A Particle")
public class ParticleTest {

	static final long ID = 12;
	static final double TIME = 1.3;
	static final Vector POSITION = vector(10.0, 10.0);
	static final Vector VELOCITY = vector(20.0, 20.0);

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

	@DisplayName("returns intersection time when it intersects a box")
	@Nested
	class IntersectsABox {
		static final double SPEED = 2.0;
		final Box BOX = new Box(5.0, 5.0, 10.0, 10.0);

		@DisplayName("to the left")
		@Test
		void toTheLeft() {
			final Vector velocity = vector(-SPEED, SPEED / 2);
			final Particle particle = new Particle(ID, TIME, POSITION, velocity);
			final double intersectionTime = TIME + (POSITION.x() - BOX.x()) / SPEED;
			assertThat(particle.intersects(BOX), is(intersectionTime));
		}

		@DisplayName("to the right")
		@Test
		void toTheRight() {
			final Vector velocity = vector(SPEED, SPEED / 2);
			final Particle particle = new Particle(ID, TIME, POSITION, velocity);
			final double intersectionTime = TIME + (POSITION.x() - BOX.x()) / SPEED;
			assertThat(particle.intersects(BOX), is(intersectionTime));
		}

		@DisplayName("to the top")
		@Test
		void toTheTop() {
			final Vector velocity = vector(SPEED / 2, -SPEED);
			final Particle particle = new Particle(ID, TIME, POSITION, velocity);
			final double intersectionTime = TIME + (POSITION.x() - BOX.x()) / SPEED;
			assertThat(particle.intersects(BOX), is(intersectionTime));
		}

		@DisplayName("to the bottom")
		@Test
		void toTheBottom() {
			final Vector velocity = vector(SPEED / 2, SPEED);
			final Particle particle = new Particle(ID, TIME, POSITION, velocity);
			final double intersectionTime = TIME + (POSITION.x() - BOX.x()) / SPEED;
			assertThat(particle.intersects(BOX), is(intersectionTime));
		}
	}
}