package protagoniste;

import bataille.Bataille;

public abstract class EtreVivant implements Comparable<EtreVivant> {
	protected String nom;
	protected int forceDeVie;
	private Bataille bataille;

	public EtreVivant(String nom, int forceDeVie) {
		super();
		this.nom = nom;
		this.forceDeVie = forceDeVie;
	}

	public String getNom() {
		return nom;
	}

	public int getForceDeVie() {
		return forceDeVie;
	}
	
	public Bataille getBataille() {
		return bataille;
	}

	public String toString() {
		return "EtreVivant [nom=" + nom + ", forceDeVie=" + forceDeVie + "]";
	}

	public void rejointBataille(Bataille bataille) {
		this.bataille = bataille;
	}
	
	public abstract void mourir(Bataille bataille);
	
	public boolean equals(EtreVivant etreVivant) {
		return this.nom.equals(etreVivant.nom);
	}
	
	public int compareTo(EtreVivant etreVivantToCompare) {
		return nom.compareTo(etreVivantToCompare.nom);
	}

}
