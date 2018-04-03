package dessinpartage.metier;

import dessinpartage.Controleur;
import dessinpartage.metier.net.Reseau;

public class Metier {

	private Controleur controleur;

	private Reseau reseau;

	private SalonDiscussion salonDiscussion;

	public Metier(Controleur controleur) {
		this.controleur = controleur;

		this.initialiser();
	}

	public SalonDiscussion getSalonDiscussion() {
		return salonDiscussion;
	}

	private void initialiser() {
		this.reseau = new Reseau();
		this.salonDiscussion = new SalonDiscussion(this.controleur);

		this.reseau.ajouterEcouteur(this.salonDiscussion);
		this.reseau.start();
	}

}
