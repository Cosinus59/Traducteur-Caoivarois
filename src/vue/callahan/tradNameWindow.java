package vue.callahan;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class tradNameWindow extends Stage {
	
	TextField tf;
	
	ControlleurTraducteur cont;
	
	public tradNameWindow(ControlleurTraducteur cont) {
		this.cont = cont;
		
		AnchorPane pane = new AnchorPane();
		pane.setPrefHeight(150);
		pane.setPrefWidth(400);
		
		Label title = new Label("Votre Traduction n'a pas de nom !");
		title.setLayoutX(65);
		title.setLayoutY(14);
		title.setFont(new Font(18));
		
		Label subTitle = new Label("Veuillez en saisir un :");
		subTitle.setLayoutX(131);
		subTitle.setLayoutY(41);
		subTitle.setFont(new Font(15));
		
		tf = new TextField();
		tf.setPromptText("Nom de la traduction");
		tf.setFont(new Font(12));
		tf.setLayoutX(87);
		tf.setLayoutY(75);
		tf.setPrefWidth(225);
		
		Button validate,cancel;
		validate = new Button("Valider");
		cancel = new Button("Annuler");
		validate.setLayoutX(105);
		validate.setLayoutY(111);
		cancel.setLayoutX(243);
		cancel.setLayoutY(111);
		
		validate.setOnAction(e ->{
			if(checkText(tf.getText())) {
				cont.saveAs(tf.getText());
				this.close();
			}
		});
		cancel.setOnAction(e ->this.close());
		
		pane.getChildren().add(title);
		pane.getChildren().add(subTitle);
		pane.getChildren().add(tf);
		pane.getChildren().add(validate);
		pane.getChildren().add(cancel);
		
		this.setScene(new Scene(pane));
		this.setResizable(false);
		this.setTitle("Veuillez nommer cette Traduction");
		this.show();
	}

	private boolean checkText(String text) {
		if(text==null|text=="")return false;
		
		if(text.matches(".*[\\p{Punct}].*")) {
			//TODO
			System.out.println("test");
			return false;
		}
		return true;
	}
	
}
