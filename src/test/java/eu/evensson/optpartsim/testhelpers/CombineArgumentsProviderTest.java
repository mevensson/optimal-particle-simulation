package eu.evensson.optpartsim.testhelpers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import org.junit.jupiter.params.ParameterizedTest;

public class CombineArgumentsProviderTest {

	@ParameterizedTest(name = "x={0}, y= {1}")
	@CombineSource(sources= {
			@LongRangeSource(min=1, max=5),
			@LongRangeSource(min=10, max=15)
	})
	void exampleTest(final long x, final long y) {
		assertThat(x, is(greaterThanOrEqualTo(1l)));
		assertThat(x, is(lessThanOrEqualTo(5l)));

		assertThat(y, is(greaterThanOrEqualTo(10l)));
		assertThat(y, is(lessThanOrEqualTo(15l)));
	}
}
