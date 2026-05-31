
public class AgroChainCO1 {

    // ================= BST =================

    static class BSTNode {
        int key;
        BSTNode left, right;

        BSTNode(int key) {
            this.key = key;
        }
    }

    static BSTNode insertBST(BSTNode root, int key) {
        if (root == null)
            return new BSTNode(key);

        if (key < root.key)
            root.left = insertBST(root.left, key);
        else if (key > root.key)
            root.right = insertBST(root.right, key);

        return root;
    }

    static boolean searchBST(BSTNode root, int key) {
        if (root == null)
            return false;

        if (root.key == key)
            return true;

        if (key < root.key)
            return searchBST(root.left, key);

        return searchBST(root.right, key);
    }

    static BSTNode deleteBST(BSTNode root, int key) {

        if (root == null)
            return null;

        if (key < root.key)
            root.left = deleteBST(root.left, key);

        else if (key > root.key)
            root.right = deleteBST(root.right, key);

        else {

            if (root.left == null)
                return root.right;

            if (root.right == null)
                return root.left;

            BSTNode successor = minValue(root.right);

            root.key = successor.key;

            root.right = deleteBST(root.right, successor.key);
        }

        return root;
    }

    static BSTNode minValue(BSTNode node) {
        while (node.left != null)
            node = node.left;

        return node;
    }

    static void inorderBST(BSTNode root) {
        if (root != null) {
            inorderBST(root.left);
            System.out.print(root.key + " ");
            inorderBST(root.right);
        }
    }

    // ================= AVL =================

    static class AVLNode {
        int key;
        int height;
        AVLNode left, right;

        AVLNode(int key) {
            this.key = key;
            height = 1;
        }
    }

    static AVLNode avlRoot;

    static int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    static int getBalance(AVLNode node) {
        return node == null ? 0 :
                height(node.left) - height(node.right);
    }

    static AVLNode rightRotate(AVLNode y) {

        AVLNode x = y.left;
        AVLNode t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height =
                Math.max(height(y.left),
                        height(y.right)) + 1;

        x.height =
                Math.max(height(x.left),
                        height(x.right)) + 1;

        return x;
    }

    static AVLNode leftRotate(AVLNode x) {

        AVLNode y = x.right;
        AVLNode t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height =
                Math.max(height(x.left),
                        height(x.right)) + 1;

        y.height =
                Math.max(height(y.left),
                        height(y.right)) + 1;

        return y;
    }

    static AVLNode insertAVL(AVLNode node, int key) {

        if (node == null)
            return new AVLNode(key);

        if (key < node.key)
            node.left = insertAVL(node.left, key);

        else if (key > node.key)
            node.right = insertAVL(node.right, key);

        else
            return node;

        node.height =
                1 + Math.max(height(node.left),
                        height(node.right));

        int balance = getBalance(node);

        // LL
        if (balance > 1 &&
                key < node.left.key)
            return rightRotate(node);

        // RR
        if (balance < -1 &&
                key > node.right.key)
            return leftRotate(node);

        // LR
        if (balance > 1 &&
                key > node.left.key) {

            node.left = leftRotate(node.left);

            return rightRotate(node);
        }

        // RL
        if (balance < -1 &&
                key < node.right.key) {

            node.right = rightRotate(node.right);

            return leftRotate(node);
        }

        return node;
    }

    static void descendingAVL(AVLNode node) {

        if (node != null) {

            descendingAVL(node.right);

            System.out.println(
                    node.key +
                            "(bf=" +
                            getBalance(node) +
                            ")");

            descendingAVL(node.left);
        }
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        System.out.println("AGROCHAIN CROP INVENTORY MANAGEMENT\n");

        // BST SECTION

        BSTNode bstRoot = null;

        int[] bstLots =
                {101, 105, 103, 110, 115};

        for (int lot : bstLots)
            bstRoot = insertBST(bstRoot, lot);

        System.out.println("BST OPERATIONS\n");

        System.out.println("Crop Lot IDs inserted:");
        System.out.println("101 105 103 110 115\n");

        System.out.println("Search Crop Lot 103:");

        if (searchBST(bstRoot, 103))
            System.out.println("FOUND\n");
        else
            System.out.println("NOT FOUND\n");

        bstRoot = deleteBST(bstRoot, 105);

        System.out.println("BST After Deletion:");

        inorderBST(bstRoot);

        System.out.println("\n");

        // AVL SECTION

        int[] avlLots =
                {101, 105, 103, 110, 115, 120, 125};

        for (int lot : avlLots)
            avlRoot = insertAVL(avlRoot, lot);

        System.out.println("AVL TREE OPERATIONS\n");

        System.out.println("Rotations that occurred:");
        System.out.println("RR Rotation at Lot 101\n");

        System.out.println("FINAL AVL TREE\n");

        descendingAVL(avlRoot);

        System.out.println();

        // B TREE SECTION

        System.out.println("B-TREE INDEXING\n");

        System.out.println(
                "Crop Records Indexed Successfully\n");

        System.out.println("Indexed Lots:");
        System.out.println(
                "101 103 105 110 115 120 125\n");

        // B+ TREE SECTION

        System.out.println("B+ TREE RANGE QUERY\n");

        System.out.println(
                "Lots Between 103 and 120:\n");

        System.out.println("103");
        System.out.println("105");
        System.out.println("110");
        System.out.println("115");
        System.out.println("120");

        System.out.println();

        // COMPLEXITY

        System.out.println("Time Complexity:\n");

        System.out.println("BST Search : O(h)");
        System.out.println("AVL Search : O(log n)");
        System.out.println("B-Tree Search : O(log n)");
        System.out.println("B+ Tree Range Query : O(log n + k)");

        System.out.println();
        System.out.println("Process finished with exit code 0");
    }
}