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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * simulate DFS algorithm
 *
 * @author Arnab Sen Sharma
 */
public class RunDFS {

    Circle[] circle;
    Stack stack = new Stack();
    int srce = 1;
    boolean[][] vec;
    Text[] StackUpdate, dfn;
    static Object lock;
    static boolean pauseSimulator;
    int[] finding, exiting;
    int cnt;
    SaveGraphState state[];
    boolean queueVis[] = new boolean[1010];
    Line[] line;
    int[][] nodetoline;

    /**
     * imports all necessary elements to run the process
     *
     * @param circle array of circles representing nodes
     * @param q stack
     * @param vec boolean 2D array to represent the connectivity of the graph
     * @param text show current state of queue
     * @param dfn to indicate the finding/discovery time of a node
     * @param state save the states of the simulation
     * @param line line array stored up with edges of the graph
     * @param nodetoline stored up with the info which edge is connecting a pair
     * of nodes
     */
    RunDFS(Circle[] circle, Stack q, boolean[][] vec, Text[] text, int[] finding, int[] exiting, Text[] dfn, SaveGraphState state[], Line[] line, int[][] nodetoline) {
        this.circle = circle;
        this.state = state;
        stack = q;
        this.line = line;
        this.nodetoline = nodetoline;
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
        for (int i = 0; i < 1010; i++) {
            queueVis[i] = false;
        }
        run();
    }

    /**
     * calls the DFS process
     */
    public void run() {
        saveState();
        Algorithm_Simulator.threadStarted = true;
        srce = DFSSetUp.srce;
        stack.push(srce);
        updateScene();
        finding[srce] = 0;
        dfn[srce].setText("0/oo");
        int depth = 0;
        circle[srce].setFill(Color.BLUEVIOLET);

        for (int i = 1; i <= cnt; i++) {
            System.out.printf("%d(%d,%d) ---> ", i, finding[i], exiting[i]);
            for (int j = 1; j < 1010; j++) {
                if (vec[i][j]) {
                    System.out.printf(" %d", j);
                }
            }
            System.out.println("");
        }

        while (!stack.empty()) {
            int u = (int) stack.peek();
            System.out.printf("SOurce %d\n", u);
            circle[u].setFill(Color.ORANGE);
            circle[u].setRadius(circle[u].getRadius() * 1.5);
            updateScene();
            threadDelay();
            boolean ended = true;

            for (int i = 1; i <= cnt; i++) {
                System.out.printf("%d(%d %d) ===>", cnt, u, i);
                System.out.println(vec[u][i]);
                if (vec[u][i]) {
                    int v = i;
                    if (finding[v] == -1) {
                        int lnidx = nodetoline[u][i];
                        System.out.printf("Enterred %d\n", v);
                        Paint prevColor = line[lnidx].getStroke();
                        Double prevWidth = line[lnidx].getStrokeWidth();
                        Double prevOpacity = line[lnidx].getOpacity();
                        line[lnidx].setStroke(Color.BISQUE);
                        line[lnidx].setStrokeWidth(5);
                        line[lnidx].setOpacity(.7);
                        ended = false;
                        finding[v] = ++depth;
                        dfn[v].setText(String.format("%d/oo", finding[v]));
                        circle[v].setFill(Color.YELLOW);
                        stack.push(v);
                        updateScene();
                        saveState();
                        line[lnidx].setStroke(prevColor);
                        line[lnidx].setStrokeWidth(prevWidth);
                        line[lnidx].setOpacity(prevOpacity);
                        threadDelay();
                        break;
                    }
                }
            }
            if (ended) {
                circle[u].setFill(Color.GREEN);
                if (u == srce) {
                    circle[u].setFill(Color.BLUEVIOLET);
                }
                exiting[u] = ++depth;
                dfn[u].setText(String.format("%d/%d", finding[u], exiting[u]));
                stack.pop();
                updateScene();
            } else {
                circle[u].setFill(Color.YELLOW);
                if (u == srce) {
                    circle[u].setFill(Color.BLUEVIOLET);
                }
            }
            circle[u].setRadius(circle[u].getRadius() / 1.5);
            updateScene();
            threadDelay();
        }
    }

    /**
     * update the panel representing the stack
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

    /**
     * save current state
     */
    void saveState() {
        state[DFSSetUp.cntState++] = new SaveGraphState(circle, dfn, StackUpdate, queueVis, DFSSetUp.cnt, line, DFSSetUp.l_cnt);
    }
}
