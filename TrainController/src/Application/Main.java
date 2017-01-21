package Application;

/**
 * Created by aadu on 1/21/17.
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {
	//Class strings
	private String applicationTitle = "Train Controller";

	//Class integers
	private int windowWidth = 950;
	private int windowHight = 750;
	private int inset = 25;
	private int colWidth = 100;
	private int rowIndex = 0;
	private int colIndex = 0;

	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("Application.fxml"));
		primaryStage.setTitle(applicationTitle);

		GridPane grid = new GridPane();
	   	grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(inset, inset, inset, inset));

        //Row 1
        Label trainIDLabel = new Label("Train: ");
        trainIDLabel.setTextAlignment(TextAlignment.RIGHT);
        trainIDLabel.setMinWidth(colWidth);
        trainIDLabel.setAlignment(Pos.CENTER_RIGHT);
        grid.add(trainIDLabel, colIndex, rowIndex);
        colIndex++;

        Text trainIDText = new Text();
        trainIDText.setWrappingWidth(colWidth*2);
        trainIDText.setText("TEMPTRAINID");
        trainIDText.setTextAlignment(TextAlignment.LEFT);
        grid.add(trainIDText, colIndex, rowIndex, 2, 1);
        colIndex = colIndex + 2;

        Label speedLabel = new Label("Speed: ");
        speedLabel.setMinWidth(colWidth*1.5);
        speedLabel.setTextAlignment(TextAlignment.RIGHT);
        speedLabel.setAlignment(Pos.CENTER_RIGHT);
        grid.add(speedLabel, colIndex, rowIndex);
        colIndex++;

        Text speedText = new Text();
        speedText.setText("TEMPSPEED");
        speedText.setWrappingWidth(colWidth*1.5);
        speedText.setTextAlignment(TextAlignment.LEFT);
        grid.add(speedText, colIndex, rowIndex);
        colIndex++;

        final Button manualBtn = new Button("MANUAL");
        HBox hManualBtn = new HBox(0);
        hManualBtn.setMinWidth(colWidth*3);
        manualBtn.setMinWidth(colWidth*2);
        manualBtn.setAlignment(Pos.CENTER);
        hManualBtn.setAlignment(Pos.CENTER_RIGHT);
        hManualBtn.getChildren().add(manualBtn);
        grid.add(hManualBtn, colIndex, rowIndex, 3, 1);
        rowIndex++;
        colIndex = 0;

        //Row 2
        Label routeLabel = new Label("Route: ");
        routeLabel.setTextAlignment(TextAlignment.RIGHT);
        routeLabel.setMinWidth(colWidth);
        routeLabel.setAlignment(Pos.CENTER_RIGHT);
        grid.add(routeLabel, colIndex, rowIndex);
        colIndex++;

        Text routeText = new Text();
        routeText.setWrappingWidth(colWidth*2);
        routeText.setText("TEMPROUTE");
        routeText.setTextAlignment(TextAlignment.LEFT);
        grid.add(routeText, colIndex, rowIndex, 2, 1);
        colIndex = colIndex + 2;

        Label powerLabel = new Label("Route: ");
        powerLabel.setMinWidth(colWidth*1.5);
        powerLabel.setTextAlignment(TextAlignment.RIGHT);
        powerLabel.setAlignment(Pos.CENTER_RIGHT);
        grid.add(powerLabel, colIndex, rowIndex);
        colIndex++;

        Text powerText = new Text();
        powerText.setText("TEMPROUTE");
        powerText.setWrappingWidth(colWidth*1.5);
        powerText.setTextAlignment(TextAlignment.LEFT);
        grid.add(powerText, colIndex, rowIndex);
        colIndex++;

        final Button automaticBtn = new Button("AUTOMATIC");
        HBox hAutomaticBtn = new HBox(0);
        hAutomaticBtn.setMinWidth(colWidth*3);
        automaticBtn.setMinWidth(colWidth*2);
        automaticBtn.setAlignment(Pos.CENTER);
        hAutomaticBtn.setAlignment(Pos.CENTER_RIGHT);
        hAutomaticBtn.getChildren().add(automaticBtn);
        grid.add(hAutomaticBtn, colIndex, rowIndex, 3, 1);
        rowIndex++;
        colIndex = 0;



		Scene scene = new Scene(grid, windowWidth, windowHight);
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
