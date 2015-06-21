/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vignesh
 */
public class reentrantest {
 
    public static void main(String[] args) {
        Processor p = new Processor();
        Thread t1 = new Thread(new Runnable(){
            public void run(){
                p.method1();
            }
        });
         Thread t2 = new Thread(new Runnable(){
            public void run(){
                p.method2();
            }
        });
         
        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(reentrantest.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         p.method3();
        
    }
}

class Processor {
    
    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();
    //private Condition cond = (lock.newCondition());
    
    private void increment(){
        for(int i = 0;i<10000;i++){
            count++;
        }   
    }
    public void method1(){
        lock.lock();
        lock2.lock();
        try {
            //cond.await();
            increment();
      //  } catch (InterruptedException ex) {
          //  Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
       finally {
            lock.unlock();
            lock2.unlock();
        }       
    }
    
    public void method2(){
            //Thread.sleep(1000);
        try {
            lock2.lock();
            lock.lock();
            increment();
          //  cond.signal();
        }
        finally{
            lock.unlock();
            lock2.unlock();
                }   
    }
    public void method3(){
        System.out.println("Count" +count);
    }
}