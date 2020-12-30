import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
/**
 * Your implementation of various different graph algorithms.
 *
 * @author Rahul
 * @version 1.0
 * @userid nrahul3 (i.e. gburdell3)
 * @GTID 903533392 (i.e. 900000000)
 *
 * Collaborators: I did this assignment alone.
 *
 * Resources: I used the course modules to do this assignment;
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("null arguments are not allowed");
        }
        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> visited = new ArrayList<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> curr = queue.remove();
            List<VertexDistance<T>> adj = graph.getAdjList().get(curr);
            if (adj == null) {
                throw new IllegalArgumentException("Adjacent list is null");
            }
            for (VertexDistance<T> ab : adj) {
                if (!visited.contains(ab.getVertex())) {
                    visited.add(ab.getVertex());
                    queue.add(ab.getVertex());
                }
            }
        }
        return visited;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("null arguments are not allowed");
        }
        List<Vertex<T>> out = new ArrayList<>();
        dfsHel(start, graph, out);
        return out;
    }
    /**
     * helper method for depth first search.
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @param out of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    private static <T> void dfsHel(Vertex<T> start, Graph<T> graph, List<Vertex<T>> out) {
        out.add(start);
        List<VertexDistance<T>> adj = graph.getAdjList().get(start);
        if (adj == null) {
            throw new IllegalArgumentException("Adjacent list is null");
        }
        for (VertexDistance<T> ab : adj) {
            if (!out.contains(ab.getVertex())) {
                dfsHel(ab.getVertex(), graph, out);
            }
        }
    }
    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adj = graph.getAdjList();
        PriorityQueue<VertexDistance<T>> queue = new PriorityQueue<>();
        List<Vertex<T>> visited = new ArrayList<>();
        Map<Vertex<T>, Integer> map = new HashMap<>();
        if (adj.get(start) == null) {
            throw new IllegalArgumentException("Starting vertex is not in the graph");
        }
        for (Vertex<T> vertex : graph.getVertices()) {
            map.put(vertex, Integer.MAX_VALUE);
        }
        queue.add(new VertexDistance<T>(start, 0));
        while (!queue.isEmpty() && visited.size() < adj.size()) {
            VertexDistance<T> verDist = queue.remove();
            if (!visited.contains(verDist.getVertex())) {
                List<VertexDistance<T>> adjL = adj.get(verDist.getVertex());
                visited.add(verDist.getVertex());
                map.put(verDist.getVertex(), verDist.getDistance());
                for (VertexDistance<T> ab : adjL) {
                    if (!visited.contains(ab.getVertex())) {
                        queue.add(new VertexDistance<>(ab.getVertex(), ab.getDistance() + verDist.getDistance()));
                    }
                }
            }
        }
        return map;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph is null");
        }
        DisjointSet<Vertex<T>> disSe = new DisjointSet<>();
        Set<Edge<T>> mstSet = new HashSet<>();
        Queue<Edge<T>> edge = new PriorityQueue<>(graph.getEdges());
        while (!edge.isEmpty()) {
            Edge<T> deEdge = edge.poll();
            if (!disSe.find(deEdge.getU()).equals(disSe.find(deEdge.getV()))) {
                mstSet.add(deEdge);
                disSe.union(disSe.find(deEdge.getU()), deEdge.getV());
                mstSet.add(new Edge<T>(deEdge.getV(), deEdge.getU(), deEdge.getWeight()));
            }
        }
        if (mstSet.size() == 2 * (graph.getAdjList().keySet().size() - 1)) {
            return mstSet;
        } else {
            return null;
        }
    }
}
