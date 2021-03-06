import java.util.*;

public class BinaryTree {
    class Node {
        int data;
        Node left;
        Node right;

        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    private Node root;
    private int size;

    public BinaryTree() {
        Scanner sc = new Scanner(System.in);
        this.root = this.takeInput(sc, root, false);
    }
    
    public BinaryTree(int[] inOrder, int[] postOrder) {
        this.root = this.construct(inOrder, 0, inOrder.length - 1, postOrder, 0, postOrder.length - 1);
        this.size = postOrder.length;
    }

    private Node construct(int[] inOrder, int inStartIdx, int inLastIdx, int[] postOrder, int postStartIdx, int postLastIdx) {
        if (inStartIdx > inLastIdx || postStartIdx > postLastIdx) {
            return null;
        }

        Node node = new Node(0, null, null);
        node.data = postOrder[postLastIdx];  // root is the last element of postOrder array

        int rootIdx = -1;       // we will keep the index of root node in inOrder array here
        for (rootIdx = inStartIdx; rootIdx <= inLastIdx; rootIdx++) { // loop over inOrder array
            if (postOrder[postLastIdx] == inOrder[rootIdx]) { // and search for the root node
                break; // when found, break to store its index in rootIdx
            }
        }

        // inside the inOrder array
        // left side will start at inStartIdx and end at rootIdx - 1
        // right side will start at rootIdx + 1 and end at inLastIdx

        // inside the postOrder Array
        // left side will start at postStartIdx and end at postLastIdx - numElemRight - 1
        // right side will start at postLastIdx - numElemRight and end at postLastIdx - 1
        int numRightElem = inLastIdx - rootIdx; // calculate the number of elements in right subtree
        node.left = this.construct(inOrder, inStartIdx, rootIdx - 1,
                postOrder, postStartIdx,postLastIdx - numRightElem - 1);
        node.right = this.construct(inOrder, rootIdx + 1, inLastIdx,
                postOrder, postLastIdx - numRightElem, postLastIdx - 1);

        return node;
    }

    public Node takeInput(Scanner sc, Node parent, boolean isLeftChild) {
        if (parent == null) {
            System.out.println("Enter the data for parent node:");
        } else {
            if (isLeftChild) {
                System.out.println("Enter the data for left child: ");
            } else {
                System.out.println("Enter the data for right child: ");
            }
        }

        int childData = sc.nextInt();
        Node child = new Node(childData, null, null);
        size++;

        System.out.println("Does " + child.data + " have any left children? ");
        boolean hasLeftChild = sc.nextBoolean();
        if (hasLeftChild) {
            child.left = this.takeInput(sc, child, true);
        }

        System.out.println("Does " + child.data + " have any right child? ");
        boolean hasRightChild = sc.nextBoolean();
        if (hasRightChild) {
            child.right = this.takeInput(sc, child, false);
        }
        return child;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return (this.size() == 0);
    }

    public void display() {
        display(this.root);
    }

    private void display(Node node) {
        if (node == null) {
            return;
        }

        String outputString = "";
        outputString += node.left == null ? "." : node.left.data;
        outputString += "->" + node.data + "<-";
        outputString += node.right == null ? "." : node.right.data;

        System.out.println(outputString);

        this.display(node.left);
        this.display(node.right);
    }

    public int getSize() {
        return (this.getSize(this.root));
    }

    private int getSize(Node node) {
        if (node == null) {
            return 0;
        }

        int leftSize = this.getSize(node.left);
        int rightSize = this.getSize(node.right);

        return (leftSize + rightSize + 1);
    }

    public int max() {
        return this.max(this.root);
    }

    private int max(Node node) {
        if (node == null) {
            return Integer.MIN_VALUE;
        }

        int leftMax = this.max(node.left);
        int rightMax = this.max(node.right);

        return (Math.max(node.data, Math.max(leftMax, rightMax)));
    }

    public int height() {
        return this.height(this.root);
    }

    private int height(Node node) {
        if (node == null) {
            return -1;
        }

        int leftHeight = this.height(node.left);
        int rightHeight = this.height(node.right);

        return (Math.max(leftHeight, rightHeight) + 1);
    }

    public boolean find(int key) {
        return find(this.root, key);
    }

    private boolean find(Node node, int key) {
        if (node == null) {
            return false;
        }

        if (node.data == key) {
            return true;
        }

        boolean presentLeft = find(node.left, key);
        boolean presentRight = find(node.right, key);

        if (presentLeft || presentRight) {
            return true;
        }

        return false;
    }

    public void preOrderTraversal() {
        this.preOrderTraversal(this.root);
        System.out.println("-------------------");
    }

    private void preOrderTraversal(Node node) {
        if (node == null) {
            return;
        }

        System.out.print(node.data + ", ");
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }

    public void postOrderTraversal() {
        this.preOrderTraversal(this.root);
        System.out.println("-------------------------");
    }

    private void postOrderTraversal(Node node) {
        if (node == null) {
            return;
        }

        this.postOrderTraversal(node.left);
        this.postOrderTraversal(node.right);
        System.out.print(node.data + ", ");
    }

    public void inOrderTraversal() {
        this.inOrderTraversal(this.root);
        System.out.println("-----------------------");
    }

    private void inOrderTraversal(Node node) {
        if (node == null) {
            return;
        }

        this.inOrderTraversal(node.left);
        System.out.print(node.data + ", ");
        this.inOrderTraversal(node.right);
    }

    public void levelOrderTraversal() {
        this.levelOrderTraversal(this.root);
        System.out.println("-------------------");
    }

    private void levelOrderTraversal(Node node) {
        Queue<Node> q = new LinkedList<>();
        q.add(node);

        while (!q.isEmpty()) {
            Node removed = q.remove();
            System.out.print(removed.data + ", ");
            if (removed.left != null) {
                q.add(removed.left);
            }
            if (removed.right != null) {
                q.add(removed.right);
            }
        }
        System.out.println("-----------------------------");
    }

    public void levelOrderTraversalRecursive() {
        this.levelOrderTraversalRecursive(new ArrayList<>(Arrays.asList(this.root)));
        System.out.println("----------------------");
    }

    private void levelOrderTraversalRecursive(ArrayList<Node> level) {
        if (level.isEmpty())
            return;

        ArrayList<Node> nextLevel = new ArrayList<>();
        for (Node node : level) {
            System.out.print(node.data + ", ");
            if (node.left != null) {
                nextLevel.add(node.left);
            }
            if (node.right != null) {
                nextLevel.add(node.right);
            }
        }

        levelOrderTraversalRecursive(nextLevel);
    }

    public void printSingleChild() {
        this.printSingleChild(this.root, this.root.left);
        this.printSingleChild(this.root, this.root.right);
    }

    private void printSingleChild(Node parent, Node child) {
        if (child == null) {
            return;
        }

        if ((parent.left == null && parent.right == child) || (parent.right == null && parent.left == child)) {
            System.out.print(child.data + ", ");
        }

        this.printSingleChild(child, child.left);
        this.printSingleChild(child, child.right);
    }

    public void removeLeafNode() {
        this.removeLeafNode(this.root, this.root.left);
        this.removeLeafNode(this.root, this.root.right);
    }

    private void removeLeafNode(Node parent, Node child) {
        if (child == null) {
            return;
        }

        if (child.left == null && child.right == null) {
            if (parent.left == child) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }

        removeLeafNode(child, child.left);
        removeLeafNode(child, child.right);
    }

    public ArrayList<Integer> rootToNodePath(int target) {
        return this.rootToNodePath(this.root, target);
    }

    private ArrayList<Integer> rootToNodePath(Node node, int target) {
        if (node == null) {
            return new ArrayList<>();
        }

        if (node.data == target) {
            ArrayList<Integer> path = new ArrayList<>();
            path.add(node.data);
            return path;
        }

        ArrayList<Integer> leftPath = this.rootToNodePath(node.left, target);
        if (leftPath.size() > 0) {
            leftPath.add(node.data);
            return leftPath;
        }

        ArrayList<Integer> rightPath = this.rootToNodePath(node.right, target);
        if (rightPath.size() > 0) {
            rightPath.add(node.data);
            return rightPath;
        }

        return new ArrayList<>();
    }

    private void printKDown(Node node, int k) {
        if (node == null) {
            return;
        }

        if (k == 0) {
            System.out.print(node.data + ", ");
        }

        printKDown(node.left, k - 1);
        printKDown(node.right, k - 1);
    }

    public void printKFar(int targetNode, int k) {
        ArrayList<Node> pathList = rootToNodePathAsNodes(this.root, targetNode);
        for (int i = 0; i < pathList.size(); i++) {
            if (i == 0) {
                printKDown(pathList.get(i), k);
            } else if (i == k) {
                printKDown(pathList.get(i), 0);
            } else {
                Node currentNode = pathList.get(i);
                Node previousNode = pathList.get(i - 1);

                if (currentNode.left == previousNode) {
                    printKDown(currentNode.right, k - i - 1);
                } else {
                    printKDown(currentNode.left, k - i - 1);
                }
            }
        }

    }

    private ArrayList<Node> rootToNodePathAsNodes(Node node, int target) {
        if (node == null) {
            return new ArrayList<>();
        }

        if (node.data == target) {
            ArrayList<Node> pathList = new ArrayList<>();
            pathList.add(node);
            return pathList;
        }

        ArrayList<Node> leftPath = rootToNodePathAsNodes(node.left, target);
        if (leftPath.size() > 0) {
            leftPath.add(node);
            return leftPath;
        }

        ArrayList<Node> rightPath = rootToNodePathAsNodes(node.right, target);
        if (rightPath.size() > 0) {
            rightPath.add(node);
            return rightPath;
        }

        return new ArrayList<>();
    }

    public void printRootToLeafPaths() {
        this.printRootToLeafPaths();
    }

    private void printRootToLeafPaths(Node node, int sumSoFar, String pathSoFar, int target) {
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) {
            if (sumSoFar + node.data < target) {
                System.out.println(pathSoFar + "->" + node.data);
            }
            return;
        }

        printRootToLeafPaths(node.left, sumSoFar + node.data, pathSoFar + "->" + node.data, target);
        printRootToLeafPaths(node.right, sumSoFar + node.data, pathSoFar + "-> " + node.data, target);
    }

    public int diameter() {
        return this.diameter(this.root);
    }

    private int diameter(Node node) {
        if (node == null) {
            return 0;
        }

        int leftDiameter = this.diameter(node.left);
        int rightDiamter = this.diameter(node.right);

        int leftHeight = this.height(node.left);
        int rightHeight = this.height(node.right);

        int heightThroughRoot = leftHeight + rightHeight + 2;

        return Math.max((Math.max(leftDiameter, rightDiamter)), heightThroughRoot);
    }

    private class DiaPair {
        int height;
        int diameter;
    }

    public int diameter2() {
        return this.diameter2(this.root).height;
    }

    private DiaPair diameter2(Node node) {
        if (node == null) {
            DiaPair basePair = new DiaPair();
            basePair.height = -1;
            basePair.diameter = 0;
            return basePair;
        }

        DiaPair leftPair = this.diameter2(node.left);
        DiaPair rightPair = this.diameter2(node.right);

        DiaPair myPair = new DiaPair();
        myPair.height = Math.max(leftPair.height, rightPair.height) + 1;
        myPair.diameter = Math.max((leftPair.height + rightPair.height + 2), Math.max(leftPair.diameter, rightPair.diameter));
        return myPair;
    }

    public void printLeftView() {
        this.printLeftView(this.root);
    }

    private void printLeftView(Node node) {
        ArrayList<Integer> preO = new ArrayList<>();
        ArrayList<Integer> floorO = new ArrayList<>();

        preOTraversalAsSV(node, preO, floorO, 0);
        int[] floorCount = new int[this.size()];

        System.out.println(preO);
        System.out.println(floorO);

        for (int i = 0; i < floorO.size(); i++) {
            floorCount[floorO.get(i)] += 1;
            if (floorCount[floorO.get(i)] == 1) {
                System.out.print(preO.get(i) + " ");
            }
        }
        System.out.println();
    }


    private void preOTraversalAsSV(Node node, ArrayList<Integer> preO, ArrayList<Integer> floorO, int floor) {
        if (node == null) { // when there are no children (leaf-node), return
            return;
        }

        // faith calls for subtrees
        preO.add(node.data);
        floorO.add(floor);
        preOTraversalAsSV(node.left, preO, floorO, floor + 1);         // Left
        preOTraversalAsSV(node.right, preO, floorO, floor + 1);        // Right
    }

}
