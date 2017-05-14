package eu.evensson.optpartsim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("A 2D Array")
@RunWith(JUnitPlatform.class)
public class Array2DTest {

	static final int WIDTH = 3;
	static final int HEIGHT = 4;

	Array2D<Integer> a2DArray;

	@BeforeEach
	void createBox() {
		a2DArray = new Array2D<>(WIDTH, HEIGHT);
	}

	@DisplayName("has null elements")
	@Test
	void hasNullElements() {
		for (int x = 0; x < WIDTH; ++x) {
			for (int y = 0; y < HEIGHT; ++y) {
				assertThat(a2DArray.get(x, y), is(nullValue()));
			}
		}
	}

	@DisplayName("returns same elements as was set")
	@Test
	void returnsSameElementsAsWasSet() {
		for (int x = 0; x < WIDTH; ++x) {
			for (int y = 0; y < HEIGHT; ++y) {
				final Integer value = x * y;
				a2DArray.set(x, y, value);
				assertThat(a2DArray.get(x, y), is(sameInstance(value)));
			}
		}
	}
}
