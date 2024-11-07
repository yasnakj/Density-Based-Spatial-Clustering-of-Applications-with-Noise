/**
 * Yasna Karimi
 * 300312772
 */

import java.util.ArrayList;
import java.util.List;

public class NearestNeighbors {
    private List<Point3D> points;

    // Constructor to initialize with a list of points
    public NearestNeighbors(List<Point3D> points) {
        this.points = points;
    }

    // Method to find neighbors within eps distance
    public List<Point3D> rangeQuery(double eps, Point3D P) {
        List<Point3D> neighbors = new ArrayList<>();
        for (Point3D point : points) {
            if (P.distance(point) <= eps) {
                neighbors.add(point);
            }
        }
        return neighbors;
    }
}
