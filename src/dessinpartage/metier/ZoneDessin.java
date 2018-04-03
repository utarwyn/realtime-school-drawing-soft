package dessinpartage.metier;

import dessinpartage.Controleur;
import dessinpartage.metier.dessin.Forme;
import dessinpartage.metier.dessin.FormeType;
import dessinpartage.metier.dessin.Pinceau;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ZoneDessin {

	private Controleur controleur;

	private Pinceau pinceau;

	private List<Forme> formes;

	ZoneDessin(Controleur controleur) {
		this.controleur = controleur;
		this.pinceau = new Pinceau(Color.BLACK, FormeType.CARRE_PLEIN, 50);
		this.formes = new ArrayList<>();
	}

	public List<Forme> getFormes() {
		return new ArrayList<>(this.formes);
	}

	public void creerForme(int x, int y) {
		double taille = this.pinceau.getTaille();

		this.ajouterForme(new Forme(
				this.pinceau.getType(), this.pinceau.getCouleur(),
				x - taille/2, y - taille/2,
				taille
		));
	}

	public void ajouterForme(Forme forme) {
		this.formes.add(forme);
	}

	public Pinceau getPinceau() {
		return this.pinceau;
	}

	public void setTypeCourant(FormeType type) {
		this.pinceau.setType(type);
	}

	public void setCouleurCourante(Color couleur) {
		this.pinceau.setCouleur(couleur);
	}

	public void incrementerTailleCourante(double tailleInc) {
		this.pinceau.setTaille(this.pinceau.getTaille() + tailleInc * 2);
	}

}
