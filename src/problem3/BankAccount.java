package problem3;

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

class UnauthorizedTransferException extends Exception {
    public UnauthorizedTransferException(String message) {
        super(message);
    }
}

abstract class BankAccount {
    protected String accountNumber;
    protected String accountName;
    protected String owner;
    protected double balance;

    public BankAccount(String accountNumber, String accountName, String owner, double balance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.owner = owner;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public abstract void withdraw(double amount) throws InsufficientBalanceException, UnauthorizedTransferException;

    public abstract void transfer(BankAccount targetAccount, double amount) throws InsufficientBalanceException, UnauthorizedTransferException;

    public abstract void showAccount();
}
