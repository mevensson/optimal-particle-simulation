package eu.evensson.optpartsim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
public class OldSimulationTest {

	final static double STRUCTURE_X = 10.0;
	final static double STRUCTURE_Y = 20.0;
	final static double STRUCTURE_WIDTH = 4.0;
	final static double STRUCTURE_HEIGHT = 10.0;
	final static int WIDTH_IN_CELLS = 4;
	final static int HEIGHT_IN_CELLS = 5;

	final CellStructure cellStructure = new CellStructure(new Box(STRUCTURE_X,
			STRUCTURE_Y, STRUCTURE_WIDTH, STRUCTURE_HEIGHT), WIDTH_IN_CELLS,
			HEIGHT_IN_CELLS);

	final EventQueue eventQueue = new EventQueue();

	final List<Particle> particles = new ArrayList<>();

	OldSimulation aSimulation;

	@BeforeEach
	void createSimulation() {
		aSimulation = new OldSimulation(cellStructure, eventQueue);
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

		final Vector POSITION_ONE = new Vector(STRUCTURE_X, STRUCTURE_Y);
		final Vector VELOCITY_ONE = new Vector(0, 0);
		final Particle PARTICLE_ONE = new Particle(0, 0, POSITION_ONE,
				VELOCITY_ONE);

		@BeforeEach
		void addParticle() {
			particles.add(PARTICLE_ONE);
		}

		@DisplayName("adds particle to Cell Structure")
		@Test
		void addsParticleToCellStructure() {
			aSimulation.simulate(particles);

			// Should not throw
			cellStructure.remove(PARTICLE_ONE);
		}
	}

	@DisplayName("with transfer left particle")
	@Nested
	class WithTransferLeftParticle {

		static final int CELL_POSITION = 1;
		static final double CELL_WIDTH = STRUCTURE_WIDTH / WIDTH_IN_CELLS;
		static final double SPEED = 1.0;
		static final double TIME = 12.3;

		final Vector POSITION = new Vector(
				STRUCTURE_X + (CELL_POSITION + 0.5) * CELL_WIDTH,
				STRUCTURE_Y + 0.1);
		final Vector VELOCITY = new Vector(-SPEED, 0.0);
		final Particle PARTICLE = new Particle(0, TIME, POSITION, VELOCITY);

		@BeforeEach
		void addParticleAndSimulate() {
			particles.add(PARTICLE);
			aSimulation.simulate(particles);
		}

		@DisplayName("adds Transfer Event to Event Queue")
		@Test
		void addsTransferEventToEventQueue() {
			final double transferTime = TIME + 0.5 * CELL_WIDTH / SPEED;
			final TransferEvent transferEvent = new TransferEvent(transferTime, PARTICLE);
			assertThat(eventQueue.removeFirst(), is(transferEvent));
		}
	}
}
