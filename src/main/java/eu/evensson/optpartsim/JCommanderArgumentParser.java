package eu.evensson.optpartsim;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class JCommanderArgumentParser implements ArgumentParser {

	private final Printer printer;

	@Parameter(names = {"-h", "--help" }, description="Display this help and exit", help = true)
	private boolean help;

	public JCommanderArgumentParser(final Printer printer) {
		this.printer = printer;
	}

	@Override
	public Arguments parse(final String[] args) {
		final JCommander jCommander = JCommander.newBuilder()
			.programName("optimal-particle-simulation")
			.addObject(this)
			.build();
		jCommander.parse(args);

		if (help) {
			final StringBuilder usageString = new StringBuilder();
			jCommander.usage(usageString);
			printer.print(usageString.toString());
		}

		return null;
	}

}
