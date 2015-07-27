package battleShipGame;

/**
 *
 * @author adhoulih
 */
public class Section{
    
    private int sectionStatus;
    private final int nodeID;
    private Section nextNodeSection;
    
    public Section(int sectionStatus, int shipID){
        this.sectionStatus = sectionStatus;
        this.nodeID = shipID;
    }

    /**
     *
     * @return next node in the current ship section
     */
    public Section getNextShipSection(){
        return this.nextNodeSection;
    }

    /**
     *
     * @param section set the next section of the current ship section
     */
    public void setNextShipSection(Section section){
        this.nextNodeSection = section;
    }

    /**
     *
     * @param status set the status of the section
     */
    public void setSectionStatus(int status){
        this.sectionStatus = status;
    }


    /**
     *
     * @return section status
     */
    public int getSectionStatus(){
        return this.sectionStatus;
    }


    /**
     *@return nodeID
     */
    public int getShipID(){
        return this.nodeID;
    
    }

    
}