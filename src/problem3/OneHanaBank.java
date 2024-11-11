package problem3;
import java.util.Scanner;

public class OneHanaBank {
    public static void main(String[] args) {
        DemandDepositAccount demandAccount = new DemandDepositAccount("1", "홍길동");
        FixedDepositAccount fixedAccount = new FixedDepositAccount("2", "홍길동", 50000000);
        OverdraftAccount overdraftAccount = new OverdraftAccount("3", "홍길동");

        Scanner scanner = new Scanner(System.in);
        String account;
        String work;
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
                        do {
                            System.out.print("> 원하시는 업무는? (+:입금, -:출금, T:이체, I:정보) ");
                            work = scanner.nextLine();
                            if (work.equals("0")) break;
                            switch (work) {
                                case "+" -> {
                                    System.out.print("입금하실 금액은? ");
                                    double depositAmount = Double.parseDouble(scanner.nextLine());
                                    if (depositAmount == 0) continue;
                                    String depositAmountStr = String.format("%,d", Math.round(depositAmount));
                                    demandAccount.deposit(depositAmount);
                                    System.out.printf("자유입출금 통장에 %s원이 입금되었습니다.\n", depositAmountStr);
                                }
                                case "-" -> {
                                    boolean isWithdrawing = true;
                                    while (isWithdrawing) {
                                        System.out.print("출금하실 금액은? (0 입력 시 이전 메뉴로 돌아가기): ");
                                        double withdrawAmount = Double.parseDouble(scanner.nextLine());

                                        if (withdrawAmount == 0) {
                                            break;
                                        }

                                        try {
                                            demandAccount.withdraw(withdrawAmount);
                                            String withdrawAmountStr = String.format("%,d", Math.round(withdrawAmount));
                                            System.out.printf("자유입출금 통장에서 %s원이 출금되었습니다.\n", withdrawAmountStr);
                                            isWithdrawing = false;
                                        } catch (InsufficientBalanceException e) {
                                            String balanceStr = String.format("%,d", Math.round(demandAccount.getBalance()));
                                            System.out.printf("잔액이 부족합니다! (잔액: %s원)\n", balanceStr);
                                        }
                                    }
                                }

                                case "T" -> {
                                    transferSelection1:
                                    while (true) {
                                        System.out.print("어디로 보낼까요? (2: 정기예금, 3:마이너스, 0 입력 시 이전 메뉴로 돌아가기) ");
                                        int transferAccount = scanner.nextInt();
                                        scanner.nextLine();

                                        if (transferAccount == 0) {
                                            break;
                                        }

                                        switch (transferAccount) {
                                            case 2 -> {
                                                while (true) {
                                                    System.out.print("정기예금 통장에 보낼 금액은? (0 입력 시 이전 질문으로 돌아가기): ");
                                                    double transferAmount = Double.parseDouble(scanner.nextLine());

                                                    if (transferAmount == 0) {
                                                        continue transferSelection1;
                                                    }

                                                    try {
                                                        demandAccount.transfer(fixedAccount, transferAmount);
                                                        String balanceStr = String.format("%,d", Math.round(demandAccount.getBalance()));
                                                        String transferAmountStr = String.format("%,d", Math.round(transferAmount));
                                                        System.out.printf("정기예금 통장에 %s원이 입금되었습니다.\n", transferAmountStr);
                                                        System.out.printf("자유입출금 통장의 잔액은 %s원 입니다.\n", balanceStr);
                                                        break;
                                                    } catch (InsufficientBalanceException e) {
                                                        String balanceStr = String.format("%,d", Math.round(demandAccount.getBalance()));
                                                        System.out.printf("잔액이 부족합니다! (잔액: %s원)\n", balanceStr);
                                                    }
                                                }
                                            }
                                            case 3 -> {
                                                while (true) {
                                                    System.out.print("마이너스 통장에 보낼 금액은? (0 입력 시 이전 질문으로 돌아가기): ");
                                                    double transferAmount = Double.parseDouble(scanner.nextLine());

                                                    if (transferAmount == 0) {
                                                        continue transferSelection1; // 상위 메뉴로 돌아가기
                                                    }

                                                    try {
                                                        demandAccount.transfer(overdraftAccount, transferAmount);
                                                        String balanceStr = String.format("%,d", Math.round(demandAccount.getBalance()));
                                                        String transferAmountStr = String.format("%,d", Math.round(transferAmount));
                                                        System.out.printf("마이너스 통장에 %s원이 입금되었습니다.\n", transferAmountStr);
                                                        System.out.printf("자유입출금 통장의 잔액은 %s원 입니다.\n", balanceStr);
                                                        break;
                                                    } catch (InsufficientBalanceException e) {
                                                        String balanceStr = String.format("%,d", Math.round(demandAccount.getBalance()));
                                                        System.out.printf("잔액이 부족합니다! (잔액: %s원)\n", balanceStr);
                                                    }
                                                }
                                            }
                                            default -> System.out.println("보내려는 통장이 존재하지 않습니다!");
                                        }
                                        break;
                                    }
                                }


                                case "I" -> demandAccount.showAccount();
                                default -> System.out.println("올바른 작업을 입력하세요.");
                            }
                        } while (!work.equalsIgnoreCase("0"));
                    }
                    case 2 -> {
                        if (isFixedAccountExpired) {
                            System.out.println("정기예금 통장은 만기 처리되어 더 이상 선택할 수 없습니다.");
                        } else {
                            fixedAccount.showAccount();
                            do {
                                System.out.print("> 정기 예금이 만기되었습니다. (+:만기처리, -:출금, T:이체, I:정보) ");
                                work = scanner.nextLine();
                                if (work.equals("0")) break;
                                switch (work) {
                                    case "+" -> {
                                        boolean isSettingMaturity = true;

                                        while (isSettingMaturity) {
                                            System.out.print("예치 개월 수를 입력하세요? (1 ~ 60개월, 0 입력 시 이전 메뉴로 돌아가기): ");
                                            int month = scanner.nextInt();
                                            scanner.nextLine();

                                            if (month == 0) {
                                                break;
                                            }

                                            double interestRate = getInterestRate(month);

                                            boolean isChoosingMaturity = true;
                                            while (isChoosingMaturity) {
                                                System.out.printf("%d개월(적용 금리 %.2f)로 만기 처리하시겠어요? (Y: 예, N: 아니요) ", month, interestRate * 100);
                                                String choice = scanner.nextLine();

                                                if (choice.equalsIgnoreCase("0") || choice.equalsIgnoreCase("N")) {
                                                    isChoosingMaturity = false;
                                                    continue;
                                                } else if (choice.equalsIgnoreCase("Y")) {
                                                    boolean isSelectingAccount = true;

                                                    while (isSelectingAccount) {
                                                        System.out.print("어디로 보낼까요? (1: 자유입출금, 3: 마이너스) ");
                                                        int receivedPlace = scanner.nextInt();
                                                        scanner.nextLine();

                                                        if (receivedPlace == 0) {
                                                            isSelectingAccount = false;
                                                        } else if (receivedPlace == 1) {
                                                            fixedAccount.processFinalAmount(demandAccount, interestRate);
                                                            isFixedAccountExpired = true;
                                                            System.out.println("정기예금 통장이 만기 처리되었습니다.");
                                                            isSettingMaturity = false;
                                                            isChoosingMaturity = false;
                                                            break;
                                                        } else if (receivedPlace == 3) {
                                                            fixedAccount.processFinalAmount(overdraftAccount, interestRate);
                                                            isFixedAccountExpired = true;
                                                            isSettingMaturity = false;
                                                            isChoosingMaturity = false;
                                                            break;
                                                        } else {
                                                            System.out.println("올바른 계좌를 선택해주세요.");
                                                        }
                                                    }
                                                } else {
                                                    System.out.println("올바른 선택을 해주세요.");
                                                }
                                            }
                                        }
                                    }

                                    case "-" -> {
                                        System.out.println("출금할 수 없는 통장입니다.");
                                    }
                                    case "T" -> {
                                        System.out.println("이체할 수 없는 통장입니다.");
                                    }
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
                            } while (!isFixedAccountExpired && !work.equalsIgnoreCase("0"));
                        }
                    }
                    case 3 -> {
                        overdraftAccount.showAccount();
                        do {
                            System.out.print("> 원하시는 업무는? (+:입금, -:출금, T:이체, I:정보) ");
                            work = scanner.nextLine();
                            if (work.equals("0")) break; // 상위 메뉴로 돌아가기
                            switch (work) {
                                case "+" -> {
                                    System.out.print("입금하실 금액은? ");
                                    double depositAmount = Double.parseDouble(scanner.nextLine());
                                    if (depositAmount == 0) {
                                        continue;
                                    }
                                    String depositAmountStr = String.format("%,d", Math.round(depositAmount));
                                    overdraftAccount.deposit(depositAmount);
                                    System.out.printf("마이너스 통장에 %s원이 입금되었습니다.\n", depositAmountStr);
                                    overdraftAccount.showAccount();
                                }
                                case "-" -> {
                                    System.out.print("출금하실 금액은? ");
                                    double withdrawAmount = Double.parseDouble(scanner.nextLine());
                                    if (withdrawAmount == 0) {
                                        continue;
                                    }
                                    String withdrawAmountStr = String.format("%,d", Math.round(withdrawAmount));
                                    overdraftAccount.withdraw(withdrawAmount);
                                    System.out.printf("마이너스 통장에 %s원이 출금되었습니다.\n", withdrawAmountStr);
                                    overdraftAccount.showAccount();
                                }
                                case "I" -> overdraftAccount.showAccount();
                                case "T" -> {
                                    while (true) {
                                        System.out.print("어디로 보낼까요? (1: 자유입출금, 2: 정기예금) ");
                                        int transferAccount = scanner.nextInt();
                                        scanner.nextLine();
                                        if (transferAccount == 0) {
                                            break;
                                        }

                                        switch (transferAccount) {
                                            case 1 -> {
                                                System.out.print("자유입출금 통장에 보낼 금액은? ");
                                                double transferAmount = Double.parseDouble(scanner.nextLine());
                                                if (transferAmount == 0) {
                                                    continue;
                                                }
                                                overdraftAccount.transfer(demandAccount, transferAmount);
                                                String balanceStr = String.format("%,d", Math.round(overdraftAccount.getBalance()));
                                                String transferAmountStr = String.format("%,d", Math.round(transferAmount));
                                                System.out.printf("자유입출금 통장에 %s원이 입금되었습니다.\n", transferAmountStr);
                                                System.out.printf("마이너스 통장의 잔액은 %s원 입니다.\n", balanceStr);
                                            }
                                            case 2 -> {
                                                System.out.print("정기예금 통장에 보낼 금액은? ");
                                                double transferAmount = Double.parseDouble(scanner.nextLine());
                                                if (transferAmount == 0) {
                                                    continue;
                                                }
                                                overdraftAccount.transfer(fixedAccount, transferAmount);
                                                String balanceStr = String.format("%,d", Math.round(overdraftAccount.getBalance()));
                                                String transferAmountStr = String.format("%,d", Math.round(transferAmount));
                                                System.out.printf("정기예금 통장에 %s원이 입금되었습니다.\n", transferAmountStr);
                                                System.out.printf("마이너스 통장의 잔액은 %s원 입니다.\n", balanceStr);
                                            }
                                            default -> System.out.println("보내려는 통장이 존재하지 않습니다!");
                                        }
                                        break;
                                    }
                                }
                                default -> System.out.println("올바른 업무 번호를 입력하세요.");
                            }
                        } while (!work.equalsIgnoreCase("0"));
                    }
                    default -> System.out.println("존재하는 계좌를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
        scanner.close();
    }

    private static double getInterestRate(int month) {
        double interestRate;
        switch (month) {
            case 1, 2 -> interestRate = 0.03;
            case 3, 4, 5, 9, 10, 11, 12, 13, 23 -> interestRate = 0.0335;
            case 6, 7, 8 -> interestRate = 0.034;
            case 24, 25, 35, 36, 37, 71 -> interestRate = 0.029;
            default -> {
                if (month >= 72) {
                    interestRate = 0.029;
                } else {
                    interestRate = 0.03;
                }
            }
        }
        return interestRate;
    }
}