package tests;

import model.RealityGrid;
import model.solvers.FloodFillSearchSolver;
import utilities.Constants;

public class FloodFillTest {

    public static void main(String[] args){
        initialGridTest();
    }

    public static void initialGridTest(){
        RealityGrid grid = new RealityGrid();
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                if(i<4){
                    grid.getRealityCell(i,j).removeBottomBorder();
                    grid.getRealityCell(i,j).removeLeftBorder();
                    grid.getRealityCell(i,j).removeTopBorder();
                    grid.getRealityCell(i,j).removeRightBorder();
                }
                if(j>7){
                    grid.getRealityCell(i,j).removeBottomBorder();
                    grid.getRealityCell(i,j).removeLeftBorder();
                    grid.getRealityCell(i,j).removeTopBorder();
                    grid.getRealityCell(i,j).removeRightBorder();
                }
            }
        }
        FloodFillSearchSolver ff = new FloodFillSearchSolver(grid);
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                ff.updateVirtualGrid(grid.getRealityCell(i,j));
            }
        }
        ff.fill();
        int[][] ints = ff.getIntGrid();
        for(int y = 0; y < Constants.mazeLength; y++){
            System.out.print("[");
            for(int x = 0; x < Constants.mazeLength; x++){
                System.out.printf("%4d", ints[y][x]);
            }
            System.out.println("]");
        }
//        for(int z = 0; z < 20; z++){
//            ff.iterate();
//            System.out.println(ff.getCurrentVirtualCell().getyPos() + ", " + ff.getCurrentVirtualCell().getxPos());
//            System.out.println("intGrid value is " + ff.getIntGrid()[ff.getCurrentVirtualCell().getyPos()][ff.getCurrentVirtualCell().getxPos()]);
//            int[][] ints = ff.getIntGrid();
//            for(int y = 0; y < Constants.mazeLength; y++){
//                System.out.print("[");
//                for(int x = 0; x < Constants.mazeLength; x++){
//                    System.out.printf("%4d", ints[y][x]);
//                }
//                System.out.println("]");
//            }
//        }

    }
}
