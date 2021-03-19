package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Feedback")
public class Feedback {
    @Id
    private long id;
    @Column
    private String attempt;
    @ElementCollection
    private  List<Mark> marks;

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.marks = marks;
    }

    public Feedback(){

    }

    public String getAttempt() {
        return attempt;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public boolean isWordGuessed() {
        return this.marks.stream()
                .allMatch(Mark.CORRECT::equals);
        /*return this.marks.stream()
                .allMatch(mark -> mark.equals(Mark.CORRECT));*/
        /*for(Mark mark : this.marks){
            if(!mark.equals(Mark.CORRECT)){
                return false;
            }
        }
        return true;*/
    }

    public boolean isWordInvalid() {
        return this.marks.stream()
                .allMatch(Mark.INVALID::equals);
        /*return this.marks.stream()
                .allMatch(mark -> mark.equals(Mark.INVALID));*/
        /*for(Mark mark : this.marks){
            if(!mark.equals(Mark.INVALID)){
                return false;
            }
        }
        return true;*/
    }

    public String giveHint(String previousHint){
        if(previousHint != null) {
            char[] toReturn = previousHint.toCharArray();
            for (int count = 0; marks.size() > count; count++) {
                if (previousHint.charAt(count) == '.' || Character.isLowerCase(previousHint.charAt(count))) {
                    switch (marks.get(count)) {
                        case CORRECT:
                            toReturn[count] = attempt.charAt(count);
                            break;
                        /*case PRESENT:
                            toReturn[count] = Character.toLowerCase(attempt.charAt(count));
                            break;*/
                        default:
                            //toReturn += ".";
                            break;
                    }
                }
            }
            return new String(toReturn);
        }else {
            StringBuilder toReturn = new StringBuilder(String.valueOf(attempt.charAt(0)));
            toReturn.append(".".repeat(attempt.length() - 1));
            return toReturn.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marks);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", marks=" + marks +
                '}';
    }
}
