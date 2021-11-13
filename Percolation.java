/*
        A Programming Assignment for Data Structures and Algorithms completed
        by John Harrington in October, 2020.

        This assignment is being shared in order to demonstrate my work to future
        employers. I do not authorize nor encourage any academic misconduct that may
        result from sharing this content online.

        This assignment was designed by Robert Sedgewick and Kevin Wayne as part of
        their Algorithms Coursera Course partnered with Princeton University.
        This program contains two classes: Percolation and PercolationStats.

        Supporting code for this assignment is part of the Algorithms Fourth Edition
        Library and can be found here: https://algs4.cs.princeton.edu/code/
*/


import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF qf; // this object will connect to source and sink
    private WeightedQuickUnionUF qfNoSink; // this object will not connect to sink thereby eliminating back-flow
    private boolean[] grid;
    public int sizeN;
    private int source;
    private int sink;

    // create N-by-N grid, with all sites initially blocked and disconnected
    public Percolation(int N) {
        try {
            sizeN = N;
            qf = new WeightedQuickUnionUF((N*N) + 2); // N x N grid plus source and sink
            qfNoSink = new WeightedQuickUnionUF((N*N) +1); // N x N grid plus source element
            grid = new boolean[N * N];        // create the N * N grid using 1D array
            source = sizeN * sizeN;   // set source to equal the total elements in array id, since this value is not used to identify any set elements
            sink = (sizeN * sizeN) + 1;  // same as above but +1
        }
        catch (NegativeArraySizeException e) {
            System.out.println("Integer N must be non-negative.");
            System.exit(-1);
        }
    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        try {
            int index = (row * sizeN) + col;  // get 2D coordinates into 1D context
            grid[index] = true;
            int aboveIndex = ((row - 1) * sizeN) + col;
            int belowIndex = ((row + 1) * sizeN) + col;
            int leftIndex = (row * sizeN) + (col - 1);
            int rightIndex = (row * sizeN) + (col + 1);

            // bounds check for uppermost row and exclude any negative indices resulting from above operations
            if (((row != 0)) && (aboveIndex > -1)) {
                if ((grid[index] && (grid[aboveIndex]))) {
                    qf.union(((aboveIndex)), (index));
                    qfNoSink.union(aboveIndex,index);
                }
            }

            // bounds check for lowermost row
            if ((row < (sizeN - 1))) {
                if ((grid[index]) && (grid[belowIndex])) {
                    qf.union(belowIndex, index);
                    qfNoSink.union(belowIndex, index);
                }
            }

            // bounds check for left-most column
            if ((col != 0) && (leftIndex >-1) ){
                if ((grid[index] && (grid[leftIndex]))) {
                    qf.union(leftIndex, index);
                    qfNoSink.union(leftIndex, index);

                }
            }

            // bounds check for right-most column
            if (col != (sizeN - 1)) {
                if ((grid[(row * sizeN) + col]) && (grid[rightIndex])) {
                    qf.union(rightIndex, index);
                    qfNoSink.union(rightIndex, index);
                }
            }

            // union operations with source and sink
            // if cell just opened is in top row, connect it to source element
            if (row == 0) {
                qf.union(index, source);
                qfNoSink.union(index, source);
            }

            // if cell just opened is in bottom row, connect to sink element
            if (row == sizeN - 1) {
                qf.union(index, sink);
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You have attempted to open a site that doesn't exist");
            System.exit(-1);
        }
    }

    // returns true if the element is open
    public boolean isOpen(int row, int col) {
        try {
            int index = (row * sizeN) + col;
            if (grid[index]) {
                return true;
            }

        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You have attempted to check a site that doesn't exist");
            System.exit(-1);
        }
        return false;
    }

    // returns true if the site is full (connected to the source)
    public boolean isFull(int row, int col) { //
        try {
            int index = (row * sizeN) + col;  // get 2D coordinates into 1D context
            if (qfNoSink.find(index) == qfNoSink.find(source)) {
                return true;
            }
        }
         catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You have attempted to check a site that doesn't exist");
            System.exit(-1);
        }
        return false;
    }

    // returns the number of sites which have been eopened
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < sizeN * sizeN; i++) {
            if (grid[i] == true) {
                count++;
            }
        }
            return count;
    }

    // returns true if the system percolates (is connected from source to sink)
    public boolean percolates() {
        if (qf.find(source) == qf.find(sink)) {
            return true;
        }
        else {
            return false;
        }
    }
}
