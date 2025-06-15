package financial.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SignUp extends JFrame {
    SignUp() {
        super("SIGN UP");

        // Set up the icon image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Icon.png"));
        Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(25, 10, 120, 120);
        add(image);

        // Sign up label
        JLabel label1 = new JLabel("SIGN UP");
        label1.setForeground(Color.white);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 30));
        label1.setBounds(350, 50, 450, 40);
        add(label1);

        // Username label and text field
        JLabel label2 = new JLabel("Enter User Name: ");
        label2.setForeground(Color.white);
        label2.setFont(new Font("Ralway", Font.BOLD, 22));
        label2.setBounds(120, 190, 375, 30);
        add(label2);

        JTextField textfield2 = new JTextField(30);
        textfield2.setBounds(320, 195, 200, 25);
        textfield2.setFont(new Font("Arial", Font.BOLD, 14));
        add(textfield2);

        // Password label and field
        JLabel label3 = new JLabel("Enter New Password: ");
        label3.setForeground(Color.white);
        label3.setFont(new Font("Ralway", Font.BOLD, 22));
        label3.setBounds(120, 240, 375, 30);
        add(label3);

        JPasswordField pinfield3 = new JPasswordField(15);
        pinfield3.setBounds(365, 245, 200, 25);
        pinfield3.setFont(new Font("Arial", Font.BOLD, 14));
        add(pinfield3);

        // Re-enter password label and field
        JLabel label4 = new JLabel("Re-enter New Password: ");
        label4.setForeground(Color.white);
        label4.setFont(new Font("Ralway", Font.BOLD, 22));
        label4.setBounds(120, 290, 375, 30);
        add(label4);

        JPasswordField pinfield4 = new JPasswordField(15);
        pinfield4.setBounds(400, 295, 200, 25);
        pinfield4.setFont(new Font("Arial", Font.BOLD, 14));
        add(pinfield4);

        // Email label and text field
        JLabel label5 = new JLabel("Enter Email: ");
        label5.setForeground(Color.white);
        label5.setFont(new Font("Ralway", Font.BOLD, 22));
        label5.setBounds(120, 340, 375, 30);
        add(label5);

        JTextField textfield5 = new JTextField(30);
        textfield5.setBounds(265, 345, 200, 25);
        textfield5.setFont(new Font("Arial", Font.BOLD, 14));
        add(textfield5);

        // Sign up button with ActionListener
        JButton button3 = new JButton("SIGN UP");
        button3.setFont(new Font("Arial", Font.BOLD, 14));
        button3.setForeground(Color.BLACK);
        button3.setBounds(300, 400, 230, 30);
        add(button3);

        JButton button4 = new JButton("BACK");
        button4.setFont(new Font("Arial", Font.BOLD, 14));
        button4.setForeground(Color.BLUE);
        button4.setBounds(650, 400, 150, 30);
        add(button4);
        button4.addActionListener(e -> {
            this.dispose();
            new Login();
        });

        ActionListener signUpAction = e -> {
            String name = textfield2.getText();
            String password = new String(pinfield3.getPassword());
            String confirmPassword = new String(pinfield4.getPassword());
            String email = textfield5.getText();

            if (name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required");
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match");
            } else {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt", true))) {
                    writer.write("Username: " + name + ", Password: " + password + ", Email: " + email);
                    writer.newLine();
                    JOptionPane.showMessageDialog(null, "Sign up successful for user: " + name);

                    dispose();
                    new Login();

                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "Error saving user information");
                    ioException.printStackTrace();
                }
            }
        };

        button3.addActionListener(signUpAction);

        // Add Enter key navigation
        textfield2.addActionListener(e -> pinfield3.requestFocus());
        pinfield3.addActionListener(e -> pinfield4.requestFocus());
        pinfield4.addActionListener(e -> textfield5.requestFocus());
        textfield5.addActionListener(signUpAction);

        ImageIcon k1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Background.jpeg"));
        Image k2 = k1.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon k3 = new ImageIcon(k2);
        JLabel k_image = new JLabel(k3);
        k_image.setBounds(0, 0, 850, 480);
        add(k_image);

        // Frame settings
        setLayout(null);
        setSize(850, 515);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SignUp();
    }
}
