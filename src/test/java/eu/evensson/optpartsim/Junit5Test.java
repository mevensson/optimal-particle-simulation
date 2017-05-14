package eu.evensson.optpartsim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@DisplayName("Sample Test Fixture")
public class Junit5Test {

	@Test
	@DisplayName("Sample Test")
	void sampleTest() {
		assertThat(true, is(true));
	}
}
