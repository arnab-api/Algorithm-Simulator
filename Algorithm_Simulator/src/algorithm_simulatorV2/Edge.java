/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

/**
 * save information of a weighted edge
 *
 * @author Arnab Sen Sharma
 */
public class Edge {

    int u, v;
    double w;
    boolean undirected = true;

    /**
     * import necessary information and build the class
     *
     * @param u node 1
     * @param v node 2
     * @param w weight
     */
    Edge(int u, int v, double w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    /**
     * import necessary information and build the class
     *
     * @param u node 1
     * @param v node 2
     * @param w weight
     * @param type indicates weather the edge is directed(false) or
     * undirected(true)
     */
    Edge(int u, int v, double w, boolean type) {
        this.u = u;
        this.v = v;
        this.w = w;
        undirected = type;
    }
}
