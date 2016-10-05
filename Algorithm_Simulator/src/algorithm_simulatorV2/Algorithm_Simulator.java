/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Launch the application and set up the main menu page
 *
 * @author Arnab Sen Sharma
 */
public class Algorithm_Simulator extends Application {

    static Stage window;
    static long timeGap = 0;
    static Thread thread;
    static boolean threadStarted = false;

    /**
     * get FXMLDocument to set up main menu and launches the application
     *
     * @param stage window of the application
     * @throws Exception debugging error
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        window = stage;
        window.setScene(scene);
        window.setTitle("Algorithm Simulator V:1.0");
        window.show();
    }

    /**
     * @param args the command line arguments :: NOT USED
     */
    public static void main(String[] args) {
        launch(args);
    }

}
