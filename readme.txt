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
