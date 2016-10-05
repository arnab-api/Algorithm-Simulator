/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.io.IOException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * set up the window for topsort algorithm
 *
 * @author Arnab Sen Sharma
 */
public class TopSortSetUp {

    static boolean SimulatorStarted = false;
    int width = 950;
    int height = 500;

    static int cnt = 0;
    static int l_cnt = 0;

    Circle[] circle = new Circle[1010];
    double[] co_ordinX = new double[1010];
    double[] co_ordinY = new double[1010];
    Line[] line = new Line[1010];
    boolean AddNodeOn = false;
    Boolean AddEdgeOn = false;
    Boolean AddDirEdgeOn = false;
    Text[] text = new Text[1010];
    Text[] StackUpdate = new Text[1010];
    Info[] info = new Info[1010];
    Stack stack = new Stack();
    boolean[][] vec = new boolean[1010][1010];
    VBox vb = new VBox();
    Group root = new Group();
    Rectangle rect = new Rectangle(width, height);
    boolean play = true;
    static int srce = 1;
    Stage primaryStage;
    Text[] dfn = new Text[1010];

    static double CircleRadius = 25;
    static int step = 1;
    Button addEdge = new Button("Add edge");
    Button addNode = new Button("Add node");
    Button addDirEdge = new Button("Add Directed Edge");
    static Button run = new Button("Run");
    //Button pause = new Button("Pause");
    Button clear = new Button("Clear");
    Button SelectSource = new Button("Select Source");
    Button reset = new Button("Reset");
    Button back = new Button("Back");
    int[] finding = new int[1010];
    int[] exiting = new int[1010];
    VBox vvbb = new VBox();
    HBox InstructionPanel = new HBox();
    static Text Instruction = new Text("Step 1 : Run DFS to count finding and exiting time                         ");
    Text tt = new Text("a->b means that a must be taken before b");
    Button next = new Button("Next>>");
    Button prev = new Button("<<Prev");
    Button back2 = new Button("Back");
    int[][] nodetoline = new int[1010][1010];
    SaveGraphState[] state = new SaveGraphState[10101];
    static int stateCnt;
    int currState = 0;

    /**
     * set up the window for topsort algorithm/build the instance
     */
    TopSortSetUp() {

        cnt = 0;
        l_cnt = 0;
        srce = 1;
        stateCnt = 0;

        primaryStage = Algorithm_Simulator.window;
        primaryStage.setTitle("Topological Sort Simulator");

        tt.setLayoutX(0);
        tt.setLayoutY(20);
        tt.setId("text");
        tt.setFill(Color.ANTIQUEWHITE);
        root.getChildren().addAll(rect, tt);
        VBox grid = new VBox();

        addNode.setOnAction(e -> {
            GetNodeWithTime gn = new GetNodeWithTime(rect, circle, root, co_ordinX, co_ordinY, text, line, dfn);
            //System.out.println(cnt);
            clearButtonPressed();
            addNode.setId("button_play");
            AddNodeOn = true;
        });

        addDirEdge.setOnAction(e -> {
            clearButtonPressed();
            addDirEdge.setId("button_play");
            AddDirEdgeOn = true;

            /*for (int i = 1; i <= cnt; i++) {
             System.out.printf("%f %f\n", co_ordinX[i], co_ordinY[i]);
             }*/
            GetDirectedEdge ge = new GetDirectedEdge(rect, circle, root, co_ordinX, co_ordinY, text, vec, nodetoline, line);
        });

        run.setOnAction(e -> {
            clearButtonPressed();
            grid.getChildren().clear();
            back2.setId("button");
            currState = 0;
            stateCnt = 0;
            grid.getChildren().addAll(prev, next, back2);
            if (step == 1) {
                TopSortSorting ob1 = new TopSortSorting(circle, dfn, finding, exiting, 1, vec, line, state, nodetoline);
            }
            showState(0);
        });
        clear.setOnAction(e -> {
            clear_call();
            clearButtonPressed();
            run.setId("button");
            addDirEdge.setId("button_pause");
            addEdge.setId("button_pause");
            addNode.setId("button_pause");
        });
        back2.setOnAction(e -> {
            grid.getChildren().clear();
            grid.getChildren().addAll(addNode, addDirEdge, clear, run, back);
            for (int i = 1; i <= cnt; i++) {
                circle[i].setFill(Color.RED);
            }
            for (int i = 0; i < 1010; i++) {
                StackUpdate[i].setText("--");
                dfn[i].setText("oo/oo");
                dfn[i].setFill(Color.GOLD);
                dfn[i].setId("text");
                StackUpdate[i].setVisible(false);
                finding[i] = -1;
                exiting[i] = -1;
            }
            Instruction.setVisible(false);
            Instruction.setText("Step 1 : Run DFS to count finding and exiting time                         ");
        });
        /*pause.setId("button_pause");
         pause.setOnAction(e -> {
         clearButtonPressed();
         if (play) {
         pause.setText("Play");
         pause.setId("button_play");
         play = false;
         if (Algorithm_Simulator.threadStarted && Algorithm_Simulator.thread.isAlive()) {
         TopSortSorting.pause();
         }
         } else {
         pause.setText("Pause");
         pause.setId("button_pause");
         play = true;
         if (Algorithm_Simulator.threadStarted && Algorithm_Simulator.thread.isAlive()) {
         TopSortSorting.play();
         }
         }
         });*/
        /*reset.setOnAction(e -> {
         clearButtonPressed();
         if (Algorithm_Simulator.threadStarted && Algorithm_Simulator.thread.isAlive()) {
         ob1.stop();
         }
         for (int i = 1; i <= cnt; i++) {
         circle[i].setFill(Color.RED);
         }
         for (int i = 0; i < 1010; i++) {
         StackUpdate[i].setText("--");
         dfn[i].setText("oo/oo");
         dfn[i].setFill(Color.GOLD);
         dfn[i].setId("text");
         StackUpdate[i].setVisible(false);
         finding[i] = -1;
         exiting[i] = -1;
         }
         Instruction.setText("Step 1 : Run DFS to count finding and exiting time                         ");
         run.setVisible(true);
         run.setOnAction(ee -> {
         clearButtonPressed();
         if (step == 1) {
         ob1 = new Thread(new TopSortSorting(circle, dfn, finding, exiting, 1, vec));
         Algorithm_Simulator.thread = ob1;
         ob1.start();
         run.setVisible(false);
         }
         });
         });*/
        back.setOnAction(e -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene scene = new Scene(root);
                Algorithm_Simulator.window.setTitle("Algorithm Simulator V:1.0");
                Algorithm_Simulator.window.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(SortingInput.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        addEdge.setPrefSize(190, 50);
        addNode.setPrefSize(190, 50);
        //pause.setPrefSize(150, 50);

        addDirEdge.setPrefSize(200, 50);
        run.setPrefSize(200, 40);
        prev.setPrefSize(200, 40);
        back2.setPrefSize(200, 50);
        next.setPrefSize(200, 40);
        clear.setPrefSize(200, 50);
        SelectSource.setPrefSize(200, 50);
        reset.setPrefSize(200, 50);
        back.setPrefSize(200, 50);

        addEdge.setId("button_pause");
        addNode.setId("button_pause");
        addDirEdge.setId("button_pause");
        //pause.setId("button_pause");
        run.setId("button");
        prev.setId("button");
        back2.setId("button");
        next.setId("button");
        clear.setId("button");
        SelectSource.setId("button_pause");
        reset.setId("button");
        back.setId("button");
        back2.setId("button");

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setSpacing(10);
        grid.getChildren().addAll(addNode, addDirEdge, clear, run, back);

        Label label = new Label("Stack");
        label.setId("label_bold");
        vb.setPadding(new Insets(10, 10, 10, 10));
        vb.getChildren().add(label);
        vb.setPrefWidth(150);
        vb.setId("layout_queue");
        for (int i = 0; i < 1010; i++) {
            StackUpdate[i] = new Text("--");
            if (i == 0) {
                StackUpdate[i].setId("text_top");
            } else {
                StackUpdate[i].setId("text_normal");
            }
            vb.getChildren().add(StackUpdate[i]);
            dfn[i] = new Text("oo/oo");
            dfn[i].setFill(Color.GOLD);
            dfn[i].setId("text");
            StackUpdate[i].setVisible(false);
            finding[i] = -1;
            exiting[i] = -1;
        }

        InstructionPanel.setPrefHeight(100);
        InstructionPanel.setId("vbox_Instruction");
        Instruction.setId("text");
        InstructionPanel.getChildren().addAll(Instruction);
        InstructionPanel.setSpacing(10);
        Instruction.setVisible(false);
        prev.setVisible(false);
        next.setVisible(false);
        //vvbb.getChildren().addAll(root, InstructionPanel);

        HBox hb = new HBox();
        hb.getChildren().addAll(root, grid);
        VBox api = new VBox();
        api.getChildren().addAll(hb, InstructionPanel);

        Scene scene = new Scene(api, width + 200, 600);
        scene.getStylesheets().add("Style.css");
        primaryStage.setScene(scene);
        next.setOnAction(e -> {
            if (currState < stateCnt - 1) {
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
    }

    /**
     * Update visibility for buttons prev and next
     */
    void updateButtonVisibility() {
        if (currState == stateCnt - 1) {
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
        System.out.println("==================> " + index + " " + stateCnt);
        Instruction.setVisible(true);
        for (int i = 1; i <= cnt; i++) {
            Circle cir = state[index].circle[i];
            circle[i].setRadius(cir.getRadius());
            circle[i].setFill(cir.getFill());

            Text txt = state[index].dfn[i];
            dfn[i].setText(txt.getText());
        }
        Instruction.setText(state[index].instruction.getText());
        for (int i = 1; i <= l_cnt; i++) {
            line[i].setStroke(state[index].line[i].getStroke());
            line[i].setStrokeWidth(state[index].line[i].getStrokeWidth());
            line[i].setOpacity(state[index].line[i].getOpacity());
        }
        updateButtonVisibility();
    }

    /**
     * clears the graph drawing rectangle/canvas
     */
    void clear_call() {
        root.getChildren().clear();
        rect = new Rectangle(width, height);
        root.getChildren().addAll(rect, tt);
        cnt = 0;
        l_cnt = 0;

        circle = new Circle[1010];
        co_ordinX = new double[1010];
        co_ordinY = new double[1010];
        line = new Line[101010];
        AddNodeOn = false;
        AddEdgeOn = false;
        AddDirEdgeOn = false;
        text = new Text[1010];
        info = new Info[101010];
        stack = new Stack();
        vec = new boolean[1010][1010];
        for (int i = 0; i < 1010; i++) {
            finding[i] = -1;
            exiting[i] = -1;
            dfn[i] = new Text("oo/oo");
            StackUpdate[i].setVisible(false);
            dfn[i] = new Text("oo/oo");
            dfn[i].setFill(Color.GOLD);
            dfn[i].setId("text");
        }
        Instruction.setText("Step 1 : Run DFS to count finding and exiting time                         ");
    }

    void updateSource(int index) {
        for (int i = 1; i <= cnt; i++) {
            circle[i].setFill(Color.RED);
            circle[i].setOnMouseClicked(null);
        }
        srce = index;
        circle[srce].setFill(Color.BLUEVIOLET);
    }

    /**
     * assigns original color to all of the buttons
     */
    void clearButtonPressed() {
        addEdge.setId("button_pause");
        addNode.setId("button_pause");
        addDirEdge.setId("button_pause");
        //pause.setId("button_pause");
        run.setId("button");
        clear.setId("button");
        SelectSource.setId("button_pause");
        reset.setId("button");
        back.setId("button");
        prev.setId("button");
        next.setId("button");
        back2.setId("Button");
    }
}
