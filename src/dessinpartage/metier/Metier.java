package dessinpartage.metier;

import dessinpartage.Controleur;
import dessinpartage.metier.dessin.Forme;
import dessinpartage.metier.net.Reseau;

import java.util.List;

public class Metier {

	private Controleur controleur;

	private Reseau reseau;

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
		this.reseau = new Reseau();
		this.salonDiscussion = new SalonDiscussion(this.controleur);
		this.zoneDessin = new ZoneDessin(this.controleur);

		this.reseau.ajouterEcouteur(this.salonDiscussion);
		this.reseau.start();
	}

}
