package CellTest;

import cell.CellRef;

import org.junit.Test;

import static org.junit.Assert.*;

public class CellRefTest {
    @Test
    public void testCellPosn() {
        // Constructors
        CellRef cellRefA1 = new CellRef("A1");
        CellRef cellRefA1Ints = new CellRef(0,0);
        CellRef cellRefB1 = new CellRef(1, 0);
        CellRef cellRefDifRow = new CellRef(2, 5);

        // equals()
        assertEquals(cellRefA1, cellRefA1Ints);

        // Getters
        assertEquals(1, cellRefB1.getColumn());
        assertEquals(0, cellRefB1.getRow());

        // compareTo()
        assertEquals(0, cellRefA1.compareTo(cellRefA1Ints));
        assertEquals(-1, cellRefA1.compareTo(cellRefB1));
        assertEquals(1, cellRefB1.compareTo(cellRefA1));
        assertEquals(-1, cellRefA1.compareTo(cellRefDifRow));
        assertEquals(1, cellRefDifRow.compareTo(cellRefA1));


        // hashCode()
        assertNotEquals(new CellRef(2, 3).hashCode(), new CellRef(3, 2).hashCode());
        assertEquals(992, cellRefB1.hashCode());

        // toString()
        assertEquals("column: A, row: 1", cellRefA1.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCellPosnBadConstructionA() {
        CellRef cellRef = new CellRef(-1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCellPosnBadConstructionB() {
        CellRef cellRef = new CellRef(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCellPosnBadConstructionC() {
        CellRef cellRef = new CellRef(0, -1);
    }
}
