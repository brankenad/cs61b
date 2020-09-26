package bearmaps.proj2c;

import bearmaps.proj2ab.KDTree;
import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.proj2ab.Point;
import bearmaps.lab9.TrieSet61B;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private Map<Point, Node> pointToNode;
    private List<Point> points;
    private TrieSet61B names;
    private Map<String, ArrayList<Node>> nameToNodes;


    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        pointToNode = new HashMap<>();
        points = new ArrayList<>();
        names = new MyTrieSet();
        nameToNodes = new HashMap<>();

        for (Node n : nodes) {
            if (neighbors(n.id()).size() > 0) {
                Point point = new Point(n.lon(), n.lat());
                pointToNode.put(point, n);
                points.add(point);
            }

            if (n.name() != null) {
                String cleanName = cleanString(n.name());
                names.add(cleanName);
                if (!nameToNodes.containsKey(cleanName)) {
                    nameToNodes.put(cleanName, new ArrayList<>());
                }
                nameToNodes.get(cleanName).add(n);
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        KDTree kdt = new KDTree(points);
        Point nearestPoint = kdt.nearest(lon, lat);
        return pointToNode.get(nearestPoint).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        return names.keysWithPrefix(prefix);
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String clearName = cleanString(locationName);
        List<Node> matchNodes = nameToNodes.get(clearName);

        if (matchNodes == null) {
            return null;
        }

        List<Map<String, Object>> returnValues = new ArrayList<>();
        for (Node n : matchNodes) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("lon", n.lon());
            temp.put("lat", n.lat());
            temp.put("name", n.name());
            temp.put("id", n.id());
            returnValues.add(temp);
        }
        return returnValues;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
