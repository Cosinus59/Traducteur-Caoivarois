package vue.callahan;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import modele.Traducteur;

public class CallahanView extends Stage{

	ControlleurTraducteur controlleur;
	Parent root;
	public CallahanView(Traducteur trad) {
		FXMLLoader loader = new FXMLLoader();
		controlleur = null;

		//System.out.println(controlleur);
		//Platform.runLater(() -> {
				System.out.println("cc");
				loader.setLocation(getClass().getResource("/Interface.fxml"));
				try {
					System.out.println("here");
					root = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				controlleur = (ControlleurTraducteur) loader.getController();
				System.out.println(controlleur);
			
		//});
		System.out.println(controlleur);
		System.out.println("he oui");
		while (controlleur == null) {
			//System.out.println("he non");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
		System.out.println("coucou");
		controlleur.init(trad,root);
		controlleur.iniItem();

		
		

		/* Because of runLater, we must wait until the windows are actually created
		 */
		

		
	}

}
