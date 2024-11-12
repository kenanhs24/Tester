package SelCucumber;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CombinedApp {
    private List<String> questions;
    private List<String> answers;
    private int currentQuestionIndex = 0;

    public CombinedApp() {
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        showInitialSelection(); // Show the first selection for Java, Software Testing, Selenium, or TestNG
    }

    private void showInitialSelection() {
        String[] options = {"JAVA", "SOFTWARE TESTING", "SELENIUM", "TESTNG"};
        String selection = (String) JOptionPane.showInputDialog(null, "Select Category", "Choose",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (selection != null) {
            switch (selection) {
                case "JAVA" -> loadQuestionsFromFile("questions.txt");
                case "SOFTWARE TESTING" -> loadQuestionsFromFile("new qu.txt");
                case "SELENIUM" -> loadQuestionsFromFile("selenium_questions.txt");
                case "TESTNG" -> loadQuestionsFromFile("testng_questions.txt");
            }
            createAndShowGUI();
        }
    }

    private void loadQuestionsFromFile(String fileName) {
        questions.clear();
        answers.clear();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                JOptionPane.showMessageDialog(null, "File not found: " + fileName, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 3);
                if (parts.length == 3) {
                    questions.add(parts[1].trim());
                    answers.add(parts[2].trim());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("TESTER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        JTextArea textArea = new JTextArea(10, 50);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);

        JButton previousButton = new JButton("Previous Question");
        JButton nextButton = new JButton("Next Question");
        JButton showAnswerButton = new JButton("Show Answer");
        JButton googleButton = new JButton("Ask Google");
        JButton selectQuestionButton = new JButton("Select Question");
        JButton exitButton = new JButton("Exit");

        panel.add(previousButton);
        panel.add(nextButton);
        panel.add(showAnswerButton);
        panel.add(googleButton);
        panel.add(selectQuestionButton);
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);

        if (!questions.isEmpty()) {
            textArea.setText("Q: " + questions.get(currentQuestionIndex));
        }

        // Show previous question
        previousButton.addActionListener(e -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                textArea.setText("Q: " + questions.get(currentQuestionIndex));
            } else {
                JOptionPane.showMessageDialog(frame, "This is the first question.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Show next question
        nextButton.addActionListener(e -> {
            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                textArea.setText("Q: " + questions.get(currentQuestionIndex));
            } else {
                JOptionPane.showMessageDialog(frame, "End of questions reached.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Show answer
        showAnswerButton.addActionListener(e -> {
            if (currentQuestionIndex < answers.size()) {
                String formattedAnswer = formatAnswerForDisplay(answers.get(currentQuestionIndex));
                JOptionPane.showMessageDialog(frame, formattedAnswer, "Answer", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "No answer available for this question.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Google search
        googleButton.addActionListener(e -> {
            String question = questions.get(currentQuestionIndex);
            try {
                String encodedQuestion = URLEncoder.encode(question, StandardCharsets.UTF_8.toString());
                String searchUrl = "https://www.google.com/search?q=" + encodedQuestion;
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(searchUrl));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error opening browser: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Select a specific question
        selectQuestionButton.addActionListener(e -> {
            String[] questionArray = questions.toArray(new String[0]);
            String selectedQuestion = (String) JOptionPane.showInputDialog(frame, "Select a Question:", "Select Question",
                    JOptionPane.PLAIN_MESSAGE, null, questionArray, questionArray[currentQuestionIndex]);

            if (selectedQuestion != null) {
                currentQuestionIndex = questions.indexOf(selectedQuestion);
                textArea.setText("Q: " + selectedQuestion);
            }
        });

        // Exit the application
        exitButton.addActionListener(e -> frame.dispose());
    }

    // Helper method to format long answer for display
    private static String formatAnswerForDisplay(String answer) {
        int maxLineLength = 80;
        StringBuilder formattedAnswer = new StringBuilder();
        String[] words = answer.split(" ");
        int currentLineLength = 0;

        for (String word : words) {
            if (currentLineLength + word.length() + 1 > maxLineLength) {
                formattedAnswer.append("\n");
                currentLineLength = 0;
            }
            formattedAnswer.append(word).append(" ");
            currentLineLength += word.length() + 1;
        }

        return formattedAnswer.toString().trim();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CombinedApp::new);
    }
}
