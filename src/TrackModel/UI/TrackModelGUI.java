package TrackModel.UI;

/**
 * Created by andrew on 1/21/17.
 */

import TrackModel.Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrackModelGUI {

    //UI Constants
    private final String applicationTitle = "Track Model";
    private final Stage stage = new Stage();
    private Scene scene;

    private final int windowWidth = 950;
    private final int windowHeight = 650;
    private final int gridBlock = 50;

    // Model Structures
    private Block selectedBlock = new Block();
    private TrackModel track = new TrackModel();
    private Label blockInfoLabel = null;

    // Main Grid
    private GridPane mainGrid = getGridPane();

    // Layout Panes (Track Navigator, Button Interface, Block Information)
    private GridPane trackLayout = getLayoutPane();
    private FlowPane buttonMenuLayout = getButtonMenuLayoutPane();
    private GridPane blockMonitorLayout = getLayoutPane();

    // Block Info Displays (Info, Infrastructure, Status)
    private GridPane blockInfo = getBlockInfoPane();
    private GridPane blockInfra = new GridPane();
    private GridPane blockStatus = new GridPane();

    // Infrastructure Icon Resources
    private ImageView trainIcon = new ImageView(new Image("TrackModel/UI/images/trainIcon.png"));
    private ImageView switchIcon = new ImageView(new Image("TrackModel/UI/images/switchIcon.png"));
    private ImageView stationIcon = new ImageView(new Image("TrackModel/UI/images/stationIcon.png"));
    private ImageView crossingIcon = new ImageView(new Image("TrackModel/UI/images/crossingIcon.png"));
    private ImageView lightsIcon = new ImageView(new Image("TrackModel/UI/images/lightsIcon.png"));

    // Status Icon Resources
    private ImageView openIcon = new ImageView(new Image("TrackModel/UI/images/openIcon.png"));
    private ImageView closedIcon = new ImageView(new Image("TrackModel/UI/images/closedIcon.png"));
    private ImageView railIcon = new ImageView(new Image("TrackModel/UI/images/railIcon.png"));
    private ImageView circuitIcon = new ImageView(new Image("TrackModel/UI/images/circuitIcon.png"));
    private ImageView powerIcon = new ImageView(new Image("TrackModel/UI/images/powerIcon.png"));

    public TrackModelGUI() throws IOException {

        stage.setTitle(applicationTitle);

        // Layout Menu
        Label layoutMenuTitle = getLayoutLabel();
        ScrollPane scrollPane = parseTrackForDisplay();
        trackLayout.add(scrollPane, 0, 0);

        // Button Menu
        buttonMenuLayout = getButtonMenu();

        // Button Info Section
        blockInfoLabel = getBlockInfoLabel();
        FlowPane blockInfoPanel = getBlockInfoPane(blockInfo.getMinWidth());
        VBox metricBox = getMetricBox(blockInfoPanel);
        blockMonitorLayout.add(metricBox, 0, 0);

        ///////////////////////////////////////////////
        // Block Infrastructure Segment              //
        ///////////////////////////////////////////////

        Label blockInfraLabel = new Label("Block Infrastructure: ");
        blockInfraLabel.setTextAlignment(TextAlignment.LEFT);

        blockInfra.setMinHeight(windowHeight/2);
        blockInfra.setMaxHeight(windowHeight/2);
        blockInfra.setMinWidth(windowWidth/3);
        blockInfra.setMaxWidth(windowWidth/3);

        FlowPane trainInfra = null;
        FlowPane switchInfra = null;
        FlowPane stationInfra = null;
        FlowPane crossingInfra = null;
        FlowPane lightsInfra = null;

        //Train train = selectedBlock.getTrain();
        //if(train != null)
            trainInfra = getTrainInfoPane();

        Switch dispSwitch = selectedBlock.getSwitch();
        if(dispSwitch != null)
            switchInfra = getSwitchInfoPane(blockInfra.getMinHeight(), blockInfra.getMinWidth(), dispSwitch);

        Station dispStation = selectedBlock.getStation();
        if(dispStation != null)
            stationInfra = getStationInfoPane(blockInfra.getMinHeight(), blockInfra.getMinWidth(), dispStation);

        Crossing dispCrossing = selectedBlock.getCrossing();
        if(dispCrossing != null)
            crossingInfra = getCrossingInfoPane(blockInfra.getMinHeight(), blockInfra.getMinWidth(), dispCrossing);

        Light light = selectedBlock.getLight();
        if(light != null)
            lightsInfra = getLightInfoPane();

        blockInfra = populateBlockInfrastructure(blockInfraLabel, trainInfra, switchInfra, stationInfra, crossingInfra, lightsInfra);
        blockMonitorLayout.add(blockInfra, 1,0);

        ///////////////////////////////////////////////
        // Block Status Segment                      //
        ///////////////////////////////////////////////

        Label blockStatusLabel = new Label("Block Status: ");

        blockStatus.setMinHeight(windowHeight/2);
        blockStatus.setMaxHeight(windowHeight/2);
        blockStatus.setMinWidth(windowWidth/3);
        blockStatus.setMaxWidth(windowWidth/3);

        FlowPane conditionStatus = getConditionStatusPane();
        FlowPane railStatus = getRailStatusPane();
        FlowPane circuitStatus = getCircuitStatusPane();
        FlowPane powerStatus = getPowerStatusPane();

        blockStatus = populateBlockStatus(blockStatusLabel, conditionStatus, railStatus, circuitStatus, powerStatus);
        blockMonitorLayout.add(blockStatus, 2, 0);

        ////////////////////////////////////
        // Add Segments To Main Grid      //
        ////////////////////////////////////

        mainGrid = populateMainGrid(layoutMenuTitle);

        scene = new Scene(mainGrid, windowWidth, windowHeight);
        stage.setScene(scene);
        stage.show();
    }

    private GridPane populateBlockInfrastructure(Label blockInfraLabel, FlowPane trainInfra, FlowPane switchInfra, FlowPane stationInfra, FlowPane crossingInfra, FlowPane lightsInfra) {

        GridPane blockInfra = new GridPane();
        blockInfra.setMinHeight(windowHeight/2);
        blockInfra.setMaxHeight(windowHeight/2);
        blockInfra.setMinWidth(windowWidth/3);
        blockInfra.setMaxWidth(windowWidth/3);

        int i = 1;

        blockInfra.add(blockInfraLabel, 0, 0);

        if(trainInfra != null){
            blockInfra.add(trainInfra, 0, i);
            i++;
        }
        if(switchInfra != null){
            blockInfra.add(switchInfra, 0, i);
            i++;
        }
        if(stationInfra != null){
            blockInfra.add(stationInfra, 0, i);
            i++;
        }
        if(crossingInfra != null){
            blockInfra.add(crossingInfra, 0, i);
            i++;
        }
        if(lightsInfra != null){
            blockInfra.add(lightsInfra, 0, i);
            i++;
        }

        return blockInfra;

    }

    private FlowPane getLightInfoPane() {
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
        return lightsInfra;
    }

    private FlowPane getTrainInfoPane() {
        FlowPane trainInfra = new FlowPane();
        trainInfra.setMaxHeight((blockInfra.getMaxHeight()/6)-4);
        trainInfra.setMinHeight((blockInfra.getMinHeight()/6)-4);
        trainInfra.setMaxWidth(blockInfra.getMinWidth());
        trainInfra.setMinWidth(blockInfra.getMaxWidth());
        trainInfra.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        GridPane trainLabels = new GridPane();
        trainLabels.setAlignment(Pos.CENTER_LEFT);

        Label trainData = new Label("Train: 14");
        Label trainLoad = new Label("Capacity: 222 | Unloading: 40");

        trainLabels.add(trainData, 0, 0);
        trainLabels.add(trainLoad, 0, 1);
        trainLabels.setPadding(new Insets(0,0,0,10));

        trainIcon.setFitHeight(trainInfra.getMaxHeight());
        trainIcon.setFitWidth(50);

        trainInfra.getChildren().add(trainIcon);
        trainInfra.getChildren().add(trainLabels);
        return trainInfra;
    }

    private GridPane populateMainGrid(Label layoutMenuTitle) {

        GridPane mainGrid = new GridPane();

        mainGrid.add(layoutMenuTitle, 0, 0);
        mainGrid.add(trackLayout, 0, 2);
        mainGrid.add(buttonMenuLayout, 0,3);
        mainGrid.add(blockMonitorLayout, 0, 4);

        return mainGrid;
    }

    private GridPane populateBlockStatus(Label blockStatusLabel, FlowPane conditionStatus, FlowPane railStatus, FlowPane circuitStatus, FlowPane powerStatus) {

        GridPane blockStatus = new GridPane();
        blockStatus.setMinHeight(windowHeight/2);
        blockStatus.setMaxHeight(windowHeight/2);
        blockStatus.setMinWidth(windowWidth/3);
        blockStatus.setMaxWidth(windowWidth/3);

        blockStatus.add(blockStatusLabel, 0, 0);
        blockStatus.add(conditionStatus, 0, 1);
        blockStatus.add(railStatus, 0, 2);
        blockStatus.add(circuitStatus, 0, 3);
        blockStatus.add(powerStatus, 0, 4);

        return blockStatus;
    }

    private FlowPane getPowerStatusPane() {
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

        Label powerStatusLabel = null;
        if(selectedBlock.isPowerState()) {
            powerStatusLabel = new Label("Power Status: Good");
        }else{
            powerStatusLabel = new Label("Power Status: Failing");
        }

        powerLabels.add(powerStatusLabel, 0, 0);
        powerLabels.setPadding(new Insets(0,0,0,10));

        powerStatus.getChildren().add(powerIcon);
        powerStatus.getChildren().add(powerLabels);
        return powerStatus;
    }

    private FlowPane getCircuitStatusPane() {
        FlowPane circuitStatus = new FlowPane();
        circuitStatus.setMaxHeight((blockInfra.getMaxHeight()/5)-4);
        circuitStatus.setMinHeight((blockInfra.getMinHeight()/5)-4);
        circuitStatus.setMaxWidth(blockInfra.getMinWidth());
        circuitStatus.setMinWidth(blockInfra.getMaxWidth());
        circuitStatus.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        circuitIcon.setFitHeight(circuitStatus.getMaxHeight());
        circuitIcon.setFitWidth(50);

        GridPane circuitLabels = new GridPane();
        circuitLabels.setAlignment(Pos.CENTER_LEFT);

        Label circuitStatusLabel = null;
        if(selectedBlock.isCircuitState()) {
            circuitStatusLabel = new Label("Track Circuit Status: Good");
        }else{
            circuitStatusLabel = new Label("Track Circuit Status: Failing");
        }

        circuitLabels.add(circuitStatusLabel, 0, 0);
        circuitLabels.setPadding(new Insets(0,0,0,10));

        circuitStatus.getChildren().add(circuitIcon);
        circuitStatus.getChildren().add(circuitLabels);
        return circuitStatus;
    }

    private FlowPane getRailStatusPane() {
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

        Label railStatusLabel = null;
        if(selectedBlock.isRailState()) {
            railStatusLabel = new Label("Rail Status: Good");
        }else{
            railStatusLabel = new Label("Rail Status: Failing");
        }

        railLabels.add(railStatusLabel, 0, 0);
        railLabels.setPadding(new Insets(0,0,0,10));

        railStatus.getChildren().add(railIcon);
        railStatus.getChildren().add(railLabels);
        return railStatus;
    }

    private FlowPane getConditionStatusPane() {

        FlowPane conditionStatus = new FlowPane();

        conditionStatus.setMaxHeight((blockInfra.getMaxHeight()/5)-4);
        conditionStatus.setMinHeight((blockInfra.getMinHeight()/5)-4);
        conditionStatus.setMaxWidth(blockInfra.getMinWidth());
        conditionStatus.setMinWidth(blockInfra.getMaxWidth());
        conditionStatus.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        openIcon.setFitHeight(conditionStatus.getMaxHeight());
        openIcon.setFitWidth(50);
        closedIcon.setFitHeight(conditionStatus.getMaxHeight());
        closedIcon.setFitWidth(50);

        GridPane conditionLabels = new GridPane();
        conditionLabels.setAlignment(Pos.CENTER_LEFT);

        Label conditionStatusLabel = new Label("Overall Block Status: " + selectedBlock.getTrackState());

        conditionStatusLabel.setPadding(new Insets(0,0,0,10));
        conditionLabels.add(conditionStatusLabel, 0, 0);
        conditionLabels.setPadding(new Insets(0, 0, 0, 10));

        if(selectedBlock.getTrackState().contains("OPEN")){
            conditionStatus.getChildren().add(openIcon);
        }else{
            conditionStatus.getChildren().add(closedIcon);
        }

        conditionStatus.getChildren().add(conditionStatusLabel);
        return conditionStatus;
    }

    private Label getBlockInfoLabel() {

        Label blockInfoLabel = new Label("Block Info: Line: " + selectedBlock.getLine() + " | Section: " + selectedBlock.getSection() + " | Block: " + selectedBlock.getBlockNumber());
        blockInfoLabel.setTextAlignment(TextAlignment.LEFT);
        blockInfoLabel.setPadding(new Insets(0,0,0,10));

        return blockInfoLabel;
    }

    private FlowPane getButtonMenu() {

        FlowPane menu = new FlowPane();
        menu.setAlignment(Pos.CENTER);

        FileChooser fileChooser = new FileChooser();
        Stage fileSelect = new Stage();
        fileSelect.setTitle("Choose a track layout data file to import:");
        fileChooser.setInitialDirectory(new File("src/TrackModel/UI/resources"));

        // Import Track Button
        Button importTrack = new Button("Import Track");
        importTrack.setMinHeight(30);
        importTrack.setMaxHeight(30);

        // import a track and modify the track layout
        importTrack.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                File file = fileChooser.showOpenDialog(fileSelect);
                if(file != null)
                {
                    track.importTrack(file.getAbsolutePath());
                    ScrollPane scrollPane = parseTrackForDisplay();
                    trackLayout.add(scrollPane, 0, 0);
                }
            }
        });

        Button infraOver = new Button("Infrastructure Override");
        infraOver.setMinHeight(30);
        infraOver.setMaxHeight(30);

        // import a track and modify the track layout
        importTrack.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                File file = fileChooser.showOpenDialog(fileSelect);
                if(file != null)
                {
                    track.importTrack(file.getAbsolutePath());
                    ScrollPane scrollPane = parseTrackForDisplay();
                    trackLayout.add(scrollPane, 0, 0);
                }
            }
        });

        Button murphyCtrl = new Button("Murphy Controls");
        murphyCtrl.setMinHeight(30);
        murphyCtrl.setMaxHeight(30);

        // modify status of a selected block
        murphyCtrl.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                if(selectedBlock.getBlockNumber() == null){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Murphy Control Error");
                    alert.setHeaderText("No Block Selected");
                    alert.setContentText("Please select a block to use this function!");
                    alert.show();
                    return;
                }

                Stage murphy = new Stage();
                GridPane murphyPanel = new GridPane();

                VBox murphyChange = new VBox();
                HBox confirm = new HBox();

                Label todo = new Label("Select systems to simulate track state:");
                todo.setPadding(new Insets(10));

                Label currentBlock = new Label("Line: " + selectedBlock.getLine() + "Section: " + selectedBlock.getSection() +  "Block: " + selectedBlock.getBlockNumber());
                currentBlock.setPadding(new Insets(10));

                CheckBox condButton = new CheckBox("Close Track");
                CheckBox railButton = new CheckBox("Break Rail");
                CheckBox circuitButton = new CheckBox("Track Circuit Failure");
                CheckBox powerButton = new CheckBox("Power Failure");

                Button okButton = new Button("OK");

                okButton.setOnAction(new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent e){
                        if(condButton.isSelected())
                            selectedBlock.toggleTrackState();

                        if(railButton.isSelected())
                            selectedBlock.toggleRailState();

                        if(circuitButton.isSelected())
                            selectedBlock.toggleCircuitState();

                        if(powerButton.isSelected())
                            selectedBlock.togglePowerState();

                        updateBlockMonitor();
                        murphy.close();
                    }
                });

                Button cancelButton = new Button("Cancel");

                cancelButton.setOnAction(new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent e){
                        murphy.close();
                    }
                });

                murphyChange.getChildren().addAll(condButton, railButton, circuitButton, powerButton);
                murphyChange.setPadding(new Insets(10));

                confirm.getChildren().addAll(okButton, cancelButton);
                confirm.setPadding(new Insets(10));

                murphyPanel.add(todo, 0, 0);
                murphyPanel.add(currentBlock, 0, 1);
                murphyPanel.add(murphyChange, 0, 2);
                murphyPanel.add(confirm, 0, 3);

                Scene murphyScene = new Scene(murphyPanel, 400, 500);
                murphy.setTitle("Murphy Control");
                murphy.setScene(murphyScene);
                murphy.show();

            }
        });

        menu.getChildren().addAll(importTrack, infraOver, murphyCtrl);
        return menu;
    }

    private void updateBlockMonitor() {
        updateBlockInfoView();
        updateBlockStatus();
        updateBlockInfrastructure();
    }

    private ScrollPane parseTrackForDisplay() {
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
                        selectedBlock = block;
                        updateBlockMonitor();
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
        return scrollPane;
    }

    private void updateBlockInfrastructure() {
        Label blockInfraLabel = new Label("Block Infrastructure: ");
        blockInfraLabel.setTextAlignment(TextAlignment.LEFT);

        blockInfra.getChildren().removeAll();

        blockInfra.setMinHeight(windowHeight/2);
        blockInfra.setMaxHeight(windowHeight/2);
        blockInfra.setMinWidth(windowWidth/3);
        blockInfra.setMaxWidth(windowWidth/3);

        FlowPane trainInfra = null;
        FlowPane switchInfra = null;
        FlowPane stationInfra = null;
        FlowPane crossingInfra = null;
        FlowPane lightsInfra = null;

        //Train train = selectedBlock.getTrain();
        //if(train != null)
            trainInfra = getTrainInfoPane();

        Switch dispSwitch = selectedBlock.getSwitch();
        if(dispSwitch != null)
            switchInfra = getSwitchInfoPane(blockInfra.getMinHeight(), blockInfra.getMinWidth(), dispSwitch);

        Station dispStation = selectedBlock.getStation();
        if(dispStation != null)
            stationInfra = getStationInfoPane(blockInfra.getMinHeight(), blockInfra.getMinWidth(), dispStation);

        Crossing dispCrossing = selectedBlock.getCrossing();
        if(dispCrossing != null)
            crossingInfra = getCrossingInfoPane(blockInfra.getMinHeight(), blockInfra.getMinWidth(), dispCrossing);

        Light light = selectedBlock.getLight();
        if(light != null)
            lightsInfra = getLightInfoPane();

        blockMonitorLayout.getChildren().remove(1);

        blockInfra = populateBlockInfrastructure(blockInfraLabel, trainInfra, switchInfra, stationInfra, crossingInfra, lightsInfra);
        blockMonitorLayout.add(blockInfra, 2, 0);
    }

    private void updateBlockStatus() {

        Label blockStatusLabel = new Label("Block Status: ");

        FlowPane conditionStatus = getConditionStatusPane();
        FlowPane railStatus = getRailStatusPane();
        FlowPane circuitStatus = getCircuitStatusPane();
        FlowPane powerStatus = getPowerStatusPane();

        blockStatus = populateBlockStatus(blockStatusLabel, conditionStatus, railStatus, circuitStatus, powerStatus);

        blockMonitorLayout.getChildren().remove(2);
        blockMonitorLayout.add(blockStatus, 3, 0);

    }

    private void updateBlockInfoView() {

        blockInfoLabel = new Label("Block Info: Line: " + selectedBlock.getLine() + " | Section: " + selectedBlock.getSection() + " | Block: " + selectedBlock.getBlockNumber());

        FlowPane blockInfoPanel = getBlockInfoPane(blockInfo.getMinWidth());
        VBox metricBox = getMetricBox(blockInfoPanel);

        blockMonitorLayout.getChildren().remove(0);
        blockMonitorLayout.getChildren().add(0, metricBox);
    }

    private Label getLayoutLabel() {

        Label layoutMenuTitle = new Label("TrackLayoutMenu");

        layoutMenuTitle.setPadding(new Insets(0,0,0,10));
        layoutMenuTitle.setTextAlignment(TextAlignment.LEFT);
        layoutMenuTitle.setMinWidth(gridBlock);
        layoutMenuTitle.setMinHeight(20);
        layoutMenuTitle.setMaxHeight(20);
        layoutMenuTitle.setAlignment(Pos.CENTER_LEFT);

        return layoutMenuTitle;
    }

    private GridPane getBlockInfoPane() {

        GridPane blockInfo = new GridPane();

        blockInfo.setAlignment(Pos.TOP_LEFT);
        blockInfo.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        blockInfo.setMinHeight(windowHeight/2);
        blockInfo.setMaxHeight(windowHeight/2);
        blockInfo.setMinWidth(windowWidth/3);
        blockInfo.setMaxWidth(windowWidth/3);

        return blockInfo;

    }

    private FlowPane getButtonMenuLayoutPane() {

        FlowPane buttonMenu = new FlowPane();

        buttonMenu.setAlignment(Pos.CENTER);
        buttonMenu.setMinWidth(windowWidth);
        buttonMenu.setMaxWidth(windowWidth);
        buttonMenu.setMinHeight(40);
        buttonMenu.setMaxHeight(40);

        return buttonMenu;

    }

    private GridPane getGridPane() {
        GridPane mainGrid = new GridPane();
        mainGrid.setAlignment(Pos.TOP_CENTER);
        mainGrid.setPadding(new Insets(0, 0, 0, 0));
        return mainGrid;
    }

    private GridPane getLayoutPane() {

        GridPane trackLayout = new GridPane();

        trackLayout.setAlignment(Pos.TOP_LEFT);
        trackLayout.setMinHeight((windowHeight/2)-70);
        trackLayout.setMaxHeight((windowHeight/2)-70);
        trackLayout.setMinWidth(windowWidth);
        trackLayout.setMaxWidth(windowWidth);

        return trackLayout;

    }

    private FlowPane getSwitchInfoPane(double height, double width, Switch aSwitch) {

        FlowPane switchInfra = new FlowPane();

        switchInfra.setMaxHeight((height/5)-4);
        switchInfra.setMinHeight((height/5)-4);
        switchInfra.setMaxWidth(width);
        switchInfra.setMinWidth(width);
        switchInfra.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        switchIcon.setFitHeight(switchInfra.getMaxHeight());
        switchIcon.setFitWidth(50);

        GridPane switchLabels = new GridPane();
        switchLabels.setAlignment(Pos.CENTER_LEFT);

        Label switchData = new Label("Switch: " + aSwitch.getSwitchNumber());
        Label switchStatusData = new Label("Status: " + aSwitch.getState());
        Label switchConnectData = new Label("Main: " + aSwitch.getMain() + " | Top: " + aSwitch.getTop() + " | Bottom: " + aSwitch.getBottom());

        switchLabels.add(switchData, 0, 0);
        switchLabels.add(switchStatusData, 0, 1);
        switchLabels.add(switchConnectData, 0, 2);

        switchData.setPadding(new Insets(0, 0, 0, 10));
        switchStatusData.setPadding(new Insets(0, 0, 0, 10));
        switchConnectData.setPadding(new Insets(0, 0, 0, 10));

        switchInfra.getChildren().add(switchIcon);
        switchInfra.getChildren().add(switchLabels);

        return switchInfra;

    }

    private FlowPane getStationInfoPane(double height, double width, Station station) {

        FlowPane stationInfra = new FlowPane();

        stationInfra.setMaxHeight((height / 6) - 4);
        stationInfra.setMinHeight((height / 6) - 4);
        stationInfra.setMaxWidth(width);
        stationInfra.setMinWidth(width);
        stationInfra.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        stationIcon.setFitHeight(stationInfra.getMaxHeight());
        stationIcon.setFitWidth(50);

        GridPane stationLabels = new GridPane();
        stationLabels.setAlignment(Pos.CENTER_LEFT);

        Label stationData = new Label("Station: " + station.getStationName());
        Label stationOccupancy = new Label("Occupancy: " + station.getOccupancy() + " | Departing: " + station.getDeparting());

        stationLabels.add(stationData, 0, 0);
        stationLabels.add(stationOccupancy, 0, 1);

        stationData.setPadding(new Insets(0, 0, 0, 10));
        stationOccupancy.setPadding(new Insets(0, 0, 0, 10));

        stationInfra.getChildren().add(stationIcon);
        stationInfra.getChildren().add(stationLabels);

        return stationInfra;
    }

    private FlowPane getCrossingInfoPane(double height, double width, Crossing crossing) {

        FlowPane crossingInfra = new FlowPane();

        crossingInfra.setMaxHeight((height / 6) - 4);
        crossingInfra.setMinHeight((height / 6) - 4);
        crossingInfra.setMaxWidth(width);
        crossingInfra.setMinWidth(width);
        crossingInfra.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        crossingIcon.setFitHeight(crossingInfra.getMaxHeight());
        crossingIcon.setFitWidth(50);

        GridPane crossingLabels = new GridPane();
        crossingLabels.setAlignment(Pos.CENTER_LEFT);

        Label crossingStatus = new Label("Crossroad Status: " + crossing.isActive());

        crossingLabels.add(crossingStatus, 0, 0);
        crossingLabels.setPadding(new Insets(0, 0, 0, 10));

        crossingInfra.getChildren().add(crossingIcon);
        crossingInfra.getChildren().add(crossingLabels);

        return crossingInfra;
    }

    private VBox getMetricBox(FlowPane infoPanel) {

        VBox metricBox = new VBox();
        metricBox.setSpacing(5);
        metricBox.setPadding(new Insets(0, 0, 0, 0));
        metricBox.setMaxWidth(windowWidth/3);
        metricBox.setMinWidth(windowWidth/3);
        metricBox.setMaxHeight(windowHeight/2);
        metricBox.setMinHeight(windowHeight/2);

        metricBox.getChildren().addAll(blockInfoLabel, infoPanel);

        return metricBox;

    }

    private FlowPane getBlockInfoPane(double windowWidth) {

        FlowPane blockInfoPanel = new FlowPane();

        Label length = new Label("Length (m): " + selectedBlock.getLength());
            length.setMinWidth(windowWidth);
            length.setPadding(new Insets(0,0,0,10));

        Label speedLimit = new Label("Speed Limit (mph): " + selectedBlock.getSpeedLimit());
            speedLimit.setMinWidth(windowWidth);
            speedLimit.setPadding(new Insets(0,0,0,10));

        Label grade = new Label("Grade (%): " + selectedBlock.getGrade());
            grade.setMinWidth(windowWidth);
            grade.setPadding(new Insets(0,0,0,10));

        Label elevation = new Label("Elevation (m): " + selectedBlock.getElevation());
            elevation.setMinWidth(windowWidth);
            elevation.setPadding(new Insets(0,0,0,10));

        Label cumElevation = new Label("Cumulative Elevation (m): " + selectedBlock.getCumulativeElevation());
            cumElevation.setMinWidth(windowWidth);
            cumElevation.setPadding(new Insets(0,0,0,10));

        Label direction = new Label("Direction: " + selectedBlock.getDirection());
            direction.setMinWidth(windowWidth);
            direction.setPadding(new Insets(0,0,0,10));

        Label temperature = new Label("Temperature (F): " + selectedBlock.getTemperature());
            temperature.setMinWidth(windowWidth);
            temperature.setPadding(new Insets(0,0,0,10));

        blockInfoPanel.getChildren().addAll(length, speedLimit, grade, elevation, cumElevation, direction, temperature);
        return blockInfoPanel;
    }

}
