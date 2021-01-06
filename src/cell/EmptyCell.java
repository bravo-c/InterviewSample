package cell;

import java.util.Map;

/**
 * Represents an empty cell in a spreadsheet at a given location.
 */
public class EmptyCell extends ACell {

    public EmptyCell(CellRef cellRef) {
        super(cellRef);
    }

    @Override
    public boolean solved() {
        return true;
    }

    @Override
    public String[] getSyms() {
        return new String[0];
    }

    @Override
    public void solve(Map<CellRef, ICell> unsolved, Map<CellRef, ICell> solved) {
        super.solve(unsolved, solved);
        solved.put(this.getLocation(), this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmptyCell)) return false;
        EmptyCell other = (EmptyCell) o;
        return this.getLocation().equals(other.getLocation());
    }

    @Override
    public String toString() {
        return "empty";
    }
}
