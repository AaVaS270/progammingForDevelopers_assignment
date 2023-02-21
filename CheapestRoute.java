package assignments;
import java.util.*;


//Question 1(a)
//There are n nations linked by train routes. You are given a 2D array indicating routes between countries and the time required to reach the target country, such that E[i]=[xi,yi,ki],
//where xi represents the source country, yi represents the destination country, and ki represents the time required to go from xi to yi. If you are also given information on the charges,
//you must pay while entering any country. Create an algorithm that returns the cheapest route from county A to county B with a time constraint.

class CheapestRoute {
    public int[] findCheapestRoute(int[][] routes, int[] charges, int A, int B, int K, int N) {
        Map<Integer, List<int[]>> graph = buildGraph(routes, charges, N);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, A, K}); // {distance, node, time left}
        int[] dist = new int[N + 1];
        int[] cost = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(cost, Integer.MAX_VALUE);
        dist[A] = 0;
        cost[A] = 0;
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int currDist = curr[0];
            int currNode = curr[1];
            int currTime = curr[2];
            if (currNode == B) {
                return new int[]{dist[B], cost[B]};
            }
            if (currTime == 0) {
                continue; // can't go further if there's no time left
            }
            for (int[] neighbor : graph.get(currNode)) {
                int nextNode = neighbor[0];
                int nextDist = neighbor[1];
                int nextCost = neighbor[2];
                int timeLeft = currTime - nextDist;
                if (timeLeft >= 0 && currDist + nextDist < dist[nextNode]) {
                    dist[nextNode] = currDist + nextDist;
                    cost[nextNode] = cost[currNode] + nextCost;
                    pq.offer(new int[]{dist[nextNode], nextNode, timeLeft});
                }

            }
        }
        return new int[]{-1, -1}; // can't reach B within the time constraint
    }

    private Map<Integer, List<int[]>> buildGraph(int[][] routes, int[] charges, int N) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int i = 1; i <= N; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] route : routes) {
            int from = route[0];
            int to = route[1];
            int dist = route[2];
            int cost = charges[from - 1];
            graph.get(from).add(new int[]{to, dist, cost});
        }
        return graph;
    }
   public static void main(String[] args) {
            int[][] edge = {{0,1,5}, {0,3,2}, {1,2,5}, {3,4,5}, {4,5,6}, {2,5,5}};
            int[] charges = {10,2,3,25,25,4};
            int source = 0;
            int destination = 5;
            int timeConstraint = 14;

            CheapestRoute solution = new CheapestRoute();
            int[] result = solution.findCheapestRoute(edge, charges, source, destination, timeConstraint, 5);

            if (result[0] == -1 && result[1] == -1) {
                System.out.println("It is not possible to reach destination within the time constraint.");
            } else {
                System.out.println("The cheapest route from " + source + " to " + destination + " is " + result[0] + " with a total cost of " + result[1] + ".");
            }
        }

}

