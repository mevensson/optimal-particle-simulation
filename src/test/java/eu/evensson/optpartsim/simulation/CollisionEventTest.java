package eu.evensson.optpartsim.simulation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.evensson.optpartsim.physics.Particle;

@DisplayName("A Collision Event")
public class CollisionEventTest {

	static final Particle PARTICLE = new Particle(0, 0, null, null);
	static final Particle OTHER_PARTICLE = new Particle(1, 0, null, null);

	CollisionEvent aCollisionEvent;

	@BeforeEach
	void createEvent() {
		aCollisionEvent = new CollisionEvent(0.0, PARTICLE, OTHER_PARTICLE);
	}

	@DisplayName("is an Event")
	@Test
	void isAnEvent() {
		assertThat(aCollisionEvent, is(instanceOf(Event.class)));
	}

	@DisplayName("has a Particle")
	@Test
	void hasAParticle() {
		assertThat(aCollisionEvent.particle(), is(sameInstance(PARTICLE)));
	}

	@DisplayName("has another Particle")
	@Test
	void hasAnotherParticle() {
		assertThat(aCollisionEvent.otherParticle(), is(sameInstance(OTHER_PARTICLE)));
	}

}
