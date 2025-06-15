package financial.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Objects;


public class Payable extends JFrame {

    JTextField amountField, reasonField, typeField, noteField, dateField, otherCategoryField;
    JButton saveButton, historyButton, homeButton;
    JComboBox<String> categoryComboBox;

    Payable() {
        super("Payable");

        // Frame setup
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Icon.png"));
        Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(25, 10, 120, 120);
        add(image);

        JLabel label1 = new JLabel("PAYABLE");
        label1.setForeground(Color.ORANGE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 35));
        label1.setBounds(400, 40, 450, 40);
        add(label1);

        // Amount input
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setForeground(Color.ORANGE);
        amountLabel.setFont(new Font("Arial", Font.BOLD, 25));
        amountLabel.setBounds(200, 200, 150, 30);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(410, 195, 200, 40);
        add(amountField);

        // Category selection
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(Color.ORANGE);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 20));
        categoryLabel.setBounds(200, 250, 150, 30);
        add(categoryLabel);

        String[] categories = { "Utilities", "Rent", "Supplies", "Business Investments", "Others" };
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setBounds(410, 245, 200, 40);
        add(categoryComboBox);

        // "Other" category text field setup
        otherCategoryField = new JTextField();
        otherCategoryField.setBounds(410, 295, 200, 40);
        otherCategoryField.setVisible(false); // Initially hidden
        add(otherCategoryField);

        // Reason input
        JLabel reasonLabel = new JLabel("Transaction Reason:");
        reasonLabel.setForeground(Color.white);
        reasonLabel.setFont(new Font("Arial", Font.BOLD, 20));
        reasonLabel.setBounds(200, 350, 200, 30);
        add(reasonLabel);

        reasonField = new JTextField();
        reasonField.setBounds(410, 345, 200, 40);
        add(reasonField);

        // Transaction type input
        JLabel typeLabel = new JLabel("Transaction Type:");
        typeLabel.setForeground(Color.white);
        typeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        typeLabel.setBounds(200, 400, 200, 30);
        add(typeLabel);

        typeField = new JTextField();
        typeField.setBounds(410, 395, 200, 40);
        add(typeField);

        // Note input
        JLabel noteLabel = new JLabel("Transaction Note:");
        noteLabel.setForeground(Color.white);
        noteLabel.setFont(new Font("Arial", Font.BOLD, 20));
        noteLabel.setBounds(200, 450, 200, 30);
        add(noteLabel);

        noteField = new JTextField();
        noteField.setBounds(410, 445, 200, 40);
        add(noteField);

        // Date input
        JLabel dateLabel = new JLabel("Transaction Date (YYYY-MM-DD):");
        dateLabel.setForeground(Color.white);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        dateLabel.setBounds(200, 500, 300, 30);
        add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(510, 495, 200, 40);
        add(dateField);

        // Save button
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setForeground(Color.GREEN);
        saveButton.setBounds(250, 570, 150, 40);
        add(saveButton);

        // Show history button
        historyButton = new JButton("Show History");
        historyButton.setFont(new Font("Arial", Font.BOLD, 16));
        historyButton.setForeground(Color.BLUE);
        historyButton.setBounds(450, 570, 150, 40);
        add(historyButton);

        homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 16));
        homeButton.setBounds(650, 570, 150, 40);
        add(homeButton);

// Add ActionListener for the Home Button
        homeButton.addActionListener(e -> {
            this.dispose(); // Close the current frame
            new Home(); // Open the Home frame
        });

        saveButton.addActionListener(e -> savePayable());

        historyButton.addActionListener(e -> showHistory());

        // Category change listener
        categoryComboBox.addActionListener(e -> otherCategoryField.setVisible(Objects.equals(categoryComboBox.getSelectedItem(), "Others")));

        // Background image setup
        ImageIcon k1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Money.jpg"));
        Image k2 = k1.getImage().getScaledInstance(1000, 800, Image.SCALE_DEFAULT);
        ImageIcon k3 = new ImageIcon(k2);
        JLabel k_image = new JLabel(k3);
        k_image.setBounds(0, 0, 1000, 800);
        add(k_image);


                addEnterKeyNavigation(amountField, categoryComboBox);
                addEnterKeyNavigation(categoryComboBox, otherCategoryField);
                addEnterKeyNavigation(otherCategoryField, reasonField);
                addEnterKeyNavigation(reasonField, typeField);
                addEnterKeyNavigation(typeField, noteField);
                addEnterKeyNavigation(noteField, dateField);
                addEnterKeyNavigation(dateField, saveButton);

                // Other initialization code remains the same...

                setLayout(null);
                setSize(1000, 800);
                setLocationRelativeTo(null);
                setVisible(true);
            }

            private void addEnterKeyNavigation(JComponent current, JComponent next) {
                current.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            next.requestFocus(); // Move focus to the next component
                        }
                    }
                });
            }


    private void savePayable() {
        try {
            String amount = amountField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            assert category != null;
            if (category.equals("Others")) {
                category = otherCategoryField.getText();
            }
            String reason = reasonField.getText();
            String type = typeField.getText();
            String note = noteField.getText();
            String date = dateField.getText();

            if (amount.isEmpty() || reason.isEmpty() || type.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            try {
                Double.parseDouble(amount);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("payables.txt", true))) {
                writer.write("Amount: " + amount + ", Category: " + category + ", Reason: " + reason + ", Type: " + type + ", Note: " + note + ", Date: " + date);
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Payable details saved!");
                clearFields();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving payable details: " + e.getMessage());
        }
    }

    private void clearFields() {
        amountField.setText("");
        reasonField.setText("");
        typeField.setText("");
        noteField.setText("");
        dateField.setText("");
        otherCategoryField.setText("");
    }

    private void showHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("payables.txt"))) {
            java.util.List<String[]> rows = new java.util.ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                rows.add(parts);
            }

            if (rows.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No history to display.", "Payable History", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String[] columnNames = { "Amount", "Category", "Reason", "Type", "Note", "Date"};

            String[][] rowData = new String[rows.size()][columnNames.length];
            for (int i = 0; i < rows.size(); i++) {
                for (int j = 0; j < rows.get(i).length; j++) {
                    String[] keyValue = rows.get(i)[j].split(": ");
                    rowData[i][j] = keyValue.length > 1 ? keyValue[1] : ""; // Handle missing data gracefully
                }
            }

            JTable table = new JTable(rowData, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);

            JOptionPane.showMessageDialog(this, scrollPane, "Payable History", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading history: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        new Payable();
    }
}
