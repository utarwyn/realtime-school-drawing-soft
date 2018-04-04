package dessinpartage.metier.dessin;

import java.awt.*;

/**
 * Représente le pinceau utilisé pour créer de nouvelles formes.
 * @version 1.0.0
 */
public class Pinceau {

	private Color couleur;

	private FormeType type;

	private double taille;

	public Pinceau(Color couleur, FormeType type, double taille) {
		this.couleur = couleur;
		this.type = type;
		this.taille = taille;
	}

	public Color getCouleur() {
		return this.couleur;
	}

	public FormeType getType() {
		return this.type;
	}

	public double getTaille() {
		return this.taille;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	public void setType(FormeType type) {
		this.type = type;
	}

	public void setTaille(double taille) {
		this.taille = taille;
	}

}
