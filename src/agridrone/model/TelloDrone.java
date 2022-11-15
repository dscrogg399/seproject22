package agridrone.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import Drone_adapter.Flight_Capabilities;


public class TelloDrone implements Flight_Capabilities {
	
	private final int maxGoto = 730, minGoto = -500, minDist = 1, maxSpeed = 100, minSpeed = 10, maxDegrees = 360, minDegrees = 1;
	private final int maxDist = maxGoto;
	
	DroneController Controller;
	
	private Drone drone;
	
	public TelloDrone(Drone drone) throws SocketException, UnknownHostException, FileNotFoundException {
		this.drone = drone;
		Controller = new DroneController(9000, 8889, "192.168.10.1");
	}

	@Override
	public void launch() throws IOException {
		// TODO Auto-generated method stub
		this.Controller.sendCommand("takeoff");
		
	}

	@Override
	public void land() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void IncreaseAltitude(int up) throws IOException {
		// TODO Auto-generated method stub
		if (up <= minDist) this.Controller.sendCommand("up " + minDist);
		else if (up > maxDist) {
			this.Controller.sendCommand("up " + maxDist);
			drone.IncreaseAltitude(Math.abs(maxDist - up));
		}
		//else this.controller.sendCommand("up " + up);
		
	}

	@Override
	public void DecreaseAltitude(int down) throws IOException {
		// TODO Auto-generated method stub
		int height = Math.abs(drone.getDroneHeight()/25);
		if (height - down <= 0) down = height - 10; 
		if (down <= minDist); //this.controller.sendCommand("down " + minDist);
		else if (down > maxDist) {
			//this.controller.sendCommand("down " + maxDist);
			drone.DecreaseAltitude(Math.abs(maxDist - down));
		}
		//else this.controller.sendCommand("down " + down);
		
	}

	@Override
	public void flyforward(int forward) throws IOException {
		// TODO Auto-generated method stub
		forward = Math.abs(forward/25) * 30;
		//this.controller.sendCommand("forward " + forward);
		
		
	}

	@Override
	public void flybackward(int backward) throws IOException {
		// TODO Auto-generated method stub
		Math.abs(backward/25);
	}

	@Override
	public void flyright(int right) throws IOException {
		// TODO Auto-generated method stub
		Math.abs(right/25);
	}

	@Override
	public void flyleft(int left) throws IOException {
		// TODO Auto-generated method stub
		Math.abs(left/25);
	}

	@Override
	public void TurnCW(double degrees) throws IOException {
		// TODO Auto-generated method stub
		Controller.sendCommand("cw" + degrees);
		
		
		
	}

	@Override
	public void TurnCCW(double degrees) throws IOException {
		// TODO Auto-generated method stub
		Controller.sendCommand("ccw" + degrees);
		
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
	public void setspeed(int speed) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getspeed() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void getbattery() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hoverInPlace(int seconds) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDroneHeight() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public void Gotoitem() throws IOException {
		
		if (drone.Checkrotation == 0) {
			TurnCCW(drone.degreeCCW);
		}
		else if (drone.Checkrotation == 1) {
			TurnCW(drone.degreeCW);
		}
		
		flyforward(drone.forwardDist);
		
		TurnCW(180);
		
		flyforward(drone.forwardDist);
		
		if (drone.Checkrotation == 0) {
			TurnCCW(-drone.degreeCCW);
		}
		else if (drone.Checkrotation == 1) {
			TurnCW(-drone.degreeCW);
		}
		
		
	}
	
	public void ScanFarm() throws IOException {
		
		TurnCCW(drone.degreeCCW);
		
		flyforward(15);
		
		TurnCCW(-drone.degreeCCW);
		
		for (int i = 0; i < 6; i++) {
			flyforward(drone.forwardDist);
			TurnCW(drone.degreeCW);
			flyforward((int) (drone.forwardDist * .15));
			TurnCW(drone.degreeCW);
			flyforward(drone.forwardDist);
			TurnCW(-drone.degreeCW);
			flyforward((int) (drone.forwardDist * .15));
			TurnCW(-drone.degreeCW);
		}
		
		TurnCCW(80);
		flyforward(drone.forwardDist * 97);
		TurnCW(80);
		
		
	}
	

}
