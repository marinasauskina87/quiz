import java.util.List;

public class Question {
    String question;
    String correctAnswer;
    List<String> answers;

    public Question(String question, String correctAnswer, List<String> answers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }
}
