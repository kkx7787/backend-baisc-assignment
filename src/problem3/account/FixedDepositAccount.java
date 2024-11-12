package problem3.account;
import problem3.exception.UnauthorizedTransferException;

public class FixedDepositAccount extends Account {
    public FixedDepositAccount(String accountNumber, String owner, double balance) {
        super(accountNumber, "정기예금", owner, balance);
    }

    @Override
    public void withdraw(double amount) throws UnauthorizedTransferException {
        throw new UnauthorizedTransferException("정기예금 계좌에서는 출금이 불가능합니다.");
    }

    @Override
    public void transfer(Transferable targetAccount, double amount) throws UnauthorizedTransferException {
        throw new UnauthorizedTransferException("정기예금 계좌에서는 이체가 불가능합니다.");
    }

    public void processFinalAmount(Account targetAccount, double interestRate) {
        double totalAmount = this.balance + this.balance * interestRate;
        targetAccount.deposit(totalAmount);
        this.balance = 0;
        String totalAmountStr = String.format("%,d", Math.round(totalAmount));
        System.out.printf("%s 통장에 %s원이 입금되었습니다.\n", targetAccount.accountName, totalAmountStr);
        System.out.println("정기예금 통장은 해지되었습니다. 감사합니다.");
    }

    @Override
    public void showAccount() {
        String balanceStr = String.format("%,d", Math.round(balance));
        System.out.printf("%s 통장 (계좌번호: %s, 예치금: %s원, 예금주:%s)\n", super.accountName, accountNumber, balanceStr, super.owner);
    }
}
