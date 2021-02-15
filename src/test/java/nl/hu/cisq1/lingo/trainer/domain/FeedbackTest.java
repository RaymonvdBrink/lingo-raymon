package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    @Test
    @DisplayName("word is guessed if all letters are correct")
    void wordIsGuessed(){
        //given
        //when
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));
        //then
        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is not guessed if not all letters are correct")
    void wordIsNotGuessed(){
        //given
        //when
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT, Mark.ABSENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));
        //then
        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is invalid if all letters are invalid")
    void guessIsInvalid(){
        //given
        //when
        Feedback feedback = new Feedback("woord", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID));
        //then
        assertTrue(feedback.isWordInvalid());
    }

    @Test
    @DisplayName("word is not invalid if not all letters are invalid")
    void guessIsNotInvalid(){
        //given
        //when
        Feedback feedback = new Feedback("woord", List.of(Mark.INVALID, Mark.ABSENT, Mark.INVALID, Mark.INVALID, Mark.INVALID));
        //then
        assertFalse(feedback.isWordInvalid());
    }
}