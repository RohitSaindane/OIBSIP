package com.reservation;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

public class ReservationForm extends JFrame implements ActionListener {

    JLabel lblPassenger, lblTrainNo, lblTrainName,
            lblClass, lblDate, lblSource, lblDestination;

    JTextField txtPassenger, txtTrainNo,
            txtTrainName, txtDate,
            txtSource, txtDestination;

    JComboBox<String> cmbClass;

    JButton btnBook, btnCancel;

    public ReservationForm() {

        setTitle("Reservation Form");
        setSize(600,500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblPassenger = new JLabel("Passenger Name");
        lblPassenger.setBounds(50,50,120,25);

        txtPassenger = new JTextField();
        txtPassenger.setBounds(200,50,200,25);

        lblTrainNo = new JLabel("Train Number");
        lblTrainNo.setBounds(50,90,120,25);

        txtTrainNo = new JTextField();
        txtTrainNo.setBounds(200,90,200,25);

        lblTrainName = new JLabel("Train Name");
        lblTrainName.setBounds(50,130,120,25);

        txtTrainName = new JTextField();
        txtTrainName.setBounds(200,130,200,25);

        lblClass = new JLabel("Class Type");
        lblClass.setBounds(50,170,120,25);

        String classes[] =
                {"Sleeper","AC","First Class"};

        cmbClass = new JComboBox<>(classes);
        cmbClass.setBounds(200,170,200,25);

        lblDate = new JLabel("Journey Date");
        lblDate.setBounds(50,210,120,25);

        txtDate = new JTextField();
        txtDate.setBounds(200,210,200,25);

        lblSource = new JLabel("Source");
        lblSource.setBounds(50,250,120,25);

        txtSource = new JTextField();
        txtSource.setBounds(200,250,200,25);

        lblDestination = new JLabel("Destination");
        lblDestination.setBounds(50,290,120,25);

        txtDestination = new JTextField();
        txtDestination.setBounds(200,290,200,25);

        btnBook = new JButton("Book Ticket");
        btnBook.setBounds(120,370,130,35);

        btnCancel = new JButton("Cancel Ticket");
        btnCancel.setBounds(280,370,130,35);

        btnBook.addActionListener(this);

        btnCancel.addActionListener(e -> {

            dispose();
            new CancellationForm();

        });

        add(lblPassenger);
        add(txtPassenger);

        add(lblTrainNo);
        add(txtTrainNo);

        add(lblTrainName);
        add(txtTrainName);

        add(lblClass);
        add(cmbClass);

        add(lblDate);
        add(txtDate);

        add(lblSource);
        add(txtSource);

        add(lblDestination);
        add(txtDestination);

        add(btnBook);
        add(btnCancel);

        setVisible(true);

        trainAutoFill();
    }

    private void trainAutoFill() {

        txtTrainNo.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {

                String trainNo =
                        txtTrainNo.getText();

                switch(trainNo) {

                    case "12123":
                        txtTrainName.setText(
                                "Deccan Queen");
                        break;

                    case "11007":
                        txtTrainName.setText(
                                "Deccan Express");
                        break;

                    case "12127":
                        txtTrainName.setText(
                                "Intercity Express");
                        break;

                    default:
                        txtTrainName.setText("");
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String passenger = txtPassenger.getText();

        String trainNo = txtTrainNo.getText();

        String trainName = txtTrainName.getText();

        String classType =
                cmbClass.getSelectedItem().toString();

        String date = txtDate.getText();

        String source = txtSource.getText();

        String destination =
                txtDestination.getText();

        // Empty Field Validation
        if(passenger.isEmpty() ||
                trainNo.isEmpty() ||
                date.isEmpty() ||
                source.isEmpty() ||
                destination.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "All Fields Required");

            return;
        }

        // Train Number Validation
        try {

            Integer.parseInt(trainNo);

        } catch(Exception ex) {

            JOptionPane.showMessageDialog(this,
                    "Train Number Must Be Numeric");

            return;
        }

        // Date Validation
        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd");

        sdf.setLenient(false);

        try {

            sdf.parse(date);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    "Date must be in yyyy-MM-dd format");

            return;
        }

        // Train Number Check
        if(trainName.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Invalid Train Number");

            return;
        }

        String pnr =
                PNRGenerator.generatePNR();

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "INSERT INTO reservations VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, pnr);
            ps.setString(2, passenger);
            ps.setInt(3, Integer.parseInt(trainNo));
            ps.setString(4, trainName);
            ps.setString(5, classType);
            ps.setString(6, date);
            ps.setString(7, source);
            ps.setString(8, destination);

            int result = ps.executeUpdate();

            if(result > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking Successful\n\n"
                                + "PNR : " + pnr
                                + "\nPassenger : " + passenger
                                + "\nTrain : " + trainName
                                + "\nDate : " + date
                );

                txtPassenger.setText("");
                txtTrainNo.setText("");
                txtTrainName.setText("");
                txtDate.setText("");
                txtSource.setText("");
                txtDestination.setText("");
            }

            con.close();

        } catch(Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Error While Booking Ticket");
        }
    }
    
}