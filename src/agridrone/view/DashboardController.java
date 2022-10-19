package agridrone.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

import agridrone.MainApp;
import agridrone.model.Drone;
import agridrone.model.Item;
import agridrone.model.ItemAbstract;
import agridrone.model.ItemContainer;

public class DashboardController {
	
	@FXML
	private TreeView<ItemAbstract> itemList;
	
	@FXML
	private Label detailsLabel;
	
	@FXML
	private Label nameLabel;
	
	@FXML
	private Label priceLabel;
	
	@FXML
	private Label locationLabel;
	
	@FXML
	private Label sizeLabel;
	
	@FXML
	private Button visitButton;
	
	
	
	//main app reference
	private MainApp mainApp;
	
	//main farm reference
	private ItemContainer farm;
	
	private EventHandler<ActionEvent> renameEvent = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			showDialogBox(0);
		}
	};
	private EventHandler<ActionEvent> changeLocationEvent = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			showDialogBox(1);
		}
	};
	private EventHandler<ActionEvent> changePriceEvent = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			showDialogBox(2);
		}
	};
	private EventHandler<ActionEvent> changeDimensionsEvent = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			showDialogBox(3);
		}
	};
	private EventHandler<ActionEvent> addItemEvent = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			showDialogBox(4);
		}
	};
	private EventHandler<ActionEvent> addContainerEvent = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			showDialogBox(5);
		}
	};
	private EventHandler<ActionEvent> deleteItemEvent = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			TreeItem<ItemAbstract> current = getSelected();
			current.getValue().delete();
			renderTree();
		}
	};
	
	
	
	//constructor
	public DashboardController() {}
	
	@FXML
	public void initialize() {
		
		//initial dummy data
		farm = new ItemContainer("Farm", 0, 0, 600, 800, 200000);
		ItemContainer barn = new ItemContainer("Barn", 10, 10, 100, 200, 10000);
		ItemContainer commCent = new ItemContainer("Command Center", 20, 20, 40, 40, 1000);
		ItemContainer field = new ItemContainer("Field", 50, 50, 100, 100, 0);
		Item tractor = new Item("Tractor", 75, 75, 5, 5, 15000);
		Item cow = new Item("Cow", 50, 40, 2, 2, 500);
		Drone drone = new Drone("Drone", 30, 30, 1, 1, 1000);
		ItemContainer milkStore = new ItemContainer("Milk Storage", 11, 11, 3, 3, 100);
		Item milk = new Item("Milk", 12, 12, 1, 1, 3);
		
		barn.addItem(cow);
		field.addItem(tractor);
		commCent.addItem(drone);
		milkStore.addItem(milk);
		barn.addItemContainer(milkStore);
		
		farm.addItemContainer(barn);
		farm.addItemContainer(commCent);
		farm.addItemContainer(field);
		
		renderTree();
		

//		initial null load
		showItemDetails(null);
		//sets the label of each item in the tree table to the name of the item
		itemList.setCellFactory(p ->  new TreeCellFactory());
		
		//selection listener
		itemList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<ItemAbstract>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<ItemAbstract>> observable, TreeItem<ItemAbstract> oldValue, TreeItem<ItemAbstract> newValue) {
				if (newValue != null) {
					showItemDetails(newValue.getValue());
				} else {
					showItemDetails(null);
				}
				
			}
		});
	}
	
	//get currently selected TreeItem
	public TreeItem<ItemAbstract> getSelected() {
		return itemList.getSelectionModel().getSelectedItem();
	}
	

	
	private ContextMenu makeContextMenu(int type) {
		//Item context menu
		ContextMenu contextMenu = new ContextMenu();
		//item right click menu actions
		MenuItem menuAction1 = new MenuItem("Rename");
		menuAction1.setOnAction(renameEvent);
		MenuItem menuAction2 = new MenuItem("Change Location");
		menuAction2.setOnAction(changeLocationEvent);
		MenuItem menuAction3 = new MenuItem("Change Price");
		menuAction3.setOnAction(changePriceEvent);
		MenuItem menuAction4 = new MenuItem("Change Dimensions");
		menuAction4.setOnAction(changeDimensionsEvent);
		MenuItem menuAction5 = new MenuItem("Add Item");
		menuAction5.setOnAction(addItemEvent);
		MenuItem menuAction6 = new MenuItem("Add Item Containter");
		menuAction6.setOnAction(addContainerEvent);
		MenuItem menuAction7 = new MenuItem("Delete");
		menuAction7.setOnAction(deleteItemEvent);
		
		switch(type) {
		case 1:
			contextMenu.getItems().addAll(menuAction1, menuAction2, menuAction3, menuAction4, menuAction7);
			break;
		case 2:
			contextMenu.getItems().addAll(menuAction1, menuAction2, menuAction3, menuAction4, menuAction5, menuAction6, menuAction7);
			break;
		case 3:
			contextMenu.getItems().addAll(menuAction1, menuAction2, menuAction3, menuAction4);
			break;
		}
		
		return contextMenu;
	}
	
	private final class TreeCellFactory extends TreeCell<ItemAbstract> {

		
		public TreeCellFactory() {}
		
		@Override
		public void updateItem(ItemAbstract item, boolean empty) {
			super.updateItem(item, empty);
			
			//if empty Item
			if (empty) {
				setText(null);
			} 
			//otherwise set text to name
			else {
				setText(item.getName());
			}
			
			//set the context menu based off item type
			if (item instanceof Item) {
				setContextMenu(makeContextMenu(1));
			}
			
			if (item instanceof ItemContainer) {
				setContextMenu(makeContextMenu(2));
			}
			
			if(item instanceof Drone) {
				setContextMenu(makeContextMenu(3));
			}
		}
	}
	
	//sets the labels on the page to info corresponding with the selected item
	private void showItemDetails(ItemAbstract item) {
		if (item != null) {
			detailsLabel.setText(item.getName() + " Details");
			nameLabel.setText(item.getName());
			priceLabel.setText(Float.toString(item.getPrice()));
			locationLabel.setText("(" + Integer.toString(item.getLocationX()) + ", " + Integer.toString(item.getLocationY()) + ")");
			sizeLabel.setText(Integer.toString(item.getLength()) + " x " + Integer.toString(item.getWidth()));
			visitButton.setText("Visit " + item.getName());
			if (item.getName().equals("Drone")) {
				visitButton.setText("Cannot visit Drone with Drone");
				visitButton.setDisable(true);
			} else {

				visitButton.setDisable(false);
			}
	
		} else {
			detailsLabel.setText("Select an item to see Details");
			nameLabel.setText("");
			priceLabel.setText("");
			locationLabel.setText("");
			sizeLabel.setText("");
			visitButton.setText("Select an item from the menu to visit it with Drone");
			visitButton.setDisable(true);

		}
	}
	
	//render the tree based of the current state of this.farm
	public void renderTree() {
		TreeItem<ItemAbstract> root = getTreeChildren(this.farm);
		itemList.setRoot(root);
	}
	

	//function recursively builds out a tree to populate the TableTreeView
	public TreeItem<ItemAbstract> getTreeChildren(ItemAbstract item) {
		//Parent node
		TreeItem<ItemAbstract> parent = new TreeItem<>(item);

		//if the item has children
		if (item.getContents() != null) {
			//for each child, create a child tree item, populate it recursively with getTreeChildren()
			for (ItemAbstract cont : item.getContents()) {
				TreeItem<ItemAbstract> child = getTreeChildren(cont);
				parent.getChildren().add(child);
			}
			//set to expanded
			parent.setExpanded(true);
			return parent;
		}
		return parent;

	}

	//main app setter
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}
	
	//dialog pane
	@FXML
	public void showDialogBox(int c) {
		TreeItem<ItemAbstract> currentItem = getSelected();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DialogBox.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        if (root == null) return;
        Stage dialogBox = new Stage();
        dialogBox.setAlwaysOnTop(true);
        DialogController dialogController = loader.getController();
        dialogBox.setScene(new Scene(root));
        dialogBox.initOwner(mainApp.getPrimaryStage());
        dialogBox.initModality(Modality.APPLICATION_MODAL);
        dialogController.setStage(dialogBox);
        dialogController.buildDialogBox(c, currentItem);
        dialogBox.setOnCloseRequest(e -> dialogController.removeController());
        dialogBox.showAndWait();
        renderTree();
		
	}

}


