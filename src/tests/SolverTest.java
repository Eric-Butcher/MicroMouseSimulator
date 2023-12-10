package tests;

import model.Cell;
import model.RealityCell;
import model.VirtualCell;
import model.generators.Generator;
import model.generators.PrimGenerator;
import model.solvers.HeuristicDepthFirstSearchSolver;
import model.solvers.Solver;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {

    @Test
    public void testImplicitConstructor() {
        Generator generator = new PrimGenerator();
        Solver solver = new HeuristicDepthFirstSearchSolver(generator.getGrid());

        ArrayList<VirtualCell> actualEndpoints = solver.getEndPoints();
        VirtualCell actualStartPoint = solver.getStartPoint();

        ArrayList<VirtualCell> expectedEndpoints = new ArrayList<>();
        expectedEndpoints.add(new VirtualCell(7, 7));
        expectedEndpoints.add(new VirtualCell(8, 7));
        expectedEndpoints.add(new VirtualCell(7, 8));
        expectedEndpoints.add(new VirtualCell(8, 8));

        VirtualCell expectedStartPoint = new VirtualCell(0, 0);

        assertEquals(expectedStartPoint, actualStartPoint);
        assertEquals(expectedEndpoints, actualEndpoints);
    }

    @Test
    public void testExplicitConstructor() {
        Generator generator = new PrimGenerator();

        ArrayList<RealityCell> endpoints = new ArrayList<>();
        endpoints.add(new RealityCell(1, 2));
        endpoints.add(new RealityCell(3, 4));
        endpoints.add(new RealityCell(6, 6));
        endpoints.add(new RealityCell(15, 15));
        ArrayList<VirtualCell> expectedEndpoints = new ArrayList<>();
        expectedEndpoints.add(new VirtualCell(1, 2));
        expectedEndpoints.add(new VirtualCell(3, 4));
        expectedEndpoints.add(new VirtualCell(6, 6));
        expectedEndpoints.add(new VirtualCell(15, 15));


        RealityCell startPoint = new RealityCell(5, 10);
        VirtualCell expectedStartpoint = new VirtualCell(5, 10);

        Solver solver = new HeuristicDepthFirstSearchSolver(generator.getGrid(), startPoint, endpoints);

        assertEquals(expectedStartpoint, solver.getStartPoint());
        assertEquals(expectedEndpoints, solver.getEndPoints());

    }

    @Test
    public void testGetUnTraversedCells() {
        ArrayList<VirtualCell> input = new ArrayList<>();

        // Create cells
        VirtualCell cellA = new VirtualCell(1, 1);
        cellA.setTraversed(true);
        input.add(cellA);

        VirtualCell cellB = new VirtualCell(5, 15);
        cellB.setTraversed(false);
        input.add(cellB);

        VirtualCell cellC = new VirtualCell(3, 4);
        cellC.setTraversed(false);
        input.add(cellC);

        VirtualCell cellD = new VirtualCell(0, 12);
        cellD.setTraversed(true);
        input.add(cellD);

        VirtualCell cellE = new VirtualCell(2, 2);
        cellE.setTraversed(true);
        input.add(cellE);

        ArrayList<VirtualCell> expected = new ArrayList<>();
        expected.add(cellB);
        expected.add(cellC);

        assertEquals(expected, Solver.getUnTraversedCells(input));
    }


    @Test
    public void testGetUnTraversedReachableNeighbors() {
        PrimGenerator primGenerator = new PrimGenerator();
        Solver solver = new HeuristicDepthFirstSearchSolver(primGenerator.getGrid());

        int ax = 5;
        int ay = 5;
        VirtualCell centerA = solver.getVirtualGrid().getCell(ax, ay);
        VirtualCell topA = solver.getVirtualGrid().getCell(ax, ay - 1);
        VirtualCell rightA = solver.getVirtualGrid().getCell(ax + 1, ay);
        VirtualCell bottomA = solver.getVirtualGrid().getCell(ax, ay + 1);
        VirtualCell leftA = solver.getVirtualGrid().getCell(ax - 1, ay);
        assertEquals(4, solver.getUntraversedReachableNeighbors(centerA).size());
        ArrayList<VirtualCell> expected = new ArrayList<>();
        expected.add(topA);
        expected.add(rightA);
        expected.add(bottomA);
        expected.add(leftA);
        ArrayList<VirtualCell> actual = solver.getUntraversedReachableNeighbors(centerA);
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));


        int bx = 7;
        int by = 10;
        VirtualCell centerB = solver.getVirtualGrid().getCell(bx, by);
        VirtualCell topB = solver.getVirtualGrid().getCell(bx, by - 1);
        VirtualCell rightB = solver.getVirtualGrid().getCell(bx + 1, by);
        VirtualCell bottomB = solver.getVirtualGrid().getCell(bx, by + 1);
        VirtualCell leftB = solver.getVirtualGrid().getCell(bx - 1, by);
        centerB.addRightBorder();
        centerB.addLeftBorder();
        ArrayList<Cell> expected2 = new ArrayList<>();
        expected2.add(topB);
        expected2.add(bottomB);
        ArrayList<VirtualCell> actual2 = solver.getUntraversedReachableNeighbors(centerB);
        assertEquals(2, actual2.size());
        assertTrue(expected2.containsAll(actual2) && actual2.containsAll(expected2));

        int cx = 3;
        int cy = 2;
        VirtualCell centerC = solver.getVirtualGrid().getCell(cx, cy);
        VirtualCell topC = solver.getVirtualGrid().getCell(cx, cy - 1);
        VirtualCell rightC = solver.getVirtualGrid().getCell(cx + 1, cy);
        VirtualCell bottomC = solver.getVirtualGrid().getCell(cx, cy + 1);
        VirtualCell leftC = solver.getVirtualGrid().getCell(cx - 1, cy);
        centerC.addBottomBorder();
        centerC.addTopBorder();
        ArrayList<VirtualCell> expected3 = new ArrayList<>();
        expected3.add(rightC);
        expected3.add(leftC);
        ArrayList<VirtualCell> actual3 = solver.getUntraversedReachableNeighbors(centerC);
        assertEquals(2, actual3.size());
        assertTrue(expected3.containsAll(actual3) && actual3.containsAll(expected3));

        int dx = 14;
        int dy = 14;
        VirtualCell centerD = solver.getVirtualGrid().getCell(dx, dy);
        VirtualCell topD = solver.getVirtualGrid().getCell(dx, dy - 1);
        VirtualCell rightD = solver.getVirtualGrid().getCell(dx + 1, dy);
        VirtualCell bottomD = solver.getVirtualGrid().getCell(dx, dy + 1);
        VirtualCell leftD = solver.getVirtualGrid().getCell(dx - 1, dy);
        rightD.addLeftBorder();
        bottomD.addTopBorder();
        leftD.addRightBorder();
        ArrayList<VirtualCell> expected4 = new ArrayList<>();
        expected4.add(topD);
        ArrayList<VirtualCell> actual4 = solver.getUntraversedReachableNeighbors(centerD);
        assertEquals(1, actual4.size());
        assertTrue(expected4.containsAll(actual4) && actual4.containsAll(expected4));

        int ex = 9;
        int ey = 1;
        VirtualCell centerE = solver.getVirtualGrid().getCell(ex, ey);
        VirtualCell topE = solver.getVirtualGrid().getCell(ex, ey - 1);
        VirtualCell rightE = solver.getVirtualGrid().getCell(ex + 1, ey);
        VirtualCell bottomE = solver.getVirtualGrid().getCell(ex, ey + 1);
        VirtualCell leftE = solver.getVirtualGrid().getCell(ex - 1, ey);
        centerE.addRightBorder();
        centerE.addTopBorder();
        centerE.addLeftBorder();
        centerE.addBottomBorder();
        ArrayList<VirtualCell> expected5 = new ArrayList<>();
        ArrayList<VirtualCell> actual5 = solver.getUntraversedReachableNeighbors(centerE);
        assertEquals(0, actual5.size());
        assertTrue(expected5.containsAll(actual5) && actual5.containsAll(expected5));
    }

    @Test
    public void testAtDestination() {
        Generator generator = new PrimGenerator();
        Solver solver = new HeuristicDepthFirstSearchSolver(generator.getGrid());

        ArrayList<VirtualCell> destinations = new ArrayList<>();
        destinations.add(new VirtualCell(7, 7));
        destinations.add(new VirtualCell(7, 8));
        destinations.add(new VirtualCell(8, 7));
        destinations.add(new VirtualCell(8, 8));
        for (VirtualCell destination : destinations) {
            assertTrue(solver.atDestination(destination));
        }

        ArrayList<VirtualCell> notDestinations = new ArrayList<>();
        notDestinations.add(new VirtualCell(5, 7));
        notDestinations.add(new VirtualCell(3, 15));
        notDestinations.add(new VirtualCell(0, 0));
        notDestinations.add(new VirtualCell(15, 15));
        notDestinations.add(new VirtualCell(1, 1));
        notDestinations.add(new VirtualCell(2, 10));
        notDestinations.add(new VirtualCell(6, 6));
        notDestinations.add(new VirtualCell(7, 10));
        notDestinations.add(new VirtualCell(8, 9));
        for (VirtualCell notDestination : notDestinations) {
            assertFalse(solver.atDestination(notDestination));
        }

        Generator generatorB = new PrimGenerator();
        ArrayList<RealityCell> endPoints = new ArrayList<>();
        endPoints.add(generatorB.getGrid().getCell(4, 5));
        endPoints.add(generatorB.getGrid().getCell(15, 15));
        endPoints.add(generatorB.getGrid().getCell(0, 0));
        endPoints.add(generatorB.getGrid().getCell(11, 13));
        endPoints.add(generatorB.getGrid().getCell(7, 7));
        RealityCell startPoint = generatorB.getGrid().getCell(3, 4);
        Solver explicitSolver = new HeuristicDepthFirstSearchSolver(generatorB.getGrid(), startPoint, endPoints);

        destinations = new ArrayList<>();
//        destinations.add(generatorB.getGrid().getCell(4, 5));
//        destinations.add(generatorB.getGrid().getCell(15, 15));
//        destinations.add(generatorB.getGrid().getCell(0, 0));
//        destinations.add(generatorB.getGrid().getCell(11, 13));
//        destinations.add(generatorB.getGrid().getCell(7, 7));
        destinations.add(new VirtualCell(4,5));
        destinations.add(new VirtualCell(15,15));
        destinations.add(new VirtualCell(0,0));
        destinations.add(new VirtualCell(11,13));
        destinations.add(new VirtualCell(7,7));

        notDestinations = new ArrayList<>();
        notDestinations.add(new VirtualCell(5, 7));
        notDestinations.add(new VirtualCell(3, 15));
        notDestinations.add(new VirtualCell(1, 1));
        notDestinations.add(new VirtualCell(2, 10));
        notDestinations.add(new VirtualCell(6, 6));
        notDestinations.add(new VirtualCell(7, 10));
        notDestinations.add(new VirtualCell(8, 9));
        notDestinations.add(new VirtualCell(8, 12));
        notDestinations.add(new VirtualCell(14, 4));
        notDestinations.add(new VirtualCell(0, 3));
        notDestinations.add(new VirtualCell(2, 0));
        notDestinations.add(new VirtualCell(8, 8));
        notDestinations.add(new VirtualCell(8, 7));
        notDestinations.add(new VirtualCell(7, 8));

        for (VirtualCell destination : destinations) {
            assertTrue(explicitSolver.atDestination(destination));
        }

        for (VirtualCell notDestination : notDestinations) {
            assertFalse(explicitSolver.atDestination(notDestination));
        }

    }
}