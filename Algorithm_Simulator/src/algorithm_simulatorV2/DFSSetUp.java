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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * set up the window for DFS simulation
 *
 * @author Arnab Sen Sharma
 */
public class DFSSetUp {

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
    Text[] text = new Text[1010];
    Text[] StackUpdate = new Text[1010];
    Info[] info = new Info[101010];
    Stack stack = new Stack();
    boolean[][] vec = new boolean[1010][1010];
    VBox vb = new VBox();
    Group root = new Group();
    Rectangle rect = new Rectangle(width, height);
    boolean play = true;
    Thread ob1;
    static int srce = 1;
    Stage primaryStage;
    Text[] dfn = new Text[1010];

    static double CircleRadius = 25;
    Button addEdge = new Button("Add edge");
    Button addNode = new Button("Add node");
    Button addDirEdge = new Button("Add Directed Edge");
    Button run = new Button("Run");
    Button pause = new Button("Pause");
    Button clear = new Button("Clear");
    Button SelectSource = new Button("Select Source");
    Button reset = new Button("Reset");
    Button back = new Button("Back");
    int[] finding = new int[1010];
    int[] exiting = new int[1010];
    Button next = new Button("Next>>");
    Button prev = new Button("<<Prev");
    Button back2 = new Button("Back");
    SaveGraphState state[] = new SaveGraphState[101010];
    static int cntState;
    int currState;
    int[][] nodetoline = new int[1010][1010];
    Paint[] edgeColor = new Paint[1010];

    /**
     * set up the window for DFS simulation
     */
    DFSSetUp() {

        cnt = 0;
        l_cnt = 0;
        srce = 1;

        primaryStage = Algorithm_Simulator.window;
        if (FXMLDocumentController.DFS) {
            primaryStage.setTitle("DFS Simulator");
        } else if (FXMLDocumentController.SCC_tar) {
            primaryStage.setTitle("SCC(tarzan) Simulator");
        }
        root.getChildren().add(rect);
        VBox grid = new VBox();

        addNode.setOnAction(e -> {
            GetNodeWithTime gn = new GetNodeWithTime(rect, circle, root, co_ordinX, co_ordinY, text, line, dfn);
            //System.out.println(cnt);
            clearButtonPressed();
            addNode.setId("button_play");
            AddNodeOn = true;
        });

        addEdge.setOnAction(e -> {
            clearButtonPressed();
            addEdge.setId("button_play");
            AddEdgeOn = true;

            /*for (int i = 1; i <= cnt; i++) {
             System.out.printf("%f %f\n", co_ordinX[i], co_ordinY[i]);
             }*/
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
                if (FXMLDocumentController.DFS) {
                    RunDFS ob1 = new RunDFS(circle, stack, vec, StackUpdate, finding, exiting, dfn, state, line, nodetoline);
                } else if (FXMLDocumentController.SCC_tar) {
                    RunSCC_tarzan ob1 = new RunSCC_tarzan(circle, stack, vec, StackUpdate, finding, exiting, dfn, state);
                }
                grid.getChildren().clear();
                grid.getChildren().addAll(prev, next, back2);
                showState(0);
            }

        });
        back2.setOnAction(e -> {
            grid.getChildren().clear();
            if (FXMLDocumentController.DFS) {
                grid.getChildren().addAll(addNode, addEdge, addDirEdge, run, SelectSource, clear, back);
            } else {
                grid.getChildren().addAll(addNode, addDirEdge, run, SelectSource, clear, back);
            }
            clearButtonPressed();
            for (int i = 1; i <= cnt; i++) {
                circle[i].setFill(Color.RED);
                circle[i].setRadius(CircleRadius);
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
        pause.setId("button_pause");
        /*pause.setOnAction(e -> {
         clearButtonPressed();
         if (play) {
         pause.setText("Play");
         pause.setId("button_play");
         play = false;
         if (Algorithm_Simulator.threadStarted && Algorithm_Simulator.thread.isAlive()) {
         if (FXMLDocumentController.DFS) {
         RunDFS.pause();
         } else if (FXMLDocumentController.SCC_tar) {
         RunSCC_tarzan.pause();
         }
         }
         } else {
         pause.setText("Pause");
         pause.setId("button_pause");
         play = true;
         if (FXMLDocumentController.DFS) {
         RunDFS.play();
         } else if (FXMLDocumentController.SCC_tar) {
         RunSCC_tarzan.play();
         }
         }
         });*/
        SelectSource.setOnAction(e -> {
            if (cnt > 1) {
                clearButtonPressed();
                SelectSource.setId("button_play");
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
                for (int i = 1; i <= cnt; i++) {
                    int alt = i;
                    circle[i].setOnMouseClicked(e1 -> {
                        updateSource(alt);
                        SelectSource.setId("button_pause");
                    });
                }
            }
        });
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
        pause.setPrefSize(190, 50);
        addDirEdge.setPrefSize(190, 50);
        run.setPrefSize(190, 50);
        clear.setPrefSize(190, 50);
        SelectSource.setPrefSize(190, 50);
        reset.setPrefSize(190, 50);
        back.setPrefSize(190, 50);

        addEdge.setId("button_pause");
        addNode.setId("button_pause");
        addDirEdge.setId("button_pause");
        pause.setId("button_pause");
        run.setId("button");
        clear.setId("button");
        SelectSource.setId("button_pause");
        reset.setId("button");
        back.setId("button");

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setSpacing(10);
        if (FXMLDocumentController.DFS) {
            grid.getChildren().addAll(addNode, addEdge, addDirEdge, run, SelectSource, clear, back);
        } else {
            grid.getChildren().addAll(addNode, addDirEdge, run, SelectSource, clear, back);
        }
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

        HBox hb = new HBox();
        hb.getChildren().addAll(root, vb, grid);

        Scene scene = new Scene(hb, width + 350, 600);
        scene.getStylesheets().add("Style.css");
        primaryStage.setScene(scene);

        prev.setPrefSize(150, 50);
        next.setPrefSize(150, 50);
        back2.setPrefSize(150, 50);
        prev.setId("button");
        next.setId("button");
        back2.setId("button");
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

    /**
     * clears the graph drawing rectangle/canvas and Stack panel
     */
    void clear_call() {
        root.getChildren().clear();
        rect = new Rectangle(width, height);
        root.getChildren().add(rect);
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
    }

    /**
     * update the source of the graph
     *
     * @param index new source
     */
    void updateSource(int index) {
        for (int i = 1; i <= cnt; i++) {
            circle[i].setFill(Color.RED);
            circle[i].setOnMouseClicked(null);
        }
        srce = index;
        circle[srce].setFill(Color.BLUEVIOLET);
    }

    /**
     * reset all buttons to original state
     */
    void clearButtonPressed() {
        addEdge.setId("button_pause");
        addNode.setId("button_pause");
        addDirEdge.setId("button_pause");
        pause.setId("button_pause");
        run.setId("button");
        clear.setId("button");
        SelectSource.setId("button_pause");
        reset.setId("button");
        back.setId("button");
    }

    /**
     * show the current state
     *
     * @param index index of current state
     */
    void showState(int index) {
        System.out.println("==================>" + index + " " + cntState);
        for (int i = 1; i <= cnt; i++) {
            Circle cir = state[index].circle[i];
            circle[i].setRadius(cir.getRadius());
            circle[i].setFill(cir.getFill());

            Text txt = state[index].dfn[i];
            dfn[i].setText(txt.getText());
        }
        for (int i = 0; i < 1010; i++) {
            Text txt = state[index].queue[i];
            StackUpdate[i].setText(txt.getText());
            StackUpdate[i].setVisible(state[index].queueVis[i]);
        }
        if (FXMLDocumentController.DFS) {
            for (int i = 1; i <= l_cnt; i++) {
                line[i].setStroke(state[index].line[i].getStroke());
                line[i].setStrokeWidth(state[index].line[i].getStrokeWidth());
                line[i].setOpacity(state[index].line[i].getOpacity());
            }
        }
        updateButtonVisibility();
    }
}
