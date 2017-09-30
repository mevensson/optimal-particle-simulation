package eu.evensson.optpartsim.physics;

import static eu.evensson.optpartsim.physics.Vector.polar;
import static eu.evensson.optpartsim.physics.Vector.vector;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("A Vector")
public class VectorTest {

	static final double X = 10.0;
	static final double Y = 20.0;

	Vector aVector;

	@BeforeEach
	void createVector() {
		aVector = vector(X, Y);
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

	@DisplayName("constructed with polar coordinates")
	@Nested
	class ConstructesWithPolarCoordinates {

		static final double DISTANCE = 10.0;
		static final double ANGLE = 2.0;

		@BeforeEach
		void createVector() {
			aVector = polar(DISTANCE, ANGLE);
		}

		@DisplayName("has an x value")
		@Test
		void hasAnX() {
			assertThat(aVector.x(), is(DISTANCE * cos(ANGLE)));
		}

		@DisplayName("has a y value")
		@Test
		void hasAY() {
			assertThat(aVector.y(), is(DISTANCE * sin(ANGLE)));
		}

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
			otherVector = vector(OTHER_X, OTHER_Y);
			addedVector = aVector.add(otherVector);
		}

		@DisplayName("is not modified")
		@Test
		void isNotModified() {
			assertThat(aVector, is(vector(X, Y)));
		}

		@DisplayName("does not modify the other vector")
		@Test
		void doesNotModifyOtherVector() {
			assertThat(otherVector, is(vector(OTHER_X, OTHER_Y)));
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

	@DisplayName("when subtracted with other vector")
	@Nested
	class WhenSubtractedWithOtherVector {

		static final double OTHER_X = 15.0;
		static final double OTHER_Y = 25.0;

		Vector otherVector;
		Vector resultVector;

		@BeforeEach
		void addWithOtherVector() {
			otherVector = vector(OTHER_X, OTHER_Y);
			resultVector = aVector.subtract(otherVector);
		}

		@DisplayName("is not modified")
		@Test
		void isNotModified() {
			assertThat(aVector, is(vector(X, Y)));
		}

		@DisplayName("does not modify the other vector")
		@Test
		void doesNotModifyOtherVector() {
			assertThat(otherVector, is(vector(OTHER_X, OTHER_Y)));
		}

		@DisplayName("returns a vector with")
		@Nested
		class ReturnsAVectorWith {

			@DisplayName("an X that is the difference of the Xes")
			@Test
			void anXThatIsTheDifferenceOfTheXes() {
				assertThat(resultVector.x(), is(X - OTHER_X));
			}

			@DisplayName("a Y that is the difference of the Ys")
			@Test
			void aYThatIsTheDifferenceOfTheYs() {
				assertThat(resultVector.y(), is(Y - OTHER_Y));
			}
		}
	}

	@DisplayName("when multiplied with scalar")
	@Nested
	class WhenMultipliedWithScalar {

		static final double SCALAR = 2.3;

		Vector multipliedVector;

		@BeforeEach
		void multiplyWithScalar() {
			multipliedVector = aVector.multiply(SCALAR);
		}

		@DisplayName("is not modified")
		@Test
		void isNotModified() {
			assertThat(aVector, is(vector(X, Y)));
		}


		@DisplayName("returns a vector with")
		@Nested
		class ReturnsAVectorWith {

			@DisplayName("an X that is multiplied with the scalar")
			@Test
			void anXThatIsMultipliedWithTheScalar() {
				assertThat(multipliedVector.x(), is(X * SCALAR));
			}

			@DisplayName("a Y that is multiplied with the scalar")
			@Test
			void aYThatIsMultipliedWithTheScalar() {
				assertThat(multipliedVector.y(), is(Y * SCALAR));
			}
		}
	}
}
