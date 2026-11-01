
import java.util.Objects;

public class BankAccount {

    private final String accountNumber;
    private final long withdrawalLimit;

    private Balance balance;
    private boolean blocked;

    public BankAccount(String accountNumber, Balance balance, long withdrawalLimit) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Invalid account number");
        }

        if (withdrawalLimit < 0) {
            throw new IllegalArgumentException("Invalid withdrawal limit");
        }

        this.accountNumber = accountNumber;
        this.balance = Objects.requireNonNull(balance);
        this.withdrawalLimit = withdrawalLimit;
        this.blocked = false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Balance getBalance() {
        return balance;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void deposit(long amount) {
        checkAccount();

        balance = balance.add(amount);
    }

    public void withdraw(long amount) {
        checkAccount();

        if (amount > withdrawalLimit) {
            throw new IllegalArgumentException("Withdrawal limit exceeded");
        }

        balance = balance.subtract(amount);
    }

    public void transferTo(BankAccount recipient, long amount) {
        checkAccount();

        Objects.requireNonNull(recipient);

        if (recipient == this) {
            throw new IllegalArgumentException("Cannot transfer to yourself");
        }

        recipient.checkAccount();

        if (amount > withdrawalLimit) {
            throw new IllegalArgumentException("Transfer limit exceeded");
        }

        if (balance.getCurrency() != recipient.balance.getCurrency()) {
            throw new IllegalArgumentException("Currencies do not match");
        }

        Balance senderBalance = balance.subtract(amount);
        Balance recipientBalance = recipient.balance.add(amount);

        balance = senderBalance;
        recipient.balance = recipientBalance;
    }

    private void checkAccount() {
        if (blocked) {
            throw new IllegalStateException("Account is blocked");
        }
    }

    @Override
    public String toString() {
        return "Account " + accountNumber +
                ", balance: " + balance +
                ", blocked: " + blocked;
    }
}
