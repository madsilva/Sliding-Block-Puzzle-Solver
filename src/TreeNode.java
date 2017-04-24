
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author madsilva
 */
public class TreeNode<T> {
    private T data;
    private TreeNode parent;
    private ArrayList<TreeNode> children;
        
    // creating the root
    public TreeNode(T d) {
        data = d;
        parent = null;
        children = new ArrayList();
    }
    
    public TreeNode(T d, TreeNode p) {
        data = d;
        parent = p;
        children = new ArrayList();
    }
    
    public void addChild(T c) {
        children.add(new TreeNode(c));
    }
    
    public ArrayList<TreeNode> getChildren() {
        return children;
    }
    
    public T getData() {
        return data;
    }
    
    // if node is root returns null
    public TreeNode getParent() {
        return parent;
    }
}
