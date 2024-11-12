package problem3.account;
import problem3.exception.*;

public interface Transferable {
    double getBalance();

    void deposit(double amount);

    void withdraw(double amount) throws InsufficientBalanceException, UnauthorizedTransferException;

    void transfer(Transferable transferable, double amount) throws InsufficientBalanceException, UnauthorizedTransferException;

    void showAccount();
}
