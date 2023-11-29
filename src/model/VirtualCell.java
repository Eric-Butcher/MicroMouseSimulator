package model;

public class VirtualCell extends Cell{

    public VirtualCell(int xPos, int yPos){
        super(xPos, yPos);
        topBorder = false;
        rightBorder = false;
        bottomBorder = false;
        leftBorder = false;
        isVirtual = true;
    }

    public void addTopBorder(){this.topBorder = true;}
    public void addRightBorder(){this.rightBorder=true;}
    public void addBottomBorder(){this.bottomBorder=true;}
    public void addLeftBorder(){this.leftBorder=true;}


}
