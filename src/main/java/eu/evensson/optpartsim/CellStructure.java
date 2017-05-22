package eu.evensson.optpartsim;

import java.util.HashSet;
import java.util.Set;

public class CellStructure {

	@SuppressWarnings("serial")
	public static class ParticleNotFoundException extends RuntimeException {
	}

	private static class Cell {
		private final Set<Particle> particles = new HashSet<>();
		private final Box box;

		public Cell(final Box box) {
			this.box = box;
		}

		public Box box() {
			return box;
		}

		public void add(final Particle particle) {
			particles.add(particle);
		}

		public boolean remove(final Particle particle) {
			return particles.remove(particle);
		}
	}

	private final Box box;
	private final Array2D<Cell> cells;
	private final double cellWidth;
	private final double cellHeight;

	public CellStructure(final Box box, final int widthInCells,
			final int heightInCells) {
		this.box = box;
		cells = new Array2D<>(widthInCells, heightInCells);
		cellWidth = box.width() / widthInCells;
		cellHeight = box.height() / heightInCells;

		cells.forEach((x, y) -> {
			cells.set(x, y, new Cell(cellBox(x, y)));
		});
	}

	public Box insert(final Particle particle) {
		final Cell cell = getCell(particle);
		cell.add(particle);
		return cell.box();
	}

	public Box remove(final Particle particle) {
		final Cell cell = getCell(particle);
		if (!cell.remove(particle)) {
			throw new ParticleNotFoundException();
		}

		return cell.box();
	}

	private Box cellBox(final Integer x, final Integer y) {
		return new Box(box.x() + cellWidth * x, box.y() + cellHeight * y,
				cellWidth, cellHeight);
	}

	private Cell getCell(final Particle particle) {
		final double x = particle.position().x();
		final double y = particle.position().y();
		return cells.get(cellColumn(x), cellRow(y));
	}

	private int cellColumn(final double x) {
		return (int) Math.floor((x - box.x()) / cellWidth);
	}

	private int cellRow(final double y) {
		return (int) Math.floor((y - box.y()) / cellHeight);
	}
}
