package eu.evensson.optpartsim.simulation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.evensson.optpartsim.physics.Particle;

@DisplayName("A Wall Bounce Event")
public class WallBounceEventTest {

	static final Particle PARTICLE = new Particle(0, 0, null, null);

	WallBounceEvent aWallBounceEvent;

	@BeforeEach
	void createEvent() {
		aWallBounceEvent = new WallBounceEvent(0.0, PARTICLE, Particle.Direction.VERTICAL);
	}

	@DisplayName("is an Event")
	@Test
	void isAnEvent() {
		assertThat(aWallBounceEvent, is(instanceOf(Event.class)));
	}

	@DisplayName("has a Particle")
	@Test
	void hasAParticle() {
		assertThat(aWallBounceEvent.particle(), is(sameInstance(PARTICLE)));
	}

	@DisplayName("has a direction")
	@ParameterizedTest
	@EnumSource(value = Particle.Direction.class)
	void hasADirection(final Particle.Direction direction) {
		final WallBounceEvent event = new WallBounceEvent(0.0, PARTICLE, direction);
		assertThat(event.direction(), is(direction));
	}

}
