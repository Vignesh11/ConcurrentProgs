
package concurrentprogramming.Chapter1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vignesh
 */
public class InterruptingThread extends Thread {
    
    @Override
    public void run(){
        int num = 1;
        while(true){
            if(isPrime(num)){ 
                System.out.println("Prime num" +num);
            }
            if(Thread.interrupted()){
                System.out.println(" Task Interrupted");
                return;
            }
            num++;
        }
    }

    private boolean isPrime(int num) {
       if (num ==1 || num == 2){
           return true;       
       } 
   for(int i =2 ;i<num;i++){
       if (num % i == 0)
           return false;      
   }
         return true;
    }
    
    public static void main(String[] args) {
        
        Thread t1 = new Thread(new InterruptingThread());
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(InterruptingThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        t1.interrupt();
    }
}
