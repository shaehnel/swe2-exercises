package ch.juventus.threads;

public class RaceConditionExample  {

    public static void main(String[] args) {
        AccountDanger ad = new AccountDanger();
        Thread one = new Thread(ad);
        Thread two = new Thread(ad);
        one.setName("Fred");
        two.setName("Lucy");
        one.start();
        two.start();
    }

    private static class AccountDanger implements Runnable {

        private Account account = new Account(50);


        @Override
        public void run() {
            for (int x = 0; x < 5; x++) {
                makeWithdrawal(10);
                if (account.getBalance() < 0) {
                    System.out.println("account is overdrawn!");
                }
            }
        }

        private void makeWithdrawal(int amount) {
            synchronized (account) {
                if (account.getBalance() >= amount) {
                    System.out.printf("%s is going to withdraw\n", Thread.currentThread().getName());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                    }
                    account.withdraw(amount);
                    System.out.printf("%s completes the withdraw\n", Thread.currentThread().getName());
                } else {
                    System.out.printf("Not enough in account %s to withdraw %d\n", Thread.currentThread().getName(), account.getBalance());
                }
            }
        }

    }

    private static class Account {
        private int balance;

        public Account(int balance) {
            this.balance = balance;
        }

        public int getBalance() {
            return balance;
        }

        public void withdraw(int amount) {
            balance = balance - amount;
        }
    }
}
