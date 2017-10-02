package eu.evensson.optpartsim.testhelpers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import org.junit.jupiter.params.ParameterizedTest;

public class LongRangeArgumentsProviderTest {

	@ParameterizedTest(name = "value={0}")
	@LongRangeSource(min=1, max=10)
	void exampleTest(final long value) {
		assertThat(value, is(greaterThanOrEqualTo(1l)));
		assertThat(value, is(lessThanOrEqualTo(10l)));
	}
}
