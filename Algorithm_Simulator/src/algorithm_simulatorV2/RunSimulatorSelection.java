/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * simulate selection sort algorithm
 * @author Arnab Sen Sharma
 */
public class RunSimulatorSelection{

    Rectangle barchart[];
    double arr[];
    static boolean shouldHalt = false;
    static Object lock = new Object();
    VBox stack[], stkk;
    Text text[];
    SaveNsqrSortState[] state;
    Text instruction = new Text();

    /**
     * import all necessary components to run the process
     * @param r rectangle bars representing the values of array 
     * @param ara array of values to be sorted
     * @param stk VBox to set up the barchart
     * @param txt show the values
     * @param state save the states of the process
     */
    RunSimulatorSelection(Rectangle[] r, double[] ara, VBox[] stk, Text txt[],SaveNsqrSortState[] state) {
        barchart = r;
        arr = ara;
        stack = stk;
        text = txt;
        this.state = state;
        run();
    }

    /**
     * runs selection sorting process
     */
    public void run() {
        Algorithm_Simulator.threadStarted = true;
        saveState();
        if (!Thread.currentThread().isInterrupted()) {
            double cur_min = 10101010;
            int min_pos = 0;
            for (int i = 0; i < arr.length - 1; i++) {
                min_pos = i;
                cur_min = arr[i];
                instruction.setText(String.format("Current minimum = %.1f at position %d",cur_min,min_pos));
                barchart[min_pos].setFill(Color.RED);
                saveState();
                threadDelay(500);
                if(shouldHalt) halt();
                for (int j = i + 1; j < arr.length; j++) {
                    if(shouldHalt) halt();
                    barchart[j].setFill(Color.ORANGE);
                    saveState();
                    threadDelay(500);
                    if (arr[j] < cur_min) {
                        cur_min = arr[j];
                        if(min_pos!=i) barchart[min_pos].setFill(Color.CYAN);
                        min_pos = j;
                        barchart[min_pos].setFill(Color.BLUE);
                        instruction.setText(String.format("Current minimum = %.1f at position %d",cur_min,min_pos));
                        threadDelay(500);
                    }
                    else barchart[j].setFill(Color.CYAN);
                    saveState();
                    if(shouldHalt) halt();
                }
                instruction.setText(String.format("swap(arr[%d],arr[%d])",i,min_pos));
                double tempHeight, temp;
                tempHeight = barchart[i].getHeight();
                barchart[i].setHeight(barchart[min_pos].getHeight());
                barchart[min_pos].setHeight(tempHeight);

                temp = arr[i];
                arr[i] = arr[min_pos];
                arr[min_pos] = temp;

                text[i].setText(String.format("%.1f", arr[i]));
                text[min_pos].setText(String.format("%.1f", arr[min_pos]));
                barchart[i].setFill(Color.GREENYELLOW);
                if(min_pos!=i) barchart[min_pos].setFill(Color.CYAN);
                threadDelay(500);
                if(shouldHalt) halt();
                saveState();
            }
            barchart[arr.length-1].setFill(Color.GREENYELLOW);
            saveState();
        }
    }

    /**
     * save current state
     */
    public void saveState(){
        state[NsqrSortSetUp.cntState++] = new SaveNsqrSortState(barchart, text,instruction);
    }
    
    /**
     * halt the process
     */
    private void halt() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * pause the process
     */
    static void pause() {
        shouldHalt = true;
    }

    /**
     * resume the process
     */
    static void resume() {
        synchronized (lock) {
            shouldHalt = false;
            lock.notify();
        }
    }

    /**
     * delay the process with Algorithm_Simulator.timeGap
     */
    void threadDelay(int time) {
        try {
            Thread.sleep(Algorithm_Simulator.timeGap);

        } catch (InterruptedException ex) {
            Logger.getLogger(RunSimulatorBubble.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
