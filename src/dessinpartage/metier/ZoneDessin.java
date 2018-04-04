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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Représente la zone de dessin côté métier
 * @version 1.0.0
 */
public class ZoneDessin implements MessageServeurListener {

	private Controleur controleur;

	private Pinceau pinceau;

	private List<Forme> formes;

	ZoneDessin(Controleur controleur, Reseau reseau) {
		this.controleur = controleur;
		this.pinceau = new Pinceau(Color.BLACK, FormeType.CARRE_PLEIN, 50);
		this.formes = Collections.synchronizedList(new ArrayList<>());

		try {
			this.initialiser(reseau);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retourne la liste des formes stockées dans le métier
	 * @return Liste des formes stockées
	 */
	public List<Forme> getFormes() {
		return new ArrayList<>(this.formes);
	}

	/**
	 * Envoi une forme sur le réseau à une position précise (en fonction du pinceau actif)
	 * @param x Position X de génération de la forme
	 * @param y Position Y de génération de la forme
	 */
	public void envoyerForme(int x, int y) {
		double taille = this.pinceau.getTaille();
		double rX = x - taille/2, rY = y - taille/2;

		try {
			Reseau.envoyer(
					"d:" + this.pinceau.getType().name() + ":" + this.pinceau.getCouleur().getRGB() + ":"
					+ rX + ":" + rY + ":" + taille
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ajoute une forme en mémoire
	 * @param forme Forme à ajouter
	 */
	public void ajouterForme(Forme forme) {
		this.formes.add(forme);
	}

	/**
	 * Retourne le pinceau actif.
	 * @return Pinceau actif utilisé pour la création de nouvelel forme.
	 */
	public Pinceau getPinceau() {
		return this.pinceau;
	}

	/**
	 * Défini le type courant de pinceau (pour changer la forme)
	 * @param type Type de pinceau à utiliser
	 */
	public void setTypeCourant(FormeType type) {
		this.pinceau.setType(type);
	}

	/**
	 * Défini la couleur du pinceau
	 * @param couleur Couleur à utiliser pour le pinceau
	 */
	public void setCouleurCourante(Color couleur) {
		this.pinceau.setCouleur(couleur);
	}

	/**
	 * Modifie la taille du pinceau
	 * @param tailleInc Taille à utiliser pour le pinceau
	 */
	public void incrementerTailleCourante(double tailleInc) {
		this.pinceau.setTaille(Math.min(300, Math.max(10, this.pinceau.getTaille() + tailleInc * 2)));
	}

	/**
	 * Essaie de supprimer une forme à une position précise sur le réseau
	 * @param x Position X où supprimer la forme souhaitée
	 * @param y Position Y où supprimer la forme souhaitée
	 */
	public void supprimerForme(int x, int y) {
		// Récupération de la forme à supprimer à la position donnée
		Forme forme = null;

		Iterator<Forme> formesIt = this.formes.iterator();
		while (formesIt.hasNext()) {
			Forme f = formesIt.next();

			if (f.isInside(x, y))
				forme = f;
		}

		if (forme == null) return;

		// Et envoi de l'identifiant de la forme à supprimer si elle existe
		try {
			Reseau.envoyer("r:" + forme.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode lancée quand un message est reçu du serveur.
	 * @param message Message de création d'un nouveau dessin (ou suppression).
	 */
	@Override
	public void nouveauMessage(String message) {
		// Suppression d'une forme depuis le serveur
		if (message.startsWith("r:")) {
			int id = Integer.parseInt(message.substring(2));

			this.formes.removeIf(forme -> forme.getId() == id);
			this.controleur.rafraichirZoneDessin();

			return;
		}

		if (!message.startsWith("d:")) return;

		// Ajout de la forme au métier ...
		this.ajouterForme(message.substring(2));

		// ... et mise à jour de l'IHM!
		this.controleur.rafraichirZoneDessin();
	}

	/**
	 * Ajoute une forme en mémoire à partir d'une chaîne de caractères
	 * @param formeStr Chaîne de caractères à utiliser pour générer la forme
	 */
	private void ajouterForme(String formeStr) {
		String[] parts = formeStr.split(":");

		// Nouvelle forme!
		int id = Integer.valueOf(parts[0]);
		FormeType type = FormeType.valueOf(parts[1]);
		Color couleur = new Color(Integer.parseInt(parts[2]));
		double x = Double.parseDouble(parts[3]);
		double y = Double.parseDouble(parts[4]);
		double taille = Double.parseDouble(parts[5]);

		// Ajout de la forme reçue par le réseau!
		this.ajouterForme(new Forme(id, type, couleur, x, y, taille));
	}

	/**
	 * Initialise la zone de dessin à partir des formes enregistrés par le serveur
	 * @param reseau Classe réseau utilisée pour récupérer les dessins du serveur
	 * @throws IOException Lancée si il est impossible de récupérer les infos
	 */
	private void initialiser(Reseau reseau) throws IOException {
		// Réception des données
		String dessins = reseau.getTcpSocketIn().readLine().substring(3);

		if (!dessins.isEmpty())
			for (String dessin : dessins.split("@@@"))
				this.ajouterForme(dessin);
	}

}
