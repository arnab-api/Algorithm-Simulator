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
 * make pair using two double values
 *
 * @author Arnab Sen Sharma
 */
class jora {

    double x, y;

    jora(double a, double b) {
        x = a;
        y = b;
    }
}

/**
 * get directed edge
 *
 * @author Arnab Sen Sharma
 */
public class GetDirectedEdge {

    boolean isselected;
    boolean[][] vec;
    double x1, y1, x2, y2;
    int frst, scnd;
    Circle[] circle;
    double[] co_ordinX;
    double[] co_ordinY;
    Group root;
    double radius;
    int cnt, l_cnt;
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
    GetDirectedEdge(Rectangle rect, Circle[] circle, Group root, double[] co_ordinX, double[] co_ordinY, Text[] text, boolean[][] vec, int[][] nodetoline, Line[] line) {
        if (FXMLDocumentController.BFS) {
            this.cnt = BFSSetUp.cnt;
            l_cnt = BFSSetUp.l_cnt;
            radius = BFSSetUp.CircleRadius;
        } else if (FXMLDocumentController.DFS || FXMLDocumentController.SCC_tar) {
            this.cnt = DFSSetUp.cnt;
            l_cnt = DFSSetUp.l_cnt;
            radius = DFSSetUp.CircleRadius;
        } else if (FXMLDocumentController.TopSort) {
            this.cnt = TopSortSetUp.cnt;
            l_cnt = TopSortSetUp.l_cnt;
            radius = TopSortSetUp.CircleRadius;
        }
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
            scnd = index;
            x2 = co_ordinX[index];
            y2 = co_ordinY[index];

            Line line = new Line(x1, y1, x2, y2);
            line.setStroke(Color.ORANGE);
            line.setOpacity(0.5);
            line.setStrokeWidth(4);
            root.getChildren().add(line);
            line.setVisible(true);
            addArrowHhead();

            nodetoline[frst][scnd] = ++l_cnt;
            //System.out.println("Directed Line Check =====> "+);
            if (FXMLDocumentController.BFS) {
                BFSSetUp.l_cnt = l_cnt;
                radius = BFSSetUp.CircleRadius;
            } else if (FXMLDocumentController.DFS || FXMLDocumentController.SCC_tar) {
                DFSSetUp.l_cnt = l_cnt;
            } else if (FXMLDocumentController.TopSort) {
                TopSortSetUp.l_cnt = l_cnt;
            }
            line_arr[l_cnt] = line;

            circle[frst].setFill(Color.RED);
            circle[scnd].setFill(Color.RED);

            //System.out.print(isselected);
            //System.out.printf("%d ---> %f %f\n",scnd,x2,y2);
            isselected = false;
            vec[frst][scnd] = true;
        }
    }

    /**
     * Given two points,draws an arrow head pointing point two from point one
     *
     * @param a1 x coefficient of line1
     * @param b1 y coefficient of line1
     * @param c1 constant value of line1
     * @param a2 x coefficient of line2
     * @param b2 y coefficient of line2
     * @param c2 constant value of line2
     * @return the intersection point
     */
    jora getIntersection(double a1, double b1, double c1, double a2, double b2, double c2) {
        double x = (b1 * c2 - b2 * c1) / (a1 * b2 - a2 * b1);
        double y = (c1 * a2 - c2 * a1) / (a1 * b2 - a2 * b1);

        //System.out.printf("==============> %f %f\n",x,y);
        jora J = new jora(x, y);
        return J;
    }

    /**
     * draws an arrow head shaped polygon
     */
    void addArrowHhead() {
        double xd, yd, xdd, ydd, dis, m1, m2;
        double X1, X2, Y1, Y2;
        X1 = x1;
        X2 = x2;
        Y1 = y1;
        Y2 = y2;

        double x1 = X2;
        double x2 = X1;
        double y1 = Y2;
        double y2 = Y1;

        double a1, b1, c1, a2, b2, c2;
        double xr, yr, xl, yl;
        dis = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        m1 = radius;
        m2 = dis - radius;
        xd = (m1 * x2 + m2 * x1) / (m1 + m2);
        yd = (m1 * y2 + m2 * y1) / (m1 + m2);

        m1 = radius;
        m2 = m2 - m1;
        xdd = (m1 * x2 + m2 * xd) / (m1 + m2);
        ydd = (m1 * y2 + m2 * yd) / (m1 + m2);

        m1 = 20;
        m2 = dis - 50;
        double xddd = (m1 * x2 + m2 * xd) / (m1 + m2);
        double yddd = (m1 * y2 + m2 * yd) / (m1 + m2);

        double A = y1 - y2;
        double B = -(x1 - x2);
        double C = (x1 - x2) * y1 - (y1 - y2) * x1;

        a1 = A;
        b1 = B;
        c1 = C;

        a2 = B;
        b2 = -A;
        c2 = A * ydd - B * xdd;

        double Kl = c1 - 10 * Math.sqrt(A * A + B * B);
        double Kr = c1 + 10 * Math.sqrt(A * A + B * B);

        //System.out.printf("------>>>>>>>>>> %f %f %f --> %f %f\n",a1,b1,c1,Kl,Kr);
        jora J1 = getIntersection(a1, b1, Kl, a2, b2, c2);
        jora J2 = getIntersection(a1, b1, Kr, a2, b2, c2);

        Polygon gon = new Polygon();
        gon.getPoints().addAll(new Double[]{
            xd, yd,
            J1.x, J1.y,
            xddd, yddd,
            J2.x, J2.y
        //111.11,111.11
        });
        root.getChildren().add(gon);
        //gon.setStroke(Color.WHITE);
        gon.setFill(Color.GOLD);
        //gon.setRotate(90);
        gon.setOpacity(.75);
    }
}
