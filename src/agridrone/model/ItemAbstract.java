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
	private ItemAbstract parentContainer;
	private ObservableList<ItemAbstract> contents = FXCollections.observableArrayList();
	
	public ItemAbstract(String name, int x, int y, int w, int l, int price) {
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
	
	public ItemAbstract getParentContainer() {
		return this.parentContainer;
	}
	public void setParentContainer(ItemAbstract parent) {
		this.parentContainer = parent;
	}
	
	public ObservableList<ItemAbstract> getContents() {
		if (!(this instanceof Item)){
			return this.contents;
		}
		return null;
	}
	public void addItemContainer(ItemContainer container) {
		
		if (!(this instanceof Item)) {
			container.setParentContainer(this);
			this.contents.add(container);
		} else {
			System.out.println("Items cannot hold containers");
		}
	}

	public void addItem(Item item) {
		if(!(this instanceof Item)) {
			item.setParentContainer(this);
			this.contents.add(item);
		} else {
			System.out.println("Items cannot hold other items");
		}
		
	}
	
	public void addItemAbstract(ItemAbstract item) {
		if(!(this instanceof Item)) {
			item.setParentContainer(this);
			this.contents.add(item);
		} else {
			System.out.println("Items cannot hold other objects");
		}
	}
	
	public void delete() {
		

		if (this.parentContainer != null) {
			//parent of current item
			ItemAbstract newParent = this.parentContainer;

			
			//if container list is not empty, set all sub containers' parents to parent of current item
			if (this.contents != null) {
				for (ItemAbstract cont : this.contents) {
					
					newParent.addItemAbstract(cont);
				}
			}
			
			//deleting current item
			this.parentContainer.contents.remove(this);
		} else {
			System.out.println("Cannot delete Farm item container");
		}
		
	}
	
	

	
}
