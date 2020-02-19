/*
// this is the testing class
// DO NOT MODIFY THIS CLASS AND ITS METHODS
*/
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.*;

public class SudokuTest {
    // test isParticallyValid
    @Test public void isParticallyValidTest1() {
        char[][] puzzle = SudokuP.puzzle();
        puzzle[0][0] = '1';
        puzzle[0][3] = '1';
        String puzzleStr = RightSolution.toString(puzzle);

        int x1 = 0, y1 = 0, x2 = 0, y2 = 8;

    	assertThat("\nThis is the test on your isParticallyValid method." + 
            "\nThe given board is\n" + puzzleStr + "\n" + 
            "\nTo check the 1st row, the provided parameters are: board, 0, 0, 0, 8.\n" +
            "\nIs this row valid?", 
            Sudoku.isParticallyValid(puzzle, x1, y1, x2, y2), 
            is(RightSolution.isParticallyValid(puzzle, x1, y1, x2, y2)));    
    }

    // test isParticallyValid
    @Test public void isParticallyValidTest2() {
        char[][] puzzle = SudokuP.puzzle();
        puzzle[0][0] = '2';
        puzzle[4][0] = '2';
        String puzzleStr = RightSolution.toString(puzzle);

        int x1 = 0, y1 = 0, x2 = 8, y2 = 0;

        assertThat("\nThis is the test on your isParticallyValid method." + 
            "\nThe given board is\n" + puzzleStr + "\n" + 
            "\nTo check the 1st col, the provided parameters are: board, 0, 0, 8, 0.\n" +
            "\nIs this col valid?", 
            Sudoku.isParticallyValid(puzzle, x1, y1, x2, y2), 
            is(RightSolution.isParticallyValid(puzzle, x1, y1, x2, y2)));    
    }

    // test isParticallyValid
    @Test public void isParticallyValidTest3() {
        char[][] puzzle = SudokuP.puzzle();
        puzzle[3][3] = '2';
        puzzle[4][4] = '2';
        String puzzleStr = RightSolution.toString(puzzle);

        int x1 = 3, y1 = 3, x2 = 5, y2 = 5;

        assertThat("\nThis is the test on your isParticallyValid method." + 
            "\nThe given board is\n" + puzzleStr + "\n" + 
            "\nTo check the 5th 3*3 block, the provided parameters are: board, 3, 3, 5, 5.\n" +
            "\nIs this block valid?", 
            Sudoku.isParticallyValid(puzzle, x1, y1, x2, y2), 
            is(RightSolution.isParticallyValid(puzzle, x1, y1, x2, y2)));    
    }
    
    // test check
    @Test public void checkTest1() {
        char[][] puzzle = SudokuP.puzzle();
        puzzle[5][5] = '2';
        puzzle[4][4] = '2';
        String puzzleStr = RightSolution.toString(puzzle);

        assertThat("\nThis is the test on your check method." + 
            "\nThe given board is\n" + puzzleStr + "\n" + 
            "\nIs this board valid?", 
            Sudoku.check(puzzle), 
            is(RightSolution.check(puzzle)));    
    }

    // test check
    @Test public void checkTest2() {
        char[][] puzzle = SudokuP.puzzle();
        puzzle[0][0] = '2';
        puzzle[4][0] = '2';
        String puzzleStr = RightSolution.toString(puzzle);

        assertThat("\nThis is the test on your check method." + 
            "\nThe given board is\n" + puzzleStr + "\n" + 
            "\nIs this board valid?", 
            Sudoku.check(puzzle), 
            is(RightSolution.check(puzzle)));    
    }

    // test check
    @Test public void checkTest3() {
        char[][] puzzle = SudokuP.puzzle();
        puzzle[0][0] = '1';
        String puzzleStr = RightSolution.toString(puzzle);

        assertThat("\nThis is the test on your check method." + 
            "\nThe given board is\n" + puzzleStr + "\n" + 
            "\nIs this board valid?", 
            Sudoku.check(puzzle), 
            is(RightSolution.check(puzzle)));    
    }
    
    // test isSpotValid
    @Test public void isSpotValidTest1() {
        char[][] puzzle = SudokuP.puzzle();
        String puzzleStr = RightSolution.toString(puzzle);

        int x = 0, y = 0;
        char c = '1';

        boolean flag = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] == '.') {
                    x = i;
                    y = j;
                    flag = true;
                    break;
                }
            }
            if (flag) {break;}
        }

        assertThat("\nThis is the test on your isSpotValid method." + 
            "\nThe given board is\n" + puzzleStr + 
            "\nThe move is to put the char " + c + " at index (" + x + ", " + y + ")" +
            "\nIs this move valid?", 
            Sudoku.isSpotValid(puzzle, x, y, c), 
            is(RightSolution.isSpotValid(puzzle, x, y, c)));    
    }

    // test isSpotValid
    @Test public void isSpotValidTest2() {
        char[][] puzzle = SudokuP.puzzle();
        String puzzleStr = RightSolution.toString(puzzle);

        int x = 0, y = 0;
        char c = '1';

        boolean flag = false;
        for (int i = 8; i >= 0; i--) {
            for (int j = 8; j >= 0; j--) {
                if (puzzle[i][j] == '.') {
                    x = i;
                    y = j;
                    flag = true;
                    break;
                }
            }
            if (flag) {break;}
        }

        assertThat("\nThis is the test on your isSpotValid method." + 
            "\nThe given board is\n" + puzzleStr + 
            "\nThe move is to put the char " + c + " at index (" + x + ", " + y + ")" +
            "\nIs this move valid?", 
            Sudoku.isSpotValid(puzzle, x, y, c), 
            is(RightSolution.isSpotValid(puzzle, x, y, c)));    
    }

    @Test public void solveSudokuTest() {
        char[][] puzzle = SudokuP.puzzle();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                puzzle[i][j] = '.';
            }
        }
        for (int j = 1; j < 9; j++) {
            puzzle[0][j] = (char)(j+1 + '0');
        }
        for (int i = 1; i < 9; i++) {
            puzzle[i][0] = (char)(10-i + '0');
        }
        puzzle[0][1] = '1';
        String puzzleStr = RightSolution.toString(puzzle);

        assertThat("\nThis is the test on your solveSudoku method." + 
            "\nThe given board is:\n" + puzzleStr + 
            "This board is valid.\n" +
            "However, is it solvable?\n", 
            Sudoku.solveSudoku(puzzle), 
            is(RightSolution.solveSudoku(puzzle)));    
    }
}
