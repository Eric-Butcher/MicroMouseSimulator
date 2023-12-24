package model;

import java.util.ArrayList;

public class RealityGrid {

    private Grid<RealityCell> grid;

    public RealityGrid(){
        grid = new Grid<>(RealityCell.class);
    }

    public RealityCell getRealityCell(int xLoc, int yLoc){
        return grid.getCell(xLoc, yLoc);
    }

    public RealityCell[][] getRealityCellGrid(){
        return grid.getCellGrid();
    }

    public RealityCell getRandomGridRealityCell(){
        return grid.getRandomGridCell();
    }

    public void createPathBetweenRealityCells(RealityCell from, RealityCell to){
        grid.createPathBetweenCells(from, to);
    }

    public boolean isTherePathBetweenRealityCells(RealityCell from, RealityCell to) throws Exception{
        return grid.isTherePathBetweenCells(from, to);
    }

    public ArrayList<RealityCell> getAdjacentRealityCells(RealityCell centerCell){
        return grid.getAdjacentCells(centerCell);
    }

    public void unSolveGrid(){
        grid.unSolveGrid();
    }
}
