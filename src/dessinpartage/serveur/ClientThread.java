package dessinpartage.serveur;

import dessinpartage.Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Socket;

/**
 * Classe qui gère dans un Thread à part le socket d'un client.
 * Ce comportement permet de gérer plusieurs connexions en même temps.
 * @version 1.0.0
 */
public class ClientThread extends Thread {

	private Serveur serveur;

	private BufferedReader in;

	private PrintWriter out;

	ClientThread(Serveur serveur, Socket socket) throws IOException {
		this.serveur = serveur;
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(socket.getOutputStream());

		this.initialiser();
	}

	@Override
	public void run() {
		while (true) {
			try {
				// En attente d'un message envoyé par le client
				String msg = this.in.readLine();

				if (msg != null) {
					// Modification du message en ajoutant l'identifiant pour un nouveau dessin
					if (msg.startsWith("d:"))
						msg = "d:" + (this.serveur.getDessins().size() + 1) + ":" + msg.substring(2);

					byte[] buf = msg.getBytes();

					// Différentes actions à executer au sein du serveur
					if (msg.startsWith("t:"))
						this.serveur.nouveauMessage(msg.substring(2));
					if (msg.startsWith("d:"))
						this.serveur.nouveauDessin(msg.substring(2));
					if (msg.startsWith("r:"))
						this.serveur.supprimerDessin(Integer.parseInt(msg.substring(2)));

					// Renvoi des informations à tous les clients grâce au multicast
					DatagramPacket packet = new DatagramPacket(buf, buf.length, this.serveur.getDatagramGroup(), 4446);
					this.serveur.getDatagramSocket().send(packet);
				}

				// On répond au client qui nous a envoyé ce message.
				this.out.println("404-NO METHOD");
				this.out.flush();

				// On attend un peu avant de traiter une autre requête.
				// (Pour améliorer un peu les performances)
				Thread.sleep(100);
			} catch (IOException | InterruptedException e) {
				break;
			}
		}
	}

	private void initialiser() {
		// Envoi des messages du tchat au client qui vient de se connecter
		if (this.serveur.getMessages().size() > 0)
			this.out.println("ts:" + String.join("@@@", this.serveur.getMessages()));
		else
			this.out.println("ts:");

		this.out.flush();

		// Envoi des dessins déjà présents au client
		if (this.serveur.getDessins().size() > 0)
			this.out.println("ds:" + String.join("@@@", this.serveur.getDessins()));
		else
			this.out.println("ds:");

		this.out.flush();
	}

}
