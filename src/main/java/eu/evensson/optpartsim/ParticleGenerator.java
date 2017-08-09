package eu.evensson.optpartsim;

import java.util.List;

public interface ParticleGenerator {

	List<Particle> generate(long numParticles, double maxInitialVelocity);

}
