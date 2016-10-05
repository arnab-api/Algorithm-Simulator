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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * simulate BFS algorithm
 * @author Arnab Sen Sharma
 */
public class RunBFS{

    Circle[] circle;
    Queue<Integer> queue = new LinkedList<Integer>();
    int srce = 1,level[];
    boolean[][] vec;
    Text[] QueueUpdate,dfn;
    static Object lock;
    static boolean pauseSimulator;
    SaveGraphState state[];
    boolean queueVis[] = new boolean[1010];
    Line[] line;
    int[][] nodetoline;
       
    /**
     * imports all necessary elements to run the process
     * @param circle array of circles representing nodes
     * @param q queue
     * @param vec boolean 2D array to represent the connectivity of the graph
     * @param text show current state of queue
     * @param dfn to indicate the finding/discovery time of a node
     * @param state save the states of the simulation
     * @param line line array  stored up with edges of the graph
     * @param nodetoline stored up with the info which edge is connecting a pair
     * of nodes 
     */
    RunBFS(Circle[] circle, Queue<Integer> q, boolean[][] vec, Text[] text,Text[] dfn, SaveGraphState state[],Line[] line,int[][] nodetoline) {
        this.circle = circle;
        this.state = state;
        this.line = line;
        this.nodetoline = nodetoline;
        queue = q;
        this.vec = vec;
        this.QueueUpdate = text;
        while (!queue.isEmpty()) {
            queue.remove();
        }

        lock = new Object();
        pauseSimulator = false;
        this.dfn = dfn;
        level =new int[1010];
        for(int i=0;i<1010;i++) queueVis[i] = false;
        run();
    }
    
    /**
     * call BFS and run the simulation
     */
    public void run() {
        saveState();
        Algorithm_Simulator.threadStarted = true;
        BFSSetUp.SimulatorStarted = true;
        srce = BFSSetUp.srce;
        level[srce]=0;
        dfn[srce].setText("0");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        queue.add(srce);
        circle[srce].setFill(Color.BLUEVIOLET);
        threadDelay();
        boolean[] taken = new boolean[1010];
        taken[srce] = true;
        while (!queue.isEmpty()) {
            //System.out.println(queue.toString()+"--->");
            if (pauseSimulator) {
                halt();
            }
            updateScene();
            int u = queue.peek();
            circle[u].setFill(Color.SANDYBROWN);
            circle[u].setRadius(circle[u].getRadius()*1.5);
            threadDelay();
            for (int i = 0; i < 1010; i++) {
                if (pauseSimulator) {
                    halt();
                }
                if (vec[u][i]) {
                    int lnidx = nodetoline[u][i]; 
                    int v = i;
                    if (!taken[v]) {
                        taken[v] = true;
                        queue.add(v);
                        Paint prevColor = line[lnidx].getStroke();
                        Double prevWidth = line[lnidx].getStrokeWidth();
                        Double prevOpacity = line[lnidx].getOpacity();
                        line[lnidx].setStroke(Color.BISQUE);
                        line[lnidx].setStrokeWidth(5);
                        line[lnidx].setOpacity(.7);
                        saveState();
                        updateScene();
                        line[lnidx].setStroke(prevColor);
                        line[lnidx].setStrokeWidth(prevWidth);
                        line[lnidx].setOpacity(prevOpacity); 
                        circle[v].setFill(Color.GREENYELLOW);
                        level[v] = level[u]+1;
                        dfn[v].setText(String.format("%d", level[v]));
                        threadDelay();
                    }
                }
            }
            queue.remove();
            circle[u].setRadius(circle[u].getRadius()/1.5);
            updateScene();
            if (u == srce) {
                circle[srce].setFill(Color.BLUEVIOLET);
            } else {
                circle[u].setFill(Color.GREEN);
            }
            threadDelay();
        }
    }

    /**
     * save the current state and delay the process with Algorithm_Simulator.timeGap
     */
    void threadDelay() {
        saveState();
        try {
            Thread.sleep(Algorithm_Simulator.timeGap);

        } catch (InterruptedException ex) {
            Logger.getLogger(RunBFS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * update the panel representing the queue
     */
    void updateScene() {
        System.out.println(queue.toString());
        Object[] arr = queue.toArray();
        for (int i = 0; i < arr.length; i++) {
            System.out.print(" " + arr[i].toString());
        }
        System.out.println("");

        for (int i = 0; i < 1010; i++) {
            QueueUpdate[i].setVisible(false);
            queueVis[i] = false;
        }
        for (int i = 0; i < arr.length; i++) {
            QueueUpdate[i].setText(arr[i].toString());
            QueueUpdate[i].setVisible(true);
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
     * save current state
     */
    void saveState(){
        state[BFSSetUp.cntState++] = new SaveGraphState(circle, dfn, QueueUpdate, queueVis,BFSSetUp.cnt,line,BFSSetUp.l_cnt);
    }
}
