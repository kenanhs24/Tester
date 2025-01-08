package TesterApp;

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
    private int fontSize = 14; // Default font size
    private JTextArea textArea; // Question display area
    private final String[] options = {
            "JAVA", "SOFTWARE TESTING", "SELENIUM", "TESTNG", "CSS", "XPATH", "UI TESTING", "GITHUB",
            "CUCUMBER", "MAVEN", "POSTMAN", "API TESTING", "SQL", "JENKINS", "JDBC",
            "AWS", "JUNIT"
    };

    public CombinedApp() {
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        createAndShowGUI();
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
                } else {
                    System.err.println("Skipped line (invalid format): " + line);
                }
            }

            if (questions.isEmpty() || answers.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No valid questions or answers found in the file.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Tester Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        // Left side options (JList)
        JList<String> topicList = new JList<>(options);
        topicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane topicScrollPane = new JScrollPane(topicList);
        topicScrollPane.setPreferredSize(new Dimension(200, 600));

        // Right side panel
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Question display area
        textArea = new JTextArea(12, 60);
        textArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
        textArea.setEditable(false);
        JScrollPane questionScrollPane = new JScrollPane(textArea);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton previousButton = new JButton("Previous Question");
        JButton nextButton = new JButton("Next Question");
        JButton showAnswerButton = new JButton("Show Answer");
        JButton googleButton = new JButton("Ask Google");
        JButton zoomInButton = new JButton("Zoom In");
        JButton zoomOutButton = new JButton("Zoom Out");

        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(showAnswerButton);
        buttonPanel.add(googleButton);
        buttonPanel.add(zoomInButton);
        buttonPanel.add(zoomOutButton);

        rightPanel.add(questionScrollPane, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(topicScrollPane, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.CENTER);

        frame.setVisible(true);

        // Load default content for the first topic
        loadQuestionsFromFile("Java_questions.txt");
        if (!questions.isEmpty()) {
            textArea.setText("Q: " + questions.get(currentQuestionIndex));
        } else {
            textArea.setText("No questions available.");
        }

        // Add listener for topic selection
        topicList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedTopic = topicList.getSelectedValue();
                if (selectedTopic != null) {
                    switch (selectedTopic) {
                        case "JAVA" -> loadQuestionsFromFile("Java_questions.txt");
                        case "SOFTWARE TESTING" -> loadQuestionsFromFile("SDLC.txt");
                        case "SELENIUM" -> loadQuestionsFromFile("selenium_questions.txt");
                        case "TESTNG" -> loadQuestionsFromFile("testng_questions.txt");
                        case "CSS" -> loadQuestionsFromFile("css_questions.txt");
                        case "XPATH" -> loadQuestionsFromFile("xpath_questions.txt");
                        case "UI TESTING" -> loadQuestionsFromFile("ui_testing_questions.txt");
                        case "GITHUB" -> loadQuestionsFromFile("github_questions.txt");
                        case "CUCUMBER" -> loadQuestionsFromFile("cucumber_questions.txt");
                        case "MAVEN" -> loadQuestionsFromFile("maven_questions.txt");
                        case "POSTMAN" -> loadQuestionsFromFile("postman_questions.txt");
                        case "API TESTING" -> loadQuestionsFromFile("api_testing_questions.txt");
                        case "SQL" -> loadQuestionsFromFile("sql_questions.txt");
                        case "JENKINS" -> loadQuestionsFromFile("jenkins_questions.txt");
                        case "JDBC" -> loadQuestionsFromFile("jdbc_questions.txt");
                        case "MANUAL TESTING" -> loadQuestionsFromFile("manual_testing_questions.txt");
                        case "AWS" -> loadQuestionsFromFile("aws_questions.txt");
                        case "JUNIT" -> loadQuestionsFromFile("junit_questions.txt");
                        default -> JOptionPane.showMessageDialog(frame, "Invalid Selection!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    currentQuestionIndex = 0; // Reset to the first question
                    if (!questions.isEmpty()) {
                        textArea.setText("Q: " + questions.get(currentQuestionIndex));
                    } else {
                        textArea.setText("No questions available.");
                    }
                }
            }
        });

        // Navigation button actions
        previousButton.addActionListener(e -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                textArea.setText("Q: " + questions.get(currentQuestionIndex));
            } else {
                JOptionPane.showMessageDialog(frame, "This is the first question.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        nextButton.addActionListener(e -> {
            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                textArea.setText("Q: " + questions.get(currentQuestionIndex));
            } else {
                JOptionPane.showMessageDialog(frame, "End of questions reached.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        showAnswerButton.addActionListener(e -> {
            if (currentQuestionIndex < answers.size()) {
                String answer = answers.get(currentQuestionIndex);
                JTextArea answerTextArea = new JTextArea(answer);
                answerTextArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
                answerTextArea.setLineWrap(true);
                answerTextArea.setWrapStyleWord(true);
                answerTextArea.setEditable(false);
                JScrollPane scrollPaneAnswer = new JScrollPane(answerTextArea);
                scrollPaneAnswer.setPreferredSize(new Dimension(600, 400));
                JOptionPane.showMessageDialog(frame, scrollPaneAnswer, "Answer", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "No answer available for this question.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

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

        zoomInButton.addActionListener(e -> {
            fontSize += 2;
            textArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
        });

        zoomOutButton.addActionListener(e -> {
            if (fontSize > 6) {
                fontSize -= 2;
                textArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CombinedApp::new);
    }
}
