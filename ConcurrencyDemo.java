import java.util.concurrent.locks.Lock;//importing Lock interface
import java.util.concurrent.locks.ReentrantLock;//importing ReentrantLock class
//This program is to display the concurrency,locks,synchronization in java
// BankAccount class representing a shared resource
class BankAccount {
    private double balance;
    private final Lock lock = new ReentrantLock(); // Lock for finer control over access

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    // Method to deposit money with synchronization
    public void deposit(double amount) {
        lock.lock(); // Acquire the lock
        try {//exception handling displayed
            System.out.println(Thread.currentThread().getName() + " is depositing: " + amount);
            balance += amount;
            System.out.println("New balance after deposit: " + balance);
        } finally {
            lock.unlock(); // Release the lock
        }
    }

    // Method to withdraw money with synchronization
    public void withdraw(double amount) {
        lock.lock(); // Acquire the lock
        try {//here if the balance is less than the amount to be withdrawn then it will display the message
            if (balance >= amount) {
                System.out.println(Thread.currentThread().getName() + " is withdrawing: " + amount);
                balance -= amount;
                System.out.println("New balance after withdrawal: " + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + " attempted to withdraw: " + amount + " but insufficient balance.");
            }
        } finally {
            lock.unlock(); // Release the lock
        }
    }

    // Method to check balance
    public double getBalance() {
        return balance;//returning the balance
    }
}

// Worker class implementing Runnable for multi-threading
class BankTask implements Runnable {
    private final BankAccount account;
    private final boolean isDeposit;
    private final double amount;

    public BankTask(BankAccount account, boolean isDeposit, double amount) {
        this.account = account;
        this.isDeposit = isDeposit;
        this.amount = amount;
    }

    @Override// annotation specifies the compiler that the method after this annotation overrides the method of the superclass
    public void run() {
        if (isDeposit) {//isdeposit has a boolean value that is passed on to threads t1,t2,t3,t4.
            account.deposit(amount);
        } else {
            account.withdraw(amount);
        }
    }
}

// Main class
public class ConcurrencyDemo {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000.0); // Shared resource

        // Creating threads for depositing and withdrawing money
        Thread t1 = new Thread(new BankTask(account, true, 500.0), "User-1");
        Thread t2 = new Thread(new BankTask(account, false, 300.0), "User-2");
        Thread t3 = new Thread(new BankTask(account, true, 200.0), "User-3");
        Thread t4 = new Thread(new BankTask(account, false, 1000.0), "User-4");

        // Starting the threads
        t1.start();//start method is used to start the execution of the thread
        t2.start();
        t3.start();
        t4.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {//exception handling
            e.printStackTrace();
        }

        // Print final account balance
        System.out.println("Final account balance: " + account.getBalance());
    }
}
