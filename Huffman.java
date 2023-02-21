package assignments;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
    private static class Node {
        private final char character;
        private final int frequency;
        private final Node leftChild;
        private final Node rightChild;

        public Node(char character, int frequency, Node leftChild, Node rightChild) {
            this.character = character;
            this.frequency = frequency;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        public boolean isLeaf() {
            return leftChild == null && rightChild == null;
        }
    }

    public static String encode(String input) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.frequency));
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.offer(new Node(entry.getKey(), entry.getValue(), null, null));
        }

        while (priorityQueue.size() > 1) {
            Node leftChild = priorityQueue.poll();
            Node rightChild = priorityQueue.poll();
            priorityQueue.offer(new Node('\0', leftChild.frequency + rightChild.frequency, leftChild, rightChild));
        }

        Map<Character, String> encodingMap = new HashMap<>();
        buildEncodingMap(priorityQueue.peek(), "", encodingMap);

        StringBuilder encoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            encoded.append(encodingMap.get(c));
        }

        return encoded.toString();
    }

    private static void buildEncodingMap(Node node, String encoding, Map<Character, String> encodingMap) {
        if (node.isLeaf()) {
            encodingMap.put(node.character, encoding);
        } else {
            buildEncodingMap(node.leftChild, encoding + "0", encodingMap);
            buildEncodingMap(node.rightChild, encoding + "1", encodingMap);
        }
    }

    public static String decode(String input, Node root) {
        StringBuilder decoded = new StringBuilder();
        Node current = root;
        for (char c : input.toCharArray()) {
            if (c == '0') {
                current = current.leftChild;
            } else {
                current = current.rightChild;
            }
            if (current.isLeaf()) {
                decoded.append(current.character);
                current = root;
            }
        }
        return decoded.toString();
    }
    
    public static void main(String[] args) {
        String input = "Hello, world!";
        String encoded = Huffman.encode(input);

        System.out.println("Input: " + input);
        System.out.println("Encoded: " + encoded);

        Node root = decodeTree(encoded);
        String decoded = Huffman.decode(encoded, root);

        System.out.println("Decoded: " + decoded);
    }

    private static Node decodeTree(String encodedTree) {
        int i = 0;
        Node root = null;
        Node current = null;
        while (i < encodedTree.length()) {
            char c = encodedTree.charAt(i);
            if (c == '0') {
                if (current == null) {
                    current = new Node('\0', 0, null, null);
                    if (root == null) {
                        root = current;
                    }
                } else {
                    current.leftChild = new Node('\0', 0, null, null);
                    current = current.leftChild;
                }
            } else {
                if (current == null) {
                    current = new Node('\0', 0, null, null);
                    if (root == null) {
                        root = current;
                    }
                } else {
                    current.rightChild = new Node('\0', 0, null, null);
                    current = current.rightChild;
                }
            }
            i++;
        }
        return root;
    }

}
