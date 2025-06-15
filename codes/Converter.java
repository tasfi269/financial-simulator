package financial.management.system;

import javax.swing.*;
import java.awt.*;

public class Converter extends JFrame {

    JLabel label1;
    JTextField amountField, resultfield;
    JComboBox<String> fromCurrency, toCurrency;
    JButton convertButton, homeButton;
    ImageIcon k1;

    // Constructor for the Converter class
    Converter() {
        super("Converter");

        // Set the layout of the JFrame to null for absolute positioning
        setLayout(null);

        // Icon Setup
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Icon.png"));
        Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(25, 10, 120, 120);
        add(image);

        // Label Setup
        label1 = new JLabel("* MONEY CONVERTER *");
        label1.setForeground(Color.ORANGE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 35));
        label1.setBounds(350, 40, 450, 40);
        add(label1);
        label1.setVisible(true);

        // Amount Input Field
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(300, 200, 200, 40);
        amountLabel.setFont(new Font("Arial", Font.BOLD, 22));
        amountLabel.setForeground(Color.ORANGE);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(400, 200, 200, 40);
        add(amountField);

        // From Currency ComboBox
        JLabel fromLabel = new JLabel("From:");
        fromLabel.setBounds(300, 250, 200, 40);
        fromLabel.setFont(new Font("Arial", Font.BOLD, 22));
        fromLabel.setForeground(Color.ORANGE);
        add(fromLabel);

        fromCurrency = new JComboBox<>(new String[]{"USD", "EUR", "BDT"});
        fromCurrency.setBounds(380, 250, 200, 40);
        add(fromCurrency);

        // To Currency ComboBox
        JLabel toLabel = new JLabel("To:");
        toLabel.setBounds(300, 300, 200, 40);
        toLabel.setFont(new Font("Arial", Font.BOLD, 22));
        toLabel.setForeground(Color.ORANGE);
        add(toLabel);

        toCurrency = new JComboBox<>(new String[]{"USD", "EUR", "BDT"});
        toCurrency.setBounds(350, 300, 200, 40);
        add(toCurrency);

        // Convert Button
        convertButton = new JButton("Convert");
        convertButton.setFont(new Font("Arial", Font.BOLD, 22));
        convertButton.setBounds(360, 370, 250, 40);
        add(convertButton);

        // Result Label
        resultfield = new JTextField("Result: ");
        resultfield.setFont(new Font("Arial", Font.BOLD, 20));
        resultfield.setBounds(290, 480, 400, 45);
        add(resultfield);

        homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 22));
        homeButton.setForeground(Color.BLUE);
        homeButton.setBounds(350, 600, 300, 50);
        add(homeButton);
        homeButton.addActionListener(e -> {
            this.dispose(); // Close the current frame
            new Home(); // Open the Home frame
        });

        // Background Image Setup
        k1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Debit_credit.jpeg"));
        Image k2 = k1.getImage().getScaledInstance(1000, 800, Image.SCALE_DEFAULT);
        ImageIcon k3 = new ImageIcon(k2);
        JLabel k_image = new JLabel(k3);
        k_image.setBounds(0, 0, 1000, 800);
        add(k_image);

        // JFrame Setup
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Action Listener for Convert Button
        convertButton.addActionListener(e -> performConversion());

        // Action Listener for Enter key in Amount Field
        amountField.addActionListener(e -> performConversion());
    }

    // Method to perform the conversion
    private void performConversion() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String from = (String) fromCurrency.getSelectedItem();
            String to = (String) toCurrency.getSelectedItem();

            assert from != null;
            double conversionRate = getConversionRate(from, to);
            double result = amount * conversionRate;

            resultfield.setText("Result: " + result + " " + to);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount. Amount should be a number.");
        }
    }

    // Method to get the conversion rate (hardcoded for simplicity)
    private double getConversionRate(String from, String to) {
        if (from.equals("USD") && to.equals("EUR")) {
            return 0.95;
        } else if (from.equals("USD") && to.equals("BDT")) {
            return 119.49;
        } else if (from.equals("EUR") && to.equals("USD")) {
            return 1.18;
        } else if (from.equals("EUR") && to.equals("BDT")) {
            return 125.75;
        } else if (from.equals("BDT") && to.equals("USD")) {
            return 0.0084;
        } else if (from.equals("BDT") && to.equals("EUR")) {
            return 0.0080;
        } else {
            return 1.0; // Default case if from and to currencies are the same
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        new Converter();
    }
}
