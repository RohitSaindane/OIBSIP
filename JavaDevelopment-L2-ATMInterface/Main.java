package atm;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Bank bank = new Bank();
        ATM atm = new ATM(bank);

        System.out.println("===== ATM INTERFACE =====");

        if (atm.login(sc)) {
            atm.showMenu(sc);
        } else {
            System.out.println("Account Locked.");
        }

        sc.close();
    }
}