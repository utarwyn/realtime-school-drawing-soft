package dessinpartage.metier;

import dessinpartage.Controleur;
import dessinpartage.metier.dessin.Forme;
import dessinpartage.metier.net.Reseau;

import java.io.IOException;
import java.util.List;

public class Metier {

	private Controleur controleur;

	private SalonDiscussion salonDiscussion;

	private ZoneDessin zoneDessin;

	public Metier(Controleur controleur) {
		this.controleur = controleur;

		this.initialiser();
	}

	public SalonDiscussion getSalonDiscussion() {
		return salonDiscussion;
	}

	public ZoneDessin getZoneDessin() {
		return this.zoneDessin;
	}

	private void initialiser() {
		Reseau reseau;

		try {
			// Création du réseau et de la socket TCP
			reseau = new Reseau();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// Création des deux zones côté métier
		this.salonDiscussion = new SalonDiscussion(this.controleur, reseau);
		this.zoneDessin = new ZoneDessin(this.controleur, reseau);

		// Ajout des écouteurs de message depuis le réseau
		reseau.ajouterEcouteur(this.salonDiscussion);
		reseau.ajouterEcouteur(this.zoneDessin);

		// Démarrage du support réseau UDP!
		reseau.start();
	}

}
