package Application.Track.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adzun_000 on 1/17/2017.
 */

/***
 * Sections represent related connected blocks of the track model
 *
 *  ex. section: A
 *      line:    Green
 *      block: {list-of-blocks}
 *      blockCount: 3
 *      totalLength: 300
 *
 */
public class Section {

    private String line;
    private String section;
    private List<Block> blocks;
    private Block nextSection;
    private Block previousSection;
    private int count;
    private Double length;


    /***
     *
     *  Basic constructor of a Application.Track.Model.Section: specifies attributes that the section and related blocks
     *  are owned by.
     *
     * @param sectionDesignation - the section ID of the track
     * @param lineDesignation - the line the section belongs to
     */
    public Section(String sectionDesignation, String lineDesignation){
        section = sectionDesignation;
        line = lineDesignation;
        blocks = new ArrayList<>();
        count = 0;
        length = 0.0;
    }

    /***
     * @return the section ID for this track segment
     */
    public String getSection() {
        return section;
    }

    public Double getLength(){

        Double totalBlockLength = 0.0;

        for(Block b : blocks){
            totalBlockLength += b.getLength();
        }

        return totalBlockLength;
    }

    /***
     * Sets the ID of this section of track
     *
     * @param section - new section ID for this track
     */
    public void setSection(String section) {
        this.section = section;
    }

    /***
     * @return the line name (usually color) this section is owned by
     */
    public String getLine() {
        return line;
    }

    /***
     * Sets the line name (usually color) this section is owned by
     *
     * @param line - new line name of this section owner
     */
    public void setLine(String line) {
        this.line = line;
    }

    /***
     * @return a list of blocks that is contained in the section
     */
    public List<Block> getBlocks() {
        return blocks;
    }

    /***
     *  Sets the blocks for this section
     *
     * @param blocks - sets a new list of blocks
     */
    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
        count = blocks.size();
    }

    /***
     * Add a new block to the sections block list
     *
     * @param block - a Application.Track.Model.Block object belonging to this section
     */
    public void addBlock(Block block){
        count++;
        blocks.add(block);
    }

    /***
     * Remove a block from the section
     *
     * @param block to remove
     * @return a true on success
     */
    public boolean removeBlock(Block block){
        this.count--;
        return blocks.remove(block);
    }

    /***
     * Checks if the section contains a specific Application.Track.Model.Block already
     *
     * @param block - block to search for
     * @return true if the block was found within the section
     */
//    public boolean hasBlock(Integer block){
//        return blocks.contains(block);
//    }

    public boolean existsBlock(Integer block){
        for(Block b : blocks){
            if(b.getBlockNumber().equals(block))
                return true;
        }

        return false;
    }


}
