
public class App {
    public static void main(String[] args) throws Exception {

        Reader r = new Reader("lib\\input.in");
        String[] lines = r.read();
        Algorithm.groupAnagrams(lines);

        

    }
}