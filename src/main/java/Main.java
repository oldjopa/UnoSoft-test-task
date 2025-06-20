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
        List<Group> result = FileParser.parseAndGroup(filePath);

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

}
