package dessinpartage.serveur;

import dessinpartage.Serveur;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe qui gère le Thread du serveur TCP.
 * @version 1.0.0
 */
public class ReceiverThread extends Thread {

	private static final int PORT = 24000;

	/**
	 * Le métier
	 */
	private Serveur metier;

	/**
	 * L'objet du serveur de sockets TCP
	 */
	private ServerSocket server;

	public ReceiverThread(Serveur serveur) throws IOException {
		this.metier = serveur;
		this.server = new ServerSocket(PORT);
	}

	@Override
	public void run() {
		System.out.println("| Serveur connecté, prêt à recevoir les clients.");

		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress("google.com", 80));

			System.out.println("| IP de connexion   : " + socket.getLocalAddress());
			System.out.println("| Port de connexion : " + PORT);
		} catch (IOException ignored) { }

		while (true) {
			try {
				new ClientThread(this.metier, this.server.accept()).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
