package eu.evensson.optpartsim.application;

import java.util.List;

import eu.evensson.optpartsim.physics.Particle;

public interface ParticleGenerator {

	List<Particle> generate(long numParticles,
			double boxHeight, double boxWidth, double maxInitialVelocity);

}
