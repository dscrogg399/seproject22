package agridrone.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Drone_adapter.Flight_Capabilities;
import agridrone.view.DashboardController;



public class TelloDrone extends DashboardController implements Flight_Capabilities {
	
	//pretend maxheight is the item max height
	private final int maxGoto = 500, minGoto = 1, minDist = 1, maxSpeed = 100, minSpeed = 10, maxDegrees = 360, minDegrees = 1, maxHeight = getSingleton().getMaxHeight(), minheight = 0;
	private final int maxDist = maxGoto;
	
	
	DroneController Controller;
	
	private static Drone drone;						
	
	
	public TelloDrone(Drone drone) throws SocketException, UnknownHostException, FileNotFoundException {
		this.drone = drone;
		Controller = new DroneController(9000, 8889, "192.168.10.1");
	}
	
	public void activateSDK() throws IOException {
		this.Controller.sendCommand("command");
	}

	public void end() throws IOException, InterruptedException {
		this.Controller.closeControlSocket();
		System.out.println("Exit Program...");
	}

	@Override
	public void launch() throws IOException {
		// TODO Auto-generated method stub
		this.Controller.sendCommand("takeoff");
		
	}

	@Override
	public void land() throws IOException {
		// TODO Auto-generated method stub
		this.Controller.sendCommand("land");
		
	}
	

	//takes the height of the item
	public int itemHeight(int height) throws IOException, NumberFormatException {
		// TODO Auto-generated method stub
		height = height * 30;
		return height;
		
	}
	


	@Override
	public void IncreaseAltitude(int up) throws IOException {
		// TODO Auto-generated method stub
		
		this.Controller.sendCommand("up " + up);
		
	}

	
	@Override
	public void DecreaseAltitude(int down) throws IOException {
		// TODO Auto-generated method stub
		int height = itemHeight(drone.ItemH);
		if (height == maxHeight) {
			down = 0;
			this.Controller.sendCommand("down " + down);
		}
		else{
			down = maxHeight - height;			
			this.Controller.sendCommand("down " + down);
		}
		
		
	}

	@Override
	public void flyforward(int forward) throws IOException {
		// TODO Auto-generated method stub
		forward = forward/25 * 30;
		if (forward <= minGoto) {
		this.Controller.sendCommand("forward " + minGoto);
		}
		else if (forward > maxGoto){
			this.Controller.sendCommand("forward " + maxGoto);
			flyforward(Math.abs(forward - maxGoto));
		}
		else {
			this.Controller.sendCommand("forward " + forward);
		}
		
	}


	@Override
	public void TurnCW(double degrees) throws IOException {
		// TODO Auto-generated method stub
		degrees = Math.abs(degrees);
		Controller.sendCommand("cw " + (int) degrees);
		
		
		
	}

	@Override
	public void TurnCCW(double degrees) throws IOException {
		// TODO Auto-generated method stub
		degrees = Math.abs(degrees);
		Controller.sendCommand("ccw " + (int) degrees);
		
	}


	@Override
	public void setspeed(int speed) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getspeed() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public void hoverInPlace(int seconds) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		TimeUnit.MILLISECONDS.sleep(seconds);
		return;
		
		
	}
	
	public void stopInPlace() throws IOException {
		this.Controller.sendCommand("stop");
	}


	public void Gotoitem() throws IOException, InterruptedException {
		
		activateSDK();
		launch();
		IncreaseAltitude(maxHeight + 10);		
		
		
		if (drone.Checkrotation == 0) {
			TurnCCW(drone.degreeCCW);
		}
		else if (drone.Checkrotation == 1) {
			TurnCW(drone.degreeCW);
		}
		
		flyforward(drone.forwardDist);
		DecreaseAltitude(itemHeight(drone.ItemH));
		TurnCCW(360);
		
		hoverInPlace(drone.Hovering);
		
		
		TurnCW(180);
		
		flyforward(drone.forwardDist);
		
		if (drone.Checkrotation == 0) {
			TurnCCW(-drone.degreeCCW + 180);
		}
		else if (drone.Checkrotation == 1) {
			TurnCW(-drone.degreeCW + 180);
		}
		
		DecreaseAltitude((int) Math.round(itemHeight(drone.ItemH) * .95));
		
		
		land();
		end();
		
	}
	
	public void ScanFarm() throws IOException, InterruptedException {
		
		
		activateSDK();
		launch();
		
		IncreaseAltitude(maxHeight + 10);
		for (int i = 0; i < 12; i++) {
			flyforward(drone.forwardDist);
			TurnCW(drone.degreeCW);
			flyforward((int) Math.round(drone.getLength() * .625));
			TurnCW(drone.degreeCW);
			flyforward(drone.forwardDist);
			TurnCCW(drone.degreeCW);
			flyforward((int) Math.round( drone.getLength() * .625));
			TurnCCW(drone.degreeCW);
		}
		
		
		
		TurnCCW(drone.degreeCCW);
		flyforward((int) ((int) drone.forwardDist * .90));
		TurnCW(drone.degreeCW);
		DecreaseAltitude((int) Math.round(itemHeight(drone.ItemH) * .95));
		land();
		end();
		
		
		
	}

	@Override
	public void flybackward(int backward) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flyright(int right) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flyleft(int left) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDroneHeight() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAccelerationX() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAccelerationY() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAccelerationZ() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void getbattery() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		
	}

	
	
	

}

