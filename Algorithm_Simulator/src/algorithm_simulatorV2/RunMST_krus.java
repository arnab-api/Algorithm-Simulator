/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * runs MST simulation
 *
 * @author Arnab Sen Sharma
 */
public class RunMST_krus {

    Circle[] circle;
    Line[] line;
    Text edge_show[];
    int e_cnt, parent[], cur_len;
    Edge[] edge;
    int cnt;
    int[][] line_info;
    static boolean pauseSimulator = false;
    static Object lock = new Object();
    SaveGraph2State[] state;
    boolean queueVis[] = new boolean[1010];

    /**
     *
     * @param circle array of circles representing nodes
     * @param line weighted edges of the graph
     * @param line_info stored up with the info which edge is connecting a pair
     * of nodes
     * @param edge information of the edges
     * @param edge_show show information of the edges
     * @param state save the states of the simulation
     */
    RunMST_krus(Circle[] circle, Line[] line, int[][] line_info, Edge[] edge, Text[] edge_show, SaveGraph2State[] state) {
        this.circle = circle;
        this.line = line;
        this.edge_show = edge_show;
        this.edge = edge;
        this.line_info = line_info;
        this.state = state;
        pauseSimulator = false;
        parent = new int[1010];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < 1010; i++) {
            queueVis[i] = false;
        }
        run();
    }

    /**
     * calls the MST process
     */
    public void run() {
        Algorithm_Simulator.threadStarted = true;
        MST();
    }

    /**
     * finds the set of current node
     *
     * @param u node whose set is to be found
     * @return set of the node
     */
    int find_set(int u) {
        if (u == parent[u]) {
            return u;
        }
        return parent[u] = find_set(parent[u]);
    }

    /**
     * runs MST to the given graph
     *
     * @return
     */
    double MST() {
        double sum = 0.0;
        e_cnt = MST_KruskalSetUp.e_cnt;
        cnt = MST_KruskalSetUp.cnt;
        updateEdgeShow(0);
        saveState();
        for (int i = 0; i < e_cnt; i++) {
            if (pauseSimulator) {
                halt();
            }
            int u = edge[i].u;
            int v = edge[i].v;
            int k = line_info[u][v];
            line[k].setStroke(Color.WHITE);
            line[k].setStrokeWidth(7);
            double w = edge[i].w;
            int set_u = find_set(u);
            int set_v = find_set(v);
            if (set_u != set_v) {
                edge_show[0].setFill(Color.GREEN);
                threadDelay();
                parent[set_u] = set_v;
                sum += w;

                line[k].setStroke(Color.CHARTREUSE);
                line[k].setStrokeWidth(7);
                line[k].setOpacity(.7);
                circle[u].setFill(Color.GREENYELLOW);
                circle[v].setFill(Color.GREENYELLOW);
                threadDelay();
            } else {
                edge_show[0].setFill(Color.RED);
                threadDelay();
                line[k].setStroke(Color.CYAN);
                line[k].setStrokeWidth(4);
                //threadDelay();
            }
            updateEdgeShow(i + 1);
            edge_show[0].setFill(Color.BLACK);
            threadDelay();
        }
        int check = 0;
        for (int i = 1; i <= cnt; i++) {
            if (i == parent[i]) {
                check++;
            }
        }
        if (check > 1) {
            return -1;
        }
        return sum;
    }

    /**
     * update edge panel
     *
     * @param st start index , from where the panel is to be updated
     */
    void updateEdgeShow(int st) {
        int j = 0;
        for (int i = 0; i < e_cnt; i++) {
            edge_show[i].setVisible(false);
            queueVis[i] = false;
        }
        for (int i = st; i < e_cnt; i++) {
            edge_show[j].setText(String.format("%d <-> %d =>%.1f", edge[i].u, edge[i].v, edge[i].w));
            edge_show[j].setVisible(true);
            queueVis[j] = true;
            j++;
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
        state[MST_KruskalSetUp.cntState++] = new SaveGraph2State(circle, edge_show, queueVis, MST_KruskalSetUp.cnt, line, MST_KruskalSetUp.l_cnt);
    }
}
