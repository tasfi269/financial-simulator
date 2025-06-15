package financial.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Login extends JFrame implements ActionListener {

    JLabel label1, label2, label3;
    JTextField textfield2;
    JPasswordField pinfield3;
    JButton button1, button2, button3;

    Login() {
        super("Financial Simulator");

        // Set up icon images and layout as in your code
        label1 = new JLabel("WELCOME TO HISHAB KITAB");
        label1.setForeground(Color.white);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 30));
        label1.setBounds(200, 125, 450, 40);
        add(label1);

        label2 = new JLabel("User ID: ");
        label2.setForeground(Color.white);
        label2.setFont(new Font("Ralway", Font.BOLD, 28));
        label2.setBounds(150, 190, 375, 30);
        add(label2);

        textfield2 = new JTextField(15);
        textfield2.setBounds(325, 190, 230, 30);
        textfield2.setFont(new Font("Arial", Font.BOLD, 14));
        add(textfield2);

        label3 = new JLabel("PIN: ");
        label3.setForeground(Color.white);
        label3.setFont(new Font("Ralway", Font.BOLD, 28));
        label3.setBounds(150, 250, 375, 30);
        add(label3);

        pinfield3 = new JPasswordField(15);
        pinfield3.setBounds(325, 250, 230, 30);
        pinfield3.setFont(new Font("Arial", Font.BOLD, 14));
        add(pinfield3);

        button1 = new JButton("LOG IN");
        button1.setFont(new Font("Arial", Font.BOLD, 14));
        button1.setForeground(Color.BLACK);
        button1.setBounds(300, 300, 100, 30);
        button1.addActionListener(this);
        add(button1);

        button2 = new JButton("CLEAR");
        button2.setFont(new Font("Arial", Font.BOLD, 14));
        button2.setForeground(Color.BLACK);
        button2.setBounds(430, 300, 100, 30);
        button2.addActionListener(this);
        add(button2);

        button3 = new JButton("SIGN UP");
        button3.setFont(new Font("Arial", Font.BOLD, 14));
        button3.setForeground(Color.BLACK);
        button3.setBounds(300, 350, 230, 30);
        button3.addActionListener(this);
        add(button3);

        textfield2.addActionListener(e -> pinfield3.requestFocus()); // Move focus to PIN field
        pinfield3.addActionListener(e -> button1.doClick()); // Simulate Login button click

        button3.addActionListener(e -> {
            this.dispose();
            new SignUp();
        });

        ImageIcon i1 = new ImageIcon (ClassLoader.getSystemResource("Icon/Icon.png"));
        Image i2 = i1.getImage().getScaledInstance(120,120,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350,10,120,120);
        add(image);

        ImageIcon j1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Taka.png"));
        Image j2 = j1.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT);
        ImageIcon j3 = new ImageIcon(j2);
        JLabel j_image = new JLabel(j3);
        j_image.setBounds(650,300,200,200);
        add(j_image);

        ImageIcon k1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Background.jpeg"));
        Image k2 = k1.getImage().getScaledInstance(850,480,Image.SCALE_DEFAULT);
        ImageIcon k3 = new ImageIcon(k2);
        JLabel k_image = new JLabel(k3);
        k_image.setBounds(0,0,850,480);
        add(k_image);

        setLayout(null);
        setSize(850, 480);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) { // LOG IN
            String username = textfield2.getText();
            String pin = new String(pinfield3.getPassword());
            boolean found = false;

            // Read credentials from file
            try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Assuming the format "Username: <username>, Password: <password>, Email: <email>"
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String storedUsername = parts[0].split(": ")[1].trim();
                        String storedPassword = parts[1].split(": ")[1].trim();

                        if (storedUsername.equals(username) && storedPassword.equals(pin)) {
                            found = true;
                            break;
                        }
                    }
                }

                if (found) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    this.dispose();
                    new Home();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or PIN.");
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error reading user data.");
            }

        } else if (e.getSource() == button2) {
            textfield2.setText("");
            pinfield3.setText("");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
