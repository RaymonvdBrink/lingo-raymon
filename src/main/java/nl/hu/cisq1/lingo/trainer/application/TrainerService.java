package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
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
    private final SpringWordRepository wordRepository;

    public TrainerService(SpringGameRepository gameRepository, SpringWordRepository wordRepository){
        this.gameRepository = gameRepository;
        this.wordRepository = wordRepository;
    }

    public Game saveTest(){
        Game game = new Game(0, null, 0, GameStatus.WAITING_FOR_ROUND, null);
        return gameRepository.save(game);
    }

    public Game getTest(Long id){
        Optional<Game> game = gameRepository.findById(id);
        return game.orElse(null);
    }

    public List<Game> getAll(){
        List<Game> game = gameRepository.findAll();
        return game;
    }

    public Progress newGame(){
        Game game = new Game(0, null, 0, GameStatus.WAITING_FOR_ROUND, null);

        Optional<Word> wordToGuess = wordRepository.findRandomWordByLength(5);
        String word = wordToGuess.orElse(null).getValue();

        game.startNewRound(word);
        this.gameRepository.save(game);

        return game.showProgress();
    }

    public Progress newRound(long id){
        Game game = getGameById(id);

        int nextLenght = game.nextWordLength();
        String nextWord = this.wordRepository.findRandomWordByLength(nextLenght).orElse(null).getValue();

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
