package battleShipGame;

/**
 *
 * @author adhoulih
 */
public class Ship {
    private Boolean shipState = true;
    private Section [] shipSections;
    private int shipType;
    private int shipID; 
    
    public Ship(int shipType, int shipID){
            this.shipType = shipType;
            this.shipID = shipID;
            this.shipSections = new Section[shipType];
            for(int i =0; i< shipType; i++){
                shipSections[i] = new Section(i);
            }
            
    }
    public Boolean getShipStatus(){
        return shipState;
    }
    public int getShipType(){
        return shipType;
    }
    public Section[] getShipSections(){
        return shipSections;
    }
    public void setShipStatus(Boolean status){
        shipState = status;
    }
    public void setDeadSection(int deadSection){
        shipSections[deadSection].setDeadSection();
    }
}
    

