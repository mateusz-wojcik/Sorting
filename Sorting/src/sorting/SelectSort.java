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
public class SelectSort extends Thread {
    
    private JProgressBar progressBar;
    private JLabel timeLabel;
    private int array[];
    private int currentPercent;
    private long onePercent;
    private long totalOperations;
    private boolean isStopped;
    private long waitingTime;
    
    public SelectSort(int[] array, JProgressBar progressBar, JLabel timeLabel){
        this.progressBar = progressBar;
        this.timeLabel = timeLabel;
        this.array = array;
        this.currentPercent = 0;
        this.totalOperations = array.length;
        this.onePercent = totalOperations / 100;
    }
    
    public int findMax(int n){
        int max = 0;
        for(int i = 0; i < n; i++){
            if(array[i] > array[max]) {
                max = i;
            }
        }
        return max;
    }
    
    public void swap(int firstIndex, int secondIndex){
        int tmp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = tmp;
    }
    
    public void sort(){
        long start = System.currentTimeMillis();
        
        long operationsDone = 0;
        for(int i = 0; i < array.length; i++){
            
            if(isStopped) stopThread();
            if(isInterrupted()) return;
            
            swap(array.length - (1 + i), findMax(array.length - (1 + i)));
            if(++operationsDone == onePercent){
                progressBar.setValue(++currentPercent);                                      
                operationsDone = 0;
            }
        }
        
        long timePassed = System.currentTimeMillis() - start - waitingTime;
        timeLabel.setText(Long.toString(timePassed) + " ms");
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
