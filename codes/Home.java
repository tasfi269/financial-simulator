package financial.management.system;

import javax.swing.*;
import java.awt.*;

public class Home extends JFrame{

    JLabel label1;
    JButton button1, button2, button3, button4, button5, button6, button7;

    Home(){
        super("Home");

        //GUI
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Icon.png"));
        Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(25, 10, 120, 120);
        add(image);

        label1 = new JLabel("HOME");
        label1.setForeground(Color.ORANGE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 35));
        label1.setBounds(450, 40, 450, 40);
        add(label1);

        //BALANCE OVERVIEW Button - goes to BalanceOverview Class
        button1 = new JButton("BALANCE OVERVIEW");
        button1.setFont(new Font("Arial", Font.BOLD, 14));
        button1.setForeground(Color.BLACK);
        button1.setBounds(350, 200, 300, 40);
        add(button1);
        button1.addActionListener(e -> {
            this.dispose();
            new BalanceOverview();
        });

        //TRANSACTIONS Button - goes to Transaction Class
        button2 = new JButton("TRANSACTIONS");
        button2.setFont(new Font("Arial", Font.BOLD, 14));
        button2.setForeground(Color.BLACK);
        button2.setBounds(350, 260, 300, 40);
        add(button2);
        button2.addActionListener(e -> {
            this.dispose();
            new Transaction();
        });

        //CREADIT/DEBIT Button - goes to CreditOrDebit Class
        button3 = new JButton("CREADIT/DEBIT");
        button3.setFont(new Font("Arial", Font.BOLD, 14));
        button3.setForeground(Color.BLACK);
        button3.setBounds(350, 320, 300, 40);
        add(button3);
        button3.addActionListener(e -> {
            this.dispose();
            new CreditOrDebit();
        });


        //RECEIVABLE Button - goes to Receivable Class
        button4 = new JButton("RECEIVABLE");
        button4.setFont(new Font("Arial", Font.BOLD, 14));
        button4.setForeground(Color.BLACK);
        button4.setBounds(350, 380, 300, 40);
        add(button4);
        button4.addActionListener(e -> {
            this.dispose();
            new Receivable();
        });

        //PAYABLE Button - goes to Payable Class
        button5 = new JButton("PAYABLE");
        button5.setFont(new Font("Arial", Font.BOLD, 14));
        button5.setForeground(Color.BLACK);
        button5.setBounds(350, 440, 300, 40);
        add(button5);
        button5.addActionListener(e -> {
            this.dispose();
            new Payable();
        });

        //CURRENCY CONVERTER Button - goes to Converter Class
        button6 = new JButton("CURRENCY CONVERTER");
        button6.setFont(new Font("Arial", Font.BOLD, 14));
        button6.setForeground(Color.BLACK);
        button6.setBounds(350, 500, 300, 40);
        add(button6);
        button6.addActionListener(e -> {
            this.dispose();
            new Converter();
        });


        //Exit Button - (Closes the window)
        button7 = new JButton("EXIT");
        button7.setFont(new Font("Arial", Font.BOLD, 16));
        button7.setForeground(Color.RED);
        button7.setBounds(700, 550, 150, 50);
        add(button7);
        button7.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Exit Successful.");
            this.dispose();
        });


        //GUI
        ImageIcon k1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Home Background.jpg"));
        Image k2 = k1.getImage().getScaledInstance(1000,700,Image.SCALE_DEFAULT);
        ImageIcon k3 = new ImageIcon(k2);
        JLabel k_image = new JLabel(k3);
        k_image.setBounds(0,0,1000,700);
        add(k_image);

        //Set Settings
        setLayout(null);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Home();
    }
}
