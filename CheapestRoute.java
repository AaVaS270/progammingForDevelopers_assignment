package assignments;
import java.util.*;

public class CheapestRoute {

    static class Edge {
        int source, dest, time, cost;

        public Edge(int source, int dest, int time, int cost) {
            this.source = source;
            this.dest = dest;
            this.time = time;
            this.cost = cost;
        }
    }

    public static int cheapestRoute(int[][] edges, int[] charges, int source, int dest, int timeConstraint) {
        int n = charges.length;
        List<Edge>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], t = edge[2];
            graph[u].add(new Edge(u, v, t, charges[v]));
            graph[v].add(new Edge(v, u, t, charges[u]));
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.time, b.time));
        pq.offer(new Edge(source, source, 0, charges[source]));
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        while (!pq.isEmpty()) {
            Edge curr = pq.poll();
            if (curr.dest == dest) {
                int[] path = new int[n];
                int index = 0;
                int u = dest;
                while (u != source) {
                    path[index++] = u;
                    u = parent[u];
                }
                path[index] = source;
                for (int i = index; i >= 0; i--) {
                    System.out.print(path[i] + " ");
                }
                System.out.println();
                return curr.cost;
            }
            if (visited[curr.dest] || curr.time > timeConstraint) {
                continue;
            }
            visited[curr.dest] = true;
            for (Edge e : graph[curr.dest]) {
                int newCost = curr.cost + e.cost;
                int newTime = curr.time + e.time;
                pq.offer(new Edge(e.source, e.dest, newTime, newCost));
                if (parent[e.dest] == -1) {
                    parent[e.dest] = curr.dest;
                }
            }
        }
        return -1; 
    }

    public static void main(String[] args) {
        int[][] edges = {{0,1,5}, {0,3,2}, {1,2,5}, {3,4,5}, {4,5,6}, {2,5,5}};
        int[] charges = {10,2,3,25,25,4};
        int source = 0, dest = 5, timeConstraint = 15;
        System.out.print("Route:");
        int cost = cheapestRoute(edges, charges, source, dest, timeConstraint);
        System.out.print("Cost travelling  ");
        System.out.print(source + " ");
        System.out.print("to ");
        System.out.print(dest + ": ");
        System.out.println(cost);
    }
}
