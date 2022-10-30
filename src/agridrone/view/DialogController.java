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

	@FXML
	private Label inputErrorLocation;

	@FXML
	private Label inputErrorPrice;

	@FXML
	private Label inputErrorDimensions;

	@FXML
	private Label inputErrorAddItem;

	@FXML
	private Label inputErrorAddItemContainer;

	@FXML
	private Label renameNull;

	@FXML
	private Label itemNameNull;

	@FXML
	private Label itemContainerNull;

	private static int tabNum = -1;

	private static DialogController dialogController;

	public DialogController() throws LoadException {
		if (dialogController == null)
			dialogController = this;
		else
			throw new LoadException("Singleton FXML");
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
		// initial tab
		tabNum = c;
		// set the validation and action handlers
		setAction(item);

		// set the title of the window
		dialogBox.setTitle(item.getValue().getName() + " Actions");

		// if the tree item is an Item literal
		if (item.getValue() instanceof Item) {
			// disable the add item and add item container tabs
			addItemTab.setDisable(true);
			addItemContTab.setDisable(true);
		}
		// otherwise, set these tabs to enabled
		else {
			addItemTab.setDisable(false);
			addItemContTab.setDisable(false);
		}
		// on tab change
		dialogContent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				// set the new tab number
				DialogController.tabNum = dialogContent.getSelectionModel().getSelectedIndex();
				// set the new validation and action handler
				setAction(item);

			}
		});

	}

	public void setAction(TreeItem<ItemAbstract> item) {

		SingleSelectionModel<Tab> selectionModel = dialogContent.getSelectionModel();
		// switch case for building dialog box
		switch (tabNum) {
		// rename
		case 0:
			selectionModel.select(tabNum);

			applyButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {
					if (!renameBox.getText().equals("")) {
						String newName = renameBox.getText();
						item.getValue().setName(newName);
						cancelDialog();
					} else {
						renameNull.setVisible(true);
					}
				}
			});
			break;
		// change location
		case 1:
			selectionModel.select(tabNum);

			applyButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {

					// input error catching
					if (xCoordBox.getText() == null || yCoordBox.getText() == null) {
						inputErrorLocation.setVisible(true);
					}
					try {
						int xTest = Integer.parseInt(xCoordBox.getText());
						int yTest = Integer.parseInt(yCoordBox.getText());

					} catch (NumberFormatException nfe) {
						System.out.println("Location Error: " + nfe);
						inputErrorLocation.setVisible(true);
					}

					int newX = Integer.parseInt(xCoordBox.getText());
					item.getValue().setLocationX(newX);
					int newY = Integer.parseInt(yCoordBox.getText());
					item.getValue().setLocationY(newY);
					cancelDialog();
				}

			});

			break;
		// change price
		case 2:
			selectionModel.select(tabNum);

			applyButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {

					if (priceBox.getText() == null) {
						inputErrorPrice.setVisible(true);
					}
					try {
						Float tryPrice = Float.parseFloat(priceBox.getText());

					} catch (NumberFormatException nfe) {
						System.out.println("Price Error: " + nfe);
						inputErrorPrice.setVisible(true);
					}
					Float newPrice = Float.parseFloat(priceBox.getText());
					item.getValue().setPrice(newPrice);
					cancelDialog();

				}

			});

			break;
		// change dimensions
		case 3:
			selectionModel.select(tabNum);
			applyButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {

					if (lengthBox.getText() == null || widthBox.getText() == null) {
						inputErrorDimensions.setVisible(true);
					}
					try {
						int testL = Integer.parseInt(lengthBox.getText());
						int testW = Integer.parseInt(widthBox.getText());

					} catch (NumberFormatException nfe) {
						System.out.println("Dimensions Error: " + nfe);
						inputErrorDimensions.setVisible(true);
					}

					int newLength = Integer.parseInt(lengthBox.getText());
					item.getValue().setLength(newLength);
					int newWidth = Integer.parseInt(widthBox.getText());
					item.getValue().setWidth(newWidth);
					cancelDialog();

				}

			});

			break;
		// add item
		case 4:
			selectionModel.select(tabNum);

			applyButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {

					if (!newItemName.getText().equals("")) {
						if (newItemPrice.getText() == null || newItemY.getText() == null
								|| newItemWidth.getText() == null || newItemLength.getText() == null
								|| newItemPrice.getText() == null) {
							inputErrorAddItem.setVisible(true);
						}

						try {
							int x = Integer.parseInt(newItemPrice.getText());
							int y = Integer.parseInt(newItemY.getText());
							int w = Integer.parseInt(newItemWidth.getText());
							int l = Integer.parseInt(newItemLength.getText());
							int p = Integer.parseInt(newItemPrice.getText());
						} catch (NumberFormatException nfe) {
							System.out.println("Add Item Error: " + nfe);
							inputErrorAddItem.setVisible(true);
						}

						int x = Integer.parseInt(newItemX.getText());
						int y = Integer.parseInt(newItemY.getText());
						int w = Integer.parseInt(newItemWidth.getText());
						int l = Integer.parseInt(newItemLength.getText());
						Float p = Float.parseFloat(newItemPrice.getText());

						Item newItem = new Item(newItemName.getText(), x, y, w, l, p);
						((ItemContainer) item.getValue()).addItemAbstract(newItem);
						cancelDialog();

					} else {
						itemNameNull.setVisible(true);
					}
				}
			});

			break;
		// add item container
		case 5:
			selectionModel.select(tabNum);
			applyButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {
					if (!newContainerName.getText().equals("")) {
						if (newContainerX.getText() == null || newContainerY.getText() == null
								|| newContainerWidth.getText() == null || newContainerLength.getText() == null
								|| newContainerPrice.getText() == null) {
							inputErrorAddItemContainer.setVisible(true);
						}

						try {
							int x = Integer.parseInt(newContainerX.getText());
							int y = Integer.parseInt(newContainerY.getText());
							int w = Integer.parseInt(newContainerWidth.getText());
							int l = Integer.parseInt(newContainerLength.getText());
							Float p = Float.parseFloat(newContainerPrice.getText());

						} catch (NumberFormatException nfe) {
							System.out.println("Add Container Error: " + nfe);
							inputErrorAddItemContainer.setVisible(true);
						}

						int x = Integer.parseInt(newContainerX.getText());
						int y = Integer.parseInt(newContainerY.getText());
						int w = Integer.parseInt(newContainerWidth.getText());
						int l = Integer.parseInt(newContainerLength.getText());
						Float p = Float.parseFloat(newContainerPrice.getText());

						ItemContainer newContainer = new ItemContainer(newContainerName.getText(), x, y, w, l, p);
						((ItemContainer) item.getValue()).addItemAbstract(newContainer);

						cancelDialog();

					} else {
						itemContainerNull.setVisible(true);
					}
				}
			});
			break;
		}
	}
}