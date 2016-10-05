/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * save MergeSort state
 *
 * @author Arnab Sen Sharma
 */
public class SaveMergeSortState {

    Rectangle[] bar, dupliBar;
    Text[] val, dupliVal;
    int cnt;
    Text instruction = new Text();

    /**
     * import the components whose information is to be saved
     *
     * @param r1 rectangle representing the values of the array
     * @param t1 text showing the values of the array
     * @param r2 rectangle representing the values of the duplicate arraydup
     * @param t2 text showing the values of the duplicate array
     * @param ins instruction
     */
    public SaveMergeSortState(Rectangle[] r1, Text[] t1, Rectangle[] r2, Text[] t2, Text ins) {
        cnt = r1.length;
        bar = new Rectangle[cnt];
        dupliBar = new Rectangle[cnt];
        val = new Text[cnt];
        dupliVal = new Text[cnt];

        for (int i = 0; i < cnt; i++) {
            bar[i] = new Rectangle();
            bar[i].setHeight(r1[i].getHeight());
            bar[i].setWidth(r1[i].getWidth());
            bar[i].setFill(r1[i].getFill());

            val[i] = new Text();
            val[i].setText(t1[i].getText());

            dupliBar[i] = new Rectangle();
            dupliBar[i].setHeight(r2[i].getHeight());
            dupliBar[i].setWidth(r2[i].getWidth());
            dupliBar[i].setFill(r2[i].getFill());

            dupliVal[i] = new Text();
            dupliVal[i].setText(t2[i].getText());
        }

        instruction.setText(ins.getText());
    }
}
