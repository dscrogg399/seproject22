package agridrone.model;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Drone extends Item{
	
	
	//Sequences for animations
	SequentialTransition sequence = new SequentialTransition();
	SequentialTransition sequence2 = new SequentialTransition();
	SequentialTransition sequence3 = new SequentialTransition();
	SequentialTransition sequence4 = new SequentialTransition();
	SequentialTransition sequence5 = new SequentialTransition();
	SequentialTransition sequence6 = new SequentialTransition();
	SequentialTransition sequence7 = new SequentialTransition();
	SequentialTransition sequence8 = new SequentialTransition();
	
	//Transitions to do animations
	TranslateTransition translate = new TranslateTransition();
	TranslateTransition translate2 = new TranslateTransition();
	TranslateTransition translate3 = new TranslateTransition();
	TranslateTransition translate4 = new TranslateTransition();
	TranslateTransition translate5 = new TranslateTransition();
	TranslateTransition translate6 = new TranslateTransition();
	TranslateTransition translate7 = new TranslateTransition();
	TranslateTransition translate8 = new TranslateTransition();
	TranslateTransition translate9 = new TranslateTransition();
	
	
	public Drone(String name, int x, int y, int w, int l, int h, double price, double mv) {
		super(name, x, y, w, l, h, price, mv);
	}

	public void gotoItem(ItemAbstract item, ImageView drone, Button scanButton) {
		scanButton.setText("Scan Farm");
		if (this.getLocationX() != item.getLocationX() || this.getLocationY() != item.getLocationY()) {
			//goto item
			translate7.setNode(drone);
			double itemx = 0;
			double itemy = 0;

			itemx = item.getLocationX() - drone.getLayoutX();
			itemy = item.getLocationY() - drone.getLayoutY();
			
			translate7.setToX(itemx);
			translate7.setToY(itemy);
			
			
			this.setLocationX(item.getLocationX());
			this.setLocationY(item.getLocationY());
			sequence.stop();
			sequence = new SequentialTransition(translate7);
			sequence.play();
		} else {
			System.out.println("Drone currently at " + item.getName() + ".");
		}
	}
	
	public void gotoParent(ImageView drone, Button scanButton) {
		scanButton.setText("Scan Farm");
		if (this.getLocationX() != this.getParentContainer().getLocationX() || this.getLocationY() != this.getParentContainer().getLocationY()) {
			//goto parent
			translate8.setNode(drone);
			translate8.setToX(-drone.getLayoutX() + this.getParentContainer().getLocationX() + 5);
			translate8.setToY(-drone.getLayoutY() - (-this.getParentContainer().getLocationY() - 5));
			
			this.setLocationX(this.getParentContainer().getLocationX() + 5);
			this.setLocationY(this.getParentContainer().getLocationY() + 5);
			
			sequence.stop();
			sequence = new SequentialTransition(translate8);
			sequence.play();
		} else {
			System.out.println("Drone currently at " + this.getName() + ".");
		}
	}
	
	public void scanFarm(double VH, double VW, ImageView drone, Button scanButton) {
		//scanFarm
		//If pressed again while running, it cancels the animation for scan farm
		scanButton.setText("Cancel Scan");
		if (sequence.getStatus() == Animation.Status.RUNNING) {

			sequence.stop();
			gotoParent(drone, scanButton);
			scanButton.setText("Scan Farm");
		}
		else {
		translate.setNode(drone);
		translate2.setNode(drone);
		translate3.setNode(drone);
		translate4.setNode(drone);
		translate5.setNode(drone);
		translate6.setNode(drone);
		translate7.setNode(drone);
		Duration time = Duration.millis(1000);
		translate.setDuration(time);
		translate2.setDuration(time);
		translate3.setDuration(time);
		translate4.setDuration(time);
		translate5.setDuration(time);
		translate5.setDuration(time);

		translate.setToX(-drone.getLayoutX());
		translate.setToY(-drone.getLayoutY());
		
		translate2.setByX(VW*.85);
		translate3.setByY(this.getLength());
		translate4.setByX(-VW*.85);
		translate5.setByY(this.getLength());
		
		
		translate6.setToX(-drone.getLayoutX() + this.getParentContainer().getLocationX() + 5);
		translate6.setToY(-drone.getLayoutY() - (-this.getParentContainer().getLocationY()) + 5);
		
		
		this.setLocationX(0);
		this.setLocationY(0);
		
		sequence2 = new SequentialTransition(translate2, translate3, translate4, translate5 );
		sequence3 = new SequentialTransition(translate2, translate3, translate4, translate5 );
		sequence4 = new SequentialTransition(translate2, translate3, translate4, translate5 );
		sequence5 = new SequentialTransition(translate2, translate3, translate4, translate5 );
		sequence6 = new SequentialTransition(translate2, translate3, translate4, translate5 );
		sequence7 = new SequentialTransition(translate6);
		sequence = new SequentialTransition(translate, sequence2, sequence3, sequence4, sequence5, sequence6,sequence7);
		
		sequence.play();
		sequence.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				scanButton.setText("Scan Farm");
			}
			
		});
		
		
		
		this.setLocationX(this.getParentContainer().getLocationX() + 40);
		this.setLocationY(this.getParentContainer().getLocationY() + 40);
		
		}

	}
	
	public void delete() {
		System.out.println("Cannot delete Drone Item");
	}

}
