package model.solvers;

import controller.TileUpdate;
import controller.ViewUpdatePacket;
import model.Cell;
import model.Grid;
import model.RealityCell;
import model.VirtualCell;
import utilities.Constants;

import java.util.*;

public class HeuristicDepthFirstSearchSolver extends Solver {

    private final Comparator<Cell> hueristicComparator = new Comparator<Cell>() {
        @Override
        public int compare(Cell o1, Cell o2) {
            return Integer.compare(heuristic(o1), heuristic(o2));
        }
    };
    // The child is the key and the parent is the value
    private final HashMap<VirtualCell, VirtualCell> parentCells = new HashMap<>(Constants.mazeLength * Constants.mazeLength);
    private final Stack<VirtualCell> stack = new Stack<>();
    private boolean startStepDone = false;
    private VirtualCell currentVirtualCell;
    private VirtualCell targetVirtualCell;
    private RealityCell currentRealityCell;
    public HeuristicDepthFirstSearchSolver(Grid<RealityCell> grid) {
        super(grid);
    }

    public HeuristicDepthFirstSearchSolver(Grid<RealityCell> grid, RealityCell startPoint, ArrayList<RealityCell> endPoints) {
        super(grid, startPoint, endPoints);
    }

    public VirtualCell getCurrentVirtualCell() {
        return currentVirtualCell;
    }

    public RealityCell getCurrentRealityCell(){
        return currentRealityCell;
    }
    public boolean isStartStepDone() {
        return startStepDone;
    }

    public void setStartStepDone(boolean startStepDone) {
        this.startStepDone = startStepDone;
    }

    @Override
    public ViewUpdatePacket makeViewUpdatePacket() {
        ViewUpdatePacket updatePacket = new ViewUpdatePacket(new ArrayList<>(300));


        for (int x = Constants.minCellIndex; x <= Constants.maxCellIndex; x++) {
            for (int y = Constants.minCellIndex; y <= Constants.maxCellIndex; y++) {

                Cell cell = this.getRealityGrid().getCell(x, y);


                TileUpdate tileUpdate = Cell.makeTileUpdateFromCell(cell, false, false);
                updatePacket.addTileUpdate(tileUpdate);
            }
        }

        // Add the current cell at the end, will override its earlier addition
        if (this.getCurrentRealityCell() != null) {
            TileUpdate tileUpdate = Cell.makeTileUpdateFromCell(this.getCurrentRealityCell(), true, false);
            updatePacket.addTileUpdate(tileUpdate);
        }

        if (targetVirtualCell != null && this.getRealityGrid().getCell(targetVirtualCell.getxPos(), targetVirtualCell.getyPos()) != null) {
            TileUpdate tileUpdate = Cell.makeTileUpdateFromCell(this.getRealityGrid().getCell(targetVirtualCell.getxPos(),targetVirtualCell.getyPos()), false, true);
            updatePacket.addTileUpdate(tileUpdate);
        }


        return updatePacket;
    }

    //Used for testing
    public Comparator<Cell> getHueristicComparator() {
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

    public ArrayList<VirtualCell> generateOrderedStackAppendList(VirtualCell currentVirtualCell) {
        ArrayList<VirtualCell> neighbors = getUntraversedReachableNeighbors(currentVirtualCell);
        neighbors.sort(Collections.reverseOrder(hueristicComparator)); // We want the closest to be ontop of the stack
        return neighbors;
    }

    public void iterate() {
        try {
            if (this.isDone()) {
            } else if (!startStepDone) {
                this.currentVirtualCell = startPoint;
                this.currentVirtualCell.setTraversed(true);
                this.currentRealityCell = this.getRealityGrid().getCell(currentVirtualCell.getxPos(), currentVirtualCell.getyPos());
                this.updateVirtualGrid(currentRealityCell.isTopBorder(), currentRealityCell.isLeftBorder(), currentRealityCell.isBottomBorder(), currentRealityCell.isRightBorder(), currentRealityCell.getxPos(), currentRealityCell.getyPos());
                List<VirtualCell> neighbors = generateOrderedStackAppendList(currentVirtualCell);
                this.stack.addAll(neighbors);
                targetVirtualCell = stack.pop();
                this.setStartStepDone(true);
            } else if (atDestination(currentVirtualCell)) {
                this.targetVirtualCell = null;
                this.setDone(true);
            } else if (this.getVirtualGrid().isTherePathBetweenCells(currentVirtualCell, targetVirtualCell)) {
                parentCells.put(targetVirtualCell, currentVirtualCell);
                currentVirtualCell = targetVirtualCell;
                currentVirtualCell.setTraversed(true);
                this.currentRealityCell = this.getRealityGrid().getCell(currentVirtualCell.getxPos(), currentVirtualCell.getyPos());
                this.updateVirtualGrid(currentRealityCell.isTopBorder(), currentRealityCell.isLeftBorder(), currentRealityCell.isBottomBorder(), currentRealityCell.isRightBorder(), currentRealityCell.getxPos(), currentRealityCell.getyPos());
                ArrayList<VirtualCell> neighbors = generateOrderedStackAppendList(currentVirtualCell);
                stack.addAll(neighbors);
                targetVirtualCell = stack.pop();
            } else {
                currentVirtualCell = parentCells.get(currentVirtualCell);
            }
        }
        catch (Exception e){
            System.out.println("You used HeuristicDepthFirstSearchSolver and have incorrect Cell types");
        }
    }

    public void finish() {
        while (!this.isDone()) {
            this.iterate();
        }
    }

    /* Heuristic Depth-First Search Explained for Maze Solving
     * Step 1.
     * Create an empty stack. Mark starting cell as visited.
     * Push the reachable neighbors of the starting cell to the stack ordered by the heuristic. (Lowest cost on top)
     *
     * Step 2.
     * While current_cell is not one of our destination cells:
     *   1. Pop a cell from the stack. This is our target cell.
     *   2. If we can move to the target cell from the current cell:
     *       a. Assign the parentage of the target cell as current cell
     *       b. Make current cell the target cell
     *       c. Mark (now) current cell as traversed
     *       d. Push all un-traversed reachable neighbors of current cell to the stack according to the heuristic. (Lowest cost on top)
     *   3. Else (we cannot move to target cell from current cell):
     *       a. Make current cell equal to the parent of current cell
     *
     */
}
