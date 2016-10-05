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
 * add a node to graph, which has a finding/finishing time indicator with it
 *
 * @author Arnab Sen Sharma
 */
public class GetNodeWithTime {

    int cnt;
    double radius = DFSSetUp.CircleRadius;

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
     * @param dfn show finding/finishing time of a node
     */
    GetNodeWithTime(Rectangle rect, Circle[] circle, Group root, double[] co_ordinX, double[] co_ordinY, Text text[], Line[] line, Text[] dfn) {

        if (FXMLDocumentController.DFS || FXMLDocumentController.SCC_tar) {
            this.cnt = DFSSetUp.cnt;
            radius = DFSSetUp.CircleRadius;
        } else if (FXMLDocumentController.TopSort) {
            this.cnt = TopSortSetUp.cnt;
            radius = TopSortSetUp.CircleRadius;
        } else if (FXMLDocumentController.SCC_koja) {
            this.cnt = SCC_KojarasuSetUp.cnt;
            radius = SCC_KojarasuSetUp.CircleRadius;
        } else if (FXMLDocumentController.Dijkstra) {
            this.cnt = DijkstraSetUp.cnt;
            radius = DijkstraSetUp.CircleRadius;
        } else if (FXMLDocumentController.BFS) {
            this.cnt = BFSSetUp.cnt;
            radius = BFSSetUp.CircleRadius;
        }
        rect.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                circle[cnt + 1] = new Circle(t.getX(), t.getY(), radius);
                Circle c = circle[cnt + 1];
                int x = getCnt();
                text[cnt] = new Text(String.format("%d", x));
                //if(!FXMLDocumentController.SCC_koja && !FXMLDocumentController.SCC_tar && !FXMLDocumentController.Dijkstra) text[cnt].setFill(Color.CYAN);
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
                StackPane stk2 = new StackPane();
                stk.setLayoutX(t.getX() - radius);
                stk.setLayoutY(t.getY() - radius);
                stk2.setLayoutX(t.getX() + radius);
                stk2.setLayoutY(t.getY() - radius - 10);
                stk.getChildren().addAll(c, text[cnt]);
                stk2.getChildren().add(dfn[cnt]);
                root.getChildren().addAll(stk, stk2);

                co_ordinX[cnt] = t.getX();
                co_ordinY[cnt] = t.getY();

                /*c.setOnMousePressed(e->{
                 c.setFill(Color.BLUE);
                 });
                 c.setOnMouseReleased(e->{
                 c.setFill(Color.RED);
                 });*/
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
        if (FXMLDocumentController.DFS || FXMLDocumentController.SCC_tar) {
            DFSSetUp.cnt++;
        } else if (FXMLDocumentController.TopSort) {
            TopSortSetUp.cnt++;
        } else if (FXMLDocumentController.SCC_koja) {
            SCC_KojarasuSetUp.cnt++;
        } else if (FXMLDocumentController.Dijkstra) {
            DijkstraSetUp.cnt++;
        } else if (FXMLDocumentController.BFS) {
            BFSSetUp.cnt++;
        }
        return cnt;
    }
}
