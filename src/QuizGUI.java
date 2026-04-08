import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class QuizGUI extends JFrame {

    JLabel questionLabel;
    JButton[] answerButtons = new JButton[4];
    int currentQuestion = 0;
    int score = 0;

    // Fragen von der API laden
    List<Question> questions = TriviaAPI.getParsedQuestions(); // API liefert List<Question>

    public QuizGUI() {
        setTitle("Trivia Quiz");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Frage Label
        // Panel für die Frage
        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // oben, links, unten, rechts

// JLabel mit HTML für zentrierten Text und Zeilenumbruch
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setVerticalAlignment(SwingConstants.CENTER);

        questionPanel.setBackground(Color.WHITE);
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        add(questionPanel, BorderLayout.NORTH);
        // Panel für Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 5, 5)); // 2x2 Grid mit Abstand
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel. setBackground(Color.DARK_GRAY);

        for (int i = 0; i < 4; i++) {
            int index = i; // wichtig für Listener
            answerButtons[i] = new JButton();
            answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            answerButtons[i].setBackground(Color.ORANGE);
            answerButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    checkAnswer(index);
                }
            });
            buttonPanel.add(answerButtons[i]);
        }

        add(buttonPanel, BorderLayout.CENTER);

        loadQuestion(); // erste Frage laden
    }

    private void checkAnswer(int selectedIndex) {
        Question q = questions.get(currentQuestion);
        if (q.answers.get(selectedIndex).equals(q.correctAnswer)) {
            score++;
            JOptionPane.showMessageDialog(this, "✅ Richtig!");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Falsch! Richtige Antwort: " + q.correctAnswer);
        }

        currentQuestion++;
        if (currentQuestion < questions.size()) {
            loadQuestion();
        } else {
            JOptionPane.showMessageDialog(this, "🎉 Quiz beendet! Dein Score: " + score + "/" + questions.size());
            System.exit(0);
        }
    }

    private void loadQuestion() {
        Question q = questions.get(currentQuestion);
        questionLabel.setText("<html><body style='width: 500px'>" + q.question + "</body></html>"); // Zeilenumbruch

        // Antworten mischen
        Collections.shuffle(q.answers);

        for (int i = 0; i < 4; i++) {
            answerButtons[i].setText(q.answers.get(i));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizGUI quiz = new QuizGUI();
            quiz.setVisible(true);
        });
    }
}