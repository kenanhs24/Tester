# README: CombinedApp - Java Learning and Testing Application

## Overview
The **CombinedApp** is a Java-based learning tool designed for software testers and developers. It allows users to browse through various topics, view questions, see answers, and interact with code examples. The application provides features like zooming in/out for better readability and Google searching for selected questions, all within a single unified interface.

---

## Features
1. **Topic Selector**:
   - A **JList** on the left side of the application allows users to select topics such as **Java**, **Selenium**, **Cucumber**, and more.

2. **Question Viewer**:
   - Questions from the selected topic are displayed in a central text area.
   - Navigate between questions using "Previous Question" and "Next Question" buttons.

3. **Answer Viewer**:
   - Answers to questions can be displayed in a pop-up window by clicking the "Show Answer" button.

4. **Search Google**:
   - Perform a Google search for the current question by clicking the "Ask Google" button.

5. **Zoom In/Out**:
   - Adjust the font size of questions and answers for better readability using "Zoom In" and "Zoom Out" buttons.

6. **Unified Layout**:
   - A single frame layout ensures a user-friendly experience, with all options available in one place.

7. **Dynamic Topic Loading**:
   - Questions and answers are dynamically loaded from resource files based on the selected topic.

---

## Prerequisites
- **Java Development Kit (JDK)** version 8 or higher.
- An IDE like **IntelliJ IDEA** (or any Java IDE).
- Resource files in the `src/main/resources` folder containing topic-based questions and answers.

---

## Resource File Format
Each topic has its own text file stored in `src/main/resources/`. These files follow a specific format:

```
1|Question Text|Answer Text
2|Question Text|Answer Text
...
```

For example, in `Java_questions.txt`:
```
1|Write a program to check if a number is an Armstrong number.|public class Armstrong {...}
2|Write a program to find the binary representation of a number.|public class BinaryRepresentation {...}
```

---

## How to Run the Application
1. Clone the repository or download the project files.
2. Open the project in your preferred IDE (e.g., IntelliJ IDEA).
3. Ensure all resource files (e.g., `Java_questions.txt`) are located in `src/main/resources`.
4. Run the `CombinedApp` class.
5. The application will launch, displaying a unified interface.

---

## Instructions for Use
1. **Select a Topic**:
   - Use the **left panel** to select a topic from the list.
   - The first question from the topic will automatically load.

2. **Navigate Questions**:
   - Use the **"Previous Question"** and **"Next Question"** buttons to browse through the questions.

3. **View Answers**:
   - Click **"Show Answer"** to display the answer for the current question in a pop-up window.

4. **Search Online**:
   - Click **"Ask Google"** to search the current question on Google in your default browser.

5. **Adjust Font Size**:
   - Use the **"Zoom In"** and **"Zoom Out"** buttons to adjust the text size.

6. **Switch Topics**:
   - Select a different topic from the left-side **JList**, and the questions for that topic will load dynamically.

---

## Folder Structure
```
CombinedApp/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── SelCumcumber/
│   │   │       └── CombinedApp.java  # Main Java Application
│   │   ├── resources/
│   │       └── *.txt  # Resource files for topics and questions
│
├── README.md  # This readme file
```

---

## How to Add a New Topic
1. **Create a New Resource File**:
   - Add a new `.txt` file to the `src/main/resources` directory.
   - Follow the same format: `QuestionID|QuestionText|AnswerText`.

2. **Update the `options` Array**:
   - Add the new topic name to the `options` array in the `CombinedApp` class.

3. **Update the Topic Switch Logic**:
   - In the `topicList.addListSelectionListener` method, add a new `case` for the new topic and link it to the corresponding file.

Example:
```java
case "NEW TOPIC" -> loadQuestionsFromFile("new_topic_questions.txt");
```

---

## Troubleshooting
1. **"File not found" Error**:
   - Ensure the resource file exists in the `src/main/resources` directory with the correct name.

2. **Questions or Answers Not Loading**:
   - Check the resource file for proper formatting (`QuestionID|QuestionText|AnswerText`).

3. **Zoom Function Not Working**:
   - Ensure the font size is within an appropriate range (minimum size: 6).

---

## Future Improvements
- Add **search functionality** to find specific questions by keywords.
- Implement **progress tracking** to show which questions have been answered.
- Allow users to **edit questions and answers** directly in the application.

---

## License
This project is licensed under the SEN License. Feel free to modify and use it for personal or educational purposes.

---

Enjoy using **CombinedApp** for your learning and testing needs! 🎉
