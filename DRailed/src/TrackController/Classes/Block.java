package TrackController.Classes;

public class Block
{
    private String ID;
    private String switchID1;
    private String switchID2;
    private String stationName;
    private boolean occupied;
    private boolean broken;
    private boolean closed;
    private boolean crossings;
    private boolean switches;
    private boolean hasCrossings;

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }


    public boolean isHasCrossings() {
        return hasCrossings;
    }

    public void setHasCrossings(boolean hasCrossings) {
        this.hasCrossings = hasCrossings;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSwitchID1() {
        return switchID1;
    }

    public void setSwitchID1(String switchID1) {
        this.switchID1 = switchID1;
    }

    public String getSwitchID2() {
        return switchID2;
    }

    public void setSwitchID2(String switchID2) {
        this.switchID2 = switchID2;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    public boolean isCrossings() {
        return crossings;
    }

    public void setCrossings(boolean crossings) {
        this.crossings = crossings;
    }

    public boolean isSwitches() {
        return switches;
    }

    public void setSwitches(boolean switches) {
        this.switches = switches;
    }


    public Block(String setID)
    {
        ID = setID;
        switchID1 = null;
        switchID2 = null;
        stationName = null;
        occupied = false;
        broken = false;
        switches = false;
        hasCrossings = false;
        closed = false;
    }

}