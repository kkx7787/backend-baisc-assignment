package problem3.account;

public class OverdraftAccount extends Account {
    public OverdraftAccount(String accountNumber, String owner) {
        super(accountNumber, "마이너스", owner, 0);
    }

    @Override
    public void withdraw(double amount) {
        balance -= amount;
    }

    @Override
    public void transfer(Transferable targetAccount, double amount) {
        balance -= amount;
        targetAccount.deposit(amount);
    }

    @Override
    public void showAccount() {
        String balanceStr = String.format("%,d", Math.round(balance));
        System.out.printf("%s 통장 - 잔액 : %s원\n", super.accountName, balanceStr);
    }
}
