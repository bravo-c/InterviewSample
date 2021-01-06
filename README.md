# Spreadsheet Evaluation
See the given specs for more information [here](./Spreadsheet%20Evaluation.pdf).

### Running the Executable, ./spreadsheetEvaluation
```./spreadsheetEvaluation "<path-to-input.txt>" "<path-to-output.txt>"```
will result in the result being written to ```<path-to-output.txt>``` If you'd like to
compare the results, you ought to:

1. ```cat <path-to-input.txt>```
2. ```cat <path-to-output.txt>```
3. ```cat <path-to-output-expected.txt>```

You may notice that some of the inputs will not produce
outputs - this is to demonstrate the use cases where there
might be cyclic dependencies.

I have included 5 "integration tests" starting from 1 through 5.
They have corresponding out-expected.txt files as well. # InterviewSample
