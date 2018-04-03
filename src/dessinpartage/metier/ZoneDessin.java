package dessinpartage.metier;

import dessinpartage.Controleur;
import dessinpartage.metier.dessin.Forme;
import dessinpartage.metier.dessin.FormeType;
import dessinpartage.metier.dessin.Pinceau;
import dessinpartage.metier.net.MessageServeurListener;
import dessinpartage.metier.net.Reseau;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZoneDessin implements MessageServeurListener {

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
		double rX = x - taille/2, rY = y - taille/2;

		this.ajouterForme(new Forme(this.pinceau.getType(), this.pinceau.getCouleur(), rX, rY, taille));

		try {
			Reseau.envoyer(
					"d:" + this.pinceau.getType().name() + ":" + this.pinceau.getCouleur().getRGB() + ":"
					+ rX + ":" + rY + ":" + taille
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		this.pinceau.setTaille(Math.min(300, Math.max(10, this.pinceau.getTaille() + tailleInc * 2)));
	}

	@Override
	public void nouveauMessage(String message) {
		if (!message.startsWith("d:")) return;
		String[] parts = message.substring(2).split(":");

		// Nouvelle forme!
		FormeType type = FormeType.valueOf(parts[0]);
		Color couleur = new Color(Integer.parseInt(parts[1]));
		double x = Double.parseDouble(parts[2]);
		double y = Double.parseDouble(parts[3]);
		double taille = Double.parseDouble(parts[4]);

		// Ajout de la forme reçue par le réseau!
		this.ajouterForme(new Forme(type, couleur, x, y, taille));

		// Et mise à jour de l'IHM
		this.controleur.rafraichirZoneDessin();
	}

}
