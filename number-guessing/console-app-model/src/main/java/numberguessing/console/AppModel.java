package numberguessing.console;

import numberguessing.PositiveIntegerGenerator;

public final class AppModel {

  private static final String NEW_LINE = System.lineSeparator();
  private static final String SELECT_MODE_MESSAGE = "1: Single player game" + NEW_LINE + "2: Multiplayer game"
      + NEW_LINE + "3: Exit" + NEW_LINE + "Enter selection: ";
  
  interface Processor {
    Processor run(String input);
  }

  private final PositiveIntegerGenerator generator;
  private boolean completed;
  private String output;
  private Processor processor;

  public AppModel(PositiveIntegerGenerator generator) {
    this.generator = generator;
    completed = false;
    output = SELECT_MODE_MESSAGE;
    processor = this::processModeSelection;
  }

  public boolean isCompleted() {
    return completed;
  }

  public String flushOutput() {
    return output;
  }

  public void processInput(String input) {
    processor = processor.run(input);
  }

  private Processor processModeSelection(String input) {
    if (input.equals("1")) {
      output = "Single player game" + NEW_LINE + "I'm thinking of a number between 1 and 100." + NEW_LINE
          + "Enter your guess: ";
      int answer = generator.generateLessThanOrEqualToHundread();
      return getSinglePlayerGameProcessor(answer, 1);
    } else {
      completed = true;
      return null;
    }
  }

  private Processor getSinglePlayerGameProcessor(int answer, int tries) {
    return input -> {
      int guess = Integer.parseInt(input);
      if (guess < answer) {
        output = "Your Guess is too low." + NEW_LINE + "Enter your guess: ";
        return getSinglePlayerGameProcessor(answer, tries + 1);
      } else if (guess > answer) {
        output = "Your Guess is too high." + NEW_LINE + "Enter your guess: ";
        return getSinglePlayerGameProcessor(answer, tries + 1);
      } else {
        output = "Correct! " + tries + (tries == 1 ? " guess." : " guesses.") + NEW_LINE + SELECT_MODE_MESSAGE;
        return this::processModeSelection;
      }
    };
  }
}
