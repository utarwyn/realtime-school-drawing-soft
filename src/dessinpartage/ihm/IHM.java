package dessinpartage.ihm;

import dessinpartage.Controleur;

import javax.swing.*;

/**
 * Classe d'IHM. Est aussi la fenêtre principale du programme.
 * @version 1.0.0
 */
public class IHM extends JFrame {

	/**
	 * Classe Contrôleur
	 */
	private Controleur controleur;

	/**
	 * Panneau de dessin
	 */
	private DessinPanel dessinPane;

	/**
	 * Panneau avec les outils de dessin
	 */
	private OutilsDessinPanel outilsDessinPan;

	/**
	 * Panneau de discussion
	 */
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

	public DessinPanel getDessinPane() {
		return this.dessinPane;
	}

	public OutilsDessinPanel getOutilsDessinPan() {
		return this.outilsDessinPan;
	}

	public Controleur getControleur() {
		return this.controleur;
	}

	/**
	 * Lancement de la fenêtre
	 */
	public void lancer() {
		this.setVisible(true);
	}

	/**
	 * Ajout d'un message dans le panel de discussion
	 * @param message
	 */
	public void nouveauMessage(String message) {
		this.discusPane.nouveauMessage(message);
	}

	public String choixDuNom() {
		return JOptionPane.showInputDialog(this, "Choisissez votre nom pour le tchat", "Nom d'utilisateur", JOptionPane.PLAIN_MESSAGE);
	}

}
