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
 * simulate Dijkstra algorithm
 *
 * @author Arnab Sen Sharma
 */
public class RunDijkstra {

    Text PQ_show[], dfn[];
    Circle circle[];
    boolean vec[][];
    double weight[][], curr_min[];
    static boolean pauseSimulator;
    static Object lock = new Object();
    int srce, node_cnt, parent[];
    Line[] edges;
    int[][] line_info;
    My_PQ pq;
    SaveGraph2State[] state;
    boolean queueVis[] = new boolean[1010];

    /**
     *
     * @param circle array of circles representing nodes
     * @param vec boolean 2D array to represent the connectivity of the graph
     * @param weight weight of the edges
     * @param dfn show current minimum distance of the nodes from source
     * @param PQ_show show current state of the priority queue
     * @param parent immediate previous node of a node(parent[source] = -1)
     * @param curr_min current minimum distance of the nodes from source
     * @param edges weighted edges of the graph
     * @param line_info stored up with the info which edge is connecting a pair
     * of nodes
     * @param state save the states of the simulation
     */
    RunDijkstra(Circle[] circle, boolean[][] vec, double[][] weight, Text[] dfn, Text[] PQ_show, int[] parent, double[] curr_min, Line[] edges, int[][] line_info, SaveGraph2State[] state) {
        pauseSimulator = false;
        srce = DijkstraSetUp.source;
        node_cnt = DijkstraSetUp.cnt;
        pq = new My_PQ();

        this.state = state;
        this.circle = circle;
        this.vec = vec;
        this.weight = weight;
        this.dfn = dfn;
        this.PQ_show = PQ_show;
        this.edges = edges;
        this.line_info = line_info;
        this.curr_min = curr_min;
        this.parent = parent;
        for (int i = 0; i < 1010; i++) {
            queueVis[i] = false;
        }
        run();
    }

    /**
     * call Dijkstra algorithm
     */
    public void run() {
        saveState();
        Algorithm_Simulator.threadStarted = true;
        for (int i = 1; i <= node_cnt; i++) {
            System.out.printf("%d(%.1f) ", i, curr_min[i]);
        }
        System.out.printf("\n");
        Dijkstra();
        for (int i = 1; i <= node_cnt; i++) {
            System.out.printf("%d(%.1f) ", i, curr_min[i]);
        }
        System.out.printf("\n");
    }

    void Dijkstra() {
        DijkstraNode dn = new DijkstraNode(srce, 0);
        circle[srce].setFill(Color.BLUEVIOLET);
        curr_min[srce] = 0;
        dfn[srce].setText("0");
        threadDelay();
        pq.push(dn);
        updatePQ();
        saveState();
        while (!pq.isEmpty()) {
            DijkstraNode udn = pq.top();
            int u = udn.node;
            circle[u].setFill(Color.PINK);
            circle[u].setRadius(circle[u].getRadius() * 1.5);
            threadDelay();
            double ucost = udn.dist;
            for (int i = 1; i <= node_cnt; i++) {
                if (vec[u][i]) {
                    int v = i;
                    /*int k = line_info[u][v];
                     edges[k].setFill(Color.BISQUE);*/

                    int k = line_info[u][v];
                    //System.out.printf("---->>>> %d %d ==> %d\n",u,v,k);
                    edges[k].setStroke(Color.BISQUE);
                    edges[k].setStrokeWidth(6);

                    threadDelay();
                    double vcost = ucost + weight[u][v];
                    if (vcost < curr_min[v]) {
                        curr_min[v] = vcost;
                        circle[v].setFill(Color.ORANGE);
                        circle[v].setRadius(circle[v].getRadius() * 1.2);
                        dfn[v].setText(String.format("%.1f", curr_min[v]));
                        DijkstraNode vdn = new DijkstraNode(v, vcost);
                        parent[v] = u;
                        pq.push(vdn);
                        updatePQ();
                        threadDelay();
                        circle[v].setRadius(circle[v].getRadius() / 1.2);
                        circle[v].setFill(Color.GREENYELLOW);
                    }

                    edges[k].setStroke(Color.CYAN);
                    edges[k].setStrokeWidth(4);
                }
            }
            pq.pop();
            updatePQ();
            circle[u].setFill(Color.GREENYELLOW);
            circle[u].setRadius(circle[u].getRadius() / 1.5);
            if (u == srce) {
                circle[u].setFill(Color.BLUEVIOLET);
            }
            threadDelay();
        }
    }

    /**
     * update the panel representing the priority queue
     */
    void updatePQ() {
        for (int i = 0; i < 1010; i++) {
            PQ_show[i].setVisible(false);
            queueVis[i] = false;
        }
        for (int i = 0; i < pq.size(); i++) {
            PQ_show[i].setText(String.format("%d --> %.1f", pq.arr[i].node, pq.arr[i].dist));
            PQ_show[i].setVisible(true);
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
        System.out.println("Dijkstra pause called");
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
        state[DijkstraSetUp.cntState++] = new SaveGraph2State(circle, dfn, PQ_show, queueVis, DijkstraSetUp.cnt, edges, DijkstraSetUp.l_cnt);
    }
}
