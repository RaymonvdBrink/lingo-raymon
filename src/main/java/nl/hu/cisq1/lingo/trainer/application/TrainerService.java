package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class TrainerService {
    private final SpringGameRepository gameRepository;
    private WordService wordService;

    public TrainerService(SpringGameRepository gameRepository, WordService wordService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }

    public List<Game> getAll(){
        List<Game> game = gameRepository.findAll();
        return game;
    }

    public Progress newGame(){
        Game game = new Game(0, null, 0, GameStatus.WAITING_FOR_ROUND);

        String word = wordService.provideRandomWord(5).toUpperCase();

        game.startNewRound(word);
        this.gameRepository.save(game);

        Progress progress = game.showProgress();
        return progress;
    }

    public Progress newRound(long id){
        Game game = getGameById(id);

        int nextLenght = game.nextWordLength();
        String nextWord = wordService.provideRandomWord(nextLenght).toUpperCase();

        game.startNewRound(nextWord);
        this.gameRepository.save(game);

        return  game.showProgress();
    }

    public Game getGameById(long id){
        return this.gameRepository.findById(id).orElseThrow();
    }

    public Progress guessWord(String guess, long id){
        Game game = getGameById(id);

        game.guess(guess);

        return game.showProgress();
    }
}
