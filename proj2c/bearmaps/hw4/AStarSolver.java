package bearmaps.hw4;


import bearmaps.proj2ab.ArrayHeapMinPQ;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

	private SolverOutcome outcome = SolverOutcome.SOLVED;
	private double solutionWeight = 0.0;
	private List<Vertex> solution;
	private int numStatesExplored = 0;
	private double explorationTime;


	private ArrayHeapMinPQ<Vertex> fringe;
	private Map<Vertex, Double> disTo;
	private Map<Vertex, Vertex> edgeTo;


	public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
		solution = new ArrayList<>();
		disTo = new HashMap<>();
		edgeTo = new HashMap<>();
		fringe = new ArrayHeapMinPQ<>();

		disTo.put(start, 0.0);
		fringe.add(start, 0);

		Vertex v;
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		while (!fringe.getSmallest().equals(end)) {
			v = fringe.removeSmallest();
			numStatesExplored += 1;
			relax(input, v, end);
			endTime = System.currentTimeMillis();

			if (fringe.size() == 0) {
				outcome = SolverOutcome.UNSOLVABLE;
				break;
			} else if ((endTime - startTime) / 1000.0 >= timeout) {
				outcome = SolverOutcome.TIMEOUT;
				break;
			}
		}

		explorationTime = (startTime - endTime) / 1000.0;

		if (outcome == SolverOutcome.SOLVED) {
			solutionWeight = disTo.get(end);
			solution.add(end);
			for (Vertex pathV = end; !pathV.equals(start); pathV = edgeTo.get(pathV)) {
				Vertex from = edgeTo.get(pathV);
				solution.add(0, from);
			}
		}
	}

	private void relax(AStarGraph<Vertex> input, Vertex v, Vertex end) {
		for (WeightedEdge<Vertex> e : input.neighbors(v)) {
			Vertex w = e.to();
			if (!disTo.containsKey(w) || disTo.get(w) > disTo.get(v) + e.weight()) {
				disTo.put(w, disTo.get(v) + e.weight());
				edgeTo.put(w, e.from());
				changePQ(input, w, end);
			}
		}
	}

	private void changePQ(AStarGraph<Vertex> input, Vertex v, Vertex end) {
		if (!fringe.contains(v)) {
			fringe.add(v, disTo.get(v) + input.estimatedDistanceToGoal(v, end));
			return;
		}
		fringe.changePriority(v, disTo.get(v) + input.estimatedDistanceToGoal(v, end));
	}

	@Override
	public SolverOutcome outcome() {
		return outcome;
	}
    
	@Override
    public List<Vertex> solution() {
    	return solution;
    }
    
    @Override
    public double solutionWeight() {
    	return solutionWeight;
    }
    
	@Override
    public int numStatesExplored() {
    	return numStatesExplored;
    }
    
	@Override
    public double explorationTime() {
    	return explorationTime;
    }
}