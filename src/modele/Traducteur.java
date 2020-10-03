package modele;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vue.callahan.ControlleurTraducteur;

public class Traducteur {

	ObservableList<ItemPickup> itemObservableList = FXCollections.observableArrayList();
	
	ControlleurTraducteur display;
	
	FileManager fm;
	ControlleurTraducteur cont;
	public Traducteur() throws IOException {
		fm = new FileManager(this);
		itemObservableList = fm.loadInternBlueprint();
	}

	public ObservableList<ItemPickup> getItemObservableList() {
		return itemObservableList;
	}
	
	
}
