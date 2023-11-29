package model;

public class VirtualCell extends Cell{

    public VirtualCell(int xPos, int yPos){
        super(xPos, yPos);
        topBorder = false;
        rightBorder = false;
        bottomBorder = false;
        leftBorder = false;
    }

    public void addTopBorder(){this.topBorder = true;}
    public void addRightBorder(){this.rightBorder=true;}
    public void addBottomBorder(){this.bottomBorder=true;}
    public void addLeftBorder(){this.leftBorder=true;}

    @Override
    public boolean equals(Object o){
        if(!(o instanceof VirtualCell cell)){
            return false;
        }
        else{
            return super.equals(o);
        }
    }

}
