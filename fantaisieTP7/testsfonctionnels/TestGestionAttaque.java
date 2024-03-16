package testsfonctionnels;

import java.util.Arrays;

import attaque.*;
import protagoniste.*;

public class TestGestionAttaque {

	public static void main(String[] args) {
		final Monstre<Pouvoir> dragotenebre = new Monstre<>("DragoTenebre", 200, ZoneDeCombat.AERIEN, Domaine.FEU,
				new Pouvoir[] { new Lave(2), new Eclair(1), new BouleDeFeu(3) });
		System.out.println(dragotenebre.toString());
		dragotenebre.entreEnCombat();
		dragotenebre.attaque().utiliser();
		dragotenebre.attaque().utiliser();
		dragotenebre.attaque().utiliser();
		dragotenebre.attaque().utiliser();
		System.out.println(dragotenebre.toString());
		System.out.println(Arrays.toString(dragotenebre.getAttaques()));
	}

}
