package modele;

import java.util.ArrayList;

import filesManagment.Serializer;

public class Traducteur {

	ArrayList<Traduction> traductions;
	
	public Traducteur() {
		traductions = new ArrayList<Traduction>();
	}

	public ArrayList<Traduction> getTraductions() {
		return traductions;
	}
	
	public void getSeralizedTrad() {
		for (Traduction traduction : Serializer.getTrads()) {
			addTraduction(Traduction.completeTrad(traduction));
		}
	}
	
	public void addTraduction(Traduction trad) {
		traductions.add(trad);
	}

	public void writTrad(Traduction currentTrad) {
		Traduction toWrite = Traduction.toWrite(currentTrad);
		Serializer.writeTrad(toWrite);
	}
}
