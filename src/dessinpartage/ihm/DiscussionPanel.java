package dessinpartage.ihm;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;

class DiscussionPanel extends JPanel implements ActionListener, KeyListener {

	private IHM ihm;

	private JPanel messages;

	private JScrollPane scrollPane;

	private JTextField champs;

	private JButton envoyer;

	DiscussionPanel(IHM ihm) {
		this.ihm = ihm;

		this.preparer();
		this.ihm.add(this, BorderLayout.EAST);
	}

	private void preparer() {
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(189, 195, 199));
		this.setPreferredSize(new Dimension(300, 600));
		this.setOpaque(true);

		// Définition des éléments
		this.messages = new JPanel();
		this.messages.setLayout(new BoxLayout(this.messages, BoxLayout.Y_AXIS));
		this.messages.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.messages.setBackground(new Color(250, 250, 250));
		this.messages.setOpaque(true);

		this.scrollPane = new JScrollPane(this.messages);
		scrollPane.setPreferredSize(new Dimension(300, 500));
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setHorizontalScrollBar(null);
		scrollPane.setOpaque(false);

		this.champs = new JTextField();
		this.envoyer = new JButton("Envoyer");

		this.champs.setBorder(new CompoundBorder(
				BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
				BorderFactory.createEmptyBorder(0, 15, 0, 15)
		));
		this.champs.addKeyListener(this);

		this.envoyer.setBackground(new Color(149, 165, 166));
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
