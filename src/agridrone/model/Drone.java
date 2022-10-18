package agridrone.model;

public class Drone extends Item{
	
	public Drone(String name, int x, int y, int w, int l, int price) {
		super(name, x, y, w, l, price);
	}
	
	public void gotoItem(ItemAbstract item) {
		if (this.getLocationX() != item.getLocationX() && this.getLocationY() != item.getLocationY()) {
			//goto item
		} else {
			System.out.println("Drone currently at " + item.getName() + ".");
		}
	}
	
	public void gotoParent() {
		if (this.getLocationX() != this.getParentContainer().getLocationX() && this.getLocationY() != this.getParentContainer().getLocationY()) {
			//goto parent
		} else {
			System.out.println("Drone currently at " + this.getName() + ".");
		}
	}
	
	public void scanFarm() {
		//scanFarm
	}

}
