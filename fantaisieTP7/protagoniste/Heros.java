package protagoniste;


public class Heros extends Homme {

	public Heros(String nom) {
		super(nom);
		this.forceDeVie = 100;
	}

	@Override
	public String toString() {
		return "Heros [nom=" + getNom() + ", Force De Vie=" + getForceDeVie() + "]";
	}

	/*public void rejoindreBataille(Bataille bataille) {
		bataille.ajouter(this);
	}*/

}
