/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * save bottom up DP state
 *
 * @author Arnab Sen Sharma
 */
public class SaveDPstate {

    Rectangle[][] rec;
    Text[][] data;
    int row, col;
    Text text;
    boolean visibility = false;
    Rectangle[] rec_arr1, rec_arr2;

    /**
     * import the components to be saved
     *
     * @param passrec rectangles representing the table
     * @param passdata data of the table
     * @param row number of rows
     * @param col number of columns
     * @param passText instruction
     */
    public SaveDPstate(Rectangle[][] passrec, Text[][] passdata, int row, int col, Text passText) {
        rec = new Rectangle[row][col];
        data = new Text[row][col];
        this.row = row;
        this.col = col;
        text = new Text();
        text.setText(passText.getText());
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                rec[i][j] = new Rectangle();
                rec[i][j].setWidth(passrec[i][j].getWidth());
                rec[i][j].setHeight(passrec[i][j].getHeight());
                rec[i][j].setFill(passrec[i][j].getFill());

                data[i][j] = new Text();
                data[i][j].setText(passdata[i][j].getText());
                data[i][j].setStroke(passdata[i][j].getStroke());
            }
        }
    }

    /**
     * import the components to be saved
     *
     * @param passrec rectangles representing the table
     * @param passdata data of the table
     * @param row number of rows
     * @param col number of columns
     * @param passText instruction
     * @param visibility visibility of instruction
     */
    public SaveDPstate(Rectangle[][] passrec, Text[][] passdata, int row, int col, Text passText, boolean visibility) {
        this(passrec, passdata, row, col, passText);
        this.visibility = visibility;
    }

    /**
     * import the components to be saved
     *
     * @param passrec rectangles representing the table
     * @param passdata data of the table
     * @param row number of rows
     * @param col number of columns
     * @param passText instruction
     * @param visibility visibility of instruction
     * @param rr1 rectangle array showing weight of items(Knapsack)
     * @param rr2 rectangle array showing price of items(Knapsack)
     */
    public SaveDPstate(Rectangle[][] passrec, Text[][] passdata, int row, int col, Text passText, boolean visibility, Rectangle[] rr1, Rectangle[] rr2) {
        this(passrec, passdata, row, col, passText, visibility);
        rec_arr1 = new Rectangle[row];
        for (int i = 0; i < row; i++) {
            rec_arr1[i] = new Rectangle();
            rec_arr1[i].setFill(rr1[i].getFill());
        }
        rec_arr2 = new Rectangle[col];
        for (int i = 0; i < col; i++) {
            rec_arr2[i] = new Rectangle();
            rec_arr2[i].setFill(rr2[i].getFill());
        }
    }
}
