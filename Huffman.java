package assignments;
import java.util.PriorityQueue;
import java.util.HashMap;

public class Huffman {

    static class Node implements Comparable<Node> {
        char ch;
        int freq;
        Node left, right;
        
        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        
        public boolean isLeaf() {
            return (left == null && right == null);
        }
        
        public int compareTo(Node node) {
            return freq - node.freq;
        }
    }
    
    // build tree and return root node
    private static Node buildTree(String text) {
        // Count frequency of characters
        HashMap<Character, Integer> freqMap = new HashMap<>();
        for (char ch : text.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }
        
        // Create priority queue and add all leaf nodes to it
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (char ch : freqMap.keySet()) {
            pq.add(new Node(ch, freqMap.get(ch), null, null));
        }
        
        // Merge two smallest nodes and add back to queue until only one node is left
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.add(parent);
        }
        
        return pq.poll();
    }
    
    //  Generate codes and store in a HashMap
    private static void generateCodes(Node root, String code, HashMap<Character, String> codeMap) {
        if (root.isLeaf()) {
            codeMap.put(root.ch, code);
        } else {
            generateCodes(root.left, code + "0", codeMap);
            generateCodes(root.right, code + "1", codeMap);
        }
    }
    
    // Encode input text using 
    public static String encode(String text) {
        Node root = buildTree(text);
        HashMap<Character, String> codeMap = new HashMap<>();
        generateCodes(root, "", codeMap);
        
        StringBuilder sb = new StringBuilder();
        for (char ch : text.toCharArray()) {
            sb.append(codeMap.get(ch));
        }
        
        return sb.toString();
    }
    
    public static String decode(String encodedString, Node root) {
        StringBuilder sb = new StringBuilder();
        Node curr = root;
        for (char ch : encodedString.toCharArray()) {
            if (ch == '0') {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
            if (curr.isLeaf()) {
                sb.append(curr.ch);
                curr = root;
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        String text = "Dustin Porier";
        
        String encodedString = Huffman.encode(text);
        System.out.println("Encoded string: " + encodedString);
        
        Node root = Huffman.buildTree(text);
        String decodedString = Huffman.decode(encodedString, root);
        System.out.println("Decoded string: " + decodedString);
    }

}
