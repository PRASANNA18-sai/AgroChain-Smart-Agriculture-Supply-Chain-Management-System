import java.util.*;

public class AgroChainCO3 {

    static final int INF = 99999;

    // ================= DIJKSTRA =================
    static void dijkstra(int[][] graph, int src) {

        int V = graph.length;
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {

            int u = -1;

            for (int i = 0; i < V; i++) {
                if (!visited[i] &&
                        (u == -1 || dist[i] < dist[u])) {
                    u = i;
                }
            }

            visited[u] = true;

            for (int v = 0; v < V; v++) {

                if (!visited[v]
                        && graph[u][v] != 0
                        && dist[u] != Integer.MAX_VALUE
                        && dist[u] + graph[u][v] < dist[v]) {

                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        System.out.println("Warehouse : " + dist[1] + " km");
        System.out.println("Market    : " + dist[2] + " km");
        System.out.println("Retailer  : " + dist[3] + " km");
    }

    // ================= BELLMAN FORD =================
    static class Edge {
        int src, dest, weight;

        Edge(int s, int d, int w) {
            src = s;
            dest = d;
            weight = w;
        }
    }

    static void bellmanFord(List<Edge> edges, int V, int src) {

        int[] dist = new int[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int i = 1; i < V; i++) {

            for (Edge e : edges) {

                if (dist[e.src] != Integer.MAX_VALUE &&
                        dist[e.src] + e.weight < dist[e.dest]) {

                    dist[e.dest] = dist[e.src] + e.weight;
                }
            }
        }

        System.out.println("Warehouse : " + dist[1] + " km");
        System.out.println("Market    : " + dist[2] + " km");
        System.out.println("Retailer  : " + dist[3] + " km");
    }

    // ================= FLOYD WARSHALL =================
    static void floydWarshall(int[][] graph) {

        int V = graph.length;

        int[][] dist = new int[V][V];

        for (int i = 0; i < V; i++)
            dist[i] = graph[i].clone();

        for (int k = 0; k < V; k++) {

            for (int i = 0; i < V; i++) {

                for (int j = 0; j < V; j++) {

                    if (dist[i][k] != INF &&
                            dist[k][j] != INF &&
                            dist[i][k] + dist[k][j] < dist[i][j]) {

                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        for (int i = 0; i < V; i++) {

            for (int j = 0; j < V; j++) {
                System.out.print(dist[i][j] + "  ");
            }

            System.out.println();
        }
    }

    // ================= MERGE SORT =================
    static void mergeSort(int[] arr, int left, int right) {

        if (left < right) {

            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    static void merge(int[] arr,
                      int left,
                      int mid,
                      int right) {

        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];

        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {

            if (L[i] <= R[j])
                arr[k++] = L[i++];
            else
                arr[k++] = R[j++];
        }

        while (i < n1)
            arr[k++] = L[i++];

        while (j < n2)
            arr[k++] = R[j++];
    }

    // ================= QUICK SORT =================
    static void quickSort(int[] arr, int low, int high) {

        if (low < high) {

            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    static int partition(int[] arr,
                         int low,
                         int high) {

        int pivot = arr[high];

        int i = low - 1;

        for (int j = low; j < high; j++) {

            if (arr[j] < pivot) {

                i++;

                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        System.out.println("AGROCHAIN SMART LOGISTICS AND MARKET OPTIMIZATION\n");

        int[][] graph = {
                {0,4,7,9},
                {4,0,3,5},
                {7,3,0,2},
                {9,5,2,0}
        };

        System.out.println("DIJKSTRA'S ALGORITHM\n");
        System.out.println("Shortest Delivery Routes from Farm:\n");
        dijkstra(graph,0);

        System.out.println("\nBELLMAN-FORD ALGORITHM\n");
        System.out.println("Shortest Delivery Routes from Farm:\n");

        List<Edge> edges = new ArrayList<>();

        edges.add(new Edge(0,1,4));
        edges.add(new Edge(1,2,3));
        edges.add(new Edge(2,3,2));
        edges.add(new Edge(0,2,7));
        edges.add(new Edge(1,3,5));

        bellmanFord(edges,4,0);

        System.out.println("\nFLOYD-WARSHALL ANALYSIS\n");
        System.out.println("All-Pairs Delivery Cost Matrix:\n");
        floydWarshall(graph);

        int[] demand = {50,30,80,20,60};

        System.out.println("\nMERGE SORT OPERATIONS\n");
        System.out.println("Crop Demand Data:\n");

        for(int x : demand)
            System.out.print(x + " ");

        mergeSort(demand,0,demand.length-1);

        System.out.println("\n\nSorted Demand Ranking:\n");

        for(int x : demand)
            System.out.print(x + " ");

        int[] prices = {45,25,60,15,35};

        System.out.println("\n\nQUICK SORT OPERATIONS\n");
        System.out.println("Crop Market Prices:\n");

        for(int x : prices)
            System.out.print(x + " ");

        quickSort(prices,0,prices.length-1);

        System.out.println("\n\nSorted Price Ranking:\n");

        for(int x : prices)
            System.out.print(x + " ");

        System.out.println("\n\nTime Complexity:");
        System.out.println("Dijkstra's Algorithm : O(V²)");
        System.out.println("Bellman-Ford Algorithm : O(VE)");
        System.out.println("Floyd-Warshall Algorithm : O(V³)");
        System.out.println("Merge Sort : O(n log n)");
        System.out.println("Quick Sort (Average Case) : O(n log n)");

        System.out.println("\nProcess finished with exit code 0");
    }
}
