module seproject22 {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	
	opens agridrone to javafx.graphics, javafx.fxml;
	opens agridrone.view to javafx.graphics, javafx.fxml;
	opens agridrone.model to javafx.graphics, javafx.fxml;
	
	exports agridrone;
	exports agridrone.view;
	exports agridrone.model;
}
