package model;

import utilities.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Grid<C extends Cell> {
    private C[][] cellGrid;

    public Grid(Class<C> cls) {

        @SuppressWarnings("unchecked")
        final C[][] cellGrid = (C[][])Array.newInstance(cls, 16, 16);
    }

    public C getCell(int xLoc, int yLoc){
        if (((xLoc < 0) || (xLoc > 16)) || ((yLoc < 0) || (yLoc > 16))) {
            throw new IllegalArgumentException("Gave location(s) outside of maze bounds. ");
        }
        return this.cellGrid[xLoc][yLoc];
    }

    public C[][] getCellGrid(){
        return cellGrid;
    }

    public C getRandomGridCell() {

        int from = 0;
        int to = 16;

        int x = ThreadLocalRandom.current().nextInt(from, to);
        int y = ThreadLocalRandom.current().nextInt(from, to);


        return this.getCell(x,y);
    }

    public void createPathBetweenCells(RealityCell from, RealityCell to) {

        int fromX = from.getxPos();
        int fromY = from.getyPos();
        int toX = to.getxPos();
        int toY = to.getyPos();

        if ((fromY == toY) && (fromX > toX)) {
            from.removeLeftBorder();
            to.removeRightBorder();
        } else if ((fromY == toY) && (fromX < toX)) {
            from.removeRightBorder();
            to.removeLeftBorder();
        } else if ((fromX == toX) && (fromY > toY)) {
            from.removeTopBorder();
            to.removeBottomBorder();
        } else if ((fromX == toX) && (fromY < toY)) {
            from.removeBottomBorder();
            to.removeTopBorder();
        } else {
            throw new IllegalStateException("Cells provided have malformed coordinates.");
        }

    }

    public static boolean isTherePathBetweenCells(Cell from, Cell to) throws Exception {

        if(from.isVirtual() != to.isVirtual()){
            throw new Exception("Mismatching Cell Type");
        }

        int fromX = from.getxPos();
        int fromY = from.getyPos();
        int toX = to.getxPos();
        int toY = to.getyPos();

        if (((fromY == toY) && (fromX == toX + 1)) && ((!from.isLeftBorder()) && (!to.isRightBorder()))) {
            return true;
        } else if (((fromY == toY) && (fromX + 1 == toX)) && ((!from.isRightBorder()) && (!to.isLeftBorder()))) {
            return true;
        } else if (((fromX == toX) && (fromY == toY + 1)) && ((!from.isTopBorder()) && (!to.isBottomBorder()))) {
            return true;
        } else return ((fromX == toX) && (fromY + 1 == toY)) && ((!from.isBottomBorder()) && (!to.isTopBorder()));
    }

    public ArrayList<C> getAdjacentCells(Cell centerCell) {
        int centerX = centerCell.getxPos();
        int centerY = centerCell.getyPos();

        int newXIndex;
        int newYIndex;

        ArrayList<C> adjacentCells = new ArrayList<>(4);

        for (int xBump = -1; xBump < 2; xBump++) {
            for (int yBump = -1; yBump < 2; yBump++) {

                if (Math.abs(xBump) != Math.abs(yBump)) {
                    newXIndex = centerX + xBump;
                    newYIndex = centerY + yBump;
                    if ((newXIndex >= Constants.minCellIndex) &&
                            (newXIndex <= Constants.maxCellIndex) &&
                            (newYIndex >= Constants.minCellIndex) &&
                            (newYIndex <= Constants.maxCellIndex)
                    ) {
                        C adjacent = this.getCell(newXIndex, newYIndex);
                        adjacentCells.add(adjacent);
                    }

                }
            }
        }

        return adjacentCells;
    }

    public void unSolveGrid() {
        for (int i = 0; i < Constants.mazeLength; i++) {
            for (int j = 0; j < Constants.mazeLength; j++) {
                this.cellGrid[i][j].setTraversed(false);
                this.cellGrid[i][j].setGoal(false);
            }
        }
    }

}
