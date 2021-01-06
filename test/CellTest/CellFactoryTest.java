package CellTest;

import cell.CellFactory;
import cell.CellRef;
import cell.EmptyCell;
import cell.ICell;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CellFactoryTest {
    @Test
    public void testBuild() {
        // standard solved cell from 1-in.txt
        ICell cellA1 = CellFactory.build("1", 0, 0);

        // standard intermediate cells from 1-in.txt
        ICell cellA2 = CellFactory.build("2 A1 *", 0, 1);
        ICell cellB1 = CellFactory.build("2 3 +", 1, 0);
        ICell cellB2 = CellFactory.build("B1 A2 /", 1, 1);

        // intermediate cell with no operator
        ICell unsolvedCell = CellFactory.build("A1", 5, 5);

        // empty cells
        ICell emptyCell = CellFactory.build("", 7, 7);
        ICell emptyCell2 = CellFactory.build("      ", 7, 7);
        ICell emptyCell3 = CellFactory.build("", 9, 9);
        assertEquals(emptyCell, emptyCell2);
        assertNotEquals(emptyCell, emptyCell3);

        // other build method
        ICell otherBuildMethod = CellFactory.build(" ", new CellRef(100, 100));
        assertEquals(new EmptyCell(new CellRef(100, 100)), otherBuildMethod);
    }
}
