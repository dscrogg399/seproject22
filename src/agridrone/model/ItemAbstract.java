package agridrone.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class ItemAbstract {
	private String name;
	private int locationX;
	private int locationY;
	private int width;
	private int length;
	private float price;
	private ItemContainer parentContainer;
	
	public ItemAbstract(String name, int x, int y, int w, int l, Float price) {
		this.name = name;
		this.locationX = x;
		this.locationY = y;
		this.width = w;
		this.length = l;
		this.price = price;
		this.parentContainer = null;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getLocationX() {
		return this.locationX;
	}
	public void setLocationX(int x) {
		this.locationX = x;
	}
	
	public int getLocationY() {
		return this.locationY;
	}
	public void setLocationY(int y) {
		this.locationY = y;
	}
	
	public int getWidth() {
		return this.width;
	}
	public void setWidth(int w) {
		this.width = w;
	}
	
	public int getLength() {
		return this.length;
	}
	public void setLength(int l) {
		this.length = l;
	}
	
	public float getPrice() {
		return this.price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	public ItemContainer getParentContainer() {
		return this.parentContainer;
	}
	public void setParentContainer(ItemContainer parent) {
		this.parentContainer = parent;
	}
	
	public abstract void delete();
	
	

	//Delete method for items
	public void lete() {
		
		if (this instanceof Drone) {
			System.out.println("Cannot delete Drone");
			return;
		}
	

		
	}
	
	

	
}
