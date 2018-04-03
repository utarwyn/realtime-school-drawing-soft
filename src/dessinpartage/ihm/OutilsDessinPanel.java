package dessinpartage.ihm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OutilsDessinPanel extends JPanel implements ActionListener {

	private IHM ihm;

	private JButton circleF, circleE, squareF, squareE;

	private JButton palette;

	private JButton effacer;

	OutilsDessinPanel(IHM ihm) {
		this.ihm = ihm;

		this.preparer();
		this.ihm.add(this, BorderLayout.WEST);
	}

	private void preparer() {
		try {
			this.circleF = this.ajouterBouton("circle_f");
			this.circleE = this.ajouterBouton("circle_e");
			this.squareF = this.ajouterBouton("square_f");
			this.squareE = this.ajouterBouton("square_e");
			this.palette = this.ajouterBouton("palette");

			this.effacer = this.ajouterBouton("effacer");
			this.effacer.setEnabled(false);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		this.setPreferredSize(new Dimension(40, 600));
		this.setBackground(new Color(220, 220, 220));
		this.setOpaque(true);
	}

	private JButton ajouterBouton(String icon) throws IOException {
		JButton bouton = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/" + icon + ".png"))));

		bouton.setBackground(new Color(220, 220, 220));
		bouton.setOpaque(true);
		bouton.addActionListener(this);
		bouton.setPreferredSize(new Dimension(40, 40));
		bouton.setBorder(BorderFactory.createEmptyBorder());
		this.add(bouton);

		return bouton;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton bouton = (JButton) event.getSource();

		if (bouton == this.effacer) {

		} else if (bouton == this.palette) {
			JColorChooser colorField = new JColorChooser();

			final JComponent[] inputs = new JComponent[] {
					new JLabel("Choisissez la couleur souhaitée"),
					colorField,
			};
			int result = JOptionPane.showConfirmDialog(null, inputs, "Choix de la couleur", JOptionPane.PLAIN_MESSAGE);

			if (result == JOptionPane.OK_OPTION)
				System.out.println(colorField.getColor().toString());
		} else {

		}
	}

}
