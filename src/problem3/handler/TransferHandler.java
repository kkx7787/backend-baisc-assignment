package problem3.handler;
import problem3.account.Account;
import problem3.account.DemandDepositAccount;
import problem3.account.FixedDepositAccount;
import problem3.account.OverdraftAccount;
import problem3.exception.InsufficientBalanceException;
import problem3.exception.UnauthorizedTransferException;
import java.util.Scanner;

public class TransferHandler {
    public static void deposit(Scanner scanner, Account account) {
        System.out.print("입금 하실 금액은? ");
        double depositAmount = Double.parseDouble(scanner.nextLine());
        if (depositAmount == 0) return;
        account.deposit(depositAmount);
        String depositAmountStr = String.format("%,d", Math.round(depositAmount));
        System.out.printf("%s 통장에 %s원이 입금되었습니다!\n", account.getAccountName(), depositAmountStr);
    }

    public static void withdraw(Scanner scanner, Account account) {
        while (true) {
            System.out.print("출금 하실 금액은? ");
            double withdrawAmount = Double.parseDouble(scanner.nextLine());
            if (withdrawAmount == 0) break;
            try {
                account.withdraw(withdrawAmount);
                String withdrawAmountStr = String.format("%,d", Math.round(withdrawAmount));
                String balanceAmountStr = String.format("%,d", Math.round(account.getBalance()));
                System.out.printf("%s 통장에서 %s원이 출금되었습니다.\n", account.getAccountName(), withdrawAmountStr);
                System.out.printf("%s 통장의 잔액은 %s원입니다.\n", account.getAccountName(), balanceAmountStr);
                break;
            } catch (InsufficientBalanceException | UnauthorizedTransferException e) {
                String balanceStr = String.format("%,d", Math.round(account.getBalance()));
                System.out.printf("잔액이 부족합니다! (잔액: %s원)\n", balanceStr);
            }
        }
    }

    public static void transfer(Scanner scanner, Account fromAccount, Account toAccount1, Account toAccount2) {
        Account toAccount;
        String transferPrompt;

        int fromAccountType = fromAccount.getAccountName().equalsIgnoreCase("자유입출금") ? 1 : 3;
        if (fromAccountType == 1) {
            System.out.print("어디로 보낼까요? (2: 정기예금, 3: 마이너스) ");
            int toAccountType = Integer.parseInt(scanner.nextLine());
            if (toAccountType == 2) {
                toAccount = toAccount1;
                transferPrompt = "정기예금 통장에 보낼 금액은? ";
            } else if (toAccountType == 3) {
                toAccount = toAccount2;
                transferPrompt = "마이너스 통장에 보낼 금액은? ";
            } else {
                System.out.println("올바른 계좌를 선택해주세요.");
                return;
            }
        } else {
            System.out.print("어디로 보낼까요? (1: 자유입출금, 2: 정기예금) ");
            int toAccountType = Integer.parseInt(scanner.nextLine());
            if (toAccountType == 1) {
                toAccount = toAccount1;
                transferPrompt = "자유입출금 통장에 보낼 금액은? ";
            } else if (toAccountType == 3) {
                toAccount = toAccount2;
                transferPrompt = "정기예금 통장에 보낼 금액은? ";
            } else {
                System.out.println("올바른 계좌를 선택해주세요.");
                return;
            }
        }

        while (true) {
            System.out.print(transferPrompt);
            double transferAmount = Double.parseDouble(scanner.nextLine());

            if (transferAmount == 0) {
                System.out.println("이체가 취소되었습니다.");
                break;
            }

            try {
                fromAccount.transfer(toAccount, transferAmount);
                String transferAmountStr = String.format("%,d", Math.round(transferAmount));
                String balanceStr = String.format("%,d", Math.round(fromAccount.getBalance()));
                System.out.printf("%s 통장에 %s원이 입금되었습니다.\n", toAccount.getAccountName(), transferAmountStr);
                System.out.printf("%s 통장의 잔액은 %s원 입니다.\n", fromAccount.getAccountName(), balanceStr);
                break;
            } catch (InsufficientBalanceException e) {
                String balanceStr = String.format("%,d", Math.round(fromAccount.getBalance()));
                System.out.printf("잔액이 부족합니다! (잔액: %s원)\n", balanceStr);
            } catch (NumberFormatException | UnauthorizedTransferException e) {
                System.out.println("올바른 금액을 입력해 주세요.");
            }
        }
    }

    public static int getDepositMonths(Scanner scanner) {
        while (true) {
            System.out.print("예치 개월 수를 입력하세요? (1 ~ 60개월) ");
            int month = scanner.nextInt();
            scanner.nextLine();
            if (month >= 1 && month <= 60) return month;
            else if (month == 0) return 0;
            System.out.println("올바른 개월 수를 입력해주세요.");
        }
    }

    public static boolean processMaturity(Scanner scanner, FixedDepositAccount fixedAccount,
                                          DemandDepositAccount demandAccount, OverdraftAccount overdraftAccount,
                                          int month, double interestRate) {
        while (true) {
            System.out.printf("%d개월(적용 금리 %.2f%%)로 만기 처리하시겠어요? (y/n): ", month, interestRate * 100);
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("N") || choice.equalsIgnoreCase("0")) return false;
            if (choice.equalsIgnoreCase("Y")) {
                System.out.print("어디로 보낼까요? (1: 자유입출금, 3: 마이너스): ");
                int targetAccount = scanner.nextInt();
                scanner.nextLine();

                if (targetAccount == 1) {
                    fixedAccount.processFinalAmount(demandAccount, interestRate);
                    return true;
                } else if (targetAccount == 3) {
                    fixedAccount.processFinalAmount(overdraftAccount, interestRate);
                    return true;
                } else {
                    System.out.println("올바른 계좌를 선택해주세요.");
                }
            } else {
                System.out.println("올바른 선택을 해주세요.");
            }
        }
    }

    public static double getInterestRate(int month) {
        if (month >= 1 && month <= 2) return 0.03;
        if (month >= 3 && month <= 5 || month == 9 || month == 12) return 0.0335;
        if (month >= 6 && month <= 8) return 0.034;
        if (month >= 24) return 0.029;
        return 0.03;
    }
}
