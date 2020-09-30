package application;
	
import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.Traducteur;
import vue.Callahan.ControlleurTraducteur;



public class Main extends Application {
	
	private static String externContentPath;
	
	static File folderPath; //    /Traducteur Caoivarois
	 
	static String internContentFolder; //       war/content
	static String internImagesFolder; //        
	
	@Override
	public void start(Stage stage) throws IOException {
		Traducteur trad = new Traducteur();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Interface.fxml"));
		Parent root = loader.load();
		ControlleurTraducteur controlleur = (ControlleurTraducteur) loader.getController();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		controlleur.init(trad,stage);
		
	}
	
	public static void main(String[] args) {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		folderPath = new File(fsv.getDefaultDirectory().getPath()+File.separator+"Traducteur Caoivarois");
		internContentFolder = "War"+File.separator+"Content";
		
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
