package bearmaps;

import java.util.List;

import static bearmaps.Point.distance;

public class KDTree implements PointSet{
    private static class Node {
        private Point point;
        private Node left;
        private Node right;
        private boolean xDimension;

        public Node(Point point) {
            this(point, true);
        }
        public Node(Point point, boolean xDimension) {
            this.point = point;
            this.left = null;
            this.right = null;
            this.xDimension = xDimension;
        }
    }
    private Node root;

    public KDTree (List<Point> points) {
        for (Point point : points) {
            add(point);
        }
    }

    private int comparePoints(Point point1, Point point2, boolean xDimension) {
        if (xDimension) {
            return Double.compare(point1.getX(), point2.getX());
        }
        return Double.compare(point1.getY(), point2.getY());
    }

    private void add (Point point) {
            root = add(root, point,true);

    }
    private Node add(Node node, Point point, boolean xDimension) {
        if (node == null) {
            return new Node(point, xDimension);
        }
        if (point.equals(node.point)) {
            return node;
        }
        int compare = comparePoints(point, node.point, xDimension);
        if (compare < 0) {
            node.left = add(node.left, point, !xDimension);
        } else {
            node.right = add(node.right, point, !xDimension);
        }
        return node;
    }

    private boolean isUseful(Node badnode, Point currentPoint, Point nearestPoint) {
        if (badnode == null) {
            return false;
        }
        double nearestDistance = Point.distance(currentPoint, nearestPoint);
        double badestDistance;
        if (badnode.xDimension) {
            badestDistance = Point.distance(new Point(currentPoint.getX(), badnode.point.getY()), currentPoint);
        } else {
            badestDistance = Point.distance(new Point(badnode.point.getX(), currentPoint.getY()), currentPoint);
        }
        return badestDistance < nearestDistance;
    }

    private Point nearestPointHelp(Node node, Point currentPoint, Point nearestPoint) {
        if (node == null) {
            return nearestPoint;
        }
        double currentDistance = Point.distance(node.point, currentPoint);
        double nearestDistance = Point.distance(currentPoint, nearestPoint);

        if (currentDistance < nearestDistance) {
            nearestPoint = node.point;
        }

        Node goodSide;
        Node badSide;
        if (comparePoints(currentPoint, node.point, node.xDimension) < 0) {
            goodSide = node.left;
            badSide = node.right;
        } else {
            goodSide = node.right;
            badSide = node.left;
        }
        nearestPoint = nearestPointHelp(goodSide, currentPoint, nearestPoint);
        if (isUseful(badSide, currentPoint, nearestPoint)) {
            nearestPoint = nearestPointHelp(badSide, currentPoint, nearestPoint);
        }
        return nearestPoint;
    }

    @Override
    public Point nearest(double x, double y) {
        Point currentPoint = new Point(x, y);
        Point nearestPoint = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
        nearestPoint = nearestPointHelp(root, currentPoint, nearestPoint);
        return nearestPoint;
    }
}
