package TrackController.Classes;

public class Switch
{
    private String ID;
    private boolean state; //0 = connected to SB1, 1 = connected to SB2
    private Block subBlock1;
    private Block subBlock2;
    private Block mainBlock;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Block getSubBlock1() {
        return subBlock1;
    }

    public void setSubBlock1(Block subBlock1) {
        this.subBlock1 = subBlock1;
    }

    public Block getSubBlock2() {
        return subBlock2;
    }

    public void setSubBlock2(Block subBlock2) {
        this.subBlock2 = subBlock2;
    }

    public Block getMainBlock() {
        return mainBlock;
    }

    public void setMainBlock(Block mainBlock) {
        this.mainBlock = mainBlock;
    }

    public Switch(String setID, Block mBlock, Block SB1, Block SB2)
    {
        ID = setID;
        state = false;
        subBlock1 = SB1;
        subBlock2 = SB2;
        mainBlock = mBlock;
    }
}