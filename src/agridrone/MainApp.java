package agridrone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;


import agridrone.model.ItemAbstract;
import agridrone.model.ItemContainer;
import agridrone.view.DashboardController;
import agridrone.view.DialogController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;




public class MainApp extends Application {
	

	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private ItemContainer farm = new ItemContainer("Farm", 0, 0, 600, 800, (float) 200000);
	
//	constructor
	public MainApp() {
		

	}
	


	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Agricultural Drone App");
		
		initRootLayout();
		
		showDashboard();
			
	}
	

	
//	root layout initializer
	public void initRootLayout() {
		try {
			//Load root layout from xml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			//show scene containing root layout
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	dashboard initializer
	public void showDashboard() {
		try {
			//Load dashboard
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Dashboard.fxml"));
			AnchorPane dashboard = (AnchorPane) loader.load();
			
			//set dash to center of root
			rootLayout.setCenter(dashboard);
			
			//give controller access to main app
			DashboardController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//main stage getter
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	//item list getter
	public ItemContainer getItemData() {
		return farm;
	}
	
	//singleton window implementation
	//source: https://stackoverflow.com/questions/41051127/javafx-single-instance-application
	//I have no clue how this works and it is literally the only thing
	//I have tried in 6 hours of attempts that actually makes it where only one instance can exist
	private static final int SINGLE_INSTANCE_LISTENER_PORT = 9999;
	private static final String SINGLE_INSTANCE_FOCUS_MESSAGE = "focus";
	
	private static final String instanceId = UUID.randomUUID().toString();
	
	private static final int FOCUS_REQUEST_PAUSE_MILLIS = 500;
	
    public void init() {
        CountDownLatch instanceCheckLatch = new CountDownLatch(1);

        Thread instanceListener = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(SINGLE_INSTANCE_LISTENER_PORT, 10)) {
                instanceCheckLatch.countDown();

                while (true) {
                    try (
                            Socket clientSocket = serverSocket.accept();
                            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(clientSocket.getInputStream()))
                    ) {
                        String input = in.readLine();
                        System.out.println("Received single instance listener message: " + input);
                        //if an instance already exists
                        if (input.startsWith(SINGLE_INSTANCE_FOCUS_MESSAGE) && primaryStage != null) {
                            Thread.sleep(FOCUS_REQUEST_PAUSE_MILLIS);
                            //push instance to the front
                            Platform.runLater(() -> {
                                System.out.println("To front " + instanceId);
                                primaryStage.setIconified(false);
                                primaryStage.show();
                                primaryStage.toFront();
                            });
                        }
                    } catch (IOException e) {
                        System.out.println("Single instance listener unable to process focus message from client");
                        e.printStackTrace();
                    }
                }
            } catch(java.net.BindException b) {
                System.out.println("SingleInstanceApp already running");

                try (
                        Socket clientSocket = new Socket(InetAddress.getLocalHost(), SINGLE_INSTANCE_LISTENER_PORT);
                        PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
                ) {
                    System.out.println("Requesting existing app to focus");
                    out.println(SINGLE_INSTANCE_FOCUS_MESSAGE + " requested by " + instanceId);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Aborting execution for instance " + instanceId);
                Platform.exit();
            } catch(Exception e) {
                System.out.println(e.toString());
            } finally {
                instanceCheckLatch.countDown();
            }
        }, "instance-listener");
        instanceListener.setDaemon(true);
        instanceListener.start();

        try {
            instanceCheckLatch.await();
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }
  

	public static void main(String[] args) {
		launch(args);
	}
}