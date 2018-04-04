package dessinpartage.metier;

import dessinpartage.Controleur;
import dessinpartage.metier.net.MessageServeurListener;
import dessinpartage.metier.net.Reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public List<String> getMessagesDefaut() {
		return new ArrayList<>(messages);
	}

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

	@Override
	public void nouveauMessage(String message) {
		if (!message.startsWith("t:")) return;
		String text = message.substring(2);

		this.controleur.nouveauMessageIHM(text);
	}

	private void initialiser(Reseau reseau) throws IOException {
		// Réception des données
		String messages = reseau.getTcpSocketIn().readLine();
		messages = messages.substring(3);

		if (!messages.isEmpty())
			this.messages = Arrays.asList(messages.split("@@@"));
	}

}
