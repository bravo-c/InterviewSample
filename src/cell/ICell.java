package cell;

import java.util.Map;

/**
 * A Cell.ICell represents a cell's current state, as well as its position.
 *
 * It contains information in one of the following forms:
 * -- the emptyCell (no internal/stored value)
 * -- a solvedCell (a single internal/stored value, solved)
 * -- an PostOpCell; containing a binary operation expression such as:
 *      -- 2 A1 *
 *      -- B1 B2 /
 *      -- 3 2 +
 * -- or a RefOnlyCell; containing only a single reference to another cell
 *      -- for instance, A1
 */
public interface ICell {
    /**
     * @return true if the expression requires no further substitutions, false otherwise
     */
    boolean solved();

    /**
     * @return the expression enclosed
     */
    String[] getSyms();

    /**
     * @return the cell's position
     */
    CellRef getLocation();

    /**
     * Attempts to solve this cell in the consumed context.
     * @param unsolved the spreadsheet's remaining unsolved cells
     * @param solved the spreadsheet's solved cells
     */
    void solve(Map<CellRef, ICell> unsolved, Map<CellRef, ICell> solved);
}
