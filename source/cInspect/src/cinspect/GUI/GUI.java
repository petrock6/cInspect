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

public class GUI extends Application{
	Pane textDisplayPane, textInputPane;
	VBox optionsVBox, checkboxVBox;
	TextField inputTextField;
	CheckBox sqlCheck, rceCheck, lfiCheck, xssCheck, 
			 rfiCheck, tsqlCheck, udrjsCheck, appdosCheck;
	Button runButton;
	
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
		checkboxVBox.setSpacing(5);
		checkboxVBox.setPrefSize(80, 200);
		optionsVBox = new VBox();
		optionsVBox.setStyle("-fx-background-color: GREEN; -fx-padding: 5");
		optionsVBox.setPrefWidth(100);
		
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
		tsqlCheck = new CheckBox("TSQL");
		udrjsCheck = new CheckBox("UDRJS");
		appdosCheck = new CheckBox("APPDOS");
		runButton = new Button("Run Program");
		runButton.setOnAction(new RunButtonEvent());
		
		//Add items to VBox
		optionsVBox.getChildren().add(checkboxVBox);
		checkboxVBox.getChildren().addAll(sqlCheck, rceCheck, lfiCheck, xssCheck, 
			 rfiCheck, tsqlCheck, udrjsCheck, appdosCheck);
		optionsVBox.getChildren().add(runButton);
		
		//Add items to Bottom Pane
		textInputPane.getChildren().add(inputTextField);
		
		//BorderPane layout
		mainPane.setCenter(textDisplayPane);
		mainPane.setBottom(textInputPane);
		mainPane.setRight(optionsVBox);
		
		
		//Display App
		primaryStage.show();
		
	}
	
	public void print(String output){
		System.out.println(output);
	}
	
	public class TextFieldHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			String input = inputTextField.getText();
			inputTextField.setText("");
			System.out.println(input);
		}
	}
	
	public class RunButtonEvent implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			Main main = new Main();
			String url = "http://localhost/vulnerabilites/"; //Change this to whatever it needs to be
	        CrawlerMT crawler  = new CrawlerMT(new SameWebsiteOnlyFilter(url));
	        crawler.addUrl(url);
	        crawler.crawl();
	        
	        System.out.println("------------ DONE ----------");
	        WebDatabase.printDatabase();
			
	        System.out.println("\n\n\n");
			
			List<WebResource> resources = WebDatabase.getDatabase(); //this needs to be updated.
			Collections.reverse(resources);
			
			System.out.println("\n");
			
			System.out.println("Starting test in 5 seconds...");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(WebResource resource : resources) {
				if(!resource.getParameters().isEmpty() ) {
					if(sqlCheck.isSelected()){
						System.out.println("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : SQL Injection\r"); 
						main.testSQLInspector(resource);
					}
					if(rceCheck.isSelected()){
						System.out.println("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : RCE Injection\r");
						main.testRCEInspector(resource);
					}
					if(lfiCheck.isSelected()){
						System.out.println("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : LFI\r");
						main.testLFIInspector(resource);
					}
					if(xssCheck.isSelected()){
						System.out.println("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : XSS Injection\r");
						main.testXSSInspector(resource);
					}
					if(rfiCheck.isSelected()){
						System.out.println("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : RFI\r");
						main.testRFIInspector(resource);
					}
					if(tsqlCheck.isSelected()){
						System.out.println("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : TimeSQL Injection\r");
						main.testTimeSQLInspector(resource);
					}
					if(udrjsCheck.isSelected()){
						System.out.println("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : UDR\r");
						main.testUDRJSInspector(resource);
					}
					if(appdosCheck.isSelected()){
						System.out.println("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : Application DoS\r");
						main.testAppDoSInspector(resource);
					}
				}
				System.out.println("\n\n");
			}	
			System.out.println("--- DONE ---");
		}
		
	}
}