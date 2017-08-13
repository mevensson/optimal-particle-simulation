package eu.evensson.optpartsim.simulation;

import static java.lang.Math.ulp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import eu.evensson.optpartsim.simulation.Event;
import eu.evensson.optpartsim.simulation.EventQueue;
import eu.evensson.optpartsim.simulation.EventQueue.EventQueueEmptyException;

@DisplayName("An Event Queue")
@RunWith(JUnitPlatform.class)
public class EventQueueTest {

	EventQueue anEventQueue;

	@BeforeEach
	void createEventQueue() {
		anEventQueue = new EventQueue();
	}

	@DisplayName("throws EventQueueEmptyException on peek")
	@Test
	void throwsOnPeek() {
		assertThrows(EventQueueEmptyException.class, () -> {
			anEventQueue.peek();
		});
	}

	@DisplayName("throws EventQueueEmptyException on remove first")
	@Test
	void throwsOnRemoveFirst() {
		assertThrows(EventQueueEmptyException.class, () -> {
			anEventQueue.removeFirst();
		});
	}

	@DisplayName("with an added Event")
	@Nested
	class WithAnAddedEvent {

		static final double TIME = 12.3;
		final Event EVENT_FIRST = new Event(TIME);

		@BeforeEach
		void addEvent() {
			anEventQueue.add(EVENT_FIRST);
		}

		@DisplayName("returns the event on peek")
		@Test
		void returnsTheEventOnPeek() {
			assertThat(anEventQueue.peek(), is(EVENT_FIRST));
		}

		@DisplayName("returns the event on remove first")
		@Test
		void returnsTheEventOnRemoveFirst() {
			assertThat(anEventQueue.removeFirst(), is(EVENT_FIRST));
		}

		@DisplayName("and a later Event added")
		@Nested
		class AndALaterEventAdded {
			final Event EVENT_LATER = new Event(TIME + ulp(TIME));

			@BeforeEach
			void addLaterEvent() {
				anEventQueue.add(EVENT_LATER);
			}

			@DisplayName("returns the first event on peek")
			@Test
			void returnsTheFirstEventOnPeek() {
				assertThat(anEventQueue.peek(), is(EVENT_FIRST));
			}

			@DisplayName("returns the first event on second peek")
			@Test
			void returnsTheFirstEventOnSecondPeek() {
				anEventQueue.peek();
				assertThat(anEventQueue.peek(), is(EVENT_FIRST));
			}

			@DisplayName("returns the first event on remove first")
			@Test
			void returnsTheFirstEventOnRemoveFirst() {
				assertThat(anEventQueue.removeFirst(), is(EVENT_FIRST));
			}

			@DisplayName("returns the later event on second remove first")
			@Test
			void returnsTheLaterEventOnSecondRemoveFirst() {
				anEventQueue.removeFirst();
				assertThat(anEventQueue.removeFirst(), is(EVENT_LATER));
			}

		}

		@DisplayName("and an earlier Event added")
		@Nested
		class AndAnEarlierEventAdded {
			final Event EVENT_EARLIER = new Event(TIME - ulp(TIME));

			@BeforeEach
			void addEarlierEvent() {
				anEventQueue.add(EVENT_EARLIER);
			}

			@DisplayName("returns the earlier event on remove first")
			@Test
			void returnsTheEventOnRemoveFirst() {
				assertThat(anEventQueue.removeFirst(), is(EVENT_EARLIER));
			}

			@DisplayName("returns the first event on second remove first")
			@Test
			void returnsTheFirstEventOnSecondRemoveFirst() {
				anEventQueue.removeFirst();
				assertThat(anEventQueue.removeFirst(), is(EVENT_FIRST));
			}

		}
	}

}
