// import statements
import java.util.*;

// main class
public class Hangman {
    // testing mode condition
    private static final boolean testingMode = true;
    // random word to use for game
    private static final String randomWord = RandomWord.newWord();
    // random word as array
    private static final String[] randomWordArr = randomWord.split("");
    // guesses remaining in game
    private static int guessesRemaining;
    // spaces allowed for chosen difficulty
    private static int allowedSpaces;

    // you will take care of user interaction under main method
    public static void main(String[] args) {
        // loop for infinite repetition
        while (true) {
            // prompts the user for the difficulty level
            System.out.println("Enter your difficulty: Easy (e), Intermediate (i), Hard (h):");
            Scanner diffSc = new Scanner(System.in);
            String diff = diffSc.nextLine();

            if (validateUserInput(diff)) {
                // store difficulty value
                int diffValue = validDifficulty(diff);

                if (diffValue != 0) {
                    // set initial guesses remaining and allowed spaces per difficulty
                    if (diffValue == 1) {
                        guessesRemaining = 15;
                        allowedSpaces = 4;
                    } else if (diffValue == 2) {
                        guessesRemaining = 12;
                        allowedSpaces = 3;
                    } else if (diffValue == 3) {
                        guessesRemaining = 10;
                        allowedSpaces = 2;
                    }

                    // print out secret word for testing
                    if (testingMode) System.out.println("The secret word is: " + randomWord);

                    // replace randomWord with dashes
                    String dashedWord = dashWord(randomWord);

                    System.out.println("The word is: " + dashedWord);

                    // repeat until letter is guessed
                    while (true) {
                        // prompts the user for guessing a letter
                        System.out.println("Please enter the letter you want to guess, or 'solve' to solve:");
                        Scanner letterSc = new Scanner(System.in);
                        String letter = letterSc.nextLine().trim();

                        if (validateUserInput(letter)) {
                            // check if letter is a number
                            if (isInt(letter) || Character.isDigit(letter.charAt(0))) {
                              System.out.println("Your input is not valid, try again.");
                              continue;
                            }

                            // lets user solve word outright if they wish to
                            if (letter.equals("solve")) {
                                System.out.println("Please solve the word! ");
                                Scanner solveSc = new Scanner(System.in);
                                String solveWord = solveSc.next();

                              if (solveWord.equals(randomWord)) {
                                  System.out.println("You win!");
                                  System.out.println("You have guessed the word, congratulations!");
                                  System.out.println("Would you like to play again? Yes (y) or No (n)");

                                  Scanner replaySc1 = new Scanner(System.in);
                                  String replay1 = replaySc1.next().trim().toLowerCase();

                                  if (validateUserInput(replay1)) {
                                    // terminate game if "n", replay if "y"
                                    if (replay1.equals("y")) {
                                      break;
                                    } else {
                                      System.exit(0);
                                    }
                                  }
                              // continue if attempt to solve word is unsuccessful
                              } else {
                                  System.out.println("That is not the secret word.");

                                  // decrease number of remaining guesses
                                  guessesRemaining--;
                                  System.out.println("Guesses remaining: " + guessesRemaining);

                                  // end game if user runs out of guesses
                                  if (guessesRemaining == 0) {
                                    System.out.println("You have failed to guess the word!");
                                    System.out.println("Would you like to play again? Yes (y) or No (n)");

                                    Scanner replaySc2 = new Scanner(System.in);
                                    String replay2 = replaySc2.next().trim().toLowerCase();

                                    if (validateUserInput(replay2)) {
                                        // terminate game if "n", replay if "y"
                                        if (replay2.equals("y")) {
                                            break;
                                        } else {
                                            System.exit(0);
                                        }
                                    }
                                 }
                              }

                            // attempt to validate if word contains specified character
                            } else if (validateWordContainsChar(letter.charAt(0))) {
                                // prompt the user for positions
                                System.out.println("Please enter the spaces you want to check (separated by spaces) [4 for easy, 3 for intermediate, 2 for hard]:");
                                Scanner positionSc = new Scanner(System.in);
                                String positions = positionSc.nextLine();

                                if (validateUserInput(positions)) {
                                    // check if spaces input is valid for difficulty level
                                    if (!validPosition(positions, allowedSpaces)) {
                                        System.out.println("Your input is not valid. Try again.");
                                        System.out.println("Guesses remaining: " + guessesRemaining);

                                        continue;
                                    }

                                    // check if guess is valid at all positions or not
                                    if (validGuess(diffValue, letter, getPosition(positions, allowedSpaces), randomWordArr)) {
                                        // update value of dashedWord with correct guesses at positions
                                        dashedWord = dashToString(dashedWord, getPosition(positions, allowedSpaces), letter.charAt(0));

                                        System.out.println("Your guess is in the word!");
                                        System.out.println("The updated word is: " + dashedWord);
                                        System.out.println("Guesses remaining: " + guessesRemaining);

                                        // end game if user has guessed the word successfully
                                        if (randomWord.equals(dashedWord)) {
                                            System.out.println("You have guessed the word, congratulations!");
                                            System.out.println("Would you like to play again? Yes (y) or No (n)");

                                            Scanner replaySc3 = new Scanner(System.in);
                                            String replay3 = replaySc3.next().trim().toLowerCase();

                                            if (validateUserInput(replay3)) {
                                                // terminate game if "n", replay if "y"
                                                if (replay3.equals("y")) {
                                                break;
                                                } else {
                                                System.exit(0);
                                                }
                                            }
                                        }
                                    } else {
                                        System.out.println("Your letter was not found in the spaces you provided.");

                                        guessesRemaining--;
                                        System.out.println("Guesses remaining: " + guessesRemaining);
                                    }

                                    // end game if user runs out of guesses
                                    if (guessesRemaining == 0) {
                                        System.out.println("You have failed to guess the word!");
                                        System.out.println("Would you like to play again? Yes (y) or No (n)");

                                        Scanner replaySc4 = new Scanner(System.in);
                                        String replay4 = replaySc4.next().trim().toLowerCase();

                                        if (validateUserInput(replay4)) {
                                            // terminate game if "n", replay if "y"
                                            if (replay4.equals("y")) {
                                                break;
                                            } else {
                                                System.exit(0);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    // this method validate if a string can be parsed to an integer
    // this method takes 1 parameter and returns a boolean:
    //      str - a string
    // when str can be parsed to an integer without any needs of modification
    // return true; otherwise false
    public static boolean isInt(String str) {
        // return false if string is empty
        if (str.length() == 0) return false;

        // tries to parse int. if unsuccessful, return false; otherwise, return true.
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException n) {
            return false;
        }
    }


    // this method validates the positions given by users as a string
    // this method takes 2 parameters and returns a boolean:
    //      positionStr - a string given by a user
    //                      representing "spaces the user want to check";
    //      spaceAllowed - an integer representing the allowed spaces at the current
    //                      difficulty level
    // when the given positionStr can be split and parsed to exactly the number of
    // spaceAllowed integers, return true; otherwise false
    // NOTE: The only allowed delimiter for positionStr is white space
    // e.g.1, positionStr: "0 1 5 7"
    //        spaceAllowed: 3
    //        return: false
    // e.g.2, positionStr: "0 1 5 a "
    //        spaceAllowed: 4
    //        return: false
    // e.g.3, positionStr: "0 100 4 21 "
    //        spaceAllowed: 4
    //        return: true
    // e.g.4, positionStr: "0, 100, 4, 21 "
    //        spaceAllowed: 4
    //        return: false
    public static boolean validPosition(String positionStr, int spaceAllowed) {
        // return false if string is empty
        if (positionStr.length() == 0) return false;

        // break positions up by spaces
        String[] positionArr = positionStr.split(" ");

        // make sure all elements contain ints
        for (int i = 0; i < positionArr.length; i++) {
            try {
                Integer.parseInt(positionArr[i]);
            } catch (NumberFormatException n) {
                return false;
            }
        }

        // return true if number of positions is allowed for current difficulty, otherwise return false.
        if (positionArr.length == spaceAllowed) return true;
        else return false;
    }


    // this method converts the validated positions from a string to an int array
    // this method takes 2 parameters and returns an int array
    //      positionStr - a string representing validated positions provided by a user
    //      spaceAllowed - an integer representing the allowed spaces at the current
    //                      difficulty level
    // NOTE 1: You should assume the validity of positionStr -
    //      only composed of integers and white spaces
    //      the numbers of integers are the same as spaceAllowed
    // NOTE 2: White spaces serve as delimiters
    // e.g.1, positionStr: "0 100 4 21 "
    //        spaceAllowed: 4
    //        return: {0, 100, 4, 21}
    // e.g.2, positionStr: "0 1          3      4    "
    //        spaceAllowed: 4
    //        return: {0, 1, 3, 4}
    public static int[] getPosition(String positionStr, int spaceAllowed) {
        // break positions up by spaces
        String[] positionArr = positionStr.split(" ");

        // make integer array for positions of length spaceAllowed
        int[] positionIntArr = new int[spaceAllowed];

        // put all elements into int array
        for (int i = 0; i < positionArr.length; i++) {
            positionIntArr[i] = Integer.parseInt(positionArr[i]);
        }

        // return new int array
        return positionIntArr;
    }

    // converts the randomly generated word for the game into dashes to use for printing.
    public static String dashWord(String randomWord) {
        // string with dashes of length randomWord
        String dashedWord = "";

        // fill empty string with dashes of correct length
        for (int i = 0; i < randomWord.length(); i++) {
            dashedWord += "-";
        }

        // return the dashed word
        return dashedWord;
    }

    // checks to see if difficulty provided by user is valid or not
    public static int validDifficulty(String diff) {
        // simplify difficulty
        diff = diff.trim().toLowerCase();

        for (int i = 0; i < diff.length(); i++) {
            if (diff.charAt(i) != 'e' && diff.charAt(i) != 'i' && diff.charAt(i) != 'h') {
                System.out.println("Invalid difficulty, try again.");
                return 0;
            }
        }

        // accounts for "e", "i", and "h" inputs
        if (diff.equals("e")) return 1;
        else if (diff.equals("i")) return 2;
        else return 3;
    }

    // this method checks if the user's guess is correct or not
    public static boolean validGuess(int lvl, String guess, int[] arrNum, String[] arrWord){
        // lvl indicated the level the user chose e.g. easy = 1, intermediate = 2, hard = 3
        // arrNum is the array that contains the positions the user wants to check
        // e.g. user wants to check : 0, 1, 2, 4
        // arrNum = [0,1,2,4]
        // arrWord is the secret word converted to an array e.g. arrWord = [A,S,C,I,I]

        if (guess.length() > 1) {
          // if the user accidently types in more than one character, we would only take the first one
          guess = Character.toString(guess.charAt(0));
        }

        // accounts for easy difficulty
        if (lvl == 1) {
            for (int i = 0; i < arrNum.length; i++) {
                // accounts for negative values
                if (arrNum[i] < 0) {
                    System.out.println("Please only input positive integers.");
                    return false;
                }

                // accounts for positions exceeding length of randomWord
                if (arrNum[i] >= randomWord.length()) {
                    System.out.println("One of your guesses is greater than the amount of characters in the word, please try again.");
                    return false;
                }

                if (arrWord[arrNum[i]].equals(guess)) {
                    return true;
                }
            }
            return false;
        // accounts for intermediate difficulty
        } else if (lvl == 2) {
            for (int i = 0; i < arrNum.length; i++) {
                // accounts for negative values
                if (arrNum[i] < 0) {
                    System.out.println("Please only input positive integers.");
                    return false;
                }

                // accounts for positions exceeding length of randomWord
                if (arrNum[i] >= randomWord.length()) {
                    System.out.println("One of your guesses is greater than the amount of characters in the word, please try again.");
                    return false;
                }

                if (arrWord[arrNum[i]].equals(guess)) {
                    return true;
                }
            }
              return false;
        // accounts for hard difficulty
        } else {
            for (int i = 0; i < arrNum.length; i++) {
                // accounts for negative values
                if (arrNum[i] < 0) {
                    System.out.println("Please only input positive integers.");
                    return false;
                }

                // accounts for positions exceeding length of randomWord
                if (arrNum[i] >= randomWord.length()) {
                    System.out.println("One of your guesses is greater than the amount of characters in the word, please try again.");
                    return false;
                }

                if (arrWord[arrNum[i]].equals(guess)) {
                    return true;
                }
            }
              return false;
        }
    }

    // this method converts a dash into a valid guess made by the user.
    public static String dashToString(String dashedWord, int[] pos, char letter) {
        // convert dashed word into array for manipulation, and declare new dashed word
        String[] dashedWordArr = dashedWord.split("");
        String newDashedWord = "";

        for (int i = 0; i < pos.length; i++) {
            // make sure that each position's character is the guessed letter,
            // otherwise go onto next position value.
            if (randomWord.charAt(pos[i]) == letter) {
                dashedWordArr[pos[i]] = Character.toString(letter);
            }
        }

        // convert dashed word array back into string and return
        for (int j = 0; j < dashedWordArr.length; j++) {
            newDashedWord += dashedWordArr[j];
        }

        return newDashedWord;
    }

    // validates user input to make sure string is not empty
    public static boolean validateUserInput(String str) {
        if (str.length() == 0) {
            System.out.println("Error - There is no input.");
            return false;
        } else {
            return true;
        }
    }

    // validate char to make sure it is contained in random word string
    public static boolean validateWordContainsChar(char c) {
        // check for letter in word
        for (int i = 0; i < randomWord.length(); i++) {
          if (randomWord.charAt(i) == c) {
            return true;
          }
        }

        System.out.println("Character '" + c + "' was not found in the word! Please try again.");
        return false;
    }


    // you are welcome to add more methods if you want
}
