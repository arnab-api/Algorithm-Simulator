/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

/**
 * make a pair of two integers
 *
 * @author Arnab Sen Sharma
 */
public class Info {

    int a, b;

    /**
     * set default values to -1
     */
    Info() {
        a = -1;
        b = -1;
    }

    /**
     * import information and build the object
     *
     * @param x integer 1
     * @param y integer 2
     */
    Info(int x, int y) {
        a = x;
        b = y;
    }
}
