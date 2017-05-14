package eu.evensson.optpartsim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("A Particle")
@RunWith(JUnitPlatform.class)
public class ParticleTest {

	@DisplayName("has an Id")
	@ParameterizedTest(name = "id={0}")
	@ValueSource(longs= {1, 12345678, Long.MAX_VALUE})
	void hasAnId(final long id) {
		final Particle particle = new Particle(id, null, null);
		assertThat(particle.id(), is(id));
	}

	@DisplayName("has a Position")
	@Test
	void hasAPosition() {
		final Vector position = new Vector(1.0, 2.0);
		final Particle particle = new Particle(1, position, null);
		assertThat(particle.position(), is(position));
	}

	@DisplayName("has a Velocity")
	@Test
	void hasAVelocity() {
		final Vector velocity = new Vector(1.0, 2.0);
		final Particle particle = new Particle(1, null, velocity);
		assertThat(particle.velocity(), is(velocity));
	}
}
