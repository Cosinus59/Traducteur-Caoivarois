package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileManager {
	
	@SuppressWarnings("unused")
	private Traducteur trad;
	
	public FileManager(Traducteur traducteur) {
		trad = traducteur;
	}
	
	
	public String readFile(File file) { // return une String du fichier 
		try {
        	FileReader fr;
        	fr = new FileReader(file);
		
			BufferedReader br = new BufferedReader(fr);
			StringBuilder sb = new StringBuilder();
        	for (String line = br.readLine(); line != null; line = br.readLine()) {
            	sb.append(line);
            	sb.append(System.getProperty("line.separator"));
        	}

        	br.close();
        	fr.close();
        	sb.deleteCharAt(sb.length()-1);
        	return sb.toString();
		} catch (FileNotFoundException e) {
			System.out.println("le fichier "+file.getPath()+" n'existe pas");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public File getResource(String fileName) {
		File file = new File(fileName);
		return file;
	}
	
	public ObservableList<ItemPickup> loadInternBlueprint() {
		ObservableList<ItemPickup> itemObservableList = FXCollections.observableArrayList();
		File internItemPickup = new File(Main.getInternContentFolder()+File.separator+"Blueprints"+File.separator+"ItemPickups");
		loadInternItem(internItemPickup,itemObservableList);
		return itemObservableList;
	}
	
	public void loadInternItem(File internItemPickup,ObservableList<ItemPickup> itemObservableList) {
		File files[] = internItemPickup.listFiles();
		for (File f : files) {
			//System.out.println(f);
			//System.out.println(dest);
			if(f.isDirectory());
			else {
				ItemPickup item = new ItemPickup(removeUasset(f.getName()),removeFullPath(f.getPath()));
				//System.out.println(item.getName());
				//System.out.println(f.getAbsolutePath());
				item.setContent(readFile(f));
				itemObservableList.add(item);
			}
		}
	}
	
	private String removeFullPath(String path) {
		int debut = path.lastIndexOf(File.separator+"Content"+File.separator);
		return path.substring(debut+9);
	}

	public String removeUasset(String toRemove){
		String res = toRemove.replaceAll(".uasset", "");
		return res;
	}
	
}
