package attaque;

public abstract class ForceDeCombat {
	private int pointDeDegat;
	private String nom;
	protected boolean operationnel = true;

	public ForceDeCombat(int pointDeDegat, String nom) {
		super();
		this.pointDeDegat = pointDeDegat;
		this.nom = nom;
	}

	public int getPointDeDegat() {
		return pointDeDegat;
	}

	public String getNom() {
		return nom;
	}

	public boolean isOperationnel() {
		return operationnel;
	}

	public String toString() {
		return "ForceDeCombat [pointDeDegat=" + pointDeDegat + ", nom=" + nom + ", operationnel=" + operationnel + "]";
	}

	public int utiliser() {
		return pointDeDegat;
	}

}
