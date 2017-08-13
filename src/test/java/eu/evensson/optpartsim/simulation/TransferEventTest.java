package eu.evensson.optpartsim.simulation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import eu.evensson.optpartsim.physics.Particle;
import eu.evensson.optpartsim.simulation.Event;
import eu.evensson.optpartsim.simulation.TransferEvent;

@DisplayName("A Transfer Event")
@RunWith(JUnitPlatform.class)
public class TransferEventTest {

	static final Particle PARTICLE = new Particle(0, 0, null, null);

	TransferEvent aTransferEvent;

	@BeforeEach
	void createEvent() {
		aTransferEvent = new TransferEvent(0.0, PARTICLE);
	}

	@DisplayName("is an Event")
	@Test
	void isAnEvent() {
		assertThat(aTransferEvent, is(instanceOf(Event.class)));
	}

	@DisplayName("has a Particle")
	@Test
	void hasAParticle() {
		assertThat(aTransferEvent.particle(), is(sameInstance(PARTICLE)));
	}

}
