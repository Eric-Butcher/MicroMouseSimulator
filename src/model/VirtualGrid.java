package model;

import java.util.ArrayList;

public class VirtualGrid {

    private Grid<VirtualCell> grid;
    public VirtualGrid(){
        grid = new Grid<>(VirtualCell.class);
    }

    public VirtualCell getVirtualCell(int xLoc, int yLoc){
        return grid.getCell(xLoc, yLoc);
    }

    public VirtualCell[][] getVirtualCellGrid(){
        return grid.getCellGrid();
    }

    public VirtualCell getRandomGridVirtualCell(){
        return grid.getRandomGridCell();
    }

    public boolean isTherePathBetweenVirtualCells(VirtualCell from, VirtualCell to) throws Exception{
        return grid.isTherePathBetweenCells(from, to);
    }

    public ArrayList<VirtualCell> getAdjacentVirtualCells(VirtualCell centerCell){
        return grid.getAdjacentCells(centerCell);
    }

    public void unSolveGrid(){
        grid.unSolveGrid();
    }
}
