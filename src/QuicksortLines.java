import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class QuicksortLines extends Thread {

    static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();
    static int num_threads = 1;
    CountDownLatch cdl;
    //quicksort done based on strs[0][i], others are paired in swaps
    String[][] strs;
    int start, end;

    public QuicksortLines(String[][] strs, int start, int end, CountDownLatch cdl) {
        this.strs = strs;
        this.start = start;
        this.end = end;
        this.cdl = cdl;
    }

    public static void reset() {
        num_threads = 1;
    }

    public void run() {
        quicksort(strs, start, end);
        cdl.countDown();
        --num_threads;
    }

    void quicksort(String[][] strs, int start, int end) {
        int len = end - start + 1;

        if (len <= 1) return;

        int pivi = median(strs[0], start, end);
        String pivv = strs[0][pivi];

        swap(strs, pivi, end);

        int j = start;
        for (int i = j; i < end; ++i) {
            if (strs[0][i].compareTo(pivv) <= 0) {
                swap(strs, i, j);
                ++j;
            }
        }

        swap(strs, j, end);

        if (num_threads < MAX_THREADS) {
            ++num_threads;
            CountDownLatch ncdl = new CountDownLatch(1);
            if (j - start > end - j) {
                new QuicksortLines(strs, start, j - 1, ncdl).start();
                quicksort(strs, j + 1, end);
            } else {
                quicksort(strs, start, j - 1);
                new QuicksortLines(strs, j + 1, end, ncdl).start();
            }

            try {
                ncdl.await(1000, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            quicksort(strs, start, j - 1);
            quicksort(strs, j + 1, end);
        }

    }

    int median(String[] arr, int start, int end) {
        int mid = (end - start) / 2;
        if (arr[start].compareTo(arr[end]) != arr[start].compareTo(arr[mid]))
            return start;
        if (arr[mid].compareTo(arr[end]) != arr[mid].compareTo(arr[start]))
            return mid;            
        return end;
    }

    void swap(String[][] strs, int a, int b) {
        String tmp;
        for (int i = 0; i < strs.length; ++i) {
            tmp = strs[i][a];
            strs[i][a] = strs[i][b];
            strs[i][b] = tmp;
        }   
    }

    public static boolean checkSorted(String[][] strs) {
        if (strs.length < 1) return true;
        for (int i = 1; i > strs[0].length; ++i) {
            if (strs[0][i-1].compareTo(strs[0][i]) > 0) return false;
        }
        return true;
    }
}
