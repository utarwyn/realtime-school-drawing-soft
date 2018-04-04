package dessinpartage.metier.net;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Classe réseau.
 * Elle gère la connexion du client au serveur.
 * @version 1.0.0
 */
public class Reseau extends Thread {

	private ArrayList<MessageServeurListener> ecouteurs;

	/**
	 * Stocket TCP
	 */
	private Socket tcpSocket;

	/**
	 * Flux entrant de données depuis le serveur de socket TCP
	 */
	private BufferedReader tcpSocketIn;

	/**
	 * Flux sortant de données vers le serveur de socket TCP
	 */
	private PrintWriter tcpSocketOut;

	public Reseau() throws IOException {
		this.ecouteurs = new ArrayList<>();

		this.tcpSocket = new Socket(InetAddress.getLocalHost(), 24000);
		this.tcpSocketIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
		this.tcpSocketOut = new PrintWriter(new OutputStreamWriter(tcpSocket.getOutputStream()));
	}

	public BufferedReader getTcpSocketIn() {
		return tcpSocketIn;
	}

	public PrintWriter getTcpSocketOut() {
		return tcpSocketOut;
	}

	/**
	 * Ajoute une classe qui va écouter l'entrée de message depuis le serveur multicast UDP
	 * @param ecouteur Classe écouteur de messages
	 */
	public void ajouterEcouteur(MessageServeurListener ecouteur) {
		this.ecouteurs.add(ecouteur);
	}

	@Override
	public void run() {
		try {
			InetAddress mcast = InetAddress.getByName("225.1.1.1");
			MulticastSocket ms = new MulticastSocket(4446);
			ms.joinGroup(mcast);

			while (true) {
				DatagramPacket msg = new DatagramPacket(new byte[1024], 1024);
				ms.receive(msg);

				String str = new String(msg.getData()).trim();

				// Lancement des écouteurs de l'évènement
				for (MessageServeurListener ecouteur : this.ecouteurs)
					ecouteur.nouveauMessage(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Envoi un message au serveur socket TCP
	 * @param message Message à envoyer
	 * @return Retour du serveur TCP
	 * @throws IOException Lancée si un problème a lieu pendant le transfert
	 */
	public static String envoyer(String message) throws IOException {
		Socket socket = new Socket(InetAddress.getLocalHost(), 24000);
		String value;

		// Envoi de données
		PrintWriter pw = new PrintWriter(socket.getOutputStream());
		pw.println(message);
		pw.flush();

 		// Réception des données
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		value = br.readLine();

		pw.close();
		br.close();
		socket.close();

		return value;
	}

}
