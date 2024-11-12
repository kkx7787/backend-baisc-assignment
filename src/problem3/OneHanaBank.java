package problem3;
import problem3.account.DemandDepositAccount;
import problem3.account.FixedDepositAccount;
import problem3.account.OverdraftAccount;
import problem3.handler.AccountHandler;
import java.util.Scanner;

public class OneHanaBank {
    public static void main(String[] args) {
        DemandDepositAccount demandAccount = new DemandDepositAccount("1", "홍길동");
        FixedDepositAccount fixedAccount = new FixedDepositAccount("2", "홍길동", 50000000);
        OverdraftAccount overdraftAccount = new OverdraftAccount("3", "홍길동");

        Scanner scanner = new Scanner(System.in);
        String account;
        boolean isFixedAccountExpired = false;

        while (true) {
            System.out.print(">> 통장을 선택하세요(1: 자유입출금, ");
            if (!isFixedAccountExpired) {
                System.out.print("2: 정기예금, ");
            }
            System.out.print("3: 마이너스) ");
            account = scanner.nextLine().trim();

            if (account.isEmpty()) {
                System.out.println("금일 OneHanaBank는 업무를 종료합니다. 감사합니다.");
                break;
            }

            try {
                int accountChoice = Integer.parseInt(account);
                switch (accountChoice) {
                    case 1 -> {
                        demandAccount.showAccount();
                        AccountHandler.handleDemandDepositAccount(scanner, demandAccount, fixedAccount, overdraftAccount);
                    }
                    case 2 -> {
                        if (isFixedAccountExpired) {
                            System.out.println("정기예금 통장은 만기 처리되어 더 이상 선택할 수 없습니다.");
                        } else {
                            fixedAccount.showAccount();
                            isFixedAccountExpired = AccountHandler.handleFixedDepositAccount(scanner, demandAccount, fixedAccount, overdraftAccount);
                        }
                    }
                    case 3 -> {
                        overdraftAccount.showAccount();
                        AccountHandler.handleOverdraftAccount(scanner, demandAccount, fixedAccount, overdraftAccount);
                    }
                    default -> System.out.println("존재하는 계좌를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
        scanner.close();
    }
}