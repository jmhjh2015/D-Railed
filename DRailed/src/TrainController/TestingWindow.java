package TrainController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by aadu on 2/14/17.
 */
public class TestingWindow {
	private final Stage stage = new Stage();

	private TrainController trainController;

	//Create Scene
	private Scene scene;

	//Class strings
	private String windowTitle = "Testing Window";

	//Class integers
	private int windowWidth = 300;
	private int windowHight = 300;
	private int inset = 25;
	private int colWidth = 75;

	public TestingWindow(TrainController tc)
	{
		trainController = tc;

		stage.setTitle(windowTitle);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(inset, inset, inset, inset));
		grid.setVgap(inset);

		TextField actualSpeedField = new TextField();
		TextField inputSpeedField = new TextField();
		Text actualLabel = new Text("Actual Speed (mi/hr): ");
		Text inputLabel = new Text("Desired Speed (mi/hr): ");
		Button okBtn = new Button("OK");
		Button cancelBtn = new Button("CANCEL");


		grid.add(actualLabel, 0, 0);

		actualSpeedField.setMinWidth(colWidth);
		grid.add(actualSpeedField, 1, 0);

		grid.add(inputLabel, 0, 1);

		inputSpeedField.setMinWidth(colWidth);
		grid.add(inputSpeedField, 1, 1);

		HBox hOKBtn = new HBox();
		hOKBtn.setAlignment(Pos.CENTER);
		hOKBtn.setMinWidth(colWidth);
		hOKBtn.getChildren().add(okBtn);
		grid.add(hOKBtn, 0, 2);

		HBox hCancelBtn = new HBox();
		hCancelBtn.setAlignment(Pos.CENTER);
		hCancelBtn.setMinWidth(colWidth);
		hCancelBtn.getChildren().add(cancelBtn);
		grid.add(hCancelBtn, 1, 2);

		Scene scene = new Scene(grid, windowWidth, windowHight);
		stage.setScene(scene);
		stage.show();



		//trainController.MakeAnnouncement("Test Announcement");
	}


}
