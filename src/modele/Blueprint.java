package modele;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Blueprint implements Serializable {
	
	private String name;
	private String FileName;
	
	public Blueprint(String name, String path) {
		this.name = name;
		this.FileName = path;
	}
	
	public Blueprint(String path) {
		this.FileName = path;
		setName(removePath(path));
	}
	
	public Blueprint() {
		this.name = null;
		this.FileName = null;
	}
	
	public String removePath(String path) {
		int idx = path.length();
		while(path.charAt(idx)!='\\'&&idx>=0) {
			idx--;
		}
		int idfin = idx;
		do {
			while(idfin!='.'&&idfin<=path.length()) {
				idfin++;
			}
		} while (!path.substring(idfin+1).equals(".uasset"));
		return path.substring(idx, idfin+1);
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return FileName;
	}

	public void setPath(String path) {
		this.FileName = path;
	}

}
