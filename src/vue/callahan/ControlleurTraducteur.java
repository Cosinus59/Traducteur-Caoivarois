package vue.callahan;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import filesManagment.PathSelector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
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
    private Tab techTab;    // Tech VV

    @FXML
    private Tab ItemsTab;     // Items VV

    @FXML
    ListView<ItemPickup> itemList;
    
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
    private Tab vehiclesTab;    // Vehicules VV
	
    @FXML
    private Tab structuresTab;    //Structures VV
    
    @FXML
    private Label leftStatus,rightStatus;

    @FXML
    private Menu tradMenu;
    
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
		
		if(traducteur!=null)this.traducteur = traducteur;
		else this.traducteur = new Traducteur();
		
		if(this.traducteur.getTraductions().size()==0)newTrad();
		else setMainTrad(this.traducteur.getTraductions().get(0));
		
		setSingleton();
		
		setCell(); // cell factory et double clique
		
		this.widthProperty().addListener((obs, oldVal, newVal) -> {
			setItemSpace(newVal.doubleValue());
		});
		this.setScene(new Scene(root));
		this.setResizable(true);
		this.show();
	}

	private void setSingleton() {
		ps = new PathSelector();
		//TODO
	}
	
	private void setTraductionMenu() {
		setNewTradButton();
		final int MAXTAILLE = 6;
		int taille = traducteur.getTraductions().size();
		int idx = 0;
		while(idx<taille&&idx<MAXTAILLE) {
			if(currentTrad!=traducteur.getTraductions().get(idx)) {
				addTradMenuItem(traducteur.getTraductions().get(idx),tradMenu);
			}
			++idx;
		}
		if(taille==MAXTAILLE)addTradMenuItem(traducteur.getTraductions().get(idx),tradMenu);
		else if(taille>MAXTAILLE) {
			Menu otherMenu = new Menu("Autres Traductions...");
			tradMenu.getItems().add(otherMenu);
			while(idx<taille) {
				if(currentTrad!=traducteur.getTraductions().get(idx)) {
					addTradMenuItem(traducteur.getTraductions().get(idx),otherMenu);
				}
				++idx;
			}
		}
	}

	private void addTradMenuItem(Traduction trad,Menu menu) {
			MenuItem newI = new MenuItem(trad.getName());
			newI.setOnAction(e ->{
				saveRequest(new ActionEvent());
				setMainTrad(trad);
				iniItem();
			});
			menu.getItems().add(newI);
	}

	private void setNewTradButton() {
		MenuItem neww = new MenuItem("Nouvelle Traduction");
		neww.setOnAction(e ->{
			saveRequest(new ActionEvent());
			newTrad();
		});
		tradMenu.getItems().add(neww);
		tradMenu.getItems().add(new SeparatorMenuItem());
	}

	private void newTrad() {
		Traduction nouvelle = new Traduction();
		nouvelle.loadIntern();
		traducteur.addTraduction(nouvelle);
		setMainTrad(nouvelle);
		iniItem();
	}

	private void setCell() {
		itemList.setCellFactory((ListView<ItemPickup> lv) -> new ItemListCell(this));
		itemList.setOnMouseClicked(event -> { //permet la selection
    		if (event.getClickCount() == 2) {
    			setSelectedItem(itemList.getSelectionModel().getSelectedItem());
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
	}

	public void iniItem() {
		itemList.getItems().clear();
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
		if(selectedListItem.replaceTitle(selectedItemTitleFld.getText())) {
			renaming = false;
			selectedItemTitleFld.setVisible(false);
			selectedItemTitleFld.setDisable(true);
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
		else save();
	}
	
	void saveAs(String name) {
		currentTrad.setName(name);
		save();
	}
	
	void save() {
		traducteur.writTrad(currentTrad);
		
		setTitle();
	}
	
	
	public void setMainTrad(Traduction trad) {
		this.currentTrad = trad;
		setTitle();
		tradMenu.getItems().clear();
		setTraductionMenu();
	}

	private void setTitle() {
		String display = "";
		if(currentTrad.getName()!=null&&!currentTrad.getName().equals(""))display = " - "+currentTrad.getName();
		this.setTitle("Traducteur Caoivarois"+display);
		leftStatus.setText(currentTrad.getName());
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
		setSelectedItemImage();
		selectedItemTitleLbl.setText(selectedListItem.getTradTitle());
		selectedItemDescLbl.setText(selectedListItem.getTradDesc());
	}

	private void setSelectedItemImage() {
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
	}
	
	@FXML
    void exportRequest(ActionEvent event) {
		traducteur.pak();
    }
	
}

