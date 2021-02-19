package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;
import java.util.Objects;

public class Feedback {
    private String attempt;
    private  List<Mark> marks;

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.marks = marks;
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

    public String giveHint(String previousHint, String wordToGuess){
        if(previousHint != null) {
            char[] toReturn = previousHint.toCharArray();
            for (int count = 0; wordToGuess.length() > count; count++) {
                if (previousHint.charAt(count) == '.' || Character.isLowerCase(previousHint.charAt(count))) {
                    switch (marks.get(count)) {
                        case CORRECT:
                            toReturn[count] = wordToGuess.charAt(count);
                            break;
                        case PRESENT:
                            toReturn[count] = Character.toLowerCase(attempt.charAt(count));
                            break;
                        default:
                            //toReturn += ".";
                            break;
                    }
                }
            }
            System.out.println();
            return new String(toReturn);
        }else {
            StringBuilder toReturn = new StringBuilder(String.valueOf(wordToGuess.charAt(0)));
            toReturn.append(".".repeat(wordToGuess.length() - 1));
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
