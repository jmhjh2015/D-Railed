package TrackModel.Model;

import MBO.java.Train;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by adzun_000 on 1/17/2017.
 */
public class TrackModel
{

    private List<Line> lines;
    private List<Train> trainDispatchList;
    private int trainToDispatch = 0;
    private int lineCount;

    public TrackModel(){
        lines = new ArrayList<>();
        trainDispatchList = new ArrayList<>();
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public void importTrack(String fileName){

        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {

            String reader = null;
            String [] dataLine = null;

            while((reader = br.readLine()) != null)
            {

                dataLine = Arrays.stream(reader.split(",")).map(String::trim).toArray(String[]::new);

                // ignore column header lines
                if(dataLine[0].equals("Line") && dataLine[1].equals("Section")){
                    continue;
                }

                Line updateLine = null;
                Section updateSection = null;
                Block updateBlock = null;

                String line = dataLine[0].toUpperCase();
                String section = dataLine[1].toUpperCase();
                Integer blockNumber = new Integer(dataLine[2]);
                Double blockLength = new Double(dataLine[3]);
                Double blockGrade = new Double(dataLine[4]);
                Double speedLimit = new Double(dataLine[5]);
                String infrastructure = dataLine[6].toUpperCase();
                Double blockElevation = new Double(dataLine[7]);
                Double blockCumulativeElevation = new Double(dataLine[8]);
                String switchInfo = null;
                String direction = null;

                if(dataLine.length > 9) {
                    switchInfo = dataLine[9].toUpperCase();
                    if(dataLine.length > 10) {
                        direction = dataLine[10].toUpperCase();
                    }
                }

                Double temperature = generateTemperature();


                updateLine = new Line(line);
                updateSection = new Section(line, section);
                updateBlock = new Block(line, section, blockNumber);

                // check if the line exists in the model if it doesn't exist add the new line to the list of lines
                if(!existsLine(line))
                {
                    lineCount++;
                    updateLine = new Line(line);

                }
                else
                {
                    updateLine = getLine(line);
                }

                // check if the section exists in the line if it doesn't exist add the section to the lines list of sections
                if(!updateLine.existsSection(section))
                {
                    updateSection = new Section(line, section);

                }
                else
                {
                    updateSection = updateLine.getSection(section);

                }

                // check if the block exists in the section if it doesn't exist add the block to the sections list of blocks
                if(!updateSection.existsBlock(blockNumber)){

                    updateBlock = new Block(line, section, blockNumber);
                    updateBlock.setParameters(blockLength, blockGrade, speedLimit, blockElevation, blockCumulativeElevation, temperature, direction);

                    List<Object> infraSet = parseInfrastructure(updateLine, section, blockNumber, infrastructure, switchInfo);

                    Station updateStation = (Station) infraSet.get(0);
                    Crossing updateCrossing = (Crossing) infraSet.get(2);
                    String other = (String) infraSet.get(3);
                    Switch updateSwitch = null;

                    // switch is a main switch
                    if((Switch) infraSet.get(1) == null){
                        updateSwitch = (Switch) infraSet.get(4);

                        for(Switch sw : updateLine.getSwitches()){
                            for(Section se : updateLine.getSections()){
                                for(Block b : se.getBlocks()){
                                    if(b.getSwitch() != null && b.getSwitch().getSwitchNumber().equals(sw.getSwitchNumber())){
                                        b.setSwitch(sw); // update switch
                                    }
                                }
                            }
                        }

                    // switch is a sub switch
                    }else if((Switch) infraSet.get(4) == null){
                        updateSwitch = (Switch) infraSet.get(1);

                        for(Switch sw : updateLine.getSwitches()){
                            for(Section se : updateLine.getSections()){
                                for(Block b : se.getBlocks()){
                                    if(b.getSwitch() != null && b.getSwitch().getSwitchNumber().equals(sw.getSwitchNumber())){
                                        b.setSwitch(sw); // update switch
                                    }
                                }
                            }
                        }

                    }else{
                        updateSwitch = null;
                    }

                    updateBlock.setInfrastructure(updateStation, updateSwitch, updateCrossing, other);

                }else{
                    // the entry already exists in the track model
                    updateBlock = updateSection.getBlock(blockNumber);
                }

                if(!existsLine(line) && !updateLine.existsSection(section) && !updateSection.existsBlock(blockNumber))
                {
                    updateSection.addBlock(updateBlock);
                    updateLine.addSection(updateSection);
                    lines.add(updateLine);

                }
                else if(!updateLine.existsSection(section) && !updateSection.existsBlock(blockNumber))
                {
                    updateSection.addBlock(updateBlock);
                    updateLine.addSection(updateSection);

                }
                else if(!updateSection.existsBlock(blockNumber))
                {
                    updateSection.addBlock(updateBlock);

                }

            }

        }catch(IOException ioe){
            System.out.println("ERROR: " + ioe.getMessage());
        }
    }

    ///////////////////////////////
    // Infrastructure Parsing    //
    ///////////////////////////////


    private List<Object> parseInfrastructure(Line updateLine, String section, Integer blockNumber, String infrastructure, String switchInfo) {

        List<Object> infraSet = new ArrayList<>();

        // 0.) Application.Track.Model.Station infrastructure is found
        if(infrastructure.contains("STATION"))
        {
            infraSet.add(parseStation(section, infrastructure));

        } // Main switch infrastructure found
        else
        {
            infraSet.add(null);
        }

        // 1.) Application.Track.Model.Switch main infrastructure is found
        if(infrastructure.contains("SWITCH"))
        {
            Switch updateSwitch = parseSwitch(updateLine, section, blockNumber, switchInfo, infrastructure);
            getLine(updateLine.getLine()).addSwitch(updateSwitch);
            infraSet.add(updateSwitch);
        }
        else
        {
            infraSet.add(null);
        }

        // 2.) Application.Track.Model.Crossing infrastructure is found
        if(infrastructure.contains("CROSSING"))
        {
            infraSet.add(new Crossing(blockNumber));

        }
        else
        {
            infraSet.add(null);
        }

        // 3.) Underground infrastructure is found
        if(infrastructure.contains("UNDERGROUND"))
        {
            infraSet.add("UNDERGROUND");

        }
        else
        {
            infraSet.add(null);
        }

        // 4.) A switch that is not apart of the main switch infrastructure needs added
        if(switchInfo != null && switchInfo.contains("SWITCH") && !infrastructure.contains("SWITCH"))
        {

            Switch updateSwitch = parseSubSwitch(updateLine, switchInfo.split(" ")[1]);
            updateSwitch.addConnector(blockNumber);
            infraSet.add(updateSwitch);

        }
        else
        {
            infraSet.add(null);
        }

        return infraSet;
    }

    private Switch parseSubSwitch(Line updateLine, String aSwitch) {

        Switch updateSwitch = null;

        // If the switch exists already add it to the infrastructure of the main or sub switch
        if(updateLine.existsSwitch(new Integer(aSwitch)))
        {
            updateSwitch = updateLine.getSwitch(new Integer(aSwitch));

        // If the switch doesn't exist yet and this switch is a sub switch of the system
        }
        else
        {
            updateSwitch = new Switch(new Integer(aSwitch));
        }

        return updateSwitch;
    }

    private Switch parseSwitch(Line updateLine, String section, Integer blockNumber, String switchInfo, String infra) {

        Switch updateSwitch = null;

        // If a switch of the same switch number exists already this switch is added as the main of the switch
        if(updateLine.existsSwitch(new Integer(switchInfo.split(" ")[1].trim())))
        {
            updateSwitch = updateLine.getSwitch(new Integer(switchInfo.split(" ")[1]));

        } // If a switch doesn't exit create a new switch and set this block as the main block of the switch
        else
        {
            updateSwitch = new Switch(new Integer(switchInfo.split(" ")[1]), blockNumber);

        }

        // if the switch is attached to the yard set to/from directions accordingly
        if(infra.contains("YARD"))
        {
            if(infra.contains("TO"))
                updateSwitch.setFromYard(true);

            if(infra.contains("FROM"))
                updateSwitch.setFromYard(true);

        }

        return updateSwitch;

    }

    private Station parseStation(String section, String infrastructure) {

        Station updateStation = null;

        // If infrastructure contains: STATION: <station-name>
        if(infrastructure.contains(":"))
        {
            updateStation = new Station(infrastructure.split(":")[1].trim(), section);

        } // If infrastructure contains: STATION; <station-name>
        else if(infrastructure.contains(";"))
        {
            updateStation = new Station(infrastructure.split(";")[1].trim(), section);

        } // If infrastructure contains: STATION (no name is provided)
        else if(infrastructure.trim().equals("STATION"))
        {
            updateStation = new Station("Unknown", section);
        }

        return updateStation;
    }

    ////////////////
    // OTHER      //
    ////////////////


    private Double generateTemperature() {
        return new Double(new Random().nextInt(100));
    }

    private boolean existsLine(String line){

        for(Line l : lines){
            if(l.getLine().equals(line))
                return true;
        }

        return false;
    }

    private Line getLine(String line){
        for(Line l : lines){
            if(l.getLine().equals(line))
                return l;
        }

        return null;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public void randomDispatch(String line){

        getRandomTrains();

        if(line != null && existsLine(line)) {
            String secVal = "A";
            int blockNo = 2;

            System.out.println("Train dispatched to: " + line + ":" + secVal + ":" + blockNo);
            getLine(line).placeTrain(secVal, blockNo, trainDispatchList.get(trainToDispatch));
            trainToDispatch++;
        }
    }

    private void getRandomTrains(){
        trainDispatchList.add(new Train(0));
        trainDispatchList.add(new Train(1));
    }

}
