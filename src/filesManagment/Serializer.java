package filesManagment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import modele.Traduction;

public class Serializer {
	
	public static void writeTrad(Traduction trad) {
		
		ObjectOutputStream oos = null;
		String cc = trad.getName();
		System.out.println(cc);
		File traduction = new File("data"+File.separator+"traduction"+File.separator);
		if(traduction.exists()) {
			if(traduction.isFile()) {
				traduction.delete();
				traduction.mkdirs();
			}
			else ;
		}
		else traduction.mkdirs();
		
		try {
			final FileOutputStream fichier = new FileOutputStream("data"+File.separator+"traduction"+File.separator+trad.getName()+".trad");
			oos = new ObjectOutputStream(fichier);
			oos.writeObject(trad);
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
    	}
    }

	public static ArrayList<Traduction> getTrads() {
		File traduction = new File("data"+File.separator+"traduction"+File.separator);
		File[] temporaires = new File[0];
		ArrayList<Traduction> traductions = new ArrayList<Traduction>();
		if(traduction.exists()&&traduction.isDirectory()) {
			temporaires = traduction.listFiles();
		}
		
		
		for (File f : temporaires) {
			if(f.isFile()&&f.getPath().substring(f.getPath().length()-5, f.getPath().length()).equals(".trad"));
			traductions.add(getTrad(f));
			System.out.println(traductions);
		}
		return traductions;
	}

	private static Traduction getTrad(File f) {
		
		ObjectInputStream ois = null;
		Traduction trad = null;
		try {
			final FileInputStream fichier = new FileInputStream(f);
			ois = new ObjectInputStream(fichier);
			trad = (Traduction) ois.readObject();
			System.out.println(f.getPath());
			System.out.println(trad);
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				return trad;
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
    	}
		return null;
	}
	
}
