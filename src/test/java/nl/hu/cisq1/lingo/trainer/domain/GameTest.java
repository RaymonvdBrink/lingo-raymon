package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    @DisplayName("nieuwe ronde aanmaken")
    void testNewRound(){
        Game game = new Game(0, 0, null, 0, GameStatus.WAITING_FOR_ROUND, null);

        game.startNewRound("BAARD");

        List<Feedback> feedbackExpect = new ArrayList<>();
        feedbackExpect.add(new Feedback("B....", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
        Round roundExpect = new Round(0, feedbackExpect,"BAARD");
        Game gameExpect = new Game(0, 0, roundExpect, 0, GameStatus.PLAYING, null);

        assertEquals(gameExpect, game);
    }

    @Test
    @DisplayName("het progress van het spel laten zien")
    void testShowProgress(){
        List<Feedback> feedbackExpect = new ArrayList<>();
        feedbackExpect.add(new Feedback("B....", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
        Round roundExpect = new Round(0, feedbackExpect,"BAARD");

        Game game = new Game(0, 0, roundExpect, 0, GameStatus.PLAYING, null);

        assertEquals(new Progress(0, feedbackExpect, 0), game.showProgress());
    }

    @Test
    @DisplayName("test de doorgeef guess functie")
    void testGuess(){
        List<Feedback> feedback = new ArrayList<>();
        feedback.add(new Feedback("B....", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
        Round round = new Round(0, feedback,"BAARD");

        Game game = new Game(0, 0, round, 0, GameStatus.PLAYING, null);

        game.guess("BONJE");

        List<Feedback> feedbackExpect = new ArrayList<>();
        feedbackExpect.add(new Feedback("B....", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
        feedbackExpect.add(new Feedback("BONJE", List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)));
        Round roundExpect = new Round(1, feedbackExpect,"BAARD");
        Game gameExpect = new Game(0, 0, roundExpect, 0, GameStatus.PLAYING, null);

        assertEquals(gameExpect,game);
    }
}