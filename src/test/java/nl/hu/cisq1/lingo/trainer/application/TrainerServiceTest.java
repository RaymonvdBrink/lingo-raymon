package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {
    WordService wordService = mock(WordService.class);
    SpringGameRepository gameRepository = mock(SpringGameRepository.class);
    TrainerService service = new TrainerService(gameRepository, wordService);

    @BeforeEach
    public void initEach(){
        WordService wordService = mock(WordService.class);
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        TrainerService service = new TrainerService(gameRepository, wordService);
    }

    @Test
    @DisplayName("Start een nieuw game plus een nieuwe ronde")
    void testNewGame(){
        when(wordService.provideRandomWord(5)).thenReturn("appel");
        Progress result = service.newGame();

        Progress expect = new Progress(1,0, new ArrayList<>() {{
            add("A....");
        }}, new ArrayList<>(),1);

        assertEquals(expect, result);
    }

    @Test
    @DisplayName("Start een nieuwe ronde")
    void testNewRound(){
        when(wordService.provideRandomWord(6)).thenReturn("TOTAAL");
        when(gameRepository.findById(anyLong())).thenReturn(
                java.util.Optional.of(new Game(1, 0, new Round(0, null, "APPEL"), 0, GameStatus.WAITING_FOR_ROUND))
                );
        Progress result = service.newRound(1);

        Progress expect = new Progress(1,0, new ArrayList<>() {{
            add("T.....");
        }}, new ArrayList<>(),1);

        assertEquals(expect, result);
    }

    @Test
    @DisplayName("doe een nieuwe guess op het woord")
    void testGuess(){
        Round round = new Round(0, new ArrayList<>(), "BAARD");
        Game game = new Game(1, 0, round, 1, GameStatus.PLAYING);

        when(gameRepository.findById(anyLong())).thenReturn(
                java.util.Optional.of(game)
        );

        Progress result = service.guessWord("BARST", 1);

        Progress expect = new Progress(1,0, new ArrayList<>() {{
            add("B....");
            add("BA...");
        }},new ArrayList<>() {{
            add(new Feedback("BARST", List.of(Mark.CORRECT, Mark.CORRECT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT)));
        }},1);

        assertEquals(expect, result);
    }
}