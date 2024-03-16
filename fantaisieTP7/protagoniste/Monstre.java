package protagoniste;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import attaque.Pouvoir;
import bataille.Bataille;

public class Monstre<P extends Pouvoir> extends EtreVivant {
	private ZoneDeCombat zoneDeCombat;
	private Domaine domaine;
	private P attaques[];
	private GestionAttaque gestionAttaque;

	@SafeVarargs
	public Monstre(String nom, int forceDeVie, ZoneDeCombat zoneDeCombat, Domaine domaine, P... attaques) {
		super(nom, forceDeVie);
		this.zoneDeCombat = zoneDeCombat;
		this.domaine = domaine;
		this.attaques = attaques;
	}

	public ZoneDeCombat getZoneDeCombat() {
		return zoneDeCombat;
	}

	public Domaine getDomaine() {
		return domaine;
	}

	public P[] getAttaques() {
		return attaques;
	}

	@Override
	public String toString() {
		return "Monstre [getNom()=" + getNom() + ", attaques=" + Arrays.toString(attaques) + ", zoneDeCombat=" + zoneDeCombat + ", domaine="
				+ domaine + ", getForceDeVie()=" + getForceDeVie() + "]";
	}

	public void entreEnCombat() {
		for (int i = 0; i < attaques.length; i++) {
			attaques[i].regenererPouvoir();
		}
		gestionAttaque = new GestionAttaque();
	}

	public P attaque() {
		if (gestionAttaque.hasNext()) {
			return gestionAttaque.next();
		} else {
			return null;
		}

	}

	public void mourir(Bataille bataille) {
		this.getBataille().eliminer(this);
	}

	private class GestionAttaque implements Iterator<P> {
		private P attaquesPossibles[] = attaques;
		private int nbAttaquesPossibles = attaques.length;

		public boolean hasNext() {
			for (int i = 0; i != nbAttaquesPossibles; i++) {
				if (!attaques[i].isOperationnel()) {
					attaques[i] = attaques[nbAttaquesPossibles - 1];
					nbAttaquesPossibles--;
				}
			}
			if (nbAttaquesPossibles == 0) {
				return false;
			} else {
				return true;
			}
		}

		public P next() {
			Random ran = new Random();
			int ind = ran.nextInt(nbAttaquesPossibles);
			return attaquesPossibles[ind];
		}
	}

	public void rejointBataille(Bataille bataille) {
		super.rejointBataille(bataille);
		this.getBataille().ajouter(this);
	}

}
