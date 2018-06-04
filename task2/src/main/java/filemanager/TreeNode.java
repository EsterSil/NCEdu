package filemanager;

import java.util.Collection;
import java.util.Iterator;


public interface TreeNode <T> {

    /**
     * Returns the parent <code>TreeNode</code>.
     */
    TreeNode<T> getParent();


    /**
     * Sets the parent <code>TreeNode</code>.<br/>
     * Is typically called in {@link #addNode(TreeNode)} and {@link #removeNode(TreeNode)} methods
     *  of the parent <code>TreeNode</code> itself.
     */
    void setParent(TreeNode<T> parent);

    /**
     * Returns false if this <code>TreeNode</code> has non-zero number of children.
     * @return true if the node is a leaf (i.e. does not have child nodes)
     */
    boolean isLeaf();

    /**
     * Adds the given node to this <code>TreeNode</code> and sets this as the parent to it.
     */
    void addNode(TreeNode<T> node);

    /**
     * Add given collection of children to this <code>TreeNode</code>
     */
    void addChildren(Collection<TreeNode<T>> children);


    /**
     * Removes the given node from this <code>TreeNode</code> and (if succeeded)
     *  sets null as the parent to it (in order to leave the tree in the consistent state).
     * @return true if succeeded, false if the child was not found
     */
    boolean removeNode(TreeNode<T> node);
    /**
     * Removes the node with given data from this <code>TreeNode</code> and (if succeeded)
     *  sets null as the parent to it (in order to leave the tree in the consistent state).
     * @return true if succeeded, false if the child was not found
     */
    boolean removeNode(T data);

    /**
     * @return user data stored in this <code>TreeNode</code>.
     */
    T getData();


    /**
     * Sets the user data to store in this <code>TreeNode</code>.
     */
    void setData(T data);

    /**
     * @return the collection of children of this <code>TreeNode</code>.
     */
    Collection<TreeNode<T>> getChildren();

    /**
     * @return the string representation of the path from root to this <code>TreeNode</code>.Path elements are separated by "\".
     */
    String getFullPath();
}
