package cell;

import java.util.Map;

/**
 * A Cell.ACell represents a cell's current state, as well as its position.
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
public abstract class ACell implements ICell {
    private CellRef location;

    ACell(CellRef location) {
        this.location = location;
    }

    @Override
    public CellRef getLocation() {
        return this.location;
    }

    @Override
    public void solve(Map<CellRef, ICell> unsolved, Map<CellRef, ICell> solved) {
        if (solved.containsKey(this.getLocation())) {
            throw new IllegalArgumentException("Tried to overwrite an existing cell.");
        }
    }
}
