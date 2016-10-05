/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * save state of graph related algorithms
 *
 * @author Arnab Sen Sharma
 */
public class SaveGraph2State {

    Circle[] circle = new Circle[1010];
    Text[] dfn = new Text[1010], queue = new Text[1010];
    Line edge[];
    boolean queueVis[] = new boolean[1010];
    int cnt, cntEdge;

    /**
     * import all necessary components whose information is to be saved
     *
     * @param passcircle circle array representing the nodes of graph
     * @param passdfn node information of the graph
     * @param passqueue queue/stack of the algorithm
     * @param vis boolean array representing weather a node is visited or not
     * @param cnt number of nodes
     * @param passedge line array representing the edges of graph
     * @param cntEdge number of edges of the graph
     */
    public SaveGraph2State(Circle[] passcircle, Text[] passdfn, Text[] passqueue, boolean[] vis, int cnt, Line[] passedge, int cntEdge) {
        this.cnt = cnt;
        this.cntEdge = cntEdge;
        for (int i = 1; i <= cnt; i++) {
            circle[i] = new Circle();
            circle[i].setRadius(passcircle[i].getRadius());
            circle[i].setFill(passcircle[i].getFill());

            dfn[i] = new Text();
            dfn[i].setText(passdfn[i].getText());
        }
        for (int i = 0; i < 1010; i++) {
            queue[i] = new Text();
            queue[i].setText(passqueue[i].getText());
            queueVis[i] = vis[i];
        }
        edge = new Line[cntEdge + 5];
        System.out.println("-----------------------> " + cntEdge);
        for (int i = 1; i <= cntEdge; i++) {
            edge[i] = new Line();
            edge[i].setStroke(passedge[i].getStroke());
            edge[i].setOpacity(passedge[i].getOpacity());
            edge[i].setStrokeWidth(passedge[i].getStrokeWidth());
        }
    }

    /**
     * import all necessary components whose information is to be saved
     *
     * @param passcircle circle array representing the nodes of graph
     * @param passqueue queue/stack of the algorithm
     * @param vis boolean array representing weather a node is visited or not
     * @param cnt number of nodes
     * @param passedge line array representing the edges of graph
     * @param cntEdge number of edges of the graph
     */
    public SaveGraph2State(Circle[] passcircle, Text[] passqueue, boolean[] vis, int cnt, Line[] passedge, int cntEdge) {
        this.cnt = cnt;
        this.cntEdge = cntEdge;
        for (int i = 1; i <= cnt; i++) {
            circle[i] = new Circle();
            circle[i].setRadius(passcircle[i].getRadius());
            circle[i].setFill(passcircle[i].getFill());
        }
        for (int i = 0; i < 1010; i++) {
            queue[i] = new Text();
            queue[i].setText(passqueue[i].getText());
            queue[i].setFill(passqueue[i].getFill());
            queueVis[i] = vis[i];
        }
        edge = new Line[cntEdge + 5];
        System.out.println("-----------------------> " + cntEdge);
        for (int i = 1; i <= cntEdge; i++) {
            edge[i] = new Line();
            edge[i].setStroke(passedge[i].getStroke());
            edge[i].setOpacity(passedge[i].getOpacity());
            edge[i].setStrokeWidth(passedge[i].getStrokeWidth());
        }
    }
}
