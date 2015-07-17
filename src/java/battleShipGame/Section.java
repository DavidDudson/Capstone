package battleShipGame;

/**
 *
 * @author adhoulih
 */
public class Section{
    
    private int sectionNum;
    private Boolean sectionStatus = true;
    
    public Section(int sectionNum){
        this.sectionNum = sectionNum;
    }
    public void setDeadSection(){
        this.sectionStatus = false;
    }
    public int getSectionNum(){
        return sectionNum;
    
    }
    public void setSeciontNum(int newVal){
        this.sectionNum = newVal;
    }
    
}