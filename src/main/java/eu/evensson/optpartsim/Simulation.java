package eu.evensson.optpartsim;

import java.util.List;

public interface Simulation {

	double simulate(List<Particle> list, double duration);

}
