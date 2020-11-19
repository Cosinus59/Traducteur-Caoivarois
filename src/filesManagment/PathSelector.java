package filesManagment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

import application.Main;

public class PathSelector {

	
	public void pathSelector() { //sert a choisir des données externe
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		JFileChooser chooser = new JFileChooser();
	 	chooser.setDialogTitle("Selectionnez le dossier Content");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int returnVal = chooser.showOpenDialog(chooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("Voici le chemin du \\War: " + chooser.getSelectedFile().getPath());
			Main.setExternContentPath(chooser.getSelectedFile().getPath());
		}
		try {
			copyTree();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void copyTree() throws IOException { //remplace les données internes par les externes
		
		File dest;
		String res = Main.getFolderPath().getPath()+File.separator+"data"+File.separator+"War"+File.separator+"Content"+File.separator+"Blueprints";
		
		dest = new File(res);
		dest.mkdirs();
		File src = new File(Main.getExternContentPath()+File.separator+"Content"+File.separator+"Blueprints");
		copyFile(src,dest);
		removeEmpty(dest);
		
		Files.newDirectoryStream(Paths.get(dest.getPath())).forEach(System.out::println);
		
	}
	
	public void copyFile( File from, File to ) throws IOException { //sert a copié un dossier vers un autre
		System.out.println(from);                                    //tout en chckant la validité
		File files[] = from.listFiles();
		for (File f : files) {
			//System.out.println(f);
			File dest = new File(to.getPath()+File.separator+f.getName());
			//System.out.println(dest);
			if(f.isDirectory()) {
				//if(hasChild(f)) {
					dest.mkdirs();
					copyFile(f,dest);
				//}
			}
			else if(itemCheck(f)) {
				//System.out.println(f);
				if(dest.exists())dest.delete();
				Files.copy(f.toPath(),dest.toPath());
			}
		}
	}
	
	public boolean hasChild(File f) {
		File files[] = f.listFiles();
		if(files.length==0)return false;
		int idx = 0;
		while(idx<files.length) {
			if(files[idx].isFile())return true;
			else if(hasChild(files[idx])==true)return true;
			idx++;
		}
		return false;
	}
	
	public void removeEmpty(File f) { //fonction qui enleve les dossiers vides
		if(f.isDirectory()) {
			if(!hasChild(f)) {
				f.delete();
			} else {
				File files[] = f.listFiles();
				for (File child : files) {
					removeEmpty(child);
				}
			}
		}
		
	}
	
	public boolean itemCheck(File f) throws IOException {
		boolean checked = false;
		int idx = 0;
		String temp;
		StringBuilder sb = new StringBuilder();
		BufferedReader b = new BufferedReader(new FileReader(f));
		String readLine = "";
		while ((readLine = b.readLine()) != null) {
             sb.append(readLine);
         }
		b.close();
		String content = sb.toString();
		//System.out.println(content.length());
		while(idx+32<content.length()&&!checked) {
			temp = ""+content.charAt(idx);
			if(temp.matches("[0-9A-F]")) {
				for (int j = 0;j<32;j++) {
					temp = ""+content.charAt(idx+j);
					if(temp.matches("[0-9A-F]")) {
						if(j==31)checked = true;
					}
					else break;
				}
			}
			idx++;
		}
		idx +=36;
		//System.out.println(idx);
		if(checked) {
			int idfin = idx;
			//int titleSize = 0;
			while(content.charAt(idfin)>=32) {
				//System.out.println("alors:"+content.charAt(idfin));
				//titleSize++;
				idfin++;
			}
			//String title = content.substring(idx, idx+titleSize);	
			//System.out.println(title);
		}
		return checked;
	}
}
