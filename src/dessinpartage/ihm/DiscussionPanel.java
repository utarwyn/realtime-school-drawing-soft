package dessinpartage.ihm;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel de discussion dans la partie Graphique de la fenêtre.
 * @version 1.0.0
 */
class DiscussionPanel extends JPanel implements ActionListener, KeyListener {

	/**
	 * Classe d'IHM
	 */
	private IHM ihm;

	/**
	 * Stocke les messages reçus depuis le serveur
	 */
	private JPanel messages;

	/**
	 * Panel de scroll pour les messages
	 */
	private JScrollPane scrollPane;

	/**
	 * Champs utilisé pour rentrer un message
	 */
	private JTextField champs;

	/**
	 * Bouton d'envoi
	 */
	private JButton envoyer;

	DiscussionPanel(IHM ihm) {
		this.ihm = ihm;

		this.preparer();
		this.ihm.add(this, BorderLayout.EAST);
	}

	private void preparer() {
		Color c = new Color(149, 175, 192);

		this.setLayout(new BorderLayout());
		this.setBackground(new Color(189, 195, 199));
		this.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, new Color(189, 215, 232)));
		this.setPreferredSize(new Dimension(300, 600));
		this.setOpaque(true);

		// Définition des éléments
		this.messages = new JPanel();
		this.messages.setLayout(new BoxLayout(this.messages, BoxLayout.Y_AXIS));
		this.messages.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.messages.setBackground(new Color(230, 230, 230));
		this.messages.setOpaque(true);

		this.scrollPane = new JScrollPane(this.messages);
		scrollPane.setPreferredSize(new Dimension(300, 500));
		scrollPane.setBorder(BorderFactory.createLineBorder(c, 3));
		scrollPane.setHorizontalScrollBar(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(12);
		scrollPane.setOpaque(false);

		this.champs = new JTextField();
		this.envoyer = new JButton("Envoyer");

		this.champs.setBorder(new CompoundBorder(
				BorderFactory.createLineBorder(c, 3),
				BorderFactory.createEmptyBorder(0, 15, 0, 15)
		));
		this.champs.addKeyListener(this);

		this.envoyer.setBorder(BorderFactory.createEmptyBorder());
		this.envoyer.setFocusPainted(false);
		this.envoyer.setPreferredSize(new Dimension(300, 30));
		this.envoyer.setBackground(c);
		this.envoyer.addActionListener(this);

		this.add(this.scrollPane, BorderLayout.NORTH);
		this.add(this.champs, BorderLayout.CENTER);
		this.add(this.envoyer, BorderLayout.SOUTH);

		this.afficherMessagesDefaut();
	}

	void nouveauMessage(String message) {
		this.messages.add(new JLabel(message));

		this.messages.revalidate();
		this.messages.repaint();

		this.scrollToBottom(this.scrollPane);
	}

	private void afficherMessagesDefaut() {
		for (String message : this.ihm.getControleur().getMessagesDefaut())
			this.nouveauMessage(message);
	}

	private void scrollToBottom(JScrollPane scrollPane) {
		JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
		AdjustmentListener downScroller = new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				Adjustable adjustable = e.getAdjustable();
				adjustable.setValue(adjustable.getMaximum());
				verticalBar.removeAdjustmentListener(this);
			}
		};

		verticalBar.addAdjustmentListener(downScroller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.ihm.getControleur().envoyerMessage(this.champs.getText());
		this.champs.setText("");
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.ihm.getControleur().envoyerMessage(this.champs.getText());
			this.champs.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
