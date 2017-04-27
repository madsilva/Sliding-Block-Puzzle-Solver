
import java.util.ArrayList;

/**
 *
 * @author madsilva
 * @author jgeati
 */

public class TreeNode<T> {
    private T data;
    private TreeNode parent;
    private ArrayList<TreeNode> children;
        
    // constructor for creating a root node with data d
    public TreeNode(T d) {
        data = d;
        parent = null;
        children = new ArrayList();
    }
    
    // constructor for creating a child node with parent p and data d
    public TreeNode(T d, TreeNode p) {
        data = d;
        parent = p;
        children = new ArrayList();
    }
    
    public void addChild(T c) {
        children.add(new TreeNode(c, this));
    }
    
    public ArrayList<TreeNode> getChildren() {
        return children;
    }
    
    public T getData() {
        return data;
    }
}
