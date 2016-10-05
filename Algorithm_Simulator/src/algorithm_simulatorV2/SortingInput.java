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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * get input for sorting algorithms
 *
 * @author Arnab Sen Sharma
 */
public class SortingInput {

    static Scene scn;

    /**
     * set the title of the window/ build instance
     *
     * @param title window title
     */
    SortingInput(String title) {
        Algorithm_Simulator.window.setTitle(title);
    }
    Text warning = new Text("In case of heapsort number of input must be less then 17");

    /**
     * set up window to get input
     */
    public void getInput() {
        warning.setFont(Font.font("Arial Rounded MT Bold", 25));
        VBox vb = new VBox();
        Label label = new Label("Please insert numbers seperated by at least one space(' ')");
        label.setFont(Font.font("Arial Rounded MT Bold", 25));
        HBox stk = new HBox();
        TextField text = new TextField();
        text.setPrefHeight(100);
        text.setPrefWidth(800);
        text.setPromptText("Your Input Here");
        Button proceed = new Button("Proceed");
        proceed.setPrefSize(200, 100);

        vb.setSpacing(30);
        proceed.setOnAction(e -> {
            try {
                if (text.getText().length() > 0) {

                    String strr = text.getText().replaceAll(" +", " ");

                    String[] input = strr.trim().split(" ");
                    gotoDisplay(input);
                }
            } catch (Exception exp) {
                text.clear();
                text.setPromptText("Wrong input!!!");
            }
        });

        VBox menu = new VBox();
        Button menuButton = new Button("<< Main Menu");
        menuButton.setPrefSize(200, 50);
        menu.setPadding(new Insets(310, 10, 10, 750));
        menu.getChildren().add(menuButton);

        menuButton.setOnAction(e -> {
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

        vb.getChildren().addAll(label, stk, warning, menu);
        warning.setFont(Font.font("Arial Rounded MT Bold", 15));
        if (FXMLDocumentController.Heap) {
            warning.setVisible(true);
        } else {
            warning.setVisible(false);
        }
        vb.setPadding(new Insets(10, 10, 10, 10));
        stk.getChildren().addAll(text, proceed);
        stk.setSpacing(5);
        scn = new Scene(vb, 1100, 600);
        scn.getStylesheets().add("Test.css");
        Algorithm_Simulator.window.setScene(scn);
    }

    /**
     * convert string to array of double values and calls the sorting algorithm
     * simulating classes
     *
     * @param str string got from textfield
     */
    public void gotoDisplay(String[] str) {
        /*for (int i = 0; i < str.length; i++) {
         System.out.println(str[i]);
         }*/
        double arr[] = new double[str.length];
        for (int i = 0; i < str.length; i++) {
            arr[i] = Double.parseDouble(str[i]);
        }
        /*for (int i = 0; i < arr.length; i++) {
         System.out.printf(" %f", arr[i]);
         }*/
        System.out.println();

        if (FXMLDocumentController.Bubble || FXMLDocumentController.Insertion || FXMLDocumentController.Selection) {
            NsqrSortSetUp setUp = new NsqrSortSetUp(arr);
            setUp.displaySorting();
        } else if (FXMLDocumentController.Merge) {
            System.out.println("MerGE Somulator called");
            MergeSortSetUp setUp = new MergeSortSetUp(arr);
            setUp.displaySorting();
        } else if (FXMLDocumentController.Quick) {
            NsqrSortSetUp setUp = new NsqrSortSetUp(arr);
            setUp.displaySorting();
        } else if (FXMLDocumentController.Heap) {
            double[] arr1 = new double[arr.length + 1];
            for (int i = arr.length - 1; i >= 0; i--) {
                arr1[i + 1] = arr[i];
            }
            HeapSortSetUp setUp = new HeapSortSetUp(arr1);
            setUp.displaySetUp();
        }
    }
}
