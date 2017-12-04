package eu.evensson.optpartsim.simulation;

import static eu.evensson.optpartsim.physics.Vector.vector;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import eu.evensson.optpartsim.physics.Particle;
import eu.evensson.optpartsim.physics.Particle.Direction;
import eu.evensson.optpartsim.physics.Vector;
import eu.evensson.optpartsim.testhelpers.CombinableSource;
import eu.evensson.optpartsim.testhelpers.CombineSource;

@DisplayName("An EventHandler")
public class EventHandlerTest {

	private static final double TIME = 12.3;
	private static final double WALL_BOUNCE_TIME = 23.4;

	final CellStructure cellStructure = mock(CellStructure.class);
	final EventChecker eventChecker = mock(EventChecker.class);
	final EventQueue eventQueue = mock(EventQueue.class);

	EventHandler eventHandler;

	@BeforeEach
	void createEventHandler() {
		eventHandler = new EventHandler(cellStructure, eventChecker, eventQueue);
	}

	@DisplayName("with wall bounce event")
	@Nested
	class WithWallBounceEvent {

		@DisplayName("returns particle momentum")
		@ParameterizedTest
		@CombineSource(sources = {
				@CombinableSource(valueSource = @ValueSource(doubles = { 0.0, 10.0 })),
				@CombinableSource(valueSource = @ValueSource(doubles = { 0.0, 10.0 })),
				@CombinableSource(enumSource  = @EnumSource(Direction.class)),
		})
		void returnsParticleMomentum(final double momentumX, final double momentumY, final Direction direction) {
			final Particle particle = particleWithMomentum(vector(momentumX, momentumY));
			final WallBounceEvent event = new WallBounceEvent(WALL_BOUNCE_TIME, particle, direction);

			final double momentum = eventHandler.handle(event);

			assertThat(momentum, is(particle.momentum(direction)));
		}

		@DisplayName("moves and bounces particle in cellstructure")
		@ParameterizedTest
		@CombineSource(sources = {
				@CombinableSource(valueSource = @ValueSource(doubles = { 0.0, 10.0 })),
				@CombinableSource(valueSource = @ValueSource(doubles = { 0.0, 10.0 })),
				@CombinableSource(enumSource  = @EnumSource(Direction.class)),
		})
		void movesAndBouncesParticleInCellStructure(final double momentumX, final double momentumY, final Direction direction) {
			final Particle particle = particleWithMomentum(vector(momentumX, momentumY));
			final WallBounceEvent event = new WallBounceEvent(WALL_BOUNCE_TIME, particle, direction);

			eventHandler.handle(event);

			verify(cellStructure).remove(particle);
			final Particle newParticle = particle.move(WALL_BOUNCE_TIME).bounce(direction);
			verify(cellStructure).insert(newParticle);
		}

		@DisplayName("adds particles next event to event queue")
		@Test
		void adds() {
			final Particle particle = new Particle(0, TIME, vector(1, 2), vector(3, 4));
			final Direction anyDirection = Direction.HORIZONTAL;
			final WallBounceEvent event = new WallBounceEvent(WALL_BOUNCE_TIME, particle, anyDirection);

			final Particle newParticle = particle.move(WALL_BOUNCE_TIME).bounce(anyDirection);
			final Event nextEvent = mock(Event.class);
			when(eventChecker.check(newParticle)).thenReturn(nextEvent);

			eventHandler.handle(event);

			verify(eventChecker).check(newParticle);
			verify(eventQueue).add(same(nextEvent));
		}

		private Particle particleWithMomentum(final Vector momentum) {
			return new Particle(0, TIME, vector(0, 0), momentum.multiply(1.0 / Particle.MASS));
		}
	}
}
