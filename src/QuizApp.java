import java.util.List;

public class QuizApp {
    public static void main(String[] args) {

        List<Question> questions = TriviaAPI.getParsedQuestions();
        int number = 1;

        for (Question q : questions) {
            System.out.println("Frage " + number + ": "+ q.question);
            number++;

            for (int i = 0; i < q.answers.size(); i++) {
                System.out.println((i + 1) + ": " + q.answers.get(i));
            }
            System.out.println("The right answer is: " + q.correctAnswer);
            System.out.println("-------------------");
        }
    }
}