package numberguessing.console;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import numberguessing.PositiveIntegerGeneratorStub;

class TestAppModel {

  private static final String NEW_LINE = System.lineSeparator();

  @Test
  void sut_is_incompleted_when_it_is_initialized() {
    var sut = new AppModel(new PositiveIntegerGeneratorStub(50));
    boolean actual = sut.isCompleted();
    assertFalse(actual);
  }

  @Test
  void sut_correctly_prints_select_mode_message() {
    var sut = new AppModel(new PositiveIntegerGeneratorStub(50));
    String actual = sut.flushOutput();
    assertThat(actual).isEqualTo("1: Single Play Game" + NEW_LINE + "2: Multiplayer game" + NEW_LINE + "3: Exit"
        + NEW_LINE + "Enter Selection: ");
  }

  @Test
  void sut_correctly_exits() {
    var sut = new AppModel(new PositiveIntegerGeneratorStub(50));
    sut.processInput("3");
    boolean actual = sut.isCompleted();
    assertTrue(actual);
  }

  @Test
  void sut_correctly_prints_single_player_game_start_message() {
    var sut = new AppModel(new PositiveIntegerGeneratorStub(50));
    sut.flushOutput();
    sut.processInput("1");
    String actual = sut.flushOutput();
    assertThat(actual).isEqualTo("Single player game" + NEW_LINE + "I'm thinking of a number between 1 and 100."
        + NEW_LINE + "Enter your guess: ");
  }

  @ParameterizedTest
  @CsvSource({ "50, 40", "30, 29", "89, 9" })
  void sut_correctly_prints_too_low_message_in_single_player_game(int answer, int guess) {
    var sut = new AppModel(new PositiveIntegerGeneratorStub(answer));
    sut.processInput("1");
    sut.flushOutput();
    sut.processInput(Integer.toString(guess));

    String actual = sut.flushOutput();
    assertThat(actual).isEqualTo("Your Guess is too low." + NEW_LINE + "Enter your guess: ");
  }

  @ParameterizedTest
  @CsvSource({ "50, 60", "80, 81" })
  void sut_correctly_prints_too_high_message_in_single_player_game(int answer, int guess) {
    var sut = new AppModel(new PositiveIntegerGeneratorStub(answer));
    sut.processInput("1");
    sut.flushOutput();
    sut.processInput(Integer.toString(guess));

    String actual = sut.flushOutput();
    assertThat(actual).isEqualTo("Your Guess is too high." + NEW_LINE + "Enter your guess: ");
  }

  @ParameterizedTest
  @ValueSource(ints = { 1, 3, 10, 100 })
  void sut_correctly_prints_correct_message_in_single_player_game(int answer) {
    var sut = new AppModel(new PositiveIntegerGeneratorStub(answer));
    sut.processInput("1");
    sut.flushOutput();
    int guess = answer;
    sut.processInput(Integer.toString(guess));

    String actual = sut.flushOutput();
    assertThat(actual).isEqualTo("Correct! ");
  }
}
