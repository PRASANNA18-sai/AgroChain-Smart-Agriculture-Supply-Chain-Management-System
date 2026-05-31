import java.util.*;

public class AgroChainCO2 {

    // ================= SEGMENT TREE =================
    static class SegmentTree {
        int[] tree;
        int n;

        SegmentTree(int[] arr) {
            n = arr.length;
            tree = new int[4 * n];
            build(arr, 1, 0, n - 1);
        }

        void build(int[] arr, int node, int start, int end) {
            if (start == end)
                tree[node] = arr[start];
            else {
                int mid = (start + end) / 2;
                build(arr, 2 * node, start, mid);
                build(arr, 2 * node + 1, mid + 1, end);
                tree[node] = tree[2 * node] + tree[2 * node + 1];
            }
        }

        int query(int node, int start, int end, int l, int r) {
            if (r < start || end < l)
                return 0;

            if (l <= start && end <= r)
                return tree[node];

            int mid = (start + end) / 2;

            return query(2 * node, start, mid, l, r)
                    + query(2 * node + 1, mid + 1, end, l, r);
        }
    }

    // ================= FENWICK TREE =================
    static class FenwickTree {
        int[] bit;
        int n;

        FenwickTree(int n) {
            this.n = n;
            bit = new int[n + 1];
        }

        void update(int index, int value) {
            index++;

            while (index <= n) {
                bit[index] += value;
                index += index & (-index);
            }
        }

        int query(int index) {
            index++;

            int sum = 0;

            while (index > 0) {
                sum += bit[index];
                index -= index & (-index);
            }

            return sum;
        }
    }

    // ================= BFS =================
    static void bfs(List<List<Integer>> graph, int start) {

        String[] names = {"Farm", "Warehouse", "Market", "Retailer"};

        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> q = new LinkedList<>();

        visited[start] = true;
        q.add(start);

        while (!q.isEmpty()) {

            int node = q.poll();

            System.out.print(names[node]);

            for (int neigh : graph.get(node)) {
                if (!visited[neigh]) {
                    visited[neigh] = true;
                    q.add(neigh);
                }
            }

            if (!q.isEmpty())
                System.out.print(" -> ");
        }
    }

    // ================= DFS =================
    static void dfs(List<List<Integer>> graph, int node,
                    boolean[] visited, String[] names) {

        visited[node] = true;

        System.out.print(names[node]);

        for (int neigh : graph.get(node)) {
            if (!visited[neigh]) {
                System.out.print(" -> ");
                dfs(graph, neigh, visited, names);
            }
        }
    }

    // ================= EDGE =================
    static class Edge implements Comparable<Edge> {
        int src, dest, weight;

        Edge(int s, int d, int w) {
            src = s;
            dest = d;
            weight = w;
        }

        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
    }

    // ================= KRUSKAL =================
    static int[] parent = new int[100];

    static int find(int x) {
        if (parent[x] == x)
            return x;

        return parent[x] = find(parent[x]);
    }

    static void union(int a, int b) {
        parent[find(a)] = find(b);
    }

    static int kruskal(List<Edge> edges, int V) {

        for (int i = 0; i < V; i++)
            parent[i] = i;

        Collections.sort(edges);

        int cost = 0;

        for (Edge e : edges) {

            if (find(e.src) != find(e.dest)) {

                union(e.src, e.dest);

                cost += e.weight;
            }
        }

        return cost;
    }

    // ================= PRIM =================
    static int prim(int[][] graph, int V) {

        boolean[] mst = new boolean[V];
        int[] key = new int[V];

        Arrays.fill(key, Integer.MAX_VALUE);

        key[0] = 0;

        int cost = 0;

        for (int count = 0; count < V; count++) {

            int u = -1;

            for (int i = 0; i < V; i++) {
                if (!mst[i] &&
                        (u == -1 || key[i] < key[u]))
                    u = i;
            }

            mst[u] = true;

            cost += key[u];

            for (int v = 0; v < V; v++) {

                if (graph[u][v] != 0 &&
                        !mst[v] &&
                        graph[u][v] < key[v]) {

                    key[v] = graph[u][v];
                }
            }
        }

        return cost;
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        System.out.println("AGROCHAIN AGRICULTURAL SUPPLY NETWORK ANALYSIS\n");

        int[] production = {100, 200, 150, 300, 250};

        SegmentTree st = new SegmentTree(production);

        System.out.println("SEGMENT TREE OPERATIONS\n");
        System.out.println("Regional Crop Production (Tons):");
        System.out.println("Region A : 100");
        System.out.println("Region B : 200");
        System.out.println("Region C : 150");
        System.out.println("Region D : 300");
        System.out.println("Region E : 250");

        int rangeSum =
                st.query(1, 0,
                        production.length - 1,
                        1, 3);

        System.out.println("\nProduction Query");
        System.out.println("Regions B to D Total Production:");
        System.out.println(rangeSum + " Tons\n");

        FenwickTree ft =
                new FenwickTree(production.length);

        for (int i = 0; i < production.length; i++)
            ft.update(i, production[i]);

        System.out.println("FENWICK TREE OPERATIONS\n");
        System.out.println("Cumulative Production Till Region D:");
        System.out.println(ft.query(3) + " Tons\n");

        int V = 4;

        List<List<Integer>> graph =
                new ArrayList<>();

        for (int i = 0; i < V; i++)
            graph.add(new ArrayList<>());

        graph.get(0).add(1);
        graph.get(1).add(2);
        graph.get(2).add(3);

        System.out.println("BFS NETWORK TRAVERSAL\n");
        System.out.print("Supply Chain Connectivity:\n");
        bfs(graph, 0);

        System.out.println("\n");

        System.out.println("DFS NETWORK TRAVERSAL\n");
        System.out.print("Supply Route Exploration:\n");

        boolean[] visited = new boolean[V];

        String[] names =
                {"Farm", "Warehouse", "Market", "Retailer"};

        dfs(graph, 0, visited, names);

        System.out.println("\n");

        List<Edge> edges = new ArrayList<>();

        edges.add(new Edge(0, 1, 4));
        edges.add(new Edge(0, 2, 3));
        edges.add(new Edge(1, 2, 2));
        edges.add(new Edge(1, 3, 5));

        int kruskalCost =
                kruskal(edges, 4);

        int[][] primGraph = {
                {0, 4, 3, 0},
                {4, 0, 2, 5},
                {3, 2, 0, 0},
                {0, 5, 0, 0}
        };

        int primCost =
                prim(primGraph, 4);

        System.out.println("PRIM'S MST ANALYSIS\n");
        System.out.println("Minimum Transportation Cost:");
        System.out.println(primCost + " Units\n");

        System.out.println("KRUSKAL'S MST ANALYSIS\n");
        System.out.println("Minimum Transportation Cost:");
        System.out.println(kruskalCost + " Units\n");

        System.out.println("Time Complexity:");
        System.out.println("Segment Tree Query : O(log n)");
        System.out.println("Fenwick Tree Query : O(log n)");
        System.out.println("BFS Traversal : O(V + E)");
        System.out.println("DFS Traversal : O(V + E)");
        System.out.println("Prim's Algorithm : O(V²)");
        System.out.println("Kruskal's Algorithm : O(E log E)");

        System.out.println("\nProcess finished with exit code 0");
    }
}
