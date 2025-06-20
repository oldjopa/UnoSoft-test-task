import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class FileParser {
    public List<List<Long>> parseFile(String filename){
        List<List<Long>> res = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(filename))))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                String[] values = line.split(";");
                List<Long> cleaned = new ArrayList<>();

                for (String value : values) {
                    value = value.replaceAll("\"", "");
                    if (value.isEmpty()) {
                        cleaned.add(null);
                    } else {
                        try {
                            cleaned.add(Long.parseLong(value));
                        } catch (NumberFormatException e) {
                            cleaned.clear();
                            break;
                        }
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
