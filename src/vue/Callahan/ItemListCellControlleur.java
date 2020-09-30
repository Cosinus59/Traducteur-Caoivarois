package vue.Callahan;

import java.io.File;
import java.net.MalformedURLException;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import modele.ItemPickup;

public class ItemListCellControlleur {
	

	@FXML
    private BorderPane container;

    @FXML
    private StackPane descField;

    @FXML
    private StackPane titleField;

    @FXML
    private TextArea descFld;

    @FXML
    private Label titleLbl;

    @FXML
    private Label descLbl;

    @FXML
    private ImageView imgViewer;

    @FXML
    private TextField titleFld;
    
    public ItemListCellControlleur() {
       valueProperty().addListener(valueChangeListener);        
    }

    /**
     * Initialisation du contrôleur.
     */
    public void initialize() {
    	titleLbl.setText(null);
    	descLbl.setText(null);
    }

    /**
    * Cet écouteur est appelé lorsque la propriété value change.
    */
    private final ChangeListener<ItemPickup> valueChangeListener = (ObservableValue<? extends ItemPickup> observableValue, ItemPickup oldValue, ItemPickup newValue) -> {
        System.out.println(newValue.getTitle());
    	updateUI(newValue);
    };

    private void updateUI(ItemPickup newValue) {
        final ItemPickup val = newValue;
        final String titleText = val.getTitle();
        final String descText = val.getDesc();
        titleLbl.setText(titleText);
        descLbl.setText(descText);
        File imgFile = new File("Images"+File.separator+"Textures"+File.separator+val.getImagePath());
        String path;
		try {
			path = imgFile.toURI().toURL().toString();
	        Image img = new Image(path);
	        imgViewer.setImage(img); 
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    /**
    * Contient la valeur à afficher.
    */
    private final Property<ItemPickup> value = new SimpleObjectProperty<ItemPickup>(this,"value");

    public final ItemPickup getValue() {
        return value.getValue();
    }

    public final void setValue(ItemPickup value) {
        this.value.setValue(value);
    }

    public final Property<ItemPickup> valueProperty() {
        return value;
    }
}

