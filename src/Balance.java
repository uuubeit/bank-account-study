
import java.util.Objects;

public final class Balance {

    private final long amount;
    private final Currency currency;

    public Balance(long amount, Currency currency) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        this.amount = amount;
        this.currency = Objects.requireNonNull(currency);
    }

    public long getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Balance add(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        return new Balance(Math.addExact(this.amount, amount), currency);
    }

    public Balance subtract(long amount) {
        if (amount < 0 || amount > this.amount) {
            throw new IllegalArgumentException("Invalid amount");
        }

        return new Balance(this.amount - amount, currency);
    }

    public Balance convertTo(Currency currency) {
        Objects.requireNonNull(currency);

        long rubles = Math.multiplyExact(
                amount,
                this.currency.getPriceInRublesKopecks()
        );

        long result = rubles / currency.getPriceInRublesKopecks();

        return new Balance(result, currency);
    }

    @Override
    public String toString() {
        return String.format(
                "%.2f %s",
                amount / 100.0,
                currency.getSymbol()
        );
    }
}
