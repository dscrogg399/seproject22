package Drone_adapter;

import java.io.IOException;

public abstract class PhysicalDrone {
	
	public abstract void setSpeed(int speed) throws IOException;
	
	
	public abstract double getSpeed() throws NumberFormatException, IOException;
	
	

}
