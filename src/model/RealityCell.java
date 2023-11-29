package model;

import controller.TileUpdate;

public class RealityCell extends Cell {

    public RealityCell(int xPos, int yPos){
        super(xPos, yPos);
        topBorder = true;
        rightBorder = true;
        bottomBorder = true;
        leftBorder = true;
    }

    public static TileUpdate makeTileUpdateFromCell(RealityCell cell, boolean isCurrent, boolean toHighlight) {
        TileUpdate retVal = new TileUpdate(cell.getxPos(), cell.getyPos(), cell.isTopBorder(), cell.isRightBorder(), cell.isBottomBorder(), cell.isLeftBorder(), false, cell.isInitialized(), cell.isTraversed(), toHighlight, isCurrent, cell.isGoal());
        return retVal;
    }

    public void removeTopBorder() {
        this.topBorder = false;
    }

    public void removeRightBorder() {
        this.rightBorder = false;
    }

    public void removeBottomBorder() {
        this.bottomBorder = false;
    }

    public void removeLeftBorder() {
        this.leftBorder = false;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof RealityCell cell)){
            return false;
        }
        else{
            return super.equals(o);
        }

    }
}
