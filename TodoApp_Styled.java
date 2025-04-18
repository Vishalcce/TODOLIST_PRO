
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TodoApp extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskField;
    private static final String FILE_NAME = "tasks.txt";

    public TodoApp() {
        setTitle("To-Do List App");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel (Input)
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(60, 63, 65));  // Dark Gray
        taskField = new JTextField(20);
        JButton addButton = new JButton("Add Task");
        styleButton(addButton);
        topPanel.add(taskField);
        topPanel.add(addButton);

        // Center Panel (List)
        taskListModel = new DefaultListModel<>();
        loadTasksFromFile();
        taskList = new JList<>(taskListModel);
        taskList.setBackground(new Color(43, 43, 43));  // Dark background for list
        taskList.setForeground(Color.WHITE);  // White text
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Bottom Panel (Delete)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(60, 63, 65));
        JButton deleteButton = new JButton("Delete Task");
        styleButton(deleteButton);
        bottomPanel.add(deleteButton);

        // Add Panels
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteTask());

        getContentPane().setBackground(new Color(43, 43, 43));  // Background of main frame
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(75, 110, 175));  // Cool Blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    private void addTask() {
        String task = taskField.getText().trim();
        if (!task.isEmpty()) {
            taskListModel.addElement(task);
            taskField.setText("");
            saveTasksToFile();
        }
    }

    private void deleteTask() {
        int selected = taskList.getSelectedIndex();
        if (selected != -1) {
            taskListModel.remove(selected);
            saveTasksToFile();
        }
    }

    private void loadTasksFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String task;
                while ((task = reader.readLine()) != null) {
                    taskListModel.addElement(task);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading tasks.");
            }
        }
    }

    private void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < taskListModel.size(); i++) {
                writer.write(taskListModel.getElementAt(i));
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving tasks.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TodoApp::new);
    }
}
