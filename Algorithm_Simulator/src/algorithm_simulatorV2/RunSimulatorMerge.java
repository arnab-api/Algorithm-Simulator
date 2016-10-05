/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * simulates Merge Sorting process
 *
 * @author Arnab Sen Sharma
 */
public class RunSimulatorMerge {

    double arr[];
    double dupli[];
    Rectangle rec[], dupliRec[];
    int len;
    static boolean flag = false;
    static Object lock = new Object();
    Text text[], dupliText[];
    SaveMergeSortState[] state;
    Text instruction = new Text();

    /**
     *
     * @param arr array of numbers to be sorted
     * @param r rectangle bars representing the values of array
     * @param d duplicate rectangles representing the values of duplicate array
     * @param txt show the values of the array
     * @param dupliText show the values of the duplicate array
     * @param state save the states of the process
     */
    RunSimulatorMerge(double[] arr, Rectangle[] r, Rectangle[] d, Text[] txt, Text dupliText[], SaveMergeSortState[] state) {
        this.arr = arr;
        rec = r;
        len = arr.length;
        dupli = new double[len];
        dupliRec = d;
        text = txt;
        this.dupliText = dupliText;
        this.state = state;
        run();
    }

    /**
     * calls Merge sorting process
     */
    public void run() {
        saveState();
        Algorithm_Simulator.threadStarted = true;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        /*for (int i = 0; i < len; i++) {
         System.out.printf(" %f", arr[i]);
         }
         System.out.println("");*/
        mergeSort(0, len - 1);
        /*for (int i = 0; i < len; i++) {
         System.out.printf(" %f", arr[i]);
         }
         System.out.println("");*/
        saveState();
    }

    /**
     * sort the data from left index to right index
     *
     * @param left left index of the array
     * @param right right index of the array
     */
    void mergeSort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(left, mid);
            mergeSort(mid + 1, right);
            merge(left, mid, right);
        }
    }

    /**
     * merge two sorted array into one sorted array
     *
     * @param left left index
     * @param mid mid index
     * @param right right index
     */
    void merge(int left, int mid, int right) {
        int lenL = mid - left + 1;
        int lenR = right - mid;
        int lpos = left;
        int rpos = mid + 1;
        int in = 0;
        for (int i = left; i <= mid; i++) {
            rec[i].setFill(Color.YELLOW);
        }
        for (int i = mid + 1; i <= right; i++) {
            rec[i].setFill(Color.ORANGE);
        }
        instruction.setText(String.format("First array [%d -> %d] && Second array [%d -> %d]", left, mid, mid + 1, right));
        saveState();
        while (lpos <= mid && rpos <= right) {
            if (flag) {
                halt();
            }
            rec[lpos].setFill(Color.DARKSALMON);
            rec[rpos].setFill(Color.BLUEVIOLET);
            saveState();
            //delayTime(500);
            if (arr[lpos] <= arr[rpos]) {
                instruction.setText(String.format("arr1[%d] = %.1f <= arr2[%d] = %.1f", lpos, arr[lpos], rpos, arr[rpos]));
                dupli[left + in] = arr[lpos];
                dupliRec[left + in].setHeight(rec[lpos].getHeight());
                dupliText[left + in].setText(String.format("%.1f", arr[lpos]));
                dupliRec[left + in].setFill(Color.YELLOW);
                rec[lpos].setFill(Color.YELLOW);
                in++;
                lpos++;
                if (lpos <= mid) {
                    rec[lpos].setFill(Color.DARKSALMON);
                }
            } else {
                instruction.setText(String.format("arr1[%d] = %.1f > arr2[%d] = %.1f", lpos, arr[lpos], rpos, arr[rpos]));
                dupli[left + in] = arr[rpos];
                dupliRec[left + in].setHeight(rec[rpos].getHeight());
                dupliText[left + in].setText(String.format("%.1f", arr[rpos]));
                dupliRec[left + in].setFill(Color.ORANGE);
                rec[rpos].setFill(Color.ORANGE);
                in++;
                rpos++;
                if (rpos <= right) {
                    rec[rpos].setFill(Color.BLUEVIOLET);
                }
            }
            //delayTime();
        }
        saveState();
        while (lpos <= mid) {
            instruction.setText("Copy rest of arr1");
            if (flag) {
                halt();
            }
            rec[lpos].setFill(Color.DARKSALMON);
            dupli[left + in] = arr[lpos];
            dupliRec[left + in].setHeight(rec[lpos].getHeight());
            dupliText[left + in].setText(String.format("%.1f", arr[lpos]));
            dupliRec[left + in].setFill(Color.YELLOW);
            rec[lpos].setFill(Color.YELLOW);
            in++;
            lpos++;
            if (lpos <= mid) {
                rec[lpos].setFill(Color.DARKSALMON);
            }
            delayTime();
        }
        while (rpos <= right) {
            instruction.setText("Copy rest of arr2");
            if (flag) {
                halt();
            }
            rec[rpos].setFill(Color.BLUEVIOLET);
            dupli[left + in] = arr[rpos];
            dupliRec[left + in].setHeight(rec[rpos].getHeight());
            dupliText[left + in].setText(String.format("%.1f", arr[rpos]));
            dupliRec[left + in].setFill(Color.ORANGE);
            rec[rpos].setFill(Color.ORANGE);
            in++;
            rpos++;
            if (rpos <= right) {
                rec[rpos].setFill(Color.BLUEVIOLET);
            }
            delayTime();
        }
        instruction.setText("Copy to main array");
        for (int i = left; i <= right; i++) {
            if (flag) {
                halt();
            }
            arr[i] = dupli[i];
            rec[i].setHeight(dupliRec[i].getHeight());
            text[i].setText(String.format("%.1f", arr[i]));
            rec[i].setFill(Color.GREEN);
            dupliRec[i].setHeight(0);
            dupliText[i].setText("--");
            //delayTime(500);
        }
        delayTime();
        for (int i = left; i <= right; i++) {
            if (flag) {
                halt();
            }
            rec[i].setFill(Color.BROWN);
        }
    }

    /**
     * save the current state and delay the process with
     * Algorithm_Simulator.timeGap
     */
    void delayTime() {
        saveState();
        try {
            Thread.sleep(Algorithm_Simulator.timeGap);
        } catch (InterruptedException ex) {
            Logger.getLogger(RunSimulatorMerge.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * halt the process
     */
    void halt() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(RunSimulatorMerge.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * pause the process
     */
    static void pause() {
        flag = true;
    }

    /**
     * resume the process
     */
    static void resume() {
        synchronized (lock) {
            flag = false;
            lock.notify();
        }
    }

    /**
     * save current state
     */
    void saveState() {
        state[MergeSortSetUp.cntState++] = new SaveMergeSortState(rec, text, dupliRec, dupliText, instruction);
    }
}
