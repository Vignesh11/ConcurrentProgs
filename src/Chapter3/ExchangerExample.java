/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vignesh
 */
public class ExchangerExample {
         public static void main(String[] args) {
        
            List<Integer> buffer1 = new ArrayList<Integer>();
             List<Integer> buffer2 = new ArrayList<Integer>();
             Exchanger<List<Integer>> exchange1 = new Exchanger<>();
             Producer p = new Producer(buffer1,exchange1);
             Consumer c = new Consumer(buffer2,exchange1);
             
             Thread t1 = new Thread(p);
             Thread t2 = new Thread(c);
             t1.start();
             
             t2.start();
             
    }
       
    
}

class Producer implements Runnable {
      private List<Integer> buffer;
      private Exchanger<List<Integer>> exchange;
      
      public Producer(List<Integer> buffer, Exchanger<List<Integer>> exchange){
          this.buffer = buffer;
          this.exchange = exchange;
      }
    @Override
    public void run() {
        for (int i = 0 ; i< 10; i++){
            for (int j = 0;j<10;j++){
                System.out.println("Cycle " + i + "item " + j);
                buffer.add(j);                
            }
            try {
                buffer = exchange.exchange(buffer);               
                System.out.println("Prod buffer size after exchange cyle " + i + " is " +buffer.size());
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}

class Consumer implements Runnable {
      private List<Integer> buffer;
      private Exchanger<List<Integer>> exchange;
      
      public  Consumer(List<Integer> buffer, Exchanger<List<Integer>> exchange){
          this.buffer = buffer;
          this.exchange = exchange;
      }
    @Override
    public void run() {
        for (int i = 0 ; i< 10; i++){
           // for (int j = 0;j<10;j++){
                System.out.println("Cycle " + i );
              //  buffer.add(j);                
          //  }
            try {
                buffer = exchange.exchange(buffer);               
                System.out.println("Consumer buffer size after exchange cyle " + i + " is " +buffer.size());
                buffer.clear();
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
