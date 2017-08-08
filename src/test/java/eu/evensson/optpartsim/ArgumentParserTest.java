package eu.evensson.optpartsim;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("An Argument Parser")
@RunWith(JUnitPlatform.class)
public class ArgumentParserTest {

	private static final String HELP_MESSAGE =
			"Usage: optimal-particle-simulation [options]\n" +
			"  Options:\n" +
			"    -h\n" +
			"      Display this help and exit\n";

	Printer printer = mock(Printer.class);

	ArgumentParser argumentParser;

	@BeforeEach
	void createArgumentParser() {
		argumentParser = new JCommanderArgumentParser(printer);
	}

	@DisplayName("prints help on -h")
	@Test
	void printsHelp() {
		argumentParser.parse(new String[]{ "-h" });

		verify(printer).print(HELP_MESSAGE);
	}
}
