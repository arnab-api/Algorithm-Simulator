/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * simulates SCC_tarzan algorithm
 *
 * @author Arnab Sen Sharma
 */
public class RunSCC_tarzan {

    Circle[] circle;
    Stack stack = new Stack();
    int srce = 1;
    boolean[][] vec;
    Text[] StackUpdate, dfn;
    static Object lock;
    static boolean pauseSimulator;
    int[] finding, exiting, belong;
    int cnt, depth, scc;
    SaveGraphState state[];
    boolean queueVis[] = new boolean[1010];

    /**
     * imports all necessary elements to run the process
     *
     * @param circle array of circles representing nodes
     * @param q stack
     * @param vec boolean 2D array to represent the connectivity of the graph
     * @param text show current state of queue
     * @param dfn to indicate the finding/discovery time of a node
     * @param state save the states of the simulation
     */
    RunSCC_tarzan(Circle[] circle, Stack q, boolean[][] vec, Text[] text, int[] finding, int[] exiting, Text[] dfn, SaveGraphState state[]) {
        this.circle = circle;
        this.state = state;
        stack = q;
        this.vec = vec;
        this.StackUpdate = text;
        this.finding = finding;
        this.exiting = exiting;
        this.dfn = dfn;
        while (!stack.empty()) {
            stack.pop();
        }
        lock = new Object();
        pauseSimulator = false;
        cnt = DFSSetUp.cnt;
        depth = 0;
        belong = new int[cnt + 5];
        for (int i = 0; i < 1010; i++) {
            queueVis[i] = false;
        }
        run();
    }

    /**
     * initialize everything and run SCC_tarzan process
     */
    public void run() {
        saveState();
        Algorithm_Simulator.threadStarted = true;
        for (int i = 1; i <= cnt; i++) {
            finding[i] = -1;
            exiting[i] = -1;
            belong[i] = -1;
        }
        for (int i = 1; i <= cnt; i++) {
            if (belong[i] == -1) {
                tarzan(i);
            }
        }
    }

    /**
     * run SCC_tarzan process for current node
     *
     * @param u node on which the algorithm is working
     */
    void tarzan(int u) {
        finding[u] = exiting[u] = depth++;
        dfn[u].setText(String.format("%d/oo", finding[u]));
        stack.push(u);
        circle[u].setFill(Color.BROWN);
        updateScene();
        threadDelay();
        for (int i = 1; i <= cnt; i++) {
            if (vec[u][i]) {
                int v = i;
                if (exiting[v] == -1) {
                    circle[u].setFill(Color.ORANGE);
                    tarzan(v);
                    circle[u].setFill(Color.GREENYELLOW);
                    finding[u] = Integer.min(finding[u], finding[v]);
                    dfn[u].setText(String.format("%d/%d", finding[u], exiting[u]));
                    threadDelay();
                } else if (belong[v] == -1) {
                    circle[v].setFill(Color.ORANGE);
                    threadDelay();
                    finding[u] = Integer.min(finding[u], exiting[v]);
                    circle[u].setFill(Color.GREENYELLOW);
                    dfn[u].setText(String.format("%d/%d", finding[u], exiting[u]));
                    threadDelay();
                }
            }
            dfn[u].setText(String.format("%d/%d", finding[u], exiting[u]));
        }

        if (finding[u] == exiting[u]) {
            int v;
            scc++;
            circle[u].setFill(Color.BLUE);
            threadDelay();
            do {
                v = (int) stack.peek();
                stack.pop();
                belong[v] = scc;
                circle[v].setFill(Color.BLUE);
                updateScene();
                threadDelay();
            } while (v != u);
            updateSccGroup(scc);
            threadDelay();
        }
    }

    /**
     * update Stack
     */
    void updateScene() {
        System.out.println(stack.toString());
        Object[] arr = stack.toArray();
        for (int i = 0; i < arr.length; i++) {
            System.out.print(" " + arr[i].toString());
        }
        System.out.println("");

        for (int i = 0; i < 1010; i++) {
            StackUpdate[i].setVisible(false);
            queueVis[i] = false;
        }
        for (int i = 0; i < arr.length; i++) {
            StackUpdate[i].setText(arr[arr.length - 1 - i].toString());
            StackUpdate[i].setVisible(true);
            queueVis[i] = true;
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

    void updateSccGroup(int scc_no) {
        for (int i = 1; i <= cnt; i++) {
            if (belong[i] == scc_no) {
                if (scc_no == 1) {
                    circle[i].setFill(Color.AQUA);
                } else if (scc_no == 2) {
                    circle[i].setFill(Color.ANTIQUEWHITE);
                } else if (scc_no == 3) {
                    circle[i].setFill(Color.FUCHSIA);
                } else if (scc_no == 4) {
                    circle[i].setFill(Color.GRAY);
                } else if (scc_no == 5) {
                    circle[i].setFill(Color.CHARTREUSE);
                } else if (scc_no == 6) {
                    circle[i].setFill(Color.CORAL);
                } else if (scc_no == 7) {
                    circle[i].setFill(Color.BROWN);
                } else if (scc_no == 8) {
                    circle[i].setFill(Color.DARKORCHID);
                } else if (scc_no == 9) {
                    circle[i].setFill(Color.DARKGOLDENROD);
                } else if (scc_no == 10) {
                    circle[i].setFill(Color.ROSYBROWN);
                } else if (scc_no == 11) {
                    circle[i].setFill(Color.GOLD);
                } else if (scc_no == 12) {
                    circle[i].setFill(Color.GREEN);
                }
            }
        }
    }

    /**
     * save current state
     */
    void saveState() {
        state[DFSSetUp.cntState++] = new SaveGraphState(circle, dfn, StackUpdate, queueVis, DFSSetUp.cnt);
    }
}
