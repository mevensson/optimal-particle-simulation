package eu.evensson.optpartsim;

public class TransferEvent extends Event {

	private final Particle particle;

	public TransferEvent(final double time, final Particle particle) {
		super(time);
		this.particle = particle;
	}

	public Particle particle() {
		return particle;
	}

}
