package nl.hu.cisq1.lingo.trainer.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private int score;
    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn
    private Round round;
    @Column
    private int rounds;
    @Column
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;
    @Transient
    private Progress progress;

    public Game(int id, int score, Round round, int rounds, GameStatus gameStatus, Progress progress) {
        this.id = id;
        this.score = score;
        this.round = round;
        this.rounds = rounds;
        this.gameStatus = gameStatus;
        this.progress = progress;
    }

    public Game(int score, Round round, int rounds, GameStatus gameStatus, Progress progress) {
        this.score = score;
        this.round = round;
        this.rounds = rounds;
        this.gameStatus = gameStatus;
        this.progress = progress;
    }

    public Game(){

    }

    public void startNewRound(String wordToGuess){
        if(!gameStatus.equals(GameStatus.WAITING_FOR_ROUND)){
            return;
        }
        String attempt = String.valueOf(wordToGuess.charAt(0));
        List<Mark> marks = new ArrayList();
        marks.add(Mark.INVALID);
        for (int i = 1; i < wordToGuess.length(); i++){
            attempt += ".";
            marks.add(Mark.INVALID);
        }

        Feedback feedback = new Feedback(attempt, marks);
        List<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(feedback);

        round = new Round(0, feedbacks, wordToGuess);

        /*List<Feedback> feedbackExpect = new ArrayList<>();
        feedbackExpect.add(new Feedback("B....", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
        round = new Round(0, feedbackExpect,"BAARD");*/

        gameStatus = GameStatus.PLAYING;

        rounds++;
    }

    public void guess(String word){
        if(gameStatus.equals(GameStatus.PLAYING)){
            round.guess(word);
            List<Feedback> feedbacks = round.getFeedbackHistory();
            if(feedbacks.get(feedbacks.size()-1).isWordGuessed()){
                gameStatus = GameStatus.WAITING_FOR_ROUND;
                score += 5 * (5 - round.getAttemps()) + 5;
            }
            if(round.getAttemps() > 5){
                gameStatus = GameStatus.ELIMINATED;
            }
        }
    }

    public Progress showProgress(){
        progress = new Progress(id, score, round.getFeedbackHistory(), rounds);
        return progress;
    }

    public Integer nextWordLength(){
        return round.NextWordLenght();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id &&
                score == game.score &&
                Objects.equals(round, game.round) &&
                gameStatus == game.gameStatus &&
                Objects.equals(progress, game.progress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, round, gameStatus, progress);
    }

    public long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public Round getRound() {
        return round;
    }

    public int getRounds() {
        return rounds;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Progress getProgress() {
        return progress;
    }
}
