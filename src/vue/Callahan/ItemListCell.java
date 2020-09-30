package vue.Callahan;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import modele.ItemPickup;

public class ItemListCell extends ListCell<ItemPickup> {
	
	private Node renderer;
    private ItemListCellControlleur rendererControlleur;
    private ControlleurTraducteur trad;
    
    public ItemListCell(ControlleurTraducteur trad) {
        super();
        // Chargement du FXML.
        this.trad = trad;
        try {
            final URL fxmlURL = getClass().getResource("/ListCell.fxml");
            final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            renderer = (Node) fxmlLoader.load();
            rendererControlleur = (ItemListCellControlleur) fxmlLoader.getController();
        } catch (IOException ex) {
            Logger.getLogger(ItemListCell.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
    }
    
    @Override
    protected void updateItem(ItemPickup value, boolean empty) {
        super.updateItem(value, empty);
        String text = null;
        Node graphic = null;
        if (!empty && value != null) {
            // Si c'est un nombre premier, on utilise le nœud provenant du FXML.
            if (renderer != null) {
                graphic = renderer;
                rendererControlleur.setValue(value);
            } else {
                text = String.valueOf(value);
            }
        }
        setText(text);
        setGraphic(graphic);
        MenuItem openItem = new MenuItem("Modifier");
		openItem.setOnAction(e-> {
				trad.setSelectedItem(value);
	    	});
		MenuItem renameItem = new MenuItem("Renommer");
		renameItem.setOnAction(e-> {
				//pressedSelectedRename(e);
	    	});
		MenuItem deleteItem = new MenuItem("Supprimer");
		deleteItem.setOnAction(e-> {
				//pressedSelectedDelete(e);
	    	});
		ContextMenu listMenu = new ContextMenu(openItem,renameItem,deleteItem);
        setContextMenu(listMenu);
    }
}
