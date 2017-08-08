package eu.evensson.optpartsim.acceptance;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import eu.evensson.optpartsim.Main;

@DisplayName("Optimal Particle Simulation")
@RunWith(JUnitPlatform.class)
public class AcceptanceTest {

	private static final String HELP_MESSAGE =
			"Usage: optimal-particle-simulation [options]\n" +
			"  Options:\n" +
			"    -h\n" +
			"      Display this help and exit\n\n";

	private final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();

	private PrintStream oldSystemOut;

	@BeforeEach
	public void stubSystemOut() {
		oldSystemOut = System.out;
		System.setOut(new PrintStream(systemOut));
	}

	@AfterEach
	public void unStubSystemOut() {
		System.setOut(oldSystemOut);
	}

	@DisplayName("prints help on '-h'")
	@Test
	void printsHelp() {
		final String[] args = new String[] {
				"-h"
		};

		Main.main(args);

		assertThat(systemOut.toString(), is(HELP_MESSAGE));
	}
}
