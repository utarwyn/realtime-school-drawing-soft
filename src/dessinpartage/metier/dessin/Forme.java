package dessinpartage.metier.dessin;

import java.awt.*;

/**
 * Représente une forme côté métier.
 * Une forme = un dessin.
 * @version 1.0.0
 */
public class Forme {

	private int id;

	private FormeType type;

	private Color color;

	private double x;

	private double y;

	private double size;

	public Forme(int id, FormeType type, Color color, double x, double y, double size) {
		this.id = id;
		this.type = type;
		this.color = color;
		this.x = x;
		this.y = y;
		this.size = size;
	}

	public int getId() {
		return this.id;
	}

	public FormeType getType() {
		return this.type;
	}

	public Color getColor() {
		return this.color;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getSize() {
		return this.size;
	}

	public boolean isInside(int x, int y) {
		switch (this.type) {
			case CARRE:
			case CARRE_PLEIN:
				return x >= getX() && y >= getY() && x <= getX() + getSize() && y <= getY() + getSize();
			case CERCLE:
			case DISQUE:
				double ray = getSize() / 2;
				double cX = getX() + ray;
				double cY = getY() + ray;

				return Math.pow(Math.abs(x - cX), 2) + Math.pow(Math.abs(y - cY), 2) < Math.pow(ray, 2);
		}

		return false;
	}

}
