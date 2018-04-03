package dessinpartage.ihm;

import dessinpartage.metier.dessin.Forme;
import dessinpartage.metier.dessin.Pinceau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class DessinPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

	private IHM ihm;

	private Point positionCourante;

	DessinPanel(IHM ihm) {
		this.ihm = ihm;

		this.preparer();
		this.ihm.add(this, BorderLayout.CENTER);
	}

	private void preparer() {
		this.setPreferredSize(new Dimension(655, 600));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Shape shape = null;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.ihm.getOutilsDessinPan().maj();

		List<Forme> formes = this.ihm.getControleur().getFormes();

		// On ajoute une forme temporaire pour savoir ce que l'utilisateur va dessiner
		if (this.positionCourante != null) {
			Pinceau pinceau = this.ihm.getControleur().getPinceauDessin();

			formes.add(new Forme(
					pinceau.getType(), pinceau.getCouleur(),
					positionCourante.getX()-pinceau.getTaille()/2,
					positionCourante.getY()-pinceau.getTaille()/2,
					pinceau.getTaille()
			));
		}

		for (Forme forme : formes) {
			g2.setColor(forme.getColor());

			switch (forme.getType()) {
				case CARRE:
				case CARRE_PLEIN:
					shape = new Rectangle((int) forme.getX(), (int) forme.getY(), (int) forme.getSize(), (int) forme.getSize());
					break;
				case CERCLE:
				case DISQUE:
					shape = new Ellipse2D.Double(forme.getX(), forme.getY(), forme.getSize(), forme.getSize());
					break;
			}

			g2.setStroke(new BasicStroke(3));

			if (forme.getType().isFillNeeded()) g2.fill(shape);
			else g2.draw(shape);

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.ihm.getControleur().creerForme(e.getX(), e.getY());
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.positionCourante = null;
		this.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.ihm.getControleur().creerForme(e.getX(), e.getY());
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.positionCourante = e.getPoint();
		this.repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.ihm.getControleur().incrementerTailleCourante(e.getPreciseWheelRotation());
		this.repaint();
	}

}
