package dessinpartage.ihm;

import javax.swing.*;
import java.awt.*;

class DessinPanel extends JPanel {

	private IHM ihm;

	DessinPanel(IHM ihm) {
		this.ihm = ihm;

		this.preparer();
		this.ihm.add(this, BorderLayout.CENTER);
	}

	private void preparer() {
		this.setPreferredSize(new Dimension(655, 600));
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
	}

}
