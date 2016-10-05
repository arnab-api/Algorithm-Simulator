/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.util.Vector;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * get an weighted undirected edge
 *
 * @author Arnab Sen Sharma
 */
public class GetEdgeWeighted {

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
    double weight[][], curr_w;
    //VectorInt[] vec;
    boolean[][] vec;
    Paint col1, col2, colLine;
    int cnt;
    TextField getWeight;
    EdgeInfo ei;
    Text[][] wt_show;
    Edge[] edge;
    Text[] edge_show;
    int[][] line_info;

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
     * @param getWeight get input from user
     * @param weight weight of the edges
     * @param ei stores up the edge information
     * @param wt_show shows the weight of an edge
     * @param edge save information of weighted edges
     * @param edge_show show information of the edges(MST)
     * @param line_info store up the info which edge is connecting a pair of
     * nodes
     */
    GetEdgeWeighted(Rectangle rect, Circle[] circle, Group root, double[] co_ordinX, double[] co_ordinY, Text[] text, Line[] line, Info[] info, boolean[][] vec, TextField getWeight, double weight[][], EdgeInfo ei, Text[][] wt_show, Edge[] edge, Text[] edge_show, int[][] line_info) {
        this.co_ordinX = co_ordinX;
        this.co_ordinY = co_ordinY;
        this.circle = circle;
        this.root = root;
        this.line = line;
        this.info = info;
        this.vec = vec;
        this.getWeight = getWeight;
        this.weight = weight;
        this.ei = ei;
        this.edge = edge;
        this.wt_show = wt_show;
        this.edge_show = edge_show;
        this.line_info = line_info;
        if (FXMLDocumentController.MST_krus) {
            this.cnt = MST_KruskalSetUp.cnt;
            l_cnt = MST_KruskalSetUp.l_cnt;
        } else if (FXMLDocumentController.Dijkstra) {
            this.cnt = DijkstraSetUp.cnt;
            l_cnt = DijkstraSetUp.l_cnt;
        }
        System.out.printf("Constructor called ===> %d %d\n", cnt, l_cnt);
        for (int i = 1; i <= cnt; i++) {
            System.out.printf("%d ---> (%f,%f)\n", i, co_ordinX[i], co_ordinY[i]);
            int a = i;
            Circle c = circle[i];
            c.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    //c.setFill(Color.GREEN);
                    Call(a);
                }
            });
            Text tt = text[i];
            tt.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    //c.setFill(Color.GREEN);
                    Call(a);
                }
            });
        }
        rect.setOnMouseClicked(null);

    }

    void Call(int index) {
        System.out.println("Selected  " + index);
        if (isselected == false) {

            try {
                curr_w = Double.parseDouble(getWeight.getText().trim());
                System.out.printf("-----> %f\n", curr_w);
            } catch (Exception e) {
                System.out.println("Wrong input format!!!");
                return;
            }

            circle[index].setFill(Color.GREEN);
            x1 = co_ordinX[index];
            y1 = co_ordinY[index];
            frst = index;
            //System.out.print(isselected);
            //System.out.printf("%d ---> %f %f\n",frst,x1,y1);
            isselected = true;
        } else {
            scnd = index;

            if (vec[frst][scnd]) {
                circle[frst].setFill(Color.RED);
                circle[scnd].setFill(Color.RED);
                isselected = false;
                if (curr_w >= weight[frst][scnd]) {
                    return;
                } else {
                    weight[frst][scnd] = curr_w;
                    weight[scnd][frst] = curr_w;
                    wt_show[frst][scnd].setText(String.format("%.1f", curr_w));
                    if (FXMLDocumentController.MST_krus) {
                        for (int i = 0; i < MST_KruskalSetUp.e_cnt; i++) {
                            if ((edge[i].u == frst && edge[i].v == scnd) || (edge[i].v == frst && edge[i].u == scnd)) {
                                edge[i].w = curr_w;
                                sortEdge();
                                return;
                            }
                        }
                    }
                    return;
                }
            }

            edge[MST_KruskalSetUp.e_cnt++] = new Edge(frst, scnd, curr_w);
            if (FXMLDocumentController.MST_krus) {
                sortEdge();
            }

            x2 = co_ordinX[index];
            y2 = co_ordinY[index];

            if (FXMLDocumentController.MST_krus) {
                this.l_cnt = ++MST_KruskalSetUp.l_cnt;
            } else if (FXMLDocumentController.Dijkstra) {
                this.l_cnt = ++DijkstraSetUp.l_cnt;
            }
            //System.out.printf(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>%d %d\n",l_cnt,BFSSetUp.l_cnt);
            line[l_cnt] = new Line(x1, y1, x2, y2);
            line[l_cnt].setStroke(Color.CYAN);
            line[l_cnt].setOpacity(0.5);
            line[l_cnt].setStrokeWidth(4);
            root.getChildren().add(line[l_cnt]);
            line[l_cnt].setVisible(true);
            info[l_cnt] = new Info(frst, scnd);
            line_info[frst][scnd] = l_cnt;
            line_info[scnd][frst] = l_cnt;

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

            weight[frst][scnd] = curr_w;
            weight[scnd][frst] = curr_w;
            wt_show[frst][scnd].setText(String.format("%.1f", curr_w));
            wt_show[frst][scnd].setFill(Color.AQUAMARINE);
            wt_show[frst][scnd].setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 10));
            wt_show[frst][scnd].setLayoutX((x1 + x2) / 2);
            wt_show[frst][scnd].setLayoutY((y1 + y2) / 2);
            root.getChildren().add(wt_show[frst][scnd]);
            wt_show[scnd][frst] = wt_show[frst][scnd];

            int i = l_cnt;
            line[i].setOnMouseEntered(e -> {
                colorSave(info[i].a, info[i].b);
                circle[info[i].a].setFill(Color.BLUE);
                circle[info[i].b].setFill(Color.BLUE);
                line[i].setStroke(Color.HOTPINK);

                ei.from.setText(String.format("%d", info[i].a));
                ei.to.setText(String.format("%d", info[i].b));
                ei.type.setText("UnDirected");
                ei.weight.setText(String.format("%.1f", weight[info[i].a][info[i].b]));
            });

            line[i].setOnMouseExited(e -> {
                circle[info[i].a].setFill(col1);
                circle[info[i].b].setFill(col2);
                line[i].setStroke(colLine);

                ei.from.setText("-f");
                ei.to.setText("-t");
                ei.type.setText("-d");
                ei.weight.setText("-w");
            });
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
        int k = line_info[a][b];
        colLine = line[k].getStroke();
    }

    /**
     * sorts the edges according to their weight
     */
    void sortEdge() {
        int len = MST_KruskalSetUp.e_cnt;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (edge[i].w > edge[j].w) {
                    //System.out.printf("Called %d && %d\n",i,j);
                    //System.out.printf("(%d %d %.1f) (%d %d %.1f)\n", edge[i].u,edge[i].v,edge[i].w,edge[j].u,edge[j].v,edge[j].w);
                    swap(i, j);
                    //System.out.printf("(%d %d %.1f) (%d %d %.1f)\n", edge[i].u,edge[i].v,edge[i].w,edge[j].u,edge[j].v,edge[j].w);
                }
            }
        }

        for (int i = 0; i < len; i++) {
            //System.out.println(String.format("%d <--> %d => %.1f",edge[i].u,edge[i].v,edge[i].w));
            edge_show[i].setText(String.format("%d <-> %d =>%.1f", edge[i].u, edge[i].v, edge[i].w));
            edge_show[i].setVisible(true);
        }
    }

    /**
     * swaps two edges
     *
     * @param a edge 1
     * @param b edge 2
     */
    void swap(int a, int b) {
        Edge temp = new Edge(-1, -1, -1);
        temp.u = edge[a].u;
        temp.v = edge[a].v;
        temp.w = edge[a].w;

        edge[a].u = edge[b].u;
        edge[a].v = edge[b].v;
        edge[a].w = edge[b].w;

        edge[b].u = temp.u;
        edge[b].v = temp.v;
        edge[b].w = temp.w;
    }
}
