package dessinpartage.metier.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Reseau extends Thread {

	private ArrayList<MessageServeurListener> ecouteurs;

	public Reseau() {
		this.ecouteurs = new ArrayList<>();
	}

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
