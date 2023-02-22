package assignments;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixMultiplication {
    
    public static void multiply(int[][] a, int[][] b, int[][] c) {
        int n = a.length;
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        
        // Divide the work into equal subtasks and assign to threads
        int blockSize = n / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int startRow = i * blockSize;
            int endRow = (i == numThreads - 1) ? n : startRow + blockSize;
            Runnable task = new MultiplicationTask(a, b, c, startRow, endRow);
            executor.execute(task);
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    static class MultiplicationTask implements Runnable {
        int[][] a, b, c;
        int startRow, endRow;
        
        public MultiplicationTask(int[][] a, int[][] b, int[][] c, int startRow, int endRow) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.startRow = startRow;
            this.endRow = endRow;
        }
        
        public void run() {
            int n = a.length;
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < n; j++) {
                    int sum = 0;
                    for (int k = 0; k < n; k++) {
                        sum += a[i][k] * b[k][j];
                    }
                    c[i][j] = sum;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        int[][] a = {{1, 2}, {3, 4}};
        int[][] b = {{5, 6}, {7, 8}};
        int[][] c = new int[2][2];
        
        MatrixMultiplication.multiply(a, b, c);
        System.out.println("Multiplying:");
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                System.out.print(a[i][j] + " ");
            }
        System.out.print("with");
        for (int k=0;k<=b.length;k++) {
        	for(int l=0;l<b.length;l++) {
        	System.out.print(b[k][l]);
        }}
        
        for (int m = 0; m < c.length; m++) {
            for (int n = 0; n < c[0].length; n++) {
                System.out.print(c[m][n] + " ");
            }
            System.out.println();
        }
    }
}
    }
