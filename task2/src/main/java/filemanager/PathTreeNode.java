package filemanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PathTreeNode implements TreeNode<String> {
    private String path = "";
    private TreeNode<String> parent = null;
    private List<TreeNode<String>> children = new ArrayList<TreeNode<String>>();

    public PathTreeNode(String data) {
        this.path = data;
    }

    public PathTreeNode addNode(String data) {
        PathTreeNode node = new PathTreeNode(data);
        this.children.add(node);
        node.setParent(this);
        return node;
    }

    public TreeNode<String> addNode(TreeNode<String> node) {
        this.children.add(node);
        node.setParent(this);
        return node;
    }

    public void addChildren(Collection<TreeNode<String>> children) {
        if (children == null) {
            throw new IllegalArgumentException();
        }
        this.children.addAll(children);
        for (TreeNode<String> node : children) {
            node.setParent(this);
        }
    }

    public boolean removeNode(TreeNode<String> child) {
        TreeNode<String> parent = child.getParent();
        parent.getChildren().remove(child);
        child.setParent(null);
        if (!child.isLeaf()) {
            parent.getChildren().addAll(child.getChildren());
        }
        return true;
    }

    public boolean removeNode(String data) {
        TreeNode<String> node = this.search(data);
        this.removeNode(node);
        return false;
    }

    public List<TreeNode<String>> getChildren() {
        return children;
    }

    public String getPath() {
        return this.path;
    }

    public void setData(String data) {
        this.path = data;
    }

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

    public TreeNode<String> getParent() {
        return this.parent;
    }

    public void setParent(TreeNode<String> parent) {
        this.parent = parent;
    }

    public boolean isLeaf() {
        if (this.children == null) {
            return true;
        }
        return (this.children.size() == 0);
    }

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
