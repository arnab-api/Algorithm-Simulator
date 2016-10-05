/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

/**
 * custom made priority queue for DijkstraNode object, the instance of
 * DijkstraNode for which dist is minimum will appear at the top of the queue
 * and then the second minimum and so on
 *
 * @author Arnab Sen Sharma
 */
public class My_PQ {

    private int sizee;
    DijkstraNode arr[];

    /**
     * assign default values
     */
    My_PQ() {
        sizee = 0;
        arr = new DijkstraNode[101010];
    }

    /**
     * add an DijkstraNode element
     *
     * @param dn the DijkstraNode element to be added
     */
    void push(DijkstraNode dn) {
        arr[sizee++] = dn;
        sortData();
    }

    /**
     * sort the data(ascending order) according to dist value
     */
    private void sortData() {
        for (int i = 0; i < sizee; i++) {
            for (int j = i + 1; j < sizee; j++) {
                if (arr[i].dist > arr[j].dist) {
                    swap(i, j);
                }
            }
        }
    }

    /**
     * swap two DijkstraNode element
     *
     * @param i DijkstraNode element 1
     * @param j DijkstraNode element 2
     */
    private void swap(int i, int j) {

        int tmpNode = arr[i].node;
        double tmpDst = arr[i].dist;

        arr[i].node = arr[j].node;
        arr[i].dist = arr[j].dist;

        arr[j].node = tmpNode;
        arr[j].dist = tmpDst;
    }

    /**
     * get the top DijkstraNode element of the priority queue
     *
     * @return the top DijkstraNode element of the priority queue
     */
    DijkstraNode top() {
        return arr[0];
    }

    /**
     * pop/delete the top DijkstraNode element of the priority queue
     */
    void pop() {
        if (sizee == 0) {
            return;
        }
        sizee--;
        for (int i = 0; i < sizee; i++) {
            arr[i] = arr[i + 1];
        }
    }

    /**
     * get the current size of the priority queue
     *
     * @return current size of the priority queue
     */
    int size() {
        return sizee;
    }

    /**
     * checks weather the priority queue is empty or not
     *
     * @return true if priority queue is empty, false otherwise
     */
    boolean isEmpty() {
        if (sizee == 0) {
            return true;
        }
        return false;
    }
}
