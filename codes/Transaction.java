package financial.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class Transaction extends JFrame {

    private final JTextArea resultArea;

    public Transaction() {
        super("Transaction");

        // Icon Setup
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Icon.png"));
        Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(25, 10, 120, 120);
        add(image);

        JLabel label1 = new JLabel("TRANSACTION");
        label1.setForeground(Color.ORANGE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 35));
        label1.setBounds(350, 40, 450, 40);
        add(label1);

        // Dropdown for Filter Type
        JLabel filterTypeLabel = new JLabel("Filter By:");
        filterTypeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        filterTypeLabel.setForeground(Color.ORANGE);
        filterTypeLabel.setBounds(200, 150, 100, 30);
        add(filterTypeLabel);

        JComboBox<String> filterTypeComboBox = new JComboBox<>(new String[]{"Date (yyyy-MM-dd)", "Month (yyyy-MM)", "Year (yyyy)", "Transaction Type", "Amount Range"});
        filterTypeComboBox.setBounds(300, 150, 200, 30);
        add(filterTypeComboBox);

        // Input Field
        JLabel inputLabel = new JLabel("Enter Value:");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputLabel.setForeground(Color.ORANGE);
        inputLabel.setBounds(200, 200, 100, 30);
        add(inputLabel);

        JTextField inputField = new JTextField();
        inputField.setBounds(300, 200, 200, 30);
        inputField.setVisible(false);  // Initially hide the input field
        add(inputField);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(520, 200, 100, 30);
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(searchButton);

        // Home Button
        JButton backButton = new JButton("Home");
        backButton.setBounds(650, 200, 100, 30);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(backButton);
        backButton.addActionListener(e -> {
            this.dispose(); // Close the current frame
            new Home(); // Open the Home frame
        });

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(200, 250, 600, 400);
        add(scrollPane);

        // Background Image
        ImageIcon k1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Money.jpg"));
        Image k2 = k1.getImage().getScaledInstance(1000, 800, Image.SCALE_DEFAULT);
        ImageIcon k3 = new ImageIcon(k2);
        JLabel k_image = new JLabel(k3);
        k_image.setBounds(0, 0, 1000, 800);
        add(k_image);

        // Button Listeners
        searchButton.addActionListener(e -> {
            String selectedFilter = (String) filterTypeComboBox.getSelectedItem();
            String userInput = inputField.getText().trim();
            if (userInput.isEmpty()) {
                JOptionPane.showMessageDialog(Transaction.this, "Please enter a valid input!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                displayTransactions(selectedFilter, userInput);
            }
        });

        filterTypeComboBox.addActionListener(e -> {
            String selectedFilter = (String) filterTypeComboBox.getSelectedItem();
            assert selectedFilter != null;
            if (selectedFilter.equals("Amount Range")) {
                inputLabel.setText("Enter Range (e.g., 100-500):");
            } else inputLabel.setText("Enter Value:");
            inputField.setVisible(true); // Show the input field
        });

        resultArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int start = resultArea.getSelectionStart();
                    int end = resultArea.getSelectionEnd();
                    String selectedTransaction = resultArea.getText().substring(start, end);
                    JOptionPane.showMessageDialog(Transaction.this, selectedTransaction, "Transaction Details", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Add Enter Key Listener to Input Field (Text Field)
        inputField.addActionListener(e -> {
            // Trigger the search button's action when Enter is pressed
            searchButton.doClick(); // Simulate a button click
        });

        // JFrame Settings
        setLayout(null);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void displayTransactions(String filterType, String input) {
        ArrayList<String> transactions = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        try (BufferedReader reader = new BufferedReader(new FileReader("creditordebit.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 3) continue;

                String transactionDateStr = parts[2].split(":")[1].trim();
                Date transactionDate = dateFormat.parse(transactionDateStr);

                switch (filterType) {
                    case "Date (yyyy-MM-dd)":
                        if (input.equals(dateFormat.format(transactionDate))) transactions.add(line);
                        break;
                    case "Month (yyyy-MM)":
                        if (input.equals(monthFormat.format(transactionDate))) transactions.add(line);
                        break;
                    case "Year (yyyy)":
                        if (input.equals(yearFormat.format(transactionDate))) transactions.add(line);
                        break;
                    case "Transaction Type":
                        if (line.toLowerCase().contains(input.toLowerCase())) transactions.add(line);
                        break;
                    case "Amount Range":
                        String[] range = input.split("-");
                        if (range.length == 2) {
                            try {
                                double minAmount = Double.parseDouble(range[0]);
                                double maxAmount = Double.parseDouble(range[1]);
                                double amount = Double.parseDouble(parts[3].split(":")[1].trim());  // Assuming amount is in the 4th column
                                if (amount >= minAmount && amount <= maxAmount) {
                                    transactions.add(line);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this, "Invalid amount range format!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                }
            }

            if (transactions.isEmpty()) {
                resultArea.setText("No transactions found for the given input.");
            } else {
                resultArea.setText(String.join("\n", transactions));
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format! Use the format based on the filter.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading transaction file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Transaction();
    }
}
