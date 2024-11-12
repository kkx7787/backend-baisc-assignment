package problem3;
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
                        handleDemandDepositAccount(scanner, demandAccount, fixedAccount, overdraftAccount);
                    }
                    case 2 -> {
                        if (isFixedAccountExpired) {
                            System.out.println("정기예금 통장은 만기 처리되어 더 이상 선택할 수 없습니다.");
                        } else {
                            fixedAccount.showAccount();
                            isFixedAccountExpired = handleFixedDepositAccount(scanner, demandAccount, fixedAccount, overdraftAccount);
                        }
                    }
                    case 3 -> {
                        overdraftAccount.showAccount();
                        handleOverdraftAccount(scanner, demandAccount, fixedAccount, overdraftAccount);
                    }
                    default -> System.out.println("존재하는 계좌를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
        scanner.close();
    }

    private static void handleDemandDepositAccount(Scanner scanner, DemandDepositAccount demandAccount, FixedDepositAccount fixedAccount, OverdraftAccount overdraftAccount) {
        String work;
        do {
            System.out.print("> 원하시는 업무는? (+:입금, -:출금, T:이체, I:정보) ");
            work = scanner.nextLine();
            if (work.equals("0")) break;
            switch (work) {
                case "+" -> deposit(scanner, demandAccount);
                case "-" -> withdraw(scanner, demandAccount);
                case "T" -> transfer(scanner, demandAccount, fixedAccount, overdraftAccount);
                case "I" -> demandAccount.showAccount();
                default -> System.out.println("올바른 작업을 입력하세요.");
            }
        } while (!work.equalsIgnoreCase("0"));
    }

    private static boolean handleFixedDepositAccount(Scanner scanner, DemandDepositAccount demandAccount,
                                                     FixedDepositAccount fixedAccount, OverdraftAccount overdraftAccount) {
        String work;
        do {
            System.out.print("> 정기 예금이 만기되었습니다. (+:만기처리, -:인출, T:이체, I:정보) ");
            work = scanner.nextLine();
            if (work.equals("0")) return false;

            switch (work) {
                case "+" -> {
                    while (true) {
                        int month = getDepositMonths(scanner);
                        if (month == 0) break;

                        double interestRate = getInterestRate(month);
                        boolean isMatured = processMaturity(scanner, fixedAccount, demandAccount, overdraftAccount, month, interestRate);

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

    private static void handleOverdraftAccount(Scanner scanner, DemandDepositAccount demandAccount,
                                               FixedDepositAccount fixedAccount, OverdraftAccount overdraftAccount) {
        String work;
        do {
            System.out.print("> 원하시는 업무는? (+:입금, -:출금, T:이체, I:정보) ");
            work = scanner.nextLine();
            if (work.equals("0")) break;

            switch (work) {
                case "+" -> deposit(scanner, overdraftAccount);
                case "-" -> withdraw(scanner, overdraftAccount);
                case "T" -> transfer(scanner, overdraftAccount, demandAccount, fixedAccount);
                case "I" -> overdraftAccount.showAccount();
                default -> System.out.println("올바른 업무 번호를 입력하세요.");
            }
        } while (!work.equalsIgnoreCase("0"));
    }

    private static void deposit(Scanner scanner, BankAccount account) {
        System.out.print("입금 하실 금액은? ");
        double depositAmount = Double.parseDouble(scanner.nextLine());
        if (depositAmount == 0) return;
        account.deposit(depositAmount);
        String depositAmountStr = String.format("%,d", Math.round(depositAmount));
        System.out.printf("%s 통장에 %s원이 입금되었습니다!\n", account.accountName, depositAmountStr);
    }

    private static void withdraw(Scanner scanner, BankAccount account) {
        while (true) {
            System.out.print("출금 하실 금액은? ");
            double withdrawAmount = Double.parseDouble(scanner.nextLine());
            if (withdrawAmount == 0) break;
            try {
                account.withdraw(withdrawAmount);
                String withdrawAmountStr = String.format("%,d", Math.round(withdrawAmount));
                String balanceAmountStr = String.format("%,d", Math.round(account.getBalance()));
                System.out.printf("%s 통장에서 %s원이 출금되었습니다.\n", account.accountName, withdrawAmountStr);
                System.out.printf("%s 통장의 잔액은 %s원입니다.\n", account.accountName, balanceAmountStr);
                break;
            } catch (InsufficientBalanceException | UnauthorizedTransferException e) {
                String balanceStr = String.format("%,d", Math.round(account.getBalance()));
                System.out.printf("잔액이 부족합니다! (잔액: %s원)\n", balanceStr);
            }
        }
    }

    private static void transfer(Scanner scanner, BankAccount fromAccount, BankAccount toAccount1, BankAccount toAccount2) {
        BankAccount toAccount;
        String transferPrompt;

        int fromAccountType = fromAccount.accountName.equalsIgnoreCase("자유입출금") ? 1 : 3;
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
                System.out.printf("%s 통장에 %s원이 입금되었습니다.\n", toAccount.accountName, transferAmountStr);
                System.out.printf("%s 통장의 잔액은 %s원 입니다.\n", fromAccount.accountName, balanceStr);
                break;
            } catch (InsufficientBalanceException e) {
                String balanceStr = String.format("%,d", Math.round(fromAccount.getBalance()));
                System.out.printf("잔액이 부족합니다! (잔액: %s원)\n", balanceStr);
            } catch (NumberFormatException | UnauthorizedTransferException e) {
                System.out.println("올바른 금액을 입력해 주세요.");
            }
        }
    }

    private static int getDepositMonths(Scanner scanner) {
        while (true) {
            System.out.print("예치 개월 수를 입력하세요? (1 ~ 60개월) ");
            int month = scanner.nextInt();
            scanner.nextLine();
            if (month >= 1 && month <= 60) return month;
            else if (month == 0) return 0;
            System.out.println("올바른 개월 수를 입력해주세요.");
        }
    }

    private static boolean processMaturity(Scanner scanner, FixedDepositAccount fixedAccount,
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

    private static double getInterestRate(int month) {
        if (month >= 1 && month <= 2) return 0.03;
        if (month >= 3 && month <= 5 || month == 9 || month == 12) return 0.0335;
        if (month >= 6 && month <= 8) return 0.034;
        if (month >= 24) return 0.029;
        return 0.03;
    }
}
