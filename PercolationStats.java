/*
        A Programming Assignment for Data Structures and Algorithms completed
        by John Harrington in October, 2020.

        This assignment is being shared in order to demonstrate my work to future
        employers. I do not authorize nor encourage any academic misconduct that may
        result from sharing this content online.

        This assignment was designed by Robert Sedgewick and Kevin Wayne as part of
        their Algorithms Coursera Course partnered with Princeton University.
        This program contains two classes: Percolation and PercolationStats.

        Percolation initializes a Percolation object to a user defined two dimensional
        size (i.e. 10 x 10). Initially, all sites within this grid will be closed and
        disconnected. Percolation contains methods to open sites and connect them to both
        a source and sink element. If a path of open and connected (connections occur on
        long edges of sites only, not corners) exists from the source element to the sink
        element, then this object Percolates.

        PercolationStats contains methods to run a MonteCarlo simulation useful for determining
        the probability distribution at which Percolation objects will begin to percolate.
        This is done by opening sites at random until the system percolates. The user can define
        the desired size of the Percolation object and the number of independent trials to run.

        Supporting code for this assignment is part of the Algorithms Fourth Edition
        Library and can be found here: https://algs4.cs.princeton.edu/code/
*/
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Random;


public class PercolationStats {
    private Random random;        // Used to open sites at random
    private Percolation p;       // Instance of Percolation object
    private double[] threshold;  // Array containing the proportion of open sites at which point object percolates for each trial
    private int numTrials;      // Number of trials to conduct

    public PercolationStats(int N, int T) {
        random = new Random();
        threshold = new double[T];
        numTrials = T;

        for (int i = 0; i < T; i++) { // do the following for each independent trial:
            p = new Percolation(N);   // create new Percolation object
            while (!p.percolates()) {   // check if it percolates, if it doesn't:
                int row = getRandom();  // get random row
                int col = getRandom();  // get random column
                if (!p.isOpen(row, col)) { //  has this site already been opened? If it has, disregard it
                    p.open(row, col);     // otherwise, open it
                }
            }
            threshold[i] = (double) p.numberOfOpenSites() / (double) (N * N);  // record proportion of sites opened when percolation succeeded
        }
    }

    public int getRandom() {
        return random.nextInt(p.sizeN);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLow() {
        return (mean()-(1.96 * stddev()) / Math.sqrt(numTrials));
    }

    // high of 95% confidence interval
    public double confidenceHigh() {
        return (mean()+ (1.96 * stddev()) / Math.sqrt(numTrials));  // formula from Travis
    }

    public static void main(String[] args) {
        Stopwatch sw = new Stopwatch();
        PercolationStats st = new PercolationStats(1000,100);
        System.out.println("Mean: "+st.mean());
        System.out.println("Standard Deviation: "+st.stddev());
        System.out.println("Low end of CI: "+st.confidenceLow());
        System.out.println("High end of CI: "+st.confidenceHigh());
        System.out.println(sw.elapsedTime());
    }
}
