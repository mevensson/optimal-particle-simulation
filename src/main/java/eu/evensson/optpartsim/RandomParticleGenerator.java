package eu.evensson.optpartsim;

import static eu.evensson.optpartsim.Vector.polar;
import static java.lang.Math.PI;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class RandomParticleGenerator implements ParticleGenerator {

	private static final double START_TIME = 0.0;

	private final Random random;

	public RandomParticleGenerator(final Random random) {
		this.random = random;
	}

	@Override
	public List<Particle> generate(final long numParticles,
			final double maxInitialVelocity) {
		final Iterator<Double> absVelocities =
				random.doubles(numParticles, 0.0, maxInitialVelocity).iterator();
		final Iterator<Double> angles =
				random.doubles(numParticles, 0.0, PI).iterator();

		final List<Particle> particleList = new LinkedList<>();
		for (long index = 0; index < numParticles; index++) {
			particleList.add(new Particle(index + 1, START_TIME,
					null,
					polar(absVelocities.next(), angles.next())));
		}
		return particleList;
	}

}
