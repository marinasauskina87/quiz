import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TriviaAPI {

    public static List<Question> getParsedQuestions() {
        String json = getQuestions();
        List<Question> questions = new ArrayList<>();

        JSONObject obj = new JSONObject(json);
        JSONArray results = obj.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject q = results.getJSONObject(i);

            String questionText = decodeHTML(q.getString("question"));
            String correct = decodeHTML(q.getString("correct_answer"));

            JSONArray incorrect = q.getJSONArray("incorrect_answers");

            List<String> answers = new ArrayList<>();

            for (int j = 0; j < incorrect.length(); j++) {
                answers.add(decodeHTML(incorrect.getString(j)));
            }

            answers.add(correct);

            // Antworten mischen
            Collections.shuffle(answers);

            questions.add(new Question(questionText, correct, answers));
        }

        return questions;
    }

    // deine alte Methode bleibt hier
    public static String getQuestions() {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL("https://opentdb.com/api.php?amount=5&type=multiple");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
    public static String decodeHTML(String text) {
        return text.replace("&quot;", "\"")
                .replace("&#039;", "'")
                .replace("&amp;", "&");
    }
}