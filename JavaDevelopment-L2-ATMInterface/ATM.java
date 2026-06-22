package atm;

import java.util.ArrayList;
import java.util.Scanner;

public class ATM {

    private Account currentAccount;
    private Bank bank;
    private ArrayList<Transaction> history;

    public ATM(Bank bank) {
        this.bank = bank;
        history = new ArrayList<>();
    }

    public boolean login(Scanner sc) {

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("Enter User ID: ");
            String userId = sc.nextLine();

            System.out.print("Enter PIN: ");
            String pin = sc.nextLine();

            Account acc = bank.getAccount(userId);

            if (acc != null && acc.getPin().equals(pin)) {
                currentAccount = acc;
                System.out.println("\nLogin Successful!");
                return true;
            }

            attempts++;
            System.out.println("Invalid Credentials.");
            System.out.println("Attempts Left: " + (3 - attempts));
        }

        return false;
    }

    public void showMenu(Scanner sc) {

        while (true) {

            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.print("Choose Option: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    showHistory();
                    break;

                case 2:
                    withdraw(sc);
                    break;

                case 3:
                    deposit(sc);
                    break;

                case 4:
                    transfer(sc);
                    break;

                case 5:
                    System.out.println("Thank You For Using ATM.");
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    private void showHistory() {

        if (history.isEmpty()) {
            System.out.println("No Transactions Yet.");
            return;
        }

        System.out.println("\nTransaction History:");

        for (Transaction t : history) {
            System.out.println(t.getDetails());
        }
    }

    private void withdraw(Scanner sc) {

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        if (currentAccount.withdraw(amount)) {

            history.add(
                    new Transaction("Withdraw : Rs." + amount));

            System.out.println("Withdrawal Successful.");
            System.out.println("Balance: Rs."
                    + currentAccount.getBalance());

        } else {
            System.out.println("Insufficient Funds.");
        }
    }

    private void deposit(Scanner sc) {

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        currentAccount.deposit(amount);

        history.add(
                new Transaction("Deposit : Rs." + amount));

        System.out.println("Deposit Successful.");
        System.out.println("Balance: Rs."
                + currentAccount.getBalance());
    }

    private void transfer(Scanner sc) {

        sc.nextLine();

        System.out.print("Enter Recipient Account ID: ");
        String recipientId = sc.nextLine();

        Account receiver = bank.getAccount(recipientId);

        if (receiver == null) {
            System.out.println("Recipient Not Found.");
            return;
        }

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        if (currentAccount.withdraw(amount)) {

            receiver.deposit(amount);

            history.add(new Transaction(
                    "Transferred Rs." + amount
                            + " to Account "
                            + recipientId));

            System.out.println("Transfer Successful.");
            System.out.println("Balance: Rs."
                    + currentAccount.getBalance());

        } else {
            System.out.println("Insufficient Funds.");
        }
    }
}