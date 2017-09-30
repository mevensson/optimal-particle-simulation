package eu.evensson.optpartsim.acceptance;

import static java.lang.Math.PI;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.stream.DoubleStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import eu.evensson.optpartsim.Main;
import eu.evensson.optpartsim.application.ApplicationInjector;

@DisplayName("Optimal Particle Simulation")
public class AcceptanceTest {

	private static final double RIGHT = 0.0;
	private static final double DOWN = PI / 2.0;
	private static final double LEFT = PI;
	private static final double UP = 3.0 * PI / 2.0;
	private static final double WHOLE_CIRCLE = 2.0 * PI;

	private final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
	private final Random random = mock(Random.class);

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

	@BeforeEach
	public void stubRandom() {
		when(random.doubles(anyLong(), anyDouble(), anyDouble()))
				.thenCallRealMethod();

		ApplicationInjector.setRandom(random);
	}

	@AfterEach
	public void unStubRandom() {
		ApplicationInjector.setRandom(null);
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

		@DisplayName("prints particle momentum on one horizontal bounce")
		@Test
		void printsParticleMomentumOnOneHorizontalBounce() {
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

			stubRandom(1, velocity, velocity);
			stubRandom(1, WHOLE_CIRCLE, LEFT);
			stubRandom(1, width, width / 2.0);

			Main.main(args);

			final double expectedMomentum = mass * velocity;
			assertThat(systemOut.toString(),
					is(String.format(SIMULATION_RESULT_FORMAT, expectedMomentum)));
		}

		@DisplayName("prints particle 2*momentum on two horizontal bounces")
		@Test
		void printsParticle2xMomentumOnTwoHorizontalBounces() {
			final double mass = 1.0;
			final double velocity = 3.0;
			final double width = 10.0;
			final double bounceTime = (width / 2.0) / velocity;
			final double secondBounceTime = bounceTime + width / velocity;
			final String[] args = new String[] {
					"-p", "1",
					"-d", Double.toString(secondBounceTime),
					"-v", Double.toString(velocity),
					"-w", Double.toString(width)
			};

			stubRandom(1, velocity, velocity);
			stubRandom(1, WHOLE_CIRCLE, LEFT);
			stubRandom(1, width, width / 2.0);

			Main.main(args);

			final double expectedMomentum = 2.0 * mass * velocity;
			assertThat(systemOut.toString(),
					is(String.format(SIMULATION_RESULT_FORMAT, expectedMomentum)));
		}

		@DisplayName("prints particle momentum on one vertical bounce")
		@Test
		void printsParticleMomentumOnOneVerticalBounce() {
			final double mass = 1.0;
			final double velocity = 3.0;
			final double height = 10.0;
			final double bounceTime = (height / 2.0) / velocity;
			final String[] args = new String[] {
					"-p", "1",
					"-d", Double.toString(bounceTime),
					"-v", Double.toString(velocity),
					"-h", Double.toString(height)
			};

			stubRandom(1, velocity, velocity);
			stubRandom(1, WHOLE_CIRCLE, UP);
			stubRandom(1, height, height / 2.0);

			Main.main(args);

			final double expectedMomentum = mass * velocity;
			assertThat(systemOut.toString(),
					is(String.format(SIMULATION_RESULT_FORMAT, expectedMomentum)));
		}

		private void stubRandom(final long values, final double maxValue, final double ... results) {
			when(random.doubles(1, 0.0, maxValue))
					.thenAnswer(invocation -> DoubleStream.of(results));
		}
	}
}
