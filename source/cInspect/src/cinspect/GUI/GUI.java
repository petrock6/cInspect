package cinspect.GUI;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;
import java.util.Collections;

import com.jenkov.crawler.mt.io.CrawlerMT;
import com.jenkov.crawler.util.SameWebsiteOnlyFilter;

import cinspect.main.Main;
import cinspect.web.ResourceRequestType;
import cinspect.web.WebDatabase;
import cinspect.web.WebResource;

public class GUI extends Application {
	Pane textDisplayPane, textInputPane;
	VBox optionsVBox, checkboxVBox;
	TextField inputTextField;
	CheckBox sqlCheck, rceCheck, lfiCheck, xssCheck, 
			 rfiCheck, tsqlCheck, udrjsCheck, appdosCheck,
			 phpinfoCheck, ccssnCheck;;
	Button runButton, stopButton;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		//Initial setup
		BorderPane mainPane = new BorderPane();
		Scene mainScene = new Scene(mainPane, 500, 500);
		mainScene.setRoot(mainPane);
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("cInspect - A Web Vulnerability Scanner");
		
		//Display instantiation
		textDisplayPane = new Pane();
		textDisplayPane.setStyle("-fx-background-color: WHITE");
		textInputPane = new Pane();
		textInputPane.setStyle("-fx-background-color: BLUE");
		textInputPane.setPrefHeight(50);
		checkboxVBox = new VBox();
		checkboxVBox.setStyle("-fx-background-color: RED; -fx-padding: 10");
		checkboxVBox.setSpacing(8);
		checkboxVBox.setPrefSize(80, 200);
		optionsVBox = new VBox();
		optionsVBox.setStyle("-fx-background-color: GREEN; -fx-padding: 5");
		optionsVBox.setSpacing(10);
		optionsVBox.setPrefWidth(125);
		
		//Interactive objects instantiation
		inputTextField = new TextField();
		inputTextField.setPrefWidth(400);
		inputTextField.setLayoutX(50);
		inputTextField.setLayoutY(15);
		inputTextField.setOnAction(new TextFieldHandler());
		sqlCheck = new CheckBox("SQL");
		rceCheck = new CheckBox("RCE");
		lfiCheck = new CheckBox("LFI");
		xssCheck = new CheckBox("XSS");
		rfiCheck = new CheckBox("RFI");
		tsqlCheck = new CheckBox("Timed SQL");
		udrjsCheck = new CheckBox("UDRJS");
		appdosCheck = new CheckBox("AppDoS");
		phpinfoCheck = new CheckBox("Phpinfo");
		ccssnCheck = new CheckBox("CC/SSN");
		runButton = new Button("Run Program");
		runButton.setOnAction(new RunButtonHandler());
		stopButton = new Button("STOP");
		stopButton.setOnAction(new StopButtonHandler());
		
		
		//Add items to VBox
		optionsVBox.getChildren().add(checkboxVBox);
		checkboxVBox.getChildren().addAll(sqlCheck, rceCheck, lfiCheck, xssCheck, 
			 rfiCheck, tsqlCheck, udrjsCheck, appdosCheck, phpinfoCheck, ccssnCheck);
		optionsVBox.getChildren().addAll(runButton, stopButton);
		
		//Add items to Bottom Pane
		textInputPane.getChildren().add(inputTextField);
		
		//BorderPane layout
		mainPane.setCenter(textDisplayPane);
		mainPane.setBottom(textInputPane);
		mainPane.setRight(optionsVBox);
		
		
		//Display App
		primaryStage.show();
		
	}
	
	public static void print(String output){
		System.out.println(output);
	}
	
	public class TextFieldHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			String input = inputTextField.getText();
			inputTextField.setText("");
			System.out.println(input);
		}
	}
	
	public class StopButtonHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event){
			print("---------- TERMINATING ----------");
			
			for(Thread t : Main.threads) {
				t.interrupt();
			}
			
			Main.threads.clear();
		}
	}
	
	public class RunButtonHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			Main main = new Main();
			//String url = "http://localhost/vulnerabilites/"; //Change this to whatever it needs to be
			
			String url = "http://192.168.1.29/";
	        CrawlerMT crawler  = new CrawlerMT(new SameWebsiteOnlyFilter(url));
	        crawler.addUrl(url);
	        crawler.crawl();
	        
	        System.out.println("------------ DONE ----------");
	        WebDatabase.printDatabase();
			
	        System.out.println("\n\n\n");
			
			List<WebResource> resources = WebDatabase.getDatabase(); //this needs to be updated.
			Collections.reverse(resources);
			
			System.out.println("\n");
			
			
			Main.spawnThreads(3, sqlCheck.isSelected(), rceCheck.isSelected(), lfiCheck.isSelected(), xssCheck.isSelected(), rfiCheck.isSelected(), tsqlCheck.isSelected(), udrjsCheck.isSelected(), appdosCheck.isSelected(), phpinfoCheck.isSelected(), ccssnCheck.isSelected());
		
		}
	}
}