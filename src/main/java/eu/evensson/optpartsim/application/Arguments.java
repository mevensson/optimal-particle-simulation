package eu.evensson.optpartsim.application;

import com.beust.jcommander.Parameter;

public class Arguments {

	@Parameter(names = {"-p" }, description = "Number of particles")
	private long particles;

	@Parameter(names = {"-h" }, description = "Height of bounding box")
	private double boxHeight = 10.0;

	@Parameter(names = {"-w" }, description = "Width of bounding box")
	private double boxWidth = 10.0;

	@Parameter(names = {"-v" }, description = "Max initial velocity")
	private double maxInitialVelocity = 1.0;

	@Parameter(names = {"-d" }, description = "Simulation duration")
	private double simulationDuration;

	public long particles() {
		return particles;
	}

	public double boxHeight() {
		return boxHeight;
	}

	public double boxWidth() {
		return boxWidth;
	}

	public double maxInitialVelocity() {
		return maxInitialVelocity;
	}

	public double simulationDuration() {
		return simulationDuration;
	}

}
