package eu.evensson.optpartsim.simulation;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import eu.evensson.optpartsim.physics.Particle;

@DisplayName("A Simulation")
public class DefaultSimulationTest {

	static final Event DEFAULT_EVENT = new Event(0.0);

	final CellStructure cellStructure = mock(CellStructure.class);
	final EventQueue eventQueue = new EventQueue();
	final EventChecker eventChecker = mock(EventChecker.class);
	final EventHandler eventHandler = mock(EventHandler.class);

	DefaultSimulation aSimulation;

	@BeforeEach
	void createSimulation() {
		aSimulation = new DefaultSimulation(cellStructure, eventQueue, eventChecker, eventHandler);
	}

	@BeforeEach
	void setDefaultEvent() {
		when(eventChecker.check(any())).thenReturn(DEFAULT_EVENT);
	}

	@DisplayName("adds particles to Cell Structure")
	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 10 })
	void addsParticlesToCellStructure(final int numParticles) {
		final List<Particle> particles = createParticles(numParticles);

		aSimulation.simulate(particles, 0.0);

		for (final Particle particle : particles) {
			verify(cellStructure).insert(particle);
		}
	}

	@DisplayName("adds first event for each particle to Event Queue")
	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 10 })
	void addsFirstEventForEachParticleToEventQueue(final int numParticles) {
		final List<Particle> particles = createParticles(numParticles);
		final List<Event> events = new ArrayList<Event>();
		final double duration = 0.0;
		double time = duration + 1.0;
		for (final Particle particle : particles) {
			final Event event = new Event(time++);
			events.add(event);
			when(eventChecker.check(particle)).thenReturn(event);
		}

		aSimulation.simulate(particles, duration);

		for (final Event event : events) {
			assertThat(eventQueue.removeFirst(), is(event));
		}
	}

	@DisplayName("handles events until duration is up")
	@ParameterizedTest
	@ValueSource(doubles = { -0.0001, 0.0, 9.9999, 10.0, 10.0001 })
	void handlesEventsUntilDuration(final double duration) {
		final Particle particle = mock(Particle.class);
		final List<Event> events = new ArrayList<Event>();
		mockEventsWithIncreasingTime(events, 0.0, 1.0);

		aSimulation.simulate(asList(particle), duration);

		for (final Event event : events) {
			if (event.time() <= duration) {
				verify(eventHandler).handle(event);
			}
		}
	}

	@DisplayName("returns sum of momentum for all handled events")
	@ParameterizedTest
	@ValueSource(doubles = { -0.0001, 0.0, 9.9999, 10.0, 10.0001 })
	void returnsSumOfMomentumForAllHandledEvents(final double duration) {
		final Particle particle = mock(Particle.class);
		final List<Event> events = new ArrayList<Event>();
		final double startMomentum = 1.2;
		final double incrementMomentum = 0.2;
		mockEventsWithIncreasingMomentum(events, 0.0, 1.0, startMomentum, incrementMomentum);

		final double totalMomentum = aSimulation.simulate(asList(particle), duration);

		final double expectedTotalMomentum = events.size() * (2 * startMomentum + (events.size() - 1) * incrementMomentum) / 2;
		assertThat(totalMomentum, is(closeTo(expectedTotalMomentum, Math.ulp(expectedTotalMomentum))));
	}

	private List<Particle> createParticles(final int numParticles) {
		final List <Particle> particles = new ArrayList<>();
		for (int i = 0; i < numParticles; ++i) {
			particles.add(mock(Particle.class));
		}
		return particles;
	}

	private List<Event> mockEventsWithIncreasingTime(
			final List<Event> events, final double start, final double increment) {
		final Event firstEvent = new Event(start);
		when(eventChecker.check(any())).thenReturn(firstEvent);

		final AtomicReference<Double> nextTime = new AtomicReference<Double>(start + increment);
		when(eventHandler.handle(any())).then(invocation -> {
			final Event event = new Event(nextTime.getAndUpdate((currTime) -> currTime + increment));
			events.add(event);
			eventQueue.add(event);
			return 0.0;
		});

		return events;
	}

	private List<Event> mockEventsWithIncreasingMomentum(
			final List<Event> events,
			final double startTime, final double incrementTime,
			final double startMomentum, final double incrementMomentum) {
		final Event firstEvent = new Event(startTime);
		when(eventChecker.check(any())).thenReturn(firstEvent);

		final AtomicReference<Double> nextTime = new AtomicReference<Double>(startTime + incrementTime);
		final AtomicReference<Double> nextMomentum = new AtomicReference<Double>(startMomentum);
		when(eventHandler.handle(any())).then(invocation -> {
			final Event event = new Event(nextTime.getAndUpdate(currTime -> currTime + incrementTime));
			events.add(event);
			eventQueue.add(event);
			return nextMomentum.getAndUpdate(currMomentum -> currMomentum + incrementMomentum);
		});

		return events;
	}
}
