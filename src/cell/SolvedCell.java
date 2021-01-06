package cell;

import java.util.Map;

/**
 * A SolvedCell represents a spreadsheet cell which contains only a number.
 * For example:
 *      -- 1.5
 *      -- 1
 *      -- 42
 *      -- -37
 *      -- -41.435
 */
public class SolvedCell extends ACell {
    private final double val;

    SolvedCell(double val, CellRef cellRef) {
        super(cellRef);
        this.val = val;
    }

    @Override
    public boolean solved() {
        return true;
    }

    @Override
    public String[] getSyms() {
        String[] syms = new String[1];
        syms[0] = this.toString();
        return syms;
    }

    // referenced: https://stackoverflow.com/questions/703396/how-to-nicely-format-floating-numbers-to-string-without-unnecessary-decimal-0
    @Override
    public String toString() {
        if (this.val == (long) this.val) return String.format("%d", (long) this.val);
        else return String.format("%s", this.val);
    }

    @Override
    public void solve(Map<CellRef, ICell> unsolved, Map<CellRef, ICell> solved) {
        super.solve(unsolved, solved);
        solved.put(this.getLocation(), this);
    }

}
