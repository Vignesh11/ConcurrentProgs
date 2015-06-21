/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter3;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vignesh
 */
public class SemaphoresExample {
    public static void main(String[] args) {
        PrinterMachines pm = new PrinterMachines();
        PrinterMan man = new PrinterMan(pm);
        for (int i = 0; i<5;i++){
            Thread t = new Thread(man);        
            t.start();
            System.out.println("Starting Thread" +t.getName());
        }              
    }
    
}

class PrinterMachines {
    private boolean freePrinters[];
    private Lock lockPrinters;
    Semaphore sem;
    
    public PrinterMachines(){
        sem = new Semaphore(3);
        lockPrinters = new ReentrantLock();
        freePrinters = new boolean[3];
        for(int i = 0;i<3;i++)
            freePrinters[i] = true;
    }
        public void printJob(Object doc){
        try {
            sem.acquire();// 3 Threads can acquire this at max.
            int printerNo;
            printerNo = getPrinter();
            Thread.sleep(1000);
            System.out.println("Printing doc for Thread " + Thread.currentThread().getName()+ " using Printer " +printerNo);
            freePrinters[printerNo] = true; //Freeing the printer again after print operation.           
        } catch (InterruptedException ex) {
            Logger.getLogger(PrinterMachines.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            sem.release();
        }            
        }    
        private int getPrinter(){
            int ret = -1;
            try {
            lockPrinters.lock();
            for(int i = 0;i<3;i++){
                if(freePrinters[i]){
                    ret = i;
                    freePrinters[i] = false;
                    break;
                }
            }
                return ret;
            } finally {
                lockPrinters.unlock();
            }
        }
}
class PrinterMan implements Runnable {
    private PrinterMachines machines;
    private Object job;
    
    public PrinterMan(PrinterMachines machines){
       this.machines = machines;
       job = new Object();
       
    }
    @Override
    public void run(){   
        machines.printJob(job);
        
    }
}
        
    
    

