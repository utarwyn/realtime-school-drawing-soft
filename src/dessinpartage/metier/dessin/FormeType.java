package dessinpartage.metier.dessin;

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
