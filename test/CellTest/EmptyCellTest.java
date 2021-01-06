package CellTest;

import cell.CellRef;
import cell.EmptyCell;
import cell.ICell;

import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmptyCellTest {
    @Test
    public void testEmptyCell() {
        ICell empty = new EmptyCell(new CellRef(0, 0));

        assertTrue(empty.solved());
        assertEquals(0, empty.getSyms().length);
        assertEquals("empty", empty.toString());
    }

    @Test
    public void testEmptyCellSolve() {
        ICell empty = new EmptyCell(new CellRef(0, 0));
        Map<CellRef, ICell> unsolved = new TreeMap<>();
        unsolved.put(new CellRef(0, 0), empty);
        Map<CellRef, ICell> solved = new TreeMap<>();
        empty.solve(unsolved, solved);
        assertEquals(unsolved, solved);
    }
}
