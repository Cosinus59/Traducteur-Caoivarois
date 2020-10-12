package modele;

import java.util.ArrayList;

import filesManagment.Serializer;

public class Traducteur {

	ArrayList<Traduction> traductions;
	
	public Traducteur() {
		traductions = Serializer.getTrads();
	}

	public ArrayList<Traduction> getTraductions() {
		return traductions;
	}
	
	public void addTraduction(Traduction trad) {
		traductions.add(trad);
	}
}
