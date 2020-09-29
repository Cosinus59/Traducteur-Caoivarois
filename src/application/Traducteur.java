package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Traducteur {

	ObservableList<ItemPickup> itemObservableList = FXCollections.observableArrayList();
	
	ControlleurTraducteur display;
	
	FileManager fm;
	ControlleurTraducteur cont;
	public Traducteur() throws IOException {
		fm = new FileManager(this);
		itemObservableList = fm.loadInternBlueprint();
	}
	
	public void test() {
		ItemPickup bp1 = new ItemPickup("ATAmmo","ATAmmo.uasset");
    	bp1.setTitle("Munition antiChar");
    	bp1.setDesc("Voila Voila");
    	
    	ItemPickup bp2 = new ItemPickup("RifleAmmo","RifleAmmo.uasset");
    	bp2.setTitle("9.80 mm");
    	bp2.setDesc("Voili Voilou");
    	
    	ObservableList<ItemPickup> itemObservableList = FXCollections.observableArrayList();
    	itemObservableList.addAll(bp1,bp2);
    	display.addAll(itemObservableList);
	}
	
	
}
