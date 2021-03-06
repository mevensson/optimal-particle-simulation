package eu.evensson.optpartsim.application;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("An Argument Parser")
public class ArgumentParserTest {

	private static final String HELP_MESSAGE =
			"Usage: optimal-particle-simulation [options]\n" +
			"  Options:\n" +
			"    --help\n" +
			"      Display this help and exit\n" +
			"    -d\n" +
			"      Simulation duration\n" +
			"      Default: 0.0\n" +
			"    -h\n" +
			"      Height of bounding box\n" +
			"      Default: 10.0\n" +
			"    -p\n" +
			"      Number of particles\n" +
			"      Default: 0\n" +
			"    -v\n" +
			"      Max initial velocity\n" +
			"      Default: 1.0\n" +
			"    -w\n" +
			"      Width of bounding box\n" +
			"      Default: 10.0\n";

	Printer printer = mock(Printer.class);

	ArgumentParser argumentParser;

	@BeforeEach
	void createArgumentParser() {
		argumentParser = new JCommanderArgumentParser(printer);
	}

	@DisplayName("prints help on --help")
	@Test
	void printsHelp() {
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

	@DisplayName("parses simulation duration")
	@ParameterizedTest
	@ValueSource(doubles = { -Double.MAX_VALUE, -Double.MIN_VALUE, 0.0,
			Double.MIN_VALUE, Double.MAX_VALUE })
	void parsesSimulationTime(final double simulationDuration) {
		final String[] args = new String[] {
				"-d",
				Double.toString(simulationDuration)
		};

		final Arguments arguments = argumentParser.parse(args);

		assertThat(arguments.simulationDuration(), is(simulationDuration));
	}

	@DisplayName("parses max initial velocity")
	@ParameterizedTest
	@ValueSource(doubles = { -Double.MAX_VALUE, -Double.MIN_VALUE, 0.0,
			Double.MIN_VALUE, Double.MAX_VALUE })
	void parsesMaxInitialVelocity(final double maxInitialVelocity) {
		final String[] args = new String[] {
				"-v",
				Double.toString(maxInitialVelocity)
		};

		final Arguments arguments = argumentParser.parse(args);

		assertThat(arguments.maxInitialVelocity(), is(maxInitialVelocity));
	}

	@DisplayName("has default max initial velocity greater than zero")
	@Test
	void hasDefaultMaxInitialVelocityGreaterThanZero() {
		final String[] args = new String[0];

		final Arguments arguments = argumentParser.parse(args);

		assertThat(arguments.maxInitialVelocity(), is(greaterThan(0.0)));
	}

	@DisplayName("parses box height")
	@ParameterizedTest
	@ValueSource(doubles = { -Double.MAX_VALUE, -Double.MIN_VALUE, 0.0,
			Double.MIN_VALUE, Double.MAX_VALUE })
	void parsesBoxHeight(final double boxHeight) {
		final String[] args = new String[] {
				"-h",
				Double.toString(boxHeight)
		};

		final Arguments arguments = argumentParser.parse(args);

		assertThat(arguments.boxHeight(), is(boxHeight));
	}

	@DisplayName("has default box height greater than zero")
	@Test
	void hasDefaultBoxHeightGreaterThanZero() {
		final String[] args = new String[0];

		final Arguments arguments = argumentParser.parse(args);

		assertThat(arguments.boxHeight(), is(greaterThan(0.0)));
	}

	@DisplayName("parses box width")
	@ParameterizedTest
	@ValueSource(doubles = { -Double.MAX_VALUE, -Double.MIN_VALUE, 0.0,
			Double.MIN_VALUE, Double.MAX_VALUE })
	void parsesBoxWidth(final double boxWidth) {
		final String[] args = new String[] {
				"-w",
				Double.toString(boxWidth)
		};

		final Arguments arguments = argumentParser.parse(args);

		assertThat(arguments.boxWidth(), is(boxWidth));
	}

	@DisplayName("has default box width greater than zero")
	@Test
	void hasDefaultBoxWidthGreaterThanZero() {
		final String[] args = new String[0];

		final Arguments arguments = argumentParser.parse(args);

		assertThat(arguments.boxWidth(), is(greaterThan(0.0)));
	}
}
