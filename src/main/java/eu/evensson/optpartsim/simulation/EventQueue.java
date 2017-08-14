package eu.evensson.optpartsim.simulation;

import java.util.PriorityQueue;
import java.util.Queue;

public class EventQueue {

	private final Queue<Event> events = new PriorityQueue<>();

	@SuppressWarnings("serial")
	public class EventQueueEmptyException extends RuntimeException {
	}

	public void add(final Event event) {
		events.add(event);
	}

	public Event peek() {
		if (events.isEmpty() ) {
			throw new EventQueueEmptyException();
		}

		return events.peek();
	}

	public Event removeFirst() {
		if (events.isEmpty() ) {
			throw new EventQueueEmptyException();
		}

		return events.remove();
	}

}
