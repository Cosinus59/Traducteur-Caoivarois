package vue.callahan;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import modele.Traducteur;

public class CallahanView extends Stage{

	ControlleurTraducteur controlleur;
	Parent root;

	public CallahanView(Traducteur trad) throws IOException, InterruptedException {
		FXMLLoader loader = new FXMLLoader();
		controlleur = null;

		
		loader.setLocation(getClass().getResource("/Interface.fxml"));
		root = loader.load();

		controlleur = (ControlleurTraducteur) loader.getController();

		
		controlleur.init(trad,root);
		controlleur.iniItem();
	}

}
