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
 * run Bubble Sort simulation
 *
 * @author Arnab Sen Sharma
 */
public class RunSimulatorBubble {

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
    RunSimulatorBubble(Rectangle[] r, double[] ara, VBox[] stk, Text txt[], SaveNsqrSortState[] state) {
        barchart = r;
        arr = ara;
        stack = stk;
        text = txt;
        this.state = state;
        this.instruction = instruction;
        run();
    }

    /**
     * run bubble sort process
     */
    public void run() {
        instruction.setVisible(true);
        saveState();
        Algorithm_Simulator.threadStarted = true;
        if (!Thread.currentThread().isInterrupted()) {
            for (int i = 0; i < arr.length - 1; i++) {
                barchart[i].setFill(Color.ORANGE);
                saveState();
                for (int j = i + 1; j < arr.length; j++) {
                    if (shouldHalt) {
                        halt();
                    }
                    //barchart[i].setFill(Color.ORANGE);
                    if (arr[i] > arr[j]) {
                        barchart[j].setFill(Color.RED);
                        instruction.setText(String.format("arr[%d] = %.1f > arr[%d] = %.1f >>> swap(arr[%d],arr[%d])", i, arr[i], j, arr[j], i, j));
                    } else {
                        barchart[j].setFill(Color.GREEN);
                        instruction.setText(String.format("arr[%d] = %.1f <= arr[%d] = %.1f >>> OK", i, arr[i], j, arr[j]));
                    }
                    saveState();

                    threadDelay(500);

                    if (arr[i] > arr[j]) {

                        double tempHeight, temp;
                        tempHeight = barchart[i].getHeight();
                        barchart[i].setHeight(barchart[j].getHeight());
                        barchart[j].setHeight(tempHeight);

                        temp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp;

                        text[i].setText(String.format("%.1f", arr[i]));
                        text[j].setText(String.format("%.1f", arr[j]));
                        saveState();
                    }

                    //barchart[i].setFill(Color.BLACK);
                    barchart[j].setFill(Color.CYAN);
                    threadDelay(500);
                }
                barchart[i].setFill(Color.CYAN);
                instruction.setVisible(false);
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
     * save the current state and delay the process with
     * Algorithm_Simulator.timeGap
     */
    void threadDelay(int time) {
        //SaveState();
        try {
            Thread.sleep(Algorithm_Simulator.timeGap);

        } catch (InterruptedException ex) {
            Logger.getLogger(RunSimulatorBubble.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
