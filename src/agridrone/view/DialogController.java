package agridrone.view;

import agridrone.model.Item;
import agridrone.model.ItemAbstract;
import agridrone.model.ItemContainer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DialogController {
	
	@FXML
    private Stage dialogBox;
    
	@FXML
    private TabPane dialogContent;
    
	@FXML
    private Tab renameTab;

	@FXML
    private Tab changeLocationTab;
    
	@FXML
    private Tab changePriceTab;
    
	@FXML
    private Tab changeDimensionsTab;
    
	@FXML
    private Tab addItemTab;
    
	@FXML
    private Tab addItemContTab;
	
	@FXML
	private Button dialogCancelButton;
	
	@FXML
	private Button applyButton;
	
	@FXML
	private TextField renameBox;
	
	@FXML
	private TextField xCoordBox;
	
	@FXML
	private TextField yCoordBox;
	
	@FXML
	private TextField priceBox;
	
	@FXML
	private TextField widthBox;
	
	@FXML
	private TextField lengthBox;
	
	@FXML
	private TextField newItemName;
	
	@FXML
	private TextField newItemX;
	
	@FXML
	private TextField newItemY;
	
	@FXML
	private TextField newItemPrice;
	
	@FXML
	private TextField newItemWidth;
	
	@FXML
	private TextField newItemLength;
	
	@FXML
	private TextField newContainerName;
	
	@FXML
	private TextField newContainerX;
	
	@FXML
	private TextField newContainerY;
	
	@FXML
	private TextField newContainerPrice;
	
	@FXML
	private TextField newContainerWidth;
	
	@FXML
	private TextField newContainerLength;
	

    
	
	private static DialogController dialogController;
    
	
    public DialogController() throws LoadException {
        if (dialogController == null) dialogController = this;
        else throw new LoadException("Singleton FXML");
    }
    
    public void setStage(Stage dialogBox) {
        this.dialogBox = dialogBox;
    }

    public void removeController() {
        dialogController = null;
    }

    private void closeStage() {
        dialogBox.close();
        removeController();
    }
   
    @FXML
    public void cancelDialog() {
    	closeStage();
    }
    
    public void buildDialogBox(int c, TreeItem<ItemAbstract> item) {
    	SingleSelectionModel<Tab> selectionModel = dialogContent.getSelectionModel();
    	dialogBox.setTitle(item.getValue().getName() + " Actions");
    	
    	if (item.getValue() instanceof Item) {
    		addItemTab.setDisable(true);
    		addItemContTab.setDisable(true);
    	} else {
    		addItemTab.setDisable(false);
    		addItemContTab.setDisable(false);
    	}
    	
    	//switch case for building dialog box
    	switch (c) {
    		//rename
    		case 0:
    			selectionModel.select(c);
    			
    			applyButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						String newName = renameBox.getText();
						item.getValue().setName(newName);
						cancelDialog();
					}
    				
    			});
    			break;
    		//change location
    		case 1:
    			selectionModel.select(c);
    			
    			applyButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						
						
						int newX = Integer.parseInt(xCoordBox.getText());
						item.getValue().setLocationX(newX);
						int newY = Integer.parseInt(yCoordBox.getText());
						item.getValue().setLocationY(newY);
						cancelDialog();
					}
    				
    			});
    			
    			
    			break;
    		//change price
    		case 2: 
    			selectionModel.select(c);
    			
    			applyButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						Float newPrice = Float.parseFloat(priceBox.getText());
						item.getValue().setPrice(newPrice);
						cancelDialog();
						
				
					}
    				
    			});
    			
    			break;
    		//change dimensions
    		case 3:
    			selectionModel.select(c);
    			applyButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						
						int newLength = Integer.parseInt(lengthBox.getText());
						item.getValue().setLength(newLength);
						int newWidth = Integer.parseInt(widthBox.getText());
						item.getValue().setWidth(newWidth);
						cancelDialog();
				
					}
    				
    			});
    			
    			break;
    		//add item
    		case 4:
    			selectionModel.select(c);
    			
    			applyButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						
						int x = Integer.parseInt(newItemX.getText());
						int y = Integer.parseInt(newItemY.getText());
						int w = Integer.parseInt(newItemWidth.getText());
						int l = Integer.parseInt(newItemLength.getText());
						int p = Integer.parseInt(newItemPrice.getText());
						
						Item newItem = new Item(newItemName.getText(), x, y, w, l, p);
						item.getValue().addItemAbstract(newItem);
						cancelDialog();
				
					}
    				
    			});
    			
    			break;
    		//add item container
    		case 5:
    			selectionModel.select(c);
    			applyButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						
						int x = Integer.parseInt(newContainerX.getText());
						int y = Integer.parseInt(newContainerY.getText());
						int w = Integer.parseInt(newContainerWidth.getText());
						int l = Integer.parseInt(newContainerLength.getText());
						int p = Integer.parseInt(newContainerPrice.getText());
						
						ItemContainer newContainer = new ItemContainer(newContainerName.getText(), x, y, w, l, p);
						item.getValue().addItemContainer(newContainer); //doesnt work for now it looks like it just makes an item 10-17 -JP
						cancelDialog();
				
					}
    				
    			});
    			
    			
    			
    			break;
    					
    	}
    	
    	
    }
}
