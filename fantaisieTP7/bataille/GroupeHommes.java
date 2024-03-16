package bataille;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

import attaque.Arme;
import attaque.Pouvoir;
import protagoniste.Homme;
import protagoniste.Monstre;

public class GroupeHommes {
	private NavigableSet<Homme> groupe = new TreeSet<>();

	public void ajouterHommes(Homme... hommes) {
		for (Homme homme : hommes) {
			if (!groupe.contains(homme)) {
				groupe.add(homme);
			}
		}
	}

	private class ComparateurHommes implements Comparator<Homme> {
		public int compare(Homme homme1, Homme homme2) {
			int comparaison = homme2.getForceDeVie() - homme1.getForceDeVie();
			if (comparaison == 0) {
				comparaison = homme1.compareTo(homme2);
			}
			return comparaison;
		}
	}

	private class ComparateurArmes implements Comparator<Arme> {
		private Monstre<? extends Pouvoir> monstre;

		public ComparateurArmes(Monstre<? extends Pouvoir> monstre) {
			super();
			this.monstre = monstre;
		}

		public int compare(Arme arme1, Arme arme2) {
			int comparaison;
			int forceDeVie = monstre.getForceDeVie();
			int degats1 = arme1.getPointDeDegat();
			int degats2 = arme2.getPointDeDegat();
			if (degats1 != degats2) {
				NavigableMap<Integer, Arme> classementForce = new TreeMap<>();
				classementForce.put(degats1, arme1);
				classementForce.put(degats2, arme2);
				Entry<Integer, Arme> meilleureArme = classementForce.floorEntry(forceDeVie);
				if (meilleureArme == null) {
					meilleureArme = classementForce.ceilingEntry(forceDeVie);
				}
				if (meilleureArme.getValue().equals(arme2)) {
					comparaison = 1;
				} else {
					comparaison = -1;
				}
			} else {
				comparaison = arme1.compareTo(arme2);
			}
			return comparaison;
		}
	}

	public List<Homme> choixParticipants(Bataille bataille) {
		bataille.reset();
		List<Homme> hommeChoisis = new ArrayList<>();
		Monstre<? extends Pouvoir> monstre = bataille.getCampMonstres().selectionner();
		NavigableMap<Arme, TreeSet<Homme>> hommesArmes = new TreeMap<>(new ComparateurArmes(monstre));
		for (Homme homme : groupe) {
			homme.choisirArme(monstre);
			if (homme.getArmeChoisie() != null) {
				if (!hommesArmes.containsKey(homme.getArmeChoisie())) {
					hommesArmes.put(homme.getArmeChoisie(), new TreeSet<>(((homme1, homme2) -> {
						if (homme2.getForceDeVie() - homme1.getForceDeVie() == 0) {
							return homme1.compareTo(homme2);
						} else {
							return homme2.getForceDeVie() - homme1.getForceDeVie();
						}
					})));
				}
				hommesArmes.get(homme.getArmeChoisie()).add(homme);
			}
		}
		for (Arme arme : hommesArmes.keySet()) {
			Iterator<Homme> iterateur = hommesArmes.get(arme).iterator();
			while (iterateur.hasNext() && bataille.getCampHumains().nbCombattants() < 3) {
				Homme homme = iterateur.next();
				bataille.ajouter(homme);
				hommeChoisis.add(homme);
			}
		}
		return hommeChoisis;
	}
}
