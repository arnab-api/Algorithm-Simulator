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
 * simulates LCS algorithm
 *
 * @author Arnab Sen Sharma
 */
public class RunLCS {

    String word1, word2;
    int len1, len2, DP[][], path[][];
    Text showData[][];
    Rectangle[][] rectangle;
    Rectangle[] w1_rr, w2_rr;
    Text[] w1_tt, w2_tt;
    static Object lock = new Object();
    static boolean pauseSimulator = false;
    Text ans;
    String lcs;

    int left = 2190;
    int up = 2191;
    int corner = 2196;
    SaveDPstate[] state;
    boolean visibility = false;

    /**
     * imports all necessary elements to run the process/ builds the instance
     *
     * @param w1 word 1
     * @param w2 word 2
     * @param len1 length of word 1
     * @param len2 length of word 2
     * @param showData to show the data table of the algorithm
     * @param rec table to show data
     * @param w1_rr rectangle array to show word 1
     * @param w2_rr rectangle array to show word 2
     * @param state save states of the process
     */
    RunLCS(String w1, String w2, int len1, int len2, Text[][] showData, Rectangle[][] rec, Rectangle[] w1_rr, Rectangle[] w2_rr, Text ans, SaveDPstate[] state) {
        this.word1 = w1;
        this.word2 = w2;
        this.len1 = len1;
        this.len2 = len2;
        this.showData = showData;
        this.rectangle = rec;
        DP = new int[len1 + 2][len2 + 2];
        path = new int[len1 + 2][len2 + 2];
        this.w1_rr = w1_rr;
        this.w2_rr = w2_rr;
        this.w1_tt = w1_tt;
        this.w2_tt = w2_tt;
        this.ans = ans;
        this.state = state;
        lcs = "";

        System.out.printf("len1 = %d && len2 = %d\n", len1, len2);
        for (int i = 0; i < word1.length(); i++) {
            System.out.printf("%d(%c)", i, word1.charAt(i));
        }
        System.out.println("");
        for (int i = 0; i < word2.length(); i++) {
            System.out.printf("%d(%c)", i, word2.charAt(i));
        }
        System.out.println("");
        run();
    }

    /**
     * runs LCS process
     */
    public void run() {
        Algorithm_Simulator.threadStarted = true;
        saveState();
        ans.setVisible(false);
        for (int i = 0; i <= len1; i++) {
            DP[i][0] = 0;
            showData[i][0].setText("0");
            rectangle[i][0].setFill(Color.BURLYWOOD);
            threadDelay();
        }
        for (int i = 0; i <= len2; i++) {
            DP[0][i] = 0;
            showData[0][i].setText("0");
            rectangle[0][i].setFill(Color.BURLYWOOD);
        }
        threadDelay();
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                rectangle[i][j].setFill(Color.RED);
                rectangle[i - 1][j].setFill(Color.YELLOW);
                rectangle[i][j - 1].setFill(Color.YELLOW);
                rectangle[i - 1][j - 1].setFill(Color.YELLOW);
                threadDelay();
                if (word1.charAt(i) == word2.charAt(j)) {
                    DP[i][j] = 1 + DP[i - 1][j - 1];
                    rectangle[i - 1][j - 1].setFill(Color.YELLOWGREEN);
                    path[i][j] = corner;
                    w1_rr[i].setFill(Color.GREEN);
                    w2_rr[j].setFill(Color.GREEN);
                } else if (DP[i - 1][j] > DP[i][j - 1]) {
                    DP[i][j] = DP[i - 1][j];
                    rectangle[i - 1][j].setFill(Color.YELLOWGREEN);
                    path[i][j] = up;
                } else {
                    DP[i][j] = DP[i][j - 1];
                    rectangle[i][j - 1].setFill(Color.YELLOWGREEN);
                    path[i][j] = left;
                }
                threadDelay();
                if (path[i][j] == left) {
                    showData[i][j].setText(String.format("%d(%s)", DP[i][j], "\u2190"));
                } else if (path[i][j] == up) {
                    showData[i][j].setText(String.format("%d(%s)", DP[i][j], "\u2191"));
                } else if (path[i][j] == corner) {
                    showData[i][j].setText(String.format("%d(%s)", DP[i][j], "\u2196"));
                }
                threadDelay();
                rectangle[i][j].setFill(Color.BURLYWOOD);
                rectangle[i - 1][j].setFill(Color.BURLYWOOD);
                rectangle[i][j - 1].setFill(Color.BURLYWOOD);
                rectangle[i - 1][j - 1].setFill(Color.BURLYWOOD);
                w1_rr[i].setFill(Color.BLUEVIOLET);
                w2_rr[j].setFill(Color.BLUEVIOLET);
                threadDelay();
            }
        }
        threadDelay();
        ans.setVisible(true);
        visibility = true;
        printLCS(len1, len2);
        saveState();
    }

    /**
     * prints a possible LCS
     *
     * @param i row index
     * @param j column index
     */
    void printLCS(int i, int j) {
        rectangle[i][j].setFill(Color.CORNSILK);
        if (i == 0 || j == 0) {
            return;
        }
        threadDelay();
        if (path[i][j] == corner) {
            lcs = String.format("%c", word1.charAt(i)) + lcs;
            ans.setText(String.format("A possible LCS : ") + lcs);
            rectangle[i][j].setFill(Color.GREENYELLOW);
            threadDelay();
            printLCS(i - 1, j - 1);
        } else if (path[i][j] == up) {
            printLCS(i - 1, j);
        } else if (path[i][j] == left) {
            printLCS(i, j - 1);
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
        state[LCSorEditDisSetUp.cntState++] = new SaveDPstate(rectangle, showData, len1 + 1, len2 + 1, ans, visibility, w1_rr, w2_rr);
    }
}
