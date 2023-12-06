package model.solvers;

import controller.ViewUpdatePacket;
import model.Cell;
import model.Grid;
import model.RealityCell;
import model.VirtualCell;
import utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public abstract class Solver {

//    private Cell[][] grid = new Cell[Constants.mazeLength][Constants.mazeLength];

    protected VirtualCell startPoint;
    protected ArrayList<VirtualCell> endPoints = new ArrayList<>();
    private final Grid<RealityCell> realityGrid;

    private Grid<VirtualCell> virtualGrid = new Grid<>(VirtualCell.class);
    private boolean done = false;

    public Solver(Grid<RealityCell> grid) {
        this.realityGrid = grid;
        this.startPoint = this.virtualGrid.getCell(0, 0);

        ArrayList<VirtualCell> ends = new ArrayList<>();
        if ((Constants.mazeLength % 2) == 0) {
            ends.add(this.virtualGrid.getCell(Constants.maxCellIndex / 2, Constants.maxCellIndex / 2));
            ends.add(this.virtualGrid.getCell(Constants.maxCellIndex / 2 + 1, Constants.maxCellIndex / 2));
            ends.add(this.virtualGrid.getCell(Constants.maxCellIndex / 2, Constants.maxCellIndex / 2 + 1));
            ends.add(this.virtualGrid.getCell(Constants.maxCellIndex / 2 + 1, Constants.maxCellIndex / 2 + 1));
        } else {
            ends.add(this.virtualGrid.getCell(Constants.maxCellIndex / 2, Constants.maxCellIndex / 2));
        }


        this.endPoints = ends;

        for (Cell cell : this.endPoints) {
            cell.setGoal(true);
        }
    }

    public Solver(Grid<RealityCell> grid, RealityCell startPoint, ArrayList<RealityCell> endPoints) {
        this.realityGrid = grid;
        this.startPoint = virtualGrid.getCell(startPoint.getxPos(), startPoint.getyPos());


        for (RealityCell cell : endPoints) {
            this.endPoints.add(virtualGrid.getCell(cell.getxPos(), cell.getyPos()));
            cell.setGoal(true);
        }
    }

    public static ArrayList<VirtualCell> getUnTraversedCells(List<VirtualCell> list) {
        ArrayList<VirtualCell> retVal = new ArrayList<>(4);
        for (VirtualCell cell : list) {
            if (!cell.isTraversed()) {
                retVal.add(cell);
            }
        }
        return retVal;
    }

    public static ArrayList<VirtualCell> getTraversedCells(List<VirtualCell> list) {
        ArrayList<VirtualCell> retVal = new ArrayList<>(4);
        for (VirtualCell cell : list) {
            if (cell.isTraversed()) {
                retVal.add(cell);
            }
        }
        return retVal;
    }

    public Grid<RealityCell> getRealityGrid() {return realityGrid;}

    public Grid<VirtualCell> getVirtualGrid(){return virtualGrid;}

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public VirtualCell getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(VirtualCell startPoint) {
        this.startPoint = startPoint;
    }

    public ArrayList<VirtualCell> getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(ArrayList<VirtualCell> endPoints) {
        this.endPoints = endPoints;
    }

    public ArrayList<VirtualCell> getUntraversedReachableNeighbors(VirtualCell center) {
        ArrayList<VirtualCell> adjacents = this.virtualGrid.getAdjacentCells(center);
        ArrayList<VirtualCell> untraversed = getUnTraversedCells(adjacents);
        ArrayList<VirtualCell> retVal = new ArrayList<>();
        for (VirtualCell cell : untraversed) {
            try {
                if (virtualGrid.isTherePathBetweenCells(center, cell)) {
                    retVal.add(cell);
                }
            }
            catch(Exception e){
                System.out.println(center.getClass());
                System.out.println(center.getxPos() + ", " + center.getyPos());
                System.out.println(cell.getClass());
                System.out.println(cell.getxPos() + ", " + cell.getyPos());
                System.out.println("You used getUntraveredReachableNeighbors and have incorrect Cell types");
            }
        }

        return retVal;
    }

    public ArrayList<VirtualCell> getTraversedReachableNeighbors(VirtualCell center) {
        ArrayList<VirtualCell> adjacents = this.virtualGrid.getAdjacentCells(center);
        ArrayList<VirtualCell> traversedCells = getTraversedCells(adjacents);
        ArrayList<VirtualCell> retVal = new ArrayList<>();
        for (VirtualCell cell : traversedCells) {
            try {
                if (virtualGrid.isTherePathBetweenCells(center, cell)) {
                    retVal.add(cell);
                }
            }
            catch (Exception e){
                System.out.println("You used getTraversedReachableNeighbors and have incorrect Cell types");
            }
        }

        return retVal;
    }

    public boolean atDestination(VirtualCell current) {
        for (VirtualCell destination : endPoints) {
            if (current.equals(destination)) {
                return true;
            }
        }
        return false;
    }

    public abstract ViewUpdatePacket makeViewUpdatePacket();

    public abstract void iterate();

    public abstract void finish();

    public void updateVirtualGrid(boolean topBorder, boolean leftBorder, boolean bottomBorder, boolean rightBorder, int xPos, int yPos){
        if(topBorder){
            this.virtualGrid.getCell(xPos, yPos).addTopBorder();
        }

        if(leftBorder){
            this.virtualGrid.getCell(xPos, yPos).addLeftBorder();
        }

        if(bottomBorder){
            this.virtualGrid.getCell(xPos, yPos).addBottomBorder();
        }

        if(rightBorder){
            this.virtualGrid.getCell(xPos, yPos).addRightBorder();
        }
    }
}
