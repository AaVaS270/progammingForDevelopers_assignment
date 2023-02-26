package assignments;

public class Q8b {
    public static int findKthMissingEvenNumber(int[] a, int k) {
        int missingCount = 0;
        int prev = -2;
        for (int i = 0; i < a.length; i++) {
            int diff = a[i] - prev - 2;
            if (diff > 0) {
                if (missingCount + diff >= k) {
                    return prev + 2 + k - missingCount - 1;
                }
                missingCount += diff;
            }
            prev = a[i];
        }
        return prev + k - missingCount;
    }

    public static void main(String[] args) {
        int[] a = {0, 2, 6, 18, 22};
        int k = 6;
        int result = findKthMissingEvenNumber(a, k);
        System.out.println("The " + k + "th missing even number in the array is: " + result);
    }
}
