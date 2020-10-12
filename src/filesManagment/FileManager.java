package filesManagment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import modele.ItemPickup;
import modele.Traducteur;

public class FileManager {
	
	@SuppressWarnings("unused")
	private Traducteur trad;
	
	public FileManager(Traducteur traducteur) {
		trad = traducteur;
	}
	
	
	public static String readFile(File file) { // return une String du fichier 
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
	
	public static ArrayList<ItemPickup> loadInternBlueprint() {
		ArrayList<ItemPickup> itemList = new ArrayList<ItemPickup>();
		File internItemPickup = new File(Main.getInternContentFolder()+File.separator+"Blueprints"+File.separator+"ItemPickups");
		loadInternItem(internItemPickup,itemList);
		return itemList;
	}
	
	public static void loadInternItem(File internItemPickup,ArrayList<ItemPickup> itemList) {
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
				itemList.add(item);
			}
		}
	}
	
	private static String removeFullPath(String path) {
		int debut = path.lastIndexOf(File.separator+"Content"+File.separator);
		return path.substring(debut+9);
	}

	public static String removeUasset(String toRemove){
		String res = toRemove.replaceAll(".uasset", "");
		return res;
	}
	
}
