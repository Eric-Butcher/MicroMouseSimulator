package tests;

import model.Grid;
import model.solvers.FloodFillSearchSolver;
import utilities.Constants;

public class FloodFillTest {

    public static void main(String[] args){
        initialGridTest();
    }

    public static void initialGridTest(){
        Grid grid = new Grid();
        FloodFillSearchSolver ff = new FloodFillSearchSolver(grid);
        ff.fill();
        int[][] ints = ff.getIntGrid();
        for(int y = 0; y < Constants.mazeLength; y++){
            System.out.print("[");
            for(int x = 0; x < Constants.mazeLength; x++){
                System.out.printf("%4d", ints[y][x]);
            }
            System.out.println("]");
        }
    }
}
