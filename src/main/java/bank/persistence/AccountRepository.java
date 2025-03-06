package bank.persistence;

import bank.domain.Account;

import java.util.ArrayList;
import java.util.List;

/* 메모리에 계좌 목록을 저장하고,
 * 변경사항이 생길 때마다 FileAccountStorage를 통해 파일에도 저장한다. */
public class AccountRepository {
    private final AccountStorage accountStorage;
    private final List<Account> accountList;

    public AccountRepository(AccountStorage accountStorage) {
        this.accountStorage = accountStorage;
        // 프로그램 시작 시, 파일에서 기존 계좌 목록을 불러와 메모리에 저장
        this.accountList = accountStorage.loadAccounts();
    }

    public List<Account> selectAllAccounts() {
        return new ArrayList<>(accountList);
    }

    public Account selectAccountByNumber(long accountNumber) {
        return accountList.stream()
                .filter(a -> a.getAccountNumber() == accountNumber)
                .findFirst()
                .orElse(null);
    }

    public void insertAccount(Account account) {
        accountList.add(account);
        // 리스트가 변경되었으므로, 파일에 즉시 저장
        accountStorage.saveAccounts(accountList);
    }

    public void updateAccount(Account updated) {
        for(int i = 0; i < accountList.size(); i++) {
            if(accountList.get(i).getAccountNumber() == updated.getAccountNumber()) {
                accountList.set(i, updated);
                accountStorage.saveAccounts(accountList);
                break;
            }
        }
    }

    public void deleteAccount(long accountNumber) {
        accountList.removeIf(a -> a.getAccountNumber() == accountNumber);
        accountStorage.saveAccounts(accountList);
    }
}
