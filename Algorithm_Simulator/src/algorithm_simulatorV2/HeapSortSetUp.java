/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * set up the window for heap sort simulation
 *
 * @author Arnab Sen Sharma
 */
public class HeapSortSetUp {

    double arr[], origin[];
    static Thread ob1;
    int len, i;
    StackPane stk[] = new StackPane[20];
    Text text[] = new Text[20];
    Stage window;
    Circle[] circle = new Circle[20];

    Line[] edge = new Line[20];
    double cordinateX[] = new double[20];
    double cordinateY[] = new double[20];

    HBox hb = new HBox();
    VBox vb = new VBox();
    Group root = new Group();
    SaveHeapState[] state = new SaveHeapState[101010];
    static int cntState;
    int currState = 0;
    Text instruction = new Text("Instruction");

    Button prev = new Button("<<Prev");
    Button next = new Button("Next>>");

    /**
     * import array of numbers to be sorted and get the window / build the class
     *
     * @param arr array of numbers to be sorted
     */
    HeapSortSetUp(double arr[]) {
        cntState = 0;
        currState = 0;
        this.arr = arr;
        len = arr.length - 1;
        origin = new double[len + 1];
        for (i = 0; i < 20; i++) {
            text[i] = new Text(String.format("%d", i));
        }
        for (i = 1; i <= len; i++) {
            origin[i] = arr[i];
            text[i].setText(String.format("%.1f", arr[i]));
            text[i].setId("text");
        }
        window = Algorithm_Simulator.window;
    }

    /**
     * set up the window for heap sort simulation
     */
    void displaySetUp() {
        Button back = new Button("Back");
        back.setPrefSize(130, 50);
        next.setPrefSize(130, 50);
        prev.setPrefSize(130, 50);
        next.setOnAction(e -> {
            if (currState < cntState - 1) {
                currState++;
                showState(currState);
            }
        });
        prev.setOnAction(e -> {
            if (currState > 0) {
                currState--;
                showState(currState);
            }
        });
        HBox insPanel = new HBox();
        insPanel.getChildren().add(instruction);
        insPanel.setPrefWidth(440);
        instruction.setFont(Font.font("Arial Rounded MT Bold", 15));
        hb.getChildren().addAll(insPanel, prev, next, back);
        hb.setPadding(new Insets(10, 10, 10, 10));
        hb.setSpacing(10);
        addAllCirclesAndEdges();
        vb.getChildren().addAll(root, hb);

        back.setOnAction(e -> {
            window.setScene(SortingInput.scn);
        });

        RunSimulatorHeap ob1 = new RunSimulatorHeap(arr, circle, edge, text, state);

        Scene scn = new Scene(vb, 900, 600);
        scn.getStylesheets().add("Style1.css");
        window.setScene(scn);
        showState(0);
    }

    /**
     * build the tree representing the array
     */
    void addAllCirclesAndEdges() {
        Rectangle rect = new Rectangle(900, 500);
        rect.setFill(Color.DARKGREY);
        root.getChildren().add(rect);
        addCircle(1, 400, 20);
        addCircle(2, 260, 100);
        addCircle(4, 190, 200);
        addCircle(8, 155, 300);
        addCircle(9, 225, 300);
        addCircle(5, 330, 200);
        addCircle(11, 365, 300);
        addCircle(10, 295, 300);
        addCircle(3, 540, 100);
        addCircle(6, 470, 200);
        addCircle(12, 435, 300);
        addCircle(13, 505, 300);
        addCircle(7, 610, 200);
        addCircle(14, 585, 300);
        addCircle(15, 645, 300);

        for (i = 1; i <= len; i++) {
            stk[i].setVisible(true);
        }

        drawline(1, 1, 2);
        drawline(2, 1, 3);
        drawline(3, 2, 4);
        drawline(4, 2, 5);
        drawline(5, 3, 6);
        drawline(6, 3, 7);
        drawline(7, 4, 8);
        drawline(8, 4, 9);
        drawline(9, 5, 10);
        drawline(10, 5, 11);
        drawline(11, 6, 12);
        drawline(12, 6, 13);
        drawline(13, 7, 14);
        drawline(14, 7, 15);

        for (i = 1; i < len; i++) {
            edge[i].setVisible(true);
        }
    }

    /**
     * add a circle at a certain coordinate
     *
     * @param index index of the array , which the circle represents
     * @param x x coordinate
     * @param y y coordinate
     */
    void addCircle(int index, double x, double y) {
        circle[index] = new Circle(20, Color.CYAN);
        stk[index] = new StackPane();
        stk[index].getChildren().addAll(circle[index], text[index]);
        stk[index].setLayoutX(x - 20);
        stk[index].setLayoutY(y - 20);
        root.getChildren().add(stk[index]);
        stk[index].setVisible(false);

        cordinateX[index] = x;
        cordinateY[index] = y;
    }

    /**
     * draw a line connecting a pair of nodes
     *
     * @param index line index
     * @param from node one
     * @param to node two
     */
    void drawline(int index, int from, int to) {
        edge[index] = new Line(cordinateX[from], cordinateY[from], cordinateX[to], cordinateY[to]);
        root.getChildren().add(edge[index]);
        edge[index].setVisible(false);

        edge[index].setStroke(Color.BLUE);
        edge[index].setOpacity(0.3);
        edge[index].setStrokeWidth(4);
    }

    /**
     * show the current state
     *
     * @param index index of current state
     */
    void showState(int index) {
        for (int i = 1; i <= len; i++) {
            Circle cir = state[index].circle[i];
            circle[i].setRadius(cir.getRadius());
            circle[i].setFill(cir.getFill());

            Text txt = state[index].text[i];
            text[i].setText(txt.getText());
        }
        for (int i = 1; i < len; i++) {
            //System.out.print(state[index].lineVisibility[i]+" ");
            Line rekha = state[index].edge[i];
            edge[i].setStroke(rekha.getStroke());
            edge[i].setOpacity(rekha.getOpacity());
            edge[i].setStrokeWidth(rekha.getStrokeWidth());
            edge[i].setVisible(state[index].lineVisibility[i]);
        }
        //System.out.println("");
        instruction.setVisible(true);
        instruction.setText(state[index].instruction.getText());
        if (index == cntState - 1) {
            instruction.setText("Process Finished!!");
        }
        updateButtonVisibility();
    }

    /**
     * Update visibility for buttons prev and next
     */

    void updateButtonVisibility() {
        if (currState == cntState - 1) {
            next.setVisible(false);
        } else {
            next.setVisible(true);
        }
        if (currState == 0) {
            prev.setVisible(false);
        } else {
            prev.setVisible(true);
        }
    }
}
