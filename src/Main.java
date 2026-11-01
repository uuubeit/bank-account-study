
public class Main {

    public static void main(String[] args) {

        BankAccount first = new BankAccount(
                "100001",
                new Balance(100_000, Currency.RUB),
                50_000
        );

        BankAccount second = new BankAccount(
                "100002",
                new Balance(50_000, Currency.RUB),
                30_000
        );

        System.out.println("Before operations:");
        System.out.println(first);
        System.out.println(second);

        first.deposit(20_000);
        first.withdraw(10_000);
        first.transferTo(second, 30_000);

        System.out.println("\nAfter operations:");
        System.out.println(first);
        System.out.println(second);

        Balance dollars = new Balance(10_000, Currency.USD);
        Balance rubles = dollars.convertTo(Currency.RUB);

        System.out.println("\nCurrency conversion:");
        System.out.println(dollars + " = " + rubles);

        first.setBlocked(true);

        try {
            first.withdraw(1_000);
        } catch (IllegalStateException e) {
            System.out.println("\n" + e.getMessage());
        }
    }
}
