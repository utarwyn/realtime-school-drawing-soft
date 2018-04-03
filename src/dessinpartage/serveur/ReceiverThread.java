package dessinpartage.serveur;

import dessinpartage.Serveur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ReceiverThread extends Thread {

	private static final int PORT = 24000;

	private Serveur metier;

	private ServerSocket server;

	public ReceiverThread(Serveur serveur) throws IOException {
		this.metier = serveur;
		this.server = new ServerSocket(PORT);
	}

	@Override
	public void run() {
		System.out.println("| Serveur connecté, prêt à recevoir les clients.");

		while (true) {
			try {
				new ClientThread(this.metier, this.server.accept()).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
