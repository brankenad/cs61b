package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import javafx.scene.paint.Stop;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    @Test
    public void testDistance() {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 100000; i += 1) {
            Point point = new Point(StdRandom.uniform(-80,100), StdRandom.uniform(-120, 150));
            points.add(point);
        }
        NaivePointSet nn = new NaivePointSet(points);
        KDTree kdt = new KDTree(points);
        for (int i = 0; i < 1000; i += 1) {
            int x = StdRandom.uniform(-50, 200);
            int y = StdRandom.uniform(-150, 200);
            Stopwatch sw1 = new Stopwatch();
            Point nnPoint = nn.nearest(x, y);
            System.out.println("Total time(nn) elapsed: " + sw1.elapsedTime() +  " seconds.");
            Stopwatch sw2 = new Stopwatch();
            Point kdtPoint = kdt.nearest(x, y);
            System.out.println("Total time(kdt) elapsed: " + sw2.elapsedTime() +  " seconds.");
            System.out.println(x + " " + y);
            assertEquals(nnPoint, kdtPoint);
        }
    }
}
