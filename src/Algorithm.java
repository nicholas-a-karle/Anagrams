import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Algorithm {

    public static void groupAnagrams(String[] lines) {

        String[][] set = {new String[lines.length], lines};

        for (int i = 0; i < lines.length; ++i) {
            char[] chars = lines[i].toLowerCase().toCharArray();
            insertionsort(chars);
            set[0][i] = new String(chars);
        }

        CountDownLatch cdl = new CountDownLatch(1);
        new QuicksortLines(set, 0, lines.length, cdl).start();

        int j = 0;
        for (int i = 0; i < set[0].length; ++i) {
            if (!set[0][i].equals(set[0][j])) {
                System.out.println("Anagrams of form " + set[0][j]);
                for (int k = j; k < i; ++k) {
                    System.out.println(set[1][k]);
                }
                j = i;
            }
        }
    }

    public static void insertionsort(char[] chars) {
        for (int i = 1; i < chars.length; ++i)
            for (int j = i; j > 0 && chars[j-1] > chars[j]; --j)
                swap(chars, j-1, j);
    }

    public static void swap(char[] chars, int a, int b) {
        char tmp = chars[a];
        chars[a] = chars[b];
        chars[b] = tmp;
    }
}