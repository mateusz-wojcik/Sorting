/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
/**
 *
 * @author Rudy
 */
public class BubbleSort extends Thread {
    
    private JProgressBar progressBar;
    private JLabel timeLabel;
    private int array[];
    private int currentPercent;
    private long onePercent;
    private long totalOperations;
    private boolean isStopped;
    private long waitingTime;
    
    public BubbleSort(int array[], JProgressBar progressBar, JLabel timeLabel){
        this.progressBar = progressBar;
        this.timeLabel = timeLabel;
        this.array = array;
        this.currentPercent = 0;
        this.totalOperations = array.length;
        this.onePercent = totalOperations / 100;
    }
    
    public void sort(){
        long start = System.currentTimeMillis();
        
        long operationsDone = 0;
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array.length - 1; j++){
                
                if(isStopped) stopThread();
                if(isInterrupted()) return;
                
                if(array[j] > array[j + 1]) {
                    swap(j, j+1);
                }
            }
        if(++operationsDone == onePercent){
            progressBar.setValue(++currentPercent);                                      
            operationsDone = 0;
        }
        }
        
        long timePassed = System.currentTimeMillis() - start - waitingTime;
        timeLabel.setText(Long.toString(timePassed) + " ms");
    }
    
    public void swap(int first, int second){
        int tmp = first;
        array[first] = array[second];
        array[second] = tmp;
    }
    
    public void display(){
        for(int i = 0; i < array.length; i++){
            System.out.println(i + " " + array[i]);
        }
    }
    
    @Override
    public void run(){
        sort();
    }
    
    public void cleanup(){
        timeLabel.setText("???");
        progressBar.setValue(0);
    }
    
    public void terminate(){
        if(!isInterrupted()){
            interrupt();
            cleanup();
        }
    }
    
    public synchronized void stopThread(){
        long start = System.currentTimeMillis();
        
        while(isStopped){
          try {
            Thread.sleep(1);
            //wait();
          } catch (InterruptedException ex) {
            Logger.getLogger(BubbleSort.class.getName()).log(Level.SEVERE, null, ex);
          }    
        }
        
        waitingTime += System.currentTimeMillis() - start;
    }
    
    public void setStopped(boolean arg) {
        this.isStopped = arg;
    }
}
