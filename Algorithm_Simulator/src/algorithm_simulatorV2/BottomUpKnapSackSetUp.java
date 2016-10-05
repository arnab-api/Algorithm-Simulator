/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * set up input panel for Bottom up knapsack algorithm
 *
 * @author Arnab Sen Sharma
 */
public class BottomUpKnapSackSetUp {

    Button addItem = new Button("Add Item");
    Button deleteItem = new Button("Delete Item");
    Button proced = new Button("Proced >>");
    Button back = new Button("Back");

    Text[] weight_arr = new Text[1010];
    Text[] price_arr = new Text[1010];
    TextField[] weight_in = new TextField[1010];
    TextField[] price_in = new TextField[1010];
    VBox input = new VBox();
    Stage window;
    int itemno = 0;
    HBox[] hbb = new HBox[1010];
    int[] weight = new int[1010];
    int[] price = new int[1010];
    static Scene scn;
    int cap = 10;
    HBox addScrol = new HBox();

    /**
     * *
     * set up input panel for Bottom up knapsack algorithm
     */
    public BottomUpKnapSackSetUp() {
        itemno = 0;
        cap = 10;
        window = Algorithm_Simulator.window;
        window.setTitle("Bottom Up KnapSack");
        Label label = new Label(" Max Capacity: ");
        label.setId("label_input");
        TextField maxcap = new TextField("10");
        maxcap.setPrefSize(150, 40);
        maxcap.setPromptText("Maximum capacity???");

        input.setSpacing(10);
        input.setPadding(new Insets(10, 10, 10, 10));
        addItem.setOnAction(e -> addItemProperty());
        deleteItem.setOnAction(e -> {
            input.getChildren().clear();
            if (itemno > 0) {
                itemno--;
            }
            for (int i = 1; i <= itemno; i++) {
                input.getChildren().add(hbb[i]);
            }
        });
        proced.setOnAction((ActionEvent e) -> {
            boolean flag = true;
            for (int i = 1; i <= itemno; i++) {
                String wt = weight_in[i].getText().trim();
                if (wt.length() > 0) {
                    try {
                        weight[i] = Integer.parseInt(wt);
                    } catch (Exception ex) {
                        System.out.println("caught");
                        weight_in[i].clear();
                        weight_in[i].setPromptText("Wrong Input!!!");
                        flag = false;
                    }
                } else {
                    weight[i] = 0;
                }
            }
            for (int i = 1; i <= itemno; i++) {
                String wt = price_in[i].getText().trim();
                if (wt.length() > 0) {
                    try {
                        price[i] = Integer.parseInt(wt);
                    } catch (Exception ex) {
                        System.out.println("caught");
                        price_in[i].clear();
                        price_in[i].setPromptText("Wrong Input!!!");
                        flag = false;
                    }
                } else {
                    price[i] = 0;
                }
            }
            try {
                cap = Integer.parseInt(maxcap.getText().trim());
            } catch (Exception ex) {
                System.out.println("caught");
                maxcap.clear();
                maxcap.setPromptText("Wrong Input!!!");
                flag = false;
            }
            if (flag) {
                for (int i = 1; i <= itemno; i++) {
                    System.out.printf("%d --> %d\n", weight[i], price[i]);
                }
                KnapSackBuildUp but = new KnapSackBuildUp(itemno, cap, weight, price);
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
        HBox buttons = new HBox();
        buttons.setSpacing(10);
        HBox capacity = new HBox();
        capacity.setSpacing(10);
        capacity.getChildren().addAll(label, maxcap);
        buttons.getChildren().addAll(addItem, deleteItem, proced, back);
        addItem.setId("button");
        deleteItem.setId("button");
        proced.setId("button");
        proced.setPrefSize(170, 65);
        back.setId("button");
        VBox vb = new VBox();
        vb.setSpacing(10);
        vb.setPadding(new Insets(10, 10, 10, 30));

        ScrollPane scrollPane = new ScrollPane(input);
        scrollPane.setFitToHeight(true);

        BorderPane root = new BorderPane(scrollPane);
        root.setPadding(new Insets(10));

        vb.getChildren().addAll(buttons, capacity, root);
        scn = new Scene(vb, 800, 600);
        scn.getStylesheets().add("Style.css");
        window.setScene(scn);
    }

    /**
     * sets up weight and price textfield to add new item
     */
    public void addItemProperty() {
        HBox hb = new HBox();
        hb.setSpacing(20);
        itemno++;
        Label label = new Label(String.format("Item %2d : ", itemno));
        label.setId("label_input");

        weight_in[itemno] = new TextField();
        weight_in[itemno].setPrefSize(250, 40);
        weight_in[itemno].setPromptText("Weight");

        price_in[itemno] = new TextField();
        price_in[itemno].setPrefSize(250, 40);
        price_in[itemno].setPromptText("Price");
        hb.getChildren().addAll(label, weight_in[itemno], price_in[itemno]);
        hbb[itemno] = new HBox();
        hbb[itemno] = hb;
        input.getChildren().addAll(hb);
    }
}
