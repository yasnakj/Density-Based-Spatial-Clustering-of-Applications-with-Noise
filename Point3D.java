/**
 * Yasna Karimi
 * 300312772
 */

 public class Point3D {
    private double x, y, z;
    private int cluster;
    private double r, g, b;

    // Constructor to initialize point coordinates
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.cluster = -1; // Undefined cluster
        this.r = this.g = this.b = 0.0; // Default RGB color
    }

    // Getter methods for coordinates
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    // Getter and setter methods for cluster
    public int getCluster() { return cluster; }
    public void setCluster(int cluster) { this.cluster = cluster; }

    // Method to set RGB color
    public void setRGB(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    // Override toString method to format output
    public String toString() {
        return x + "," + y + "," + z + "," + cluster + "," + r + "," + g + "," + b;
    }

    // Method to calculate Euclidean distance between two points
    public double distance(Point3D pt) {
        return Math.sqrt(Math.pow(x - pt.getX(), 2) + Math.pow(y - pt.getY(), 2) + Math.pow(z - pt.getZ(), 2));
    }
}
