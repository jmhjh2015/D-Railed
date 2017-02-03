package TrainSystem.MBO;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MBOController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../UI/MBOUI.fxml"));

        Screen mainScreen = Screen.getPrimary();
        Rectangle2D screenBounds = mainScreen.getVisualBounds();


        primaryStage.setTitle("MBO Interface");
        primaryStage.setScene(new Scene(root, screenBounds.getWidth(), screenBounds.getHeight()));

        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
