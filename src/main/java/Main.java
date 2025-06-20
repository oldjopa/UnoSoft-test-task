import java.io.IOException;
import java.util.*;

class DSU {
    private final int[] parent;

    public DSU(int size) {
        parent = new int[size];
        for (int i = 0; i < size; i++) parent[i] = i;
    }

    public int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    public void union(int x, int y) {
        int rx = find(x);
        int ry = find(y);
        if (rx != ry) parent[ry] = rx;
    }
}

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage:  java -jar tst-1.0.jar <file>");
            System.exit(0);
        }
        long startTime = System.currentTimeMillis();
        String filePath = args[0];
        FileParser fileParser = new FileParser();
        List<List<Long>> parsedFile = fileParser.parseFile(filePath);
        List<Group> result = parseGroups(parsedFile);

        result = result.stream()
                .filter(k -> k.getLines().size() > 1)
                .sorted(Comparator.comparingInt((Group k) -> k.getLines().size()).reversed())
                .toList();
        try {
            long endTime = System.currentTimeMillis();
            System.out.println("Время выполнения: " + (endTime - startTime) + " ms");
            System.out.println("Всего групп с более чем 1 элементом: " + result.size());
            Writer.writeResultToFile("output.txt", result);
        }catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static List<Group> parseGroups(List<List<Long>> parsedFile) {
        int n = parsedFile.size();
        DSU dsu = new DSU(n);
        Map<Unit, Integer> unitToLine = new HashMap<>();

        for (int lineIndex = 0; lineIndex < n; lineIndex++) {
            List<Long> line = parsedFile.get(lineIndex);
            for (int colIndex = 0; colIndex < line.size(); colIndex++) {
                Long value = line.get(colIndex);
                if (value == null) continue;

                Unit unit = new Unit(colIndex, value);

                if (unitToLine.containsKey(unit)) {
                    int otherIndex = unitToLine.get(unit);
                    dsu.union(lineIndex, otherIndex);
                } else {
                    unitToLine.put(unit, lineIndex);
                }
            }
        }

        Map<Integer, Group> groups = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = dsu.find(i);
            groups.computeIfAbsent(root, k -> new Group()).addLine(parsedFile.get(i));
        }

        return new ArrayList<>(groups.values());
    }
}
