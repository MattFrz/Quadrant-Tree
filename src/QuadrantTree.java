/**
 * Represents a QuadTree data structure.
 * @author Matt Farzaneh
 */
public class QuadrantTree {
    private QTreeNode root;

    /**
     * Constructs a QuadrantTree with the given pixel data.
     *
     * @param thePixels The pixel data represented as a 2D array of integers.
     */
    public QuadrantTree(int[][] thePixels) {
        int size = thePixels.length;
        this.root = buildTree(thePixels, 0, 0, size);
    }

    /**
     * Recursively builds the QuadTree.
     *
     * @param pixels The pixel data represented as a 2D array of integers.
     * @param x      The starting x-coordinate.
     * @param y      The starting y-coordinate.
     * @param size   The size of the quadrant.
     * @return The root node of the constructed Quadtree.
     */
    private QTreeNode buildTree(int[][] pixels, int x, int y, int size) {
    	// Base case: If the size of the quadrant is 1, create a leaf node and return it
    	if (size == 1) {
            int color = pixels[x][y];
            return new QTreeNode(null, x, y, size, color);
        }

        int halfSize = size / 2;
        int[][] subPixels = new int[halfSize][halfSize];
        for (int i = x; i < x + halfSize; i++) {
            for (int j = y; j < y + halfSize; j++) {
                subPixels[i - x][j - y] = pixels[i][j];
            }
        }
        
        //uses recursion to build tree
        QTreeNode[] children = new QTreeNode[4];
        children[0] = buildTree(pixels, x, y, halfSize);
        children[1] = buildTree(pixels, x, y + halfSize, halfSize);
        children[2] = buildTree(pixels, x + halfSize, y, halfSize);
        children[3] = buildTree(pixels, x + halfSize, y + halfSize, halfSize);

        int avgColor = Gui.averageColor(pixels, x, y, size);
        return new QTreeNode(children, x, y, size, avgColor);
    }

    /**
     * Retrieves the root node of the QuadrantTree.
     *
     * @return The root node.
     */
    public QTreeNode getRoot() {
        return this.root;
    }

    /**
     * Retrieves the pixels at the specified level from the given node.
     *
     * @param node  The node from which to retrieve pixels.
     * @param level The level of the pixels to retrieve.
     * @return A list of nodes containing the pixels.
     */
    public ListNode<QTreeNode> getPixels(QTreeNode node, int level) {
    	// Base case: If the node is a leaf or the specified level is less than 1, return a list containing the node itself
    	if (node.isLeaf() || level < 1) {
            return new ListNode<QTreeNode>(node);
        }

        ListNode<QTreeNode> firstNode = null;
        ListNode<QTreeNode> currentNode = null;

        int i = 0;
        while (i < 4) {
            QTreeNode childNode = node.getChild(i);

            if (childNode != null) {
                ListNode<QTreeNode> newNode = getPixels(childNode, level - 1);

                if (firstNode == null) {
                    firstNode = newNode;
                    currentNode = newNode;
                } else {
                    currentNode.setNext(newNode);
                    currentNode = newNode;
                }

                while (currentNode.getNext() != null) {
                    currentNode = currentNode.getNext();
                }
            }
            i++;
        }

        return firstNode;
    }

    /**
     * Finds nodes with pixels similar to the specified color at the given level.
     *
     * @param r        The root node of the QuadrantTree.
     * @param theColor The color to match.
     * @param theLevel The level at which to search for the color.
     * @return A Duple containing a list of matching nodes and the count of matches.
     */
    public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
    	// Base case: If the node is a leaf or the specified level is 0, check if the color matches
    	if (r.isLeaf() || theLevel == 0) {
            if (Gui.similarColor(r.getColor(), theColor)) {
                return new Duple(new ListNode<QTreeNode>(r), 1);
            }
            return new Duple(null, 0);
        }

        ListNode<QTreeNode> first = null;
        ListNode<QTreeNode> current = null;
        int count = 0;

        int i = 0;
        do {
            QTreeNode child = r.getChild(i);
            if (child != null) {
                Duple duple = findMatching(child, theColor, theLevel - 1);
                if (duple.getCount() > 0) {
                    if (first == null) {
                        first = duple.getFront();
                        current = first;
                        while (current.getNext() != null) {
                            current = current.getNext();
                        }
                    } else {
                        current.setNext(duple.getFront());
                        while (current.getNext() != null) {
                            current = current.getNext();
                        }
                    }
                    count += duple.getCount();
                }
            }
            i++;
        } while (i < 4);

        return new Duple(first, count);
    }

    /**
     * Finds the node at the specified level and coordinates.
     *
     * @param r        The root node of the QuadrantTree.
     * @param theLevel The level at which to find the node.
     * @param x        The x-coordinate of the node.
     * @param y        The y-coordinate of the node.
     * @return The node found at the specified level and coordinates, or null if not found.
     */
    public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
    	// Base case: If the specified level is 0 and the coordinates match, return the current node
    	if (theLevel == 0) {
            if (x == r.getx() && y == r.gety()) {
                return r;
            }
        }

        int i = 0;
        while (i < 4) {
            QTreeNode child = r.getChild(i);

            if (child != null) {
                QTreeNode node = findNode(child, theLevel - 1, x, y);

                if (node != null) {
                    return node;
                }
            }
            i++;
        }

        return null;
    }
}