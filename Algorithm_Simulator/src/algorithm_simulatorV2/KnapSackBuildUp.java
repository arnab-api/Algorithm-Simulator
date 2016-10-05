/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * set up the window for knapsack simulation
 *
 * @author Arnab Sen Sharma
 */
public class KnapSackBuildUp {

    Group root = new Group();
    Rectangle rect = new Rectangle(900, 530);
    Rectangle[][] rectangle = new Rectangle[1010][1010];
    HBox buttonPanel = new HBox();
    Button back = new Button("Back");
    double unitW, unitH, posX = 10, posY = 300;
    Text[][] showData = new Text[1010][1010];
    Stage window;
    Text compare = new Text("Compare :");
    boolean play = true;
    Thread ob1 = Algorithm_Simulator.thread;
    SaveDPstate state[] = new SaveDPstate[10010];
    static int cntState = 0;
    int currState = 0;
    Button next = new Button("Next>>");
    Button prev = new Button("<<Prev");
    Button back2 = new Button("Back");

    /**
     * import necessary elements
     *
     * @param itemno number of items
     * @param cap maximum capacity
     * @param weight weight of the items
     * @param price price if the items
     */
    KnapSackBuildUp(int itemno, int cap, int[] weight, int[] price) {

        cntState = 0;
        currState = 0;

        root.getChildren().add(rect);
        buttonPanel.setSpacing(10);
        buttonPanel.setPadding(new Insets(10, 10, 10, 430));
        buttonPanel.getChildren().addAll(prev, next, back);
        buttonPanel.setPrefHeight(100);

        unitW = (900 - 20 - itemno * 3) / (itemno + 1);
        unitH = 40;
        compare.setId("text");
        compare.setFill(Color.BLANCHEDALMOND);
        compare.setLayoutX(20);
        compare.setLayoutY(150);
        root.getChildren().add(compare);

        for (int x = 0; x <= itemno; x++) {
            StackPane stk = new StackPane();
            Rectangle rec = new Rectangle(unitW, unitH);
            rec.setFill(Color.BLUEVIOLET);
            Text tt = new Text();
            tt.setId("text");
            tt.setFill(Color.AZURE);
            stk.getChildren().addAll(rec, tt);
            stk.setLayoutX(10 + x * (unitW + 3));
            stk.setLayoutY(20);
            root.getChildren().add(stk);

            if (x == 0) {
                tt.setText("Weight->");
            } else {
                tt.setText(String.format("%d", weight[x]));
            }
        }

        for (int x = 0; x <= itemno; x++) {
            StackPane stk = new StackPane();
            Rectangle rec = new Rectangle(unitW, unitH);
            rec.setFill(Color.BLUEVIOLET);
            Text tt = new Text();
            tt.setFill(Color.AZURE);
            tt.setId("text");
            stk.getChildren().addAll(rec, tt);
            stk.setLayoutX(10 + x * (unitW + 3));
            stk.setLayoutY(70);
            root.getChildren().add(stk);

            if (x == 0) {
                tt.setText("price->");
            } else {
                tt.setText(String.format("%d", price[x]));
            }
        }

        unitW = (900 - 20 - cap * 3) / (cap + 1);
        unitH = (400 - 120 - itemno * 3) / (itemno + 1);
        for (int x = 0; x <= itemno; x++) {
            for (int y = 0; y <= cap; y++) {
                StackPane stk = new StackPane();
                rectangle[x][y] = new Rectangle(unitW, unitH);
                rectangle[x][y].setFill(Color.ORANGE);
                showData[x][y] = new Text("--");
                showData[x][y].setId("text");
                stk.getChildren().addAll(rectangle[x][y], showData[x][y]);
                posX = 10 + y * (unitW + 3);
                posY = 160 + x * (unitH + 3);
                stk.setLayoutX(posX);
                stk.setLayoutY(posY);
                root.getChildren().add(stk);

                if (x == 0) {
                    showData[x][y].setText(String.format("%d .", y));
                    rectangle[x][y].setFill(Color.AQUA);
                } else if (y == 0) {
                    showData[x][y].setText(String.format("%d .", x));
                    rectangle[x][y].setFill(Color.AQUA);
                }
            }
        }
        back.setId("button");
        prev.setId("button");
        next.setId("button");
        back.setOnAction(e -> {
            window.setScene(BottomUpKnapSackSetUp.scn);
        });
        next.setPrefSize(150, 50);
        prev.setPrefSize(150, 50);
        back.setPrefSize(150, 50);
        VBox vb = new VBox();
        vb.getChildren().addAll(root, buttonPanel);
        Scene scn = new Scene(vb, 900, 600);
        window = Algorithm_Simulator.window;
        scn.getStylesheets().add("Style1.css");
        window.setScene(scn);

        RunKnapSack ob1 = new RunKnapSack(showData, rectangle, weight, price, cap, itemno, compare, state);

        next.setOnAction(e -> {
            if (currState < cntState - 1) {
                currState++;
                showState(currState);
            }
            updateButtonVisibility();
        });
        prev.setOnAction(e -> {
            if (currState > 0) {
                currState--;
                showState(currState);
            }
            updateButtonVisibility();
        });
        showState(0);
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

    /**
     * show the current state
     *
     * @param index index of current state
     */
    void showState(int index) {
        int row = state[index].row;
        int col = state[index].col;
        compare.setText(state[index].text.getText());
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                rectangle[i][j].setFill(state[index].rec[i][j].getFill());
                showData[i][j].setText(state[index].data[i][j].getText());
            }
        }
        if(index==cntState-1) compare.setText("Process finished!!");
        updateButtonVisibility();
    }
}
