package assignments;

import java.util.*;

public class Q2b {
    public static int minServiceCenters(TreeNode[] nodes) {
        if (nodes == null || nodes.length == 0) {
            return 0;
        }
        
        int[] count = new int[1];
        count[0] = 0;
        Set<TreeNode> visited = new HashSet<>();
        
        for (TreeNode node : nodes) {
            if (!visited.contains(node)) {
                dfs(node, visited, count);
                count[0]++;
            }
        }
        
        return count[0];
    }
    
    private static void dfs(TreeNode node, Set<TreeNode> visited, int[] count) {
        visited.add(node);
        
        if (node.left != null && !visited.contains(node.left)) {
            dfs(node.left, visited, count);
        }
        
        if (node.right != null && !visited.contains(node.right)) {
            dfs(node.right, visited, count);
        }
        
        if (node.parent != null && !visited.contains(node.parent)) {
            dfs(node.parent, visited, count);
        }
    }

    
    public static void main(String[] args) {
        TreeNode node0 = new TreeNode(0);
        TreeNode node1 = new TreeNode(0);
        TreeNode node2 = null;
        TreeNode node3 = new TreeNode(0);
        TreeNode node4 = null;
        TreeNode node5 = new TreeNode(0);
        TreeNode node6 = null;
        TreeNode node7 = null;
        TreeNode node8 = new TreeNode(0);
        
        node0.left = node1;
        node0.right = node2;
        node1.left = node3;
        node1.right = node4;
        node3.left = node5;
        node3.right = node6;
        node4.left = node7;
        node4.right = node8;
        node5.parent = node3;
        node6.parent = node3;
        node7.parent = node4;
        node8.parent = node4;
        node3.parent = node1;
        node4.parent = node1;
        node1.parent = node0;
        node2.parent = node0;
        node8.parent = node4;
        
        int result = minServiceCenters(new TreeNode[] {node0, node1, node2, node3, node4, node5, node6, node7, node8});
        System.out.println(result); // Expected output: 2
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode parent;
    
    public TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}
