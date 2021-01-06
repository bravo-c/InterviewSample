package cell;

import java.util.Map;

/**
 * A RefOnlyCell represents a cell which only contains a single reference.
 * For example:
 *      -- A1
 *      -- B2
 *      -- D5
 */
public class RefOnlyCell extends ACell {

    private final CellRef referenced;
    private String[] syms = new String[1];

    RefOnlyCell(CellRef location, CellRef referenced) {
        super(location);
        this.referenced = referenced;
        this.syms[0] = this.referenced.toString();
    }

    @Override
    public boolean solved() {
        return false;
    }

    @Override
    public String[] getSyms() {
        return this.syms.clone();
    }

    @Override
    public void solve(Map<CellRef, ICell> unsolved, Map<CellRef, ICell> solved) {
        super.solve(unsolved, solved);
        if (solved.containsKey(this.referenced)) {
            CellRef currLoc = this.getLocation();
            ICell newSolvedCell = CellFactory.build(String.join(" ", solved.get(this.referenced).getSyms()), currLoc);
            solved.put(currLoc, newSolvedCell);
        }
    }

    @Override
    public String toString() {
        return this.referenced.toString();
    }
}
