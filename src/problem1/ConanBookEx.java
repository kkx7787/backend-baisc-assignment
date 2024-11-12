package problem1;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConanBookEx {
    private static final ConanBookStore[] ConanBooksList = {
            new ConanBookStore("ISBN1234", "셜록홈즈", 20000, "코난 도일", "고전 추리 소설", "추리소설", "2018/10/08"),
            new ConanBookStore("ISBN2345", "도리안 그레이의 초상", 16000, "오스카 와일드", "예술을 위한 예술!", "고전소설", "2022/01/22"),
            new ConanBookStore("ISBN3456", "쥐덫", 27000, "애거서 크리스티", "갇힌 여관에서 벌어지는 추리극", "추리 소설", "2019/06/10")
    };
    private static final ConanBookStore[] ConanBooksShoppingCart = new ConanBookStore[10];
    private static int cartItemCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userName, phoneNumber;
        System.out.print("당신의 이름을 입력하세요: ");
        userName = scanner.nextLine();
        System.out.print("연락처를 입력하세요: ");
        phoneNumber = scanner.nextLine();

        int choice;

        do {
            System.out.println("\n********************************************\n");
            System.out.println("오늘의 선택, 코난문고");
            System.out.println("영원한 스테디셀러, 명탐정 코난시리즈를 만나보세요~");
            System.out.println("\n********************************************\n");
            System.out.println("1. 고객 정보 확인하기 | 2. 장바구니 상품 목록 보기");
            System.out.println("3. 바구니에 항목 추가하기 | 4. 장바구니의 항목 삭제하기");
            System.out.println("5. 장바구니 비우기 | 6. 영수증 표시하기 | 7. 종료");
            System.out.println("\n********************************************\n");
            System.out.print("메뉴 번호를 선택해주세요: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 소비

            switch (choice) {
                case 1:
                    System.out.println("[현재 고객 정보]");
                    displayUser(userName, phoneNumber);
                    break;
                case 2:
                    displayShoppingCart();
                    break;
                case 3:
                    addShoppingCart(scanner);
                    break;
                case 4:
                    removeShoppingCart(scanner);
                    break;
                case 5:
                    clearShoppingCart();
                    break;
                case 6:
                    displayReceipt(scanner, userName, phoneNumber);
                    break;
                case 7:
                    System.out.println("프로그램을 종료합니다.");
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } while (choice != 7);

        scanner.close();
    }

    private static void displayUser(String userName, String phoneNumber) {
        System.out.printf("이름: %s | 연락처: %s%n", userName, phoneNumber);
    }

    private static void displayShoppingCart() {
        System.out.println("\n[장바구니 상품 목록]");
        if (cartItemCount == 0) {
            System.out.println("장바구니가 비어 있습니다.");
        } else {
            System.out.println("-----------------------------");
            System.out.println("도서ID\t|\t수량\t|\t합계");
            for (int i = 0; i < cartItemCount; i++) {
                ConanBooksShoppingCart[i].displayShoppingCart();
            }
            System.out.println("-----------------------------");
        }
    }

    private static void addShoppingCart(Scanner scanner) {
        if (cartItemCount >= ConanBooksShoppingCart.length) {
            System.out.println("장바구니가 가득 찼습니다.");
            return;
        }

        for (ConanBookStore book : ConanBooksList) {
            book.displayDetails();
        }

        System.out.print("장바구니에 추가할 도서의 ID를 입력하세요: ");
        String serialNumber = scanner.nextLine();

        ConanBookStore selectedBook = null;

        for (ConanBookStore book : ConanBooksList) {
            if (book.getSerialNumber().equals(serialNumber)) {
                selectedBook = book;
                break;
            }
        }

        if (selectedBook == null) {
            System.out.println("유효하지 않은 ID입니다.");
            return;
        }

        System.out.print("장바구니에 추가하겠습니까? (Y | N) ");
        String choice = scanner.nextLine().trim();

        if (!choice.equalsIgnoreCase("Y")) {
            return;
        }

        boolean foundInCart = false;
        for (int i = 0; i < cartItemCount; i++) {
            if (ConanBooksShoppingCart[i].getSerialNumber().equals(selectedBook.getSerialNumber())) {
                ConanBooksShoppingCart[i].increaseQuantity();
                foundInCart = true;
                System.out.println("장바구니에 수량이 증가되었습니다.");
                break;
            }
        }

        if (!foundInCart) {
            ConanBooksShoppingCart[cartItemCount++] = selectedBook;
            System.out.println("장바구니에 추가되었습니다.");
        }
    }

    private static void removeShoppingCart(Scanner scanner) {
        if (cartItemCount == 0) {
            System.out.println("장바구니가 비어 있습니다.");
            return;
        }

        System.out.println("\n[장바구니 목록]");
        System.out.println("-----------------------------");
        System.out.println("도서ID\t|\t수량\t|\t합계");
        for (int i = 0; i < cartItemCount; i++) {
            ConanBooksShoppingCart[i].displayShoppingCart();
        }
        System.out.println("-----------------------------");

        System.out.print("장바구니에서 삭제할 도서의 ID를 입력하세요: ");
        String serialNumber = scanner.nextLine();
        boolean found = false;

        for (int i = 0; i < cartItemCount; i++) {
            if (ConanBooksShoppingCart[i].getSerialNumber().equals(serialNumber)) {
                for (int j = i; j < cartItemCount - 1; j++) {
                    ConanBooksShoppingCart[j] = ConanBooksShoppingCart[j + 1];
                }
                ConanBooksShoppingCart[--cartItemCount] = null;
                System.out.printf("장바구니에서 %s가 제거되었습니다.", serialNumber);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("해당 ID를 가진 도서가 장바구니에 없습니다.");
        }
    }


    private static void clearShoppingCart() {
        for (int i = 0; i < cartItemCount; i++) {
            ConanBooksShoppingCart[i] = null;
        }
        cartItemCount = 0;
        System.out.println("장바구니가 비워졌습니다.");
    }

    private static void displayReceipt(Scanner scanner, String userName, String phoneNumber) {
        int total = 0;
        System.out.print("배송받을 분은 고객정보와 같습니까? ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("N")) {
            return;
        }
        System.out.print("배송지를 입력해주세요 ");
        String receivedPlace = scanner.nextLine();

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formatedNow = now.format(formatter);

        System.out.println("[배송 받을 고객 정보]");
        displayUser(userName, phoneNumber);
        System.out.printf("배송지 : %s | 발송일 : %s\n", receivedPlace, formatedNow);
        System.out.println("\n[장바구니 상품 목록]");
        if (cartItemCount == 0) {
            System.out.println("장바구니가 비어 있습니다.");
        } else {
            System.out.println("-----------------------------");
            System.out.println("도서ID\t|\t수량\t|\t합계");
            for (int i = 0; i < cartItemCount; i++) {
                ConanBooksShoppingCart[i].displayShoppingCart();
                total += ConanBooksShoppingCart[i].getTotalPrice();
            }
            System.out.println("-----------------------------");
        }
        System.out.printf("총액: %d원\n", total);
    }
}