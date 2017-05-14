package eu.evensson.optpartsim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("A Box")
@RunWith(JUnitPlatform.class)
public class BoxTest {

	static final double X = 10.0;
	static final double Y = 10.0;
	static final double WIDTH = 10.0;
	static final double HEIGHT = 20.0;

	Box aBox;

	@BeforeEach
	void createBox() {
		aBox = new Box(X, Y, WIDTH, HEIGHT);
	}

	@DisplayName("has an X position")
	@Test
	void hasAnXPosition() {
		assertThat(aBox.x(), is(X));
	}

	@DisplayName("has a Y position")
	@Test
	void hasAYPosition() {
		assertThat(aBox.y(), is(Y));
	}

	@DisplayName("has a width")
	@Test
	void hasAWidth() {
		assertThat(aBox.width(), is(WIDTH));
	}

	@DisplayName("has a height")
	@Test
	void hasAHeight() {
		assertThat(aBox.height(), is(HEIGHT));
	}
}
