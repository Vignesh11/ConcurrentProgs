/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter3;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vignesh
 */
public class CountDownLatchTest {
    
    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(10);
        ExecutorService ex = Executors.newFixedThreadPool(3);
        for (int i = 0;i<10;i++){
          ex.submit(new testCDL(cdl));
        }
        
        try {
            cdl.await();
        } catch (InterruptedException ex1) {
            Logger.getLogger(CountDownLatchTest.class.getName()).log(Level.SEVERE, null, ex1);
        }
        
                
        System.out.println("Game Over " +cdl.getCount());
    }   
}

class testCDL implements Runnable {
    private CountDownLatch cdl;
    public testCDL(CountDownLatch cdl){
        this.cdl = cdl;
    }
    public void run(){
        System.out.println("Thread Start" +cdl.getCount());
        try {    
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(testCDL.class.getName()).log(Level.SEVERE, null, ex);
        }
        cdl.countDown();
    }
}

