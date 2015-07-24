/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleShipGame;

/**
 *
 * @author adhoulih
 */
public final class ShipLinkedList {
    
    private Section head;
    private int shipType;
    private int shipID;
    private Boolean shipStatus = true;
    
    ShipLinkedList(int shipType, int shipID){
        this.shipType = shipType;
        this.shipID = shipID;
        this.head = new Section(shipType, shipID);
        for(int i =1; i< shipType; i++){
            this.addShipSection(new Section(shipType, shipID));
        }
        
    }
    
    public void addShipSection(Section section){
        if(head == null){
            head = section;
        }
        
        Section currentSection = head;
        while(currentSection.getNextShipSection() != null){
            currentSection = currentSection.getNextShipSection();
        }
        currentSection.setNextShipSection(section);
    }
    
    public Section getShipSection(int index){
        //returns section based on index value
        Section currentSection = head;
        for(int i = 0; i< index; i++){
            if(currentSection.getNextShipSection() == null){
                return null;
            }
            currentSection = currentSection.getNextShipSection();
        }
        return currentSection;
    }

    /**
     * @return the shipType
     */
    public int getShipType() {
        return shipType;
    }

    /**
     * @param shipType the shipType to set
     */
    public void setShipType(int shipType) {
        this.shipType = shipType;
    }

    /**
     * @return the shipID
     */
    public int getShipID() {
        return shipID;
    }

    /**
     * @param shipID the shipID to set
     */
    public void setShipID(int shipID) {
        this.shipID = shipID;
    }

    /**
     * @param shipStatus the shipStatus to set
     */
    public void setShipStatus(Boolean shipStatus) {
        this.shipStatus = shipStatus;
    }

    /**
     * @return the shipStatus
     */
    public Boolean getShipStatus() {
        return shipStatus;
    }
    
}
