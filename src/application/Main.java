package application;
	
import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import javafx.application.Application;
import javafx.stage.Stage;
import modele.Traducteur;
import vue.callahan.CallahanView;



public class Main extends Application {
	
	private static String externContentPath;
	
	static File folderPath; //    /Traducteur Caoivarois
	 
	static String internContentFolder; //       war/content
	static String internImagesFolder; //        
	
	@Override
	public void start(Stage stage) throws IOException, InterruptedException {
		Traducteur trad = new Traducteur();
		new CallahanView(trad);
	}
	
	public static void main(String[] args) {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		folderPath = new File(fsv.getDefaultDirectory().getPath()+File.separator+"Traducteur Caoivarois");
		internContentFolder = "data"+File.separator+"Content";
		
		launch(args);
	}
	
	public static String getInternContentFolder() {
		return internContentFolder;
	}

	public static String getExternContentPath() {
		return externContentPath;
	}

	public static void setExternContentPath(String externContentPath) {
		Main.externContentPath = externContentPath;
	}

	public static File getFolderPath() {
		return folderPath;
	}
	
}
