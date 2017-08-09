package eu.evensson.optpartsim;

import com.beust.jcommander.Parameter;

public class Arguments {

	@Parameter(names = {"-p" }, description = "Number of parameters")
	private long particles;

	@Parameter(names = {"-d" }, description = "Simulation duration")
	private double simulationDuration;

	public long particles() {
		return particles;
	}

	public double simulationDuration() {
		return simulationDuration;
	}

}
