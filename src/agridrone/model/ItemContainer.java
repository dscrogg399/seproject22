package agridrone.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemContainer extends ItemAbstract {
	
	private ObservableList<ItemAbstract> contents = FXCollections.observableArrayList();
	
	public ItemContainer(String name, int x, int y, int w, int l, Float p) {
		super(name, x, y, w, l, p);
	}
	
	public ObservableList<ItemAbstract> getContents() {
		return this.contents;
	}
	
	public void addItemAbstract(ItemAbstract item) {
		item.setParentContainer(this);
		this.contents.add(item);
	}
	
	public void delete() {
		if (this.getParentContainer() != null) {
			//parent of current item
			ItemContainer newParent = this.getParentContainer();

			
			//if container list is not empty, set all sub containers' parents to parent of current item
			if (this.contents != null) {
				for (ItemAbstract cont : this.contents) {
					
					newParent.addItemAbstract(cont);
				}
			}
			
			//deleting current item
			this.getParentContainer().getContents().remove(this);
			return;
		} else {
			System.out.println("Cannot delete Farm item container");
			return;
		}
	}


}
