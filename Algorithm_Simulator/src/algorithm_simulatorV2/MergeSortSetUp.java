/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * set up the window for Merge Sort simulation
 *
 * @author Arnab Sen Sharma
 */
public class MergeSortSetUp {

    double arr[], origin[];
    static Thread ob1;
    Rectangle barchart[], dupliBar[];
    int len, i;
    VBox stack[], dupliStack[];
    Text text[], dupliText[];
    Stage window;
    static int cntState;
    int currState;
    SaveMergeSortState[] state = new SaveMergeSortState[101010];
    Button next = new Button("Next>>");
    Button prev = new Button("<<Prev");
    Text instruction = new Text("Instruction");

    /**
     * import array of numbers to be sorted and get the window / build the class
     *
     * @param arr array of numbers to be sorted
     */
    MergeSortSetUp(double arr[]) {
        cntState = 0;
        currState = 0;
        this.arr = arr;
        len = arr.length;
        origin = new double[len];
        stack = new VBox[len];
        text = new Text[len];
        for (int i = 0; i < len; i++) {
            origin[i] = arr[i];
        }
        window = Algorithm_Simulator.window;
    }

    /**
     * set up the window for heap sort simulation
     */
    public void displaySorting() {
        Scene scn;
        VBox vb = new VBox();
        HBox hb1 = new HBox();
        hb1.setSpacing(5);
        barchart = new Rectangle[len];
        dupliBar = new Rectangle[len];
        dupliText = new Text[len];
        dupliStack = new VBox[len];

        double unitHeight, unitWidth, min_val, max_val;
        max_val = Double.MIN_VALUE;
        min_val = Double.MAX_VALUE;
        for (i = 0; i < arr.length; i++) {
            if (arr[i] < min_val) {
                min_val = arr[i];
            }
            if (arr[i] > max_val) {
                max_val = arr[i];
            }
        }
        //for(i=0;i<arr.length;i++) arr[i] = arr[i] - min_val + 1;

        for (i = 0; i < arr.length; i++) {
            if (arr[i] - min_val + 1 < min_val) {
                min_val = arr[i] - min_val + 1;
            }
            if (arr[i] - min_val + 1 > max_val) {
                max_val = arr[i] - min_val + 1;
            }
        }

        unitHeight = 200 / (max_val);
        unitWidth = 700.0 / (arr.length + 5);
        hb1.setPrefSize(800, 250);
        /*hb1.getChildren().clear();
        for (i = 0; i < len; i++) {
            barchart[i] = new Rectangle(unitWidth, arr[i] * unitHeight);
            text[i] = new Text(String.format("%.1f", arr[i]));
            text[i].setRotate(90);
            barchart[i].setFill(Color.BROWN);
            stack[i] = new VBox();
            stack[i].getChildren().addAll(barchart[i], text[i]);
            stack[i].setAlignment(Pos.BASELINE_CENTER);
            hb1.getChildren().add(stack[i]);
        }*/
        for (int i = 0; i < arr.length; i++) {
            barchart[i] = new Rectangle(unitWidth, (arr[i] - min_val + 1) * unitHeight);
            //barchart[i].setFill(Color.BLACK);
            barchart[i].setFill(javafx.scene.paint.Color.BROWN);
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
            hb1.getChildren().add(stack[i]);
        }
        Button back = new Button("Back");

        back.setOnAction(e -> {
            window.setScene(SortingInput.scn);
        });
        HBox hb2 = new HBox();
        hb2.setPrefSize(800, 250);
        hb1.setId("hbox_main");
        hb2.setId("hbox_sub");

        /*for (int i = 0; i < len; i++) {
            dupliBar[i] = new Rectangle(unitWidth, 0);
            dupliText[i] = new Text("--");
            dupliText[i].setRotate(90);

            dupliStack[i] = new VBox();
            dupliStack[i].getChildren().addAll(dupliBar[i], dupliText[i]);
            dupliStack[i].setAlignment(Pos.BASELINE_CENTER);

            hb2.getChildren().addAll(dupliStack[i]);
        }*/
        for (int i = 0; i < arr.length; i++) {
            dupliBar[i] = new Rectangle(unitWidth,0);
            //barchart[i].setFill(Color.BLACK);
            //dupliBar[i].setFill(javafx.scene.paint.Color.BROWN);
            dupliText[i] = new Text("--");
            //text[i].setRotate(90);
            dupliText[i].setRotate(180);
            dupliStack[i] = new VBox();
            dupliStack[i].setPrefWidth(unitWidth);
            dupliStack[i].setPadding(new Insets(0, 0, 0, 0));
            dupliStack[i].getChildren().addAll(dupliBar[i], dupliText[i]);
            dupliStack[i].setRotate(180);
            dupliText[i].setStroke(Color.RED);
            //stack[i].setAlignment(Pos.BASELINE_CENTER);
            hb2.getChildren().add(dupliStack[i]);
        }
        
        
        hb2.setSpacing(5);

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
        next.setPrefSize(150, 50);
        prev.setPrefSize(150, 50);
        back.setPrefSize(150, 50);
        HBox hb3 = new HBox();
        hb3.getChildren().addAll(prev, next, back);
        HBox hb4 = new HBox();
        HBox h5 = new HBox();
        h5.getChildren().add(instruction);
        hb4.getChildren().addAll(h5, hb3);
        h5.setPrefWidth(650);
        vb.getChildren().addAll(hb1, hb2, hb4);
        scn = new Scene(vb, 1100, 600);
        hb3.setPadding(new Insets(10, 10, 10, 10));
        hb3.setSpacing(5);
        instruction.setFont(Font.font("Arial Rounded MT Bold", 15));
        scn.getStylesheets().add("Style1.css");

        RunSimulatorMerge ob1 = new RunSimulatorMerge(arr, barchart, dupliBar, text, dupliText, state);
        Algorithm_Simulator.window.setScene(scn);
        System.out.printf("=======> %d\n", cntState);
        showState(0);
    }

    /**
     * show the current state
     *
     * @param index index of current state
     */
    void showState(int index) {
        for (int i = 0; i < len; i++) {
            Rectangle rec = state[index].bar[i];
            barchart[i].setHeight(rec.getHeight());
            barchart[i].setWidth(rec.getWidth());
            barchart[i].setFill(rec.getFill());

            Text txt = state[index].val[i];
            text[i].setText(txt.getText());

            Rectangle rec2 = state[index].dupliBar[i];
            dupliBar[i].setHeight(rec2.getHeight());
            dupliBar[i].setWidth(rec2.getWidth());
            dupliBar[i].setFill(rec2.getFill());

            Text txt2 = state[index].dupliVal[i];
            dupliText[i].setText(txt2.getText());
        }
        instruction.setVisible(true);
        instruction.setText(state[index].instruction.getText());
        if (index == cntState - 1) {
            for (int i = 0; i < arr.length; i++) {
                barchart[i].setFill(Color.GREEN);
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
