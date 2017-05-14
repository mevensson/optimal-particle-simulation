package eu.evensson.optpartsim;

public class Array2D<T> {

	private final Object[] array;
	private final int width;

	public Array2D(final int width, final int height) {
		this.width = width;
		array = new Object[width * height];
	}

	@SuppressWarnings("unchecked")
	public T get(final int x, final int y) {
		return (T) array[getIndex(x, y)];
	}

	public void set(final int x, final int y, final T value) {
		array[getIndex(x, y)] = value;
	}

	private int getIndex(final int x, final int y) {
		return x * width + y;
	}

}
