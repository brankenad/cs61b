package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*
 * constructor should take time proportional to N^2.
 * all methods should take constant time plus a constant number of calls to the union-find methods.
 * use the WeightedQuickUnionUF class! Do not reimplement the Union Find ADT.
 */
public class Percolation {
	private boolean[][]  gird;
	private int sidelength;
	private int top; // virtual top node
	private int bottom; // virtual bottom node
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF ufExcludeBottom; // to avoid backwash
	private int numberOfOpenSites;
	private static int[][] surroundings = new int[][]{{0, -1}, {0, 1}, {1, 0}, {-1, 0}};

	// create N-by-N grid, with all sites initially blocked.
	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException();
		}
		sidelength = N;
		numberOfOpenSites = 0;
		gird = new boolean[N][N];
		top = 0;
		bottom = N * N + 1;
		uf = new WeightedQuickUnionUF(N * N + 2);
		ufExcludeBottom = new WeightedQuickUnionUF(N * N + 1);
	}

	// map the position in the gird to uf.
	private int posMapTouf(int row, int col) {
		return row * sidelength + col + 1;
	}

	private void isIndexIllegal(int row, int col) {
		if (row < 0 || row > sidelength - 1 || col < 0 || col > sidelength - 1) {
			throw new IndexOutOfBoundsException();
		}
	}

	// open the site (row, col) if it is not open already.
	public void open(int row, int col) {
		isIndexIllegal(row, col);
		if (isOpen(row, col)) {
			return;
		}
		gird[row][col] = true;
		numberOfOpenSites += 1;
		int mappos = posMapTouf(row, col);

		if (row == 0) {
			uf.union(mappos, top);
			ufExcludeBottom.union(mappos, top);
		}
		if (row == sidelength - 1) {
			uf.union(mappos, bottom);
		}
		for (int[] surrounding : surroundings) {
			int adjrow = row + surrounding[0];
			int adjcol = col + surrounding[1];
			int adjmappos = posMapTouf(adjrow, adjcol);
			if (adjrow >= 0 && adjrow <= sidelength - 1 && adjcol >= 0 && adjcol <= sidelength - 1) {
				if(isOpen(adjrow, adjcol)) {
					uf.union(mappos, adjmappos);
					ufExcludeBottom.union(mappos, adjmappos);
				}
			}
		}
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		isIndexIllegal(row, col);
		return gird[row][col];
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		isIndexIllegal(row, col);
		int mappos = posMapTouf(row, col);
		return ufExcludeBottom.connected(mappos, top);

	}

	// number of open sites
	public int numberOfOpenSites() {
		return numberOfOpenSites;
	}

	// does the system percolate?
	public boolean percolates() {
		return uf.connected(top, bottom);
	}

	// use for unit testing (not required, but keep this here for the autograder).
	public static void main(String[] args) {

	}
}
