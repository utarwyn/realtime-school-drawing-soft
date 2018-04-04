package dessinpartage.ihm;

import dessinpartage.metier.dessin.FormeType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutilsDessinPanel extends JPanel implements ActionListener {

	private static final Color ORIG_COLOR = new Color(220, 220, 220);

	private static final Color HOVER_COLOR = Color.GRAY;

	private IHM ihm;

	private Map<JButton, FormeType> btnFormes;

	private JButton palette;

	private JButton effacer;

	private JLabel tailleLab;

	OutilsDessinPanel(IHM ihm) {
		this.ihm = ihm;
		this.btnFormes = new HashMap<>();

		this.preparer();
		this.ihm.add(this, BorderLayout.WEST);
	}

	public void maj() {
		this.tailleLab.setText(String.format("%.1f", this.ihm.getControleur().getPinceauDessin().getTaille()));
	}

	private void preparer() {
		try {
			this.btnFormes.put(this.ajouterBouton("circle_f"), FormeType.DISQUE);
			this.btnFormes.put(this.ajouterBouton("circle_e"), FormeType.CERCLE);
			this.btnFormes.put(this.ajouterBouton("square_f"), FormeType.CARRE_PLEIN);
			this.btnFormes.put(this.ajouterBouton("square_e"), FormeType.CARRE);

			this.palette = this.ajouterBouton("palette");
			this.effacer = this.ajouterBouton("effacer");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		this.setFormeCourrante(this.btnFormes.entrySet().iterator().next().getKey());

		this.tailleLab = new JLabel("");
		this.add(this.tailleLab);

		this.setPreferredSize(new Dimension(40, 600));
		this.setBackground(ORIG_COLOR);
		this.setOpaque(true);

		this.maj();
	}

	private JButton ajouterBouton(String icon) throws IOException {
		JButton bouton = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/" + icon + ".png"))));

		bouton.setBackground(ORIG_COLOR);
		bouton.setOpaque(true);
		bouton.addActionListener(this);
		bouton.setPreferredSize(new Dimension(40, 40));
		bouton.setFocusPainted(false);
		bouton.setBorder(BorderFactory.createEmptyBorder());
		this.add(bouton);

		return bouton;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton bouton = (JButton) event.getSource();

		if (bouton == this.effacer) {
			this.setFormeCourrante(null);
			this.effacer.setBackground(HOVER_COLOR);
		} else if (bouton == this.palette) {
			JColorChooser colorField = new JColorChooser();

			final JComponent[] inputs = new JComponent[] {
					new JLabel("Choisissez la couleur souhaitée"),
					colorField,
			};
			int result = JOptionPane.showConfirmDialog(null, inputs, "Choix de la couleur", JOptionPane.PLAIN_MESSAGE);

			if (result == JOptionPane.OK_OPTION) {
				this.ihm.getControleur().changerCouleurCourrante(colorField.getColor());
				this.palette.setBackground(colorField.getColor());
			}
		} else {
			this.effacer.setBackground(ORIG_COLOR);
			this.setFormeCourrante(bouton);
		}
	}

	private void setFormeCourrante(JButton courant) {
		for (JButton bouton : this.btnFormes.keySet())
			if (bouton == courant)
				bouton.setBackground(HOVER_COLOR);
			else
				bouton.setBackground(ORIG_COLOR);

		if (courant != null)
			this.ihm.getControleur().changerTypeCourant(this.btnFormes.get(courant));
	}

}
