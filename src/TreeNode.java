import java.util.ArrayList;

/**
 *
 * @author madsilva
 * @author jgeati
 */
public class TreeNode<T> {
    private T data;
    private ArrayList<TreeNode> children;
        
    public TreeNode(T d) {
        data = d;
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
}
