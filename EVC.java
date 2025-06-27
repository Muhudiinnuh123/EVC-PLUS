// EVC Plus

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Transaction Class
class Transaction {
    String type;
    int amount;
    String target;
    LocalDateTime date;

    public Transaction(String type, int amount, String target) {
        this.type = type;
        this.amount = amount;
        this.target = target;
        this.date = LocalDateTime.now();
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return type + " $" + amount + " → " + target + " | " + date.format(formatter);
    }
}

// Bank Account Class
class BankAccount {
    private int balance;
    private int pin;

    public BankAccount(int balance, int pin) {
        this.balance = balance;
        this.pin = pin;
    }

    public boolean checkPIN(int enteredPin) {
        return this.pin == enteredPin;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public boolean withdraw(int amount) {
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }

    public int getBalance() {
        return balance;
    }
}

// Customer Class
class Customer {
    String name;
    int balance;
    int pin;
    List<Transaction> history;
    BankAccount bankAccount;

    public Customer(String name, int balance, int pin, BankAccount bankAccount) {
        this.name = name;
        this.balance = balance;
        this.pin = pin;
        this.history = new ArrayList<>();
        this.bankAccount = bankAccount;
    }

    public void addTransaction(String type, int amount, String target) {
        Transaction t = new Transaction(type, amount, target);
        history.add(t);
    }

    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("Waxba lama sameyn wali.");
        } else {
            for (Transaction t : history) {
                System.out.println(t);
            }
        }
    }
}

public class EVC {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BankAccount userBank = new BankAccount(700, 12345);
        Customer user = new Customer("MUHUDIN", 1500, 2000, userBank);

        System.out.print("Geli USSD code-ka: ");
        String ussd = input.nextLine();
        if (!ussd.equals("*770#")) {
            System.out.println("ERROR: USSD code qalad ah.");
            return;
        }

        System.out.print("Geli PIN-kaaga: ");
        int enteredPin = input.nextInt();
        if (enteredPin != user.pin) {
            System.out.println("ERROR: PIN qalad ah.");
            return;
        }

        while (true) {
            System.out.println("\n-- EVCPLUS MENU --");
            System.out.println("1. Itus haraaga");
            System.out.println("2. U wareeji EVCPLUS");
            System.out.println("3. Ku shub Airtime");
            System.out.println("4. Bixi Biil");
            System.out.println("5. Warbixin (History)");
            System.out.println("6. Salaam Bank");
            System.out.println("7. Ka bax");
            System.out.print("Dooro: ");
            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                System.out.println("Haraagaaga waa: $" + user.balance);
            }

            else if (choice == 2) {
                System.out.print("Geli lambarka aad u wareejinayso: ");
                String lambar = input.nextLine();
                System.out.print("Geli lacagta: ");
                int lacag = input.nextInt();
                if (lacag > user.balance) {
                    System.out.println("Lacagta kuma filna!");
                } else {
                    user.balance -= lacag;
                    user.addTransaction("Transfer", lacag, lambar);
                    System.out.println("Waad u wareejisay $" + lacag + " qofka " + lambar);
                }
            }

            else if (choice == 3) {
                System.out.print("Geli lacagta aad ku shubayso Airtime: ");
                int airtime = input.nextInt();
                if (airtime > user.balance) {
                    System.out.println("Lacag kuma filna!");
                } else {
                    user.balance -= airtime;
                    user.addTransaction("Airtime", airtime, "Naftaada");
                    System.out.println("Waad ku shubatay $" + airtime);
                }
            }

            else if (choice == 4) {
                System.out.print("Magaca shirkada: ");
                String company = input.nextLine();
                System.out.print("Lacagta la bixinayo: ");
                int amount = input.nextInt();
                if (amount > user.balance) {
                    System.out.println("Kuma filna lacagta.");
                } else {
                    user.balance -= amount;
                    user.addTransaction("Bill Payment", amount, company);
                    System.out.println("Waad bixisay $" + amount + " shirkada " + company);
                }
            }

            else if (choice == 5) {
                user.showHistory();
            }

            else if (choice == 6) {
                System.out.print("Geli PIN-ka Salaam Bank: ");
                int pin = input.nextInt();
                if (!user.bankAccount.checkPIN(pin)) {
                    System.out.println("PIN qalad ah!");
                    continue;
                }

                while (true) {
                    System.out.println("\n-- SALAAM BANK MENU --");
                    System.out.println("1. Haraaga Bankiga");
                    System.out.println("2. Dhig Lacag");
                    System.out.println("3. Qaado Lacag");
                    System.out.println("4. Ka noqo");
                    System.out.print("Dooro: ");
                    int bChoice = input.nextInt();

                    if (bChoice == 1) {
                        System.out.println("Bank balance: $" + user.bankAccount.getBalance());
                    }
                    else if (bChoice == 2) {
                        System.out.print("Geli lacagta la dhigayo: ");
                        int amount = input.nextInt();
                        user.bankAccount.deposit(amount);
                        user.addTransaction("Bank Deposit", amount, "Bank");
                        System.out.println("Waad dhigatay $" + amount);
                    }
                    else if (bChoice == 3) {
                        System.out.print("Geli lacagta la qaadanayo: ");
                        int amount = input.nextInt();
                        if (user.bankAccount.withdraw(amount)) {
                            user.addTransaction("Bank Withdrawal", amount, "Bank");
                            System.out.println("Waad ka qaadatay $" + amount);
                        } else {
                            System.out.println("Kuma filna lacagta Bankiga.");
                        }
                    }
                    else if (bChoice == 4) {
                        System.out.println("Waad ka baxday Salaam Bank.");
                        break;
                    }
                    else {
                        System.out.println("Doorasho aan sax ahayn!");
                    }
                }
            }

            else if (choice == 7) {
                System.out.println("Mahadsanid! Barnaamijka wuu xiray.");
                break;
            }

            else {
                System.out.println("Doorasho khaldan!");
            }
        }

        input.close();
    }
}
// Barnaamijkan wuxuu matalayaa nidaam EVCPLUS oo u shaqeeya sida mobile money (sida Hormuud).
// Waxaa lagu dhisay Java iyadoo la adeegsanayo mabdaa OOP (Object-Oriented Programming).
//Features:
// - USSD system login: *770# + PIN
// - Macaamil leh EVC balance + Bank Account
//- Transactions: Transfer, Airtime, Bill Payment
// - Salaam Bank: Deposit, Withdraw, Check Balance
// - Taariikhda waxqabadka (History log)
// - Xogta waxaa lagu kaydiyaa Transaction objects
// Isticmaalka OOP:
// - Transaction class Kaydinta taariikhda waxqabadka
// - Customer class Macaamiil iyo taariikhdiisa
// - BankAccount class Bank account gaar ah oo leh PIN
// - EVCPlusOOP Main application
// Isticmaalka Scanner:
// User input waa la qaataa si ula falgalo menu-yo kala duwan
