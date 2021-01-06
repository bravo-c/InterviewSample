package cell;

import java.util.Objects;

/**
 * Represents the immutable (column, row) position of a cell in a spreadsheet.
 * An example CellRef is: "A1" -> (col: 0, row: 0)
 * We expect the columns to start from "A" and proceed through "Z"
 * and then "a" through "z" and beyond via ASCII chars.
 */
public class CellRef implements Comparable<CellRef> {
    private final int column; // NOTE: these are indexed from ASCII 'A' onward, i.e. 'A' == 0
    private final int row; // NOTE: these are zero-indexed onward, i.e. '1' == 0

    public CellRef(String colRow) {
        if (colRow.length() < 2) {
            throw new IllegalArgumentException("invalid CellRef construction from: \'" + colRow + "\"");
        }
        this.column = colRow.charAt(0) - ((int) 'A');
        this.row = Integer.parseInt(colRow.substring(1)) - 1;
        if (this.row < 0) {
            throw new IllegalArgumentException("invalid CellRef row (negative index) from colRow: " + colRow);
        }
    }

    public CellRef(int column, int row) {
        if (column < 0 || row < 0) {
            throw new IllegalArgumentException("CellRef: invalid row/column; negatives not allowed.");
        }
        this.column = column;
        this.row = row;
    }

    /**
     * @return the column as a primitive integer, no worries about immutability here
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * @return the row as a primitive integer, no worries about immutability here
     */
    public int getRow() {
        return this.row;
    }

    @Override
    public int compareTo(CellRef o) {
        if (this.row == o.row) {
            return Integer.compare(this.column, o.column);
        } else if (this.row < o.row) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CellRef)) return false;
        CellRef other = (CellRef) o;
        return this.column == other.column && this.row == other.row;
    }

    // referenced: https://stackoverflow.com/questions/11742593/what-is-the-hashcode-for-a-custom-class-having-just-two-int-properties
    @Override
    public int hashCode() {
        return Objects.hash(this.column, this.row);
    }

    @Override
    public String toString() {
        return "column: " + toASCII(this.getColumn()) + ", row: " + (this.getRow() + 1);
    }

    /**
     * A convenient way of changing an int back to the ASCII char it was originally.
     * @param col   the integer value corresponding to the column name
     * @return      the column name as a char
     */
    private char toASCII(int col) {
        return (char) (col + ((int) 'A'));
    }
}
