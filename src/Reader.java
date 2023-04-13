import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {
    String filename;
    public Reader(String filename) {
        this.filename = filename;
    }

    String[] read() throws IOException {
        ArrayList<String> lines = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader(new File(filename)));

        String buffer = "";

        while ((buffer = br.readLine()) != null) {
            lines.add(buffer);
        }

        return lines.toArray(new String[lines.size()]);
    }

}
