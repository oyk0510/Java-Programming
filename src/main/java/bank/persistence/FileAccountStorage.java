package bank.persistence;

import bank.domain.Account;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// 1. 파일에 계좌 정보 저장
// 2. 파일에서 불러오는 기능 담당
public class FileAccountStorage implements AccountStorage {

    private static final String FILE_PATH = "src/main/java/bank/db/accountDB.dat";


    @Override
    public void saveAccounts(List<Account> accounts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            // List<Account> 전체를 직렬화해서 파일에 저장
            oos.writeObject(accounts);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류 발생", e);
        }
    }

    @Override
    public List<Account> loadAccounts() {
        File file = new File(FILE_PATH);
        // 파일이 없다면, 아직 저장된 계좌가 없으므로 빈 리스트를 반환
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // 파일에서 List<Account>를 읽어온 뒤, 반환
            return (List<Account>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("파일 로딩 중 오류 발생", e);
        }
    }
}
