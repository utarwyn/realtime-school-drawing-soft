package dessinpartage.ihm;

import dessinpartage.Controleur;

import javax.swing.*;

public class IHM extends JFrame {

	private Controleur controleur;

	private DessinPanel dessinPane;

	private OutilsDessinPanel outilsDessinPan;

	private DiscussionPanel discusPane;

	public IHM(Controleur controleur) {
		this.controleur = controleur;

		this.dessinPane = new DessinPanel(this);
		this.outilsDessinPan = new OutilsDessinPanel(this);
		this.discusPane = new DiscussionPanel(this);

		this.setTitle("Dessin partagé");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(960, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	public Controleur getControleur() {
		return this.controleur;
	}

	public void lancer() {
		this.setVisible(true);
	}

	public void nouveauMessage(String message) {
		this.discusPane.nouveauMessage(message);
	}

	public String choixDuNom() {
		return JOptionPane.showInputDialog(this, "Choisissez votre nom pour le tchat", "Nom d'utilisateur", JOptionPane.PLAIN_MESSAGE);
	}

}
