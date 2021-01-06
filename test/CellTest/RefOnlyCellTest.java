package CellTest;

import cell.CellFactory;
import cell.CellRef;
import cell.ICell;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RefOnlyCellTest {
    @Test
    public void testRefOnlyCell() {
        ICell cellB1 = CellFactory.build("5", new CellRef(1, 0));

        ICell refOnlyCell = CellFactory.build("B1", new CellRef(0, 0));
        assertFalse(refOnlyCell.solved());
        assertEquals("column: B, row: 1", refOnlyCell.getSyms()[0]);
        assertEquals("column: B, row: 1", refOnlyCell.toString());

        Map<CellRef, ICell> unsolved = new TreeMap<>();
        Map<CellRef, ICell> solved = new TreeMap<>();
        solved.put(new CellRef(1, 0), cellB1);
        refOnlyCell.solve(unsolved, solved);

        assertEquals(2, solved.size());
        assertEquals(CellFactory.build("5", new CellRef(0,0)).toString(),
                solved.get(new CellRef(0, 0)).toString());
    }
}
