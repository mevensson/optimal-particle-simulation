package eu.evensson.optpartsim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("An Argument Parser")
@RunWith(JUnitPlatform.class)
public class ArgumentParserTest {

	private static final String HELP_MESSAGE =
			"Usage: optimal-particle-simulation [options]\n" +
			"  Options:\n" +
			"    -h, --help\n" +
			"      Display this help and exit\n" +
			"    -p\n" +
			"      Number of parameters\n" +
			"      Default: 0\n";

	Printer printer = mock(Printer.class);

	ArgumentParser argumentParser;

	@BeforeEach
	void createArgumentParser() {
		argumentParser = new JCommanderArgumentParser(printer);
	}

	@DisplayName("prints help on -h")
	@Test
	void printsHelpShortOption() {
		argumentParser.parse(new String[]{ "-h" });

		verify(printer).print(HELP_MESSAGE);
	}

	@DisplayName("prints help on --help")
	@Test
	void printsHelpLongOption() {
		argumentParser.parse(new String[]{ "--help" });

		verify(printer).print(HELP_MESSAGE);
	}

	@DisplayName("parses number of particles")
	@ParameterizedTest
	@ValueSource(longs = { 0, 1, 10, Long.MAX_VALUE - 1, Long.MAX_VALUE })
	void parsesNumberOfParticles(final long numParticles) {
		final String[] args = new String[] {
				"-p",
				Long.toString(numParticles)
		};

		final Arguments arguments = argumentParser.parse(args);

		assertThat(arguments.particles(), is(numParticles));
	}
}
