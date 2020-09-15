package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;


public class PercolationStats {
	private double[] prob;

	// perform T independent experiments on an N-by-N grid.
	public PercolationStats(int N, int T, PercolationFactory pf) {
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException();
		}
		prob = new double[T];
		for (int i = 0; i < T; i += 1) {
			Percolation pF = pf.make(N);
			int num = 0;
			while (!pF.percolates()) {
				pF.open(StdRandom.uniform(0, N ), StdRandom.uniform(0, N ));
				num = num + 1;
			} 
			prob[i] = (double)num / (double)N * N;
		}
	}

	// sample mean of percolation threshold.
	public double mean() {
		double sum = 0.0;
		for (int i = 0; i < prob.length; i += 1) {
			sum += prob[i];
		}
		return sum / prob.length;
	}

	// sample standard deviation of percolation threshold.
	public double stddev() {
		return StdStats.stddev(prob);
	}

	// low endpoint of 95% confidence interval.
	public double confidenceLow() {
		return mean() - (1.96 * Math.sqrt(stddev()) / Math.sqrt(prob.length));
	}

	// high endpoint of 95% confidence interval.
	public double confidenceHigh() {
		return mean() + (1.96 * Math.sqrt(stddev()) / Math.sqrt(prob.length));
	}	
}
