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
    
    public Section getNextShipSection(){
        return this.nextNodeSection;
    }
    public void setNextShipSection(Section section){
        this.nextNodeSection = section;
    }
    
    public void setSectionStatus(int status){
        this.sectionStatus = status;
    }
    public int getSectionStatus(){
        return this.sectionStatus;
    }
    
    public int getShipID(){
        return this.nodeID;
    
    }

    
}