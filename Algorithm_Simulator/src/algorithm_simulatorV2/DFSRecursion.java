/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * given a graph runs recursive dfs process
 * @author Arnab Sen Sharma
 */
public class DFSRecursion implements Runnable{
    
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
    Text instruction;
    Button run;
    
    /**
     * given a graph runs recursive dfs process
     *
     * @param circle array of circles representing nodes
     * @param dfn to indicate the finding/discovery time and finishing time of a
     * node
     * @param finding finding/discovery time of a node
     * @param finishing finishing time of a node
     * @param srce source of the graph
     * @param vec boolean 2D array representing the connectivity of nodes in the
     * graph
     */
    DFSRecursion(Circle[] circle,Text[] dfn,int[] finding,int[] finishing,int srce,boolean[][] vec){
        this.circle = circle;
        this.dfn = dfn;
        this.finding = finding;
        this.finishing = finishing;
        this.srce = srce;
        this.vec = vec;
        if(FXMLDocumentController.TopSort){
            cnt = TopSortSetUp.cnt;
            instruction = TopSortSetUp.Instruction;
            run = TopSortSetUp.run;
        }
    }
    /**
     * calls the DFS process
     */
    @Override
    public void run() {
        circle[srce].setFill(Color.BLUEVIOLET);
        threadDelay();
        DFS(srce);
    }
    /**
     * runs DFS process
     * @param u current source of the DFS 
     */
    void DFS(int u){
        Algorithm_Simulator.threadStarted = true;
        circle[u].setFill(Color.ORANGE);
        finding[u]=++depth;
        dfn[u].setText(String.format("%d/oo", finding[u]));
        threadDelay();
        for(int i=1;i<=cnt;i++){
            if(vec[u][i]){
                int v = i;
                if(finding[v]==-1){
                    circle[u].setFill(Color.YELLOW);
                    DFS(v);
                    circle[u].setFill(Color.ORANGE);
                    threadDelay();
                }
            }
        }
        finishing[u] = ++depth;
        dfn[u].setText(String.format("%d/%d", finding[u],finishing[u]));
        circle[u].setFill(Color.BLUE);
        threadDelay();
    }
    /**
     * used to temporarily halt the DFS process
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
     * delay the  running process by Algorithm_Simulator.timeGap
     */
    void threadDelay() {
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
}
