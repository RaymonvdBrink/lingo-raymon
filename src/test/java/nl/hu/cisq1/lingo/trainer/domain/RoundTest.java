package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    static Stream<Arguments> provideGuessExamples() {
        return Stream.of(
                Arguments.of(
                        "BONJE",
                        new Round(
                            1,
                            new ArrayList<Feedback>() {{
                                add(new Feedback("BONJE", List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)));
                            }},
                            "BAARD"
                        )
                ),
                Arguments.of(
                        "BARST",
                        new Round(
                                1,
                                new ArrayList<Feedback>() {{
                                    add(new Feedback("BARST", List.of(Mark.CORRECT, Mark.CORRECT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT)));
                                }},
                                "BAARD"
                        )
                ),
                Arguments.of(
                        "DRAAD",
                        new Round(
                                1,
                                new ArrayList<Feedback>() {{
                                    add(new Feedback("DRAAD", List.of(Mark.ABSENT, Mark.PRESENT, Mark.CORRECT, Mark.PRESENT, Mark.CORRECT)));
                                }},
                                "BAARD"
                        )
                ),
                Arguments.of(
                        "BAARD",
                        new Round(
                                1,
                                new ArrayList<Feedback>() {{
                                    add(new Feedback("BAARD", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT)));
                                }},
                                "BAARD"
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideGuessExamples")
    @DisplayName("test van de functie voor het raden van het woord")
    void testGuess(String guess, Round result) {
        //given
        Round round = new Round(0, new ArrayList<>(), "BAARD");
        //when
        round.guess(guess);
        //then
        assertEquals(result, round);
    }

    @Test
    @DisplayName("test van de functie voor het raden van het woord met een guess waarvan de lengte niet overeen komt")
    void TestGuessWrongLengt(){
        //given
        Round round = new Round(0, new ArrayList<>(), "BAARD");
        //when
        round.guess("BAARD1");
        round.guess("BAAR");
        //then
        Round result = new Round(
            2,
            new ArrayList<Feedback>() {{
                add(new Feedback("BAARD1", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
                add(new Feedback("BAAR", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
            }},
            "BAARD"
        );
        assertEquals(result, round);
    }

    @Test
    void testGiveHint(){
        Round round = new Round(
                3,
                new ArrayList<Feedback>() {{
                    add(new Feedback("BARST", List.of(Mark.CORRECT, Mark.CORRECT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT)));
                    add(new Feedback("BARST", List.of(Mark.CORRECT, Mark.CORRECT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT)));
                    add(new Feedback("BARST", List.of(Mark.CORRECT, Mark.CORRECT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT)));
                }},
                "BAARD"
        );

        assertEquals("BA...", round.giveHint());
    }

    static Stream<Arguments> testDataNextWordLenght() {
        return Stream.of(
                Arguments.of(new Round(0,null, "12345"), 6),
                Arguments.of(new Round(0,null, "123456"), 7),
                Arguments.of(new Round(0,null, "1234567"), 5)
        );
    }

    @ParameterizedTest
    @MethodSource("testDataNextWordLenght")
    @DisplayName("test of getNextWordLenght de juiste leghte terug geeft")
    void testgetNextWordLenght(Round round, int lengteNext){
        assertEquals(lengteNext, round.nextWordLenght());
    }
}