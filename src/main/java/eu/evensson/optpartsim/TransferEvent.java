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

	// Generated code

	@Override
	public String toString() {
		return "TransferEvent [particle=" + particle + ", toString()="
				+ super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((particle == null) ? 0 : particle.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TransferEvent other = (TransferEvent) obj;
		if (particle == null) {
			if (other.particle != null)
				return false;
		} else if (!particle.equals(other.particle))
			return false;
		return true;
	}


}
