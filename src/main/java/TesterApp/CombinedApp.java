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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CombinedApp {
    private List<String> questions;
    private List<String> answers;
    private List<String> wrongAnswers; // List to store wrong answers
    private List<Integer> correctAnswers; // Indices of correct answers
    private List<Integer> wrongAnswersIndices; // Indices of wrong answers
    private Set<Integer> viewedQuestions; // Indices of viewed questions
    private int currentQuestionIndex = 0;
    private int fontSize = 22; // Font size for main display
    private int answerFontSize = 22; // Font size for answer dialog
    private JTextArea textArea; // Question display area
    private JList<String> questionNumberList; // List for question numbers
    private DefaultListModel<String> questionNumberModel; // Model for dynamic question numbers
    private final String[] options = {
            "JAVA", "SOFTWARE TESTING", "SELENIUM", "TESTNG", "CSS", "XPATH", "UI TESTING", "GITHUB",
            "CUCUMBER", "MAVEN", "POSTMAN", "API TESTING", "SQL", "JENKINS", "JDBC",
            "AWS", "JUNIT"
    };

    public CombinedApp() {
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        wrongAnswers = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        wrongAnswersIndices = new ArrayList<>();
        viewedQuestions = new HashSet<>();
        createAndShowGUI();
    }

    private void loadQuestionsFromFile(String fileName) {
        questions.clear();
        answers.clear();
        correctAnswers.clear();
        wrongAnswersIndices.clear();
        wrongAnswers.clear();
        viewedQuestions.clear();

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

            if (questions.isEmpty() || answers.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No valid questions or answers found in the file.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Update the list of question numbers
            updateQuestionNumbers();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Tester Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());

        // Left side panel (Topics and Question Numbers)
        JPanel leftPanel = new JPanel(new BorderLayout());

        JList<String> topicList = new JList<>(options);
        topicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane topicScrollPane = new JScrollPane(topicList);
        topicScrollPane.setPreferredSize(new Dimension(200, 350));
        topicScrollPane.setBorder(BorderFactory.createTitledBorder("Topics"));

        questionNumberModel = new DefaultListModel<>();
        questionNumberList = new JList<>(questionNumberModel);
        questionNumberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        questionNumberList.setCellRenderer(new CustomCellRenderer()); // Custom renderer for colors
        JScrollPane questionNumberScrollPane = new JScrollPane(questionNumberList);
        questionNumberScrollPane.setPreferredSize(new Dimension(200, 350));
        questionNumberScrollPane.setBorder(BorderFactory.createTitledBorder("Question Numbers"));

        leftPanel.add(topicScrollPane, BorderLayout.NORTH);
        leftPanel.add(questionNumberScrollPane, BorderLayout.CENTER);

        // Right side panel
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Question display area
        textArea = new JTextArea(12, 60);
        textArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
        textArea.setEditable(false);
        JScrollPane questionScrollPane = new JScrollPane(textArea);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setPreferredSize(new Dimension(800, 120));

        JButton previousButton = new JButton("Previous Question");
        JButton nextButton = new JButton("Next Question");
        JButton showAnswerButton = new JButton("Show Answer");
        JButton correctButton = new JButton("Answer Correct");
        JButton wrongButton = new JButton("Answer Wrong");
        JButton repeatWrongButton = new JButton("Repeat Wrong Answers");
        JButton googleButton = new JButton("Ask Google");
        JButton zoomInButton = new JButton("Zoom In");
        JButton zoomOutButton = new JButton("Zoom Out");

        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(showAnswerButton);
        buttonPanel.add(correctButton);
        buttonPanel.add(wrongButton);
        buttonPanel.add(repeatWrongButton);
        buttonPanel.add(googleButton);
        buttonPanel.add(zoomInButton);
        buttonPanel.add(zoomOutButton);

        rightPanel.add(questionScrollPane, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.CENTER);

        frame.setVisible(true);

        // Load default content for the first topic
        loadQuestionsFromFile("Java_questions.txt");
        if (!questions.isEmpty()) {
            textArea.setText("Q: " + questions.get(currentQuestionIndex));
        } else {
            textArea.setText("No questions available.");
        }

        // Topic selection listener
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
                        case "AWS" -> loadQuestionsFromFile("aws_questions.txt");
                        case "JUNIT" -> loadQuestionsFromFile("junit_questions.txt");
                        default -> JOptionPane.showMessageDialog(frame, "Invalid Selection!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    currentQuestionIndex = 0;
                    if (!questions.isEmpty()) {
                        textArea.setText("Q: " + questions.get(currentQuestionIndex));
                    } else {
                        textArea.setText("No questions available.");
                    }
                }
            }
        });

        // Question number selection listener
        questionNumberList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = questionNumberList.getSelectedIndex();
                if (selectedIndex != -1) {
                    currentQuestionIndex = selectedIndex;
                    textArea.setText("Q: " + questions.get(currentQuestionIndex));
                    markAsViewed(currentQuestionIndex);
                }
            }
        });

        // Button actions
        previousButton.addActionListener(e -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                textArea.setText("Q: " + questions.get(currentQuestionIndex));
                questionNumberList.setSelectedIndex(currentQuestionIndex);
                markAsViewed(currentQuestionIndex);
            } else {
                JOptionPane.showMessageDialog(frame, "This is the first question.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        nextButton.addActionListener(e -> {
            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                textArea.setText("Q: " + questions.get(currentQuestionIndex));
                questionNumberList.setSelectedIndex(currentQuestionIndex);
                markAsViewed(currentQuestionIndex);
            } else {
                JOptionPane.showMessageDialog(frame, "End of questions reached.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        showAnswerButton.addActionListener(e -> {
            if (currentQuestionIndex < answers.size()) {
                JTextArea answerTextArea = new JTextArea(answers.get(currentQuestionIndex));
                answerTextArea.setFont(new Font("Arial", Font.PLAIN, answerFontSize));
                answerTextArea.setLineWrap(true);
                answerTextArea.setWrapStyleWord(true);

                JScrollPane answerScrollPane = new JScrollPane(answerTextArea);
                answerScrollPane.setPreferredSize(new Dimension(600, 400));

                JPanel answerPanel = new JPanel(new BorderLayout());
                JButton zoomInAnswer = new JButton("Zoom In Answer");
                JButton zoomOutAnswer = new JButton("Zoom Out Answer");

                zoomInAnswer.addActionListener(zoomEvent -> {
                    answerFontSize += 2;
                    answerTextArea.setFont(new Font("Arial", Font.PLAIN, answerFontSize));
                });

                zoomOutAnswer.addActionListener(zoomEvent -> {
                    if (answerFontSize > 6) {
                        answerFontSize -= 2;
                        answerTextArea.setFont(new Font("Arial", Font.PLAIN, answerFontSize));
                    }
                });

                answerPanel.add(answerScrollPane, BorderLayout.CENTER);
                JPanel zoomPanel = new JPanel();
                zoomPanel.add(zoomInAnswer);
                zoomPanel.add(zoomOutAnswer);

                answerPanel.add(zoomPanel, BorderLayout.SOUTH);
                JOptionPane.showMessageDialog(frame, answerPanel, "Answer", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "No answer available for this question.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        googleButton.addActionListener(e -> {
            if (currentQuestionIndex < questions.size()) {
                String question = questions.get(currentQuestionIndex);
                try {
                    String encodedQuestion = URLEncoder.encode(question, StandardCharsets.UTF_8);
                    String searchUrl = "https://www.google.com/search?q=" + encodedQuestion;
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(new URI(searchUrl));
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error opening browser: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No question selected for Google search.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        correctButton.addActionListener(e -> {
            if (!correctAnswers.contains(currentQuestionIndex)) {
                correctAnswers.add(currentQuestionIndex);
                wrongAnswersIndices.remove((Integer) currentQuestionIndex); // Remove from wrong if exists
                questionNumberList.repaint();
            }
        });

        wrongButton.addActionListener(e -> {
            if (!wrongAnswersIndices.contains(currentQuestionIndex)) {
                wrongAnswersIndices.add(currentQuestionIndex);
                wrongAnswers.add(questions.get(currentQuestionIndex));
                correctAnswers.remove((Integer) currentQuestionIndex); // Remove from correct if exists
                questionNumberList.repaint();
            }
        });

        repeatWrongButton.addActionListener(e -> {
            if (wrongAnswers.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No wrong answers to repeat.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, String.join("\n", wrongAnswers), "Wrong Answers", JOptionPane.INFORMATION_MESSAGE);
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

    private void markAsViewed(int index) {
        viewedQuestions.add(index);
        questionNumberList.repaint();
    }

    private void updateQuestionNumbers() {
        questionNumberModel.clear();
        for (int i = 0; i < questions.size(); i++) {
            questionNumberModel.addElement("Q" + (i + 1));
        }
        questionNumberList.setSelectedIndex(currentQuestionIndex);
    }

    private class CustomCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (renderer instanceof JLabel label) {
                if (correctAnswers.contains(index)) {
                    label.setBackground(Color.GREEN);
                } else if (wrongAnswersIndices.contains(index)) {
                    label.setBackground(Color.RED);
                } else if (viewedQuestions.contains(index)) {
                    label.setBackground(Color.CYAN);
                }
                if (isSelected) {
                    label.setForeground(Color.BLACK);
                }
            }
            return renderer;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CombinedApp::new);
    }
}
