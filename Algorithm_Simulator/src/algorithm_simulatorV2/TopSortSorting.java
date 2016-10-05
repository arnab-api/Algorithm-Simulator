/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * simulate topSort algorithm
 *
 * @author Arnab Sen Sharma
 */
public class TopSortSorting {

    Circle[] circle;
    Text[] dfn;
    int[] finding;
    int[] finishing;
    int srce;
    static boolean pauseSimulator;
    static Object lock = new Object();
    boolean vec[][];
    int cnt;
    int depth = -1;
    Button run_btn;
    boolean[] vis, taken;
    int[] topSort;
    int num_taken = 0;
    Queue QtopSort = new LinkedList();
    boolean flag = false;
    boolean loop[];
    SaveGraphState[] state;
    Line[] line;
    int[][] nodetoline;

    /**
     * imports all the necessary components to run the topSorting process
     *
     * @param circle array of circles representing nodes
     * @param dfn to indicate the finding/discovery time and finishing time of a
     * node
     * @param finding finding/discovery time of a node
     * @param finishing finishing time of a node
     * @param srce source of the graph
     * @param vec boolean 2D array representing the connectivity of nodes in the
     * graph
     * @param line edges of the graph
     * @param state saves the states of the process
     * @param nodetoline stored up with the info which edge is connecting a pair
     * of nodes
     */
    TopSortSorting(Circle[] circle, Text[] dfn, int[] finding, int[] finishing, int srce, boolean[][] vec, Line[] line, SaveGraphState[] state, int[][] nodetoline) {
        this.circle = circle;
        this.dfn = dfn;
        this.finding = finding;
        this.finishing = finishing;
        this.srce = srce;
        this.vec = vec;
        this.line = line;
        this.nodetoline = nodetoline;
        this.state = state;
        if (FXMLDocumentController.TopSort) {
            cnt = TopSortSetUp.cnt;
            //instruction = TopSortSetUp.Instruction;
            run_btn = TopSortSetUp.run;
            vis = new boolean[cnt + 5];
            taken = new boolean[cnt + 5];
            topSort = new int[cnt + 5];
            loop = new boolean[cnt + 5];
        }
        run();
    }

    /**
     * runs the topSort process
     */
    public void run() {
        SaveState();
        System.out.println("Enterred TopSort");
        Algorithm_Simulator.threadStarted = true;
        for (int i = 1; i <= cnt; i++) {
            if (!vis[i]) {
                srce = i;
                circle[srce].setFill(Color.BROWN);
                threadDelay();
                DFS(srce);
            }
        }

        if (flag) {
            TopSortSetUp.Instruction.setText("There exists at least one cyclic relation/dependency\nNo topSort sequence exists");
            SaveState();
            return;
        }

        updateTopSortInstructionPanel();
        SaveState();

        for (int i = 1; i <= cnt; i++) {
            int cur_max = -1;
            int now = -1;
            for (int j = 1; j <= cnt; j++) {
                if (!taken[j] && finishing[j] > cur_max) {
                    cur_max = finishing[j];
                    now = j;
                }
            }
            taken[now] = true;
            QtopSort.add(now);
            circle[now].setFill(Color.GREEN);
            updateResult();
            threadDelay();
        }
    }

    /**
     * runs DFS traversal to the given graph
     *
     * @param u current node
     */
    void DFS(int u) {
        if (loop[u]) {
            System.out.println("----->  " + u);
            flag = true;
            return;
        }
        if (vis[u]) {
            return;
        }
        vis[u] = true;
        loop[u] = true;
        Algorithm_Simulator.threadStarted = true;
        circle[u].setFill(Color.ORANGE);
        finding[u] = ++depth;
        dfn[u].setText(String.format("%d/oo", finding[u]));
        threadDelay();
        for (int i = 1; i <= cnt; i++) {
            if (vec[u][i]) {
                int v = i;
                int lnidx = nodetoline[u][i];
                //if (finding[v] == -1) {
                Paint prevColor = line[lnidx].getStroke();
                Double prevWidth = line[lnidx].getStrokeWidth();
                Double prevOpacity = line[lnidx].getOpacity();
                line[lnidx].setStroke(Color.BISQUE);
                line[lnidx].setStrokeWidth(5);
                line[lnidx].setOpacity(.6);
                circle[u].setFill(Color.YELLOW);
                SaveState();
                line[lnidx].setStroke(prevColor);
                line[lnidx].setStrokeWidth(prevWidth);
                line[lnidx].setOpacity(prevOpacity);
                DFS(v);
                circle[u].setFill(Color.ORANGE);
                threadDelay();
                //}
            }
        }
        finishing[u] = ++depth;
        dfn[u].setText(String.format("%d/%d", finding[u], finishing[u]));
        circle[u].setFill(Color.BLUE);
        threadDelay();
        loop[u] = false;
    }

    /**
     * halts the process
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
     * pauses the process
     */
    static void pause() {
        pauseSimulator = true;
    }

    /**
     * resumes the process
     */
    static void play() {
        pauseSimulator = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    /**
     * saves the state and delays the process
     */
    void threadDelay() {
        SaveState();
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
     * update instruction
     */
    void updateTopSortInstructionPanel() {
        TopSortSetUp.Instruction.setText("Step 2 : Sort the nodes according to the descending order of   \nfinishing time");
    }

    /**
     * update result
     */
    void updateResult() {
        TopSortSetUp.Instruction.setText("Possible TopSort Sequence : " + QtopSort.toString());
    }

    /**
     * save current state
     */
    void SaveState() {
        state[TopSortSetUp.stateCnt++] = new SaveGraphState(circle, TopSortSetUp.cnt, dfn, line, TopSortSetUp.l_cnt, TopSortSetUp.Instruction);
    }
}
