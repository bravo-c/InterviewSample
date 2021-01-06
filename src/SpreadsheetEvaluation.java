import cell.CellFactory;
import cell.CellRef;
import cell.ICell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Overall Description:
 * The class for our solution (for now.)
 *
 * Command-line Arguments:
 * args[0] will be <input-file> in the form "1-in.txt"
 * args[1] will be <output-file> in the form "1-out.txt"
 */
public class SpreadsheetEvaluation {
    public static void main(String[] args) {
        int EXPECTED_ARGS = 2;
        if (incorrectArgLength(args, EXPECTED_ARGS)) return;
        try {
            Map<CellRef, ICell> unsolvedSpreadsheet = parseInputSpreadsheet(args[0]);
            Map<CellRef, ICell> solvedSpreadsheet = solveSpreadsheet(unsolvedSpreadsheet);
            writeOutputToFile(args[1], solvedSpreadsheet);
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        }
    }

    /**
     * Confirms the number of command-line arguments is what we expect.
     */
    private static boolean incorrectArgLength(String[] args, int expectedArgs) {
        if (args.length != expectedArgs) {
            System.err.println("Did not provide the correct number of arguments.");
            return true;
        }
        return false;
    }

    /**
     * Parses the input file into a set of ICells which maintain their expressions and positions internally.
     * @param inputFileName             the file to be parsed
     * @throws FileNotFoundException    when the file name is bogus or can't be opened properly
     */
    private static Map<CellRef, ICell> parseInputSpreadsheet(String inputFileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputFileName));
        Map<CellRef, ICell> spreadsheet = new TreeMap<>();
        int currRowNum = 0;
        while (scanner.hasNext()) {
            int currColNum = 0;
            String currLine = scanner.nextLine();
            String[] cells = currLine.split(",");
            for (String cell : cells) {
                CellRef currLoc = new CellRef(currColNum, currRowNum);
                spreadsheet.put(currLoc, CellFactory.build(cell, currLoc));
                currColNum++;
            }
            currRowNum++;
        }
        scanner.close();
        return spreadsheet;
    }

    /**
     * Solves the cells, resolving dependencies until there are no remaining unsolved cells or we error out.
     * @param unsolvedSpreadsheet   the original problem space
     * @return                      the solved spreadsheet with only atomic values
     */
    private static Map<CellRef, ICell> solveSpreadsheet(Map<CellRef, ICell> unsolvedSpreadsheet) {
        Map<CellRef, ICell> solvedSpreadsheet = new TreeMap<>();
        int prevSolved = 0;
        while (unsolvedSpreadsheet.size() != solvedSpreadsheet.size()) {
            Map<CellRef, ICell> solvedOnes = unsolvedSpreadsheet.entrySet().stream()
                    .filter(x -> x.getValue().solved())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            solvedSpreadsheet.putAll(solvedOnes);
            for (Map.Entry<CellRef, ICell> entry : unsolvedSpreadsheet.entrySet()) {
                CellRef currLoc = entry.getKey();
                ICell currCell = entry.getValue();
                if (currCell.solved()) {
                    solvedSpreadsheet.put(currLoc, currCell);
                } else {
                    currCell.solve(unsolvedSpreadsheet, solvedSpreadsheet);
                }
            }
            if (!(solvedSpreadsheet.size() > prevSolved)) {
                System.err.println(unsolvedSpreadsheet);
                System.err.println(solvedSpreadsheet);
                System.err.println("The solution did not converge.");
                throw new RuntimeException("The solution did not converge");
            }
            prevSolved = solvedSpreadsheet.size();
        }
        return solvedSpreadsheet;
    }


    /**
     * Writes the solution to the output file, if the solution exists.
     * NOTE: referenced https://www.geeksforgeeks.org/redirecting-system-out-println-output-to-a-file-in-java/
     * @param outputFileName            the file to write to
     * @param spreadsheet               the spreadsheet to write
     * @throws FileNotFoundException    when the file opening fails
     */
    private static void writeOutputToFile(String outputFileName, Map<CellRef, ICell> spreadsheet) throws FileNotFoundException {
        PrintStream o = new PrintStream(new File(outputFileName));
        PrintStream console = System.out;
        System.setOut(o);
        if (!isSolved(spreadsheet)) {
            System.setOut(console);
            return;
        } else {
            int maxRow = -1;
            int maxCol = -1;
            for (CellRef c : spreadsheet.keySet()) {
                if (c.getColumn() > maxCol) maxCol = c.getColumn();
                if (c.getRow() > maxRow) maxRow = c.getRow();
            }
            for (int row = 0; row <= maxRow; row++) {
                int finalRow = row;
                Stream<ICell> cellStream = spreadsheet
                        .values().stream().filter(e -> e.getLocation().getRow() == finalRow);
                List<ICell> cells = cellStream.collect(Collectors.toList());
                for (int col = 0; col < cells.size(); col++) {
                    System.out.print(cells.get(col));
                    if (col != cells.size() - 1) {
                        System.out.print(",");
                    }
                }
                if (row != maxRow) {
                    System.out.println();
                }
            }
        }
        System.setOut(console);
    }

    /**
     * Determines if the consumed spreadsheet is solved.
     * @param spreadsheet   the spreadsheet being queried
     * @return              true if the spreadsheet is solved, else false
     */
    private static boolean isSolved(Map<CellRef, ICell> spreadsheet) {
        for (ICell cell : spreadsheet.values()) {
            if (!cell.solved()) {
                return false;
            }
        }
        return true;
    }

}