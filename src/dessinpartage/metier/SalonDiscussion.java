package dessinpartage.metier;

import dessinpartage.Controleur;
import dessinpartage.metier.net.MessageServeurListener;
import dessinpartage.metier.net.Reseau;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Représente le salon de discussion côté métier
 * @version 1.0.0
 */
public class SalonDiscussion implements MessageServeurListener {

	private Controleur controleur;

	private List<String> messages;

	private String nom;

	SalonDiscussion(Controleur controleur, Reseau reseau) {
		this.controleur = controleur;
		this.messages = new ArrayList<>();

		try {
			this.initialiser(reseau);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retourne les messages par défaut du programme
	 * @return Liste des messages par défaut
	 */
	public List<String> getMessagesDefaut() {
		return new ArrayList<>(messages);
	}

	/**
	 * Envoi un message sur le réseau (peut demander un nom si nécessaire)
	 * @param message Le message à envoyer sur le réseau
	 */
	public void envoyerMessage(String message) {
		// On demande le nom d'utilisateur
		while (this.nom == null || this.nom.isEmpty()) {
			this.nom = this.controleur.choixDuNom();
		}

		try {
			Reseau.envoyer("t:" + this.nom + ": " + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Un nouveau message vient d'être reçu du serveur.
	 * @param message Message reçu
	 */
	@Override
	public void nouveauMessage(String message) {
		if (!message.startsWith("t:")) return;
		String text = message.substring(2);

		this.controleur.nouveauMessageIHM(text);
	}

	/**
	 * Initialise le salon de discussion avec les messages du serveur
	 * @param reseau Classe de gestion du réseau nécessaire pour la récupération de l'historique
	 * @throws IOException Lancée si un problème réseau a eu lieu.
	 */
	private void initialiser(Reseau reseau) throws IOException {
		// Réception des données
		String messages = reseau.getTcpSocketIn().readLine();
		messages = messages.substring(3);

		if (!messages.isEmpty())
			this.messages = Arrays.asList(messages.split("@@@"));
	}

}
