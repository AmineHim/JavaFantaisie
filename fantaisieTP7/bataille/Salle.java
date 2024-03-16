package bataille;

import protagoniste.ZoneDeCombat;

public class Salle {
	private ZoneDeCombat zoneDeCombat;
	private int numSalle;

	public Salle(ZoneDeCombat zoneDeCombat, int numSalle) {
		super();
		this.zoneDeCombat = zoneDeCombat;
		this.numSalle = numSalle;
	}

	public ZoneDeCombat getZoneDeCombat() {
		return zoneDeCombat;
	}

	public int getNumSalle() {
		return numSalle;
	}

	@Override
	public String toString() {
		return "Salle n°" + numSalle + " de type combat " + zoneDeCombat;
	}
	

}
