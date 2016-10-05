/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * add a node to graph
 *
 * @author Arnab Sen Sharma
 */
public class GetNode {

    int cnt;
    double radius;

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
     */
    GetNode(Rectangle rect, Circle[] circle, Group root, double[] co_ordinX, double[] co_ordinY, Text text[], Line[] line) {
        if (FXMLDocumentController.BFS) {
            this.cnt = BFSSetUp.cnt;
            radius = BFSSetUp.CircleRadius;
        } else if (FXMLDocumentController.DFS) {
            this.cnt = DFSSetUp.cnt;
            radius = DFSSetUp.CircleRadius;
        } else if (FXMLDocumentController.TopSort) {
            this.cnt = TopSortSetUp.cnt;
            radius = TopSortSetUp.CircleRadius;
        } else if (FXMLDocumentController.SCC_koja) {
            this.cnt = SCC_KojarasuSetUp.cnt;
            radius = SCC_KojarasuSetUp.CircleRadius;
        } else if (FXMLDocumentController.MST_krus) {
            this.cnt = MST_KruskalSetUp.cnt;
            radius = MST_KruskalSetUp.CircleRadius;
        }
        rect.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                circle[cnt + 1] = new Circle(t.getX(), t.getY(), radius);
                Circle c = circle[cnt + 1];
                int x = getCnt();
                text[cnt] = new Text(String.format("%d", x));
                if (FXMLDocumentController.MST_krus) {
                    text[cnt].setFill(Color.BLACK);
                } else {
                    text[cnt].setFill(Color.CYAN);
                }
                text[cnt].setStrokeWidth(3);
                text[cnt].setId("text");
                /*c.setOnMouseClicked(new EventHandler<MouseEvent>() {

                 @Override
                 public void handle(MouseEvent t) {
                 c.setFill(Color.GREEN);
                 }

                 });*/
                c.setFill(Color.RED);
                StackPane stk = new StackPane();
                stk.setLayoutX(t.getX() - radius);
                stk.setLayoutY(t.getY() - radius);
                stk.getChildren().addAll(c, text[cnt]);
                root.getChildren().addAll(stk);

                co_ordinX[cnt] = t.getX();
                co_ordinY[cnt] = t.getY();
            }
        });
        for (int i = 1; i <= cnt; i++) {
            circle[i].setOnMouseClicked(null);
            text[i].setOnMouseClicked(null);
        }
    }

    /**
     * increment the nodeCount by 1
     *
     * @return incremented nodeCount
     */
    int getCnt() {
        cnt++;
        if (FXMLDocumentController.BFS) {
            BFSSetUp.cnt++;
        } else if (FXMLDocumentController.DFS) {
            DFSSetUp.cnt++;
        } else if (FXMLDocumentController.TopSort) {
            TopSortSetUp.cnt++;
        } else if (FXMLDocumentController.SCC_koja) {
            SCC_KojarasuSetUp.cnt++;
        } else if (FXMLDocumentController.MST_krus) {
            MST_KruskalSetUp.cnt++;
        }
        return cnt;
    }
}
