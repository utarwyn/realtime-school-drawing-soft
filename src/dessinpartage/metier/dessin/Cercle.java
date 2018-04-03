package dessinpartage.metier.dessin;

import java.awt.*;

public class Cercle implements Forme {

	private Color color;

	private double x;

	private double y;

	private double size;

	private boolean filled;

	public Cercle(Color color, double x, double y, double size, boolean filled) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.size = size;
		this.filled = filled;
	}

	public Color getColor() {
		return color;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getSize() {
		return size;
	}

	public boolean isFilled() {
		return filled;
	}

}
