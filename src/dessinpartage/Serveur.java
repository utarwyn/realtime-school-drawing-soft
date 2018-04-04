package dessinpartage;

import dessinpartage.serveur.ReceiverThread;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Classe de gestion du serveur.
 * @version 1.0.0
 */
public class Serveur {

	/**
	 * Socket de connexion UDP (Multicast)
	 */
	private DatagramSocket datagramSocket;

	/**
	 * Groupe utilisée par la socket multicast
	 */
	private InetAddress datagramGroup;

	/**
	 * Liste des messages stockés par le serveur
	 */
	private ArrayList<String> messages;

	/**
	 * Liste des dessins stockés par le serveur
	 */
	private ArrayList<String> dessins;

	private Serveur() {
		this.messages = new ArrayList<>();
		this.dessins = new ArrayList<>();

		try {
			this.datagramSocket = new DatagramSocket();
			this.datagramGroup = InetAddress.getByName("225.1.1.1");
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public DatagramSocket getDatagramSocket() {
		return datagramSocket;
	}

	public InetAddress getDatagramGroup() {
		return datagramGroup;
	}

	public ArrayList<String> getMessages() {
		return this.messages;
	}

	public ArrayList<String> getDessins() {
		return dessins;
	}

	/**
	 * Enregistre un nouveau message
	 * @param message Message à enregistrer
	 */
	public void nouveauMessage(String message) {
		this.messages.add(message);
	}

	/**
	 * Enregistre un nouveau dessin
	 * @param dessin Dessin à enregistrer en mémoire
	 */
	public void nouveauDessin(String dessin) {
		this.dessins.add(dessin);
	}

	/**
	 * Supprime un message de la mémoire
	 * @param id Identifiant du dessin à supprimer
	 */
	public void supprimerDessin(int id) {
		this.dessins.removeIf(dessin -> Integer.parseInt(dessin.substring(0, 1)) == id);
	}

	public static void main(String[] args) throws IOException {
		Serveur serveur = new Serveur();

		new ReceiverThread(serveur).start();
	}

}
