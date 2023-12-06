//package model.solvers;
//
//import controller.TileUpdate;
//import controller.ViewUpdatePacket;
//import model.Cell;
//import model.Grid;
//import model.VirtualCell;
//import utilities.Constants;
//
//import java.util.*;
//
//
//public class FloodFillSearchSolver extends Solver{
//
//    private int[][] intGrid = new int[16][16];
//
//    private Grid<VirtualCell> grid = new Grid<>(VirtualCell.class);
//    private VirtualCell currentCell = startPoint;
//
//    public FloodFillSearchSolver(Grid<VirtualCell> grid) {
//        super(grid);
//    }
//
//
//    public FloodFillSearchSolver(Grid<VirtualCell> grid, VirtualCell startPoint, ArrayList<VirtualCell> endPoints){
//        super(grid, startPoint, endPoints);
//    }
//    public VirtualCell getCurrentCell() {
//        return currentCell;
//    }
//
//    public void fill(){
//        Queue<VirtualCell> cellQueue = new LinkedList<>();
//        HashMap<VirtualCell, Integer> cellToIndexMap = new HashMap<>();
//        HashSet<VirtualCell> queueSet = new HashSet<>();
//        for(VirtualCell endPoints: this.getEndPoints()){
//            cellQueue.add(endPoints);
//            cellToIndexMap.put(endPoints, 0);
//            queueSet.add(endPoints);
//        }
//        while(!cellQueue.isEmpty()){
//            VirtualCell c = cellQueue.poll();
//            c.setTraversed(true);
//            queueSet.remove(c);
//            for(VirtualCell neighbors : getUntraversedReachableNeighbors(c)){
//                boolean isAnEndpoint = false;
//                for(VirtualCell ep : this.getEndPoints()){
//                    if (neighbors.equals(ep)) {
//                        isAnEndpoint = true;
//                        break;
//                    }
//                }
//                if(!isAnEndpoint){
//                    if(!queueSet.contains(neighbors)){
//                        cellQueue.add(neighbors);
//                        queueSet.add(neighbors);
//                        cellToIndexMap.put(neighbors, cellToIndexMap.get(c) + 1);
//                    }
//                }
//            }
//        }
//        for(Cell cell : cellToIndexMap.keySet()){
//            intGrid[cell.getyPos()][cell.getxPos()] = cellToIndexMap.get(cell);
//        }
//    }
//
//    @Override
//    public ViewUpdatePacket makeViewUpdatePacket() {
//        ViewUpdatePacket updatePacket = new ViewUpdatePacket(new ArrayList<>(300));
//
//
//        for (int x = Constants.minCellIndex; x <= Constants.maxCellIndex; x++) {
//            for (int y = Constants.minCellIndex; y <= Constants.maxCellIndex; y++) {
//
//                VirtualCell cell = this.getGrid().getCell(x, y);
//
//
//                TileUpdate tileUpdate = VirtualCell.makeTileUpdateFromCell(cell, false, false);
//                updatePacket.addTileUpdate(tileUpdate);
//            }
//        }
//
//        // Add the current cell at the end, will override its earlier addition
//        if (currentCell != null) {
//            TileUpdate tileUpdate = VirtualCell.makeTileUpdateFromCell(this.getCurrentCell(), true, false);
//            updatePacket.addTileUpdate(tileUpdate);
//        }
//
//        return updatePacket;
//    }
//
//    public int[][] getIntGrid(){
//        return intGrid;
//    }
//
//    public void iterate(){
//        if(this.isDone()){
//
//        }
//        else{
//
//        }
//
//    }
//
//    public void finish(){
//
//    }
//}
