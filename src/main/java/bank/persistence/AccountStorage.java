package bank.persistence;

import bank.domain.Account;

import java.util.List;

/* 계좌 데이터를 저장/불러오는 방법 추상화 */
public interface AccountStorage {
    void saveAccounts(List<Account> accounts);
    List<Account> loadAccounts();
}
