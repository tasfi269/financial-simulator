package financial.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BalanceOverview extends JFrame {

    private final JTextArea transactionDetailsArea;

    public BalanceOverview() {
        super("Balance Overview");

        // Set the main panel with a border layout
        JPanel mainPanel = new JPanel(null);  // Using null layout
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(mainPanel);

        // Header Panel
        JPanel headerPanel = new JPanel(null);
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(headerPanel);
        headerPanel.setBounds(10, 10, 760, 120);

        // Icon Setup
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Icon.png"));
        Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(10, 10, 120, 120);
        headerPanel.add(image);

        JLabel label1 = new JLabel("BALANCE OVERVIEW", JLabel.CENTER);
        label1.setForeground(Color.ORANGE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 35));
        label1.setBounds(140, 10, 600, 100);
        headerPanel.add(label1);

        // Content Panel
        JPanel contentPanel = new JPanel(null);
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(contentPanel);
        contentPanel.setBounds(10, 140, 760, 400);

        // Balance Labels
        JLabel creditLabel = new JLabel("Total Credit: ");
        creditLabel.setFont(new Font("Arial", Font.BOLD, 20));
        creditLabel.setBounds(10, 10, 200, 30);
        contentPanel.add(creditLabel);

        JLabel creditValueLabel = new JLabel("0.00");
        creditValueLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        creditValueLabel.setBounds(220, 10, 200, 30);
        contentPanel.add(creditValueLabel);

        JLabel debitLabel = new JLabel("Total Debit: ");
        debitLabel.setFont(new Font("Arial", Font.BOLD, 20));
        debitLabel.setBounds(10, 50, 200, 30);
        contentPanel.add(debitLabel);

        JLabel debitValueLabel = new JLabel("0.00");
        debitValueLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        debitValueLabel.setBounds(220, 50, 200, 30);
        contentPanel.add(debitValueLabel);

        JLabel balanceLabel = new JLabel("Net Balance: ");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        balanceLabel.setBounds(10, 90, 200, 30);
        contentPanel.add(balanceLabel);

        JLabel balanceValueLabel = new JLabel("0.00");
        balanceValueLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        balanceValueLabel.setBounds(220, 90, 200, 30);
        contentPanel.add(balanceValueLabel);

        // Transaction Details Area
        transactionDetailsArea = new JTextArea();
        transactionDetailsArea.setEditable(false);
        transactionDetailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(transactionDetailsArea);
        scrollPane.setBounds(10, 130, 740, 200);
        contentPanel.add(scrollPane);

        // Home Button
        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 20));
        homeButton.setBounds(300, 340, 160, 40);
        contentPanel.add(homeButton);
        homeButton.addActionListener(e -> {
            this.dispose();
            new Home();  // Assuming Home is another JFrame
        });

        // Calculate Balance and Update Labels
        double[] balances = calculateBalance();
        creditValueLabel.setText(String.format("%.2f", balances[0]));
        debitValueLabel.setText(String.format("%.2f", balances[1]));
        balanceValueLabel.setText(String.format("%.2f", (balances[0] - balances[1])));

        // JFrame Settings
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private double[] calculateBalance() {
        double totalCredit = 0;
        double totalDebit = 0;
        ArrayList<String> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("creditordebit.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Read Line: " + line);  // Debugging print to see if the file is read correctly

                // Split the line by "|" and process each part
                String[] parts = line.split("\\|");
                if (parts.length < 3) continue;  // Skip malformed lines

                // Extract transaction type (Credit or Debit) and amount
                String type = parts[0].split(":")[0].trim();  // "Credit" or "Debit"
                String amountStr = parts[0].split(":")[1].trim(); // Extract the amount part after Credit/Debit

                // Debugging: check if amount is correctly extracted
                System.out.println("Extracted Type: " + type + " Amount: " + amountStr);

                double amount;

                // Validate if the amount is a valid number
                try {
                    amount = Double.parseDouble(amountStr);  // Parse the amount
                } catch (NumberFormatException e) {
                    // Handle invalid amount data
                    System.err.println("Invalid amount format: " + amountStr + " in line: " + line);
                    continue;  // Skip this line if the amount is invalid
                }

                // Extract date and note (if available)
                String date = parts[2].split(":")[1].trim().split(" ")[0];  // Extract only the date part (yyyy-MM-dd)

                // Add the transaction info to the list (for displaying purposes)
                transactions.add(type + " Amount:  " + amount + "  |  Date: " + date);

                // Add to the credit or debit totals
                if (type.equalsIgnoreCase("Credit")) {
                    totalCredit += amount;
                } else if (type.equalsIgnoreCase("Debit")) {
                    totalDebit += amount;
                }
            }

            // Display transactions in the details area
            if (transactions.isEmpty()) {
                transactionDetailsArea.setText("No transactions found.");
            } else {
                transactionDetailsArea.setText(String.join("\n", transactions));
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading transaction file!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return new double[]{totalCredit, totalDebit};
    }

    public static void main(String[] args) {
        new BalanceOverview();
    }
}
