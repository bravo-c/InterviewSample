package cell;

/**
 * We leverage the Factory pattern here to get around the problem of differentiating
 * between which cells are solved and which cells require further substitutions.
 */
public class CellFactory {
    /**
     * The original build constructor for ICells.
     * @param syms    the expression to be enclosed within the cell
     * @param column  the column-wise location at which the cell is in the spreadsheet
     * @param row     the row-wise location at which the cell is in the spreadsheet
     * @return        the cell enclosed with expression at location (column, row)
     */
    private static ICell build(String[] syms, int column, int row) {
        if (syms.length == 0) {
            return new EmptyCell(new CellRef(column, row));
        } else if (syms.length == 1) {
            if (syms[0].trim().length() == 0) {
                return new EmptyCell(new CellRef(column, row));
            }
            try {
                return new SolvedCell(Double.parseDouble(syms[0]), new CellRef(column, row));
            } catch (NumberFormatException e) {
                return new RefOnlyCell(new CellRef(column, row), new CellRef(syms[0]));
            }
        } else if (syms.length == 3) {
            return new PostOpCell(syms, new CellRef(column, row));
        }
        throw new IllegalArgumentException("Unexpected number of symbols in expression.");
    }

    /**
     * One of two convenience constructors for ICells using this build style.
     * @param expression  the expression to be enclosed by the cell
     * @param cellRef     the location at which the cell is in the spreadsheet
     * @return            the cell enclosed with expression at location cellRef
     */
    public static ICell build(String expression, CellRef cellRef) {
        return build(expression, cellRef.getColumn(), cellRef.getRow());
    }

    /**
     * The other convenience constructor for ICells using this build style.
     * @param expression  the expression to be enclosed by the cell
     * @param column      the column-wise location at which the cell is in the spreadsheet
     * @param row         the row-wise location at which the cell is in the spreadsheet
     * @return            the cell enclosed with expression at location (column, row)
     */
    public static ICell build(String expression, int column, int row) {
        return build(expression.split(" "), column, row);
    }
}
