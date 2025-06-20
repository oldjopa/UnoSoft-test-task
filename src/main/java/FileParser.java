import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileParser {
    public static List<Group> parseAndGroup(String filename) {
        int lineCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        DSU dsu = new DSU(lineCount);
        Map<Unit, Integer> unitToLine = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineIndex = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                String[] values = line.split(";");

                for (int colIndex = 0; colIndex < values.length; colIndex++) {
                    String value = values[colIndex].replaceAll("\"", "");
                    if (value.isEmpty()) continue;

                    Unit unit = new Unit(colIndex, value);
                    if (unitToLine.containsKey(unit)) {
                        dsu.union(lineIndex, unitToLine.get(unit));
                    } else {
                        unitToLine.put(unit, lineIndex);
                    }
                }

                lineIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        Map<Integer, Group> groups = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineIndex = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                String[] values = line.split(";");
                List<String> cleaned = new ArrayList<>();

                for (String value : values) {
                    value = value.replaceAll("\"", "");
                    cleaned.add(value.isEmpty() ? null : value);
                }

                int root = dsu.find(lineIndex);
                groups.computeIfAbsent(root, k -> new Group()).addLine(cleaned);
                lineIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        return new ArrayList<>(groups.values());
    }
}
