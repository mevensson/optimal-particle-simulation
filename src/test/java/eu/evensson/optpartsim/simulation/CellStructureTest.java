package eu.evensson.optpartsim.simulation;

import static eu.evensson.optpartsim.physics.Vector.vector;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import eu.evensson.optpartsim.physics.Box;
import eu.evensson.optpartsim.physics.Particle;
import eu.evensson.optpartsim.physics.Vector;
import eu.evensson.optpartsim.simulation.CellStructure.ParticleNotFoundException;

@DisplayName("A Cell Structure")
public class CellStructureTest {

	final static double STRUCTURE_X = 10.0;
	final static double STRUCTURE_Y = 20.0;
	final static double STRUCTURE_WIDTH = 4.0;
	final static double STRUCTURE_HEIGHT = 10.0;
	final static int WIDTH_IN_CELLS = 4;
	final static int HEIGHT_IN_CELLS = 5;

	static final double CELL_WIDTH = STRUCTURE_WIDTH / WIDTH_IN_CELLS;
	static final double CELL_HEIGHT = STRUCTURE_HEIGHT / HEIGHT_IN_CELLS;

	final CellStructure aCellStructure = new CellStructure(
			new Box(STRUCTURE_X, STRUCTURE_Y, STRUCTURE_WIDTH, STRUCTURE_HEIGHT),
			WIDTH_IN_CELLS, HEIGHT_IN_CELLS);

	@DisplayName("throws ParticleNotFoundException when particle is removed")
	@Test
	void throwsParticleNotFoundExceptionWhenParticleRemoved() {
		final Vector position = vector(STRUCTURE_X, STRUCTURE_Y);
		final Particle aParticle = new Particle(0, 0, position, null);
		assertThrows(ParticleNotFoundException.class,
				() -> aCellStructure.remove(aParticle));
	}

	@DisplayName("with inserted particle in top left cell")
	@Nested
	class WithInsertedParticleInTopLeftCell {

		static final double PARTICLE_X = STRUCTURE_X + CELL_WIDTH / 2;
		static final double PARTICLE_Y = STRUCTURE_Y + CELL_HEIGHT / 2;

		Particle aParticle;

		@BeforeEach
		void insertParticle() {
			final Vector position = vector(PARTICLE_X, PARTICLE_Y);
			aParticle = new Particle(0, 0, position, null);
			aCellStructure.insert(aParticle);
		}

		@DisplayName("returns top left cell when particle inserted")
		@Test
		void returnsTopLeftCellWhenParticleInserted() {
			final Box expectedBox = new Box(STRUCTURE_X, STRUCTURE_Y,
					CELL_WIDTH, CELL_HEIGHT);
			assertThat(aCellStructure.insert(aParticle), is(expectedBox));
		}

		@DisplayName("returns top left cell when particle removed")
		@Test
		void returnsTopLeftCellWhenParticleRemoved() {
			final Box expectedBox = new Box(STRUCTURE_X, STRUCTURE_Y,
					CELL_WIDTH, CELL_HEIGHT);
			assertThat(aCellStructure.remove(aParticle), is(expectedBox));
		}

		@DisplayName("throws ParticleNotFoundException "
				+ "when other particle is removed")
		@Test
		void throwsParticleNotFoundExceptionWhenOtherParticleRemoved() {
			final Vector position = vector(STRUCTURE_X, STRUCTURE_Y);
			final Particle otherParticle = new Particle(1, 0, position, null);
			assertThrows(ParticleNotFoundException.class,
					() -> aCellStructure.remove(otherParticle));
		}

		@DisplayName("with inserted particle in bottom right cell")
		@Nested
		class WithInsertedParticleInBottomRightCell {

			static final double OTHER_PARTICLE_X = STRUCTURE_X
					+ CELL_WIDTH * (WIDTH_IN_CELLS - 1) + CELL_WIDTH / 2;
			static final double OTHER_PARTICLE_Y = STRUCTURE_Y
					+ CELL_HEIGHT * (HEIGHT_IN_CELLS - 1) + CELL_HEIGHT / 2;

			Particle secondParticle;

			@BeforeEach
			void insertSecondParticle() {
				final Vector position = vector(OTHER_PARTICLE_X, OTHER_PARTICLE_Y);
				secondParticle = new Particle(0, 0, position, null);
				aCellStructure.insert(secondParticle);
			}

			@DisplayName("returns bottom right cell when second particle removed")
			@Test
			void returnsBottomRightCellWhenSecondParticleRemoved() {
				final Box expectedBox = new Box(
						STRUCTURE_X + CELL_WIDTH * (WIDTH_IN_CELLS - 1),
						STRUCTURE_Y + CELL_HEIGHT * (HEIGHT_IN_CELLS - 1),
						CELL_WIDTH, CELL_HEIGHT);
				assertThat(aCellStructure.remove(secondParticle),
						is(expectedBox));
			}
		}
	}

	@DisplayName("particle in lower right corner is in lower right cell")
	@Test
	void returnsTopLeftCellWhenParticleRemoved() {
		final Vector position = vector(
				STRUCTURE_X + STRUCTURE_WIDTH - Particle.RADIUS,
				STRUCTURE_Y + STRUCTURE_HEIGHT - Particle.RADIUS);
		final Particle aParticle = new Particle(0, 0, position, null);
		aCellStructure.insert(aParticle);

		final Box expectedBox = new Box(
				STRUCTURE_X + CELL_WIDTH * (WIDTH_IN_CELLS - 1),
				STRUCTURE_Y + CELL_HEIGHT * (HEIGHT_IN_CELLS - 1),
				CELL_WIDTH, CELL_HEIGHT);
		assertThat(aCellStructure.remove(aParticle), is(expectedBox));
	}

}
