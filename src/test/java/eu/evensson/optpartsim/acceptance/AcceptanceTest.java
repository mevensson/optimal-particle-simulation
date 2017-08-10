package eu.evensson.optpartsim.acceptance;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import eu.evensson.optpartsim.Main;

@DisplayName("Optimal Particle Simulation")
@RunWith(JUnitPlatform.class)
public class AcceptanceTest {

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

	@DisplayName("prints help")
	@Nested
	class PrintsHelp {

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
				"      Number of parameters\n" +
				"      Default: 0\n" +
				"    -v\n" +
				"      Max initial velocity\n" +
				"      Default: 1.0\n" +
				"    -w\n" +
				"      Width of bounding box\n" +
				"      Default: 10.0\n" +
				"\n";

		@DisplayName("on '--help'")
		@Test
		void printsHelpLongOption() {
			final String[] args = new String[] { "--help" };

			Main.main(args);

			assertThat(systemOut.toString(), is(HELP_MESSAGE));
		}
	}

	private static final String SIMULATION_RESULT_FORMAT =
			"Total momentum is %g.\n";

	@DisplayName("no arguments")
	@Nested
	class NoArguments {

		@DisplayName("prints 0 momentum")
		@Test
		void prints0Momentum() {
			final String[] args = new String[0];

			Main.main(args);

			assertThat(systemOut.toString(),
					is(String.format(SIMULATION_RESULT_FORMAT, 0.0)));
		}
	}

	@DisplayName("no particles")
	@Nested
	class NoParticles {

		@DisplayName("prints 0 momentum")
		@Test
		void prints0Momentum() {
			final String[] args = new String[] { "-p", "0" };

			Main.main(args);

			assertThat(systemOut.toString(),
					is(String.format(SIMULATION_RESULT_FORMAT, 0.0)));
		}
	}

	@DisplayName("one particle")
	@Nested
	class OneParticle {

		@DisplayName("no bounce")
		@Nested
		class NoBounce {

			@DisplayName("prints 0 momentum on deafult time argument")
			@Test
			void prints0MomentumOnDefaultTimeArguemnt() {
				final String[] args = new String[] { "-p", "1" };

				Main.main(args);

				assertThat(systemOut.toString(),
						is(String.format(SIMULATION_RESULT_FORMAT, 0.0)));
			}

			@DisplayName("prints 0 momentum on 0.0 duration argument")
			@Test
			void prints0MomentumOnZeroDurationArguemnt() {
				final String[] args = new String[] { "-p", "1", "-d", "0.0" };

				Main.main(args);

				assertThat(systemOut.toString(),
						is(String.format(SIMULATION_RESULT_FORMAT, 0.0)));
			}
		}

		@DisplayName("prints particle momentum on one bounce")
		@Test
		void prints0MomentumOnZeroMaxInitialVelocity() {
			final double mass = 1.0;
			final double velocity = 3.0;
			final double width = 10.0;
			final double bounceTime = (width / 2.0) / velocity;
			final String[] args = new String[] {
					"-p", "1",
					"-d", Double.toString(bounceTime),
					"-v", Double.toString(velocity),
					"-w", Double.toString(width)
			};

			// TODO: Mock generated particle to always have max velocity

			Main.main(args);

			final double momentum = mass * velocity;
			assertThat(systemOut.toString(),
					is(String.format(SIMULATION_RESULT_FORMAT, momentum)));
		}
	}
}
