package cinspect.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;

import java.util.List;
import java.util.Collections;

import com.jenkov.crawler.mt.io.CrawlerMT;
import com.jenkov.crawler.util.SameWebsiteOnlyFilter;

import cinspect.main.Main;
import cinspect.web.ResourceRequestType;
import cinspect.web.WebDatabase;
import cinspect.web.WebResource;

import com.aquafx_project.AquaFx;

public class GUI extends Application {
	Pane textDisplayPane, textInputPane;
	VBox optionsVBox, checkboxVBox;
	TextField inputTextField;
	CheckBox sqlCheck, rceCheck, lfiCheck, xssCheck, 
			 rfiCheck, tsqlCheck, udrjsCheck, appdosCheck,
			 phpinfoCheck, ccssnCheck;
	Button runButton, stopButton, closeButton, minimizeButton, maximizeButton;
	static TextArea text;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		//Initial setup
		AquaFx.style();
		
		BorderPane mainPane = new BorderPane();
		Scene mainScene = new Scene(mainPane, 500, 500);
		mainScene.setRoot(mainPane);
		mainScene.getStylesheets().add("cInspect/GUI/ButtonStyle.css");
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("cInspect - A Web Vulnerability Scanner");
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		
		//Display instantiation
		textDisplayPane = new Pane();
		textInputPane = new Pane();
		textInputPane.setPrefHeight(50);
		checkboxVBox = new VBox();
		checkboxVBox.setStyle("-fx-padding: 10; -fx-background-color: rgb(230.0, 230.0, 230.0);");
		checkboxVBox.setSpacing(8);
		checkboxVBox.setPrefSize(80, 200);
		optionsVBox = new VBox();
		optionsVBox.setStyle("-fx-padding: 5");
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
		
		//Toolbar
		closeButton = new Button("  ");
		closeButton.getStyleClass().add("close");
		minimizeButton = new Button("  ");
		minimizeButton.getStyleClass().add("minimize");
		maximizeButton = new Button("  ");
		maximizeButton.getStyleClass().add("maximize");
		ToolBar toolbar = new ToolBar();
		toolbar.getItems().addAll(closeButton, new Label(" "), 
				minimizeButton, new Label(" "), maximizeButton);
		
		//Scrolling Text
		int textOffset = 5;
		text = new TextArea();
		text.setEditable(false);
		text.setPrefSize(375-textOffset, 400);
		text.setLayoutX(textOffset);
		text.setLayoutY(textOffset);
		
		//Add items to VBox
		optionsVBox.getChildren().add(checkboxVBox);
		checkboxVBox.getChildren().addAll(sqlCheck, rceCheck, lfiCheck, xssCheck, 
			 rfiCheck, tsqlCheck, udrjsCheck, appdosCheck, phpinfoCheck, ccssnCheck);
		optionsVBox.getChildren().addAll(runButton, stopButton);
		
		//Add items to Bottom Pane
		textInputPane.getChildren().add(inputTextField);
		
		//Add items to Center Pane
		textDisplayPane.getChildren().add(text);
		
		//BorderPane layout
		mainPane.setCenter(textDisplayPane);
		mainPane.setBottom(textInputPane);
		mainPane.setRight(optionsVBox);
		mainPane.setTop(toolbar);
		
		//Display App
		primaryStage.show();
		
	}
	
	public static void print(String output){
		System.out.println(output);
		text.appendText(output+"\n");
	}
	
	public class TextFieldHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			String input = inputTextField.getText();
			inputTextField.setText("");
			print(input);
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
			//Main main = new Main();
			//String url = "http://localhost/vulnerabilites/"; //Change this to whatever it needs to be
			
			String url = "http://192.168.1.29/";
	        CrawlerMT crawler  = new CrawlerMT(new SameWebsiteOnlyFilter(url));
	        crawler.addUrl(url);
	        crawler.crawl();
	        
	        print("------------ DONE ----------");
	        WebDatabase.printDatabase();
			
	        print("\n\n\n");
			
			List<WebResource> resources = WebDatabase.getDatabase(); //this needs to be updated.
			Collections.reverse(resources);
			
			print("\n");
			
			
			Main.spawnThreads(3, sqlCheck.isSelected(), rceCheck.isSelected(), lfiCheck.isSelected(), xssCheck.isSelected(), rfiCheck.isSelected(), tsqlCheck.isSelected(), udrjsCheck.isSelected(), appdosCheck.isSelected(), phpinfoCheck.isSelected(), ccssnCheck.isSelected());
		
		}
	}
}