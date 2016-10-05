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
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 * simulates SCC_kojarasu algorithm
 *
 * @author Arnab Sen Sharma
 */
public class RunSCC_kojarasu {

    Circle[] circle;
    Text[] dfn;
    int[] finding;
    int[] finishing;
    int srce;
    static boolean pauseSimulator;
    static Object lock = new Object();
    boolean vec[][], rev_vec[][] = new boolean[1010][1010];
    int cnt;
    int depth = -1;
    Button run_btn;
    boolean[] vis, taken;
    int[] topSort;
    int num_taken = 0;
    Queue QtopSort = new LinkedList();
    int[] scc_id;
    int scc = 0;
    SaveGraphState[] state;
    Line[] line;
    int[][] nodetoline;
    boolean[][] visible;

    /**
     *
     * @param circle array of circles representing nodes
     * @param dfn to indicate the finding/discovery time of a node
     * @param finding finding/discovery time of a node
     * @param finishing finishing time of a node
     * @param srce source of process
     * @param vec 2D boolean array to represent the connectivity of the graph
     * @param line line array stored up with edges of the graph
     * @param state save the states of the simulation
     * @param nodetoline stored up with the info which edge is connecting a pair
     * of nodes
     * @param visible visibility of the arrow head polygons
     */
    RunSCC_kojarasu(Circle[] circle, Text[] dfn, int[] finding, int[] finishing, int srce, boolean[][] vec, Line[] line, SaveGraphState[] state, int[][] nodetoline, boolean[][] visible) {
        this.circle = circle;
        this.dfn = dfn;
        this.finding = finding;
        this.finishing = finishing;
        this.srce = srce;
        this.vec = vec;
        this.line = line;
        this.nodetoline = nodetoline;
        this.state = state;
        this.visible = visible;
        if (FXMLDocumentController.SCC_koja) {
            cnt = SCC_KojarasuSetUp.cnt;
            run_btn = SCC_KojarasuSetUp.run;
            vis = new boolean[cnt + 5];
            taken = new boolean[cnt + 5];
            topSort = new int[cnt + 5];
            scc_id = new int[cnt + 5];
        }
        run();
    }

    /**
     * runs the SCC-kojarasu process
     */
    public void run() {
        saveState();
        Algorithm_Simulator.threadStarted = true;
        for (int i = 1; i <= cnt; i++) {
            if (!vis[i]) {
                srce = i;
                circle[srce].setFill(Color.BROWN);
                threadDelay();
                DFS(srce);
            }
        }

        updateInstructionPanel();
        saveState();
        //halt();

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

        //threadDelay();
        //threadDelay();
        Object[] sequence;
        sequence = QtopSort.toArray();
        for (int i = 0; i < sequence.length; i++) {
            int seq = (int) sequence[i];
            dfn[seq].setText(String.format("%d", i + 1));
        }

        updateInstructionPanel1();
        saveState();
        //halt();

        reverse_Graph();
        saveState();

        updateInstructionPanel2();
        //halt();
        saveState();

        for (int i = 0; i < vis.length; i++) {
            vis[i] = false;
        }
        for (int i = 0; i < cnt; i++) {
            int seq = (int) sequence[i];
            if (!vis[seq]) {
                scc++;
                Rev_DFS(seq);
                //threadDelay();
                updateSccGroup(scc);
                threadDelay();
            }
        }
    }

    /**
     * runs DFS process
     *
     * @param u current node of the DFS process
     */
    void DFS(int u) {
        if (vis[u]) {
            return;
        }
        vis[u] = true;
        circle[u].setFill(Color.ORANGE);
        finding[u] = ++depth;
        dfn[u].setText(String.format("%d/oo", finding[u]));
        threadDelay();
        for (int i = 1; i <= cnt; i++) {
            if (vec[u][i]) {
                int v = i;
                Paint prevColor = line[nodetoline[u][v]].getStroke();
                double prevWidth = line[nodetoline[u][v]].getStrokeWidth();
                line[nodetoline[u][v]].setStroke(Color.BEIGE);
                line[nodetoline[u][v]].setStrokeWidth(6);
                saveState();
                line[nodetoline[u][v]].setStroke(prevColor);
                line[nodetoline[u][v]].setStrokeWidth(prevWidth);
                //if (finding[v] == -1) {
                circle[u].setFill(Color.PINK);
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
    }

    /**
     * runs DFS process in the reversed graph
     *
     * @param u current node of the DFS process
     */
    void Rev_DFS(int u) {
        if (vis[u]) {
            return;
        }
        vis[u] = true;
        scc_id[u] = scc;
        circle[u].setFill(Color.ORANGE);
        threadDelay();
        for (int i = 1; i <= cnt; i++) {
            if (rev_vec[u][i]) {
                int v = i;
                circle[u].setFill(Color.BLUE);
                Rev_DFS(v);
                //circle[u].setFill(Color.ORANGE);
                //threadDelay();
            }
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
     * update Instruction
     */
    void updateInstructionPanel() {
        run_btn.setVisible(true);
        SCC_KojarasuSetUp.Instruction.setText("Step 2 : Sort the nodes according to the descending order of   \nfinishing time");
    }

    /**
     * update Instruction
     */
    void updateInstructionPanel1() {
        run_btn.setVisible(true);
        SCC_KojarasuSetUp.Instruction.setText("Step 3 : Reverse the graph             ");
    }

    /**
     * update Instruction
     */
    void updateInstructionPanel2() {
        run_btn.setVisible(true);
        SCC_KojarasuSetUp.Instruction.setText("Step 4 : Traverse thea reversed graph according to topSort sequence");
    }

    /**
     * update result
     */
    void updateResult() {
        SCC_KojarasuSetUp.Instruction.setText("Possible TopSort Sequence : " + QtopSort.toString());
    }

    /**
     * reverse the graph
     */
    void reverse_Graph() {
        for (int i = 1; i <= cnt; i++) {
            for (int j = 1; j <= cnt; j++) {
                if (vec[i][j]) {
                    rev_vec[j][i] = true;
                }
            }
        }
        for (int i = 1; i <= SCC_KojarasuSetUp.a_cnt; i++) {
            SCC_KojarasuSetUp.gon_arr[i][0].setVisible(false);
            SCC_KojarasuSetUp.gon_arr[i][1].setVisible(true);
            visible[i][0] = false;
            visible[i][1] = true;
        }
    }

    /**
     * assign different colors to different scc groups
     *
     * @param scc_no component number of SCC component
     */
    void updateSccGroup(int scc_no) {
        for (int i = 1; i <= cnt; i++) {
            if (scc_id[i] == scc_no) {
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
        state[SCC_KojarasuSetUp.stateCnt++] = new SaveGraphState(circle, SCC_KojarasuSetUp.cnt, dfn, line, SCC_KojarasuSetUp.l_cnt, SCC_KojarasuSetUp.Instruction, visible);
    }
}
