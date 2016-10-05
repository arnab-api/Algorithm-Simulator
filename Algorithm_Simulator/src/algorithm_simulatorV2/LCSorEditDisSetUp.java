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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * set up the window for LCS or Edit Distance simulation
 *
 * @author Arnab Sen Sharma
 */
public class LCSorEditDisSetUp {

    String word1;
    String word2;
    int len1, len2;
    Stage window = Algorithm_Simulator.window;

    Group root = new Group();
    Rectangle rect = new Rectangle(800, 530);
    Rectangle[][] rectangle = new Rectangle[1010][1010];
    HBox buttonPanel = new HBox();
    Button back = new Button("Back");
    double unitW, unitH, posX = 10, posY = 300;
    Text[][] showData = new Text[1010][1010];
    Text compare = new Text("A possible LCS :");
    boolean play = true;
    Rectangle[] w1_rr = new Rectangle[1010];
    Rectangle[] w2_rr = new Rectangle[1010];
    Text[] w1_tt = new Text[1010];
    Text[] w2_tt = new Text[1010];
    SaveDPstate state[] = new SaveDPstate[10010];
    static int cntState = 0;
    int currState = 0;
    Button next = new Button("Next>>");
    Button prev = new Button("<<Prev");
    Button back2 = new Button("Back");

    /**
     * import two strings on which the algorithm will run
     *
     * @param str1 string 1
     * @param str2 string 2
     */
    public LCSorEditDisSetUp(String str1, String str2) {

        cntState = 0;
        currState = 0;

        word1 = str1;
        word2 = str2;
        len1 = word1.length();
        len2 = word2.length();
        word1 = "-" + word1;
        word2 = "-" + word2;

        root.getChildren().add(rect);
        buttonPanel.setSpacing(10);
        buttonPanel.setPadding(new Insets(10, 10, 10, 430));
        buttonPanel.getChildren().addAll(prev, next, back);
        buttonPanel.setPrefHeight(100);
        compare.setFont(Font.font("Arial Rounded MT Bold", 25));
        compare.setFill(Color.BLANCHEDALMOND);
        compare.setLayoutX(20);
        compare.setLayoutY(130);
        root.getChildren().add(compare);

        unitW = (800 - 20 - len2 * 3) / (len2 + 2);
        unitH = (400 - 120 - len1 * 3) / (len1 + 2);

        for (int x = 0; x <= len1; x++) {
            Rectangle rr;
            Text tt;
            int y = 0;
            StackPane stk = new StackPane();
            rr = new Rectangle(unitW, unitH);
            rr.setFill(Color.BLUEVIOLET);
            tt = new Text(String.format("%d(%c).", x, word1.charAt(x)));
            tt.setId("text");
            tt.setFill(Color.AZURE);
            stk.getChildren().addAll(rr, tt);
            posX = 10 + (y) * (unitW + 3);
            posY = 160 + (x + 1) * (unitH + 3);
            stk.setLayoutX(posX);
            stk.setLayoutY(posY);
            root.getChildren().add(stk);

            w1_rr[x] = new Rectangle();
            w1_rr[x] = rr;
            w1_tt[x] = new Text();
            w1_tt[x] = tt;
        }

        for (int y = 0; y <= len2; y++) {
            Rectangle rr;
            Text tt;
            int x = 0;
            StackPane stk = new StackPane();
            rr = new Rectangle(unitW, unitH);
            rr.setFill(Color.BLUEVIOLET);
            tt = new Text(String.format("%d(%c).", y, word2.charAt(y)));
            tt.setId("text");
            tt.setFill(Color.AZURE);
            stk.getChildren().addAll(rr, tt);
            posX = 10 + (y + 1) * (unitW + 3);
            posY = 160 + (x) * (unitH + 3);
            stk.setLayoutX(posX);
            stk.setLayoutY(posY);
            root.getChildren().add(stk);

            w2_rr[y] = new Rectangle();
            w2_rr[y] = rr;
            w2_tt[y] = new Text();
            w2_tt[y] = tt;
        }

        /*for(int x=0;x<=100;x++){
         for(int y=0;y<=100;y++) {
         showData[x][y] = new Text("--");
         rectangle[x][y] = new Rectangle(unitW,unitH);
         }
         }*/
        for (int x = 0; x <= len1; x++) {
            for (int y = 0; y <= len2; y++) {
                StackPane stk = new StackPane();
                rectangle[x][y] = new Rectangle(unitW, unitH);
                rectangle[x][y].setFill(Color.ORANGE);
                showData[x][y] = new Text("--");
                showData[x][y].setId("text");
                stk.getChildren().addAll(rectangle[x][y], showData[x][y]);
                posX = 10 + (y + 1) * (unitW + 3);
                posY = 160 + (x + 1) * (unitH + 3);
                stk.setLayoutX(posX);
                stk.setLayoutY(posY);
                root.getChildren().add(stk);
            }
        }
        back.setId("button");
        prev.setId("button");
        next.setId("button");
        back.setOnAction(e -> {
            window.setScene(TwoWordInput.scn);
        });
        prev.setPrefSize(150, 50);
        next.setPrefSize(150, 50);
        back.setPrefSize(150, 50);
        VBox vb = new VBox();
        vb.getChildren().addAll(root, buttonPanel);
        Scene scn = new Scene(vb, 800, 600);
        window = Algorithm_Simulator.window;
        scn.getStylesheets().add("Style1.css");
        window.setScene(scn);

        /*System.out.printf("len1 = %d && len2 = %d\n",len1,len2);
         for(int i=0;i<word1.length();i++) System.out.printf("%d(%c)",i,word1.charAt(i));
         System.out.println("");
         for(int i=0;i<word2.length();i++) System.out.printf("%d(%c)",i,word2.charAt(i));
         System.out.println("========>");*/
        if (FXMLDocumentController.LCS) {
            compare.setVisible(true);
            RunLCS ob1 = new RunLCS(word1, word2, len1, len2, showData, rectangle, w1_rr, w2_rr, compare, state);
        } else if (FXMLDocumentController.editDis) {
            compare.setVisible(false);
            RunEditDis ob1 = new RunEditDis(word1, word2, len1, len2, showData, rectangle, w1_rr, w2_rr, state);
        }
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
        if (FXMLDocumentController.LCS) {
            compare.setVisible(state[index].visibility);
            for (int i = 0; i < row; i++) {
                w1_rr[i].setFill(state[index].rec_arr1[i].getFill());
            }
            for (int i = 0; i < col; i++) {
                w2_rr[i].setFill(state[index].rec_arr2[i].getFill());
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                rectangle[i][j].setFill(state[index].rec[i][j].getFill());
                showData[i][j].setText(state[index].data[i][j].getText());
            }
        }
        updateButtonVisibility();
    }
}
