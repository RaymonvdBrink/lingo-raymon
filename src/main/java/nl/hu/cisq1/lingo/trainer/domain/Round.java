package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Round")
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private int attemps;
    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn
    private List<Feedback> feedback;
    @Column
    private String word;

    public Round(int attemps, List<Feedback> feedback, String word){
        this.attemps = attemps;
        this.feedback = feedback;
        this.word = word;
    }

    public Round(){

    }

    public void guess(String attempt){
        attemps += 1;
        List<Mark> marks = new ArrayList<>();

        if(word.length() != attempt.length()){
            for (int count = 0; word.length() > count; count++) {
                marks.add(Mark.INVALID);
            }
            feedback.add(new Feedback(attempt, marks));
            return;
        }

        for (int count = 0; word.length() > count; count++) {
            marks.add(Mark.ABSENT);
        }

        char[] wordList = word.toCharArray();
        for (int count = 0; word.length() > count; count++) {
            if(word.charAt(count) == attempt.charAt(count)){
                wordList[count] = '#';
            }
        }
        for (int count = 0; word.length() > count; count++) {
            if(new String(wordList).indexOf(attempt.charAt(count)) > 0){
                marks.set(count, Mark.PRESENT);
            }
        }

        for (int count = 0; word.length() > count; count++) {
            if(word.charAt(count) == attempt.charAt(count)){
                marks.set(count, Mark.CORRECT);
            }
        }

        feedback.add(new Feedback(attempt, marks));
    }

    public List<String> giveHint(){
        List<String> toReturn = new ArrayList<>();

        toReturn.add(String.valueOf(word.charAt(0)) + ".".repeat(word.length()-1));

        for (Feedback feedback :feedback) {
            toReturn.add(feedback.giveHint(toReturn.get(toReturn.size()-1)));
        }
        return toReturn;
    }

    public List<Feedback> feedbackHistory(){
        return feedback;
    }

    public Integer nextWordLenght(){
        int toReturn = word.length() + 1;
        if(toReturn > 7){
            toReturn = 5;
        }
        return toReturn;
    }

    public int getAttemps(){
        return attemps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return attemps == round.attemps &&
                Objects.equals(feedback, round.feedback) &&
                Objects.equals(word, round.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attemps, feedback, word);
    }

    public long getId() {
        return id;
    }

    public List<Feedback> getFeedback() {
        return feedback;
    }

    public String getWord() {
        return word;
    }
}
