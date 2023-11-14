package model.solvers;

import controller.TileUpdate;
import controller.ViewUpdatePacket;
import model.Cell;
import utilities.Constants;
import model.Grid;
import java.util.*;


public class FloodFillSearchSolver extends Solver{

    private int[][] intGrid = new int[16][16];

    private Grid gridCopy = new Grid();
    private Cell currentCell = startPoint;

    private final Comparator<Cell> hueristicComparator = new Comparator<Cell>() {
        @Override
        public int compare(Cell o1, Cell o2) {
            return Integer.compare(heuristic(o1), heuristic(o2));
        }
    };

    public FloodFillSearchSolver(Grid grid){
        super(grid);
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                gridCopy.getCell(i, j).removeTopBorder();
                gridCopy.getCell(i, j).removeBottomBorder();
                gridCopy.getCell(i, j).removeLeftBorder();
                gridCopy.getCell(i, j).removeRightBorder();
            }
        }

    }

    public FloodFillSearchSolver(Grid grid, Cell startPoint, ArrayList<Cell> endPoints){
        super(grid, startPoint, endPoints);
    }
    public Cell getCurrentCell() {
        return currentCell;
    }

    public Comparator<Cell> getHueristicComparator(){
        return hueristicComparator;
    }

    public int manhattanDistance(Cell current, Cell target) {
        int dx = Math.abs(current.getxPos() - target.getxPos());
        int dy = Math.abs(current.getyPos() - target.getyPos());
        int retVal = dx + dy;
        return retVal;
    }

    public int heuristic(Cell cell) {
        int minDistance = Integer.MAX_VALUE;
        int calculatedDistance = 0;
        for (Cell target : this.endPoints) {
            calculatedDistance = manhattanDistance(cell, target);
            minDistance = Math.min(minDistance, calculatedDistance);
        }
        return minDistance;
    }

    public ArrayList<Cell> getGridCopyEndpoints(){
        ArrayList<Cell> ends = new ArrayList<>();
        if ((Constants.mazeLength % 2) == 0) {
            ends.add(this.gridCopy.getCell(Constants.maxCellIndex / 2, Constants.maxCellIndex / 2));
            ends.add(this.gridCopy.getCell(Constants.maxCellIndex / 2 + 1, Constants.maxCellIndex / 2));
            ends.add(this.gridCopy.getCell(Constants.maxCellIndex / 2, Constants.maxCellIndex / 2 + 1));
            ends.add(this.gridCopy.getCell(Constants.maxCellIndex / 2 + 1, Constants.maxCellIndex / 2 + 1));
        } else {
            ends.add(this.gridCopy.getCell(Constants.maxCellIndex / 2, Constants.maxCellIndex / 2));
        }
        return ends;
    }
    public void fill(){
        Queue<Cell> cellQueue = new LinkedList<>();
        HashMap<Cell, Integer> cellToIndexMap = new HashMap<>();
        HashSet<Cell> queueSet = new HashSet<>();
        for(Cell endPoints: this.getGridCopyEndpoints()){
            cellQueue.add(endPoints);
            cellToIndexMap.put(endPoints, 0);
            queueSet.add(endPoints);
        }
        while(!cellQueue.isEmpty()){
            Cell c = cellQueue.poll();
            c.setTraversed(true);
            queueSet.remove(c);
            for(Cell neighbors : getUntraversedReachableNeighborsGridCopy(c)){
                boolean isAnEndpoint = false;
                for(Cell ep : this.getGridCopyEndpoints()){
                    if (neighbors.equals(ep)) {
                        isAnEndpoint = true;
                        break;
                    }
                }
                if(!isAnEndpoint){
                    if(!queueSet.contains(neighbors)){
                        cellQueue.add(neighbors);
                        queueSet.add(neighbors);
                        cellToIndexMap.put(neighbors, cellToIndexMap.get(c) + 1);
                    }
                }
            }
        }
        for(Cell cell : cellToIndexMap.keySet()){
            intGrid[cell.getyPos()][cell.getxPos()] = cellToIndexMap.get(cell);
        }
    }

    public ArrayList<Cell> getUntraversedReachableNeighborsGridCopy(Cell center) {
        ArrayList<Cell> adjacents = this.gridCopy.getAdjacentCells(center);
        ArrayList<Cell> untraversed = getUnTraversedCells(adjacents);
        //System.out.println("center = " + center.getxPos() + " , " + center.getyPos());
        ArrayList<Cell> retVal = new ArrayList<>();
        for (Cell cell : untraversed) {
            //System.out.println("neighbors are: " + cell.getxPos() + " , " + cell.getyPos());
            if (Grid.isTherePathBetweenCells(center, cell)) {
                retVal.add(cell);
            }
        }
        return retVal;
    }

    @Override
    public ViewUpdatePacket makeViewUpdatePacket() {
        ViewUpdatePacket updatePacket = new ViewUpdatePacket(new ArrayList<>(300));


        for (int x = Constants.minCellIndex; x <= Constants.maxCellIndex; x++) {
            for (int y = Constants.minCellIndex; y <= Constants.maxCellIndex; y++) {

                Cell cell = this.getGrid().getCell(x, y);


                TileUpdate tileUpdate = Cell.makeTileUpdateFromCell(cell, false, false);
                updatePacket.addTileUpdate(tileUpdate);
            }
        }

        // Add the current cell at the end, will override its earlier addition
        if (currentCell != null) {
            TileUpdate tileUpdate = Cell.makeTileUpdateFromCell(this.getCurrentCell(), true, false);
            updatePacket.addTileUpdate(tileUpdate);
        }

        if (targetCell != null) {
            TileUpdate tileUpdate = Cell.makeTileUpdateFromCell(this.targetCell, false, true);
            updatePacket.addTileUpdate(tileUpdate);
        }


        return updatePacket;
    }

    public int[][] getIntGrid(){
        return intGrid;
    }

    public void iterate(){
        if(this.isDone()){

        }
        else{

        }

    }

    public void finish(){

    }
}
