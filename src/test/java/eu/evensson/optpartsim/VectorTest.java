package eu.evensson.optpartsim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("A Vector")
@RunWith(JUnitPlatform.class)
public class VectorTest {

	static double X = 10.0;
	static double Y = 20.0;

	Vector aVector;

	@BeforeEach
	void createVector() {
		aVector = new Vector(X, Y);
	}

	@DisplayName("has an x value")
	@Test
	void hasAnX() {
		assertThat(aVector.x(), is(X));
	}

	@DisplayName("has a y value")
	@Test
	void hasAY() {
		assertThat(aVector.y(), is(Y));
	}
}
