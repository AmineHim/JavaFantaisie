package bataille;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import protagoniste.Monstre;
import attaque.Pouvoir;

import protagoniste.ZoneDeCombat;
import protagoniste.ZoneDeCombatNonCompatibleException;

public class Grotte {
	private LinkedHashMap<Salle, ArrayList<Salle>> planGrotte = new LinkedHashMap<>();
	private HashMap<Salle, Bataille> batailles = new HashMap<>();
	private HashSet<Salle> sallesExplorees = new HashSet<>();
	private int numeroSalleDecisive;

	public void setNumeroSalleDecisive(int numeroSalleDecisive) {
		this.numeroSalleDecisive = numeroSalleDecisive;
	}
	
	/*
	public void leverException() throws ZoneDeCombatNonCompatibleException {
		throw new ZoneDeCombatNonCompatibleException("probleme");
    }*/
	
	public void ajouterSalle(ZoneDeCombat zoneDeCombat, Monstre<? extends Pouvoir>... monstres){
		

		/*for(Monstre<? extends Pouvoir> monstreAT : monstres) {
			if(zoneDeCombat!=monstreAT.getZoneDeCombat()) {
				throw new ZoneDeCombatNonCompatibleException();
				//throw de la class pour excpetion
			}*/
			/*if(zoneDeCombat!=monstreAT.getZoneDeCombat()) {
				try {
					this.leverException();
					}catch (ZoneDeCombatNonCompatibleException me) {System.out.println("mince");
				//ZoneDeCombatNonCompatibleException exception = new ZoneDeCombatNonCompatibleException("probleme");
			 }*/
		
		Salle salle = new Salle(zoneDeCombat, planGrotte.size() + 1);
		Bataille bataille = new Bataille();
		for (Monstre<? extends Pouvoir> monstre : monstres) {
			monstre.rejointBataille(bataille);
		}
		planGrotte.put(salle, new ArrayList<Salle>());
		batailles.put(salle, bataille);
	}

	public String afficherPlanGrotte() {
		StringBuilder affichage = new StringBuilder();
		for (Map.Entry<Salle, ArrayList<Salle>> entry : planGrotte.entrySet()) {
			Salle salle = entry.getKey();
			List<Salle> acces = planGrotte.get(salle);
			affichage.append("La " + salle + ".\nelle possede " + acces.size() + " acces : ");
			for (Salle access : acces) {
				affichage.append(" vers la salle " + access);
			}
			Bataille bataille = batailles.get(salle);
			Camp<Monstre<?>> camp = bataille.getCampMonstres();
			Monstre<?> monstre = camp.selectionner();
			if (camp.nbCombattants() > 1) {
				affichage.append("\n" + camp.nbCombattants() + " monstres de type ");
			} else {
				affichage.append("\nUn monstre de type ");
			}
			affichage.append(monstre.getNom() + " la protege.\n");
			if (salle.getNumSalle() == numeroSalleDecisive) {
				affichage.append("C'est dans cette salle que se trouve la pierre de sang.\n");
			}
			affichage.append("\n");
		}
		return affichage.toString();
	}

	public void configurerAcces(int numSalleOrigine, int... numSallesAccessibles) {
		Salle salleOrigine = trouverSalle(numSalleOrigine);
		ArrayList<Salle> sallesAccessibles = new ArrayList<>();
		for (int i : numSallesAccessibles) {
			sallesAccessibles.add(trouverSalle(i));
		}
		planGrotte.put(salleOrigine, sallesAccessibles);
	}

	private Salle trouverSalle(int numeroSalle) {
		int indice = 1;
		Set<Salle> salles = planGrotte.keySet();
		for (Salle salle : salles) {
			indice += 1;
			if (salle.getNumSalle() == numeroSalle) {
				return salle;
			}
		}
		return null;
	}

	public boolean salleDecisive(Salle salle) {
		return salle.getNumSalle() == numeroSalleDecisive;
	}

	public Salle premiereSalle() {
		Salle premiereSalle = trouverSalle(1);
		sallesExplorees.add(premiereSalle);
		return premiereSalle;
	}

	public Salle salleSuivante(Salle salleActuelle) {
		List<Salle> salleAcces = new ArrayList<>(planGrotte.get(salleActuelle));
		Salle nouvelleSalle;
		salleAcces.removeAll(salleAcces);
		if (salleAcces.isEmpty()) {
			salleAcces = planGrotte.get(salleActuelle);
		}
		nouvelleSalle = salleAcces.get(new Random().nextInt(salleAcces.size()));
		sallesExplorees.add(nouvelleSalle);
		return nouvelleSalle;

	}

}
