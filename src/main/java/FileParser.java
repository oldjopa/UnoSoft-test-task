import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileParser {
    public List<List<String>> parseFile(String filename) {
        List<List<String>> res = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                String[] values = line.split(";");
                List<String> cleaned = new ArrayList<>();

                for (String value : values) {
                    value = value.replaceAll("\"", "");
                    if (value.isEmpty()) {
                        cleaned.add(null);
                    } else {
                        cleaned.add(value);
                    }
                }
                if (!cleaned.isEmpty()) {
                    res.add(cleaned);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
