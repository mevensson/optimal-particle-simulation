package eu.evensson.optpartsim;

import java.util.List;

public class Simulation {

	private final CellStructure cellStructure;

	public Simulation(final CellStructure cellStructure) {
		this.cellStructure = cellStructure;
	}

	public double simulate(final List<Particle> particles) {
		for (final Particle particle : particles) {
			cellStructure.insert(particle);
		}

		return 0.0;
	}

}
