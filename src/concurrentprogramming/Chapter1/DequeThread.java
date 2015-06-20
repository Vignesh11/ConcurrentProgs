/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrentprogramming.Chapter1;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vignesh
 */
public class DequeThread {
    
    public static void main(String[] args) {
        
        Deque d = new ArrayDeque<Event>(100);
        for(int i = 0;i<3;i++){
            Thread t = new Thread(new WriterTask(d));
            t.start();
        }
        Thread t2 = new Thread(new CleanerTask(d));
        t2.start();
        
    }
}

class Event {
    private Date date;
    String name;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}

class WriterTask implements Runnable {
    
    private Deque<Event> deque;
    
    public WriterTask(Deque<Event> deque){
        this.deque = deque;
    }

    @Override
    public void run() {
        for(int i = 0;i<100;i++) {           
                Event e = new Event();
                e.setDate(new Date());
                e.setName("Event" +i);
                deque.add(e);
                try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(WriterTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

class CleanerTask implements Runnable {
  private Deque<Event> deque;
    
    public CleanerTask(Deque<Event> deque){
        this.deque = deque;
    }  

    @Override
    public void run() {
        while(true){
        Date d = new Date();
        boolean flag = false;
        System.out.println("deque size" +deque.size());
        if(deque.size() > 0){
       Event e = deque.getLast();
        if (d.getTime() - e.getDate().getTime() > 10000){
        deque.removeLast();
        flag = true;
       }
        }
       if(flag){
         System.out.println("deque size after removal" +deque.size());  
       }
    }
    }
}
