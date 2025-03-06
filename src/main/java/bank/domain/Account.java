package bank.domain;

import java.io.Serializable;

public class Account implements Serializable {
    private long accountNumber;          // 계좌번호
    private String ownerName;           // 예금주
    private double balance;             // 계좌 잔액
    private AccountType accountType;    // 계좌 유형(SAVINGS, CHECKING)

    /* 계좌 생성 시 필요한 정보 */
    public Account(long accountNumber, String ownerName, double initialDeposit, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialDeposit;      // 계좌를 만들 때 처음 넣는 돈
        this.accountType = accountType;
    }

    /* 입금 메소드 */
    public void deposit(double amount) {
        if(amount <= 0) {
            // amount가 0 이하이면 예외 발생
            throw new IllegalArgumentException("입금액은 0보다 커야 합니다.");
        }
        balance += amount;   // balance를 amount만큼 증가시킨다.
    }

    /* 출금 메소드 */
    public void withdraw(double amount) {
        if(amount <= 0) {
            // amount가 0 이하이면 예외 발생
            throw new IllegalArgumentException("출금액은 0보다 커야 합니다.");
        }

        if(balance < amount) {
            // 잔액이 출금액보다 적으면 예외 발생
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        balance -= amount;   // balance에서 amount만큼 감소시킨다.
    }

    /* Getter 메소드 */
    public long getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public String toString() {
        return "Account{" +
                "계좌번호 =" + accountNumber +
                ", 예금주 ='" + ownerName + '\'' +
                ", 계좌 잔액 =" + balance +
                ", 계좌 유형 =" + accountType +
                '}';
    }
}
