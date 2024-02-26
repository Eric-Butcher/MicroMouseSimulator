package model;

import utilities.Constants;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {
    private Cell[][] cellGrid = new Cell[Constants.mazeLength][Constants.mazeLength];
    public int grid_length =  Constants.mazeLength;
    public int grid_max_cell_index =Constants.maxCellIndex;


    public Grid() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Cell cell = new Cell(i, j);
                this.cellGrid[i][j] = cell;
            }
        }
    }

    public Grid(int width, int height) {
        this.grid_length = width;
        this.grid_max_cell_index = width - 1;
        this.cellGrid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell cell = new Cell(i, j);
                this.cellGrid[i][j] = cell;
            }
        }
    }

    public Cell[][] getCellGrid()
    {
        return this.cellGrid;
    }

    public static boolean isTherePathBetweenCells(Cell from, Cell to) {

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

    public Cell getCell(int xLoc, int yLoc) {
        if (((xLoc < Constants.minCellIndex) || (xLoc > this.grid_max_cell_index)) || ((yLoc < Constants.minCellIndex) || (yLoc > this.grid_max_cell_index))) {
            throw new IllegalArgumentException("Gave location(s) outside of maze bounds. ");
        }
        return this.cellGrid[xLoc][yLoc];
    }

    public Cell getRandomGridCell() {

        int from = 0;
        int to = 16;

        int x = ThreadLocalRandom.current().nextInt(from, to);
        int y = ThreadLocalRandom.current().nextInt(from, to);

        return this.getCell(x, y);
    }

    public void removeBorderForAllCells() {
        for (int i = 0; i < this.grid_length; i++) {
            for (int j = 0; j < this.grid_length; j++) {
                this.cellGrid[i][j].removeBottomBorder();
                this.cellGrid[i][j].removeTopBorder();
                this.cellGrid[i][j].removeRightBorder();
                this.cellGrid[i][j].removeLeftBorder();
            }
        }
    }

    public void borderToggle(int locx, int locy, String direction)
    {
        switch (direction) {
            case "top" -> this.cellGrid[locx][locy].enableTopBorder();
            case "bottom" -> this.cellGrid[locx][locy].enableBottomBorder();
            case "left" -> this.cellGrid[locx][locy].enableLeftBorder();
            case "right" -> this.cellGrid[locx][locy].enableRightBorder();
            default -> System.out.println("error");
        }
    }


        public void createPathBetweenCells(Cell from, Cell to) {

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

    public ArrayList<Cell> getAdjacentCells(Cell centerCell) {
        int centerX = centerCell.getxPos();
        int centerY = centerCell.getyPos();

        int newXIndex;
        int newYIndex;

        ArrayList<Cell> adjacentCells = new ArrayList<>(4);

        for (int xBump = -1; xBump < 2; xBump++) {
            for (int yBump = -1; yBump < 2; yBump++) {

                if (Math.abs(xBump) != Math.abs(yBump)) {
                    newXIndex = centerX + xBump;
                    newYIndex = centerY + yBump;
                    if ((newXIndex >= Constants.minCellIndex) &&
                            (newXIndex <= this.grid_max_cell_index) &&
                            (newYIndex >= Constants.minCellIndex) &&
                            (newYIndex <= this.grid_max_cell_index)
                    ) {
                        Cell adjacent = this.getCell(newXIndex, newYIndex);
                        adjacentCells.add(adjacent);
                    }

                }
            }
        }

        return adjacentCells;
    }

    public void unSolveGrid() {
        for (int i = 0; i < this.grid_length; i++) {
            for (int j = 0; j < this.grid_length; j++) {
                this.cellGrid[i][j].setTraversed(false);
                this.cellGrid[i][j].setGoal(false);
            }
        }

    }

    public void setSingularCellValue(int xLoc, int yLoc, int value) {
        this.cellGrid[xLoc][yLoc].setCellValue(value);
    }


        public void setAllCellValues(int value) {
        for (int i = 0; i <this.grid_length; i++) {
            for (int j = 0; j < this.grid_length; j++) {
                this.cellGrid[i][j].setCellValue(value);
            }
        }
    }
}
