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
        /*for (int count = 0; word.length() > count; count++) {
            if(word.charAt(count) == attempt.charAt(count)){
                Mark mark = Mark.CORRECT;
                for (Feedback feedback:feedback) {
                    if(feedback.getMarks().get(count).equals(Mark.CORRECT)){
                        mark = Mark.ABSENT;
                    }
                }
                marks.add(mark);
            }else if(word.indexOf(attempt.charAt(count)) > 0){
                Mark mark = Mark.PRESENT;
                for (Feedback feedback:feedback) {
                    if(feedback.getMarks().get(count).equals(Mark.PRESENT)){
                        mark = Mark.ABSENT;
                    }
                }
                marks.add(mark);
            }else {
                marks.add(Mark.ABSENT);
            }
        }*/

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

    public String giveHint(){
        /*if(feedback.size() > 0){
            String previousHint = feedback.get(feedback.size() - 2).getAttempt();
            return feedback.get(feedback.size() - 1).giveHint(previousHint);
        }else {
            return feedback.get(feedback.size() - 1).giveHint(null);
        }*/
        String toReturn = null;
        for (Feedback feedback :feedback) {
            toReturn = feedback.giveHint(toReturn);
        }
        return toReturn;
    }

    public List<Feedback> getFeedbackHistory(){
        return feedback;
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
}
