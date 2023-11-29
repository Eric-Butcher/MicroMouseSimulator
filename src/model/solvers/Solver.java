package model.solvers;

import controller.ViewUpdatePacket;
import model.Cell;
import model.Grid;
import model.VirtualCell;
import utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public abstract class Solver {

//    private Cell[][] grid = new Cell[Constants.mazeLength][Constants.mazeLength];

    protected VirtualCell startPoint;
    protected ArrayList<VirtualCell> endPoints;
    private final Grid<VirtualCell> grid;
    private boolean done = false;

    public Solver(Grid<VirtualCell> grid) {
        this.grid = grid;
        this.startPoint = this.grid.getCell(0, 0);

        ArrayList<VirtualCell> ends = new ArrayList<>();
        if ((Constants.mazeLength % 2) == 0) {
            ends.add(this.grid.getCell(Constants.maxCellIndex / 2, Constants.maxCellIndex / 2));
            ends.add(this.grid.getCell(Constants.maxCellIndex / 2 + 1, Constants.maxCellIndex / 2));
            ends.add(this.grid.getCell(Constants.maxCellIndex / 2, Constants.maxCellIndex / 2 + 1));
            ends.add(this.grid.getCell(Constants.maxCellIndex / 2 + 1, Constants.maxCellIndex / 2 + 1));
        } else {
            ends.add(this.grid.getCell(Constants.maxCellIndex / 2, Constants.maxCellIndex / 2));
        }


        this.endPoints = ends;

        for (Cell cell : this.endPoints) {
            cell.setGoal(true);
        }
    }

    public Solver(Grid<VirtualCell> grid, VirtualCell startPoint, ArrayList<VirtualCell> endPoints) {
        this.grid = grid;
        this.startPoint = startPoint;
        this.endPoints = endPoints;

        for (Cell cell : this.endPoints) {
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

    public Grid<VirtualCell> getGrid() {
        return grid;
    }

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
        ArrayList<VirtualCell> adjacents = this.grid.getAdjacentCells(center);
        ArrayList<VirtualCell> untraversed = getUnTraversedCells(adjacents);
        ArrayList<VirtualCell> retVal = new ArrayList<>();
        for (VirtualCell cell : untraversed) {
            try {
                if (grid.isTherePathBetweenCells(center, cell)) {
                    retVal.add(cell);
                }
            }
            catch(Exception e){
                System.out.println("You have incorrect Cell types");
            }
        }

        return retVal;
    }

    public ArrayList<VirtualCell> getTraversedReachableNeighbors(VirtualCell center) {
        ArrayList<VirtualCell> adjacents = this.grid.getAdjacentCells(center);
        ArrayList<VirtualCell> traversedCells = getTraversedCells(adjacents);
        ArrayList<VirtualCell> retVal = new ArrayList<>();
        for (VirtualCell cell : traversedCells) {
            try {
                if (grid.isTherePathBetweenCells(center, cell)) {
                    retVal.add(cell);
                }
            }
            catch (Exception e){
                System.out.println("You have incorrect Cell types");
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
}
