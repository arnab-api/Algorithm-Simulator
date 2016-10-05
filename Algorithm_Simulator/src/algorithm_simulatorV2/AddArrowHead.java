/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Purpose : Given two points,draws an arrow head pointing point two from point
 * one
 *
 * @author Arnab Sen Sharma
 * @version 1.0 8/25/2016
 */
public class AddArrowHead {

    /**
     * Draws the arrow head and assigns it to gon_arr
     *
     * @param x1 x coordinate of point 1
     * @param x2 x coordinate of point 2
     * @param y1 y coordinate of point 1
     * @param y2 y coordinate of point 2
     * @param root group on which the graph is being drawn
     * @param gon_arr 2D array of arrow_head(s)
     * @param radius standard radius of the circle.which represents a
     * node/vertex
     * @param index 1D index of gon_arr
     * @param yo 2D index of gon_arr
     */
    AddArrowHead(double x1, double x2, double y1, double y2, Group root, Polygon[][] gon_arr, double radius, int index, int yo) {
        double xd, yd, xdd, ydd, dis, m1, m2;
        double X1, X2, Y1, Y2;
        X1 = x1;
        X2 = x2;
        Y1 = y1;
        Y2 = y2;

        x1 = X2;
        x2 = X1;
        y1 = Y2;
        y2 = Y1;

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

        gon_arr[index][yo] = new Polygon();
        Polygon gon = gon_arr[index][yo];
        gon.getPoints().addAll(new Double[]{
            xd, yd,
            J1.x, J1.y,
            xddd, yddd,
            J2.x, J2.y
        });
        root.getChildren().add(gon);
        if (yo == 0) {
            gon.setFill(Color.GREENYELLOW);
        } else {
            gon.setFill(Color.RED);
            gon.setVisible(false);
        }
        gon.setOpacity(.75);
    }

    /**
     * Given two points,draws an arrow head pointing point two from point one
     *
     * @param a1 x co-efficient of line1
     * @param b1 y co-efficient of line1
     * @param c1 constant value of line1
     * @param a2 x co-efficient of line2
     * @param b2 y co-efficient of line2
     * @param c2 constant value of line2
     * @return the intersection point
     */
    public jora getIntersection(double a1, double b1, double c1, double a2, double b2, double c2) {
        double x = (b1 * c2 - b2 * c1) / (a1 * b2 - a2 * b1);
        double y = (c1 * a2 - c2 * a1) / (a1 * b2 - a2 * b1);

        //System.out.printf("==============> %f %f\n",x,y);
        jora J = new jora(x, y);
        return J;
    }
}
