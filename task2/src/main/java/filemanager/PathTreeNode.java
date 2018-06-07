package filemanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PathTreeNode implements TreeNode<String> {
    private String path = "";
    private TreeNode<String> parent = null;
    private List<TreeNode<String>> children = new ArrayList<TreeNode<String>>();

    /**
     * constructor
     * @param path
     */
    public PathTreeNode(String path) {
        this.path = path;
    }

    /**
     * adds new child <code>PathTreeNode</code>  to current node, constricting new node by path
     * @param path
     * @return new child
     */
    public PathTreeNode addNode(String path) {
        PathTreeNode node = new PathTreeNode(path);
        this.children.add(node);
        node.setParent(this);
        return node;
    }

    /**
     * adds this child <code>TreeNode</code>  to current node
     * @param node
     * @return
     */
    public TreeNode<String> addNode(TreeNode<String> node) {
        this.children.add(node);
        node.setParent(this);
        return node;
    }

    /**
     * adds collection of <code>TreeNode</code> to children collection of this node
     * @param children
     */
    public void addChildren(Collection<TreeNode<String>> children) {
        if (children == null) {
            throw new IllegalArgumentException();
        }
        this.children.addAll(children);
    }

    /**
     * removes given node from Tree
     * @param child
     * @return
     */
    public boolean removeNode(TreeNode<String> child) {
        TreeNode<String> parent = child.getParent();
        parent.getChildren().remove(child);
        child.setParent(null);
        if (!child.isLeaf()) {
            parent.getChildren().addAll(child.getChildren());
        }
        return true;
    }

    /**
     * removes a node with given path
     * @param path
     * @return
     */
    public boolean removeNode(String path) {
        TreeNode<String> node = this.search(path);
        this.removeNode(node);
        return false;
    }

    /**
     *
     * @return children list  of this node
     */
    public List<TreeNode<String>> getChildren() {
        return children;
    }

    /**
     *
     * @return path from this node
     */
    public String getPath() {
        return this.path;
    }

    /**
     * set given path to this node
     * @param path
     */
    public void setData(String path) {
        this.path = path;
    }

    /**
     * @return full tree path from this node to root node
     */
    public String getFullPath() {
        String path = "";

        if (this.getPath() != null) {
            path = this.getPath();
        }
        TreeNode currentNode = this;
        do {
            if (currentNode.getParent() != null) {
                path = currentNode.getParent().getPath() + "\\" + path;
            }
            currentNode = currentNode.getParent();
        }while (currentNode != null);
        return path;
    }

    /**
     *
     * @return sub-path from this node to root-previous node
     */
    public String getSubPath() {
        String path = "";

        if (this.getPath() != null) {
            path = this.getPath();
        }
        TreeNode currentNode = this;
        while (currentNode.getParent() != null) {
            currentNode = currentNode.getParent();
            if (currentNode.getParent() != null) {
                path = currentNode.getParent().getPath() + "\\" + path;
            }

        }
        return path;
    }

    /**
     *
     * @return shallow copy of this node
     */
    public TreeNode<String> copy() {
        PathTreeNode result = new PathTreeNode(this.getPath());
        result.addChildren(this.getChildren());
        return result;
    }

    /**
     *
     * @return parent if this node
     */
    public TreeNode<String> getParent() {
        return this.parent;
    }

    /**
     * set parent to this node
     * @param parent
     */
    public void setParent(TreeNode<String> parent) {
        this.parent = parent;
    }

    /**
     *
     * @return true if this node is leaf, false if not
     */
    public boolean isLeaf() {
        if (this.children == null) {
            return true;
        }
        return (this.children.size() == 0);
    }

    /**
     * recursive search for node with given path
     * @param path
     * @return found node or null
     */
    private TreeNode<String> search(String path) {
        if (!this.isLeaf()) {
            List<TreeNode<String>> currentLvl = this.children;
            List<TreeNode<String>> nextLvl = new ArrayList<TreeNode<String>>();
            while (currentLvl.size() != 0) {
                for (TreeNode<String> child : currentLvl) {
                    if ((child.getPath() != null) && (path != null)) {
                        if (child.getPath().equals(path)) {
                            return child;
                        }
                    } else {
                        if ((child.getPath() == null) && (path == null)) {
                            return child;
                        }
                    }
                    if (!child.isLeaf()) {
                        nextLvl.addAll(child.getChildren());
                    }
                }
                currentLvl = nextLvl;
                nextLvl = new ArrayList<TreeNode<String>>();
            }

        }
        return null;
    }
}
