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
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import eu.evensson.optpartsim.physics.Particle;
import eu.evensson.optpartsim.simulation.WallBounceEvent.Direction;

@DisplayName("A Wall Bounce Event")
@RunWith(JUnitPlatform.class)
public class WallBounceEventTest {

	static final Particle PARTICLE = new Particle(0, 0, null, null);

	WallBounceEvent aWallBounceEvent;

	@BeforeEach
	void createEvent() {
		aWallBounceEvent = new WallBounceEvent(0.0, PARTICLE, Direction.VERTICAL);
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
	@EnumSource(value = Direction.class)
	void hasADirection(final Direction direction) {
		final WallBounceEvent event = new WallBounceEvent(0.0, PARTICLE, direction);
		assertThat(event.direction(), is(direction));
	}

}
