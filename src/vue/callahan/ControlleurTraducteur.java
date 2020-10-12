package vue.callahan;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import filesManagment.PathSelector;
import filesManagment.Serializer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modele.ItemPickup;
import modele.Traducteur;
import modele.Traduction;

public class ControlleurTraducteur extends Stage {
	
	@FXML
    private Tab techTab;    // Tech

    @FXML
    private Tab ItemsTab;     // Items

    @FXML
    private TextField selectedItemTitleFld;
    
    @FXML
    private TextArea selectedItemDescFld;
    
    @FXML
    private ImageView selectedImgViewer;

    @FXML
    private Label selectedItemTitleLbl, selectedItemDescLbl;
    
    @FXML
    private Pane itemSpacer1,itemSpacer2,itemSpacer3,itemSpacer4;
    
    @FXML
    private MenuItem repathBtn;

    @FXML
    private Tab vehiclesTab;    // Vehicules
	
    @FXML
    ListView<ItemPickup> itemList;
    
    @FXML
    private Tab structuresTab;    //Structures
    
	private Traducteur traducteur;
	private Traduction currentTrad;
    PathSelector ps;
	ItemPickup selectedListItem;
	
	@SuppressWarnings("unused")
	private boolean renaming,retexting;
	
	public void init(Traducteur traducteur, Parent root) {
		//System.out.println("-----------");
		renaming = false;
		retexting = false;
		
		this.traducteur = traducteur;
		if(traducteur.getTraductions().size()==0)traducteur.addTraduction(new Traduction());
		currentTrad = traducteur.getTraductions().get(0);
		
		ps = new PathSelector();
		
		// cell factory et double clique
		itemList.setCellFactory((ListView<ItemPickup> lv) -> new ItemListCell(this));
		itemList.setOnMouseClicked(event -> { //permet la selection
    		if (event.getClickCount() == 2) {
    			setSelectedItem(itemList.getSelectionModel().getSelectedItem());
    			displayRefresh();
    		}
		});
		
        itemList.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue){
                	itemList.getItems();
                }
            }
        });
		
		this.widthProperty().addListener((obs, oldVal, newVal) -> {
			setItemSpace(newVal.doubleValue());
		});
		this.setScene(new Scene(root));
		this.setTitle("Traducteur Caoivarois");
		this.setResizable(true);
		this.show();
	}
	
	public void iniItem() {
		addAll(currentTrad.getItemList());
		
		itemList.requestFocus();
		itemList.getSelectionModel().select(0);
		itemList.scrollTo(0);
		setSelectedItem(itemList.getSelectionModel().getSelectedItem());
		
	}
	
	/*
		Renommage et redéfinition de l'item sélectionné
	 */

	void cancelRename() {
		selectedItemTitleFld.setVisible(false);
		selectedItemTitleFld.setDisable(true);
		renaming = false;
	}
	
	@FXML
    void changeTitle(ActionEvent event) {
		renaming = true;
		selectedItemTitleFld.setVisible(true);
		selectedItemTitleFld.setDisable(false);
		selectedItemTitleFld.setText(selectedItemTitleLbl.getText());
		selectedItemTitleFld.requestFocus();
    }
	
	@FXML
	void testEndRename(KeyEvent event) {
		if(event.getCode().equals(KeyCode.ESCAPE)) {
			cancelRename();
		}
	}
	
	@FXML
	void endRename(ActionEvent event) {
		if(selectedListItem.checkName(selectedItemTitleFld.getText())) {
			renaming = false;
			selectedItemTitleFld.setVisible(false);
			selectedItemTitleFld.setDisable(true);
			selectedListItem.replaceTitle(selectedItemTitleFld.getText());
			System.out.println(selectedListItem.getTradTitle());
			selectedItemTitleLbl.setText(selectedListItem.getTradTitle());
			itemList.refresh();
		}
    }
	
	protected void cancelRetext() {
		selectedItemDescFld.setVisible(false);
		selectedItemDescFld.setDisable(true);
		retexting = false;
	}
	
	@FXML
	private void changeDesc(ActionEvent event) {
		retexting = true;
		selectedItemDescFld.setVisible(true);
		selectedItemDescFld.setDisable(false);
		selectedItemDescFld.setText(selectedItemDescLbl.getText());
		selectedItemDescFld.requestFocus();
	}
	
	@FXML
	void endRetext(KeyEvent event) {
		if(event.getCode().equals(KeyCode.ENTER)&&!event.isShiftDown()) {
			if(selectedListItem.checkDesc(selectedItemDescFld.getText())) {
				retexting = false;
				selectedItemDescFld.setVisible(false);
				selectedItemDescFld.setDisable(true);
				selectedListItem.replaceDesc(selectedItemDescFld.getText());
				selectedItemDescLbl.setText(selectedListItem.getTradDesc());
				itemList.refresh();
			} else {
				event.consume();
			}
		} else if(event.getCode().equals(KeyCode.ESCAPE)) {
			cancelRetext();
		} else if(event.getCode().equals(KeyCode.ENTER)) {
			int caretPosition = selectedItemDescFld.getCaretPosition();
			selectedItemDescFld.insertText(caretPosition, "\n");
		}
    }
	
	@FXML
	public void saveRequest(ActionEvent event) {
		if(currentTrad.getName()==null||currentTrad.getName().equals(""))new tradNameWindow(this);
		else saveAs(currentTrad.getName());
	}
	
	void saveAs(String name) {
		currentTrad.setName(name);
		Serializer.writeTrad(currentTrad);
	}
	
	private void displayRefresh() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unused")
	private void updateSelection() {
		// TODO le truc qui update a droite
		
	}

	public void deletePressed(ActionEvent event) {
		
	}
	
	public void setMain(Traduction trad) {
		this.currentTrad = trad;
	}
	
	public void repathRequest(ActionEvent event) {
		ps.pathSelector();
	}

	public void addAll(ArrayList<ItemPickup> arrayList) {
		itemList.getItems().addAll(arrayList);
		
	}
	
	public void add(ItemPickup item) {
		itemList.getItems().add(item);
	}

	public void setItemSpace(double newVal) {
		double panelSizeLeft = ((newVal/2)-451)/2;
		double panelSizeRight = ((newVal/2)-407)/2;
		itemSpacer1.setPrefWidth(panelSizeLeft);
		itemSpacer2.setPrefWidth(panelSizeLeft);
		itemSpacer3.setPrefWidth(panelSizeRight);
		itemSpacer4.setPrefWidth(panelSizeRight);
	}

	public void setSelectedItem(ItemPickup itemPickup) {
		selectedListItem = itemPickup;
		File imgFile = new File("Images"+File.separator+"Textures"+File.separator+selectedListItem.getImagePath());
        String path;
		try {
			path = imgFile.toURI().toURL().toString();
			Image img = new Image(path);
			selectedImgViewer.setImage(img);
		} catch (MalformedURLException e) {
			System.out.println("pb avec l'image de l'item sélectionné");
			e.printStackTrace();
		}
		selectedItemTitleLbl.setText(selectedListItem.getTradTitle());
		selectedItemDescLbl.setText(selectedListItem.getTradDesc());
	}

}

