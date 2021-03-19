package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

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

    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of(
                        "B....",
                        new Feedback("BERGEN", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)),
                        "B...."),
                Arguments.of(
                        "B....",
                        new Feedback("BONJE", List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)),
                        "B...."),
                Arguments.of(
                        "B....",
                        new Feedback("BARST", List.of(Mark.CORRECT, Mark.CORRECT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT)),
                        "BA..."),
                Arguments.of(
                        "BA...",
                        new Feedback("DRAAD", List.of(Mark.ABSENT, Mark.PRESENT, Mark.CORRECT, Mark.PRESENT, Mark.CORRECT)),
                        "BAA.D"),
                Arguments.of(
                        "BAA.D",
                        new Feedback("BAARD", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT)),
                        "BAARD")
        );
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("test Give Hint")
    void testGiveHint(String previousHint, Feedback feedback, String toGiveHint){
        //given
        //when
        //then
        assertEquals(toGiveHint,feedback.giveHint(previousHint));
    }
}