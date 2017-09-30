package eu.evensson.optpartsim.application;

import java.util.List;

import eu.evensson.optpartsim.physics.Box;
import eu.evensson.optpartsim.physics.Particle;

public interface ParticleGenerator {

	List<Particle> generate(long numParticles, Box box,
			double maxInitialVelocity);

}
