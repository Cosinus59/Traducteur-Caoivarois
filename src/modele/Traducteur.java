package modele;

import java.io.IOException;
import java.util.ArrayList;

import vue.callahan.ControlleurTraducteur;

public class Traducteur {

	ArrayList<ItemPickup> itemList = new ArrayList<ItemPickup>();
	
	ControlleurTraducteur display;
	
	FileManager fm;
	ControlleurTraducteur cont;
	public Traducteur() throws IOException {
		fm = new FileManager(this);
		itemList = fm.loadInternBlueprint();
	}

	public ArrayList<ItemPickup> getItemList() {
		return itemList;
	}
	
}
