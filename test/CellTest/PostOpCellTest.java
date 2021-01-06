package CellTest;

import cell.CellFactory;
import cell.CellRef;
import cell.ICell;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class PostOpCellTest {
    @Test
    public void testPostOpCell() {
        ICell postOpCell = CellFactory.build("5 3 +", new CellRef(0, 0));
        assertFalse(postOpCell.solved());
        String[] values = new String[]{"5", "3", "+"};
        for (int i = 0; i < 3; i++) {
            assertEquals(values[i], postOpCell.getSyms()[i]);
        }
        assertEquals("5 3 +", postOpCell.toString());
    }

    @Test
    public void testPostOpCellSolve() {
        ICell postOpCellA = CellFactory.build("5 3 +", new CellRef(0, 0));
        ICell postOpCellS = CellFactory.build("5 3 -", new CellRef(1, 1));
        ICell postOpCellM = CellFactory.build("5 3 *", new CellRef(2, 2));
        ICell postOpCellD = CellFactory.build("5 3 /", new CellRef(3, 3));
        ICell postOpCellWithReplacement = CellFactory.build("A1 1 +", new CellRef(4, 4));

        Map<CellRef, ICell> unsolved = new TreeMap<>();
        unsolved.put(new CellRef(0, 0), postOpCellA);
        unsolved.put(new CellRef(1, 1), postOpCellS);
        unsolved.put(new CellRef(2, 2), postOpCellM);
        unsolved.put(new CellRef(3, 3), postOpCellD);
        unsolved.put(new CellRef(4, 4), postOpCellWithReplacement);

        Map<CellRef, ICell> solved = new TreeMap<>();
        postOpCellA.solve(unsolved, solved);
        postOpCellS.solve(unsolved, solved);
        postOpCellM.solve(unsolved, solved);
        postOpCellD.solve(unsolved, solved);
        postOpCellWithReplacement.solve(unsolved, solved);

        Map<CellRef, ICell> results = new TreeMap<>();

        ICell postOpCellAResult = CellFactory.build("8", new CellRef(0, 0));
        ICell postOpCellSResult = CellFactory.build("2", new CellRef(1, 1));
        ICell postOpCellMResult = CellFactory.build("15", new CellRef(2, 2));
        double d = ((double) 5) / 3;
        String s = String.valueOf(d);
        ICell postOpCellDResult = CellFactory.build(s, new CellRef(3, 3));
        ICell postOpCellWithReplacementResult = CellFactory.build("9", new CellRef(4, 4));
        results.put(new CellRef(0, 0), postOpCellAResult);
        results.put(new CellRef(1, 1), postOpCellSResult);
        results.put(new CellRef(2, 2), postOpCellMResult);
        results.put(new CellRef(3, 3), postOpCellDResult);
        results.put(new CellRef(4, 4), postOpCellWithReplacementResult);

        // if you aren't convinced... System.out.println(results.toString() + "\n" + solved.toString());
        // wasn't able to compare maps directly or by entry via this version of junit
        assertEquals(results.toString(), solved.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadOp() {
        ICell badOp = CellFactory.build("5 4 =", new CellRef(0, 0));
        Map<CellRef, ICell> unsolved = new TreeMap<>();
        unsolved.put(new CellRef(0, 0), badOp);
        Map<CellRef, ICell> solved = new TreeMap<>();
        badOp.solve(unsolved, solved);
    }

    @Test
    public void testFailedParseExceptionCaught() {
        ICell referencingSomethingRandom = CellFactory.build("A2", new CellRef(0, 0));
        Map<CellRef, ICell> unsolved = new TreeMap<>();
        unsolved.put(new CellRef(0, 0), referencingSomethingRandom);
        Map<CellRef, ICell> solved = new TreeMap<>();
        referencingSomethingRandom.solve(unsolved, solved);
    }
}
