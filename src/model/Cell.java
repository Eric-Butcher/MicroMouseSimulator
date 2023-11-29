package model;

import controller.TileUpdate;

import java.util.Objects;

public abstract class Cell {

    private final int xPos;
    private final int yPos;
    private boolean isTraversed = false;

    protected boolean topBorder;
    protected boolean rightBorder;
    protected boolean bottomBorder;
    protected boolean leftBorder;

    private boolean isGoal = false;

    private boolean isInitialized = false;

    public Cell(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }


    public boolean isTraversed() {
        return isTraversed;
    }

    public void setTraversed(boolean traversed) {
        this.isTraversed = traversed;
    }

    public boolean isGoal() {
        return isGoal;
    }

    public void setGoal(boolean goal) {
        isGoal = goal;
    }

    public boolean isTopBorder() {
        return topBorder;
    }

    public boolean isRightBorder() {
        return rightBorder;
    }

    public boolean isBottomBorder() {
        return bottomBorder;
    }

    public boolean isLeftBorder() {
        return leftBorder;
    }


    public boolean isInitialized() {
        return isInitialized;
    }

    public void initializeCell() {
        this.isInitialized = true;
    }

    public static TileUpdate makeTileUpdateFromCell(Cell cell, boolean isCurrent, boolean toHighlight) {
        TileUpdate retVal = new TileUpdate(cell.getxPos(), cell.getyPos(), cell.isTopBorder(), cell.isRightBorder(), cell.isBottomBorder(), cell.isLeftBorder(), false, cell.isInitialized(), cell.isTraversed(), toHighlight, isCurrent, cell.isGoal());
        return retVal;
    }
    @Override
    public int hashCode() {
        return Objects.hash(getxPos(), getyPos());
    }

    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Cell cell)){
            return false;
        }
        else{
            return this.getyPos() == cell.getyPos() && this.getxPos()==cell.getxPos() && this.isBottomBorder()==cell.isBottomBorder() && this.isLeftBorder()==cell.isLeftBorder() && this.isTopBorder()== cell.isTopBorder() && this.isRightBorder()==cell.isRightBorder() && this.isTraversed()==cell.isTraversed() && this.isInitialized()==cell.isInitialized();
        }
    }
}
