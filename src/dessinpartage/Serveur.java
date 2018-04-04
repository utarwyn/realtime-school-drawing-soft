package dessinpartage;

import dessinpartage.serveur.ReceiverThread;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Serveur {

	private DatagramSocket datagramSocket;

	private InetAddress datagramGroup;

	private ArrayList<String> messages;

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

	public void nouveauMessage(String message) {
		this.messages.add(message);
	}

	public void nouveauDessin(String dessin) {
		this.dessins.add(dessin);
	}

	public static void main(String[] args) throws IOException {
		Serveur serveur = new Serveur();

		new ReceiverThread(serveur).start();
	}

}
