package dessinpartage.metier;

import dessinpartage.Controleur;
import dessinpartage.metier.dessin.Forme;
import dessinpartage.metier.net.Reseau;

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
		Reseau reseau = new Reseau();

		// Création des deux zones côté métier
		this.salonDiscussion = new SalonDiscussion(this.controleur);
		this.zoneDessin = new ZoneDessin(this.controleur);

		// Ajout des écouteurs de message depuis le réseau
		reseau.ajouterEcouteur(this.salonDiscussion);
		reseau.ajouterEcouteur(this.zoneDessin);

		// Démarrage du support réseau!
		reseau.start();
	}

}
