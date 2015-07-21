package battleShipGame;

/**
 *
 * @author adhoulih
 */
public class Section{
    
    private int sectionStatus;
    private int shipID;
    private Section nextShipSection;
    
    public Section(int sectionStatus, int shipID){
        this.sectionStatus = sectionStatus;
        this.shipID = shipID;
    }
    
    public Section getNextShipSection(){
        return this.nextShipSection;
    }
    public void setNextShipSection(Section section){
        this.nextShipSection = section;
    }
    
    public void setSectionStatus(int status){
        this.sectionStatus = status;
    }
    public int getSectionStatus(){
        return this.sectionStatus;
    }
    
    public int getShipID(){
        return this.shipID;
    
    }

    
}