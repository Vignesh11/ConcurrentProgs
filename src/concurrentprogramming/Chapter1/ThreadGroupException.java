/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrentprogramming.Chapter1;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vignesh
 */
public class ThreadGroupException {
    
    public static void main(String[] args) {
        Result res = new Result();
        MyThreadGroup myGroup = new MyThreadGroup("Tester");
        for(int i = 0;i<5;i++){
            Thread t = new Thread(myGroup ,new ThreadRunner(res));
            t.start();
        }
    }
    
    
    
}

 class MyThreadGroup extends ThreadGroup {

    public MyThreadGroup(String name) {
        super(name);
    }
    
    @Override
    public void uncaughtException(Thread t,Throwable e){
        System.out.println("Uncaught Exception" );
        e.printStackTrace();
        System.out.println("Terminating the threads");
        interrupt();
    }
}
class ThreadRunner implements Runnable {
    private Result result;
    public ThreadRunner(Result result){
        this.result = result;
    }

    @Override
    public void run() {
        while(true){
        doSomething();
        result.setName(Thread.currentThread().getName());
        System.out.println("Finisher"+result.getName());
        if(Thread.interrupted()){
         System.out.println("Being interrupted");
        }
    }  
  }
    private void doSomething() {
        Random random = new Random();
           int i =  10 / random.nextInt(10);       
    }
}
class Result {
    private String name ;   
    public Result() {        
    }
    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
