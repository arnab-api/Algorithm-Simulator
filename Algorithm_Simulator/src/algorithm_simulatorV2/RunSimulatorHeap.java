/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * Simulates the Heap Sorting process
 *
 * @author Arnab Sen Sharma
 */
public class RunSimulatorHeap {

    double[] arr;
    Circle[] circle;
    Line[] edge;
    int heapsize;
    Text[] text;
    boolean maxheap = false;
    static Object lock = new Object();
    static boolean flag = false;
    SaveHeapState[] state;
    boolean lineVisibility[];
    Text instruction = new Text();

    /**
     * imports necessary information to run the process
     *
     * @param arr array of numbers to be sorted
     * @param circle representing the nodes of the Heap tree
     * @param line representing the edges of the Heap tree
     * @param text used to write the numbers on the circle
     * @param state array to store each step of the Heap Sort process
     */
    RunSimulatorHeap(double[] arr, Circle[] circle, Line[] line, Text[] text, SaveHeapState[] state) {
        this.arr = arr;
        this.circle = circle;
        this.edge = line;
        this.text = text;
        this.state = state;
        heapsize = arr.length - 1;
        lineVisibility = new boolean[17];
        for (int i = 0; i < 17; i++) {
            lineVisibility[i] = true;
        }
        run();
    }

    /**
     * calls the Heap Sorting process
     */
    public void run() {
        saveState();
        Algorithm_Simulator.threadStarted = true;
        for (int i = 1; i < arr.length; i++) {
            System.out.printf("%.1f ", arr[i]);
        }
        System.out.println("--->");
        Heap_sort(heapsize);
        for (int i = 1; i < arr.length; i++) {
            System.out.printf("%.1f ", arr[i]);
        }
        System.out.println("--->");
    }

    /**
     * builds the max heap subtree for current index its children
     *
     * @param index current processing node
     */
    void Max_heapify(int index) {
        circle[index].setRadius(30);
        circle[index].setFill(Color.BLUEVIOLET);
        //threadDelay(500);
        int left = index * 2;
        int right = index * 2 + 1;
        int largest = index;

        if (left <= heapsize) {
            circle[left].setFill(Color.ORANGE);
        }
        if (right <= heapsize) {
            circle[right].setFill(Color.ORANGE);
        }
        if (flag) {
            halt();
        }
        instruction.setText(String.format("Current procesing node %d value %.1f", index, arr[index]));
        threadDelay();
        if (flag) {
            halt();
        }
        if (left <= heapsize) {
            if (arr[left] > arr[largest]) {
                instruction.setText(String.format("left(%d) = %.1f > %.1f.  Current largest = %.1f", left, arr[left], arr[largest], arr[left]));
                largest = left;
                circle[left].setRadius(30);
                circle[left].setFill(Color.RED);
                if (flag) {
                    halt();
                }
                threadDelay();
                if (flag) {
                    halt();
                }
            } else {
                instruction.setText(String.format("left(%d) = %.1f <= %.1f >> OK", left, arr[left], arr[largest]));
                if (!maxheap) {
                    circle[left].setRadius(20);
                    circle[left].setFill(Color.CYAN);
                } else {
                    circle[left].setRadius(20);
                    circle[left].setFill(Color.GREENYELLOW);
                }
                if (flag) {
                    halt();
                }
                threadDelay();
                if (flag) {
                    halt();
                }
            }
        }
        if (right <= heapsize) {
            if (arr[right] > arr[largest]) {
                instruction.setText(String.format("Right(%d) = %.1f > %.1f.  Current largest = %.1f", right, arr[right], arr[largest], arr[right]));
                largest = right;
                circle[right].setRadius(30);
                circle[right].setFill(Color.RED);
                if (!maxheap) {
                    circle[left].setRadius(20);
                    circle[left].setFill(Color.CYAN);
                } else {
                    circle[left].setRadius(20);
                    circle[left].setFill(Color.GREENYELLOW);
                }
                if (flag) {
                    halt();
                }
                threadDelay();
                if (flag) {
                    halt();
                }
            } else {
                instruction.setText(String.format("right(%d) = %.1f <= %.1f >> OK", right, arr[right], arr[largest]));
                if (!maxheap) {
                    circle[right].setRadius(20);
                    circle[right].setFill(Color.CYAN);
                } else {
                    circle[right].setRadius(20);
                    circle[right].setFill(Color.GREENYELLOW);
                }
                if (flag) {
                    halt();
                }
                threadDelay();
                if (flag) {
                    halt();
                }
            }
        }
        if (largest != index) {
            instruction.setText(String.format("swap(%.1f,%.1f)", arr[index], arr[largest]));
            swap(index, largest);
            if (!maxheap) {
                circle[index].setRadius(20);
                circle[index].setFill(Color.CYAN);
            } else {
                circle[index].setRadius(20);
                circle[index].setFill(Color.GREENYELLOW);
            }
            circle[largest].setFill(Color.BLUEVIOLET);
            if (flag) {
                halt();
            }
            threadDelay();
            if (flag) {
                halt();
            }
            Max_heapify(largest);
        } else {
            circle[index].setRadius(20);
            circle[index].setFill(Color.GREENYELLOW);
            if (flag) {
                halt();
            }
            threadDelay();
            if (flag) {
                halt();
            }
        }
    }

    /**
     * builds max heap
     *
     * @param n current heap size
     */
    void Build_heap(int n) {
        for (int i = n / 2; i >= 1; i--) {
            Max_heapify(i);
        }
        maxheap = true;
    }

    /**
     * runs the heap sorting process
     *
     * @param n
     */
    void Heap_sort(int n) {
        int i;
        heapsize = n;
        Build_heap(n);
        for (i = 1; i <= heapsize; i++) {
            circle[i].setFill(Color.GREENYELLOW);
        }
        if (flag) {
            halt();
        }
        instruction.setText("Max Heap tree Built!!!");
        threadDelay();
        if (flag) {
            halt();
        }
        for (i = n; i > 1; i--) {
            circle[1].setRadius(30);
            circle[1].setFill(Color.FUCHSIA);
            circle[heapsize].setRadius(30);
            circle[heapsize].setFill(Color.FUCHSIA);
            if (flag) {
                halt();
            }
            instruction.setText(String.format("swap(%d,%d)", 1, i));

            threadDelay();
            if (flag) {
                halt();
            }
            swap(heapsize, 1);
            circle[1].setRadius(20);
            circle[1].setFill(Color.GREENYELLOW);
            circle[heapsize].setRadius(20);
            circle[heapsize].setFill(Color.GREEN);
            heapsize--;
            if (heapsize > 0) {
                edge[heapsize].setVisible(false);
                lineVisibility[heapsize] = false;
            }
            if (flag) {
                halt();
            }
            threadDelay();
            if (flag) {
                halt();
            }
            Max_heapify(1);
        }
        circle[1].setFill(Color.GREEN);
        if (flag) {
            halt();
        }

        instruction.setText("Process Finished!!!!");

        threadDelay();
        if (flag) {
            halt();
        }
    }

    /**
     * swaps two nodes
     *
     * @param x node one
     * @param y node two
     */
    void swap(int x, int y) {
        double temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;

        text[x].setText(String.format("%.1f", arr[x]));
        text[y].setText(String.format("%.1f", arr[y]));
    }

    /**
     * saves the state and delays the process a certain time
     */
    void threadDelay() {
        saveState();
        try {
            Thread.sleep(Algorithm_Simulator.timeGap);
        } catch (InterruptedException ex) {
            Logger.getLogger(RunSimulatorHeap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * halts the process
     */
    void halt() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(RunSimulatorHeap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * pause the process
     */
    static void pause() {
        flag = true;
    }

    /**
     * resume the process
     */
    static void resume() {
        synchronized (lock) {
            lock.notify();
            flag = false;
        }
    }

    /**
     * saves current state of the process
     */
    void saveState() {
        //for(int i=1;i<=arr.length;i++) System.out.print(lineVisibility[i]+" ");
        //System.out.println("");
        state[HeapSortSetUp.cntState++] = new SaveHeapState(circle, edge, text, lineVisibility, instruction);
    }
}
