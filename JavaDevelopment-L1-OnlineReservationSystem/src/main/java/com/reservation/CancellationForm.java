package com.reservation;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class CancellationForm extends JFrame implements ActionListener {

    JLabel lblPNR, lblDetails;

    JTextField txtPNR;

    JTextArea txtDetails;

    JButton btnFetch, btnCancel;

    String currentPNR = "";

    public CancellationForm() {

        setTitle("Ticket Cancellation");
        setSize(600,500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblPNR = new JLabel("Enter PNR Number");
        lblPNR.setBounds(50,50,150,25);

        txtPNR = new JTextField();
        txtPNR.setBounds(220,50,200,25);

        btnFetch = new JButton("Fetch");
        btnFetch.setBounds(440,50,100,25);

        lblDetails = new JLabel("Booking Details");
        lblDetails.setBounds(50,100,150,25);

        txtDetails = new JTextArea();
        txtDetails.setEditable(false);

        JScrollPane sp =
                new JScrollPane(txtDetails);

        sp.setBounds(50,130,490,220);

        btnCancel =
                new JButton("Confirm Cancellation");

        btnCancel.setBounds(180,380,220,35);

        btnFetch.addActionListener(this);
        btnCancel.addActionListener(this);

        add(lblPNR);
        add(txtPNR);
        add(btnFetch);

        add(lblDetails);
        add(sp);

        add(btnCancel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btnFetch) {

            fetchBooking();

        }

        if(e.getSource() == btnCancel) {

            cancelBooking();

        }
    }

    private void fetchBooking() {

        String pnr = txtPNR.getText();

        if(pnr.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Enter PNR Number");

            return;
        }

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM reservations WHERE pnr=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1,pnr);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                currentPNR = pnr;

                txtDetails.setText(
                        "PNR : " + rs.getString("pnr")
                        + "\nPassenger : "
                        + rs.getString("passenger_name")

                        + "\nTrain No : "
                        + rs.getInt("train_no")

                        + "\nTrain Name : "
                        + rs.getString("train_name")

                        + "\nClass : "
                        + rs.getString("class_type")

                        + "\nJourney Date : "
                        + rs.getString("journey_date")

                        + "\nSource : "
                        + rs.getString("source_station")

                        + "\nDestination : "
                        + rs.getString("destination_station")
                );

            } else {

                JOptionPane.showMessageDialog(this,
                        "PNR Not Found");

                txtDetails.setText("");
            }

            con.close();

        } catch(Exception ex) {

            ex.printStackTrace();
        }
    }

    private void cancelBooking() {

        if(currentPNR.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Fetch Booking First");

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to cancel this booking?",
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION
                );

        if(choice == JOptionPane.YES_OPTION) {

            try {

                Connection con =
                        DBConnection.getConnection();

                String sql =
                        "DELETE FROM reservations WHERE pnr=?";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setString(1,currentPNR);

                int rows =
                        ps.executeUpdate();

                if(rows > 0) {

                    JOptionPane.showMessageDialog(this,
                            "Ticket Cancelled Successfully");

                    txtDetails.setText("");
                    txtPNR.setText("");

                    currentPNR = "";
                }

                con.close();

            } catch(Exception ex) {

                ex.printStackTrace();
            }
        }
    }
}