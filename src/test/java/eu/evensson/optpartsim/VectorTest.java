package eu.evensson.optpartsim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("A Vector")
@RunWith(JUnitPlatform.class)
public class VectorTest {

	@DisplayName("has an x value")
	@ParameterizedTest(name = "x={0,number,0.#E0}")
	@ValueSource(doubles = {Double.MIN_VALUE, 0.0, Double.MAX_VALUE})
	void hasAnX(final double x) {
		final Vector vector = new Vector(x, 0);
		assertThat(vector.x(), is(x));
	}

	@DisplayName("has a y value")
	@ParameterizedTest(name = "y={0,number,0.#E0}")
	@ValueSource(doubles = {Double.MIN_VALUE, 0.0, Double.MAX_VALUE})
	void hasAY(final double y) {
		final Vector vector = new Vector(0, y);
		assertThat(vector.y(), is(y));
	}
}
