package eu.evensson.optpartsim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("A Particle")
@RunWith(JUnitPlatform.class)
public class ParticleTest {

	static final long ID = 12;
	static final double TIME = 1.3;
	static final Vector POSITION = new Vector(10.0, 10.0);
	static final Vector VELOCITY = new Vector(20.0, 20.0);

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
}
