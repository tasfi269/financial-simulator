package financial.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CreditOrDebit extends JFrame {

    // GUI components
    private final JTextField amountField;
    private final JTextField noteField;
    public JButton creditButton;
    public JButton debitButton;
    public JButton exitButton;
    public JLabel dateTimeLabel;
    public JLabel messageLabel;

    public CreditOrDebit() {
        super("Credit / Debit");

        // Icon Setup
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Icon.png"));
        Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(25, 10, 120, 120);
        add(image);

        JLabel label1 = new JLabel("CREDIT / DEBIT");
        label1.setForeground(Color.ORANGE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 35));
        label1.setBounds(350, 40, 450, 40);
        add(label1);

        // Date and Time Label
        dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dateTimeLabel.setForeground(Color.ORANGE);
        dateTimeLabel.setBounds(750, 10, 250, 30);
        add(dateTimeLabel);
        updateDateTime();

        // Amount Input
        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setForeground(Color.WHITE);
        amountLabel.setBounds(220, 170, 200, 30);
        amountLabel.setForeground(Color.ORANGE);
        amountLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(380, 170, 200, 30);
        amountField.setToolTipText("Enter the transaction amount in numeric format.");
        add(amountField);

        // Note Input
        JLabel noteLabel = new JLabel("Enter Note:");
        noteLabel.setForeground(Color.WHITE);
        noteLabel.setBounds(220, 220, 150, 30);
        noteLabel.setForeground(Color.ORANGE);
        noteLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(noteLabel);

        noteField = new JTextField();
        noteField.setBounds(340, 220, 200, 30);
        noteField.setToolTipText("Enter a brief note about the transaction.");
        add(noteField);

        // Buttons for Credit and Debit
        creditButton = new JButton("Credit");
        creditButton.setBounds(300, 300, 100, 40);
        creditButton.setBackground(Color.white);
        creditButton.setForeground(Color.green);
        creditButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(creditButton);

        debitButton = new JButton("Debit");
        debitButton.setBounds(450, 300, 100, 40);
        debitButton.setBackground(Color.WHITE);
        debitButton.setForeground(Color.red);
        debitButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(debitButton);

        // Exit Button
        exitButton = new JButton("HOME");
        exitButton.setBounds(380, 400, 100, 40);
        exitButton.setBackground(Color.WHITE);
        exitButton.setForeground(Color.BLUE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(exitButton);
        exitButton.addActionListener(e -> {
            this.dispose();
            new Home();
        });

        // Message Label for Feedback
        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        messageLabel.setBounds(300, 500, 400, 30);
        add(messageLabel);

        // Background Image
        ImageIcon k1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Debit_credit.jpeg"));
        Image k2 = k1.getImage().getScaledInstance(1000, 800, Image.SCALE_DEFAULT);
        ImageIcon k3 = new ImageIcon(k2);
        JLabel k_image = new JLabel(k3);
        k_image.setBounds(0, 0, 1000, 800);
        add(k_image);

        // Add Key Bindings for Enter Key
        addEnterKeyListener(amountField, noteField); // Move from amountField to noteField

        // Button Listeners
        creditButton.addActionListener(e -> processTransaction("Credit"));
        debitButton.addActionListener(e -> processTransaction("Debit"));
        exitButton.addActionListener(e -> exitToHome());

        // JFrame Settings
        setLayout(null);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to Process Transactions
    private void processTransaction(String type) {
        String amount = amountField.getText();
        String note = noteField.getText();

        if (amount.isEmpty() || note.isEmpty()) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Error: Please fill in all fields!");
            return;
        }

        try {
            double transactionAmount = Double.parseDouble(amount);

            // Get Current Date and Time
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // Save to File
            try (FileWriter writer = new FileWriter("creditordebit.txt", true)) {
                writer.write(type + ": " + transactionAmount + " | Note: " + note + " | Date: " + timeStamp + "\n");
            }

            messageLabel.setForeground(Color.GREEN);
            messageLabel.setText(type + " Successful: Amount: " + transactionAmount + ", Note: " + note + "\n");
            amountField.setText("");
            noteField.setText("");
        } catch (NumberFormatException ex) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Error: Amount must be a valid number!");
        } catch (IOException ex) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Error: Could not save the transaction!");
        }
    }

    // Method to Update Date and Time in the UI
    private void updateDateTime() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                dateTimeLabel.setText(currentDateTime);
            }
        }, 0, 1000); // Updates every second
    }

    // Method to Exit to Home
    private void exitToHome() {
        this.dispose(); // Close the current frame
        new Home();     // Open the Home class (Assumes you have a Home class)
    }

    // Method to Add Enter Key Listener
    private void addEnterKeyListener(JComponent currentComponent, JComponent nextComponent) {
        currentComponent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    nextComponent.requestFocus(); // Move focus to the next component
                }
            }
        });
    }

    public static void main(String[] args) {
        new CreditOrDebit();
    }
}
