import java.util.*;

class Sudoku {
    // just in case you need this method for testing
    public static void main(String[] args) {
         // initial instantiation of Sudoku puzzle
         char[][] puzzle = SudokuP.puzzle();

         // attempt to solve puzzle and print it to
         // terminal
         solve(puzzle);
    }

    // print out one solution of the given puzzle
    // accepted parameter(s): 2D char array representing a sudoku board
    public static void solve(char[][] puzzle) {
        // if puzzle is empty or invalid, print error message and stop execution
        if (puzzle == null || puzzle.length == 0 || !check(puzzle)) {
          System.out.println("Something went wrong, could not attempt to solve puzzle.");
          System.exit(1);
        }

        // otherwise, print it out with formatting, or say that it is not solvable
        else if (solveSudoku(puzzle)) {
          System.out.println("One possible solution for the generated Sudoku puzzle:\n");
          System.out.println(Arrays.deepToString(puzzle).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
        } else {
          System.out.println("This puzzle is not solvable.");
        }
    }

    // solve a given sudoku puzzle board
    // additionally, return true if solvable; otherwise return false
    // accepted parameter(s): a 2D char array representing a sudoku board
    // return type: boolean
    // NOTE: you can assume that only valid sudoku board will be given as parameters to this method
    public static boolean solveSudoku(char[][] puzzle) {
      // dynamically updating row and col indices
      // and variable to keep track of emptiness
      boolean isSolved = true;
      int curRow = 0;
      int curCol = 0;

      // search for values with '.' as character, indicating emptiness
      for (int r = 0; r < puzzle.length; r++) {
        for (int c = 0; c < puzzle[r].length; c++) {
          if (puzzle[r][c] == '.') {
            // obtain index with missing value, say that
            // puzzle is not yet full, and stop
            isSolved = false;
            curRow = r;
            curCol = c;

            break;
          }
        }

        // break outer loop as well if has missing values
        if (!isSolved) break;
      }

      // proceed with code if puzzle has not been solved
      if (!isSolved) {
        // loop through values 1 through 9, repeatedly checking
        // whether or not they are valid for the given space.
        for (int val = 1; val <= 9; val++) {
          if (isSpotValid(puzzle, curRow, curCol, Character.forDigit(val, 10))) {
            // add value to sudoku board
            puzzle[curRow][curCol] = Character.forDigit(val, 10);

            // perform recursive call on method in order to replace
            // all empty spaces in puzzle; return true if solved
            if (solveSudoku(puzzle)) return true;
          }
        }
        // return true if puzzle has been solved; will
        // act as return value of condition for return
        // value inside of next for loop
      } else return true;

      // if method never returns true, it means puzzle is unsolvable,
      // so return false
      return false;
    }

    // check if a given sudoku puzzle board is valid or not
    // return true if valid; otherwise return false
    // accepted parameter(s): a 2D char array representing a sudoku board
    // return type: boolean
    public static boolean check(char[][] puzzle) {
      // check to make sure puzzle or any part of it is not empty
      if (puzzle.length == 0 || puzzle[0].length == 0) return false;

      // check all rows of puzzle
      for (int i = 0; i < puzzle.length; i++) {
        // call isParticallyValid to validate each row of puzzle, store result,
        // and return if false
        boolean result = isParticallyValid(puzzle, 0, i, puzzle[i].length, i);
        if (!result) return false;
      }

      // check all columns of puzzle
      for (int i = 0; i < puzzle.length; i++) {
        // call isParticallyValid to validate each column of puzzle, store result,
        // and return if false
        boolean result = isParticallyValid(puzzle, i, 0, i, puzzle[i].length);
        if (!result) return false;
      }

      // check all 3*3 boxes of puzzle
      for (int i = 0; i <= puzzle.length - 3; i = i + 3) {
        for (int j = 0; j <= puzzle[i].length - 3; j = j + 3) {
          // call isParticallyValid to validate each 3*3 box of puzzle, store result,
          // and return if false
          boolean result = isParticallyValid(puzzle, i, j, i + 3, j + 3);
          if (!result) return false;
        }
      }


      // if all conditions pass, return true
      return true;
    }

    // check if the specified area of the given sudoku board is valid
    // valid - following the 3 rules of sudoku
    // accepted parameters: puzzle - standing for a sudoku board in the representation of a 2D char array
    //                      four integers x1, y1, x2, y2
    //                      (x1, y1) stands for the top left corner of the area (inclusive)
    //                       x1 - row index; y1 - column index
    //                      (x2, y2) stands for the bottom right corner of the area (inclusive)
    //                       x2 - row index; y2 - column index
    // return data type: boolean
    // if the specified area is valid, return true; otherwise false
    // e.g.1, isParticallyValid(puzzle,0,0,0,8) is used to check the 1st row of puzzle
    // e.g.2, isParticallyValid(puzzle,0,0,8,0) is used to check the 1st column of puzzle
    // e.g.3, isParticallyValid(puzzle,0,0,2,2) is used to check the top left 3*3 area
    // NOTE that this method will only be applied to every row, every column, and every 3*3 small areas (9 small areas in total)
    public static boolean isParticallyValid(char[][] puzzle, int x1, int y1, int x2, int y2) {
      // check to make sure puzzle or any part of it is not empty
      if (puzzle.length == 0 || puzzle[0].length == 0) return false;

      // various checks to make sure that x1, y1, x2, and y2 are
      // all valid numbers
      if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) return false;
      if (x1 > puzzle.length || y1 > puzzle.length || x2 > puzzle.length || y2 > puzzle.length) return false;
      if (y1 != y2 && x1 != x2 && x2 != (x1 + 3) && y2 != (y1 + 3)) return false;


      // create new arraylists to store numbers found for each of the three rules
      ArrayList<Character> rowVals = new ArrayList<Character>();
      ArrayList<Character> colVals = new ArrayList<Character>();
      ArrayList<Character> boxVals = new ArrayList<Character>();

      // determine whether or not x1, y1, x2, and y2 are describing a row, column, or box

      // if the y values are the same, we are dealing with a row
      if (y1 == y2) {
        for (int j = x1; j < x2; j++) {
          if (puzzle[y1][j] == '.') continue;

          // check to see if number is already in current row, if yes return false
          if (rowVals.size() != 0) {
            if (rowVals.contains(puzzle[y1][j])) return false;
          }

          // otherwise, add value to rowVals to check next iteration
          rowVals.add(puzzle[y1][j]);
        }
      }

      // if the x values are the same, we are dealing with a column
      else if (x1 == x2) {
        for (int j = y1; j < y2; j++) {
          if (puzzle[x1][j] == '.') continue;

          // check to see if number is already in current column, if yes return false
          if (colVals.size() != 0) {
            if (colVals.contains(puzzle[x1][j])) return false;
          }

          // otherwise, add value to colVals to check next iteration
          colVals.add(puzzle[x1][j]);
        }
      }

      // if the x and y values both have a difference of 3,
      // we are dealing with a box
      else if (x2 == (x1 + 3) && y2 == (y1 + 3)) {
        for (int r = x1; r < x2; r++) {
          for (int c = y1; c < y2; c++) {
            if (puzzle[r][c] == '.') continue;

            // check to see if number is already in current box, if yes return false
            if (boxVals.size() != 0) {
              if (boxVals.contains(puzzle[r][c])) return false;
            }

            // otherwise, add value to boxVals to check next iteration
            boxVals.add(puzzle[r][c]);
          }
        }
      }

      // if all conditions pass, return true
      return true;
    }


    // check whether putting a digit c at the position (x, y) in a given sudoku board
    // will make the board invalid
    // accepted parameters: puzzle - standing for a sudoku board in the representation of a 2D char array
    //                      two integers x, y
    //                      x - row index; y - column index
    //                      c - a digit in the form of char to put at (x, y)
    // return data type: boolean
    // if putting c in puzzle is a valid move, return true; otherwise false
    public static boolean isSpotValid(char[][] puzzle, int row, int col, char c) {
        // if puzzle is not valid to begin with, return false
        if (!check(puzzle)) return false;

        // various checks to make sure that row and col are
        // both valid numbers
        if (row < 0 || col < 0) return false;
        if (row > puzzle.length || col > puzzle.length) return false;

        // check row and column for breaking of rules 1 and 2
        for (int i = 0; i < puzzle.length; i++) {
          if (puzzle[row][i] == c || puzzle[i][col] == c) {
            return false;
          }
        }

        // obtain starting index of box based on current coordinate
        // (3 is used because it is the length and width of one box)
        // e.g. if row or col is 5, 5 % 3 is 2, and 5 - 2 is 4, which
        // is the start of the box on either the x or y axis, counting
        // from 0
        int boxStartX = row - (row % 3);
        int boxStartY = col - (col % 3);

        // check box for breaking of rule 3
        // (boundaries are set to 3 squares on each axis)
        for (int i = boxStartX; i < boxStartX + 3; i++) {
          for (int j = boxStartY; j < boxStartY + 3; j++) {
            if (puzzle[i][j] == c) return false;
          }
        }

        // if all conditions pass, return true
        return true;
    }
}
