package agridrone.view;

import agridrone.model.Item;
import agridrone.model.ItemAbstract;
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
    			String newName = "bitch";
    			
    			applyButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						item.getValue().setName(newName);
						cancelDialog();
					}
    				
    			});
    			break;
    		//change location
    		case 1:
    			selectionModel.select(c);
    			
    			break;
    		//change price
    		case 2: 
    			selectionModel.select(c);
    			
    			break;
    		//change dimensions
    		case 3:
    			selectionModel.select(c);
    			
    			break;
    		//add item
    		case 4:
    			selectionModel.select(c);
//    			Item newItem = new Item(newName, c, c, c, c, c);
//    			item.getValue().addItemAbstract(newItem);
    			
    			break;
    		//add item container
    		case 5:
    			selectionModel.select(c);
    			
    			break;
    					
    	}
    	
    	
    }
}
