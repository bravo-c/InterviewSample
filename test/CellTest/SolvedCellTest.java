package CellTest;

import cell.CellFactory;
import cell.CellRef;
import cell.ICell;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SolvedCellTest {
    @Test
    public void testSolvedCell() {
        ICell solvedCell = CellFactory.build("7", new CellRef(0, 0));
        assertTrue(solvedCell.solved());

        // note this test is unrealistic, but including it for complete coverage in intelliJ
        Map<CellRef, ICell> unsolved = new TreeMap<>();
        unsolved.put(new CellRef(0, 0), solvedCell);
        Map<CellRef, ICell> solved = new TreeMap<>();

        solvedCell.solve(unsolved, solved);
        assertEquals(unsolved.toString(), solved.toString());
    }
}
