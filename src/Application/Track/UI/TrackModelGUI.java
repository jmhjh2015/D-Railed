package Application.Track.UI;

/**
 * Created by andrew on 1/21/17.
 */
import Application.Track.Model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TrackModelGUI extends Application {

    //Class strings
    private String applicationTitle = "Track Model";

    TrackModel track = new TrackModel();
    Block selectedBlock = null;
    Label blockInfoLabel;

    //Class integers
    private int windowWidth = 950;
    private int windowHeight = 650;
    private int inset = 25;
    private int gridBlock = 50;
    private int rowIndex = 0;
    private int colIndex = 0;

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
        trackLayout.setMinHeight((windowHeight/2)-70);
        trackLayout.setMaxHeight((windowHeight/2)-70);
        trackLayout.setMinWidth(windowWidth);
        trackLayout.setMaxWidth(windowWidth);

        // BUTTON MENU LAYOUT
        FlowPane buttonMenu = new FlowPane();
        buttonMenu.setAlignment(Pos.CENTER);
        buttonMenu.setMinWidth(windowWidth);
        buttonMenu.setMaxWidth(windowWidth);
        buttonMenu.setMinHeight(40);
        buttonMenu.setMaxHeight(40);

        // BLOCK MONITOR LAYOUT bottom pane
        GridPane blockMonitor = new GridPane();
        blockMonitor.setAlignment(Pos.TOP_LEFT);
        blockMonitor.setMinHeight((windowHeight/2)-70);
        blockMonitor.setMaxHeight((windowHeight/2)-70);
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

        // Layout Label
        Label layoutMenuTitle = new Label("TrackLayoutMenu");
        layoutMenuTitle.setPadding(new Insets(0,0,0,10));
        layoutMenuTitle.setTextAlignment(TextAlignment.LEFT);
        layoutMenuTitle.setMinWidth(gridBlock);
        layoutMenuTitle.setMinHeight(20);
        layoutMenuTitle.setMaxHeight(20);
        layoutMenuTitle.setAlignment(Pos.CENTER_LEFT);

        Accordion lineAccordion = new Accordion();
        List<Accordion> sectionAccordions = new ArrayList<>();
        List<TitledPane> linePanes = new ArrayList<>();
        List<TitledPane> sectionPanes = new ArrayList<>();
        List<Button> blockButtons = new ArrayList<>();
        List<VBox> buttonBoxes = new ArrayList<>();

        for(Line line : track.getLines()){
            Accordion sectionAccordion = new Accordion();
            for(Section section : line.getSections()){
                VBox buttonBox = new VBox(5);
                for(Block block : section.getBlocks()){
                    Button blockButton = new Button("Block: " + block.getBlockNumber());
                    blockButton.setMinWidth(windowWidth);
                    blockButton.setAlignment(Pos.CENTER_LEFT);
                    blockButton.setTextAlignment(TextAlignment.LEFT);
                    blockButton.setOnAction((event) -> {
                        selectedBlock = section.getBlock(block.getBlockNumber());
                        blockInfoLabel = new Label("Block Info: Line: " + selectedBlock.getLine() + " | Section: " + selectedBlock.getSection() + " | Block: " + selectedBlock.getBlockNumber());
                        //blockInfo.add(blockInfoLabel, 0, 0);
                        //blockMonitor.add(blockInfo, 0, 0);
                    });
                    blockButtons.add(blockButton);
                    buttonBox.getChildren().add(blockButton);
                }
                buttonBox.setPadding(new Insets(0));
                TitledPane sectionPane = new TitledPane("Section: " + section.getSection(), buttonBox);
                sectionAccordion.getPanes().add(sectionPane);
            }
            TitledPane linePane = new TitledPane("Line: " + line.getLine(), sectionAccordion);
            linePanes.add(linePane);
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

        ////////////////////////////////////
        // Button Menu                    //
        ////////////////////////////////////

        FileChooser fileChooser = new FileChooser();
        Stage fileSelect = new Stage();
        fileSelect.setTitle("Choose a track layout data file to import:");
        fileChooser.setInitialDirectory(new File("C:\\Users\\adzun_000\\IdeaProjects\\D-Railed\\src\\Application\\Track\\UI\\resources"));

        // Import Track Button
        Button importTrack = new Button("Import Track");
        importTrack.setMinHeight(30);
        importTrack.setMaxHeight(30);

        // import a track and modify the track layout
        importTrack.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                File file = fileChooser.showOpenDialog(fileSelect);
                if(file != null){
                    track.importTrack(file.getAbsolutePath());

                    Accordion lineAccordion = new Accordion();
                    List<Accordion> sectionAccordions = new ArrayList<>();
                    List<TitledPane> linePanes = new ArrayList<>();
                    List<TitledPane> sectionPanes = new ArrayList<>();
                    List<Button> blockButtons = new ArrayList<>();
                    List<VBox> buttonBoxes = new ArrayList<>();

                    for(Line line : track.getLines()){

                        for(Switch s : line.getSwitches()){
                            System.out.println("Switch: " + s.getSwitchInfo());
                        }

                        for(Station s : line.getStations()){
                            System.out.println("Station: " + s.getStationName());
                        }

                        for(Crossing c : line.getCrossings()){
                            System.out.println("Crossing: " + c.getCrossingNumber());
                        }

                        Accordion sectionAccordion = new Accordion();
                        for(Section section : line.getSections()){
                            VBox buttonBox = new VBox(5);
                            for(Block block : section.getBlocks()){
                                Button blockButton = new Button("Block: " + block.getBlockNumber());
                                blockButton.setMinWidth(windowWidth);
                                blockButton.setAlignment(Pos.CENTER_LEFT);
                                blockButton.setTextAlignment(TextAlignment.LEFT);
                                blockButton.setOnAction((event) -> {
                                    selectedBlock = section.getBlock(block.getBlockNumber());
                                    blockInfoLabel = new Label("Block Info: Line: " + selectedBlock.getLine() + " | Section: " + selectedBlock.getSection() + " | Block: " + selectedBlock.getBlockNumber());
                                    //blockInfo.add(blockInfoLabel, 0, 0);
                                    //blockMonitor.add(blockInfo, 0, 0);

                                });
                                blockButtons.add(blockButton);
                                buttonBox.getChildren().add(blockButton);
                            }
                            buttonBox.setPadding(new Insets(0));
                            TitledPane sectionPane = new TitledPane("Section: " + section.getSection(), buttonBox);
                            sectionAccordion.getPanes().add(sectionPane);
                        }
                        TitledPane linePane = new TitledPane("Line: " + line.getLine(), sectionAccordion);
                        linePanes.add(linePane);
                    }

                    // Accordion Settings
                    lineAccordion.getPanes().addAll(linePanes);
                    lineAccordion.setMinWidth(windowWidth);
                    ScrollPane scrollPane = new ScrollPane();
                    scrollPane.setContent(lineAccordion);
                    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

                    //trackLayout.getChildren().clear();
                    trackLayout.add(scrollPane, 0, 0);

                }

            }
        });

        Button infraOver = new Button("Infrastructure Override");
        infraOver.setMinHeight(30);
        infraOver.setMaxHeight(30);

        Button murphyCtrl = new Button("Murphy Controls");
        murphyCtrl.setMinHeight(30);
        murphyCtrl.setMaxHeight(30);

        buttonMenu.getChildren().add(importTrack);
        buttonMenu.getChildren().add(infraOver);
        buttonMenu.getChildren().add(murphyCtrl);

        /////////////////////////////////////
        // Block Info Segment              //
        /////////////////////////////////////

        // block info label
        blockInfoLabel = new Label("Block Info: Line: None | Section: None | Block: None");
        blockInfoLabel.setTextAlignment(TextAlignment.LEFT);
        blockInfo.add(blockInfoLabel, 0, 0);

        // block info table
        TableView<Metric> blockInfoMetrics = new TableView();
        blockInfoMetrics.setEditable(true);

        TableColumn<Metric, String> metric = new TableColumn("Metric");
        //metric.setCellValueFactory(new PropertyValueFactory<Metric, String>("metric"));
        metric.setMinWidth(60);

        TableColumn<Metric, String> value = new TableColumn("Value");
        //value.setCellValueFactory(new PropertyValueFactory<Metric, String>("value"));
        value.setMinWidth(250);

        ObservableList<Metric> data = FXCollections.observableArrayList(
                new Metric("Length (m):", "100.0"),
                new Metric("Speed Limit (m/s):", "75.0"),
                new Metric("Grade (%):", "5.0"),
                new Metric("Elevation (m):", "0.0"),
                new Metric("Cumulative Elevation (m):", "0.5"),
                new Metric("Temperature (C):", "64.2"),
                new Metric("Direction (BI/UNI):", "BI")
        );

        blockInfoMetrics.setItems(data);
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

        ImageView trainIcon = new ImageView(new Image("Application/Track/UI/images/trainIcon.png"));
        ImageView switchIcon = new ImageView(new Image("Application/Track/UI/images/switchIcon.png"));
        ImageView stationIcon = new ImageView(new Image("Application/Track/UI/images/stationIcon.png"));
        ImageView crossingIcon = new ImageView(new Image("Application/Track/UI/images/crossingIcon.png"));
        ImageView heaterIcon = new ImageView(new Image("Application/Track/UI/images/heaterIcon.png"));
        ImageView lightsIcon = new ImageView(new Image("Application/Track/UI/images/lightsIcon.png"));

        blockInfra.add(blockInfraLabel, 0, 0);
        blockInfra.setMinHeight(windowHeight/2);
        blockInfra.setMaxHeight(windowHeight/2);
        blockInfra.setMinWidth(windowWidth/3);
        blockInfra.setMaxWidth(windowWidth/3);

        FlowPane trainInfra = new FlowPane();
        trainInfra.setMaxHeight((blockInfra.getMaxHeight()/6)-4);
        trainInfra.setMinHeight((blockInfra.getMinHeight()/6)-4);
        trainInfra.setMaxWidth(blockInfra.getMinWidth());
        trainInfra.setMinWidth(blockInfra.getMaxWidth());
        trainInfra.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        GridPane trainLabels = new GridPane();
        trainLabels.setAlignment(Pos.CENTER_LEFT);

        Label trainData = new Label("Train: 14");
        Label trainLoad = new Label("Can Load: 140 | Unloading: 40");
        Label trainCap = new Label("Occupancy: 100 | Max Occupancy: 200");

        trainLabels.add(trainData, 0, 0);
        trainLabels.add(trainLoad, 0, 1);
        trainLabels.add(trainCap, 0, 2);
        trainLabels.setPadding(new Insets(0,0,0,10));

        trainIcon.setFitHeight(trainInfra.getMaxHeight());
        trainIcon.setFitWidth(50);

        trainInfra.getChildren().add(trainIcon);
        trainInfra.getChildren().add(trainLabels);

        FlowPane switchInfra = new FlowPane();
        switchInfra.setMaxHeight((blockInfra.getMaxHeight()/6)-4);
        switchInfra.setMinHeight((blockInfra.getMinHeight()/6)-4);
        switchInfra.setMaxWidth(blockInfra.getMinWidth());
        switchInfra.setMinWidth(blockInfra.getMaxWidth());
        switchInfra.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        switchIcon.setFitHeight(switchInfra.getMaxHeight());
        switchIcon.setFitWidth(50);

        GridPane switchLabels = new GridPane();
        switchLabels.setAlignment(Pos.CENTER_LEFT);

        Label switchData = new Label("Switch: 16");
        Label switchStatusData = new Label("Status: TOP");
        Label switchConnectData = new Label("Main: A | Top: B | Bottom: E");

        switchLabels.add(switchData, 0, 0);
        switchLabels.add(switchStatusData, 0, 1);
        switchLabels.add(switchConnectData, 0, 2);

        switchData.setPadding(new Insets(0, 0, 0, 10));
        switchStatusData.setPadding(new Insets(0, 0, 0, 10));
        switchConnectData.setPadding(new Insets(0, 0, 0, 10));

        switchInfra.getChildren().add(switchIcon);
        switchInfra.getChildren().add(switchLabels);

        FlowPane stationInfra = new FlowPane();
        stationInfra.setMaxHeight((blockInfra.getMaxHeight()/6)-4);
        stationInfra.setMinHeight((blockInfra.getMinHeight()/6)-4);
        stationInfra.setMaxWidth(blockInfra.getMinWidth());
        stationInfra.setMinWidth(blockInfra.getMaxWidth());
        stationInfra.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        stationIcon.setFitHeight(stationInfra.getMaxHeight());
        stationIcon.setFitWidth(50);

        GridPane stationLabels = new GridPane();
        stationLabels.setAlignment(Pos.CENTER_LEFT);

        Label stationData = new Label("Station: SHADYSIDE");
        Label stationOccupancy = new Label("Occupancy: 1000 | Departing: 100");

        stationLabels.add(stationData, 0, 0);
        stationLabels.add(stationOccupancy, 0, 1);

        stationData.setPadding(new Insets(0, 0, 0, 10));
        stationOccupancy.setPadding(new Insets(0, 0, 0, 10));

        stationInfra.getChildren().add(stationIcon);
        stationInfra.getChildren().add(stationLabels);

        FlowPane crossingInfra = new FlowPane();
        crossingInfra.setMaxHeight((blockInfra.getMaxHeight()/6)-4);
        crossingInfra.setMinHeight((blockInfra.getMinHeight()/6)-4);
        crossingInfra.setMaxWidth(blockInfra.getMinWidth());
        crossingInfra.setMinWidth(blockInfra.getMaxWidth());
        crossingInfra.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        crossingIcon.setFitHeight(crossingInfra.getMaxHeight());
        crossingIcon.setFitWidth(50);

        GridPane crossingLabels = new GridPane();
        crossingLabels.setAlignment(Pos.CENTER_LEFT);

        Label crossingStatus = new Label("Crossroad Status: ACTIVE");

        crossingLabels.add(crossingStatus, 0, 0);
        crossingLabels.setPadding(new Insets(0,0,0,10));

        crossingInfra.getChildren().add(crossingIcon);
        crossingInfra.getChildren().add(crossingLabels);

        FlowPane heaterInfra = new FlowPane();
        heaterInfra.setMaxHeight((blockInfra.getMaxHeight()/6)-4);
        heaterInfra.setMinHeight((blockInfra.getMinHeight()/6)-4);
        heaterInfra.setMaxWidth(blockInfra.getMinWidth());
        heaterInfra.setMinWidth(blockInfra.getMaxWidth());
        heaterInfra.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        heaterIcon.setFitHeight(heaterInfra.getMaxHeight());
        heaterIcon.setFitWidth(50);

        GridPane heaterLabels = new GridPane();
        heaterLabels.setAlignment(Pos.CENTER_LEFT);

        Label heaterStatus = new Label("Heater Status: ON");
        Label heaterRate = new Label("RailTemp: 24 | SetTemp: 50 | TempRate: 5");

        heaterLabels.add(heaterStatus, 0, 0);
        heaterLabels.add(heaterRate, 0, 1);

        heaterStatus.setPadding(new Insets(0, 0, 0, 10));
        heaterRate.setPadding(new Insets(0, 0, 0, 10));

        heaterInfra.getChildren().add(heaterIcon);
        heaterInfra.getChildren().add(heaterLabels);

        FlowPane lightsInfra = new FlowPane();
        lightsInfra.setMaxHeight((blockInfra.getMaxHeight()/6)-3);
        lightsInfra.setMinHeight((blockInfra.getMinHeight()/6)-3);
        lightsInfra.setMaxWidth(blockInfra.getMinWidth());
        lightsInfra.setMinWidth(blockInfra.getMaxWidth());
        lightsInfra.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        lightsIcon.setFitHeight(lightsInfra.getMaxHeight());
        lightsIcon.setFitWidth(50);

        GridPane lightsLabels = new GridPane();
        lightsLabels.setAlignment(Pos.CENTER_LEFT);

        Label lightsStatus = new Label("Lights Status: ON");

        lightsLabels.add(lightsStatus, 0, 0);
        lightsLabels.setPadding(new Insets(0, 0, 0, 10));

        lightsInfra.getChildren().add(lightsIcon);
        lightsInfra.getChildren().add(lightsLabels);

        blockInfra.add(trainInfra, 0, 1);
        blockInfra.add(switchInfra, 0, 2);
        blockInfra.add(stationInfra, 0, 3);
        blockInfra.add(crossingInfra, 0, 4);
        blockInfra.add(heaterInfra, 0, 5);
        blockInfra.add(lightsInfra, 0, 6);

        blockMonitor.add(blockInfra, 1,0);

        ///////////////////////////////////////////////
        // Block Status Segment                      //
        ///////////////////////////////////////////////

        Label blockStatusLabel = new Label("Block Status: ");
        blockStatusLabel.setTextAlignment(TextAlignment.LEFT);

        ImageView conditionIcon = new ImageView(new Image("Application/Track/UI/images/openIcon.png"));
        ImageView railIcon = new ImageView(new Image("Application/Track/UI/images/railIcon.png"));
        ImageView circuitIcon = new ImageView(new Image("Application/Track/UI/images/circuitIcon.png"));
        ImageView powerIcon = new ImageView(new Image("Application/Track/UI/images/powerIcon.png"));

        blockStatus.add(blockStatusLabel, 0, 0);
        blockStatus.setMinHeight(windowHeight/2);
        blockStatus.setMaxHeight(windowHeight/2);
        blockStatus.setMinWidth(windowWidth/3);
        blockStatus.setMaxWidth(windowWidth/3);

        FlowPane conditionStatus = new FlowPane();
        conditionStatus.setMaxHeight((blockInfra.getMaxHeight()/5)-4);
        conditionStatus.setMinHeight((blockInfra.getMinHeight()/5)-4);
        conditionStatus.setMaxWidth(blockInfra.getMinWidth());
        conditionStatus.setMinWidth(blockInfra.getMaxWidth());
        conditionStatus.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        conditionIcon.setFitHeight(conditionStatus.getMaxHeight());
        conditionIcon.setFitWidth(50);

        GridPane conditionLabels = new GridPane();
        conditionLabels.setAlignment(Pos.CENTER_LEFT);

        Label conditionStatusLabel = new Label("Overall Block Status: OPEN");
        Label tabLabel = new Label("Failure System Check: 3/3 Good");

        conditionLabels.add(conditionStatusLabel, 0, 0);
        conditionLabels.add(tabLabel,0,0);

        conditionLabels.setPadding(new Insets(0, 0, 0, 10));

        conditionStatus.getChildren().add(conditionIcon);
        conditionStatus.getChildren().add(conditionStatusLabel);

        FlowPane railStatus = new FlowPane();
        railStatus.setMaxHeight((blockInfra.getMaxHeight()/5)-4);
        railStatus.setMinHeight((blockInfra.getMinHeight()/5)-4);
        railStatus.setMaxWidth(blockInfra.getMinWidth());
        railStatus.setMinWidth(blockInfra.getMaxWidth());
        railStatus.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        railIcon.setFitHeight(railStatus.getMaxHeight());
        railIcon.setFitWidth(50);

        GridPane railLabels = new GridPane();
        railLabels.setAlignment(Pos.CENTER_LEFT);

        Label railStatusLabel = new Label("Rail Status: Good");

        railLabels.add(railStatusLabel, 0, 0);
        railLabels.setPadding(new Insets(0,0,0,10));

        railStatus.getChildren().add(railIcon);
        railStatus.getChildren().add(railLabels);

        FlowPane circuitStatus = new FlowPane();
        circuitStatus.setMaxHeight((blockInfra.getMaxHeight()/5)-4);
        circuitStatus.setMinHeight((blockInfra.getMinHeight()/5)-4);
        circuitStatus.setMaxWidth(blockInfra.getMinWidth());
        circuitStatus.setMinWidth(blockInfra.getMaxWidth());
        circuitStatus.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        circuitIcon.setFitHeight(crossingInfra.getMaxHeight());
        circuitIcon.setFitWidth(50);

        GridPane circuitLabels = new GridPane();
        circuitLabels.setAlignment(Pos.CENTER_LEFT);

        Label circuitStatusLabel = new Label("Track Circuit Status: Good");

        circuitLabels.add(circuitStatusLabel, 0, 0);
        circuitLabels.setPadding(new Insets(0,0,0,10));

        circuitStatus.getChildren().add(circuitIcon);
        circuitStatus.getChildren().add(circuitLabels);

        FlowPane powerStatus = new FlowPane();
        powerStatus.setMaxHeight((blockInfra.getMaxHeight()/5)-4);
        powerStatus.setMinHeight((blockInfra.getMinHeight()/5)-4);
        powerStatus.setMaxWidth(blockInfra.getMinWidth());
        powerStatus.setMinWidth(blockInfra.getMaxWidth());
        powerStatus.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        powerIcon.setFitHeight(powerStatus.getMaxHeight());
        powerIcon.setFitWidth(50);

        GridPane powerLabels = new GridPane();
        powerLabels.setAlignment(Pos.CENTER_LEFT);

        Label powerStatusLabel = new Label("Power Status: Good");

        powerLabels.add(powerStatusLabel, 0, 0);
        powerLabels.setPadding(new Insets(0,0,0,10));

        powerStatus.getChildren().add(powerIcon);
        powerStatus.getChildren().add(powerLabels);

        blockStatus.add(conditionStatus, 0, 1);
        blockStatus.add(railStatus, 0, 2);
        blockStatus.add(circuitStatus, 0, 3);
        blockStatus.add(powerStatus, 0, 4);


        blockMonitor.add(blockStatus, 3, 0);

        ////////////////////////////////////
        // Add Segments To Main Grid      //
        ////////////////////////////////////

        mainGrid.add(layoutMenuTitle, 0, 0);
        mainGrid.add(trackLayout, 0, 2);
        mainGrid.add(buttonMenu, 0,3);
        mainGrid.add(blockMonitor, 0, 4);

        Scene scene = new Scene(mainGrid, windowWidth, windowHeight);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
