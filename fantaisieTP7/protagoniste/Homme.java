package protagoniste;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import attaque.Arme;
import attaque.Pouvoir;
import bataille.Bataille;

public class Homme extends EtreVivant {
	private EnumMap<ZoneDeCombat, List<Arme>> armes = new EnumMap<>(ZoneDeCombat.class);
	private Arme armeChoisie;

	public Homme(String nom) {
		super(nom, 70);
	}

	public Arme getArmeChoisie() {
		return armeChoisie;
	}

	public void rejointBataille(Bataille bataille) {
		super.rejointBataille(bataille);
		this.getBataille().ajouter(this);
	}

	public void mourir(Bataille bataille) {
		bataille.eliminer(this);
	}

	@Override
	public String toString() {
		return "Homme [nom=" + getNom() + ", Force De Vie=" + getForceDeVie() + "]";
	}

	public void ajouterArmes(Arme... armesAj) {
		for (Arme arme : armesAj) {
			Set<ZoneDeCombat> zoneArme = arme.getZonesDeCombat();
			for (ZoneDeCombat zoneDeCombat : zoneArme) {
				List<Arme> liste = this.armes.get(zoneDeCombat);
				if (liste == null) {
					liste = new ArrayList<>();
					this.armes.put(zoneDeCombat, liste);
				}
				liste.add(arme);
			}
		}
	}

	public void supprimerArme(Arme armeSup) {
		for (ZoneDeCombat zone : armeSup.getZonesDeCombat()) {
			if (this.armes.get(zone).contains(armeSup)) {
				this.armes.get(zone).remove(armeSup);
			}
		}
	}

	public Arme choisirArme(Monstre<? extends Pouvoir> monstre) {
		Arme arme;
		if (!this.armes.containsKey(monstre.getZoneDeCombat())) {
			return null;
		}
		List<Arme> armePossible = new ArrayList<>(armes.get(monstre.getZoneDeCombat()));
		NavigableSet<Arme> armesTriees = new TreeSet<>(armePossible);
		NavigableSet<Arme> armesAdaptees = new TreeSet<>();
		
		if(!armesTriees.isEmpty()) {
			armesAdaptees = armesTriees.tailSet(new KeyArme(monstre.getForceDeVie()),false);
		}
		if(!armesAdaptees.isEmpty()) {
			this.armeChoisie = armesAdaptees.first();
			arme = armesAdaptees.first();
		}else {
			this.armeChoisie = armesTriees.last();
			arme = armesTriees.last();
		}
		return arme;
	}

}
