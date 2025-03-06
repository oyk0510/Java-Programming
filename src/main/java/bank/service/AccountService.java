package bank.service;

import bank.domain.Account;
import bank.persistence.AccountRepository;

import java.util.List;

/* 계좌와 관련된 비즈니스 로직 처리
 * (계좌 개설 시 중복 번호 체크, 입출금 시 조건 확인 등) */
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /* 전체 계좌 조회 */
    public List<Account> getAllAccounts() {
        return accountRepository.selectAllAccounts();
    }

    /* 계좌 개설
    * - 이미 존재하는 계좌번호인지 확인
    * - 문제 없으면 저장 */
    public void openAccount(Account newAccount) {
        // 중복 계좌번호 체크
        if(accountRepository.selectAccountByNumber(newAccount.getAccountNumber()) != null) {
            throw new IllegalArgumentException("이미 존재하는 계좌번호입니다.");
        }
        accountRepository.insertAccount(newAccount);
    }

    /* 입금
    * - 계좌번호가 존재하는지 확인
    * - Account 클래스의 deposit()을 통해 잔액 증가 */
    public void deposit(long accountNumber, double amount) {
        Account account = accountRepository.selectAccountByNumber(accountNumber);
        if(account == null) {
            throw new IllegalArgumentException("해당 계좌를 찾을 수 없습니다.");
        }
        account.deposit(amount);
        accountRepository.updateAccount(account);
    }

    /* 출금
    * - 계좌번호가 존재하는지 확인
    * - Account 클래스의 withdraw()로 잔액 감소 */
    public void withdraw(long accountNumber, double amount) {
        Account account = accountRepository.selectAccountByNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("해당 계좌를 찾을 수 없습니다.");
        }
        account.withdraw(amount);
        accountRepository.updateAccount(account);
    }

    public void deleteAccount(long accountNumber) {
        Account account = accountRepository.selectAccountByNumber(accountNumber);
        if(account == null) {
            throw new IllegalArgumentException("해당 계좌를 찾을 수 없습니다.");
        }
        accountRepository.deleteAccount(accountNumber);
    }

}
