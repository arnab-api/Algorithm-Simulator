/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * simulates Knapsack algorithm
 *
 * @author Arnab Sen Sharma
 */
public class RunKnapSack {

    Text[][] showData;
    Rectangle[][] rectangle;
    int cap, item;
    int[] weight, price;
    int[][] dp;
    int[][] path;
    static Object lock = new Object();
    static boolean pauseSimulator = false;
    Text compare;
    SaveDPstate[] state;

    /**
     * imports the necessary elements to run the process/ builds the instance
     *
     * @param showdata to show the data table of the algorithm
     * @param rect table to show data
     * @param weight weight of the items
     * @param price price of the items
     * @param cap maximum capacity
     * @param item number of items
     * @param com comparison instruction
     * @param state save the states of the process
     */
    public RunKnapSack(Text[][] showdata, Rectangle[][] rect, int[] weight, int[] price, int cap, int item, Text com, SaveDPstate[] state) {
        this.showData = showdata;
        this.rectangle = rect;
        this.weight = weight;
        this.price = price;
        this.cap = cap;
        this.item = item;
        this.state = state;
        compare = com;

        dp = new int[item + 5][cap + 5];
        path = new int[item + 5][cap + 5];
        pauseSimulator = false;
        run();
    }

    /**
     * runs the knapsack process
     */
    public void run() {
        saveState();
        Algorithm_Simulator.threadStarted = true;
        int i, j;
        System.out.printf("Item = %d && cap = %d\n", item, cap);
        for (i = 0; i <= cap; i++) {
            dp[0][i] = 0;
        }
        for (i = 0; i <= item; i++) {
            dp[i][0] = 0;
        }

        for (i = 1; i <= item; i++) {
            for (j = 1; j <= cap; j++) {
                System.out.printf("(%d %d)\n", i, j);
                rectangle[i][j].setFill(Color.RED);
                compare.setText("Compare : ");
                threadDelay();
                if (weight[i] <= j) {
                    dp[i][j] = Math.max(dp[i - 1][j], price[i] + dp[i - 1][j - weight[i]]);
                    int ax = i - 1, ay = j;
                    int bx = i - 1, by = j - weight[i];

                    compare.setText(String.format("Compare : %d+%d = %d or %d", price[i], dp[i - 1][j - weight[i]], price[i] + dp[i - 1][j - weight[i]], dp[i - 1][j]));
                    Paint pa = rectangle[ax][ay].getFill();
                    Paint pb = rectangle[bx][by].getFill();
                    if (ax > 0 && ay > 0) {
                        rectangle[ax][ay].setFill(Color.BLUEVIOLET);
                    }
                    if (bx > 0 && by > 0) {
                        rectangle[bx][by].setFill(Color.BLUEVIOLET);
                    }
                    threadDelay();
                    if (price[i] + dp[i - 1][j - weight[i]] > dp[i - 1][j]) {
                        if(bx > 0 && by > 0) rectangle[bx][by].setFill(Color.GREENYELLOW);
                        compare.setText(compare.getText() + String.format(" >>> %d", price[i] + dp[i - 1][j - weight[i]]));
                    } else if (ax > 0 && ay > 0) {
                        rectangle[ax][ay].setFill(Color.GREENYELLOW);
                        compare.setText(compare.getText() + String.format(" >>> %d", dp[i - 1][j]));
                    }
                    if (dp[i][j] > dp[i - 1][j]) {
                        path[i][j] = 1;
                    }
                    showData[i][j].setText(String.format("%d(%d)", dp[i][j], path[i][j]));
                    threadDelay();

                    rectangle[ax][ay].setFill(pa);
                    rectangle[bx][by].setFill(pb);
                } else {
                    dp[i][j] = dp[i - 1][j];

                    compare.setText("weight of the item < j");
                    rectangle[i][j].setFill(Color.GREENYELLOW);
                    showData[i][j].setText(String.format("%d(0)", dp[i][j]));
                    threadDelay();
                }

                rectangle[i][j].setFill(Color.BURLYWOOD);
            }
        }
        saveState();
    }

    /**
     * halt the process
     */
    void halt() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(RunBFS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * pause the process
     */
    static void pause() {
        pauseSimulator = true;
    }

    /**
     * resume the process
     */
    static void play() {
        pauseSimulator = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    /**
     * save the current state and delay the process with
     * Algorithm_Simulator.timeGap
     */
    void threadDelay() {
        saveState();
        if (pauseSimulator) {
            halt();
        }
        try {
            Thread.sleep(Algorithm_Simulator.timeGap);

        } catch (InterruptedException ex) {
            Logger.getLogger(RunBFS.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pauseSimulator) {
            halt();
        }
    }

    /**
     * save current state
     */
    void saveState() {
        state[KnapSackBuildUp.cntState++] = new SaveDPstate(rectangle, showData, item + 1, cap + 1, compare);
    }
}
