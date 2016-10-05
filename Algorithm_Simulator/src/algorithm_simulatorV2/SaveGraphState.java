/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 * save state of graph related algorithms
 *
 * @author Arnab Sen Sharma
 */
public class SaveGraphState {

    Circle[] circle = new Circle[1010];
    Text[] dfn = new Text[1010], queue = new Text[1010];
    boolean queueVis[] = new boolean[1010];
    int cnt;

    /**
     * import all necessary components whose information is to be saved
     *
     * @param passcircle circle array representing the nodes of graph
     * @param passdfn node information of the graph
     * @param passqueue queue/stack of the algorithm
     * @param vis boolean array representing weather a node is visited or not
     * @param cnt number of nodes
     */
    public SaveGraphState(Circle[] passcircle, Text[] passdfn, Text[] passqueue, boolean[] vis, int cnt) {
        this.cnt = cnt;
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
    }
    Line[] line;
    int l_cnt;

    /**
     * import all necessary components whose information is to be saved
     *
     * @param passcircle circle array representing the nodes of graph
     * @param passdfn node information of the graph
     * @param passqueue queue/stack of the algorithm
     * @param vis boolean array representing weather a node is visited or not
     * @param cnt number of nodes
     * @param passline node information of the graph
     * @param l_cnt number of edges
     */
    public SaveGraphState(Circle[] passcircle, Text[] passdfn, Text[] passqueue, boolean[] vis, int cnt, Line[] passline, int l_cnt) {
        this(passcircle, passdfn, passqueue, vis, cnt);
        this.l_cnt = l_cnt;
        line = new Line[l_cnt + 5];
        for (int i = 1; i <= l_cnt; i++) {
            line[i] = new Line();
            line[i].setStroke(passline[i].getStroke());
            line[i].setOpacity(passline[i].getOpacity());
            line[i].setStrokeWidth(passline[i].getStrokeWidth());
        }
    }
    Text instruction;

    /**
     * import all necessary components whose information is to be saved
     *
     * @param passcircle circle array representing the nodes of graph
     * @param cnt number of nodes
     * @param passdfn node information of the graph
     * @param passline edge information of the graph
     * @param l_cnt number of edges
     * @param passinstruction instruction
     */
    public SaveGraphState(Circle[] passcircle, int cnt, Text[] passdfn, Line[] passline, int l_cnt, Text passinstruction) {
        this.cnt = cnt;
        for (int i = 1; i <= cnt; i++) {
            circle[i] = new Circle();
            circle[i].setRadius(passcircle[i].getRadius());
            circle[i].setFill(passcircle[i].getFill());

            dfn[i] = new Text();
            dfn[i].setText(passdfn[i].getText());
        }
        this.l_cnt = l_cnt;
        line = new Line[l_cnt + 5];
        for (int i = 1; i <= l_cnt; i++) {
            line[i] = new Line();
            line[i].setStroke(passline[i].getStroke());
            line[i].setOpacity(passline[i].getOpacity());
            line[i].setStrokeWidth(passline[i].getStrokeWidth());
        }
        instruction = new Text();
        instruction.setText(passinstruction.getText());
    }
    Boolean[][] visibility;

    /**
     * import all necessary components whose information is to be saved
     * 
     * @param passcircle circle array representing the nodes of graph
     * @param cnt number of nodes
     * @param passdfn node information of the graph
     * @param passline node information of the graph
     * @param l_cnt number of edges
     * @param passinstruction instruction about the step
     * @param visible visibility of a arrow head
     */
    public SaveGraphState(Circle[] passcircle, int cnt, Text[] passdfn, Line[] passline, int l_cnt, Text passinstruction, boolean[][] visible) {
        this(passcircle, cnt, passdfn, passline, l_cnt, passinstruction);
        visibility = new Boolean[SCC_KojarasuSetUp.a_cnt + 5][2];
        for (int i = 1; i <= SCC_KojarasuSetUp.a_cnt; i++) {
            visibility[i][0] = visible[i][0];
            visibility[i][1] = visible[i][1];
        }
    }
}
