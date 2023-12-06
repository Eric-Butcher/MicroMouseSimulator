package tests;

import model.Cell;
import model.RealityCell;
import model.VirtualCell;
import model.generators.Generator;
import model.generators.PrimGenerator;
import model.solvers.HeuristicDepthFirstSearchSolver;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HeuristicDepthFirstSearchSolverTest {

    @Test
    public void testGetsPastStartStep() {
        PrimGenerator primGenerator = new PrimGenerator();
        primGenerator.finish();
        HeuristicDepthFirstSearchSolver heuristicDepthFirstSearchSolver = new HeuristicDepthFirstSearchSolver(primGenerator.getGrid());
        heuristicDepthFirstSearchSolver.iterate();
        assertTrue(heuristicDepthFirstSearchSolver.isStartStepDone());
    }

    @Test
    public void testNoTeleportingMouse() {
        PrimGenerator primGenerator = new PrimGenerator();
        primGenerator.finish();
        HeuristicDepthFirstSearchSolver heuristicDepthFirstSearchSolver = new HeuristicDepthFirstSearchSolver(primGenerator.getGrid());
        heuristicDepthFirstSearchSolver.iterate();
        VirtualCell previousCell = heuristicDepthFirstSearchSolver.getCurrentVirtualCell();
        VirtualCell currentCell = null;
        while (!heuristicDepthFirstSearchSolver.isDone()) {
            heuristicDepthFirstSearchSolver.iterate();
            currentCell = heuristicDepthFirstSearchSolver.getCurrentVirtualCell();

            // The mouse should not teleport
            assertTrue(Math.abs(previousCell.getxPos() - currentCell.getxPos()) <= 1);
            assertTrue(Math.abs(previousCell.getyPos() - currentCell.getyPos()) <= 1);
            previousCell = currentCell;
        }
    }

    @Test
    public void testGetsToDestination() {
        Generator generator = new PrimGenerator();
        generator.finish();
        HeuristicDepthFirstSearchSolver heuristicDepthFirstSearchSolver = new HeuristicDepthFirstSearchSolver(generator.getGrid());
        heuristicDepthFirstSearchSolver.finish();
        assertTrue(heuristicDepthFirstSearchSolver.isDone());
        assertTrue(heuristicDepthFirstSearchSolver.getEndPoints().contains(heuristicDepthFirstSearchSolver.getCurrentVirtualCell()));
    }

    @Test
    public void testManhattanDistance() {
        Generator generator = new PrimGenerator();
        HeuristicDepthFirstSearchSolver solver = new HeuristicDepthFirstSearchSolver(generator.getGrid());
        Cell a1 = new VirtualCell(0, 0);
        Cell a2 = new VirtualCell(1, 1);
        assertEquals(2, solver.manhattanDistance(a1, a2));

        Cell b1 = new RealityCell(0, 0);
        Cell b2 = new RealityCell(5, 5);
        assertEquals(10, solver.manhattanDistance(b1, b2));

        Cell c1 = new RealityCell(0, 0);
        Cell c2 = new RealityCell(15, 15);
        assertEquals(30, solver.manhattanDistance(c1, c2));

        Cell d1 = new VirtualCell(4, 2);
        Cell d2 = new VirtualCell(12, 8);
        assertEquals(14, solver.manhattanDistance(d1, d2));

        Cell e1 = new RealityCell(2, 7);
        Cell e2 = new VirtualCell(3, 8);
        assertEquals(2, solver.manhattanDistance(e1, e2));

        Cell f1 = new RealityCell(4, 14);
        Cell f2 = new VirtualCell(3, 8);
        assertEquals(7, solver.manhattanDistance(f1, f2));

        Cell g1 = new VirtualCell(12, 7);
        Cell g2 = new RealityCell(9, 0);
        assertEquals(10, solver.manhattanDistance(g1, g2));
    }

    @Test
    public void testHeuristicImplicitConstructor() {
        PrimGenerator generator = new PrimGenerator();
        HeuristicDepthFirstSearchSolver heuristicDepthFirstSearchSolver = new HeuristicDepthFirstSearchSolver(generator.getGrid());
        int heuristicValue;

        Cell a = new VirtualCell(0, 0);
        heuristicValue = heuristicDepthFirstSearchSolver.heuristic(a);
        assertEquals(14, heuristicValue);

        Cell b = new VirtualCell(6, 6);
        heuristicValue = heuristicDepthFirstSearchSolver.heuristic(b);
        assertEquals(2, heuristicValue);

        Cell c = new RealityCell(7, 6);
        heuristicValue = heuristicDepthFirstSearchSolver.heuristic(c);
        assertEquals(1, heuristicValue);

        Cell d = new RealityCell(7, 7);
        heuristicValue = heuristicDepthFirstSearchSolver.heuristic(d);
        assertEquals(0, heuristicValue);

        Cell e = new VirtualCell(8, 8);
        heuristicValue = heuristicDepthFirstSearchSolver.heuristic(e);
        assertEquals(0, heuristicValue);

        Cell f = new RealityCell(9, 8);
        heuristicValue = heuristicDepthFirstSearchSolver.heuristic(f);
        assertEquals(1, heuristicValue);

        Cell g = new VirtualCell(9, 9);
        heuristicValue = heuristicDepthFirstSearchSolver.heuristic(g);
        assertEquals(2, heuristicValue);

        Cell h = new RealityCell(15, 15);
        heuristicValue = heuristicDepthFirstSearchSolver.heuristic(h);
        assertEquals(14, heuristicValue);

        Cell i = new VirtualCell(15, 0);
        heuristicValue = heuristicDepthFirstSearchSolver.heuristic(i);
        assertEquals(14, heuristicValue);

        Cell j = new RealityCell(12, 3);
        heuristicValue = heuristicDepthFirstSearchSolver.heuristic(j);
        assertEquals(8, heuristicValue);

        Cell k = new VirtualCell(3, 14);
        heuristicValue = heuristicDepthFirstSearchSolver.heuristic(k);
        assertEquals(10, heuristicValue);

        Cell l = new RealityCell(0, 15);
        heuristicValue = heuristicDepthFirstSearchSolver.heuristic(l);
        assertEquals(14, heuristicValue);
    }

    @Test
    public void testHeuristicWithExplicitConstructor() {
        PrimGenerator generator = new PrimGenerator();
        RealityCell startCell = generator.getGrid().getCell(0, 0);
        ArrayList<RealityCell> endPoints = new ArrayList<>();
        endPoints.add(generator.getGrid().getCell(15, 15));
        endPoints.add(generator.getGrid().getCell(4, 4));
        endPoints.add(generator.getGrid().getCell(2, 14));
        HeuristicDepthFirstSearchSolver heuristicDepthFirstSearchSolver = new HeuristicDepthFirstSearchSolver(generator.getGrid(), startCell, endPoints);

        Cell a = new VirtualCell(4, 1);
        assertEquals(3, heuristicDepthFirstSearchSolver.heuristic(a));

        Cell b = new RealityCell(15, 0);
        assertEquals(15, heuristicDepthFirstSearchSolver.heuristic(b));

        Cell c = new VirtualCell(6, 4);
        assertEquals(2, heuristicDepthFirstSearchSolver.heuristic(c));

        Cell d = new RealityCell(2, 6);
        assertEquals(4, heuristicDepthFirstSearchSolver.heuristic(d));

        Cell e = new VirtualCell(1, 9);
        assertEquals(6, heuristicDepthFirstSearchSolver.heuristic(e));

        Cell f = new RealityCell(8, 9);
        assertEquals(9, heuristicDepthFirstSearchSolver.heuristic(f));

        Cell g = new VirtualCell(15, 9);
        assertEquals(6, heuristicDepthFirstSearchSolver.heuristic(g));

        Cell h = new VirtualCell(5, 10);
        assertEquals(7, heuristicDepthFirstSearchSolver.heuristic(h));

        Cell i = new RealityCell(13, 12);
        assertEquals(5, heuristicDepthFirstSearchSolver.heuristic(i));

        Cell j = new RealityCell(1, 13);
        assertEquals(2, heuristicDepthFirstSearchSolver.heuristic(j));

        Cell k = new VirtualCell(8, 14);
        assertEquals(6, heuristicDepthFirstSearchSolver.heuristic(k));

        Cell l = new RealityCell(9, 14);
        assertEquals(7, heuristicDepthFirstSearchSolver.heuristic(l));

        Cell m = new VirtualCell(8, 15);
        assertEquals(7, heuristicDepthFirstSearchSolver.heuristic(m));

        Cell n = new RealityCell(9, 15);
        assertEquals(6, heuristicDepthFirstSearchSolver.heuristic(n));

        Cell o = new VirtualCell(2, 15);
        assertEquals(1, heuristicDepthFirstSearchSolver.heuristic(o));

        Cell p = new RealityCell(12, 15);
        assertEquals(3, heuristicDepthFirstSearchSolver.heuristic(p));
    }

    @Test
    public void testHeuristicComparator() {
        PrimGenerator primGenerator = new PrimGenerator();
        HeuristicDepthFirstSearchSolver heuristicDepthFirstSearchSolver = new HeuristicDepthFirstSearchSolver(primGenerator.getGrid());

        Cell biggerA = new VirtualCell(1, 1);
        Cell smallerA = new RealityCell(2, 2);
        int resultA = heuristicDepthFirstSearchSolver.getHueristicComparator().compare(smallerA, biggerA);
        assertTrue(resultA < 0);

        Cell biggerB = new RealityCell(0, 0);
        Cell smallerB = new RealityCell(6, 6);
        int resultB = heuristicDepthFirstSearchSolver.getHueristicComparator().compare(smallerB, biggerB);
        assertTrue(resultB < 0);

        Cell biggerC = new VirtualCell(4, 4);
        Cell smallerC = new VirtualCell(4, 4);
        int resultC = heuristicDepthFirstSearchSolver.getHueristicComparator().compare(smallerC, biggerC);
        assertEquals(0, resultC);
    }

    @Test
    public void testGenerateOrderedStackAppendList() {
        PrimGenerator primGenerator = new PrimGenerator();
        HeuristicDepthFirstSearchSolver heuristicDepthFirstSearchSolver = new HeuristicDepthFirstSearchSolver(primGenerator.getGrid());

        VirtualCell center = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(4, 4);
        VirtualCell right = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(5, 4);
        VirtualCell left = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(3, 4);
        VirtualCell top = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(4, 3);
        VirtualCell bottom = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(4, 5);
        center.addRightBorder();
        center.addLeftBorder();

        ArrayList<VirtualCell> actualCells = heuristicDepthFirstSearchSolver.generateOrderedStackAppendList(center);
        ArrayList<VirtualCell> expectedCells = new ArrayList<>();
        expectedCells.add(top);
        expectedCells.add(bottom);
        assertEquals(expectedCells, actualCells);

        int bx = 10;
        int by = 10;

        VirtualCell centerB = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(bx, by);
        VirtualCell rightB = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(bx + 1, by);
        VirtualCell leftB = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(bx - 1, by);
        VirtualCell topB = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(bx, by - 1);
        VirtualCell bottomB = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(bx, by + 1);
        centerB.addBottomBorder();
        centerB.addTopBorder();

        ArrayList<VirtualCell> actualCellsB = heuristicDepthFirstSearchSolver.generateOrderedStackAppendList(centerB);
        ArrayList<VirtualCell> expectedCellsB = new ArrayList<>();
        expectedCellsB.add(rightB);
        expectedCellsB.add(leftB);
        assertEquals(expectedCellsB, actualCellsB);

        int cx = 14;
        int cy = 1;

        VirtualCell centerC = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(cx, cy);
        VirtualCell rightC = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(cx + 1, cy);
        VirtualCell leftC = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(cx - 1, cy);
        VirtualCell topC = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(cx, cy - 1);
        VirtualCell bottomC = heuristicDepthFirstSearchSolver.getVirtualGrid().getCell(cx, cy + 1);
        centerC.addTopBorder();
        centerC.addBottomBorder();
        centerC.addLeftBorder();
        centerC.addRightBorder();

        ArrayList<VirtualCell> actualCellsC = heuristicDepthFirstSearchSolver.generateOrderedStackAppendList(centerC);
        ArrayList<VirtualCell> expectedCellsC = new ArrayList<>();
        assertEquals(expectedCellsC, actualCellsC);

    }

}
