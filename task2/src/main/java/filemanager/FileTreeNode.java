package filemanager;

import com.sun.org.apache.xerces.internal.xs.StringList;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class FileTreeNode implements TreeNode<String> {
    private String path = "";
    private TreeNode<String> parent = null;
    private List<TreeNode<String>> children = new ArrayList<TreeNode<String>>();

    public FileTreeNode(String data) {
        this.path = data;
    }

    public TreeNode<String> addNode(String data) {
        FileTreeNode node = new FileTreeNode(data);
        this.children.add(node);
        return node;
    }

    public void addNode(TreeNode<String> node) {
        this.children.add(node);
        node.setParent(this);
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
        if(!child.isLeaf()){
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

    public String getData() {
        return this.path;
    }

    public void setData(String data) {
        this.path = data;
    }

    public String getFullPath() {
        String path = "";
        if (this.getData() != null) {
            path = this.getData();
        }
        TreeNode currentNode = this.getParent();
        while (currentNode.getParent() != null) {
            if (currentNode.getData() != null) {
                path = currentNode.getData() + "\\" + path;
            }
            currentNode = currentNode.getParent();
        }
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
                    if ((child.getData() != null) && (path != null)) {
                        if (child.getData().equals(path)) {
                            return child;
                        }
                    } else {
                        if ((child.getData() == null) && (path == null)) {
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
