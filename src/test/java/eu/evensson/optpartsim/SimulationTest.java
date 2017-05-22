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
public class SimulationTest {

	final static double STRUCTURE_X = 10.0;
	final static double STRUCTURE_Y = 20.0;
	final static double STRUCTURE_WIDTH = 4.0;
	final static double STRUCTURE_HEIGHT = 10.0;
	final static int WIDTH_IN_CELLS = 4;
	final static int HEIGHT_IN_CELLS = 5;

	final CellStructure cellStructure = new CellStructure(new Box(STRUCTURE_X,
			STRUCTURE_Y, STRUCTURE_WIDTH, STRUCTURE_HEIGHT), WIDTH_IN_CELLS,
			HEIGHT_IN_CELLS);

	final List<Particle> particles = new ArrayList<>();

	Simulation aSimulation;

	@BeforeEach
	void createSimulation() {
		aSimulation = new Simulation(cellStructure);
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
		void addsAllParticlesToCellStructure() {
			aSimulation.simulate(particles);

			// Should not throw
			cellStructure.remove(PARTICLE_ONE);
		}

	}
}
