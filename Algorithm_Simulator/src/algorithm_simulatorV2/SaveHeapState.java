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
 * save HeapSort state
 *
 * @author Arnab Sen Sharma
 */
public class SaveHeapState {

    Circle[] circle;
    Line[] edge;
    int cnt;
    Text[] text;
    boolean[] lineVisibility;
    Text instruction = new Text();

    /**
     * import all necessary components whose information is to be saved
     *
     * @param passcircle circle array representing the nodes of graph
     * @param passedge edge information of the graph
     * @param passtext showing the value at current index
     * @param lineVis edge visibility of a graph
     * @param ins instruction
     */
    SaveHeapState(Circle[] passcircle, Line[] passedge, Text[] passtext, boolean[] lineVis, Text ins) {

        circle = new Circle[17];
        edge = new Line[17];
        text = new Text[17];
        lineVisibility = new boolean[17];
        instruction.setText(ins.getText());

        for (int i = 1; i <= 15; i++) {
            circle[i] = new Circle();
            circle[i].setRadius(passcircle[i].getRadius());
            circle[i].setFill(passcircle[i].getFill());

            text[i] = new Text();
            text[i].setText(passtext[i].getText());
        }
        for (int i = 1; i <= 14; i++) {
            edge[i] = new Line();
            edge[i].setVisible(lineVisibility[i]);
            edge[i].setStroke(passedge[i].getStroke());
            edge[i].setOpacity(passedge[i].getOpacity());
            edge[i].setStrokeWidth(passedge[i].getStrokeWidth());
            lineVisibility[i] = lineVis[i];
        }
    }
}
