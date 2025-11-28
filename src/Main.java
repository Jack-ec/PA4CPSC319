import java.io.*;
import java.util.*;

// Node class representing an element in a singly linked list.
class Node {
    String data;
    int weight;
    Node next;

    public Node(String data, int weight) {
        this.data = data;
        this.weight = weight;
        this.next = null;
    }
}

// Edge class representing a directed edge with a destination and weight.
class Edge {
    String destination;
    int weight;

    public Edge(String destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }
}

// Singly Linked List implementation for storing edges in adjacency list format.
class SinglyLinkedList {
    private Node head;

    // Edges are inserted in order by weight.
    // While Dijkstra's algorithm does not require this,
    // it's useful to understand how ordered lists work in adjacency representations.
    public void addSorted(String destination, int weight) {
        Node newNode = new Node(destination, weight);
        if (head == null || head.weight > weight) {
            newNode.next = head;
            head = newNode;
            return;
        }

        Node current = head;
        while (current.next != null && current.next.weight < weight) {
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;
    }

    public List<Edge> toList() {
        List<Edge> edges = new ArrayList<>();
        Node current = head;
        while (current != null) {
            edges.add(new Edge(current.data, current.weight));
            current = current.next;
        }
        return edges;
    }
}

// Graph class implementing an adjacency list representation using linked lists.
class Graph {
    private Map<String, SinglyLinkedList> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    // TODO #1: Add a directed edge from 'source' to 'destination' with the given weight.
    //
    // ** Core to building the graph (adjacency list logic). Students should write this. **
    //
    // HINT: This method updates the adjacency list — a HashMap where:
    //       - Each key is a source node (e.g., "A", "B")
    //       - Each value is a linked list of neighbors (destinations with weights)
    //
    //       Use the get-or-create pattern: if the source node isn't already in the map,
    //       create a new SinglyLinkedList for it first, then add the destination node to it.
    //
    // PSEUDOCODE:
    //      If the source node is not already in the adjacency list:
    //           Create an empty linked list for the source node.
    //      Insert the destination node (and weight) into the source node’s list,
    //      making sure the list remains sorted by weight.
    //
    // Example:
    //    If "A" already has edges to "B" (weight 3) and "C" (weight 7),
    //    and you now add ("A", "D", 5),
    //    the final order in the list will be: B (3) → D (5) → C (7)
    //
    public void addEdge(String source, String destination, int weight) {
        if (!containsNode(source)) {
            adjacencyList.put(source, new SinglyLinkedList());
        }
        adjacencyList.get(source).addSorted(destination, weight);
    }


    // This method checks if a given node exists in the graph.
    // It returns true if the node is present in the adjacency list, false otherwise.
    public boolean containsNode(String node) {

        // The adjacencyList is a map that stores each node as a key.
        // If the node is present as a key in the map, it means the graph knows about it.
        // We use the built-in containsKey() method to check for the node's presence.
        return adjacencyList.containsKey(node);
    }

    // Dijkstra’s algorithm starts.
    public void dijkstra(String start) {

        // Create a HashMap to store the shortest known distances from the start node to every other node.
        // The key is the node name (as a String), and the value is the shortest distance found so far (as an Integer).
        // Initially, all distances will be set to "infinity" (Integer.MAX_VALUE), except for the starting node.
        Map<String, Integer> distances = new HashMap<>();

        // Create a PriorityQueue (min-heap) to always process the node with the smallest known distance next.
        // Each entry in the queue is a key-value pair: node name and its current shortest distance.
        // We use Map.Entry.comparingByValue() to automatically sort entries based on the distance value.
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());


        // Convert the set of nodes (from the adjacency list keys) into a list,
        // so we can loop over them using a basic index-based for-loop.
        List<String> nodeList = new ArrayList<>(adjacencyList.keySet());

        // Loop through each node in the graph using a classic for-loop.
        for (int i = 0; i < nodeList.size(); i++) {

            // Get the node at index i from the node list.
            // This gives us the node name as a string (e.g., "A", "B", "1", "2", etc.).
            String node = nodeList.get(i);

            // Set the initial distance to this node as "infinity",
            // represented by Integer.MAX_VALUE in Java.
            // This means we don’t yet know the shortest path to this node.
            distances.put(node, Integer.MAX_VALUE);
        }

        // TODO #2: Set the starting node's distance to 0, because the shortest path from the start to itself is zero.
        // All other nodes will initially be set to "infinity" (Integer.MAX_VALUE), but the start is known.
        //
        // ** Conceptually important: distance from start node to itself is 0. **
        //
        // PSEUDOCODE:
        //      Set the distance from the start node to itself as 0.
        //      (All other nodes have already been initialized to infinity.)
        //
        TODO #2: ** YOUR CODE HERE **

                // Add the starting node to the priority queue.
                // We begin Dijkstra’s algorithm by visiting the start node, with a known distance of 0.

                // Create a key-value pair (entry) that represents the starting node and its distance.
                // The key is the node name (e.g., "A" or "1"), and the value is the distance (0).
                Map.Entry<String, Integer> startEntry = new AbstractMap.SimpleEntry<>(start, 0);

        // Add this entry to the priority queue.
        // This tells the algorithm to start processing from this node first.
        pq.add(startEntry);


        // TODO #3: Keep processing nodes from the priority queue until it's empty.
        // The queue always gives us the next node with the smallest known distance.
        //
        // ** Main priority queue loop logic — students must understand how Dijkstra works. **
        //
        // PSEUDOCODE:
        //      While the priority queue is not empty:
        //          Remove the node with the smallest known distance.
        //          Process its neighbors to try to find shorter paths.
        //
        // YOUR RESPONSIBILITY: Start the loop and extract the most promising node —
        //                          a key conceptual and practical moment in understanding Dijkstra’s algorithm.
        //
        while ( TODO #3: ** YOUR CODE HERE ** ) {

            // Remove (poll) the node with the smallest distance from the priority queue.
            // This gives us the next "most promising" node to explore.
            TODO #3: ** YOUR CODE HERE **

                    // Extract the node name (e.g., "A", "B", etc.) from the current entry.
                    TODO #3: ** YOUR CODE HERE **

            // Extract the current known distance from the start node to this node.
            int currentDistance = current.getValue();

            // TODO #4: Skip this node if we’ve already found a shorter path to it before.
            // This avoids re-processing nodes with outdated (longer) distances.
            // ** Critical: skip outdated paths — real Dijkstra logic. **
            //
            // PSEUDOCODE:
            //      If the distance we are currently processing is greater than the best known distance:
            //          Skip this node (we’ve already found a better way to reach it).
            //
            TODO #4: ** YOUR CODE HERE **

                    // Get the list of neighbors (edges) for the current node.
                    // If the current node has no outgoing edges, we get an empty list instead.
                    List<Edge> neighbors = adjacencyList.getOrDefault(currentNode, new SinglyLinkedList()).toList();

            // Loop through each edge in the neighbor list using a basic for-loop with index.
            for (int i = 0; i < neighbors.size(); i++) {

                // Get the edge at index i from the list of neighbors.
                // This edge represents a connection from the current node to a neighbor.
                // Each edge object contains the neighbor's name (edge.destination) and the cost to reach it (edge.weight).
                Edge edge = neighbors.get(i);

                // TODO #5: Calculate the total distance from the start to this neighbor node
                // through the current node.
                // ** Teaches distance update + priority queue reinsertion — fundamental Dijkstra step. **
                //
                // PSEUDOCODE: newDist = current node’s distance + edge weight to this neighbor
                //
                TODO #5: ** YOUR CODE HERE **

                // Get the current known shortest distance to the destination node.
                // If the destination node is not yet in the distances map, use Integer.MAX_VALUE as the default (infinity).
                int knownDistance = distances.getOrDefault(edge.destination, Integer.MAX_VALUE);

                // TODO #6 & #7: Compare the new distance (through the current node) with the known distance.
                // If the new path is shorter, we will update it in the map.
                //
                // ** Teaches distance update + priority queue reinsertion — fundamental Dijkstra step. **
                //
                // PSEUDOCODE:
                //      If the new distance is less than the current known distance:
                //          Update the shortest path to this neighbor.

                if ( TODO #6: ** YOUR CODE HERE ** ) {

                    // TODO #7: Update the shortest known distance to this neighbor.
                    //
                    // ** Teaches distance update + priority queue reinsertion — fundamental Dijkstra step. **
                    //
                    // PSEUDOCODE: Save newDist as the best known distance to this neighbor.

                    TODO #7: ** YOUR CODE HERE **

                            // TODO #8: Add this entry to the priority queue.
                            // The queue will later give us this neighbor to process based on the shortest distance.
                            //
                            // ** Teaches distance update + priority queue reinsertion — fundamental Dijkstra step. **
                            //
                            // PSEUDOCODE:
                            //      Create a (node, distance) pair for the neighbor.
                            //      Add it to the priority queue.
                            //

                            TODO #8: ** YOUR CODE HERE **

                }
            }
        }

        // =================================================================================
        // Note: The final output block is provided for consistency and
        // to help you focus on the core logic of Dijkstra's algorithm.
        // Do not modify this unless instructed.

        // Get all the node names from the distances map and store them in a list,
        // so we can use a classic index-based for loop.
        List<String> outputNodeList = new ArrayList<>(distances.keySet());

        // Loop through the list using a traditional for loop with an index.
        for (int i = 0; i < outputNodeList.size(); i++) {

            // Get the node at the current index.
            String node = outputNodeList.get(i);

            // Look up the distance from the start node to this node.
            int dist = distances.get(node);

            // If the distance is still Integer.MAX_VALUE (i.e. infinity), we never found a path to this node.
            // Otherwise, print the actual distance.
            if (dist == Integer.MAX_VALUE) {
                System.out.println(start + " → " + node + ": No Path Available");
            } else {
                System.out.println(start + " → " + node + ": " + dist);
            }
        }
    }

    // This method loads a graph from a text file.
    // Each line in the file should represent a directed edge in the format:
    // source destination weight
    // Example: A B 5
    public void loadGraphFromFile(String filename) throws IOException {

        // Create a BufferedReader to read the file line by line.
        // FileReader opens the file, and BufferedReader allows efficient reading of text.
        BufferedReader br = new BufferedReader(new FileReader(filename));

        // Declare a variable to hold each line of the file as we read it.
        String line;

        // Read the file one line at a time until we reach the end (i.e., readLine() returns null).
        while ((line = br.readLine()) != null) {

            // If the line starts with '#' (a comment) or is blank/empty, skip it.
            // This allows students to comment out lines or leave spacing in their input file.
            if (line.startsWith("#") || line.isBlank()) continue;

            // Split the line into parts using whitespace (spaces, tabs, etc.) as separators.
            // This should result in exactly three parts: source, destination, and weight.
            String[] parts = line.split("\\s+");

            // Only process the line if it has exactly 3 parts.
            // This avoids errors if the line is malformed.
            if (parts.length == 3) {

                // Call addEdge(...) using the parsed values:
                // parts[0] = source node (String)
                // parts[1] = destination node (String)
                // parts[2] = weight (converted to int)
                addEdge(parts[0], parts[1], Integer.parseInt(parts[2]));
            }
        }

        // Close the file once all lines have been read to free system resources.
        br.close();
    }

}

// The Main class contains the entry point of the program.
public class Main {

    // The main method is where the program starts running.
    public static void main(String[] args) {

        // Create a Scanner object to read user input from the console.
        Scanner scanner = new Scanner(System.in);

        // Create a new Graph object to hold the graph data and methods.
        Graph graph = new Graph();

        // Prompt the user to enter the name of the graph file.
        System.out.print("Enter the graph file: ");

        // Read the filename entered by the user (e.g., "graph.txt").
        String filename = scanner.nextLine();

        try {
            // Try to load the graph data from the file.
            // This method reads edges from the file and builds the graph.
            graph.loadGraphFromFile(filename);

            // Confirm to the user that the file was read successfully.
            System.out.println("Graph loaded successfully.\n");
        } catch (IOException e) {
            // If an error occurs while reading the file, show an error message.
            // This might happen if the file doesn't exist or is unreadable.
            System.out.println("Error reading file: " + e.getMessage());

            // Exit the program early since the graph couldn’t be loaded.
            return;
        }

        // Enter a loop to allow the user to repeatedly query the graph.
        while (true) {

            // Ask the user to enter a starting node for Dijkstra's algorithm.
            // Typing 'exit' will quit the loop and end the program.
            System.out.print("Enter the starting node (or type 'exit' to quit): ");

            // Read the user’s input as the starting node.
            String start = scanner.nextLine();

            // If the user types 'exit' (case-insensitive), break the loop and end the program.
            if (start.equalsIgnoreCase("exit")) break;

            // Check if the entered node actually exists in the graph.
            // If not, display an error and prompt the user again.
            if (!graph.containsNode(start)) {
                System.out.println("Error: Node '" + start + "' not found in graph.");
                continue;
            }

            // Run Dijkstra’s algorithm from the given starting node.
            // This will compute and print the shortest paths to all other nodes.
            graph.dijkstra(start);
        }

        // Inform the user that the program has finished.
        System.out.println("Program terminated.");
    }
}