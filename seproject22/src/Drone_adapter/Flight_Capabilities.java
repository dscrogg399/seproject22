package Drone_adapter;

import java.io.IOException;

public interface Flight_Capabilities {
	
	
	
	public void launch() throws IOException;
	
	public void land() throws IOException;
	
	public void IncreaseAltitude(int up) throws IOException;
	
	public void DecreaseAltitude(int down) throws IOException;
	
	public void flyforward(int forward) throws IOException;
	
	public void flybackward(int backward) throws IOException;
	
	public void flyright(int right) throws IOException;
	
	public void flyleft(int left) throws IOException;
	
	public int getDroneHeight() throws IOException;
	
	public void TurnCW(double degrees) throws IOException;
	
	public void TurnCCW(double degrees) throws IOException;
	
	
	public double getAccelerationX() throws IOException;
	
	public double getAccelerationY() throws IOException;

	/***
	 * Gets aircraft's IMU angular acceleration data and returns the Z component
	 * @return double precision acceleration value based on SDK units
	 * @throws IOException 
	 */
	public double getAccelerationZ() throws IOException;

	public void setspeed(int speed) throws IOException;
	
	public double getspeed() throws NumberFormatException, IOException;
	
	public void getbattery() throws NumberFormatException, IOException;
	
	public void hoverInPlace(int seconds) throws InterruptedException, IOException;
	
	
	
	
	

}
