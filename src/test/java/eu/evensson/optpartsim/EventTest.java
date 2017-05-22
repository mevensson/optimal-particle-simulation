package eu.evensson.optpartsim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("An Event")
@RunWith(JUnitPlatform.class)
public class EventTest {

	static final double TIME = 12.3;

	Event anEvent;

	@BeforeEach
	void createEvent() {
		anEvent = new Event(TIME);
	}

	@DisplayName("has a Time")
	@Test
	void hasATime() {
		assertThat(anEvent.time(), is(TIME));
	}
}
