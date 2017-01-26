package Application;

/*
 * Created by Jon on 1/25/17.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Main extends Application {

	//Class Objects
	private Stage mainStage, sideStage;
	private Scene mainScene, murphyScene, userInScene, engScene, toTMScene;
	private Label testLabel;
    private Button murphyButton, userInputsButton, engInputsButton, toTrackModelButton, murphyBreakTrackButton;


	@Override
	public void start(Stage primaryStage) throws Exception {

	    //Set variables
        int windowWidth = 950;
        int windowHeight = 250;
        int sideWidth = 400;
        int sideHeight = 200;
        int inset = 25;
        int colWidth = 100;
        int rowIndex = 0;
        int colIndex = 0;
        String applicationTitle = "Track Controller";

	    //Set Stages
        mainStage = primaryStage;
        sideStage = new Stage();
        mainStage.setTitle(applicationTitle);

        //Create Panes
        GridPane main = new GridPane();
        GridPane murphy = new GridPane();
        GridPane userInputs = new GridPane();
        GridPane engInputs = new GridPane();
        GridPane toTrackModel = new GridPane();


        //Create Scenes
        mainScene = new Scene(main, windowWidth, windowHeight);
        murphyScene =  new Scene(murphy, sideWidth,sideHeight);
        userInScene =  new Scene(userInputs, sideWidth,sideHeight);
        engScene =  new Scene(engInputs, sideWidth,sideHeight);
        toTMScene =  new Scene(toTrackModel, sideWidth,sideHeight);

        //Initialize Main Buttons
        murphyButton = new Button("Murphy Controls");
        userInputsButton = new Button("CTC Inputs");
        engInputsButton = new Button("Engineer Inputs");
        toTrackModelButton = new Button("Inputs To Track Model");

        //Initialize Murphy Buttons
        murphyBreakTrackButton = new Button("Break Track");


        main.setAlignment(Pos.TOP_CENTER);
        main.setPadding(new Insets(inset, inset, inset, inset));
        main.setGridLinesVisible(true);

        //Set up items on main screen
        testLabel = new Label("Test");
        testLabel.setMinWidth(colWidth);
        murphyButton.setOnAction(e -> MainButtonClicked(e));
        murphyButton.setMinWidth(colWidth);
        userInputsButton.setOnAction(e -> MainButtonClicked(e));
        userInputsButton.setMinWidth(colWidth);
        engInputsButton.setOnAction(e -> MainButtonClicked(e));
        engInputsButton.setMinWidth(colWidth);
        toTrackModelButton.setOnAction(e -> MainButtonClicked(e));
        toTrackModelButton.setMinWidth(colWidth);

        //Set up main GridPane
        main.add(murphyButton, 0, 0);
        main.add(userInputsButton, 0, 1);
        main.add(engInputsButton, 1, 0);
        main.add(toTrackModelButton, 1, 1);
        main.add(testLabel, 10, 2);


        //Set up items on murphy GridPane
        murphyBreakTrackButton.setOnAction(e -> MurphyButtonClicked(e));
        murphy.add(murphyBreakTrackButton, 0, 0);

/*      //Row 1
		Label trainIDLabel = new Label("Train: ");
		trainIDLabel.setTextAlignment(TextAlignment.RIGHT);
		trainIDLabel.setMinWidth(colWidth);
		trainIDLabel.setAlignment(Pos.CENTER_RIGHT);
		main.add(trainIDLabel, colIndex, rowIndex);
		colIndex++;

		Text trainIDText = new Text();
		trainIDText.setWrappingWidth(colWidth*2);
		trainIDText.setText("TEMPTRAINID");
		trainIDText.setTextAlignment(TextAlignment.LEFT);
		main.add(trainIDText, colIndex, rowIndex, 2, 1);
		colIndex = colIndex + 2;

		Label speedLabel = new Label("Speed: ");
		speedLabel.setMinWidth(colWidth*1.5);
		speedLabel.setTextAlignment(TextAlignment.RIGHT);
		speedLabel.setAlignment(Pos.CENTER_RIGHT);
		main.add(speedLabel, colIndex, rowIndex);
		colIndex++;

		Text speedText = new Text();
		speedText.setText("TEMPSPEED");
		speedText.setWrappingWidth(colWidth*1.5);
		speedText.setTextAlignment(TextAlignment.LEFT);
		main.add(speedText, colIndex, rowIndex);
		colIndex++;

		final Button manualBtn = new Button("MANUAL");
		HBox hManualBtn = new HBox(0);
		hManualBtn.setMinWidth(colWidth*3);
		manualBtn.setMinWidth(colWidth*2);
		manualBtn.setAlignment(Pos.CENTER);
		hManualBtn.setAlignment(Pos.CENTER_RIGHT);
		hManualBtn.getChildren().add(manualBtn);
		main.add(hManualBtn, colIndex, rowIndex, 3, 1);
		rowIndex++;
		colIndex = 0;

		//Row 2
		Label routeLabel = new Label("Route: ");
		routeLabel.setTextAlignment(TextAlignment.RIGHT);
		routeLabel.setMinWidth(colWidth);
		routeLabel.setAlignment(Pos.CENTER_RIGHT);
		main.add(routeLabel, colIndex, rowIndex);
		colIndex++;

		Text routeText = new Text();
		routeText.setWrappingWidth(colWidth*2);
		routeText.setText("TEMPROUTE");
		routeText.setTextAlignment(TextAlignment.LEFT);
		main.add(routeText, colIndex, rowIndex, 2, 1);
		colIndex = colIndex + 2;

		Label powerLabel = new Label("Route: ");
		powerLabel.setMinWidth(colWidth*1.5);
		powerLabel.setTextAlignment(TextAlignment.RIGHT);
		powerLabel.setAlignment(Pos.CENTER_RIGHT);
		main.add(powerLabel, colIndex, rowIndex);
		colIndex++;

		Text powerText = new Text();
		powerText.setText("TEMPROUTE");
		powerText.setWrappingWidth(colWidth*1.5);
		powerText.setTextAlignment(TextAlignment.LEFT);
		main.add(powerText, colIndex, rowIndex);
		colIndex++;

		final Button automaticBtn = new Button("AUTOMATIC");
		HBox hAutomaticBtn = new HBox(0);
		hAutomaticBtn.setMinWidth(colWidth*3);
		automaticBtn.setMinWidth(colWidth*2);
		automaticBtn.setAlignment(Pos.CENTER);
		hAutomaticBtn.setAlignment(Pos.CENTER_RIGHT);
		hAutomaticBtn.getChildren().add(automaticBtn);
		main.add(hAutomaticBtn, colIndex, rowIndex, 3, 1);
		rowIndex++;
		colIndex = 0;*/


        mainStage.setScene(mainScene);
        mainStage.show();
    }

    public void MurphyButtonClicked(ActionEvent e)
    {
        Object source = e.getSource();
        if(source == murphyBreakTrackButton)
        {
            testLabel.setText("From Murphy");
        }
    }

    public void MainButtonClicked(ActionEvent e)
    {
        String newTitle = "Unknown Event";
        Object source = e.getSource();
        if(source == murphyButton)
        {
            sideStage.setScene(murphyScene);
            newTitle = "Murphy Controls";
        }
        else if(source == userInputsButton)
        {
            sideStage.setScene(userInScene);
            newTitle = "User Inputs";
        }
        else if(source == engInputsButton)
        {
            sideStage.setScene(engScene);
            newTitle = "Engineer Inputs";
        }
        else if(source == toTrackModelButton)
        {
            sideStage.setScene(toTMScene);
            newTitle = "Output To Track Model";
        }
        sideStage.setTitle(newTitle);
        sideStage.show();
    }


	public static void main(String[] args) {
		launch(args);
	}
}
