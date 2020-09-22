package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point currentPoint = new Point(x, y);
        Point nearestPoint = new Point(x, y);
        double smallestDistance = Double.MAX_VALUE;
        double distance;
        for(Point point : points) {
            distance = Point.distance(currentPoint, point);
            if (distance < smallestDistance) {
                smallestDistance = distance;
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }
}
