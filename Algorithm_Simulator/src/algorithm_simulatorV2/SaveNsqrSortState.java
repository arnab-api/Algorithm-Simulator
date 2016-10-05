/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * save state O(n^2) sorting system (bubble, insertion, selection) and QuickSort
 * simulation
 *
 * @author Arnab Sen Sharma
 */
public class SaveNsqrSortState {

    Rectangle bar[];
    Text val[];
    int cnt;
    Text instruction;

    /**
     * import the components whose information is to be saved
     *
     * @param passBar rectangle representing the values of the array
     * @param passVal Text showing the values of the array
     */
    public SaveNsqrSortState(Rectangle[] passBar, Text[] passVal) {
        cnt = passBar.length;
        bar = new Rectangle[cnt];
        val = new Text[cnt];
        for (int i = 0; i < cnt; i++) {
            bar[i] = new Rectangle();
            bar[i].setHeight(passBar[i].getHeight());
            bar[i].setWidth(passBar[i].getWidth());
            bar[i].setFill(passBar[i].getFill());

            val[i] = new Text();
            val[i].setText(passVal[i].getText());
        }
    }

    /**
     * import the components whose information is to be saved
     *
     * @param passBar rectangle representing the values of the array
     * @param passVal Text showing the values of the array
     * @param ins instruction
     */
    public SaveNsqrSortState(Rectangle[] passBar, Text[] passVal, Text ins) {
        this(passBar, passVal);
        instruction = new Text();
        System.out.println(ins.getText());
        instruction.setText(ins.getText());
    }
}
