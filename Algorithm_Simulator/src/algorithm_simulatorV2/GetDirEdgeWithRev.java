/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * get directed edge , the direction of which can be reversed(used in kojarasu
 * method for SCC)
 *
 * @author Arnab Sen Sharma
 */
public class GetDirEdgeWithRev {

    boolean isselected;
    boolean[][] vec;
    double x1, y1, x2, y2;
    int frst, scnd;
    Circle[] circle;
    double[] co_ordinX;
    double[] co_ordinY;
    Group root;
    double radius;
    int cnt, l_cnt, a_cnt = 0;
    Polygon[][] gon_arr;
    int[][] nodetoline;
    Line[] line_arr;

    /**
     * import all necessary objects and build the class
     *
     * @param rect canvas on which the graph is being drawn
     * @param circle array of circles to represent the nodes
     * @param root group of objects used to represent the graph
     * @param co_ordinX x coordinate of the nodes
     * @param co_ordinY y coordinate of the nodes
     * @param text write the identity of the node on the circle
     * @param vec boolean 2D array to represent the connectivity of the graph
     * @param nodetoline store up the info which edge is connecting a pair of
     * nodes
     * @param line line array to store up the edges
     */
    GetDirEdgeWithRev(Rectangle rect, Circle[] circle, Group root, double[] co_ordinX, double[] co_ordinY, Text[] text, boolean[][] vec, int[][] nodetoline, Line[] line) {

        this.cnt = SCC_KojarasuSetUp.cnt;
        l_cnt = SCC_KojarasuSetUp.l_cnt;
        radius = SCC_KojarasuSetUp.CircleRadius;
        gon_arr = SCC_KojarasuSetUp.gon_arr;
        a_cnt = SCC_KojarasuSetUp.a_cnt;

        this.co_ordinX = co_ordinX;
        this.co_ordinY = co_ordinY;
        this.circle = circle;
        this.root = root;
        this.vec = vec;
        this.nodetoline = nodetoline;
        this.line_arr = line;
        for (int i = 1; i <= cnt; i++) {
            int a = i;
            Circle c = circle[i];
            c.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    //c.setFill(Color.GREEN);
                    call(a);
                }
            });
            Text tt = text[i];
            tt.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    //c.setFill(Color.GREEN);
                    call(a);
                }
            });
        }
        rect.setOnMouseClicked(null);
    }

    /**
     * selects two nodes and draws an edge connecting them
     *
     * @param index selected node
     */
    void call(int index) {
        if (isselected == false) {
            circle[index].setFill(Color.GREEN);
            x1 = co_ordinX[index];
            y1 = co_ordinY[index];
            frst = index;
            //System.out.print(isselected);
            //System.out.printf("%d ---> %f %f\n",frst,x1,y1);
            isselected = true;
        } else {
            a_cnt++;
            SCC_KojarasuSetUp.a_cnt++;

            scnd = index;
            x2 = co_ordinX[index];
            y2 = co_ordinY[index];

            Line line = new Line(x1, y1, x2, y2);
            line.setStroke(Color.ORANGE);
            line.setOpacity(0.5);
            line.setStrokeWidth(4);
            root.getChildren().add(line);
            line.setVisible(true);
            AddArrowHead addArrow1 = new AddArrowHead(x1, x2, y1, y2, root, gon_arr, radius, a_cnt, 0);
            AddArrowHead addArrow2 = new AddArrowHead(x2, x1, y2, y1, root, gon_arr, radius, a_cnt, 1);
            nodetoline[frst][scnd] = ++l_cnt;
            SCC_KojarasuSetUp.l_cnt = l_cnt;
            line_arr[l_cnt] = line;

            circle[frst].setFill(Color.RED);
            circle[scnd].setFill(Color.RED);

            //System.out.print(isselected);
            //System.out.printf("%d ---> %f %f\n",scnd,x2,y2);
            isselected = false;
            vec[frst][scnd] = true;
        }
    }
}
