package modele;

import java.io.Serializable;
import java.util.ArrayList;

import filesManagment.FileManager;

@SuppressWarnings("serial")
public class Traduction implements Serializable {

	ArrayList<ItemPickup> itemList = new ArrayList<ItemPickup>();
	
	String name;
	
	public Traduction() {
		itemList = FileManager.loadInternBlueprint();
	}

	public ArrayList<ItemPickup> getItemList() {
		return itemList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "Nom: "+name+" Nb d'item: "+itemList.size();
	}
	
}
