package cell;

import java.util.Map;

/**
 * PostOpCell represents a spreadsheet cell of the form:
 *
 * <li>
 *      <uL> A1 2 + </uL>
 *      <uL> 2 3 + </uL>
 *      <uL> 2 A1 * </uL>
 *      <uL> B1 A2 / </uL>
 * </li>
 *
 * in which the cell contains a 3-symbol expression with an operator as the third
 * symbol.
 *
 * It also contains the logic necessary to substitute and solve its expression
 * internally.
 */
public class PostOpCell extends ACell {

    private String[] syms;

    PostOpCell(String[] syms, CellRef cellRef) {
        super(cellRef);
        this.syms = syms;
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
        try {
            double fst = parseOrLookup(this.getSyms()[0], solved);
            double snd = parseOrLookup(this.getSyms()[1], solved);
            ICell solvedCell;
            // I considered making this an enum as it seems to lend itself to being one
            // and ended up not doing so as it was going to create more problems than it
            // would have solved at the time.
            switch (this.getSyms()[2]) {
                case "+":
                    solvedCell = new SolvedCell(fst + snd, this.getLocation());
                    solved.put(this.getLocation(), solvedCell);
                    break;
                case "-":
                    solvedCell = new SolvedCell(fst - snd, this.getLocation());
                    solved.put(this.getLocation(), solvedCell);
                    break;
                case "*":
                    solvedCell = new SolvedCell(fst * snd, this.getLocation());
                    solved.put(this.getLocation(), solvedCell);
                    break;
                case "/":
                    solvedCell = new SolvedCell(fst / snd, this.getLocation());
                    solved.put(this.getLocation(), solvedCell);
                    break;
                default:
                    System.err.println("Invalid operator.");
                    throw new IllegalArgumentException("Invalid operator.");
            }
        } catch (FailedParseException e) {
            System.err.println("Can't solve this expression yet!");
        }
    }

    /**
     * parseOrLookup seeks to replace a given symbol with a number. If it is
     * unable to find the number either via parsing or replacement it throws an
     * error.
     * @param sym the symbol in question
     * @param solved the worklist to replace references from
     * @return the number for the given symbol
     * @throws RuntimeException when the symbol is neither parsable nor lookup-able
     */
    private double parseOrLookup(String sym, Map<CellRef, ICell> solved) throws FailedParseException {
        try {
            return Double.parseDouble(sym);
        } catch (NumberFormatException e) {
            CellRef toReplace = new CellRef(sym);
            if (solved.containsKey(toReplace)) {
                return Double.parseDouble(solved.get(toReplace).getSyms()[0]);
            } else {
                throw new FailedParseException("Couldn't parse or lookup the given expression.");
            }
        }
    }

    @Override
    public String toString() {
        return String.join(" ", this.syms);
    }

    private static class FailedParseException extends Exception {
        FailedParseException(String message) {
            super(message);
        }
    }
}
