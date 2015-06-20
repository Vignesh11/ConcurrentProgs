/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrentprogramming.Chapter1;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vignesh
 */
public class ThreadLocalWithAndWithout {
    
    public static void main(String[] args) {
        
        WithoutLocal wl = new WithoutLocal();
        for(int i = 0;i<3;i++){
            Thread t = new Thread(wl);
            t.start();
        }
        
          WithLocal ll = new WithLocal();
        for(int i = 0;i<3;i++){
            Thread t2 = new Thread(ll);
            t2.start();
        }        
    }    
}

class WithoutLocal implements Runnable {
    private Date date;   
    @Override
    public void run(){
        date = new Date();
        System.out.println("Timing Start " +date.getTime());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(WithoutLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Timing End " +date.getTime());    
    }
}

class WithLocal implements Runnable {
    
    private static ThreadLocal<Date> tl = new ThreadLocal<Date>(){
    protected Date initialValue(){
        return new Date();
    }
    };
    
     public void run(){
        System.out.println("Timing Start " +tl.get().getTime());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(WithoutLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Timing End " +tl.get().getTime());    
    }
}
