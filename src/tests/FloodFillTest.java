package tests;

import model.RealityGrid;
import model.generators.Generator;
import model.generators.PrimGenerator;
import model.solvers.FloodFillSearchSolver;
import org.junit.jupiter.api.Test;
import utilities.Constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FloodFillTest {


    public static void initialGridTest(){
        int filler = Constants.mazeLength;
        RealityGrid grid = new RealityGrid();
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                if(j<4){
                    grid.getRealityCell(i,j).removeBottomBorder();
                    grid.getRealityCell(i,j).removeRightBorder();
                    grid.getRealityCell(i,j).removeLeftBorder();
                    grid.getRealityCell(i,j).removeTopBorder();
                }
                if(j==4){
                    grid.getRealityCell(i,j).removeTopBorder();
                    grid.getRealityCell(i,j).removeLeftBorder();
                    grid.getRealityCell(i,j).removeRightBorder();

                }
                if(i>3){
                    grid.getRealityCell(i,j).removeBottomBorder();
                    grid.getRealityCell(i,j).removeRightBorder();
                    grid.getRealityCell(i,j).removeLeftBorder();
                    grid.getRealityCell(i,j).removeTopBorder();
                }
            }
        }
        grid.getRealityCell(0,4).removeTopBorder();
        FloodFillSearchSolver ff = new FloodFillSearchSolver(grid);
        ff.finish();
        System.out.println(ff.getCurrentVirtualCell().getxPos() + ", " + ff.getCurrentVirtualCell().getyPos());
    }

    @Test
    public void fillingAnEmptyMaze(){
        RealityGrid grid = new RealityGrid();
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                grid.getRealityCell(i,j).removeBottomBorder();
                grid.getRealityCell(i,j).removeRightBorder();
                grid.getRealityCell(i,j).removeLeftBorder();
                grid.getRealityCell(i,j).removeTopBorder();
            }
        }
        int[][] expectedAns = {
                {14, 13, 12, 11, 10, 9, 8, 7, 7, 8, 9, 10, 11, 12, 13, 14},
                {13, 12, 11, 10, 9, 8, 7, 6, 6, 7, 8, 9, 10, 11, 12, 13},
                {12, 11, 10, 9, 8, 7, 6, 5, 5, 6, 7, 8, 9, 10, 11, 12},
                {11, 10, 9, 8, 7, 6, 5, 4, 4, 5, 6, 7, 8, 9, 10, 11},
                {10, 9, 8, 7, 6, 5, 4, 3, 3, 4, 5, 6, 7, 8, 9, 10},
                {9, 8, 7, 6, 5, 4, 3, 2, 2, 3, 4, 5, 6, 7, 8, 9},
                {8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8},
                {7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7},
                {7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7},
                {8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8},
                {9, 8, 7, 6, 5, 4, 3, 2, 2, 3, 4, 5, 6, 7, 8, 9},
                {10, 9, 8, 7, 6, 5, 4, 3, 3, 4, 5, 6, 7, 8, 9, 10},
                {11, 10, 9, 8, 7, 6, 5, 4, 4, 5, 6, 7, 8, 9, 10, 11},
                {12, 11, 10, 9, 8, 7, 6, 5, 5, 6, 7, 8, 9, 10, 11, 12},
                {13, 12, 11, 10, 9, 8, 7, 6, 6, 7, 8, 9, 10, 11, 12, 13},
                {14, 13, 12, 11, 10, 9, 8, 7, 7, 8, 9, 10, 11, 12, 13, 14}

        };
        FloodFillSearchSolver solver = new FloodFillSearchSolver(grid);
        solver.fill(solver.getEndPoints());
        int[][] actualAns = solver.getIntGrid();
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                assertEquals(expectedAns[i][j], actualAns[i][j]);
            }
        }
    }

    @Test
    public void fullOfWalls(){
        RealityGrid grid = new RealityGrid();
        FloodFillSearchSolver solver = new FloodFillSearchSolver(grid);
        int[][] expectedAns = {
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 0, 0, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 0, 0, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694},
                {694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694, 694}
        };
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                solver.updateVirtualGrid(solver.getRealityGrid().getRealityCell(i,j));
            }
        }
        solver.fill(solver.getEndPoints());
        int[][] actualAns = solver.getIntGrid();
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                assertEquals(expectedAns[i][j], actualAns[i][j]);
            }
        }
    }

    @Test
    public void wall(){
        RealityGrid grid = new RealityGrid();
        FloodFillSearchSolver solver = new FloodFillSearchSolver(grid);
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                if(i > 6){
                    grid.getRealityCell(i,j).removeBottomBorder();
                    grid.getRealityCell(i,j).removeRightBorder();
                    grid.getRealityCell(i,j).removeLeftBorder();
                    grid.getRealityCell(i,j).removeTopBorder();
                }
                if(j == 0){
                    grid.getRealityCell(i,j).removeRightBorder();
                    grid.getRealityCell(i,j).removeLeftBorder();
                    grid.getRealityCell(i,j).removeTopBorder();
                }
            }
        }
        int[][] expectedAns = {
                {14, 13, 12, 11, 10, 9, 8, 7, 7, 8, 9, 10, 11, 12, 13, 14},
                {694, 694, 694, 694, 694, 694, 694, 6, 6, 7, 8, 9, 10, 11, 12, 13},
                {694, 694, 694, 694, 694, 694, 694, 5, 5, 6, 7, 8, 9, 10, 11, 12},
                {694, 694, 694, 694, 694, 694, 694, 4, 4, 5, 6, 7, 8, 9, 10, 11},
                {694, 694, 694, 694, 694, 694, 694, 3, 3, 4, 5, 6, 7, 8, 9, 10},
                {694, 694, 694, 694, 694, 694, 694, 2, 2, 3, 4, 5, 6, 7, 8, 9},
                {694, 694, 694, 694, 694, 694, 694, 1, 1, 2, 3, 4, 5, 6, 7, 8},
                {694, 694, 694, 694, 694, 694, 694, 0, 0, 1, 2, 3, 4, 5, 6, 7},
                {694, 694, 694, 694, 694, 694, 694, 0, 0, 1, 2, 3, 4, 5, 6, 7},
                {694, 694, 694, 694, 694, 694, 694, 1, 1, 2, 3, 4, 5, 6, 7, 8},
                {694, 694, 694, 694, 694, 694, 694, 2, 2, 3, 4, 5, 6, 7, 8, 9},
                {694, 694, 694, 694, 694, 694, 694, 3, 3, 4, 5, 6, 7, 8, 9, 10},
                {694, 694, 694, 694, 694, 694, 694, 4, 4, 5, 6, 7, 8, 9, 10, 11},
                {694, 694, 694, 694, 694, 694, 694, 5, 5, 6, 7, 8, 9, 10, 11, 12},
                {694, 694, 694, 694, 694, 694, 694, 6, 6, 7, 8, 9, 10, 11, 12, 13},
                {694, 694, 694, 694, 694, 694, 694, 7, 7, 8, 9, 10, 11, 12, 13, 14}

        };
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                solver.updateVirtualGrid(solver.getRealityGrid().getRealityCell(i,j));
            }
        }
        solver.fill(solver.getEndPoints());
        int[][] actualAns = solver.getIntGrid();
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                assertEquals(expectedAns[i][j], actualAns[i][j]);
            }
        }
    }

    @Test
    public void getToDestination(){
        Generator generator = new PrimGenerator();
        generator.finish();
        FloodFillSearchSolver solver = new FloodFillSearchSolver(generator.getGrid());
        solver.finish();
        assertTrue(solver.isDone());
        assertTrue(solver.getEndPoints().contains(solver.getCurrentVirtualCell()));
    }

    @Test
    public void rerun(){
        Generator generator = new PrimGenerator();
        generator.finish();
        FloodFillSearchSolver solver = new FloodFillSearchSolver(generator.getGrid());
        solver.finish();

    }
}
