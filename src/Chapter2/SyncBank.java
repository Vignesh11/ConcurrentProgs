/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter2;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vignesh
 */
public class SyncBank {
    
    public static void main(String[] args) {
        Account acc = new Account();
        acc.setAccName("Vignesh");
        acc.setBalance(1000);
        Thread t1 = new Thread(new Deposit(acc));
        Thread t2 = new Thread(new Withdraw(acc));   
        t1.start();
        t2.start();    
        try {
            t1.join();
             t2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(SyncBank.class.getName()).log(Level.SEVERE, null, ex);
        }
     System.out.println("Final balance of " +acc.getAccName()+ " is " + acc.getBalance());       
    }
    
}


class Account {
    private String accName;
    private int balance;

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    
}

class Deposit implements Runnable {
    private Account acc;
    
    public Deposit(Account acc){
        this.acc = acc;
    }
    @Override
    public  void run() {
        for (int i = 0 ;i< 100 ; i++ ) {
        doDeposit(1000);
    }
}
    private synchronized void doDeposit(int amount){
        int tmp = acc.getBalance() + amount;
        acc.setBalance(tmp);
    }
}

class Withdraw implements Runnable {
    private Account acc;
    
    public Withdraw(Account acc){
        this.acc = acc;
    }
    @Override
    public void run() {
        for (int i = 0 ;i< 100 ; i++ ) {
        doWithdraw(1000);
    }
}
    private synchronized void doWithdraw(int amount){
        int tmp = acc.getBalance() - amount;
        acc.setBalance(tmp);
    }
}