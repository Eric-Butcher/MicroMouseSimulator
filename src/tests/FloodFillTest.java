package tests;

import model.RealityGrid;
import model.solvers.FloodFillSearchSolver;
import utilities.Constants;

public class FloodFillTest {

    public static void main(String[] args){
        initialGridTest();
    }

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

//        for(int i = 0; i < 16; i++){
//            for(int j = 0; j < 16; j++){
//                ff.updateVirtualGrid(ff.getRealityGrid().getRealityCell(i,j));
//            }
//        }
//        ff.fill();

//        ff.updateVirtualGrid(ff.getRealityGrid().getRealityCell(0,0));
//        ff.fill();
//        ff.updateVirtualGrid(ff.getRealityGrid().getRealityCell(0,1));
////        ff.updateVirtualGrid(ff.getRealityGrid().getRealityCell(1,0));
//        ff.fill();
//        ff.updateVirtualGrid(ff.getRealityGrid().getRealityCell(1,0));
//        ff.fill();
//        ff.updateVirtualGrid(ff.getRealityGrid().getRealityCell(0,2));
//        ff.fill();
//        ff.updateVirtualGrid(ff.getRealityGrid().getRealityCell(0,3));
//        ff.fill();
//        ff.updateVirtualGrid(ff.getRealityGrid().getRealityCell(0,4));
//        ff.fill();
//        int[][] ints = ff.getIntGrid();
//        for(int y = 0; y < Constants.mazeLength; y++){
//            System.out.print("[");
//            for(int x = 0; x < Constants.mazeLength; x++){
//                System.out.printf("%4d", ints[y][x]);
//            }
//            System.out.println("]");
//        }

        for(int i = 0; i < 6; i++){
            ff.iterate();
            System.out.println(ff.getCurrentVirtualCell().getxPos() + ", " + ff.getCurrentVirtualCell().getyPos());
            int[][] ints = ff.getIntGrid();
            for(int y = 0; y < Constants.mazeLength; y++){
                System.out.print("[");
                for(int x = 0; x < Constants.mazeLength; x++){
                    System.out.printf("%4d", ints[y][x]);
                }
                System.out.println("]");
            }
        }

//        ff.iterate();
    }
}
