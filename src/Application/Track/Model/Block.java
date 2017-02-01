package Application.Track.Model;

/**
 * Created by andrew on 1/17/2017.
 */

/***
 * Blocks represent track infrastructure and physical attributes. They serve as pieces and the
 * building "blocks" of the overall track model.
 *
 *  ex. block: 1
 *      section: A
 *      line: Green
 *      length: 100 m
 *      grade: 0x5 %
 *      speedLimit: 55 km/hr
 *      elevation: 1.0 m
 *      cumulative elevation: 1.5 m
 *      temperature: 60 F
 *      direction: BI
 *      trainData: Train 1
 *      train: true
 *
 */

public class Block {

    // Application.Track.Model.Block Identifiers
    private String line;
    private String section;
    private Integer blockNumber;

    // Application.Track.Model.Block Configurable Parameters
    private Double length;
    private Double grade;
    private Double speedLimit;
    private Double elevation;
    private Double cumulativeElevation;
    private Double temperature;
    private String direction;

    // Infrastructure and Functionality
    String trainData; // this needs to be replaced by the train from the train model (only when a train is present)
    private boolean hasTrain;
    private Switch aSwitch;
    private Station station;
    private Crossing crossing;
    private String other;

    // System State
    private String trackState;
    private boolean railState;
    private boolean circuitState;
    private boolean powerState;

    // Connected Blocks
    private Block nextBlock;
    private Block previousBlock;

    // To Train: Send Power To Train
    private Double setPointSpeed;
    private Double authority;

    public Block(String line, String section, Integer blockNumber){
        this.line = line;
        this.section = section;
        this.blockNumber = blockNumber;
    }

    public void setParameters(Double length, Double grade, Double speedLimit, Double elevation, Double cumElevation, Double temperature, String direction){
        this.length = length;
        this.grade = grade;
        this.speedLimit = speedLimit;
        this.elevation = elevation;
        this.cumulativeElevation = cumElevation;
        this.temperature = temperature;
        this.direction = direction;
    }

    public void setInfrastructure(Station station, Switch aSwitch, Crossing crossing, String other){
        this.station = station;
        this.aSwitch = aSwitch;
        this.crossing = crossing;
        this.other = other;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Double getLength(){
        return this.length;
    }

    public Double getSetPointSpeed() {
        return setPointSpeed;
    }

    public void setSetPointSpeed(Double setPointSpeed) {
        this.setPointSpeed = setPointSpeed;
    }

    public Double getAuthority() {
        return authority;
    }

    public void setAuthority(Double authority) {
        this.authority = authority;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}


