/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Vignesh
 */
public class ReadWriteLocks {
    public static void main(String[] args) {
        Product p = new Product();
        Reader r = new Reader(p);
        Writer w = new Writer(p);
        for(int i = 0;i<5;i++){
            Thread t = new Thread(r);
            t.start();
        }
        Thread t2 = new Thread(w);
        t2.start();   
    }   
}
class Product {
    private int prod1;
    private int prod2;
    private ReadWriteLock l;
    
    public Product(){
        prod1 = 0;
        prod2 = 0;
        l =  new ReentrantReadWriteLock();        
}
    
    public int getProd1Price(){
        l.readLock().lock();
         int val = prod1;
        l.readLock().unlock();
        return val;
        //return prod1;
    }
    public int getProd2Price(){
        l.readLock().lock();
        int val = prod2;
        l.readLock().unlock();
        return val;
    }
    public void setProd1Price(int val){
        l.writeLock().lock();
         prod1 = val;
          l.writeLock().unlock();
    }
    
    public void setProd2Price(int val){
        l.writeLock().lock();
         prod2 = val;
         l.writeLock().unlock();
    }
}

class Reader implements Runnable {

    private Product prod;
    
    public Reader(Product prod){
        this.prod = prod;
    }
    @Override
    public void run() {
       for(int i = 0;i<10;i++){
           System.out.println("Prod 1 price " +prod.getProd1Price());
           System.out.println("Prod 2 price " +prod.getProd2Price());
       }
    }   
}
class Writer implements Runnable {
    private Product prod;
    
    public Writer(Product prod){
        this.prod = prod;
    }
    @Override
    public void run() {
       for(int i = 0;i<10;i++){
           System.out.println("Set Prod 1,2 price " );
            prod.setProd1Price(i);
             prod.setProd2Price(i);          
       }
    }  
}
