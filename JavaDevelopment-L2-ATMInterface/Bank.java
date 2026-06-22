package atm;

import java.util.HashMap;

public class Bank {

    private HashMap<String, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();

        accounts.put("1001", new Account("1001", "1234", 10000));
        accounts.put("1002", new Account("1002", "5678", 5000));
    }

    public Account getAccount(String userId) {
        return accounts.get(userId);
    }
}