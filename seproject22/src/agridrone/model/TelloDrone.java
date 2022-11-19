package agridrone.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Drone_adapter.Flight_Capabilities;



public class TelloDrone implements Flight_Capabilities {
	
	private final int maxGoto = 500, minGoto = 1, minDist = 1, maxSpeed = 100, minSpeed = 10, maxDegrees = 360, minDegrees = 1;
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
		TimeUnit.MILLISECONDS.sleep(seconds);
		return;
		
		
	}
	
	public void stopInPlace() throws IOException {
		this.Controller.sendCommand("stop");
	}

	@Override
	public int getDroneHeight() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public void Gotoitem() throws IOException, InterruptedException {
		
		activateSDK();
		launch();
		if (drone.Checkrotation == 0) {
			TurnCCW(drone.degreeCCW);
		}
		else if (drone.Checkrotation == 1) {
			TurnCW(drone.degreeCW);
		}
		
		flyforward(drone.forwardDist);
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
		
		
		land();
		end();
		
	}
	
	public void ScanFarm() throws IOException, InterruptedException {
		
		
		activateSDK();
		launch();
		
		
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
		land();
		end();
		
		
		
	}
	
	public void examplefly() throws IOException, InterruptedException{
		
		activateSDK();
		launch();
		flyforward(75);
		TurnCW(180);
		flyforward(75);
		land();
		end();
	}
	
//	public static void main(String[] args) throws IOException, InterruptedException {
//		TelloDrone tello = new TelloDrone(drone);
//
//		System.out.println("Tello Drone Demo" + "\n");
//		System.out.println("Tello: command takeoff land flip forward back left right" + "\n" + "      " + " up down cw ccw speed speed?" + "\n");
//		System.out.println("end -- quit demo" + "\n");
//
//		Scanner scan = new Scanner(System.in);
//
//		String command = scan.nextLine();
//
//		while(!command.equals("end") && command != null && !command.trim().isEmpty()) {
//			tello.Controller.sendCommand(command);
//			command = scan.nextLine();
//		}
//
//		scan.close();
//		tello.end();
//		//tello.controller.closeControlSocket();
//		System.out.println("Exit Program...");
//	}	
	
	
	
	

}
