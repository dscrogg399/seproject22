package agridrone.model;

public class Item extends ItemAbstract{
	public Item(String name, int x, int y, int w, int l, int h, Float p) {
		super(name, x, y, w, l, h, p);
	}
	
	public void delete() {
		this.getParentContainer().getContents().remove(this);
	}
}
