package dessinpartage.metier.dessin;

import java.awt.*;

public class Forme {

	private FormeType type;

	private Color color;

	private double x;

	private double y;

	private double size;

	public Forme(FormeType type, Color color, double x, double y, double size) {
		this.type = type;
		this.color = color;
		this.x = x;
		this.y = y;
		this.size = size;
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

}
