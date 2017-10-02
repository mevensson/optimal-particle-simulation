package eu.evensson.optpartsim.simulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import eu.evensson.optpartsim.physics.Array2D;
import eu.evensson.optpartsim.physics.Box;
import eu.evensson.optpartsim.physics.Particle;

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

	private final Box walls;
	private final Array2D<Cell> cells;
	private final double cellWidth;
	private final double cellHeight;

	public CellStructure(final Box walls, final int widthInCells,
			final int heightInCells) {
		this.walls = walls;
		cells = new Array2D<>(widthInCells, heightInCells);
		cellWidth = walls.width() / widthInCells;
		cellHeight = walls.height() / heightInCells;

		cells.forEach((x, y) -> {
			cells.set(x, y, new Cell(cellBox(x, y)));
		});
	}

	public Box getWalls() {
		return walls;
	}

	public Box insert(final Particle particle) {
		final Cell cell = getCell(particle);
		cell.add(particle);
		return cell.box();
	}

	public Collection<Particle> neighbourParticles(final Particle particle) {
		final Collection<Particle> result = new ArrayList<>();
		final int px = cellColumn(particle);
		final int py = cellRow(particle);
		for (int nx = px - 1; nx <= px + 1; nx++) {
			if (nx < 0 || nx >= cells.width()) {
				continue;
			}
			for (int ny = py - 1; ny <= py + 1; ny++) {
				if (ny < 0 || ny >= cells.height()) {
					continue;
				}
				final Cell cell = cells.get(nx, ny);
				result.addAll(cell.particles);
			}
		}
		return result;
	}

	public Box remove(final Particle particle) {
		final Cell cell = getCell(particle);
		if (!cell.remove(particle)) {
			throw new ParticleNotFoundException();
		}

		return cell.box();
	}

	private Box cellBox(final Integer x, final Integer y) {
		return new Box(walls.x() + cellWidth * x, walls.y() + cellHeight * y,
				cellWidth, cellHeight);
	}

	private Cell getCell(final Particle particle) {
		return cells.get(cellColumn(particle), cellRow(particle));
	}

	private int cellColumn(final Particle particle) {
		final double x = particle.position().x();
		return (int) Math.floor((x - walls.x()) / cellWidth);
	}

	private int cellRow(final Particle particle) {
		final double y = particle.position().y();
		return (int) Math.floor((y - walls.y()) / cellHeight);
	}
}
