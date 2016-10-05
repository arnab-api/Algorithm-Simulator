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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * get two words from user to run LCS or Edit Distance process
 *
 * @author Arnab Sen Sharma
 */
public class TwoWordInput {

    Label instructuion = new Label("Enter two words in the textfields below\n\n\n");
    Label label1 = new Label(" 1st Word :   ");
    Label label2 = new Label("2nd Word :   ");
    TextField text1 = new TextField();
    TextField text2 = new TextField();
    VBox vb = new VBox();
    HBox buttonPanel = new HBox();
    String word1, word2;
    Button proceed = new Button("Proceed");
    Button back = new Button("Back");
    Stage window = Algorithm_Simulator.window;
    static Scene scn;

    /**
     * set up the window to get two words from user
     */
    public TwoWordInput() {
        vb.setSpacing(10);
        window.setTitle("Two Word Input");
        HBox hb1 = new HBox();
        HBox hb2 = new HBox();
        hb1.setSpacing(10);
        hb2.setSpacing(10);
        instructuion.setFont(Font.font("Arial Black", 25));
        label1.setFont(Font.font("Arial Black", 25));
        label2.setFont(Font.font("Arial Black", 25));
        text1.setPrefSize(400, 70);
        text2.setPrefSize(400, 70);
        text1.setFont(Font.font("Arial Rounded MT Bold", 25));
        text2.setFont(Font.font("Arial Rounded MT Bold", 25));

        hb1.getChildren().addAll(label1, text1);
        hb2.getChildren().addAll(label2, text2);
        vb.getChildren().addAll(instructuion, hb1, hb2);
        vb.setPrefHeight(500);
        vb.setPadding(new Insets(10, 10, 10, 10));
        buttonPanel.setPrefHeight(100);
        buttonPanel.setPadding(new Insets(10, 10, 10, 480));
        buttonPanel.setSpacing(10);
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
        proceed.setOnAction(e -> {
            word1 = text1.getText().trim();
            word2 = text2.getText().trim();
            LCSorEditDisSetUp setUp = new LCSorEditDisSetUp(word1, word2);
        });
        proceed.setId("button");
        proceed.setPrefSize(150, 65);
        back.setId("button");
        back.setPrefSize(150, 45);
        buttonPanel.getChildren().addAll(proceed, back);
        VBox vbb = new VBox();
        vbb.getChildren().addAll(vb, buttonPanel);

        scn = new Scene(vbb, 800, 600);
        scn.getStylesheets().add("Style.css");
        window.setScene(scn);
    }
}
