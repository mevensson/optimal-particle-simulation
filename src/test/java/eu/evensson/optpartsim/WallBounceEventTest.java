package eu.evensson.optpartsim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("A Wall Bounce Event")
@RunWith(JUnitPlatform.class)
public class WallBounceEventTest {

	static final Particle PARTICLE = new Particle(0, 0, null, null);

	WallBounceEvent aWallBounceEvent;

	@BeforeEach
	void createEvent() {
		aWallBounceEvent = new WallBounceEvent(0.0, PARTICLE);
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

}
