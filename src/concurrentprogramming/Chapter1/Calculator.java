

package concurrentprogramming.Chapter1;
/**
 *
 * @author Vignesh
 */
public class Calculator { 
    public static void main(String[] args) {
        for(int i =1 ;i<10;i++){
            Processor process = new Processor(i);
            Thread t1 = new Thread(process);
            t1.start();     
        }
    }
}

class Processor implements Runnable {
    private int number;
    public Processor(int number){
        this.number = number;
    }
    @Override
    public void run() {
       for(int i = 1;i<10;i++){
           System.out.printf(" %s : %d * %d = %d  \n", Thread.currentThread().getName(),i,number,i*number);
       }
    }    
}