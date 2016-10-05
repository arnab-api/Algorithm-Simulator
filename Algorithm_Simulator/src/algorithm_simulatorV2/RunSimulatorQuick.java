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
 * simulates Quick sort algorithm
 *
 * @author Arnab Sen Sharma
 */
public class RunSimulatorQuick {

    Rectangle barchart[];
    double arr[];
    static boolean shouldHalt = false;
    static Object lock = new Object();
    VBox stack[], stkk;
    Text text[];
    boolean set[];
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
    RunSimulatorQuick(Rectangle[] r, double[] ara, VBox[] stk, Text txt[], SaveNsqrSortState[] state) {
        barchart = r;
        arr = ara;
        stack = stk;
        text = txt;
        set = new boolean[arr.length];
        this.state = state;
        run();
    }

    /**
     * runs quick sorting process
     */
    public void run() {
        Algorithm_Simulator.threadStarted = true;
        saveState();
        QuickSort(0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("%.1f ", arr[i]);
        }
        System.out.println("");
        for (int i = 0; i < arr.length; i++) {
            barchart[i].setFill(Color.GREEN);
        }
    }

    /**
     * sort the data from (from) to (to)
     *
     * @param from left index of the array
     * @param to right index of the array
     */
    void QuickSort(int from, int to) {
        if (from == to) {
            barchart[from].setFill(Color.GREENYELLOW);
            set[to] = true;
            threadDelay(500);
            instruction.setText(String.format("Value %.1f for position %d is fixed",arr[from], from));
            saveState();
        }
        if (from < to) {
            threadDelay(500);
            for (int i = from; i <= to; i++) {
                barchart[i].setFill(Color.ORANGE);
            }
            instruction.setText(String.format("Current Sub-array pos %d to %d", from, to));
            saveState();
            threadDelay(500);
            int q = find_partition(from, to);
            QuickSort(from, q - 1);
            QuickSort(q + 1, to);
        }
    }

    /**
     * find position of the pivot = arr[to] in the sorted array and divides the
     * array in two arrays
     *
     * @param from left index of the array
     * @param to right index of the array
     * @return position of the pivot = arr[to] in the sorted array
     */
    int find_partition(int from, int to) {
        double pivot = arr[to];
        barchart[to].setFill(Color.BLUE);
        barchart[from].setFill(Color.DEEPPINK);
        instruction.setText(String.format("Pivot -> arr[%d] = %.1f & Current pos = %d", to, arr[to], from));
        saveState();
        if (shouldHalt) {
            halt();
        }
        threadDelay(500);
        if (shouldHalt) {
            halt();
        }
        int pos = from, i;
        for (i = from; i < to; i++) {
            barchart[i].setFill(Color.BROWN);
            saveState();
            if (shouldHalt) {
                halt();
            }
            threadDelay(500);
            if (shouldHalt) {
                halt();
            }
            if (arr[i] < pivot) {
                barchart[i].setFill(Color.RED);
                instruction.setText(String.format("Pivot -> arr[%d] = %.1f & arr[%d] = %.1f < pivot", to, arr[to], i, arr[i]));
                saveState();
                if (shouldHalt) {
                    halt();
                }
                threadDelay(500);
                if (shouldHalt) {
                    halt();
                }
                swap(pos, i);
                barchart[pos].setFill(Color.RED);
                //SaveState();
                //threadDelay(500);
                if (pos != i) {
                    barchart[i].setFill(Color.ORANGE);
                    //SaveState();
                }
                barchart[pos + 1].setFill(Color.DEEPPINK);
                instruction.setText(String.format("swap(arr[%d],arr[%d]) & Current pos = %d", pos, i, pos + 1));
                saveState();
                if (shouldHalt) {
                    halt();
                }
                threadDelay(500);
                if (shouldHalt) {
                    halt();
                }
                pos++;
                //barchart[pos].setFill(Color.DEEPPINK);
                //threadDelay(500);
            } else {
                barchart[i].setFill(Color.ORANGE);
                instruction.setText(String.format("Pivot -> arr[%d] = %.1f & arr[%d] = %.1f >= pivot", to, arr[to], i, arr[i]));
                //SaveState();
                //threadDelay(500);
            }
        }
        instruction.setText(String.format("swap(arr[%d],arr[%d])", pos, to));
        saveState();
        barchart[pos].setFill(Color.ORANGE);
        //SaveState();
        if (shouldHalt) {
            halt();
        }
        threadDelay(500);
        if (shouldHalt) {
            halt();
        }
        swap(pos, to);
        barchart[pos].setFill(Color.BLUE);
        barchart[to].setFill(Color.ORANGE);
        //SaveState();
        if (shouldHalt) {
            halt();
        }
        threadDelay(500);
        if (shouldHalt) {
            halt();
        }
        barchart[pos].setFill(Color.GREENYELLOW);
        instruction.setText(String.format("Value %.1f for position %d is fixed",arr[pos], pos));
        set[pos] = true;
        for (i = 0; i < arr.length; i++) {
            if (!set[i]) {
                barchart[i].setFill(Color.CYAN);
            }
        }
        saveState();
        if (shouldHalt) {
            halt();
        }
        threadDelay(500);
        if (shouldHalt) {
            halt();
        }
        return pos;
    }

    /**
     * swap two values
     *
     * @param x value 1
     * @param y value 2
     */
    void swap(int x, int y) {
        double temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;

        temp = barchart[x].getHeight();
        barchart[x].setHeight(barchart[y].getHeight());
        barchart[y].setHeight(temp);

        text[x].setText(String.format("%.1f", arr[x]));
        text[y].setText(String.format("%.1f", arr[y]));
    }

    /**
     * save current state
     */
    public void saveState() {
        state[NsqrSortSetUp.cntState++] = new SaveNsqrSortState(barchart, text, instruction);
    }

    /**
     * delay the process with Algorithm_Simulator.timeGap
     */
    void threadDelay(long time) {
        try {
            Thread.sleep(Algorithm_Simulator.timeGap);
        } catch (InterruptedException ex) {
            Logger.getLogger(RunSimulatorQuick.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * pause the process
     */
    static void pause() {
        shouldHalt = true;
    }

    /**
     * halt the process
     */
    void halt() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(RunSimulatorQuick.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
}
