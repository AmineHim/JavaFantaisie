package livre;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NavigableSet;
import java.util.TreeSet;

import attaque.PicsDeGlace;
import attaque.Pouvoir;
import bataille.Bataille;
import protagoniste.Homme;
import protagoniste.Monstre;
import protagoniste.ZoneDeCombat;
import attaque.Morsure;
import protagoniste.Domaine;
import protagoniste.Heros;

public class AideEcrivain {
	private Bataille bataille;
	private NavigableSet<Monstre<? extends Pouvoir>> monstresDomaineSet;
	private NavigableSet<Monstre<? extends Pouvoir>> monstresZoneSet;
	private NavigableSet<Monstre<? extends Pouvoir>> monstresDeFeu;
	private NavigableSet<Monstre<? extends Pouvoir>> monstresDeGlace;
	private NavigableSet<Monstre<? extends Pouvoir>> monstresTranchants;

	public AideEcrivain(Bataille bataille) {
		this.bataille = bataille;
	}

	public NavigableSet<Monstre<? extends Pouvoir>> getMonstresDeFeu() {
		initMonstresDeFeu();
		return monstresDeFeu;
	}

	public NavigableSet<Monstre<? extends Pouvoir>> getMonstresDeGlace() {
		initMonstresDeGlace();
		return monstresDeGlace;
	}

	public NavigableSet<Monstre<? extends Pouvoir>> getMonstresTranchants() {
		initMonstresTranchant();
		return monstresTranchants;
	}

	public String visualiserForcesHumaines() {
		List<Homme> listeTriee = new LinkedList<>();
		for (Homme humain : bataille.getCampHumains()) {
			if (listeTriee.isEmpty()) {
				listeTriee.add(humain);
			} else {
				if (humain instanceof Heros) {
					sousFoncVisualiserForcesHumaines(listeTriee, humain);
				} else {
					listeTriee.add(humain);
				}
			}
		}
		return listeTriee.toString();
	}

	private void sousFoncVisualiserForcesHumaines(List<Homme> listeTriee, Homme homme) {
		for (ListIterator<Homme> iterator = listeTriee.listIterator(); iterator.hasNext();) {
			if (iterator.next().getClass() == Homme.class) {
				iterator.previous();
				iterator.add(homme);
				break;
			}
		}
	}

	public String ordreNaturelMonstre() {
		String retour = "";
		NavigableSet<Monstre<? extends Pouvoir>> ordreNaturel = new TreeSet<>();
		for (Monstre<? extends Pouvoir> monstre : bataille.getCampMonstres()) {
			ordreNaturel.add(monstre);
		}
		for (Monstre<? extends Pouvoir> monstre : ordreNaturel) {
			retour += monstre.getNom() + ", ";
		}
		return retour;
	}

	public void updateMonstresDomaine() {
		monstresDomaineSet = new TreeSet<>(new Comparator<Monstre<? extends Pouvoir>>() {
			public int compare(Monstre<? extends Pouvoir> monstre1, Monstre<? extends Pouvoir> monstre2) {
				Domaine domaineMonstre1 = monstre1.getDomaine();
				Domaine domaineMonstre2 = monstre2.getDomaine();
				int comparaison = domaineMonstre1.compareTo(domaineMonstre2);
				if (comparaison == 0) {
					comparaison = monstre1.compareTo(monstre2);
				}
				return comparaison;
			}
		});
		for (Monstre<? extends Pouvoir> monstre : bataille.getCampMonstres()) {
			monstresDomaineSet.add(monstre);
		}
	}

	public String ordreMonstreDomaine() {
		updateMonstresDomaine();
		String retour = "FEU :\n";
		retour += stringSetDomaine(monstresDomaineSet.headSet(new Monstre<>("a", 0, null, Domaine.GLACE), false));
		retour += "\nGLACE :\n";
		retour += stringSetDomaine(monstresDomaineSet.subSet(new Monstre<>("a", 0, null, Domaine.GLACE), true,
				new Monstre<>("a", 0, null, Domaine.TRANCHANT), false));
		retour += "\nTRANCHANT :\n";
		retour += stringSetDomaine(monstresDomaineSet.tailSet(new Monstre<>("a", 0, null, Domaine.TRANCHANT), true));
		return retour;
	}

	private String stringSetDomaine(NavigableSet<Monstre<?>> setTemp) {
		String retour = "";
		for (Monstre<?> mstr : setTemp) {
			retour += mstr.getNom() + ", ";
		}
		return retour;
	}

	public void updateMonstresZone() {
		monstresZoneSet = new TreeSet<Monstre<?>>(new Comparator<Monstre<?>>() {
			public int compare(Monstre<? extends Pouvoir> monstre1, Monstre<? extends Pouvoir> monstre2) {
				int comparaison = monstre1.getZoneDeCombat().compareTo(monstre2.getZoneDeCombat());
				if (comparaison == 0) {
					comparaison = monstre2.getForceDeVie() - monstre1.getForceDeVie();
				}
				if (comparaison == 0) {
					comparaison = monstre1.compareTo(monstre2);
				}
				return comparaison;
			}
		});
		for (Monstre<?> mstr : bataille.getCampMonstres()) {
			monstresZoneSet.add(mstr);
		}
	}

	public String ordreMonstreZone() {
		updateMonstresZone();
		String retour = "AERIEN :\n";
		retour += stringSetZone(monstresZoneSet.headSet(new Monstre<>("a", 200, ZoneDeCombat.AQUATIQUE, null), false));
		retour += "\nAQUATIQUE :\n";
		retour += stringSetZone(monstresZoneSet.subSet(new Monstre<>("a", 200, ZoneDeCombat.AQUATIQUE, null), true,
				new Monstre<>("a", 200, ZoneDeCombat.TERRESTRE, null), false));
		retour += "\nTERRESTRE :\n";
		retour += stringSetZone(monstresZoneSet.tailSet(new Monstre<>("a", 200, ZoneDeCombat.TERRESTRE, null), true));
		return retour;
	}

	private String stringSetZone(NavigableSet<Monstre<? extends Pouvoir>> setTemp) {
		String retour = "";
		for (Monstre<?> mstr : setTemp) {
			retour += mstr.getNom() + " : " + mstr.getForceDeVie() + ", ";
		}
		return retour;
	}

//	public Monstre<? extends Pouvoir> firstMonstreDomaine(Domaine domaine) {
//		updateMonstresDomaine();
//		for (Monstre<? extends Pouvoir> monstre : monstresDomaineSet) {
//			if (monstre.getDomaine().equals(domaine)) {
//				return monstre;
//			}
//		}
//		return null;
//	}

//	public void initMonstresDeFeu() {
//		Monstre<? extends Pouvoir> monstre = firstMonstreDomaine(Domaine.GLACE);
//		monstresDeFeu = monstresDomaineSet.headSet(monstre, false);
//	}

	public void initMonstresDeFeu() {
		updateMonstresDomaine();
		monstresDeFeu = monstresDomaineSet.headSet(new Monstre<>("", 0, null, Domaine.GLACE, new PicsDeGlace(0)),
				false);
	}

	public void initMonstresDeGlace() {
		updateMonstresDomaine();
		monstresDeGlace = monstresDomaineSet.subSet(new Monstre<>("", 0, null, Domaine.GLACE, new PicsDeGlace(0)), true,
				new Monstre<>("", 0, null, Domaine.TRANCHANT, new Morsure(0)), false);
	}

	public void initMonstresTranchant() {
		updateMonstresDomaine();
		monstresTranchants = monstresDomaineSet.tailSet(new Monstre<>("", 0, null, Domaine.TRANCHANT, new Morsure(0)),
				true);
	}

}
