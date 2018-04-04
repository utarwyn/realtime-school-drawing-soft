package dessinpartage.metier.dessin;

/**
 * Classe pour gérer les types de forme disponibles.
 * @version 1.0.0
 */
public enum FormeType {

	CERCLE(false),
	DISQUE(true),
	CARRE(false),
	CARRE_PLEIN(true);

	private boolean fill;

	FormeType(boolean fill) {
		this.fill = fill;
	}

	public boolean isFillNeeded() {
		return this.fill;
	}

}
