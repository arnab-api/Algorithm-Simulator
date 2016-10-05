/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.util.Vector;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * get an unweighted undirected edge
 *
 * @author Arnab Sen Sharma
 */
public class GetEdge {

    boolean isselected;
    double x1, y1, x2, y2;
    int frst, scnd;
    Circle[] circle;
    double[] co_ordinX;
    double[] co_ordinY;
    Group root;
    Line[] line;
    int l_cnt;
    Info[] info;
    //VectorInt[] vec;
    boolean[][] vec;
    Paint col1, col2;
    int cnt;
    int[][] nodetoline;

    /**
     * import all necessary objects and build the class
     *
     * @param rect canvas on which the graph is being drawn
     * @param circle array of circles to represent the nodes
     * @param root group of objects used to represent the graph
     * @param co_ordinX x coordinate of the nodes
     * @param co_ordinY y coordinate of the nodes
     * @param text write the identity of the node on the circle
     * @param line line array to store up the edges
     * @param info store up the information witch two nodes are being connected
     * with this edge
     * @param vec boolean 2D array to represent the connectivity of the graph
     * @param nodetoline store up the info which edge is connecting a pair of
     * nodes
     */
    GetEdge(Rectangle rect, Circle[] circle, Group root, double[] co_ordinX, double[] co_ordinY, Text[] text, Line[] line, Info[] info, boolean[][] vec, int[][] nodetoline) {
        this.co_ordinX = co_ordinX;
        this.co_ordinY = co_ordinY;
        this.circle = circle;
        this.root = root;
        this.line = line;
        this.info = info;
        this.vec = vec;
        this.nodetoline = nodetoline;
        if (FXMLDocumentController.BFS) {
            this.cnt = BFSSetUp.cnt;
            l_cnt = BFSSetUp.l_cnt;
        } else if (FXMLDocumentController.DFS) {
            this.cnt = DFSSetUp.cnt;
            l_cnt = DFSSetUp.l_cnt;
        } else if (FXMLDocumentController.TopSort) {
            this.cnt = TopSortSetUp.cnt;
            l_cnt = TopSortSetUp.l_cnt;
        }
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
            scnd = index;
            x2 = co_ordinX[index];
            y2 = co_ordinY[index];
            if (FXMLDocumentController.BFS) {
                this.l_cnt = ++BFSSetUp.l_cnt;
            } else if (FXMLDocumentController.DFS) {
                this.l_cnt = ++DFSSetUp.l_cnt;
            } else if (FXMLDocumentController.DFS) {
                this.l_cnt = ++TopSortSetUp.l_cnt;
            }
            nodetoline[frst][scnd] = l_cnt;
            nodetoline[scnd][frst] = l_cnt;

            //System.out.printf(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>%d %d\n",l_cnt,BFSSetUp.l_cnt);
            line[l_cnt] = new Line(x1, y1, x2, y2);
            line[l_cnt].setStroke(Color.CYAN);
            line[l_cnt].setOpacity(0.5);
            line[l_cnt].setStrokeWidth(4);
            root.getChildren().add(line[l_cnt]);
            line[l_cnt].setVisible(true);
            info[l_cnt] = new Info(frst, scnd);

            int i = l_cnt;

            line[i].setOnMouseEntered(e -> {
                colorSave(info[i].a, info[i].b);
                circle[info[i].a].setFill(Color.BLUE);
                circle[info[i].b].setFill(Color.BLUE);
                line[i].setStroke(Color.HOTPINK);
            });

            line[i].setOnMouseExited(e -> {
                //circle[info[i].a].setFill(Color.RED);
                //circle[info[i].b].setFill(Color.RED);
                circle[info[i].a].setFill(col1);
                circle[info[i].b].setFill(col2);
                line[i].setStroke(Color.CYAN);
            });

            /*Path path1 = new Path();
             Path path2 = new Path();
             path1.getElements().add(new LineTo(x1,y1));
             path2.getElements().add(new LineTo(x2,y2));
             root.getChildren().add(path1);
             root.getChildren().add(path1);*/
            circle[frst].setFill(Color.RED);
            circle[scnd].setFill(Color.RED);

            //System.out.print(isselected);
            //System.out.printf("%d ---> %f %f\n",scnd,x2,y2);
            isselected = false;

            //System.out.printf("%d <-----------------> %d\n",frst,scnd);
            vec[frst][scnd] = true;
            vec[scnd][frst] = true;
        }
    }

    /**
     * saves the color of two connected nodes
     *
     * @param a node 1
     * @param b node 2
     */
    void colorSave(int a, int b) {
        col1 = circle[a].getFill();
        col2 = circle[b].getFill();
    }
}
