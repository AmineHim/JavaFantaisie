package attaque;

import java.util.HashSet;
import java.util.Set;

import protagoniste.ZoneDeCombat;

public abstract class Arme extends ForceDeCombat implements Comparable<Arme> {
	private Set<ZoneDeCombat> zonesDeCombat = new HashSet<>();

	public Arme(int pointDeDegat, String nom, ZoneDeCombat... zonesDeCombat) {
		super(pointDeDegat, nom);
		for (ZoneDeCombat zoneDeCombat : zonesDeCombat) {
			this.zonesDeCombat.add(zoneDeCombat);
		}
	}

	public Set<ZoneDeCombat> getZonesDeCombat() {
		return zonesDeCombat;
	}

	public int compareTo(Arme ArmeToCompare) {
		int comparaison = ((Boolean) this.operationnel).compareTo((Boolean) ArmeToCompare.operationnel);

		if (comparaison == 0) {
			comparaison = ArmeToCompare.getPointDeDegat() - this.getPointDeDegat();
		}
		if (comparaison == 0) {
			comparaison = this.getNom().compareTo(ArmeToCompare.getNom());
		}
		return comparaison;
	}
}
