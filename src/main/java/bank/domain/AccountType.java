package bank.domain;

public enum AccountType {
    SAVINGS,     // 예금 계좌
    CHECKING;

    public static AccountType fromString(String type) {
        return switch(type.toUpperCase()) {
            case "SAVINGS" -> SAVINGS;
            case "CHECKING" -> CHECKING;
            default -> throw new IllegalArgumentException("Invalid Account Type : " + type);
        };
    }
}
