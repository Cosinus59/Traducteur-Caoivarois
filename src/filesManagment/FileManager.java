package filesManagment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import modele.ItemPickup;
import modele.Traduction;

public class FileManager {
	
	private static FileManager instance;
	
	private FileManager() {}
	
	public static FileManager getInstance() {
		if(instance==null)instance = new FileManager();
		return instance;
	}
	
	public String readFile(File file) { // return une String du fichier 
		StringBuilder sb = new StringBuilder();
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			
			for (line = br.readLine(); line != null; line = br.readLine()) {
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
			}

			sb.deleteCharAt(sb.length()-1);
			return sb.toString();
		} catch (FileNotFoundException e) {
			System.out.println("le fichier "+file.getPath()+" n'existe pas");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeFile(File f,String content) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
			bw.write(content);
		} catch (FileNotFoundException e) {
			System.out.println("le fichier "+f.getPath()+" n'a pas pu être écris");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void writeFiles(Traduction trad) {
		File internItemPickup = new File(Main.getInternContentFolder()+File.separator+"Blueprints"+File.separator+"ItemPickups");
		File warFolder = new File(Main.getInternPakFolder()+File.separator+"War");
		
		
		File pakFolder = new File(Main.getInternPakFolder()+File.separator+"War"+File.separator+"Content"+File.separator+"Blueprints"+File.separator+"ItemPickups");
		File files[] = internItemPickup.listFiles();
		
		warFolder.delete();
		warFolder.mkdir();
		
		pakFolder.delete();
		pakFolder.mkdirs();
		
		replaceContent(trad, pakFolder, files);
		
	}

	private void replaceContent(Traduction trad, File pakFolder, File[] files) {
		int idx = 0;
		
		ItemPickup temp = trad.getItemList().get(idx);
		String content;
		File translated;
		for (File f : files) {
			if(temp.getName().equals(removeUasset(removeFullPath(f.getPath())))) {
				content = readFile(f);
				if(temp.isTtlTrnsltd()) {
					replaceTitile(temp, content);
				}
				if(temp.isDscTrnsltd()) {
					replaceDesc(temp, content);
				}
				translated = new File(pakFolder.getPath()+temp.getName()+".uasset");
				writeFile(translated, content);
				temp = trad.getItemList().get(++idx);
			}
		}
	}

	private void replaceDesc(ItemPickup temp, String content) {
		StringBuilder translation = new StringBuilder();
		String deb;
		String fin;
		deb = content.substring(0, temp.getDescPos());
		fin = content.substring(temp.getDescPos()+temp.getDescSize());
		translation.append(temp.getTradDesc());
		for(int i = translation.length();i<temp.getDescSize();++i) {
			translation.append(' ');
		}
		content = deb+translation.toString()+fin;
	}

	private void replaceTitile(ItemPickup temp, String content) {
		StringBuilder translation = new StringBuilder();
		String deb;
		String fin;
		deb = content.substring(0, temp.getTitlePos());
		fin = content.substring(temp.getTitlePos()+temp.getTitleSize());
		translation.append(temp.getTradTitle());
		for(int i = translation.length();i<temp.getTitleSize();++i) {
			translation.append(' ');
		}
		content = deb+translation.toString()+fin;
	}
	
	public File getResource(String fileName) {
		File file = new File(fileName);
		return file;
	}
	
	public ArrayList<ItemPickup> loadInternBlueprint() {
		ArrayList<ItemPickup> itemList = new ArrayList<ItemPickup>();
		File internItemPickup = new File(Main.getInternContentFolder()+File.separator+"Blueprints"+File.separator+"ItemPickups");
		loadInternItem(internItemPickup,itemList);
		return itemList;
	}
	
	public void loadInternItem(File internItemPickup,ArrayList<ItemPickup> itemList) {
		File files[] = internItemPickup.listFiles();
		for (File f : files) {
			
			if(f.isDirectory());
			else {
				ItemPickup item = new ItemPickup(removeUasset(f.getName()),removeFullPath(removeUasset(f.getPath())));
				
				item.setContent(readFile(f));
				itemList.add(item);
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
