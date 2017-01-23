package Application.Track.GUI;

/**
 * Created by andrew on 1/21/17.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TrackModelGUI extends Application {

    //Class strings
    private String applicationTitle = "Track Model";

    //Class integers
    private int windowWidth = 950;
    private int windowHeight = 550;
    private int inset = 25;
    private int gridBlock = 50;
    private int rowIndex = 0;
    private int colIndex = 0;

    final String[] lineNames = new String[]{"Green", "Red", "BLUE", "ORANGE", "YELLOW", "INDIGO", "VIOLET", "NAVY", "ROSE", "PURPLE", "BLACK", "WHITE"};
    final String[] sectionNames = new String[]{"A", "B"};
    final String[] blockNames = new String[]{"1", "2", "3"};

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Application/Track/Model/TrackModelFX.fxml"));
        stage.setTitle(applicationTitle);

        /////////////////////////////////
        // Grid Segments               //
        /////////////////////////////////

        // MAIN top pane
        GridPane mainGrid = new GridPane();
        mainGrid.setAlignment(Pos.TOP_CENTER);
        mainGrid.setPadding(new Insets(0, 0, 0, 0));

        // TRACK LAYOUT top pane

        GridPane trackLayout = new GridPane();
        trackLayout.setAlignment(Pos.TOP_LEFT);
        trackLayout.setMinHeight((windowHeight/2)-10);
        trackLayout.setMaxHeight((windowHeight/2)-10);
        trackLayout.setMinWidth(windowWidth);
        trackLayout.setMaxWidth(windowWidth);

        // BLOCK MONITOR LAYOUT bottom pane

        GridPane blockMonitor = new GridPane();
        blockMonitor.setAlignment(Pos.TOP_LEFT);
        blockMonitor.setMinHeight((windowHeight/2)-10);
        blockMonitor.setMaxHeight((windowHeight/2)-10);
        blockMonitor.setMinWidth(windowWidth);
        blockMonitor.setMaxWidth(windowWidth);

        // BLOCK INFO bottom left pane
        GridPane blockInfo = new GridPane();
        blockInfo.setAlignment(Pos.TOP_LEFT);
        blockInfo.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        blockInfo.setMinHeight(windowHeight/2);
        blockInfo.setMaxHeight(windowHeight/2);
        blockInfo.setMinWidth(windowWidth/3);
        blockInfo.setMaxWidth(windowWidth/3);

        // BLOCK INFRASTRUCTURE bottom center pane
        GridPane blockInfra = new GridPane();
        blockInfra.setAlignment(Pos.TOP_LEFT);

        // BLOCK STATUS bottom right pane
        GridPane blockStatus = new GridPane();
        blockStatus.setAlignment(Pos.TOP_LEFT);

        ///////////////////////////////////
        // Layout Menu                   //
        ///////////////////////////////////

        // Label
        Label layoutMenuTitle = new Label("TrackLayoutMenu");
        layoutMenuTitle.setPadding(new Insets(0,0,0,10));
        layoutMenuTitle.setTextAlignment(TextAlignment.LEFT);
        layoutMenuTitle.setMinWidth(gridBlock);
        layoutMenuTitle.setMinHeight(10);
        layoutMenuTitle.setAlignment(Pos.CENTER_LEFT);

        // Menu Layout
        TitledPane[] linePanes = new TitledPane[lineNames.length];
        TitledPane[] sectionPanes = new TitledPane[sectionNames.length];
        Button[] blockButtons = new Button[blockNames.length];
        VBox[] buttonBoxes = new VBox[blockNames.length];

        Accordion lineAccordion = new Accordion();
        Accordion[] sectionAccordions = new Accordion[lineNames.length];

        for(int i = 0; i < lineNames.length; i++) {
            sectionAccordions[i] = new Accordion();
            for(int j = 0; j < sectionNames.length; j++){
                buttonBoxes[j] = new VBox(5);
                for(int k = 0; k < blockNames.length; k++){
                    blockButtons[k] = new Button("Block: " + blockNames[k]);
                    blockButtons[k].setMinWidth(windowWidth);
                    blockButtons[k].setAlignment(Pos.CENTER_LEFT);
                    blockButtons[k].setTextAlignment(TextAlignment.LEFT);
                }
                buttonBoxes[j].getChildren().addAll(blockButtons);
                buttonBoxes[j].setPadding(new Insets(0));
                sectionPanes[j] = new TitledPane("Section: " + sectionNames[j], buttonBoxes[j]);
            }
            sectionAccordions[i].getPanes().addAll(sectionPanes);
            linePanes[i] = new TitledPane("Line: " + lineNames[i], sectionAccordions[i]);
        }

        // Accordion Settings
        lineAccordion.getPanes().addAll(linePanes);
        lineAccordion.setMinWidth(windowWidth);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(lineAccordion);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Layout Menu Settings
        trackLayout.add(scrollPane, 0, 0);
        trackLayout.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        /////////////////////////////////////
        // Block Info Segment              //
        /////////////////////////////////////

        // block info label
        Label blockInfoLabel = new Label("Block Info: ");
        blockInfoLabel.setTextAlignment(TextAlignment.LEFT);
        blockInfo.add(blockInfoLabel, 0, gridBlock);

        // block info table
        TableView blockInfoMetrics = new TableView();

        TableColumn metric = new TableColumn("Metric");
        TableColumn value = new TableColumn("Value");
        value.setMinWidth(windowWidth/3);

        blockInfoMetrics.getColumns().addAll(metric, value);

        final VBox metricBox = new VBox();
        metricBox.setSpacing(5);
        metricBox.setPadding(new Insets(0, 0, 0, 0));
        metricBox.setMaxWidth(windowWidth/3);
        metricBox.setMinWidth(windowWidth/3);
        metricBox.setMaxHeight(windowHeight/2);
        metricBox.setMinHeight(windowHeight/2);
        metricBox.getChildren().addAll(blockInfoLabel, blockInfoMetrics);

        blockMonitor.add(metricBox, 0, 0);

        ///////////////////////////////////////////////
        // Block Infrastructure Segment              //
        ///////////////////////////////////////////////

        Label blockInfraLabel = new Label("Block Infrastructure: ");
        blockInfraLabel.setTextAlignment(TextAlignment.LEFT);

        blockInfra.add(blockInfraLabel, 0, gridBlock);
        blockInfra.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        blockInfra.setMinHeight(windowHeight/2);
        blockInfra.setMaxHeight(windowHeight/2);
        blockInfra.setMinWidth(windowWidth/3);
        blockInfra.setMaxWidth(windowWidth/3);

        blockMonitor.add(blockInfra, 1,0);

        ///////////////////////////////////////////////
        // Block Status Segment                      //
        ///////////////////////////////////////////////

        Label blockStatusLabel = new Label("Block Status: ");
        blockStatusLabel.setTextAlignment(TextAlignment.LEFT);

        blockStatus.add(blockStatusLabel, 0, gridBlock);
        blockStatus.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        blockStatus.setMinHeight(windowHeight/2);
        blockStatus.setMaxHeight(windowHeight/2);
        blockStatus.setMinWidth(windowWidth/3);
        blockStatus.setMaxWidth(windowWidth/3);

        blockMonitor.add(blockStatus, 3, 0);

        ////////////////////////////////////
        // Add Segments To Main Grid      //
        ////////////////////////////////////

        mainGrid.add(layoutMenuTitle, 0, 0);
        mainGrid.add(trackLayout, 0, 2);
        mainGrid.add(blockMonitor, 0, 3);

        Scene scene = new Scene(mainGrid, windowWidth, windowHeight);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
