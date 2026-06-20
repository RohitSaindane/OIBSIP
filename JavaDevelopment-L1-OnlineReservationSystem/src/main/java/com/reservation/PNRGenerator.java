package com.reservation;

public class PNRGenerator {

    public static String generatePNR() {

        return "PNR" + System.currentTimeMillis();

    }

}