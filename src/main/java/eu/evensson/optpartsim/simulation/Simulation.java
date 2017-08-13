package eu.evensson.optpartsim.simulation;

import java.util.List;

import eu.evensson.optpartsim.physics.Particle;

public interface Simulation {

	double simulate(List<Particle> list, double duration);

}
