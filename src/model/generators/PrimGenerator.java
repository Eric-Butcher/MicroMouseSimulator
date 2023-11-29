package model.generators;

import controller.TileUpdate;
import controller.ViewUpdatePacket;
import model.RealityCell;
import utilities.Constants;

import java.util.ArrayList;

public class PrimGenerator extends Generator {

    private final ArrayList<RealityCell> frontier = new ArrayList<>(Constants.mazeLength * Constants.mazeLength);

    private boolean startStepDone = false;

    public PrimGenerator() {
        super();
    }

    public ArrayList<RealityCell> getFrontier() {
        return frontier;
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
        boolean inFrontier;

        for (int x = Constants.minCellIndex; x <= Constants.maxCellIndex; x++) {
            for (int y = Constants.minCellIndex; y <= Constants.maxCellIndex; y++) {

                RealityCell cell = this.getGrid().getCell(x, y);
                inFrontier = frontier.contains(cell);

                TileUpdate tileUpdate = RealityCell.makeTileUpdateFromCell(cell, false, inFrontier);
                updatePacket.addTileUpdate(tileUpdate);
            }
        }
        return updatePacket;
    }

    private void startStep() {
        RealityCell startCell = this.getGrid().getRandomGridCell();
        startCell.initializeCell();
        ArrayList<RealityCell> adjacentCells = this.getGrid().getAdjacentCells(startCell);
        this.getFrontier().addAll(adjacentCells);
        this.setStartStepDone(true);
    }

    public void iterate() {
        if (!this.isStartStepDone()) {
            startStep();
        } else if (this.getFrontier().isEmpty()) {
            this.setDone();
        } else {
//            1. Pop a cell from the frontier list randomly.
            RealityCell chosen = Generator.popRandomCellFromList(this.getFrontier());

//            2. Generate a list of all adjacent cells that are initialized.
            ArrayList<RealityCell> adjacentCells = this.getGrid().getAdjacentCells(chosen);
            ArrayList<RealityCell> initializedNeighbors = Generator.getInitializedCells(adjacentCells);

//          3. Pick one of these initialized cells at random.
            RealityCell initializedNeighbor = Generator.popRandomCellFromList(initializedNeighbors);

//          4. Form a path (delete the wall/s ) between the frontier cell and the initialized cell.
            this.getGrid().createPathBetweenCells(chosen, initializedNeighbor);

//          5. Set the frontier cell as initialized.
            chosen.initializeCell();

            ArrayList<RealityCell> uninitializedNeighbors = Generator.getUnInitializedCells(adjacentCells);
            for (RealityCell uninitialized : uninitializedNeighbors) {
                if (!this.getFrontier().contains(uninitialized)) {
                    this.getFrontier().add(uninitialized);
                }
            }
        }
    }

    public void finish() {
//        while ((!this.getFrontier().isEmpty()) || (!this.isStartStepDone())){
//            this.iterate();
//        }
        while (!this.getDoneGenerating()) {
            this.iterate();
        }
    }

    /*
     * Prims Algorithm Explained Step-by-Step for Maze Generation
     *
     *  Step 1.
     *  Start with a grid full of walled-off cells.
     *
     *  Step 2. (our startStep)
     *  Pick any cell on the maze at random and mark it as initialized.
     *  Add the 4 cells it is adjacent to, to the frontier list.
     *
     *  Step 3.
     *  While there are cells in the frontier list:
     *      1. Pop a cell from the frontier list randomly.
     *      2. Generate a list of all adjacent cells that are initialized.
     *      3. Pick one of these initialized cells at random.
     *      4. Form a path (delete the wall/s ) between the frontier cell and the initialized cell.
     *      5. Set the frontier cell as initialized.
     *      6. Add cells to the frontier list that are adjacent to the chosen frontier cell and not yet in frontier.
     *
     *
     *  When there are no more frontier cells the maze is complete.
     *
     */
}
