package eu.evensson.optpartsim;

import static eu.evensson.optpartsim.Vector.vector;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("A Simulation")
@RunWith(JUnitPlatform.class)
public class DefaultSimulationTest {

	final static Box WALLS = new Box(10.0, 20.0, 4.0, 10.0);
	static final Event DEFAULT_EVENT = new Event(0.0);

	final CellStructure cellStructure = mock(CellStructure.class);
	final EventQueue eventQueue = new EventQueue();
	final EventChecker eventChecker = mock(EventChecker.class);

	final List<Particle> particles = new ArrayList<>();

	DefaultSimulation aSimulation;

	@BeforeEach
	void createSimulation() {
		aSimulation = new DefaultSimulation(cellStructure, eventQueue, eventChecker);
	}

	@BeforeEach
	void setDefaultEvent() {
		when(eventChecker.check(any())).thenReturn(DEFAULT_EVENT);
	}

	@DisplayName("with no particles")
	@Nested
	class WithNoParticles {

		@DisplayName("returns zero momentum")
		@Test
		void returnsZeroMomentum() {
			assertThat(aSimulation.simulate(particles), is(0.0));
		}
	}

	@DisplayName("with one particle")
	@Nested
	class WithAParticle {

		final Particle PARTICLE =
				new Particle(0, 0.0, vector(0.0, 0.0), vector(0.0, 0.0));

		@BeforeEach
		void addParticle() {
			particles.add(PARTICLE);
		}

		@DisplayName("adds particle to Cell Structure")
		@Test
		void addsParticleToCellStructure() {
			aSimulation.simulate(particles);

			verify(cellStructure).insert(PARTICLE);
		}
	}

	@DisplayName("with wall bounce left particle")
	@Nested
	class WithWallBounceLeftParticle {

		final Event WALL_BOUNCE_EVENT = new WallBounceEvent(0.0);
		final Particle PARTICLE =
				new Particle(0, 0.0, vector(0.0, 0.0), vector(0.0, 0.0));

		@BeforeEach
		void addParticle() {
			particles.add(PARTICLE);
		}

		@BeforeEach
		void setEvent() {
			when(eventChecker.check(PARTICLE)).thenReturn(WALL_BOUNCE_EVENT);
		}

		@DisplayName("adds Wall Bounce Event to Event Queue")
		@Test
		void addsWallBounceEventToEventQueue() {
			aSimulation.simulate(particles);

			assertThat(eventQueue.removeFirst(), is(WALL_BOUNCE_EVENT));
		}
	}
}
