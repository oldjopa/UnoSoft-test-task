import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage:  java -jar tst-1.0.jar <file>");
            System.exit(0);
        }
        long startTime = System.currentTimeMillis();
        String filePath = args[0];
        List<Group> result = parseAndGroup(filePath);

        result = result.stream()
                .filter(k -> k.getLines().size() > 1)
                .sorted(Comparator.comparingInt((Group k) -> k.getLines().size()).reversed())
                .toList();
        try {
            long endTime = System.currentTimeMillis();
            System.out.println("Время выполнения: " + (endTime - startTime) + " ms");
            System.out.println("Всего групп с более чем 1 элементом: " + result.size());
            Writer.writeResultToFile("output.txt", result);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static List<Group> parseAndGroup(String filename) {
        int lineCount = 0;

        // 1. Подсчет строк
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
