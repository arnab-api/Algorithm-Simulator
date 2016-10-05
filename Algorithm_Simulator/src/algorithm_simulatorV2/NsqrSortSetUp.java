/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * set up the window for O(n^2) sorting system (bubble, insertion, selection)
 * and QuickSort simulation
 *
 * @author Arnab Sen Sharma
 */
public class NsqrSortSetUp {

    double arr[];
    double origin[];
    Scene scn;
    Rectangle[] barchart, src;
    VBox[] stack;
    Text text[], instruction = new Text("Instruction");
    Stage window;
    static int cntState;
    int currState = 0;
    HBox hb = new HBox();
    HBox hb2 = new HBox();
    SaveNsqrSortState[] state = new SaveNsqrSortState[101010];
    Button back = new Button("Back");
    Button next = new Button("Next>>");
    Button prev = new Button("<<Prev");

    /**
     * import array of numbers to be sorted and get the window / build the class
     *
     * @param arr array of numbers to be sorted
     */
    NsqrSortSetUp(double[] arr) {
        this.arr = arr;
        origin = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            origin[i] = arr[i];
        }
        stack = new VBox[arr.length];
        text = new Text[arr.length];
        window = Algorithm_Simulator.window;
        cntState = 0;
    }

    /**
     * set up the window for heap sort simulation
     */
    void displaySorting() {
        VBox vb = new VBox();
        HBox layout = new HBox();
        layout.setSpacing(5);
        double unitHeight, unitWidth, min_val, max_val;
        max_val = Double.MIN_VALUE;
        min_val = Double.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < min_val) {
                min_val = arr[i];
            }
            if (arr[i] > max_val) {
                max_val = arr[i];
            }
        }
        //for(int i=0;i<arr.length;i++) arr[i] = arr[i] - min_val + 1;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < min_val) {
                min_val = arr[i];
            }
            if (arr[i] > max_val) {
                max_val = arr[i];
            }
        }

        barchart = new Rectangle[arr.length];

        unitHeight = 400 / (max_val - min_val + 1);
        unitWidth = 700.0 / (arr.length + 5);

        /*System.out.printf(">>>>> --->%f %f\n",unitHeight,unitWidth);
         for(int i=0;i<arr.length;i++) System.out.printf(" ->%f",arr[i]);
         System.out.println("");*/
        layout.getChildren().clear();
        for (int i = 0; i < arr.length; i++) {
            barchart[i] = new Rectangle(unitWidth, (arr[i] - min_val + 1) * unitHeight);
            //barchart[i].setFill(Color.BLACK);
            barchart[i].setFill(javafx.scene.paint.Color.CYAN);
            text[i] = new Text(String.format("%.1f", arr[i]));
            //text[i].setRotate(90);
            text[i].setRotate(180);
            stack[i] = new VBox();
            stack[i].setPrefWidth(unitWidth);
            stack[i].setPadding(new Insets(0, 0, 0, 0));
            stack[i].getChildren().addAll(barchart[i], text[i]);
            stack[i].setRotate(180);
            text[i].setStroke(Color.RED);
            //stack[i].setAlignment(Pos.BASELINE_CENTER);
            layout.getChildren().add(stack[i]);
        }

        //for(int i=0;i<barchart.length;i++) System.out.printf(" >>%f",barchart[i].getHeight());
        hb.setSpacing(10);
        hb.setPadding(new Insets(10, 10, 10, 10));

        hb2.setSpacing(4);
        hb2.getChildren().addAll(instruction);
        hb2.setPrefWidth(660);
        HBox hb3 = new HBox();
        hb3.getChildren().addAll(hb2, hb);

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

        hb.getChildren().addAll(prev, next, back);
        hb.setPrefHeight(80);
        vb.getChildren().addAll(layout, hb3);
        vb.setSpacing(80);
        back.setOnAction(e -> {
            window.setScene(SortingInput.scn);
        });
        back.setPrefSize(130, 50);

        if (FXMLDocumentController.Bubble) {
            RunSimulatorBubble ob1 = new RunSimulatorBubble(barchart, arr, stack, text, state);
        } else if (FXMLDocumentController.Selection) {
            RunSimulatorSelection ob1 = new RunSimulatorSelection(barchart, arr, stack, text, state);
        } else if (FXMLDocumentController.Insertion) {
            RunSimulatorInsertion ob1 = new RunSimulatorInsertion(barchart, arr, stack, text, state);
        } else if (FXMLDocumentController.Quick) {
            RunSimulatorQuick ob1 = new RunSimulatorQuick(barchart, arr, stack, text, state);
        }
        scn = new Scene(vb, 1100, 600);
        scn.getStylesheets().add("Test.css");
        window.setScene(scn);
        currState = 0;
        showState(0);
        System.out.printf("=======> %d\n", cntState);
    }

    /**
     * show the current state
     *
     * @param index index of current state
     */
    void showState(int index) {
        System.out.println("============>" + currState);
        for (int i = 0; i < arr.length; i++) {
            //stack[i].getChildren().clear();
            //stack[i].getChildren().addAll(state[index].bar[i],state[index].val[i]);
            Rectangle rec = state[index].bar[i];
            barchart[i].setHeight(rec.getHeight());
            barchart[i].setWidth(rec.getWidth());
            barchart[i].setFill(rec.getFill());

            Text txt = state[index].val[i];
            text[i].setText(txt.getText());
        }
        instruction.setVisible(true);
        instruction.setText(state[index].instruction.getText());
        if (index == cntState - 1) {
            for (int i = 0; i < arr.length; i++) {
                barchart[i].setFill(Color.GREENYELLOW);
            }
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
