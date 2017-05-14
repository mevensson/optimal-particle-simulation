package eu.evensson.optpartsim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("A Vector")
@RunWith(JUnitPlatform.class)
public class VectorTest {

	static final double X = 10.0;
	static final double Y = 20.0;

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

	@DisplayName("when added with other vector")
	@Nested
	class WhenAddedWithOtherVector {

		static final double OTHER_X = 15.0;
		static final double OTHER_Y = 25.0;

		Vector otherVector;
		Vector addedVector;

		@BeforeEach
		void addWithOtherVector() {
			otherVector = new Vector(OTHER_X, OTHER_Y);
			addedVector = aVector.add(otherVector);
		}

		@DisplayName("is not modified")
		@Test
		void isNotModified() {
			assertThat(aVector, is(new Vector(X, Y)));
		}

		@DisplayName("does not modify the other vector")
		@Test
		void doesNotModifyOtherVector() {
			assertThat(otherVector, is(new Vector(OTHER_X, OTHER_Y)));
		}

		@DisplayName("returns a vector with")
		@Nested
		class ReturnsAVectorWith {

			@DisplayName("an X that is the sum of the Xes")
			@Test
			void anXThatIsTheSumOfTheXes() {
				assertThat(addedVector.x(), is(X + OTHER_X));
			}

			@DisplayName("a Y that is the sum of the Ys")
			@Test
			void aYThatIsTheSumOfTheYs() {
				assertThat(addedVector.y(), is(Y + OTHER_Y));
			}
		}
	}
}
