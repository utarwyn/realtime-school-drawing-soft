package dessinpartage;

import dessinpartage.ihm.IHM;
import dessinpartage.metier.Metier;

import java.util.List;

public class Controleur {

	private Metier metier;

	private IHM ihm;

	private Controleur() {
		this.metier = new Metier(this);
		this.ihm = new IHM(this);

		this.ihm.lancer();
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
