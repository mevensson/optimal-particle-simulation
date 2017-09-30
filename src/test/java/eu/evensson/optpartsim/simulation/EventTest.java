package eu.evensson.optpartsim.simulation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("An Event")
public class EventTest {

	static final double TIME = 12.3;
	static final double EPSILON = Math.ulp(TIME);

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

	@DisplayName("is less than an Event with greater time")
	@Test
	void isLessThanEventWithGreaterTime() {
		assertThat(anEvent, is(lessThan(new Event(TIME + EPSILON))));
	}

	@DisplayName("is equal to an Event with same time")
	@Test
	void isEqualToEventWithSameTime() {
		assertThat(anEvent, is(comparesEqualTo(new Event(TIME))));
	}

	@DisplayName("is less than an Event with lesser time")
	@Test
	void isGreaterThanEventWithLesserTime() {
		assertThat(anEvent, is(greaterThan(new Event(TIME - EPSILON))));
	}
}
