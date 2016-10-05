/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

/**
 * save the current minimum distance of a node from source node in DIjkstra
 * algorithm
 *
 * @author Arnab Sen Sharma
 */
public class DijkstraNode {

    int node;
    double dist;

    /**
     * import necessary information and build the class
     *
     * @param node node witch information is to be saved
     * @param dist current minimum distance of the node from source node
     */
    DijkstraNode(int node, double dist) {
        this.node = node;
        this.dist = dist;
    }
}
