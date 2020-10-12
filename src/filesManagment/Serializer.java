package filesManagment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import modele.Traducteur;

public class Serializer {
	
	public static void writeTrad(Traducteur trad) {
		
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
	
}
