package problem3.handler;
import problem3.account.DemandDepositAccount;
import problem3.account.FixedDepositAccount;
import problem3.account.OverdraftAccount;
import java.util.Scanner;

public class AccountHandler {
    public static void handleDemandDepositAccount(Scanner scanner, DemandDepositAccount demandAccount, FixedDepositAccount fixedAccount, OverdraftAccount overdraftAccount) {
        String work;
        do {
            System.out.print("> 원하시는 업무는? (+:입금, -:출금, T:이체, I:정보) ");
            work = scanner.nextLine();
            if (work.equals("0")) break;
            switch (work) {
                case "+" -> TransferHandler.deposit(scanner, demandAccount);
                case "-" -> TransferHandler.withdraw(scanner, demandAccount);
                case "T" -> TransferHandler.transfer(scanner, demandAccount, fixedAccount, overdraftAccount);
                case "I" -> demandAccount.showAccount();
                default -> System.out.println("올바른 작업을 입력하세요.");
            }
        } while (!work.equalsIgnoreCase("0"));
    }

    public static boolean handleFixedDepositAccount(Scanner scanner, DemandDepositAccount demandAccount,
                                                    FixedDepositAccount fixedAccount, OverdraftAccount overdraftAccount) {
        String work;
        do {
            System.out.print("> 정기 예금이 만기되었습니다. (+:만기처리, -:인출, T:이체, I:정보) ");
            work = scanner.nextLine();
            if (work.equals("0")) return false;

            switch (work) {
                case "+" -> {
                    while (true) {
                        int month = TransferHandler.getDepositMonths(scanner);
                        if (month == 0) break;

                        double interestRate = TransferHandler.getInterestRate(month);
                        boolean isMatured = TransferHandler.processMaturity(scanner, fixedAccount, demandAccount, overdraftAccount, month, interestRate);

                        if (isMatured) {
                            return true;
                        }
                    }
                }
                case "-" -> System.out.println("출금할 수 없는 통장입니다.");
                case "T" -> System.out.println("이체할 수 없는 통장입니다.");
                case "I" -> {
                    fixedAccount.showAccount();
                    System.out.println("* 예치 개월에 따른 적용 금리");
                    System.out.println("1개월 이상\t 3.0%");
                    System.out.println("3개월 이상\t 3.35%");
                    System.out.println("6개월 이상\t 3.4%");
                    System.out.println("9개월 이상\t 3.35%");
                    System.out.println("12개월 이상\t 3.35%");
                    System.out.println("24개월 이상\t 2.9%");
                    System.out.println("36개월 이상\t 2.9%");
                    System.out.println("72개월 이상\t 2.9%");
                }
                default -> System.out.println("올바른 입력을 해주세요.");
            }
        } while (!work.equalsIgnoreCase("0"));
        return false;
    }

    public static void handleOverdraftAccount(Scanner scanner, DemandDepositAccount demandAccount,
                                              FixedDepositAccount fixedAccount, OverdraftAccount overdraftAccount) {
        String work;
        do {
            System.out.print("> 원하시는 업무는? (+:입금, -:출금, T:이체, I:정보) ");
            work = scanner.nextLine();
            if (work.equals("0")) break;

            switch (work) {
                case "+" -> TransferHandler.deposit(scanner, overdraftAccount);
                case "-" -> TransferHandler.withdraw(scanner, overdraftAccount);
                case "T" -> TransferHandler.transfer(scanner, overdraftAccount, demandAccount, fixedAccount);
                case "I" -> overdraftAccount.showAccount();
                default -> System.out.println("올바른 업무 번호를 입력하세요.");
            }
        } while (!work.equalsIgnoreCase("0"));
    }
}
