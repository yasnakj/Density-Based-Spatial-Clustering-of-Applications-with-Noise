/**
 * Yasna Karimi
 * 300312772
 */

import java.io.*;
import java.util.*;

public class DBScan {
    private List<Point3D> points;
    private double eps;
    private int minPts;

    // Constructor to initialize with a list of points
    public DBScan(List<Point3D> points) {
        this.points = points;
    }

    // Setter methods for eps and minPts
    public void setEps(double eps) { this.eps = eps; }
    public void setMinPts(int minPts) { this.minPts = minPts; }

    // Method to execute the DBScan algorithm
    public void findClusters() {
        int C = 0;
        NearestNeighbors nn = new NearestNeighbors(points);
        for (Point3D P : points) {
            if (P.getCluster() != -1) continue; // Skip already processed points
            List<Point3D> N = nn.rangeQuery(eps, P);
            if (N.size() < minPts) {
                P.setCluster(0); // Mark as noise
                continue;
            }
            C++; // Next cluster label
            P.setCluster(C);
            Stack<Point3D> S = new Stack<>();
            S.push(P);
            while (!S.isEmpty()) {
                Point3D Q = S.pop();
                List<Point3D> NQ = nn.rangeQuery(eps, Q);
                if (NQ.size() >= minPts) {
                    for (Point3D R : NQ) {
                        if (R.getCluster() == 0) R.setCluster(C); // Noise becomes border point
                        if (R.getCluster() == -1) {
                            R.setCluster(C);
                            S.push(R);
                        }
                    }
                }
            }
        }
    }

    // Method to get the number of clusters
    public int getNumberOfClusters() {
        Set<Integer> clusters = new HashSet<>();
        for (Point3D p : points) {
            if (p.getCluster() != 0 && p.getCluster() != -1) {
                clusters.add(p.getCluster());
            }
        }
        return clusters.size();
    }

    // Method to get the list of points
    public List<Point3D> getPoints() {
        return points;
    }

    // Static method to read points from a CSV file
    public static List<Point3D> read(String filename) throws IOException {
        List<Point3D> points = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        
        // Skip the header line
        br.readLine();
        
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            points.add(new Point3D(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2])));
        }
        br.close();
        return points;
    }

    // Method to save points with clusters to a CSV file
    public void save(String filename) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        for (Point3D p : points) {
            bw.write(p.toString());
            bw.newLine();
        }
        bw.close();
    }

    // Main method to run the DBScan algorithm
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: java DBScan <filename> <eps> <minPts>");
            return;
        }
        String filename = args[0];
        double eps = Double.parseDouble(args[1]);
        int minPts = Integer.parseInt(args[2]);

        // Read points from the file
        List<Point3D> points = DBScan.read(filename);
        DBScan dbscan = new DBScan(points);
        dbscan.setEps(eps);
        dbscan.setMinPts(minPts);
        dbscan.findClusters();

        // Assign colors to clusters
        Map<Integer, double[]> clusterColors = new HashMap<>();
        Random rand = new Random();
        for (Point3D p : dbscan.getPoints()) {
            int cluster = p.getCluster();
            if (!clusterColors.containsKey(cluster)) {
                clusterColors.put(cluster, new double[]{rand.nextDouble(), rand.nextDouble(), rand.nextDouble()});
            }
            double[] color = clusterColors.get(cluster);
            p.setRGB(color[0], color[1], color[2]);
        }

        // Save the results to a new file
        int numClusters = dbscan.getNumberOfClusters();
        String outputFilename = filename + "_clusters_" + eps + "_" + minPts + "_" + numClusters + ".csv";
        dbscan.save(outputFilename);
        System.out.println("Clusters: " + numClusters);
        System.out.println("Noise: " + points.stream().filter(p -> p.getCluster() == 0).count());
    }
}