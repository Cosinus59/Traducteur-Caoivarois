package modele;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import filesManagment.FileManager;

@SuppressWarnings("serial")
public class Traducteur implements Serializable {

	ArrayList<ItemPickup> itemList = new ArrayList<ItemPickup>();
	
	String name;
	
	public Traducteur() throws IOException {
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
	
}
