/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * simulate insertion sorting process
 *
 * @author Arnab Sen Sharma
 */
public class RunSimulatorInsertion {

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
     *
     * @param r rectangle bars representing the values of array
     * @param ara array of values to be sorted
     * @param stk VBox to set up the barchart
     * @param txt show the values
     * @param state save the states of the process
     */
    RunSimulatorInsertion(Rectangle[] r, double[] ara, VBox[] stk, Text txt[], SaveNsqrSortState[] state) {
        barchart = r;
        arr = ara;
        stack = stk;
        text = txt;
        this.state = state;
        run();
    }

    /**
     * run insertion sorting process
     */
    public void run() {
        Algorithm_Simulator.threadStarted = true;
        saveState();
        if (!Thread.currentThread().isInterrupted()) {
            //for(int i=0;i<arr.length;i++) System.out.printf(" %.1f",arr[i]);
            //System.out.println("");
            for (int i = 0; i < arr.length; i++) {
                if (shouldHalt) {
                    halt();
                }
                int j = i - 1;
                double save = barchart[i].getHeight();
                double saved_val = arr[i];
                barchart[i].setFill(Color.BLUE);
                instruction.setText(String.format("arr[%d] = %.1f", i, arr[i]));
                saveState();
                threadDelay(500);
                if (shouldHalt) {
                    halt();
                }
                while (j >= 0 && arr[j] > arr[i]) {
                    if (shouldHalt) {
                        halt();
                    }
                    barchart[j].setFill(Color.ORANGE);
                    instruction.setText(String.format("arr[%d] = %.1f < arr[%d] = %.1f >>> CONTINUE", i, arr[i], j, arr[j]));
                    saveState();
                    threadDelay(500);
                    if (shouldHalt) {
                        halt();
                    }
                    j--;
                }
                if (j >= 0) {
                    barchart[j].setFill(Color.RED);
                    instruction.setText(String.format("arr[%d] = %.1f >= arr[%d] = %.1f >>> STOP", i, arr[i], j, arr[j]));
                    saveState();
                }
                if (shouldHalt) {
                    halt();
                }
                threadDelay(500);
                if (shouldHalt) {
                    halt();
                }
                j++;
                boolean flg = false;
                for (int k = i; k > j; k--) {
                    flg = true;
                    barchart[k].setHeight(barchart[k - 1].getHeight());
                    arr[k] = arr[k - 1];
                    text[k].setText(String.format("%.1f", arr[k]));
                    barchart[k].setFill(Color.ORANGE);
                }
                barchart[j].setHeight(save);
                arr[j] = saved_val;
                text[j].setText(String.format("%.1f", arr[j]));
                barchart[j].setFill(Color.BLUE);
                if (flg) {
                    instruction.setText(String.format("Insert %.1f to position %d", saved_val, j));
                    saveState();
                }
                if (shouldHalt) {
                    halt();
                }
                threadDelay(1000);
                if (shouldHalt) {
                    halt();
                }
                for (int k = 0; k < arr.length; k++) {
                    barchart[k].setFill(Color.CYAN);
                }
                saveState();
            }
        }
    }

    /**
     * save current state
     */
    public void saveState() {
        state[NsqrSortSetUp.cntState++] = new SaveNsqrSortState(barchart, text, instruction);
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
