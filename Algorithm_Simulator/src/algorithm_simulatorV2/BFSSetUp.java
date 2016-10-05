/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Set up the window for BFS simulation
 *
 * @author User
 */
public class BFSSetUp {

    static boolean SimulatorStarted = false;
    int width = 800;
    int height = 1600;

    static int cnt = 0;
    static int l_cnt = 0;

    Circle[] circle = new Circle[1010];
    double[] co_ordinX = new double[1010];
    double[] co_ordinY = new double[1010];
    Line[] line = new Line[101010];
    boolean AddNodeOn = false;
    Boolean AddEdgeOn = false;
    Boolean AddDirEdgeOn = false;
    Text[] text = new Text[1010], dfn = new Text[1010];
    Text[] QueueUpdate = new Text[1010];
    Info[] info = new Info[101010];
    Queue<Integer> queue = new LinkedList<Integer>();
    boolean[][] vec = new boolean[1010][1010];
    VBox vb = new VBox();
    Group root = new Group();
    Rectangle rect = new Rectangle(width, height);
    boolean play = true;
    Thread ob1;
    static int srce = 1;
    Stage primaryStage;

    static double CircleRadius = 25;
    Button addEdge = new Button("Add edge");
    Button addNode = new Button("Add node");
    Button addDirEdge = new Button("Add Directed Edge");
    Button run = new Button("Run");
    //Button pause = new Button("Pause");
    Button clear = new Button("Clear");
    Button SelectSource = new Button("Select Source");
    Button reset = new Button("Reset");
    Button back = new Button("Back");
    Button next = new Button("Next>>");
    Button prev = new Button("<<Prev");
    Button back2 = new Button("Back");
    SaveGraphState state[] = new SaveGraphState[101010];
    static int cntState;
    int currState;
    int[][] nodetoline = new int[1010][1010];
    Paint[] edgeColor = new Paint[1010];

    /**
     * Set up the window for BFS Simulation
     */
    BFSSetUp() {

        cnt = 0;
        l_cnt = 0;
        srce = 1;
        cntState = 0;
        currState = 0;

        primaryStage = Algorithm_Simulator.window;
        root.getChildren().add(rect);
        VBox grid = new VBox();
        for (int i = 0; i < 1010; i++) {
            dfn[i] = new Text("oo");
            dfn[i].setId("text");
            dfn[i].setFill(Color.ALICEBLUE);
        }

        addNode.setOnAction(e -> {
            GetNodeWithTime gn = new GetNodeWithTime(rect, circle, root, co_ordinX, co_ordinY, text, line, dfn);
            System.out.println(cnt);
            clearButtonPressed();
            addNode.setId("button_play");
            AddNodeOn = true;
        });

        addEdge.setOnAction(e -> {
            clearButtonPressed();
            addEdge.setId("button_play");
            AddEdgeOn = true;

            for (int i = 1; i <= cnt; i++) {
                System.out.printf("%f %f\n", co_ordinX[i], co_ordinY[i]);
            }
            GetEdge ge = new GetEdge(rect, circle, root, co_ordinX, co_ordinY, text, line, info, vec, nodetoline);
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
            if (cnt > 0) {
                cntState = 0;
                currState = 0;
                clearButtonPressed();
                for (int i = 1; i <= l_cnt; i++) {
                    edgeColor[i] = line[i].getStroke();
                }
                RunBFS ob1 = new RunBFS(circle, queue, vec, QueueUpdate, dfn, state, line, nodetoline);
                grid.getChildren().clear();
                grid.getChildren().addAll(prev, next, back2);
                showState(0);
            }
        });
        back2.setOnAction(e -> {
            grid.getChildren().clear();
            grid.getChildren().addAll(addNode, addEdge, addDirEdge, run, SelectSource, clear, back);
            clearButtonPressed();
            for (int i = 1; i <= cnt; i++) {
                circle[i].setFill(Color.RED);
                circle[i].setRadius(CircleRadius);
            }
            for (int i = 0; i < 1010; i++) {
                QueueUpdate[i].setText("--");
                QueueUpdate[i].setVisible(false);
                dfn[i].setText("oo");
            }
            for (int i = 1; i <= l_cnt; i++) {
                line[i].setStroke(edgeColor[i]);
                line[i].setOpacity(.5);
                line[i].setStrokeWidth(4);
            }
        });
        clear.setOnAction(e -> {
            clear_call();
            clearButtonPressed();
            run.setId("button");
            addDirEdge.setId("button_pause");
            addEdge.setId("button_pause");
            addNode.setId("button_pause");
        });
        /*pause.setId("button_pause");
         pause.setOnAction(e -> {
         clearButtonPressed();
         if (play) {
         pause.setText("Play");
         pause.setId("button_play");
         play = false;
         if (Algorithm_Simulator.threadStarted && Algorithm_Simulator.thread.isAlive()) {
         RunBFS.pause();
         }
         } else {
         pause.setText("Pause");
         pause.setId("button_pause");
         play = true;
         if (Algorithm_Simulator.threadStarted && Algorithm_Simulator.thread.isAlive()) {
         RunBFS.play();
         }
         }
         });*/
        SelectSource.setOnAction(e -> {
            if (cnt > 0) {
                clearButtonPressed();
                SelectSource.setId("button_play");
                for (int i = 1; i <= cnt; i++) {
                    int alt = i;
                    circle[i].setOnMouseClicked(e1 -> {
                        updateSource(alt);
                    });
                }
                for (int i = 1; i <= cnt; i++) {
                    int alt = i;
                    text[i].setOnMouseClicked(e2 -> {
                        updateSource(alt);
                    });
                }
            }
        });
        back.setOnAction(e -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene scene = new Scene(root);
                Algorithm_Simulator.window.setTitle("Algorithm Simulator V:2.0");
                Algorithm_Simulator.window.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(SortingInput.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        addEdge.setPrefSize(190, 50);
        addNode.setPrefSize(190, 50);
        //pause.setPrefSize(150, 50);
        addDirEdge.setPrefSize(190, 50);
        run.setPrefSize(190, 50);
        clear.setPrefSize(190, 50);
        SelectSource.setPrefSize(190, 50);
        reset.setPrefSize(190, 50);
        back.setPrefSize(190, 50);

        addEdge.setId("button_pause");
        addNode.setId("button_pause");
        addDirEdge.setId("button_pause");
        //pause.setId("button_pause");
        run.setId("button");
        clear.setId("button");
        SelectSource.setId("button_pause");
        reset.setId("button");
        back.setId("button");

        prev.setPrefSize(150, 50);
        next.setPrefSize(150, 50);
        back2.setPrefSize(150, 50);
        prev.setId("button");
        next.setId("button");
        back2.setId("button");

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setSpacing(10);
        grid.getChildren().addAll(addNode, addEdge, addDirEdge, run, SelectSource, clear, back);

        Label label = new Label("Queue");
        label.setId("label_bold");
        vb.setPadding(new Insets(10, 10, 10, 10));
        vb.getChildren().add(label);
        vb.setPrefWidth(150);
        vb.setId("layout_queue");
        for (int i = 0; i < 1010; i++) {
            QueueUpdate[i] = new Text("--");
            if (i == 0) {
                QueueUpdate[i].setId("text_top");
            } else {
                QueueUpdate[i].setId("text_normal");
            }
            vb.getChildren().add(QueueUpdate[i]);
            QueueUpdate[i].setVisible(false);
        }

        HBox hb = new HBox();
        hb.getChildren().addAll(root, vb, grid);

        Scene scene = new Scene(hb, width + 350, 600);
        primaryStage.setTitle("BFS Simulator");
        scene.getStylesheets().add("Style.css");
        primaryStage.setScene(scene);

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
     * clears the graph drawing rectangle/canvas and Queue panel
     */
    void clear_call() {
        root.getChildren().clear();
        rect = new Rectangle(width, height);
        root.getChildren().add(rect);
        cnt = 0;
        l_cnt = 0;
        cntState = 0;
        currState = 0;

        circle = new Circle[1010];
        co_ordinX = new double[1010];
        co_ordinY = new double[1010];
        line = new Line[101010];
        AddNodeOn = false;
        AddEdgeOn = false;
        AddDirEdgeOn = false;
        text = new Text[1010];
        info = new Info[101010];
        queue = new LinkedList<Integer>();
        vec = new boolean[1010][1010];
        for (int i = 0; i < 1010; i++) {
            QueueUpdate[i].setVisible(false);
            dfn[i].setText("oo");
        }
    }

    /**
     * updates the source node of the graph
     *
     * @param index new source
     */
    void updateSource(int index) {
        for (int i = 1; i <= cnt; i++) {
            circle[i].setFill(Color.RED);
            dfn[i].setText("oo");
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
    }

    /**
     * show the current state of BFS simulation for the current graph
     *
     * @param index the state of simulation process to be shown
     */
    void showState(int index) {
        System.out.println("==================>" + index);
        for (int i = 1; i <= cnt; i++) {
            Circle cir = state[index].circle[i];
            circle[i].setRadius(cir.getRadius());
            circle[i].setFill(cir.getFill());

            Text txt = state[index].dfn[i];
            dfn[i].setText(txt.getText());
        }
        for (int i = 0; i < 1010; i++) {
            Text txt = state[index].queue[i];
            QueueUpdate[i].setText(txt.getText());
            QueueUpdate[i].setVisible(state[index].queueVis[i]);
        }
        for (int i = 1; i <= l_cnt; i++) {
            line[i].setStroke(state[index].line[i].getStroke());
            line[i].setStrokeWidth(state[index].line[i].getStrokeWidth());
            line[i].setOpacity(state[index].line[i].getOpacity());
        }
        updateButtonVisibility();
    }
}
