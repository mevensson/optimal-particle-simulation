package eu.evensson.optpartsim;

import com.beust.jcommander.Parameter;

public class Arguments {

	@Parameter(names = {"-p" }, description = "Number of parameters")
	private long particles;

	public long particles() {
		return particles;
	}

}
