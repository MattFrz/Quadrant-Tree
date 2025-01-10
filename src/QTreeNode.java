/**
 * Represents a node in a Quadtree.
 * @author Matt Farzaneh
 */
public class QTreeNode {
    private int x, y;
    private int size;
    private int color;
    private QTreeNode parent;
    private QTreeNode[] children;

    /**
     * Constructs a QTreeNode with default values.
     */
    public QTreeNode() {
        children = new QTreeNode[4];
        x = 0;
        y = 0;
        size = 0;
        color = 0;
        parent = null;
        
        // makes all values of children defaulted to null
        for (int i = 0; i < children.length; i++) {
            this.children[i] = null;
        }
    }

    /**
     * Constructs a QTreeNode with specified values.
     *
     * @param theChildren The child nodes.
     * @param xcoord      The x-coordinate.
     * @param ycoord      The y-coordinate.
     * @param theSize     The size.
     * @param theColor    The color.
     */
    public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) {
        
    	children = new QTreeNode[4];
        x = xcoord;
        y = ycoord;
        size = theSize;
        color = theColor;
        
        //Initializes children with value
        for (int i = 0; i < children.length; i++) {
            if (theChildren == null) {
                children[i] = null;
            } else {
                children[i] = theChildren[i];
            }
        }
    }

    /**
     * Checks if a point is contained within the node.
     *
     * @param xcoord The x-coordinate of the point.
     * @param ycoord The y-coordinate of the point.
     * @return True if the point is contained, otherwise false.
     */
    public boolean contains(int xcoord, int ycoord) {
        int maxX = this.x + this.size;
        int maxY = this.y + this.size;

        if(xcoord >= this.x && xcoord < maxX && ycoord >= this.y && ycoord < maxY) {
        	return true;
        } else {
        	return false;
        }
    }

    /**
     * Gets the x-coordinate of the node.
     *
     * @return The x-coordinate.
     */
    public int getx() {
        return this.x;
    }

    /**
     * Gets the y-coordinate of the node.
     *
     * @return The y-coordinate.
     */
    public int gety() {
        return this.y;
    }

    /**
     * Gets the size of the node.
     *
     * @return The size.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Gets the color of the node.
     *
     * @return The color.
     */
    public int getColor() {
        return this.color;
    }

    /**
     * Gets the parent node.
     *
     * @return The parent node.
     */
    public QTreeNode getParent() {
        return this.parent;
    }

    /**
     * Gets the child node at the specified index.
     *
     * @param index The index of the child node.
     * @return The child node.
     * @throws QTreeException if the index is invalid or the children array is null.
     */
    public QTreeNode getChild(int index) throws QTreeException {
        if (children == null) {
            throw new QTreeException("Children array is null.");
        }

        if (index < 0 || index > 3) {
            throw new QTreeException("Invalid index");
        }

        return this.children[index];
    }

    /**
     * Sets the x-coordinate of the node.
     *
     * @param newX The new x-coordinate.
     */
    public void setx(int newX) {
        this.x = newX;
    }

    /**
     * Sets the y-coordinate of the node.
     *
     * @param newY The new y-coordinate.
     */
    public void sety(int newY) {
        this.y = newY;
    }

    /**
     * Sets the size of the node.
     *
     * @param newSize The new size.
     */
    public void setSize(int newSize) {
        this.size = newSize;
    }

    /**
     * Sets the color of the node.
     *
     * @param newColor The new color.
     */
    public void setColor(int newColor) {
        this.color = newColor;
    }

    /**
     * Sets the parent node.
     *
     * @param newParent The new parent node.
     */
    public void setParent(QTreeNode newParent) {
        this.parent = newParent;
    }

    /**
     * Sets the child node at the specified index.
     *
     * @param newChild The new child node.
     * @param index    The index of the child node.
     * @throws QTreeException if the index is invalid or the children array is null.
     */
    public void setChild(QTreeNode newChild, int index) throws QTreeException {
        if (children == null) {
            throw new QTreeException("Children array is null.");
        }

        if (index < 0 || index > 3) {
            throw new QTreeException("Invalid index");
        }

        this.children[index] = newChild;
    }

    /**
     * Checks if the node is a leaf node.
     *
     * @return True if the node is a leaf, otherwise false.
     */
    public boolean isLeaf() {
        if (this.children == null) {
            return true;
        }

        for (int i = 0; i < children.length; i++) {
            if (children[i] != null) {
                return false;
            }
        }
        return true;
    }
}
