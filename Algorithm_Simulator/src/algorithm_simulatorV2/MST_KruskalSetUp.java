/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * set up the window for MST_krushkal simulation
 *
 * @author Arnab Sen Sharma
 */
public class MST_KruskalSetUp {

    static boolean SimulatorStarted = false;
    int width = 800;
    int height = 500;

    static int cnt = 0;
    static int l_cnt = 0;

    Circle[] circle = new Circle[1010];
    double[] co_ordinX = new double[1010];
    double[] co_ordinY = new double[1010];
    Line[] line = new Line[101010];
    boolean AddNodeOn = false;
    Boolean AddEdgeOn = false;
    Text[] text = new Text[1010];
    Info[] info = new Info[101010];
    boolean[][] vec = new boolean[1010][1010];
    VBox vb = new VBox();
    Group root = new Group();
    Rectangle rect = new Rectangle(width, height);
    boolean play = true;
    static Thread ob1;
    Stage primaryStage;

    static double CircleRadius = 25;
    static int step = 1;
    Button addEdge = new Button("Add edge");
    Button addNode = new Button("Add node");
    Button run = new Button("Run");
    Button pause = new Button("Pause");
    Button clear = new Button("Clear");
    Button reset = new Button("Reset");
    Button back = new Button("Back");
    VBox vvbb = new VBox(), CurrentSortedEdge = new VBox();
    Text[] edge_show = new Text[1010];
    EdgeInfo edge_panel = new EdgeInfo();
    TextField getWeight = new TextField();
    double weight[][] = new double[1010][1010];
    Button ok = new Button("OK");
    VBox weightPanel = new VBox();
    Text[][] wt_show = new Text[1010][1010];
    Edge edge[] = new Edge[101010];
    static int e_cnt;
    int[][] line_info = new int[1010][1010];
    SaveGraph2State[] state = new SaveGraph2State[101010];
    static int cntState = 0;
    int currState = 0;
    Button next = new Button("Next>>");
    Button prev = new Button("<<Prev");
    Button back2 = new Button("Back");

    /**
     * set up the window for MST_krushkal simulation/build the object
     */
    MST_KruskalSetUp() {

        cnt = 0;
        l_cnt = 0;
        e_cnt = 0;
        ob1 = Algorithm_Simulator.thread;
        cntState = 0;
        currState = 0;

        primaryStage = Algorithm_Simulator.window;
        primaryStage.setTitle("MST(kruskal) Simulator");
        for (int i = 0; i < 101; i++) {
            for (int j = 0; j < 101; j++) {
                wt_show[i][j] = new Text("--");
                wt_show[i][j].setId("text");
            }
        }

        root.getChildren().addAll(rect);
        VBox grid = new VBox();

        addNode.setOnAction(e -> {
            GetNode gn = new GetNode(rect, circle, root, co_ordinX, co_ordinY, text, line);
            //System.out.println(cnt);
            clearButtonPressed();
            addNode.setId("button_play");
            AddNodeOn = true;
        });
        addEdge.setOnAction(e -> {
            clearButtonPressed();
            addEdge.setId("button_play");
            AddEdgeOn = true;
            weightPanel.setVisible(true);

            GetEdgeWeighted ge = new GetEdgeWeighted(rect, circle, root, co_ordinX, co_ordinY, text, line, info, vec, getWeight, weight, edge_panel, wt_show, edge, edge_show, line_info);
        });

        run.setOnAction(e -> {
            if (cnt > 0) {
                cntState = 0;
                currState = 0;
                clearButtonPressed();
                RunMST_krus ob1 = new RunMST_krus(circle, line, line_info, edge, edge_show, state);
                grid.getChildren().clear();
                grid.getChildren().addAll(prev, next, back2);
                showState(0);
                updateButtonVisibility();
            }
        });
        clear.setOnAction(e -> {
            clear_call();
            clearButtonPressed();
            run.setId("button");
            addEdge.setId("button_pause");
            addEdge.setId("button_pause");
            addNode.setId("button_pause");
        });
        pause.setId("button_pause");
        reset.setOnAction(e -> {
            clearButtonPressed();
            for (int i = 1; i <= cnt; i++) {
                circle[i].setFill(Color.RED);
            }
            for (int i = 1; i <= l_cnt; i++) {
                line[i].setStroke(Color.CYAN);
                line[i].setStrokeWidth(4);
            }
            for (int i = 0; i < e_cnt; i++) {
                edge_show[i].setText(String.format("%d <-> %d =>%.1f", edge[i].u, edge[i].v, edge[i].w));
                edge_show[i].setVisible(true);
            }
        });
        back2.setOnAction(e -> {
            grid.getChildren().clear();
            grid.getChildren().addAll(addNode, addEdge, weightPanel, run, clear, back);
            clearButtonPressed();
            edge_show[0].setFill(Color.BLACK);
            for (int i = 1; i <= cnt; i++) {
                circle[i].setFill(Color.RED);
            }
            for (int i = 1; i <= l_cnt; i++) {
                line[i].setStroke(Color.CYAN);
                line[i].setOpacity(.4);
                line[i].setStrokeWidth(4);
            }
            for (int i = 0; i < e_cnt; i++) {
                edge_show[i].setText(String.format("%d <-> %d =>%.1f", edge[i].u, edge[i].v, edge[i].w));
                edge_show[i].setVisible(true);
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

        addEdge.setPrefSize(150, 50);
        addNode.setPrefSize(150, 50);
        pause.setPrefSize(150, 50);
        run.setPrefSize(150, 50);
        clear.setPrefSize(150, 50);
        reset.setPrefSize(150, 50);
        back.setPrefSize(150, 50);
        getWeight.setPrefSize(150, 50);
        getWeight.setPromptText("Weight??");
        getWeight.setFont(Font.font("Arial Rounded MT Bold", 15));
        ok.setPrefSize(150, 50);

        weightPanel.getChildren().addAll(getWeight);
        weightPanel.setVisible(false);

        addEdge.setId("button_pause");
        addNode.setId("button_pause");
        pause.setId("button_pause");
        run.setId("button");
        clear.setId("button");
        reset.setId("button");
        back.setId("button");

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setSpacing(10);
        grid.getChildren().addAll(addNode, addEdge, weightPanel, run, clear, back);

        Label label = new Label("Edges\n");
        label.setId("label_bold");
        CurrentSortedEdge.getChildren().add(label);

        vvbb.getChildren().addAll(root, edge_panel.setUp);

        CurrentSortedEdge.setPrefWidth(250);
        CurrentSortedEdge.setId("sorted_edge");
        for (int i = 0; i < 1010; i++) {
            edge_show[i] = new Text("--");
            if (i == 0) {
                edge_show[i].setId("text_top");
            } else {
                edge_show[i].setId("text_normal");
            }
            CurrentSortedEdge.getChildren().add(edge_show[i]);
            edge_show[i].setVisible(false);
        }
        HBox hb = new HBox();
        hb.getChildren().addAll(vvbb, CurrentSortedEdge, grid);

        Scene scene = new Scene(hb, width + 400, 600);
        scene.getStylesheets().add("Style.css");
        primaryStage.setScene(scene);

        prev.setPrefSize(150, 50);
        next.setPrefSize(150, 50);
        back2.setPrefSize(150, 50);
        prev.setId("button");
        next.setId("button");
        back2.setId("button");
        next.setOnAction(e -> {
            clearButtonPressed();
            if (currState < cntState - 1) {
                currState++;
                showState(currState);
            }
            updateButtonVisibility();
        });
        prev.setOnAction(e -> {
            clearButtonPressed();
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
     * clears the graph drawing rectangle/canvas and Edge panel
     */
    void clear_call() {
        root.getChildren().clear();
        rect = new Rectangle(width, height);
        root.getChildren().addAll(rect);
        cnt = 0;
        l_cnt = 0;
        e_cnt = 0;

        circle = new Circle[1010];
        co_ordinX = new double[1010];
        co_ordinY = new double[1010];
        line = new Line[101010];
        edge = new Edge[101010];
        AddNodeOn = false;
        AddEdgeOn = false;
        text = new Text[1010];
        info = new Info[101010];
        vec = new boolean[1010][1010];
        for (int i = 0; i < edge_show.length; i++) {
            edge_show[i].setVisible(false);
        }
    }

    /**
     * assigns original color to all of the buttons
     */
    void clearButtonPressed() {
        addEdge.setId("button_pause");
        addNode.setId("button_pause");
        pause.setId("button_pause");
        run.setId("button");
        clear.setId("button");
        reset.setId("button");
        back.setId("button");
        weightPanel.setVisible(false);
        /*rect.setOnMouseClicked(null);
         for (int i = 1; i <= cnt; i++) {
         circle[i].setOnMouseClicked(null);
         text[i].setOnMouseClicked(null);
         }*/
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

        }
        for (int i = 0; i < 1010; i++) {
            Text txt = state[index].queue[i];
            edge_show[i].setText(txt.getText());
            edge_show[i].setVisible(state[index].queueVis[i]);
            edge_show[i].setFill(txt.getFill());
        }
        for (int i = 1; i <= l_cnt; i++) {
            Line edg = state[index].edge[i];
            line[i].setStroke(edg.getStroke());
            line[i].setOpacity(edg.getOpacity());
            line[i].setStrokeWidth(edg.getStrokeWidth());
        }
    }
}
