package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;
import java.util.Objects;

public class Progress {
    private long gameId;
    private int score;
    private List<Feedback> hints;
    private int roundNumber;

    public Progress(long gameId, int score, List<Feedback> hints, int roundNumber){
        this.gameId = gameId;
        this.score = score;
        this.hints = hints;
        this.roundNumber = roundNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Progress progress = (Progress) o;
        return score == progress.score &&
                roundNumber == progress.roundNumber &&
                Objects.equals(hints, progress.hints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, hints, roundNumber);
    }
}
