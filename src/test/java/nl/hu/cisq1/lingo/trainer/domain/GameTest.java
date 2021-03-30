package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    @DisplayName("nieuwe ronde aanmaken")
    void testNewRound() throws Exception {
        Game game = new Game(0, 0, null, 0, GameStatus.WAITING_FOR_ROUND);

        game.startNewRound("BAARD");

        List<Feedback> feedbackExpect = new ArrayList<>();
        feedbackExpect.add(new Feedback("B....", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
        Round roundExpect = new Round(0, feedbackExpect,"BAARD");
        Game gameExpect = new Game(0, 0, roundExpect, 0, GameStatus.PLAYING);

        assertEquals(gameExpect, game);
    }

    @Test
    @DisplayName("nieuwe ronde aanmaken als gamestatus playing is")
    void testNewRoundPlaying() throws Exception {
        Game game = new Game(0, 0, null, 0, GameStatus.PLAYING);

        game.startNewRound("BAARD");

        Game gameExpect = new Game(0, 0, null, 0, GameStatus.PLAYING);

        assertEquals(gameExpect, game);
    }

    @Test
    @DisplayName("nieuwe ronde aanmaken als gamestatus eliminated is")
    void testNewRoundEliminated(){
        Game game = new Game(0, 0, null, 0, GameStatus.ELIMINATED);

        game.startNewRound("BAARD");

        Game gameExpect = new Game(0, 0, null, 0, GameStatus.ELIMINATED);

        assertEquals(gameExpect, game);
    }

    @Test
    @DisplayName("het progress van het spel laten zien")
    void testShowProgress(){
        List<Feedback> feedbackExpect = new ArrayList<>();
        feedbackExpect.add(new Feedback("B....", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
        Round roundExpect = new Round(0, feedbackExpect,"BAARD");

        Game game = new Game(0, 0, roundExpect, 0, GameStatus.PLAYING);

        assertEquals(new Progress(0,0, feedbackExpect, 0), game.showProgress());
    }

    @Test
    @DisplayName("test de doorgeef guess functie")
    void testGuess(){
        List<Feedback> feedback = new ArrayList<>();
        feedback.add(new Feedback("B....", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
        Round round = new Round(0, feedback,"BAARD");

        Game game = new Game(0, 0, round, 0, GameStatus.PLAYING);

        game.guess("BONJE");

        List<Feedback> feedbackExpect = new ArrayList<>();
        feedbackExpect.add(new Feedback("B....", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
        feedbackExpect.add(new Feedback("BONJE", List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)));
        Round roundExpect = new Round(1, feedbackExpect,"BAARD");
        Game gameExpect = new Game(0, 0, roundExpect, 0, GameStatus.PLAYING);

        assertEquals(gameExpect,game);
    }

    @Test
    @DisplayName("test score met corecte guess")
    void testGuessScore(){
        List<Feedback> feedback = new ArrayList<>();
        feedback.add(new Feedback("B....", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
        Round round = new Round(0, feedback,"BAARD");

        Game game = new Game(0, 0, round, 0, GameStatus.PLAYING);

        game.guess("BAARD");

        List<Feedback> feedbackExpect = new ArrayList<>();
        feedbackExpect.add(new Feedback("B....", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
        feedbackExpect.add(new Feedback("BAARD", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT)));
        Round roundExpect = new Round(1, feedbackExpect,"BAARD");
        Game gameExpect = new Game(0, 25, roundExpect, 0, GameStatus.WAITING_FOR_ROUND);

        assertEquals(gameExpect,game);
    }

    @Test
    @DisplayName("doorgeven van getNextWordLenght")
    void testDoorgevenGetNextWordLenght(){
        Round round = new Round(0,null, "12345");
        Game game = new Game(25, round, 0, GameStatus.WAITING_FOR_ROUND);

        //assertEquals(6, game.getNextWordLenght());
    }

    public Game getGame = new Game(0,0, new Round(), 0, GameStatus.WAITING_FOR_ROUND);

    @Test
    void getId() {
        assertEquals(0, getGame.getId());
    }

    @Test
    void getScore() {
        assertEquals(0, getGame.getScore());
    }

    @Test
    void getRound() {
        assertEquals(new Round(), getGame.getRound());
    }

    @Test
    void getRounds() {
        assertEquals(0, getGame.getRounds());
    }

    @Test
    void getGameStatus() {
        assertEquals(GameStatus.WAITING_FOR_ROUND, getGame.getGameStatus());
    }

}