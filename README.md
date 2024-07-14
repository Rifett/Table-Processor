# Table Processor

## Overview
This project aims to create an application to process tables with formulas. 
The application can load a table from a CSV file, evaluate formulas within the table, and output the processed table in a specified format. 
Also, Object-Oriented Programming design principles are widely used to make addition of new elements easier in the future.

## Features
### Table Loading 
- The application loads a table from a CSV file.
- The column separator can be specified using the --separator [SEPARATOR] argument. If not specified, the default comma (,) is used.
- The input file should be passed using the --input-file [FILE] argument.

### Formulas evaluation
- Formulas in the table are denoted by starting with an equals sign (=).
- Formulas can contain numbers, cell references (e.g., A1, B2), and arithmetic operators (+, -, *, /).
- Proper operator precedence and support for parentheses are implemented.
- The evaluation checks for cyclical dependencies and handles errors accordingly.

### Table printing
- The application can output the processed table in CSV or Markdown format.
- The output format is specified using the --format [TYPE] argument, where [TYPE] can be csv or md.
- The CSV output can have a custom column separator specified with the --output-separator [SEPARATOR] parameter.
- The --headers option adds a header row with column names and row numbers.

## Command-Line Interface
Proper command-line interface support is to be implemented in the future as of the current state, arguments should be specified before classes are utilized (format and examples can be seen in the tests directory).

## Design Considerations
- The application is designed using Object-Oriented Programming principles.
- The code is modular and easily extensible to support additional input/output formats, more formula expressions, and other features.

## Testing
- The project includes unit tests for all implemented features
- Done via *MUnit* testing library.
