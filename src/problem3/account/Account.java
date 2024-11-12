package problem3.account;
import problem3.exception.*;

public abstract class Account implements Transferable {
    protected String accountNumber;
    protected String accountName;
    protected String owner;
    protected double balance;

    public Account(String accountNumber, String accountName, String owner, double balance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.owner = owner;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public abstract void withdraw(double amount) throws InsufficientBalanceException, UnauthorizedTransferException;

    public abstract void transfer(Transferable targetAccount, double amount) throws InsufficientBalanceException, UnauthorizedTransferException;

    public abstract void showAccount();
}
