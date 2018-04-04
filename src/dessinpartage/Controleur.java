package dessinpartage;

import dessinpartage.ihm.IHM;
import dessinpartage.metier.Metier;
import dessinpartage.metier.dessin.Forme;
import dessinpartage.metier.dessin.FormeType;
import dessinpartage.metier.dessin.Pinceau;

import java.awt.*;
import java.util.List;

public class Controleur {

	private Metier metier;

	private IHM ihm;

	private Controleur() {
		this.metier = new Metier(this);
		this.ihm = new IHM(this);

		this.ihm.lancer();
	}

	/* ---------------- */
	/*  ZONE DE DESSIN  */
	/* ---------------- */
	public List<Forme> getFormes() {
		return this.metier.getZoneDessin().getFormes();
	}

	public void envoyerForme(int x, int y) {
		this.metier.getZoneDessin().envoyerForme(x, y);
	}

	public Pinceau getPinceauDessin() {
		return this.metier.getZoneDessin().getPinceau();
	}

	public void changerTypeCourant(FormeType type) {
		this.metier.getZoneDessin().setTypeCourant(type);
	}

	public void changerCouleurCourrante(Color couleur) {
		this.metier.getZoneDessin().setCouleurCourante(couleur);
	}

	public void incrementerTailleCourante(double tailleInc) {
		this.metier.getZoneDessin().incrementerTailleCourante(tailleInc);
	}

	public void supprimerForme(int x, int y) {
		this.metier.getZoneDessin().supprimerForme(x, y);
	}

	public void rafraichirZoneDessin() {
		this.ihm.getDessinPane().repaint();
	}

	/* ---------- */
	/*  CHAT BOX  */
	/* ---------- */
	public void nouveauMessageIHM(String message) {
		this.ihm.nouveauMessage(message);
	}

	public List<String> getMessagesDefaut() {
		return this.metier.getSalonDiscussion().getMessagesDefaut();
	}

	public String choixDuNom() {
		return this.ihm.choixDuNom();
	}

	public void envoyerMessage(String message) {
		if (message.isEmpty()) return;
		this.metier.getSalonDiscussion().envoyerMessage(message);
	}


	public static void main(String[] args) {
		new Controleur();
	}

}
