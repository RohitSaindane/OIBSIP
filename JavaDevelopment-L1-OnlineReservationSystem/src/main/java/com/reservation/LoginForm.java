package com.reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm extends JFrame implements ActionListener {

    JLabel lblTitle, lblUser, lblPass;
    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin;

    public LoginForm() {

        setTitle("Online Reservation System - Login");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTitle = new JLabel("LOGIN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(150, 20, 100, 30);

        lblUser = new JLabel("Username:");
        lblUser.setBounds(50, 80, 100, 25);

        txtUser = new JTextField();
        txtUser.setBounds(150, 80, 150, 25);

        lblPass = new JLabel("Password:");
        lblPass.setBounds(50, 130, 100, 25);

        txtPass = new JPasswordField();
        txtPass.setBounds(150, 130, 150, 25);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(140, 190, 100, 30);
        btnLogin.addActionListener(this);

        add(lblTitle);
        add(lblUser);
        add(txtUser);
        add(lblPass);
        add(txtPass);
        add(btnLogin);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String username = txtUser.getText();
        String password = String.valueOf(txtPass.getPassword());

        if(username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter username and password");
            return;
        }

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT * FROM users WHERE username=? AND password=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                JOptionPane.showMessageDialog(this,
                        "Login Successful");

                dispose();

                new ReservationForm();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Invalid Username or Password");

            }

            con.close();

        } catch(Exception ex) {

            ex.printStackTrace();

        }
    }
}