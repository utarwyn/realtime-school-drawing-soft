package dessinpartage.serveur;

import dessinpartage.Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

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
				String msg = this.in.readLine();

				if (msg != null) {
					byte[] buf = msg.getBytes();

					if (msg.startsWith("t:"))
						this.serveur.nouveauMessage(msg.substring(2));
					if (msg.startsWith("d:"))
						this.serveur.nouveauDessin(msg.substring(2));

					DatagramPacket packet = new DatagramPacket(buf, buf.length, this.serveur.getDatagramGroup(), 4446);
					this.serveur.getDatagramSocket().send(packet);
				}

				this.out.println("404-NO METHOD");
				this.out.flush();
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
