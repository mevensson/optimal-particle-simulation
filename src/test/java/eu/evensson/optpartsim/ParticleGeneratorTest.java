package eu.evensson.optpartsim;

import static eu.evensson.optpartsim.Vector.polar;
import static java.lang.Math.PI;
import static java.util.Arrays.stream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("An Argument Parser")
@RunWith(JUnitPlatform.class)
public class ParticleGeneratorTest {

	private static final double EXPECTED_START_TIME = 0.0;

	Random random = mock(Random.class);

	ParticleGenerator particleGenerator;

	@BeforeEach
	void createParticleGenerator() {
		particleGenerator = new RandomParticleGenerator(random);
	}

	@DisplayName("returns empty list when num particles is zero")
	@Test
	void returnsEmptyListWhenNumParticlesIsZero() {
		final List<Particle> particleList = particleGenerator.generate(0, 0.0);

		assertThat(particleList, is(empty()));
	}

	@DisplayName("returns list with particles")
	@ParameterizedTest
	@CsvSource({ "1, 10.0", "2, 20.0" })
	void returnsListWithParticles(final int numParticles, final double maxVelocity) {
		final double[] expectedAbsVelocities =
				new Random().doubles(numParticles, 0.0, maxVelocity).toArray();
		when(random.doubles(numParticles, 0.0, maxVelocity))
				.thenReturn(stream(expectedAbsVelocities));

		final double[] expectedAngles =
				new Random().doubles(numParticles, 0.0, PI).toArray();
		when(random.doubles(numParticles, 0.0, PI))
				.thenReturn(stream(expectedAngles));

		final List<Particle> particleList = particleGenerator.generate(numParticles, maxVelocity);

		assertThat(particleList, hasSize(numParticles));
		int expectedParticleIndex = 1;
		for (final Particle actualParticle : particleList) {
			assertThat(actualParticle, is(notNullValue()));
			final Vector position = null;
			final Vector velocity = polar(expectedAbsVelocities[expectedParticleIndex - 1],
					expectedAngles[expectedParticleIndex - 1]);
			final Particle expectedParticle =
					new Particle(expectedParticleIndex, EXPECTED_START_TIME, position, velocity);
			assertThat(actualParticle, is(expectedParticle));
			expectedParticleIndex += 1;
		}
	}
}
