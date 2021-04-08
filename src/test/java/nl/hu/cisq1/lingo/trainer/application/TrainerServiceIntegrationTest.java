package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class TrainerServiceIntegrationTest {

    @Autowired
    private TrainerService service;

    @MockBean
    private SpringGameRepository gameRepository;

    @Test
    @DisplayName("doe een nieuwe guess op het woord")
    void testGuess(){
        List<Feedback> feedbacks = new ArrayList<>();
        Round round = new Round(0, feedbacks, "BAARD");
        Game game = new Game(1, 0, round, 1, GameStatus.PLAYING);

        when(gameRepository.findById(anyLong())).thenReturn(
                java.util.Optional.of(game)
        );

        Progress result = service.guessWord("BARST", 1);

        Progress expect = new Progress(1,0, new ArrayList<>() {{
            add("B....");
            add("BA...");
        }}, new ArrayList<>() {{
            add(new Feedback("BARST", List.of(Mark.CORRECT, Mark.CORRECT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT)));
        }}
        ,1);

        assertEquals(expect, result);
    }
}