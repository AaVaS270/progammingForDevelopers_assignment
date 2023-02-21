package assignments;

import java.util.LinkedList;

public class Q4b {
    public static int stepsToSortLinkedList(LinkedList<Integer> list) {
        int steps = 0;
        boolean sorted = false;

        while (!sorted) {
            sorted = true;
            int prev = list.getFirst();
            for (int i = 1; i < list.size(); i++) {
                int curr = list.get(i);
                if (prev > curr) {
                    list.remove(i);
                    sorted = false;
                    steps++;
                } else {
                    prev = curr;
                }
            }
        }

        return steps;
    }

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(3);
        list.add(1);
        list.add(4);
        list.add(2);
        list.add(7);
        list.add(0);
        int steps = stepsToSortLinkedList(list);
        System.out.println("Steps to sort: " + steps);
    }
}

