package modele;

import java.io.Serializable;
import java.util.ArrayList;

import filesManagment.FileManager;

@SuppressWarnings("serial")
public class Traduction implements Serializable {

	ArrayList<ItemPickup> itemList;
	
	String name;
	
	public Traduction() {
		itemList = new ArrayList<ItemPickup>();
	}

	public boolean loadIntern() {
		itemList = FileManager.getInstance().loadInternBlueprint();
		return true;
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

	public static Traduction toWrite(Traduction currentTrad) {
		Traduction res = new Traduction();
		res.name = currentTrad.name;
		for (ItemPickup entree : currentTrad.getItemList()) {
			if(entree.isTtlTrnsltd()||entree.isDscTrnsltd()) {
				res.itemList.add(entree);
			}
		}
		return res;
	}

	public static Traduction completeTrad(Traduction traduction) {
		Traduction res = new Traduction();
		res.name = traduction.name;
		res.loadIntern();
		int idx = 0;
		int max = traduction.itemList.size();
		for (int i = 0;i<res.itemList.size();++i) {
			ItemPickup entree = res.itemList.get(i);
			if(idx<max&&entree.getName().equals(traduction.itemList.get(idx).getName())) {
				res.itemList.set(i, traduction.itemList.get(idx));
				idx++;
			}
		}
		return res;
	}
	
}
