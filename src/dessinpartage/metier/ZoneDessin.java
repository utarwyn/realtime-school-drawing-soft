package dessinpartage.metier;

import dessinpartage.Controleur;
import dessinpartage.metier.dessin.Forme;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ZoneDessin {

	private Controleur controleur;

	private Color couleurCourante;

	private List<Forme> formes;

	public ZoneDessin(Controleur controleur) {
		this.controleur = controleur;
		this.couleurCourante = Color.BLACK;
		this.formes = new ArrayList<>();
	}

}
