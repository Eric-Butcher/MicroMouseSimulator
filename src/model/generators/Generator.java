package model.generators;

import controller.ViewUpdatePacket;
import model.Grid;
import model.RealityCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Generator {

//    private Cell[][] grid = new Cell[Constants.mazeLength][Constants.mazeLength];

    private final Grid<RealityCell> grid;

    private boolean done = false;

    public Generator() {

//        for (int i = 0; i < 16; i++){
//            for (int j = 0; j < 16; j++){
//                Cell cell = new Cell(i, j);
//                this.grid[i][j] = cell;
//            }
//        }

        this.grid = new Grid(RealityCell.class);
    }

    public static RealityCell popRandomCellFromList(List<RealityCell> list) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(list.size());
        return list.remove(randomIndex);
    }

    public static ArrayList<RealityCell> getInitializedCells(List<RealityCell> list) {
        ArrayList<RealityCell> retVal = new ArrayList<>(4);
        for (RealityCell cell : list) {
            if (cell.isInitialized()) {
                retVal.add(cell);
            }
        }
        return retVal;
    }

    public static ArrayList<RealityCell> getUnInitializedCells(List<RealityCell> list) {
        ArrayList<RealityCell> retVal = new ArrayList<>(4);
        for (RealityCell cell : list) {
            if (!cell.isInitialized()) {
                retVal.add(cell);
            }
        }
        return retVal;
    }

    protected void doneGenerating() {
        this.done = true;
    }

    public Grid<RealityCell> getGrid() {
        return grid;
    }

    public boolean getDoneGenerating() {
        return this.done;
    }

    public void setDone() {
        this.done = true;
    }

    public abstract ViewUpdatePacket makeViewUpdatePacket();

    public abstract void iterate();

    public abstract void finish();


}
