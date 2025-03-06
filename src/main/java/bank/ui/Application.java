package bank.ui;

import bank.domain.Account;
import bank.domain.AccountType;
import bank.persistence.AccountRepository;
import bank.persistence.FileAccountStorage;
import bank.service.AccountService;

import java.util.List;
import java.util.Scanner;

/* 콘솔에서 사용자 입력을 받고, 서비스(AccountService)를 호출하여
 * 실제 계좌 관련 작업 수행하는 메인 클래스 */
public class Application {
    private final AccountService accountService;
    private final Scanner sc;

    public Application() {
        // 프로그램 시작 시, 파일 저장 방식을 쓰는
        // AccountStorage, AccountRepository, AccountService를 순서대로 초기화
        AccountRepository accountRepository = new AccountRepository(new FileAccountStorage());
        this.accountService = new AccountService(accountRepository);
        this.sc = new Scanner(System.in);
    }

    public void run() {
        while(true) {
            System.out.println("\n===== 은행 계좌 관리 프로그램 =====");
            System.out.println("1. 전체 계좌 조회");
            System.out.println("2. 계좌 개설");
            System.out.println("3. 입금");
            System.out.println("4. 출금");
            System.out.println("5. 계좌 삭제");
            System.out.println("0. 프로그램 종료");
            System.out.print("원하시는 서비스 번호를 입력하세요 : ");

            int choice = sc.nextInt();
            sc.nextLine();  // 개행문자 제거

            try {
                switch (choice) {
                    case 1 -> showAllAcounts();
                    case 2 -> openAccount();
                    case 3 -> deposit();
                    case 4 -> withdraw();
                    case 5 -> deleteAccount();
                    case 0 -> {
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    }
                    default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
                }
            } catch (Exception e) {
                // 다른 파일에서 발생한 예외 메세지 출력 (서비스 계층 등등)
                System.out.println("오류 : " + e.getMessage());
            }
        }
    }


    private void showAllAcounts() {
        List<Account> accounts = accountService.getAllAccounts();
        if(accounts.isEmpty()) {
            System.out.println("현재 등록된 계좌가 없습니다.");
        } else {
            accounts.forEach(System.out::println);
        }
    }

    private void openAccount() {
        System.out.print("새로운 계좌번호 입력 : ");
        int accountNumber = sc.nextInt();
        sc.nextLine();

        System.out.print("예금주 이름 입력 : ");
        String ownerName = sc.nextLine();

        System.out.print("초기 입금액 입력 : ");
        double initialDeposit = sc.nextDouble();
        sc.nextLine();

        System.out.print("계좌 유형 입력 (SAVINGS / CHECKING) : ");
        String typeInput = sc.nextLine();
        AccountType accountType = AccountType.fromString(typeInput);

        Account newAccount = new Account(accountNumber, ownerName, initialDeposit, accountType);
        accountService.openAccount(newAccount);
        System.out.println("계좌 개설 성공!");
    }

    private void deposit() {
        System.out.print("입금할 계좌번호 입력: ");
        int accountNumber = sc.nextInt();
        sc.nextLine();

        System.out.print("입금액 입력: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        accountService.deposit(accountNumber, amount);
        System.out.println("입금 완료!");
    }

    private void withdraw() {
        System.out.print("출금할 계좌번호 입력: ");
        int accountNumber = sc.nextInt();
        sc.nextLine();

        System.out.print("출금액 입력: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        accountService.withdraw(accountNumber, amount);
        System.out.println("출금 완료!");
    }

    private void deleteAccount() {
        System.out.print("삭제할 계좌번호 입력 : ");
        long accountNumber = sc.nextLong();
        sc.nextLine();

        accountService.deleteAccount(accountNumber);
        System.out.println("계좌 삭제 완료!");
    }

    public static void main(String[] args) {
        new Application().run();
    }

}
