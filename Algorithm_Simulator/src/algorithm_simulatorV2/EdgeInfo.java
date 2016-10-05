/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * show information of an edge
 *
 * @author Arnab Sen Sharma
 */
public class EdgeInfo {

    Text from, to, weight, type;
    Text f_in, to_in, w_in, t_in;
    HBox setUp = new HBox();

    /**
     * build a panel to show information of an edge
     */
    EdgeInfo() {
        setUp.setPrefSize(800, 100);
        f_in = new Text("From : ");
        to_in = new Text("To : ");
        w_in = new Text("Weight : ");
        t_in = new Text("Type : ");

        from = new Text("-f");
        to = new Text("-t");
        weight = new Text("-w");
        type = new Text("-T");

        VBox des = new VBox();
        VBox weight_panel = new VBox();

        HBox from_panel = new HBox();
        from_panel.getChildren().addAll(f_in, from);
        HBox to_panel = new HBox();
        to_panel.getChildren().addAll(to_in, to);
        HBox type_panel = new HBox();
        type_panel.getChildren().addAll(t_in, type);
        des.getChildren().addAll(from_panel, to_panel);
        des.setPrefSize(400, 100);

        HBox info = new HBox();
        info.getChildren().addAll(w_in, weight);
        weight_panel.setPrefSize(400, 100);
        weight_panel.getChildren().addAll(type_panel, info);

        setUp.getChildren().addAll(des, weight_panel);
        des.setSpacing(5);
        weight_panel.setSpacing(5);

        from.setFont(Font.font("Arial Rounded MT Bold", 25));
        to.setFont(Font.font("Arial Rounded MT Bold", 25));
        type.setFont(Font.font("Arial Rounded MT Bold", 25));
        weight.setFont(Font.font("Arial Rounded MT Bold", 25));
        f_in.setFont(Font.font("Arial Rounded MT Bold", 25));
        to_in.setFont(Font.font("Arial Rounded MT Bold", 25));
        w_in.setFont(Font.font("Arial Rounded MT Bold", 25));
        t_in.setFont(Font.font("Arial Rounded MT Bold", 25));
    }
}
