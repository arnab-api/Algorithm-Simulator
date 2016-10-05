/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_simulatorV2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * communicate with the FXMLDocument.fxml
 *
 * @author Arnab Sen Sharma
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    static boolean Bubble = false;
    static boolean Selection = false;
    static boolean Insertion = false;
    static boolean Merge = false;
    static boolean Quick = false;
    static boolean Heap = false;
    static boolean BFS = false;
    static boolean DFS = false;
    static boolean TopSort = false;
    static boolean SCC_koja = false;
    static boolean SCC_tar = false;
    static boolean KnapSack = false;
    static boolean LCS = false;
    static boolean editDis = false;
    static boolean MST_krus = false;
    static boolean Dijkstra = false;

    /**
     * not used
     *
     * @param url not used
     * @param rb not used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * call the SortingInput class to get input and set Bubble = true
     */
    public void BubbleButtonClicked() {
        cleanButtonInput();
        Bubble = true;
        System.out.println("Bubble Button Clicked");
        SortingInput si = new SortingInput("Bubble Sort Simulator");
        si.getInput();
    }

    /**
     * call the SortingInput class to get input and set Secection = true
     */
    public void SelectionButtonClicked() {
        cleanButtonInput();
        Selection = true;
        System.out.println("Selection Button Clicked");
        SortingInput si = new SortingInput("Selection Sort Simulator");
        si.getInput();
    }

    /**
     * call the SortingInput class to get input and set Insertion = true
     */
    public void InsetrionButtonClicked() {
        cleanButtonInput();
        Insertion = true;
        System.out.println("Insertion Button Clicked");
        SortingInput si = new SortingInput("Insertion Sort Simulator");
        si.getInput();
    }

    /**
     * call the SortingInput class to get input and set Merge = true
     */
    public void MergeButtonClicked() {
        cleanButtonInput();
        Merge = true;
        System.out.println("Merge Button Clicked");
        SortingInput si = new SortingInput("Merge Sort Simulator");
        si.getInput();
    }

    /**
     * call the SortingInput class to get input and set Quick = true
     */
    public void QuickButtonClicked() {
        cleanButtonInput();
        Quick = true;
        System.out.println("Quick Button Clicked");
        SortingInput si = new SortingInput("Quick Sort Simulator");
        si.getInput();
    }

    /**
     * call the SortingInput class to get input and set Heap = true
     */
    public void HeapButtonClicked() {
        cleanButtonInput();
        Heap = true;
        System.out.println("Heap Button Clicked");
        SortingInput si = new SortingInput("Haep Sort Simulator");
        si.getInput();
    }

    /**
     * call BFSSetUp to set up the window for BFS simulation
     */
    public void BFSButtonClicked() {
        cleanButtonInput();
        BFS = true;
        BFSSetUp setUp = new BFSSetUp();
    }

    /**
     * call DFSSetUp to set up the window for DFS simulation
     */
    public void DFSButtonClicked() {
        cleanButtonInput();
        DFS = true;
        DFSSetUp setUp = new DFSSetUp();
    }

    /**
     * call TopSortSetUp to set up the window for TopSort simulation
     */
    public void TopSortButtonClicked() {
        cleanButtonInput();
        TopSort = true;
        TopSortSetUp setUp = new TopSortSetUp();
    }

    /**
     * call SCC_kojarasuSetUp to set up the window for BFS simulation
     */
    public void kojarasuSCCclicked() {
        cleanButtonInput();
        SCC_koja = true;
        SCC_KojarasuSetUp setUp = new SCC_KojarasuSetUp();
    }

    /**
     * call DFSSetUp to set up the window for Tarzan SCC simulation
     */
    public void TarjanSCCclicked() {
        cleanButtonInput();
        SCC_tar = true;
        DFSSetUp setUp = new DFSSetUp();
    }

    /**
     * call BottomUpKnapSackSetUp to set up the window for BottomUp KnapSack
     * simulation
     */
    public void KnapSackclicked() {
        cleanButtonInput();
        KnapSack = true;
        BottomUpKnapSackSetUp setUp = new BottomUpKnapSackSetUp();
    }

    /**
     * call TwoWordInput to get two words for LCS simulation
     */
    public void LCSclicked() {
        cleanButtonInput();
        LCS = true;
        TwoWordInput setUp = new TwoWordInput();
    }

    /**
     * call TwoWordInput to get two words for Edit Distance simulation
     */
    public void editDisclicked() {
        cleanButtonInput();
        editDis = true;
        TwoWordInput setUp = new TwoWordInput();
    }

    /**
     * call MST_KruskalSetUp to set up the window for MST_Kruskal simulation
     */
    public void MST_Kruskalclicked() {
        cleanButtonInput();
        MST_krus = true;
        MST_KruskalSetUp setUp = new MST_KruskalSetUp();
    }

    /**
     * call DijkstraSetUp to set up the window for Dijkstra simulation
     */
    public void DijkstraClicked() {
        cleanButtonInput();
        Dijkstra = true;
        DijkstraSetUp setUp = new DijkstraSetUp();
    }

    /**
     * set all the selected algorithm to false
     */
    private void cleanButtonInput() {
        Bubble = false;
        Selection = false;
        Insertion = false;
        Merge = false;
        Quick = false;
        Heap = false;
        BFS = false;
        DFS = false;
        TopSort = false;
        SCC_koja = false;
        SCC_tar = false;
        KnapSack = false;
        LCS = false;
        editDis = false;
        MST_krus = false;
        Dijkstra = false;
    }
}
