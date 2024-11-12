package problem3.account;

import problem3.exception.InsufficientBalanceException;

public class DemandDepositAccount extends Account {
    public DemandDepositAccount(String accountNumber, String owner) {
        super(accountNumber, "자유입출금", owner, 0);
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (balance < amount) {
            throw new InsufficientBalanceException("잔액이 부족합니다.");
        }
        balance -= amount;
    }

    @Override
    public void transfer(Transferable targetAccount, double amount) throws InsufficientBalanceException {
        if (balance < amount) {
            throw new InsufficientBalanceException("잔액이 부족하여 이체할 수 없습니다.");
        }
        balance -= amount;
        targetAccount.deposit(amount);
    }

    @Override
    public void showAccount() {
        String balanceStr = String.format("%,d", Math.round(balance));
        System.out.printf("%s 통장 (계좌번호: %s, 잔액: %s원, 예금주:%s)\n",
                super.accountName, accountNumber, balanceStr, super.owner);
    }
}
