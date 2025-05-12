import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class person {
    private String name;
    private double contri;
    private double balance;

    public person(String name, double contri) {
        this.name = name;
        this.contri = contri;
        this.balance = 0;
    }

    public person(String name) {
        this.name = name;
    }

    public String getname() {
        return name;
    }

    public double getcontri() {
        return contri;
    }

    public double getbalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }
}

class Expense {
    String expensetype;
    double expense;
    List<person> contributors;

    public Expense(String descr, double amount, List<person> contributors) {
        this.expensetype = descr;
        this.expense = amount;
        this.contributors = contributors;
    }

    public String getdescription() {
        return expensetype;
    }

    public double getamount() {
        return expense;

    }

    public List<person> getcontributors() {
        return contributors;
    }
}

class billsplitter {
    private List<person> people;
    private List<Expense> amounts;

    public billsplitter() {
        this.people = new ArrayList<>();
        this.amounts = new ArrayList<>();
    }

    public void addperson(String name) {
        people.add(new person(name));

    }

    // public double shareofeach() {
    // return amount / people.size();
    // }

    public void addExpense(String description, double amount, List<String> contributors) {
        List<person> contributorsname = new ArrayList<>();
        for (String i : contributors) {
            person p = getpersonbyname(i);
            if (p != null) {
                contributorsname.add(p);
            }
        }
        amounts.add(new Expense(description, amount, contributorsname));
    }

    private person getpersonbyname(String name) {
        for (person i : people) {
            if (i.getname().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }

    public void calculateBalances() {
        for (Expense e : amounts) {
            double share = e.getamount() / e.getcontributors().size();
            for (person contributor : e.getcontributors()) {
                contributor.updateBalance(-share);
            }

            for (person contri : people) {
                contri.updateBalance(+share);
            }
        }
    }

    public void printBalances() {
        System.out.println("Balances: ");
        for (person p : people) {
            System.out.println(p.getname() + ":" + String.format("%.2f", p.getbalance()));
        }
    }

    public void printExpenses() {
        System.out.println("Expenses: ");
        for (Expense e : amounts) {
            System.out.println("- " + e.getdescription() + ": " + String.format("%.2f", e.getamount()));
        }
    }

    // public List<String> getdetails() {
    // List<String> settlements = new ArrayList<>();
    // calculateBalances();

    // for (person payer : people) {
    // if (payer.getbalance() < 0) {
    // for (person reciver : people) {
    // if (reciver.getbalance() > 0) {
    // double amount = Math.min(-payer.getbalance(), reciver.getbalance());
    // if (amount > 0) {
    // settlements.add(payer.getname() + " owes " + reciver.getname() + " $"
    // + String.format("%.2f", amount));
    // payer.setBalance(payer.getbalance() - amount);
    // }
    // }
    // }
    // }
    // }

    // if (settlements.isEmpty()) {
    // settlements.add("Everyone is settled up !");
    // }
    // return settlements;
    // }
}

public class BillsplitterApp {
    public static void main(String[] args) {
        billsplitter bills = new billsplitter();

        bills.addperson("Kanav");
        bills.addperson("Pranav");
        bills.addperson("Harshit");
        bills.addperson("Aayush");

        bills.addExpense("Dinner", 90.0, List.of("Kanav", "Pr", "Bob",""));
        bills.addExpense("Groceries", 50.0, List.of("Alice", "Charlie"));
        bills.addExpense("Rent", 120.0, List.of("Bob", "Charlie"));

        bills.calculateBalances();
        bills.printExpenses();
        bills.printBalances();
    }
}