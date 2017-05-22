package eu.evensson.optpartsim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("A 2D Array")
@RunWith(JUnitPlatform.class)
public class Array2DTest {

	static final int WIDTH = 3;
	static final int HEIGHT = 5;

	Array2D<Integer> a2DArray;

	@BeforeEach
	void createBox() {
		a2DArray = new Array2D<>(WIDTH, HEIGHT);
	}

	@DisplayName("for each iterates over all elements in row first order")
	@Test
	void forEachIteratesOverAllElementsInRowFirstOrder() {
		final AtomicInteger expectedX = new AtomicInteger(0);
		final AtomicInteger expectedY = new AtomicInteger(0);
		a2DArray.forEach((x, y) -> {
			assertThat(x, is(expectedX.get()));
			assertThat(y, is(expectedY.get()));

			if (expectedX.incrementAndGet() == WIDTH) {
				expectedX.set(0);
				expectedY.incrementAndGet();
			}
		});
		assertThat(expectedX.get(), is(0));
		assertThat(expectedY.get(), is(HEIGHT));
	}

	@DisplayName("has null elements")
	@Test
	void hasNullElements() {
		a2DArray.forEach((x, y) -> {
			assertThat(a2DArray.get(x, y), is(nullValue()));
		});
	}

	@DisplayName("returns same elements as was set")
	@Test
	void returnsSameElementsAsWasSet() {
		a2DArray.forEach((x, y) -> {
			final Integer value = x * y;
			a2DArray.set(x, y, value);
		});
		a2DArray.forEach((x, y) -> {
			final Integer value = x * y;
			assertThat(a2DArray.get(x, y), is(sameInstance(value)));
		});
	}
}
